package com.duanyou.lavimao.proj_duanyou.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;


import java.util.logging.LogRecord;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(activity,MainActivity.class));
            finish();
        }
    };

    @Override
    public void setView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initData() {
        handler.sendMessageDelayed(new Message(),3000);
    }

    @Override
    public void startInvoke() {

    }
}
