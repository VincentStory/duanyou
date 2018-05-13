package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.FansAdapter;
import com.duanyou.lavimao.proj_duanyou.adapter.InteractionAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TagFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.FriendCircleRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.PeopleNearbyRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetFollowsBean;
import com.duanyou.lavimao.proj_duanyou.net.response.GetInteraction;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HuDongActivity extends BaseActivity {
    private int page = 1;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.listview)
    ListView listView;

    List<GetInteraction.Interactions> mList = new ArrayList<>();
    private InteractionAdapter mAdapter;

    @Override
    public void setView() {
        setContentView(R.layout.activity_hudong);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        setTitle("互动");
        initView();
        initList();
        getData();
    }


    private void initView() {
        refreshLayout.setHeaderView(new SinaRefreshView(HuDongActivity.this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);

                page = 1;
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);

                page++;
                getData();
            }
        });

    }


    /**
     * 初始化列表
     */
    private void initList() {
        if (mAdapter == null) {

            mAdapter = new InteractionAdapter(HuDongActivity.this, mList, R.layout.adapter_interaction);

            listView.setAdapter(mAdapter);

        }

    }


    @Override
    public void startInvoke() {

    }

    private void getData() {
        getInteraction(HuDongActivity.this, page, new GetContentResult() {
            @Override
            public void success(String json) {
                GetInteraction response = JSON.parseObject(json, GetInteraction.class);

                if (page == 1) {
                    mList.clear();
                    refreshLayout.finishRefreshing();
                } else {
                    refreshLayout.finishLoadmore();
                }

                if (response.getInteractions().size() > 0) {
                    mList.addAll(response.getInteractions());
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(getResources().getString(R.string.no_more));
                }
            }

            @Override
            public void error(Exception ex) {

            }
        });
    }


    @OnClick({R.id.iv_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;


        }
    }

    /**
     * 获取互动
     */
    protected void getInteraction(final Activity context, int page, final GetContentResult result) {
        FriendCircleRequest request = new FriendCircleRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(UserInfo.getDeviceId());
        request.setToken(UserInfo.getToken());
        request.setPage(page);

        NetUtil.getData(Api.getInteraction, context, request, new ResultCallback() {
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
