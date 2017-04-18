package com.school.bus.cn.listener;

/**
 * Created by Administrator on 2017/4/5.
 */

public interface OnDriverResponseListener {
    public void onSuccess(String response);
    public void onFail(String message);
}
