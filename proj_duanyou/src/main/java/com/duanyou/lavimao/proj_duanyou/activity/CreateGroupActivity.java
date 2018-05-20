package com.duanyou.lavimao.proj_duanyou.activity;

import android.view.View;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGroupActivity extends BaseActivity {

    @BindView(R.id.nav_title)
    TextView titleTv;
    @BindView(R.id.right_tv)
    TextView rightTv;

    @Override
    public void setView() {
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        titleTv.setText("创建群");
        rightTv.setText("提交");
    }

    @Override
    public void initData() {

    }

    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.iv_left, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.right_tv:
                break;
        }
    }

}
