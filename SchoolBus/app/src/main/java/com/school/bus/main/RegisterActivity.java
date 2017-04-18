package com.school.bus.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.bus.R;
import com.school.bus.cn.presenter.RegisterPresenter;
import com.school.bus.cn.presenter.RegisterPresenterImpl;
import com.school.bus.cn.view.RegisterView;

/**
 * Created by zhangxingpei on 2017/3/31.
 */

public class RegisterActivity extends Activity implements View.OnClickListener,RegisterView{
    TextView registerView;
    TextView titleView;
    ImageView addView;
    ImageView backView;
    EditText telephone;
    EditText password;
    EditText name;
    CheckBox rememberPassword;
    TextView forgetPassword;
    Button login;
    RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_login);
        initView();
        registerPresenter=new RegisterPresenterImpl(this);
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.title_content);
        registerView = (TextView) findViewById(R.id.to_register);
        addView = (ImageView) findViewById(R.id.add);
        backView = (ImageView) findViewById(R.id.back);
        telephone = (EditText) findViewById(R.id.telephone);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        rememberPassword = (CheckBox) findViewById(R.id.remember_password);
        forgetPassword = (TextView) findViewById(R.id.forget_password);
        login = (Button) findViewById(R.id.login_button);
        titleView.setText("注册");
        login.setText("注册");

        name.setVisibility(View.VISIBLE);
        addView.setVisibility(View.GONE);
        rememberPassword.setVisibility(View.GONE);
        forgetPassword.setVisibility(View.GONE);
        backView.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                registerPresenter.toRegister(telephone.getText().toString(),password.getText().toString(),name.getText().toString());
                break;
            case R.id.back:
                registerPresenter.back();
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
    public void nameToast(String error) {
        name.setError(error);
    }
}
