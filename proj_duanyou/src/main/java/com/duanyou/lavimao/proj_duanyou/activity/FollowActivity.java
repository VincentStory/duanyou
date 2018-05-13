package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.FansAdapter;
import com.duanyou.lavimao.proj_duanyou.adapter.NearbyPeopleAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.PeopleNearbyRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetFollowsBean;
import com.duanyou.lavimao.proj_duanyou.net.response.NearbyPeopleResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
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

public class FollowActivity extends BaseActivity {

    @BindView(R.id.follow_tv)
    TextView follow_tv;
    @BindView(R.id.fans_tv)
    TextView fans_tv;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.listview)
    ListView listView;
    private int type = 1;
    private int page = 1;
    List<GetFollowsBean.Fans> mList = new ArrayList<>();
    private FansAdapter mAdapter;

    @Override
    public void setView() {
        setContentView(R.layout.activity_follow);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initView();
        initList();
        getData(type, page);
    }


    private void initView() {
        refreshLayout.setHeaderView(new SinaRefreshView(FollowActivity.this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);

                page = 1;
                getData(type, page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);

                page++;
                getData(type, page);
            }
        });

    }


    /**
     * 初始化列表
     */
    private void initList() {
        if (mAdapter == null) {

            mAdapter = new FansAdapter(FollowActivity.this, mList, R.layout.adapter_fans);

            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(FollowActivity.this, PeopleInfoActivity.class);
                    intent.putExtra(Constants.targetDyID, mList.get(position).getDyID());

                    startActivity(intent);


                }
            });
        }

    }

    private void getData(int type, final int page) {
        if (type == 1) {
            getFollowList(FollowActivity.this, page, new GetContentResult() {
                @Override
                public void success(String json) {
                    GetFollowsBean response = JSON.parseObject(json, GetFollowsBean.class);

                    if (page==1) {
                        mList.clear();
                        refreshLayout.finishRefreshing();
                    } else {
                        refreshLayout.finishLoadmore();
                    }

                    if (response.getFans().size() > 0) {
                        mList.addAll(response.getFans());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShort(getResources().getString(R.string.no_more));
                    }
                }

                @Override
                public void error(Exception ex) {

                }
            });
        } else {
            getFansList(FollowActivity.this, page, new GetContentResult() {
                @Override
                public void success(String json) {
                    GetFollowsBean response = JSON.parseObject(json, GetFollowsBean.class);
                    if (page==1) {
                        mList.clear();
                        refreshLayout.finishRefreshing();
                    } else {
                        refreshLayout.finishLoadmore();
                    }

                    if (response.getFans().size() > 0) {
                        mList.addAll(response.getFans());
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


    }

    @Override
    public void startInvoke() {
        follow_tv.setTextColor(getResources().getColor(R.color.white));
        follow_tv.setSelected(true);
    }

    private void recoverDefaultState() {


        follow_tv.setSelected(false);
        fans_tv.setSelected(false);
        follow_tv.setTextColor(getResources().getColor(R.color.black2));
        fans_tv.setTextColor(getResources().getColor(R.color.black2));
    }


    @OnClick({R.id.iv_left,R.id.follow_tv, R.id.fans_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.follow_tv:
                recoverDefaultState();
                follow_tv.setTextColor(getResources().getColor(R.color.white));
                follow_tv.setSelected(true);
                type = 1;
                page=1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                getData(type, page);

                break;
            case R.id.fans_tv:
                recoverDefaultState();
                fans_tv.setTextColor(getResources().getColor(R.color.white));
                fans_tv.setSelected(true);
                type = 2;
                page=1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                getData(type, page);
                break;
        }
    }


    /**
     * 1.4.18
     */
    private void getFollowList(final Activity context, int page, final GetContentResult result) {
        PeopleNearbyRequest request = new PeopleNearbyRequest();
        request.setDyID(UserInfo.getDyId());
        request.setPage(page);


        NetUtil.getData(Api.getFollowList, context, request, new ResultCallback() {
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

    /**
     * 1.4.18
     */
    private void getFansList(final Activity context, int page, final GetContentResult result) {
        PeopleNearbyRequest request = new PeopleNearbyRequest();
        request.setDyID(UserInfo.getDyId());
        request.setPage(page);


        NetUtil.getData(Api.getFansList, context, request, new ResultCallback() {
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
