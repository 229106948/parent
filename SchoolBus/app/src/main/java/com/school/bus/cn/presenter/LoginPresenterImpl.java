package com.school.bus.cn.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.school.bus.cn.data.Parent;
import com.school.bus.cn.data.Student;
import com.school.bus.cn.listener.OnLoginResposeListener;
import com.school.bus.cn.listener.OnStudentResponseListener;
import com.school.bus.cn.net.NetworkProtocol;
import com.school.bus.cn.util.GsonUtil;
import com.school.bus.cn.view.LoginView;
import com.school.bus.main.ForgetPasswordActivity;
import com.school.bus.main.MainActivity;
import com.school.bus.main.RegisterActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxingpei on 2017/3/31.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private Parent parent;
    private LoginView mLoginView;
    private String telephone;
    private String password;
    String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<Student>students;
    public LoginPresenterImpl(LoginView loginView) {
        mLoginView=loginView;
        sharedPreferences=mLoginView.getActivity().getSharedPreferences("database", Activity.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @Override
    public void start() {
        getArguments();
    }

    @Override
    public void getLoginResult(final String telephone, final String password) {
        NetworkProtocol.getLoginResult(telephone,password);
        NetworkProtocol.setOnResposeListener(new OnLoginResposeListener() {
            @Override
            public void onSuccess(String response) {
                parent= GsonUtil.changeJsonToParent(response);
                if(parent!=null){
                    if(mLoginView.isRememberPassword()){
                        rememberPassword(parent.getTelephone(),parent.getPassword());
                    }else{
                        editor.clear();
                        editor.commit();
                    }
                    getStudentInfo();
                }else {
                    Toast.makeText(mLoginView.getActivity(),"登录失败，请检查你的账号密码",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFail(String message) {
                Toast.makeText(mLoginView.getActivity(),message,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getStudentInfo(){
        NetworkProtocol.getStudentByParent(parent.getId());
        NetworkProtocol.setOnResposeListener(new OnStudentResponseListener() {
            @Override
            public void onSuccess(String response) {
                Log.d("zxp",response);
                if(!TextUtils.isEmpty(response)){
                   students=GsonUtil.changeJsonToStudent(response);
                }else{
                    students=null;
                }
                toMainActivity();
            }
            @Override
            public void onFail(String message) {

            }
        });
    }
    private void toMainActivity(){
        Intent intent=new Intent(mLoginView.getActivity(), MainActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("parent",parent);
        intent.putExtras(bundle);
        intent.putExtra("student",(Serializable) students);
        Log.d("zxp",""+students.size());
        mLoginView.getActivity().startActivity(intent);
        mLoginView.getActivity().finish();
    }

    @Override
    public void getArguments() {
        Bundle bundle=mLoginView.getActivity().getIntent().getExtras();
        if(bundle!=null){
            telephone=bundle.getString("telephone");
            password=bundle.getString("password");
        }else {
            telephone=sharedPreferences.getString("telephone","");
            password=sharedPreferences.getString("password","");
        }
        if(!TextUtils.isEmpty(telephone)&&!TextUtils.isEmpty(password)) {
            mLoginView.passwordAutoComplete(telephone, password);
            mLoginView.rememberPassword();
        }
    }

    @Override
    public void toLogin(String telephone, String password) {
        if(TextUtils.isEmpty(telephone)){
            mLoginView.telephoneToast("手机号不能为空");
        }else{
            if(!telephone.matches(telRegex)){
                mLoginView.telephoneToast("手机号格式不正确");
            }else {
                if (TextUtils.isEmpty(password)) {
                    mLoginView.passwordToast("密码不能为空");
                }else {
                    getLoginResult( telephone, password);
                }
            }
        }
    }

    @Override
    public void toRegister() {
        Intent intent=new Intent(mLoginView.getActivity(), RegisterActivity.class);
        mLoginView.getActivity().startActivity(intent);
        mLoginView.getActivity().finish();
    }

    @Override
    public void rememberPassword(String telephone, final String password) {
        editor.putString("telephone",telephone);
        editor.putString("password",password);
        editor.commit();
    }

    @Override
    public void forgetPassword() {
        Intent intent=new Intent(mLoginView.getActivity(), ForgetPasswordActivity.class);
        mLoginView.getActivity().startActivity(intent);
    }
}
