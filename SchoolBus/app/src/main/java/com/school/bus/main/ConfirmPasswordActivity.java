package com.school.bus.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.school.bus.R;
import com.school.bus.cn.listener.OnUpdateResposeListener;
import com.school.bus.cn.net.NetworkProtocol;

/**
 * Created by Administrator on 2017/4/1.
 */

public class ConfirmPasswordActivity extends Activity implements View.OnClickListener {
    TextView registerView;
    TextView titleView;
    ImageView addView;
    ImageView backView;
    EditText telephone;
    EditText password;
    Button confirm;
    String mTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getArgment();
        initView();
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.title_content);
        registerView = (TextView) findViewById(R.id.to_register);
        addView = (ImageView) findViewById(R.id.add);
        backView = (ImageView) findViewById(R.id.back);
        telephone = (EditText) findViewById(R.id.telephone);
        password = (EditText) findViewById(R.id.new_password);
        confirm = (Button) findViewById(R.id.confirm_button);
        telephone.setText(mTelephone);
        titleView.setText("确认密码");
        addView.setVisibility(View.GONE);
        backView.setVisibility(View.VISIBLE);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                toLogin("", "");
                break;
            case R.id.confirm_button:
                confirm();
                break;
            default:
                break;
        }
    }

    private void confirm() {
        final String password = this.password.getText().toString();
        NetworkProtocol.getUpdateResult(mTelephone, password);
        NetworkProtocol.setOnResposeListener(new OnUpdateResposeListener() {
            @Override
            public void onSuccess(String response) {
                boolean result = Boolean.valueOf(response);
                if (result) {
                    toLogin(mTelephone, password);
                } else {
                    Toast.makeText(ConfirmPasswordActivity.this, "您的手机号可能未注册", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(ConfirmPasswordActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getArgment() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTelephone = bundle.getString("telephone");
        }
    }

    private void toLogin(String telephone, String password) {
        Intent intent = new Intent(this, BeforeLoginActivity.class);

        if (!TextUtils.isEmpty(telephone) && !TextUtils.isEmpty(password)) {
            Bundle bundle = new Bundle();
            bundle.putString("telephone", telephone);
            bundle.putString("password", password);
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }
}
