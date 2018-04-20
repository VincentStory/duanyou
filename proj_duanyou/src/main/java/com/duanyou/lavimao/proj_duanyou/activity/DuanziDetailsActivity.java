package com.duanyou.lavimao.proj_duanyou.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetUserUploadContentRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetUserUploadContentResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 段子详情
 */
public class DuanziDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView leftIv;
    @BindView(R.id.nav_right_iv)
    ImageView navRightIv;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;
    @BindView(R.id.head_iv)
    ImageView headIv;
    @BindView(R.id.name_tv)
    TextView nameTv;

    private String targetDyID;
    private String beginContentID;

    @Override
    public void setView() {
        setContentView(R.layout.activity_duanzi_details);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        leftIv.setImageResource(R.drawable.black_back);
        navRightIv.setImageResource(R.drawable.jubao);
        navRightIv.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
        initParams();
        getDuanziDetails();

        videoplayer.setUp("http://www.dyouclub.com/resources/mp4/3333333320221711.mp4", JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        Glide.with(this).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640").into(videoplayer.thumbImageView);

        Glide.with(this).load("http://resource.dyouclub.com/headPortrait/164d2d5d35236fb5!400x400_big.jpg").into(headIv);
        nameTv.setText("段友1314");
    }

    private void initParams() {
        targetDyID = getIntent().getStringExtra(Constants.targetDyID);
        beginContentID = getIntent().getStringExtra(Constants.beginContentID);
    }

    private void getDuanziDetails() {
        GetUserUploadContentRequest request = new GetUserUploadContentRequest();
        request.setTargetDyID("");
        request.setBeginContentID("");
        NetUtil.getData(Api.getUserUploadContent, this, request, new ResultCallback() {
            @Override
            public void onResult(String jsonResult) {
                GetUserUploadContentResponse response = JSON.parseObject(jsonResult, GetUserUploadContentResponse.class);
                if (null != response) {

                }
            }

            @Override
            public void onError(Exception ex) {

            }
        });
    }

    @Override
    public void startInvoke() {

    }


}
