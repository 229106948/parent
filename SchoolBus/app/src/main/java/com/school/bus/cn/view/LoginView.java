package com.school.bus.cn.view;

import android.app.Activity;

import com.school.bus.cn.presenter.LoginPresenter;

/**
 * Created by Administrator on 2017/3/31.
 */

public interface LoginView {
    public Activity getActivity();
    public boolean isRememberPassword();
    public void telephoneToast(String error);
    public void passwordToast(String error);
    public void passwordAutoComplete(String telephone,String password);
    public void rememberPassword();
}
