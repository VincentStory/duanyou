package com.duanyou.lavimao.proj_duanyou.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.LoginActivity;
import com.duanyou.lavimao.proj_duanyou.activity.NearbyActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;
import com.duanyou.lavimao.proj_duanyou.net.response.NearbyPeopleResponse;
import com.duanyou.lavimao.proj_duanyou.util.CommonAdapter;
import com.duanyou.lavimao.proj_duanyou.util.ImageUtils;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * 标签fragment的适配器
 */
public class NearbyPeopleAdapter extends CommonAdapter<NearbyPeopleResponse.UserInfo> {


    public NearbyPeopleAdapter(Context context, List<NearbyPeopleResponse.UserInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, NearbyPeopleResponse.UserInfo item) {
        RoundedImageView headIv = helper.getView(R.id.head_iv);
        TextView nameTv = helper.getView(R.id.name_tv);
        TextView distanceTv = helper.getView(R.id.distance_tv);
        TextView signature_tv = helper.getView(R.id.signature_tv);


        Glide.with(mContext).load(item.getHeadPortraitUrl()).into(headIv);
        nameTv.setText(item.getNickName());
        int dis = (Integer.parseInt(item.getDistance())) / 1000;
        distanceTv.setText(dis + "km | " + item.getLatelyTime());
        signature_tv.setText(item.getSignature());

    }


}
