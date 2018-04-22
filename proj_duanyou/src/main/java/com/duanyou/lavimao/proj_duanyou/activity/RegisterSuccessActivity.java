package com.duanyou.lavimao.proj_duanyou.activity;

import android.content.Intent;
import android.view.View;

import com.duanyou.lavimao.proj_duanyou.MyApplication;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2018/4/21.
 */

public class RegisterSuccessActivity extends BaseActivity {


    @Override
    public void setView() {
        setContentView(R.layout.activity_register_success);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void startInvoke() {

    }


    @OnClick(R.id.login_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                Intent intent = new Intent(RegisterSuccessActivity.this, LoginActivity.class);

                startActivity(intent);
                MyApplication.getInstance().finishActivity();
                break;
            default:
                break;
        }
    }
}
