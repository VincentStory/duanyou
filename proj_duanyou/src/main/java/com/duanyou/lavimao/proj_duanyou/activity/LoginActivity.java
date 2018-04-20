package com.duanyou.lavimao.proj_duanyou.activity;

import android.content.Intent;
import android.view.View;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2018/4/20.
 */

public class LoginActivity extends BaseActivity {


    @Override
    public void setView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void startInvoke() {

    }



    @OnClick(R.id.no_account_tv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no_account_tv:
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
