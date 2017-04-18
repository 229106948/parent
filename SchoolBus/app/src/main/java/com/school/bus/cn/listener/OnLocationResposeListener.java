package com.school.bus.cn.listener;

/**
 * Created by Administrator on 2017/3/30.
 */

public interface OnLocationResposeListener {
    public void onSuccess(String response);
    public void onFail(String message);
}
