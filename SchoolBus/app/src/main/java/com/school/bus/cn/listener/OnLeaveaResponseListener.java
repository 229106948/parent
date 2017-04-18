package com.school.bus.cn.listener;

/**
 * Created by zhang on 2017/4/16.
 */

public interface OnLeaveaResponseListener {
    public void onSuccess(String response);
    public void onFail(String message);
}
