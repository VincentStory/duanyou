package com.duanyou.lavimao.proj_duanyou.activity;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FollowActivity extends BaseActivity {

    @BindView(R.id.follow_tv)
    TextView follow_tv;
    @BindView(R.id.fans_tv)
    TextView fans_tv;

    @Override
    public void setView() {
        setContentView(R.layout.activity_follow);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void startInvoke() {
        follow_tv.setTextColor(getResources().getColor(R.color.white));
        follow_tv.setSelected(true);
    }

    private void recoverDefaultState() {
        follow_tv.setBackgroundResource(R.drawable.rectangle_tab_left);
        fans_tv.setBackgroundResource(R.drawable.rectangle_tab_right);

        follow_tv.setSelected(false);
        fans_tv.setSelected(false);
        follow_tv.setTextColor(getResources().getColor(R.color.black2));
        fans_tv.setTextColor(getResources().getColor(R.color.black2));
    }


    @OnClick({R.id.follow_tv, R.id.fans_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.follow_tv:
                recoverDefaultState();
                follow_tv.setTextColor(getResources().getColor(R.color.white));
                follow_tv.setSelected(true);
                break;
            case R.id.fans_tv:
                recoverDefaultState();
                fans_tv.setTextColor(getResources().getColor(R.color.white));
                fans_tv.setSelected(true);
                break;
        }
    }
}
