package com.school.bus.main;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by zhangxinigpeion 2017/3/29.
 * 在Application中初始化创建实例,避免多次去new实例
 */

public class MainApplication extends Application {
    private static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue=Volley.newRequestQueue(getApplicationContext());
    }
    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
