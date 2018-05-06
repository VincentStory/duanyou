package com.duanyou.lavimao.proj_duanyou.activity;

import android.view.View;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreActivity extends BaseActivity {

    @Override
    public void setView() {
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.close_iv, R.id.duanzi_ll, R.id.duanyouxiu_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                finish();
                break;
            case R.id.duanzi_ll:
                if (UserInfo.getLoginState()) {
                    jumpTo(UploadDuanziActivity.class);
                } else {
                    jumpTo(LoginActivity.class);
                }
                finish();
                break;
            case R.id.duanyouxiu_ll:
                break;

        }
    }
}
