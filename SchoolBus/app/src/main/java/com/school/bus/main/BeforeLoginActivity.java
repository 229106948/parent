package com.school.bus.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.school.bus.R;
import com.school.bus.cn.presenter.LoginPresenter;
import com.school.bus.cn.presenter.LoginPresenterImpl;
import com.school.bus.cn.view.LoginView;

/**
 * Created by zhangxingpei on 2017/3/30.
 */

public class BeforeLoginActivity extends Activity implements View.OnClickListener, LoginView {
    TextView registerView;
    TextView titleView;
    ImageView addView;
    ImageView backView;
    EditText telephone;
    EditText password;
    CheckBox rememberPassword;
    TextView forgetPassword;
    Button login;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_login);
        initView();
        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.start();
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.title_content);
        registerView = (TextView) findViewById(R.id.to_register);
        addView = (ImageView) findViewById(R.id.add);
        backView = (ImageView) findViewById(R.id.back);
        telephone = (EditText) findViewById(R.id.telephone);
        password = (EditText) findViewById(R.id.password);
        rememberPassword = (CheckBox) findViewById(R.id.remember_password);
        forgetPassword = (TextView) findViewById(R.id.forget_password);
        login = (Button) findViewById(R.id.login_button);
        titleView.setText("登录");
        registerView.setVisibility(View.VISIBLE);
        addView.setVisibility(View.GONE);
        backView.setVisibility(View.GONE);
        registerView.setOnClickListener(this);
        rememberPassword.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public boolean isRememberPassword() {
        return rememberPassword.isChecked();
    }
    @Override
    public void rememberPassword() {
        rememberPassword.setChecked(true);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_register:
                loginPresenter.toRegister();
                break;
            case R.id.forget_password:
                loginPresenter.forgetPassword();
                break;
            case R.id.login_button:
                loginPresenter.toLogin(telephone.getText().toString(),password.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void telephoneToast(String error) {
        telephone.setError(error);
    }

    @Override
    public void passwordToast(String error) {
        password.setError(error);
    }

    @Override
    public void passwordAutoComplete(String telephone, String password) {
        if (!TextUtils.isEmpty(telephone)) {
            this.telephone.setText(telephone);
        }
        if (!TextUtils.isEmpty(password)) {
            this.password.setText(password);
        }
    }
}
