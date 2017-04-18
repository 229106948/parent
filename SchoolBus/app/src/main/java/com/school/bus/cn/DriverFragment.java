package com.school.bus.cn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.school.bus.R;
import com.school.bus.cn.adapter.DriverListAdapter;
import com.school.bus.cn.data.Driver;
import com.school.bus.cn.data.Student;
import com.school.bus.cn.listener.OnDriverResponseListener;
import com.school.bus.cn.listener.OnLocationResposeListener;
import com.school.bus.cn.net.NetworkProtocol;
import com.school.bus.cn.util.GsonUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class DriverFragment extends Fragment implements AdapterView.OnItemClickListener{
    private View view;
    private ListView listView;
    private TextView noChild;
    private DriverListAdapter driverListAdapter;
    private List<Driver>drivers;
    private List<Student>students;
    TextView title;
    ImageView back;
    ImageView add;
    ImageView message;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.fragment_driver,null);
            initView();
        }
        //缓存的view需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }
    private void initView(){
        initTitle();
        noChild = (TextView) view.findViewById(R.id.no_child);
        listView=(ListView) view.findViewById(R.id.list_view);
        driverListAdapter=new DriverListAdapter(getActivity());
        listView.setAdapter(driverListAdapter);
        listView.setOnItemClickListener(this);
    }
    private void initTitle() {
        title = (TextView) view.findViewById(R.id.title_content);
        back = (ImageView) view.findViewById(R.id.back);
        add = (ImageView) view.findViewById(R.id.add);
        message = (ImageView) view.findViewById(R.id.message);
        title.setText("司机");
        back.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((students = (List<Student>) getActivity().getIntent().getSerializableExtra("student")) == null) {
            childVisibility();
        } else {
            getDriver();
        }
    }

    private void getDriver(){
        NetworkProtocol.getDriverData(String.valueOf(students.get(0).getParentId()));
        NetworkProtocol.setOnResposeListener(new OnDriverResponseListener() {
            @Override
            public void onSuccess(String response) {
              if(!TextUtils.isEmpty(response)){
                  drivers=GsonUtil.changeJsonToDriver(response);
                  driverListAdapter.setDrivers(drivers);
              }
            }
            @Override
            public void onFail(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void childVisibility() {
        listView.setVisibility(View.GONE);
        noChild.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(),LocationActivity.class);
        intent.putExtra("driverId",drivers.get(position).getId());
        startActivity(intent);
    }
}
