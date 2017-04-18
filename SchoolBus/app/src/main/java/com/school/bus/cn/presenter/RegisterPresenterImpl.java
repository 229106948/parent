package com.school.bus.cn.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.school.bus.cn.data.Parent;
import com.school.bus.cn.listener.OnRegisterResposeListener;
import com.school.bus.cn.net.NetworkProtocol;
import com.school.bus.cn.util.GsonUtil;
import com.school.bus.cn.view.RegisterView;
import com.school.bus.main.BeforeLoginActivity;


/**
 * Created by Administrator on 2017/3/31.
 */

public class RegisterPresenterImpl implements RegisterPresenter {
    RegisterView mRegisterView;
    String telRegex = "[1][358]\\d{9}";
    Parent parent;
    boolean result = false;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.mRegisterView = registerView;
    }

    @Override
    public void getRegisterResult(String telephone, String password, String name) {

        NetworkProtocol.getRegisterResult(GsonUtil.changeParentToJson(parent));
        NetworkProtocol.setOnResposeListener(new OnRegisterResposeListener() {
            @Override
            public void onSuccess(String response) {
                result = Boolean.valueOf(response);
                if (result) {
                    toLogin();
                } else {
                    Toast.makeText(mRegisterView.getActivity(), "注册失败，您的手机号可能已被注册", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFail(String message) {
                Toast.makeText(mRegisterView.getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void toLogin() {
        Intent intent = new Intent(mRegisterView.getActivity(), BeforeLoginActivity.class);
        if (result) {
            Bundle bundle = new Bundle();
            bundle.putString("telephone", parent.getTelephone());
            bundle.putString("password", parent.getPassword());
            intent.putExtras(bundle);
        }
        mRegisterView.getActivity().startActivity(intent);
        mRegisterView.getActivity().finish();
    }

    @Override
    public void toRegister(String telephone, String password, String name) {
        if (TextUtils.isEmpty(telephone)) {
            mRegisterView.telephoneToast("手机号不能为空");
        } else {
            if (!telephone.matches(telRegex)) {
                mRegisterView.telephoneToast("手机号格式不正确");
            } else {
                if (TextUtils.isEmpty(password)) {
                    mRegisterView.passwordToast("密码不能为空");
                } else {
                    parent = new Parent(password,0,name,telephone);
                    getRegisterResult(telephone, password, name);
                }
            }
        }
    }

    @Override
    public void back() {
        toLogin();
    }
}
