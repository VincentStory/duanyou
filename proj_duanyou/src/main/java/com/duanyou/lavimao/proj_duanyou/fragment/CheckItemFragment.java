package com.duanyou.lavimao.proj_duanyou.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.Event.UpdateFragemntEvent;
import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.LoginActivity;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentUnreviewedResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.ImageUtils;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 审核的子item 的 fragment
 */
public class CheckItemFragment extends BaseFragment {

    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.jz_video)
    JZVideoPlayerStandard videoplayer;
    @BindView(R.id.content_iv)
    ImageView contentIv;

    public GetContentUnreviewedResponse.DyContextsBean item;
    public int position;

    public static CheckItemFragment newInstance(GetContentUnreviewedResponse.DyContextsBean item, int position) {
        Bundle args = new Bundle();
        args.putInt(Constants.POSITION, position);
        args.putSerializable(Constants.DyContextsBean, item);
        CheckItemFragment fragment = new CheckItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_item, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void initWidget(View view) {

    }

    @Override
    public void startInvoke(View view) {
        initParams();
        initView();
    }

    private void initParams() {
        item = (GetContentUnreviewedResponse.DyContextsBean) getArguments().getSerializable(Constants.DyContextsBean);
        position = getArguments().getInt(Constants.POSITION);
    }

    private void initView() {
        String type = item.getContextType();
        if ("2".equals(type)) {
            contentIv.setVisibility(View.GONE);
            videoplayer.setVisibility(View.GONE);
        } else if ("3".equals(type)) {
            contentIv.setVisibility(View.VISIBLE);
            videoplayer.setVisibility(View.GONE);

            ImageUtils.reCalculateImage(contentIv,
                    item.getPixelWidth(),
                    item.getPixelHeight(),
                    ScreenUtils.getScreenWidth());
            GlideApp.with(this)
                    .load(item.getContextUrl())
                    .placeholder(R.drawable.default_pic)
                    .into(contentIv);
        } else if ("4".equals(type)) {
            contentIv.setVisibility(View.GONE);
            videoplayer.setVisibility(View.VISIBLE);

            ImageUtils.reCalculateJzVideo(videoplayer,
                    item.getPixelWidth(),
                    item.getPixelHeight(),
                    ScreenUtils.getScreenWidth());
            videoplayer.setUp(item.getContextUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            GlideApp.with(getActivity())
                    .load(item.getVideoDisplay())
                    .error(R.drawable.default_load)
                    .placeholder(R.drawable.default_load)
                    .into(videoplayer.thumbImageView);
        }

        if (TextUtils.isEmpty(item.getContextText())) {
            contentTv.setVisibility(View.GONE);
        } else {
            contentTv.setText(item.getContextText());
            contentTv.setVisibility(View.VISIBLE);
        }
    }

 /*   public void upData(GetContentUnreviewedResponse.DyContextsBean bean) {
        item = bean;
        initView();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//解除订阅
    }

    @OnClick({R.id.report_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.report_ll:
                userOperation("4", "3", "", new GetContentResult() {
                    @Override
                    public void success(String json) {
                        BaseResponse response = JSON.parseObject(json, BaseResponse.class);
                        if (null != response) {
                            if ("0".equals(response.getRespCode())) {
                                ToastUtils.showShort("举报成功");
                            } else {
                                ToastUtils.showShort(response.getRespMessage());
                            }
                        }
                    }

                    @Override
                    public void error(Exception ex) {

                    }
                });
                break;
        }
    }

    /**
     * 刷新item
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upDateFragment(UpdateFragemntEvent event) {
        item = event.getmList().get(position);
        initView();
    }

    /**
     * 用户操作
     *
     * @param type     1-段子 2-评论 3-回复 4-审核 5-用户
     * @param operator 1-点赞 2-点踩 3-举报 4-视频播放 5-转发 6-收藏/关注 7-取消收藏/取消关注 8-删除（只能删除自己的） 9-用户反馈（type为9）
     * @param remark
     */
    public void userOperation(String type, String operator, String remark, final GetContentResult result) {
        if (UserInfo.getLoginState()) {
            UserOperationRequest request = new UserOperationRequest();
            request.setDyID(UserInfo.getDyId());
            request.setDeviceID(UserInfo.getDeviceId());
            request.setToken(UserInfo.getToken());
            List<Integer> mList = new ArrayList<>();
            mList.add(item.getDyContextID());
            request.setDyDataID(mList);
            request.setType(type);
            request.setOperator(operator);
            request.setRemark(remark);
            NetUtil.getData(Api.userOperation, getActivity(), request, new ResultCallback() {
                @Override
                public void onResult(String jsonResult) {
                    result.success(jsonResult);
                }

                @Override
                public void onError(Exception ex) {
                    result.error(ex);
                }
            });
        } else {
            gotoActivity(LoginActivity.class);
        }
    }

}
