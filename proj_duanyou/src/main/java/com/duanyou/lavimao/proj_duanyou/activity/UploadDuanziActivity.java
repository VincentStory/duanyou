package com.duanyou.lavimao.proj_duanyou.activity;

import android.view.View;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadDuanziActivity extends BaseActivity {

    @BindView(R.id.nav_title)
    TextView titleTv;
    @BindView(R.id.right_tv)
    TextView rightTv;

    @Override
    public void setView() {
        setContentView(R.layout.activity_upload_duanzi);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initTitle();
    }

    private void initTitle() {
        titleTv.setText("段子");
        rightTv.setText("发布");
    }

    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.iv_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.right_tv:
                //发布操作
                break;
        }
    }

}
