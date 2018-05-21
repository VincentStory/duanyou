package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.ReplyAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetReplyRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.BaseReply;
import com.duanyou.lavimao.proj_duanyou.net.response.DyContextsBean;
import com.duanyou.lavimao.proj_duanyou.net.response.GetCommentResponse;
import com.duanyou.lavimao.proj_duanyou.net.response.GetReplyResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.TimeConstants;
import com.duanyou.lavimao.proj_duanyou.util.TimeUtils;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReplyActivity extends BaseActivity {

    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.nav_title)
    TextView titleTv;
    @BindView(R.id.head_riv)
    RoundedImageView headRiv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.comment_tv)
    TextView commentTv;
    @BindView(R.id.reply_rv)
    RecyclerView replyRv;
    @BindView(R.id.zan_tv)
    TextView zanTv;
    @BindView(R.id.zan_iv)
    ImageView zanIv;


    private GetCommentResponse.CommentsNewBean bean;
    private ReplyAdapter adapter;
    private List<BaseReply> replyList = new ArrayList<>();
    private boolean refreshTag = true;  //下拉刷新  true   加载更多  false

    @Override
    public void setView() {
        setContentView(R.layout.activity_reply);
        ButterKnife.bind(this);
        initTitle();
        initParams();
        initView();
    }

    private void initView() {
        replyRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReplyAdapter(this, R.layout.item_reply2, replyList);
        replyRv.setAdapter(adapter);

        refreshLayout.setHeaderView(new SinaRefreshView(this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshTag = true;
                getReply(0);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshTag = false;
                if (replyList.size() > 0) {
                    getReply(replyList.get(replyList.size() - 1).getReplyID());
                }

            }
        });

    }

    private void initParams() {
        bean = (GetCommentResponse.CommentsNewBean) getIntent().getSerializableExtra(Constants.CommentsNewBean);
        GlideApp.with(this).load(bean.getHeadPortraitUrl()).into(headRiv);
        nameTv.setText(bean.getNickName());
        try {
            timeTv.setText(TimeUtils.getCommentTime(bean.getUploadDate(), TimeConstants.SEC));
        } catch (Exception e) {
        }
        commentTv.setText(bean.getContextText());
        zanTv.setText(bean.getPraiseNum() + "");
    }

    private void initTitle() {
        titleTv.setText("回复");
    }

    @Override
    public void initData() {
        getReply(0);
    }

    private void getReply(int benginReplyID) {
        GetReplyRequest request = new GetReplyRequest();
        request.setDyCommentID(bean.getCommentID());
        request.setBenginReplyID(benginReplyID);
        NetUtil.getData(Api.getReply, this, request, new ResultCallback() {
            @Override
            public void onResult(String jsonResult) {
                if (refreshTag) {
                    refreshLayout.finishRefreshing();
                } else {
                    refreshLayout.finishLoadmore();
                }
                GetReplyResponse response = JSON.parseObject(jsonResult, GetReplyResponse.class);
                if (null != response) {
                    if ("0".equals(response.getRespCode())) {
                        if (refreshTag) {
                            replyList.clear();
                        }
                        replyList.addAll(response.getReply());
                        adapter.notifyDataSetChanged();
                        if (response.getReply().size() <= 0) {
                            ToastUtils.showShort(getResources().getString(R.string.no_more));
                        }

                    } else {
                        ToastUtils.showShort(response.getRespMessage());
                    }
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

    @OnClick({R.id.iv_left, R.id.zan_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.zan_ll:
                userOperation("3", "1", "", bean, new GetContentResult() {
                    @Override
                    public void success(String json) {
                        int zan = Integer.parseInt(zanTv.getText().toString().trim()) + 1;
                        zanTv.setText(zan + "");
                        ToastUtils.showShort("已zan");
                        zanIv.setImageResource(R.drawable.good1);
                    }

                    @Override
                    public void error(Exception ex) {

                    }
                });
                break;
        }
    }

    /**
     * 用户操作
     *
     * @param type     1-段子 2-评论 3-回复 4-审核 5-用户
     * @param operator 1-点赞 2-点踩 3-举报 4-视频播放 5-转发 6-收藏/关注 7-取消收藏/取消关注 8-删除（只能删除自己的） 9-用户反馈（type为9）
     * @param remark
     */
    public void userOperation(String type, String operator, String remark, GetCommentResponse.CommentsNewBean item, final GetContentResult result) {
        if (UserInfo.getLoginState()) {
            UserOperationRequest request = new UserOperationRequest();
            request.setDyID(UserInfo.getDyId());
            request.setDeviceID(UserInfo.getDeviceId());
            request.setToken(UserInfo.getToken());
            List<Integer> mList = new ArrayList<>();
            mList.add(item.getCommentID());
            request.setDyDataID(mList);
            request.setType(type);
            request.setOperator(operator);
            request.setRemark(remark);
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
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
        }
    }
}
