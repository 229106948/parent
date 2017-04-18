package com.school.bus.cn.listener;

/**
 * Created by Administrator on 2017/4/1.
 */

public interface OnStudentResponseListener {
    public void onSuccess(String response);
    public void onFail(String message);
}
