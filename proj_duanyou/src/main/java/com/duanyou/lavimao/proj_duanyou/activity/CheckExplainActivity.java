package com.duanyou.lavimao.proj_duanyou.activity;

import android.view.View;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckExplainActivity extends BaseActivity {

    @BindView(R.id.nav_title)
    TextView titleTv;

    @Override
    public void setView() {
        setContentView(R.layout.activity_check_explain);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initTitle();
    }

    private void initTitle() {
        titleTv.setText("审核说明");
    }

    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.nav_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_left:
                finish();
                break;
        }
    }

}
