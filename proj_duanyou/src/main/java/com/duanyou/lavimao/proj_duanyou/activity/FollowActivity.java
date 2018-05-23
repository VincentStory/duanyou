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
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
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

public class FollowActivity extends BaseActivity implements FansAdapter.OnItemClickLintener {

    @BindView(R.id.follow_tv)
    TextView follow_tv;
    @BindView(R.id.fans_tv)
    TextView fans_tv;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.listview)
    ListView listView;
    private int type = 1;//1是关注，2是粉丝
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
            mAdapter.setLintener(this);
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
        mAdapter.setType(type);
        if (type == 1) {
            getFollowList(FollowActivity.this, page, new GetContentResult() {
                @Override
                public void success(String json) {
                    GetFollowsBean response = JSON.parseObject(json, GetFollowsBean.class);

                    if (page == 1) {
                        mList.clear();
                        refreshLayout.finishRefreshing();
                    } else {
                        refreshLayout.finishLoadmore();
                    }

                    if (response.getFans().size() > 0) {
                        mList.addAll(response.getFans());

                    } else {
                        ToastUtils.showShort(getResources().getString(R.string.no_more));
                    }
                    mAdapter.notifyDataSetChanged();
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
                    if (page == 1) {
                        mList.clear();
                        refreshLayout.finishRefreshing();
                    } else {
                        refreshLayout.finishLoadmore();
                    }

                    if (response.getFans().size() > 0) {
                        mList.addAll(response.getFans());
                    } else {
                        ToastUtils.showShort(getResources().getString(R.string.no_more));
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void error(Exception ex) {

                }
            });
        }


    }

    /**
     * 用户操作
     *
     * @param type     1-段子 2-评论 3-回复 4-审核 5-用户
     * @param operator 1-点赞 2-点踩 3-举报 4-视频播放 5-转发 6-收藏/关注 7-取消收藏/取消关注 8-删除（只能删除自己的） 9-用户反馈（type为9）
     */
    public void userOperation(String type, String operator, int dyId, final GetContentResult result) {
        if (UserInfo.getLoginState()) {
            UserOperationRequest request = new UserOperationRequest();
            request.setDyID(UserInfo.getDyId());
            request.setDeviceID(UserInfo.getDeviceId());
            request.setToken(UserInfo.getToken());
            List<Integer> mList = new ArrayList<>();
            mList.add(dyId);
            request.setDyDataID(mList);
            request.setType(type);
            request.setOperator(operator);
//            request.setRemark(remark);
            NetUtil.getData(Api.userOperation, this, request, new ResultCallback() {
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
            jumpTo(LoginActivity.class);
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


    @OnClick({R.id.iv_left, R.id.follow_tv, R.id.fans_tv})
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
                page = 1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                getData(type, page);

                break;
            case R.id.fans_tv:
                recoverDefaultState();
                fans_tv.setTextColor(getResources().getColor(R.color.white));
                fans_tv.setSelected(true);
                type = 2;
                page = 1;
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


    @Override
    public void followClick(GetFollowsBean.Fans item) {
        if (type == 1) {
            userOperation("5", "7", Integer.parseInt(item.getDyID()), new GetContentResult() {
                @Override
                public void success(String json) {
                    getData(type, page);
                }

                @Override
                public void error(Exception ex) {
                    ToastUtils.showShort(ex.toString());

                }
            });
        } else {
            userOperation("5", "6", Integer.parseInt(item.getDyID()), new GetContentResult() {
                @Override
                public void success(String json) {
                    getData(type, page);
                }

                @Override
                public void error(Exception ex) {
                    ToastUtils.showShort(ex.toString());

                }
            });
        }
    }
}
