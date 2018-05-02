package com.duanyou.lavimao.proj_duanyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.MyApplication;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.util.Contents;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2018/4/21.
 */

public class RegisterSuccessActivity extends BaseActivity {

    @BindView(R.id.content_tv)
    TextView contentTv;

    private String pageType;

    @Override
    public void setView() {
        setContentView(R.layout.activity_register_success);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        MyApplication.getInstance().addActivity(this);
        pageType = getIntent().getStringExtra("pageType");
        if (pageType.equals(Contents.PWD_CODE_TYPE)) {
            contentTv.setText("密码修改成功！快来一起嗨皮吧");

        }
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
