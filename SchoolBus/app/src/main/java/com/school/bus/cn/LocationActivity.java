package com.school.bus.cn;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.school.bus.R;
import com.school.bus.cn.data.Student;
import com.school.bus.cn.listener.OnLocationResposeListener;
import com.school.bus.cn.net.NetworkProtocol;
import com.school.bus.cn.util.GsonUtil;

import java.util.List;

/**
 * Created by zhangxingpei on 2017/3/7.
 */

public class LocationActivity extends Activity {
    private TextureMapView mapView;
    private BaiduMap baiduMap;
    private MyLocationData mLocData;
    private boolean isFirstLoation = true;
    private LocationClient mLocationClient;
    private int driverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.location_layout);
        driverId=getIntent().getIntExtra("driverId",0);
        mapView = (TextureMapView)findViewById(R.id.driver_location);
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//普通地图
        mLocationClient = new LocationClient(getApplicationContext());
        initLocation();
    }
    private void requestForData() {
        NetworkProtocol.getLocationData(String.valueOf(driverId));
        NetworkProtocol.setOnResposeListener(new OnLocationResposeListener() {
            @Override
            public void onSuccess(String response) {
                mLocData = GsonUtil.changeJsonToLocation(response);
                if (mLocData != null) {
                    displayLocation();
                }
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(LocationActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart() {
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
        requestForData();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    public void onStop() {
        // 关闭图层定位
        baiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    private void displayLocation() {
        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 设置定位数据
        baiduMap.setMyLocationData(mLocData);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.location1);
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
        baiduMap.setMyLocationConfigeration(config);
        LatLng latLng = new LatLng(mLocData.latitude, mLocData.longitude);
        //定义地图状态
        MapStatus mMapStatus = null;
        if (isFirstLoation) {
            isFirstLoation = false;
            mMapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(20)//Level依次为：20、19、18、17、16、15、14、13、12、11、10、9、8、7、6、5、4、3 1
                    .build();
        } else {
            mMapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(baiduMap.getMapStatus().zoom)
                    .build();
        }
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);
    }
}
