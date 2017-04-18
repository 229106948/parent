package com.school.bus.cn.presenter;

/**
 * Created by Administrator on 2017/3/31.
 */

public interface RegisterPresenter {
    public void getRegisterResult(String telephone, String password,String name);
    public void toRegister(String telephone, String password,String name);
    public void back();
}
