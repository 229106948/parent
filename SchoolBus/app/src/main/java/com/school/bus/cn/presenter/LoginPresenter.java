package com.school.bus.cn.presenter;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/3/31.
 */

public interface LoginPresenter {
    public void getArguments();
    public void start();
    public void getLoginResult(String telephone, String password);
    public void toLogin(String telephone, String password);
    public void toRegister();
    public void rememberPassword(String telephone, final String password);
    public void forgetPassword();
}
