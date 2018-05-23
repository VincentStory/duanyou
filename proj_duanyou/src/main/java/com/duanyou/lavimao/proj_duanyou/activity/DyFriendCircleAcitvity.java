package com.duanyou.lavimao.proj_duanyou.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TagFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.FriendCircleRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.ModifyHeadImageRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.SetUserInfoRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.UserInfoRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.UserInfoResponse;
import com.duanyou.lavimao.proj_duanyou.util.AddressPickTask;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.DeviceUtils;
import com.duanyou.lavimao.proj_duanyou.util.FileSizeUtil;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.BottomPopupWindow;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;
import com.xiben.ebs.esbsdk.util.LogUtil;

import net.bither.util.CompressTools;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.SinglePicker;

/**
 * Created by vincent on 2018/4/22.
 */

public class DyFriendCircleAcitvity extends BaseActivity {

    @BindView(R.id.nickname_tv)
    TextView nicknameTv;

    @BindView(R.id.headimage_iv)
    RoundedImageView headIv;
    @BindView(R.id.bg_iv)
    ImageView bgIv;
    @Override
    public void setView() {
        setContentView(R.layout.activity_dyfriend_circle);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initTitle();

        //必需继承FragmentActivity,嵌套fragment只需要这行代码
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, TagFragment.newInstance("6", "")).commitAllowingStateLoss();

    }

    @Override
    public void startInvoke() {


    }


    private void initTitle() {
        setTitle("段友圈");
        if (!UserInfo.getBgUrl().isEmpty())
            Glide.with(DyFriendCircleAcitvity.this).load(UserInfo.getBgUrl()).into(bgIv);
        if (!UserInfo.getHeadUrl().isEmpty())
            Glide.with(DyFriendCircleAcitvity.this).load(UserInfo.getHeadUrl()).into(headIv);

        nicknameTv.setText(UserInfo.getNickName());

    }


    @OnClick({R.id.iv_left, R.id.nav_right_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.nav_right_iv:

                break;


            default:
                break;
        }
    }


    /**
     * 获取段友圈
     */
    private void getDyCoterie(final Activity context, String beginContentId, final GetContentResult result) {
        FriendCircleRequest request = new FriendCircleRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(UserInfo.getDeviceId());
        request.setBeginContentID(beginContentId);
        request.setToken(UserInfo.getToken());

        NetUtil.getData(Api.getDyCoterie, context, request, new ResultCallback() {
            @Override
            public void onResult(final String jsonResult) {
                BaseResponse response = JSON.parseObject(jsonResult, BaseResponse.class);
                if (response.getRespCode().equals("0")) {

                    result.success(jsonResult);


                } else {
                    ToastUtils.showShort(response.getRespMessage());
                }

            }

            @Override
            public void onError(Exception ex) {

                result.error(ex);
            }
        });

    }


}
