package com.duanyou.lavimao.proj_duanyou.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.duanyou.lavimao.proj_duanyou.Event.UpdateFragemntEvent;
import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentUnreviewedResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.ImageUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        EventBus.getDefault().register(this);//订阅
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

    public void upData(GetContentUnreviewedResponse.DyContextsBean bean) {
        item = bean;
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//解除订阅
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upDateFragment(UpdateFragemntEvent event) {
        item = event.getmList().get(position);
        initView();
    }
}