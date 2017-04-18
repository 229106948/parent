package com.school.bus.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class ForgetPasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.initSDK(this,"1bc180a4f8699", "fc6bad0cb9b7bc601440b7ad99a93738");
        final RegisterPage registerPage=new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                super.afterEvent(event, result, data);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    final String telephone = (String) phoneMap.get("phone");
                    toConfirm(telephone);
                }
            }
        });
        registerPage.show(this);
    }
    private void toConfirm(String telephone){
        Intent intent=new Intent(this,ConfirmPasswordActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("telephone",telephone);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
