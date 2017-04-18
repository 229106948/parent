package com.school.bus.cn.view;

import android.app.Activity;

/**
 * Created by zhangxingpei on 2017/3/31.
 */

public interface RegisterView {
    public Activity getActivity();
    public void telephoneToast(String error);
    public void passwordToast(String error);
    public void nameToast(String error);
}
