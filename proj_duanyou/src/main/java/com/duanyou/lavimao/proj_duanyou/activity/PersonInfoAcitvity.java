package com.duanyou.lavimao.proj_duanyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.response.LoginResponse;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2018/4/22.
 */

public class PersonInfoAcitvity extends BaseActivity {


    @BindView(R.id.iv_left)
    ImageView leftIv;
    @BindView(R.id.nav_title)
    TextView navTitle;


    @Override
    public void setView() {
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initTitle();
    }

    @Override
    public void startInvoke() {

    }


    private void initTitle() {
        leftIv.setImageResource(R.drawable.black_back);
        navTitle.setText("个人资料");
    }


    @OnClick({R.id.iv_left, R.id.headimage_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.headimage_rl:



                break;


            default:
                break;
        }
    }
}
