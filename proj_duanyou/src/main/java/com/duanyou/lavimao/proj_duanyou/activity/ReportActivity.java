package com.duanyou.lavimao.proj_duanyou.activity;

import android.view.View;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 举报
 */
public class ReportActivity extends BaseActivity {

    @BindView(R.id.nav_title)
    TextView titleTv;

    @Override
    public void setView() {
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        titleTv.setText("举报");
    }

    @Override
    public void initData() {

    }

    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.iv_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            default:
                break;
        }
    }

}
