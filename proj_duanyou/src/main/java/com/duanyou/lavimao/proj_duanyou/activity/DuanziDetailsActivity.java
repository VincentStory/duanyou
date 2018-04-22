package com.duanyou.lavimao.proj_duanyou.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.CommentAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetCommentRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetCommentResponse;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.ImageUtils;
import com.duanyou.lavimao.proj_duanyou.util.KeyboardUtils;
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
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 段子详情
 */
public class DuanziDetailsActivity extends BaseActivity {

    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.iv_left)
    ImageView leftIv;
    @BindView(R.id.nav_right_iv)
    ImageView navRightIv;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.content_iv)
    ImageView contentIv;
    @BindView(R.id.head_iv)
    ImageView headIv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.zan_iv)
    ImageView zanIv;
    @BindView(R.id.zan_tv)
    TextView zanTv;
    @BindView(R.id.cai_iv)
    ImageView cai_tv;
    @BindView(R.id.cai_tv)
    TextView caiTv;
    @BindView(R.id.comment_iv)
    ImageView commentIv;
    @BindView(R.id.comment_tv)
    TextView commentTv;
    @BindView(R.id.comment_et)
    EditText commentEt;
    @BindView(R.id.comment_rv)
    RecyclerView commentRv;
    @BindView(R.id.empty_iv)
    ImageView emptyIv;

    private CommentAdapter commentAdapter;
    private List<GetCommentResponse.CommentsNewBean> mlist = new ArrayList<>();
    private GetContentResponse.DyContextsBean bean;  //详情类
    private boolean refreshTag = true;//下拉刷新  true  加载更多  false

    @Override
    public void setView() {
        setContentView(R.layout.activity_duanzi_details);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        leftIv.setImageResource(R.drawable.black_back);
        navRightIv.setImageResource(R.drawable.jubao);
    }

    @Override
    public void initData() {
        initParams();
        initViews();
        //getDuanziDetails();
        commentRv.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(this, R.layout.item_comment, mlist);
        commentRv.setAdapter(commentAdapter);
        loadComent(0);

    }

    private void initViews() {
        refreshLayout.setHeaderView(new SinaRefreshView(this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshTag = true;
                initParams();
                loadComent(0);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshTag = false;
                if (mlist.size() > 0) {
                    loadComent(mlist.get(mlist.size() - 1).getCommentID());
                } else {
                    refreshLayout.finishLoadmore();
                }
            }
        });
    }

    private void initParams() {
        bean = (GetContentResponse.DyContextsBean) getIntent().getSerializableExtra(Constants.CLICK_BEAN);
        if (null != bean) {
            switch (bean.getContextType()) {
                case "2":
                    contentTv.setVisibility(View.VISIBLE);
                    contentIv.setVisibility(View.GONE);
                    videoplayer.setVisibility(View.GONE);
                    contentTv.setText(bean.getContextText());
                    break;
                case "3":
                    contentTv.setVisibility(View.GONE);
                    contentIv.setVisibility(View.VISIBLE);
                    videoplayer.setVisibility(View.GONE);
                    ImageUtils.reCalculateImage(contentIv,
                            bean.getPixelWidth(),
                            bean.getPixelHeight(),
                            ScreenUtils.getScreenWidth());
                    GlideApp.with(this)
                            .load(bean.getContextUrl())
                            .placeholder(R.drawable.default_pic)
                            .into(contentIv);
                    break;
                case "4":
                    contentTv.setVisibility(View.GONE);
                    contentIv.setVisibility(View.GONE);
                    videoplayer.setVisibility(View.VISIBLE);
                    ImageUtils.reCalculateJzVideo(videoplayer,
                            bean.getPixelWidth(),
                            bean.getPixelHeight(),
                            ScreenUtils.getScreenWidth());
                    videoplayer.setUp(bean.getContextUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                    GlideApp.with(DuanziDetailsActivity.this)
                            .load(bean.getVideoDisplay())
                            .error(R.drawable.default_load)
                            .placeholder(R.drawable.default_load)
                            .into(videoplayer.thumbImageView);


                    break;
            }
            nameTv.setText(bean.getNickName());
            GlideApp.with(DuanziDetailsActivity.this)
                    .load(bean.getHeadPortraitUrl())
                    .placeholder(R.drawable.default_pic)
                    .into(headIv);
            zanTv.setText(bean.getPraiseNum() + "");
            caiTv.setText(bean.getTrampleNum() + "");
            commentTv.setText(bean.getCommentNum() + "");
        }

    }

   /* private void getDuanziDetails() {
        GetUserUploadContentRequest request = new GetUserUploadContentRequest();
        request.setDyID(SpUtil.getStringSp(SpUtil.dyID));
        request.setDeviceID(DeviceUtils.getAndroidID());
        request.setToken(SpUtil.getStringSp(SpUtil.TOKEN));
        request.setTargetDyID(targetDyID);
        request.setBeginContentID(beginContentID + "");
        NetUtil.getData(Api.getUserUploadContent, this, request, new ResultCallback() {
            @Override
            public void onResult(String jsonResult) {
                GetUserUploadContentResponse response = JSON.parseObject(jsonResult, GetUserUploadContentResponse.class);
                if (null != response && "0".equals(response.getRespCode())) {

                }
            }

            @Override
            public void onError(Exception ex) {

            }
        });
    }*/

    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.iv_left, R.id.nav_right_iv, R.id.zan_iv, R.id.cai_iv, R.id.comment_iv, R.id.comment_tv, R.id.send_btn, R.id.empty_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.nav_right_iv:
                jumpTo(ReportActivity.class);
                break;
            case R.id.zan_iv:
                userOperation("1", "1", "", new GetContentResult() {
                    @Override
                    public void success(String json) {
                        BaseResponse response = JSON.parseObject(json, BaseResponse.class);
                        if (null != response) {
                            if ("0".equals(response.getRespCode())) {
                                try {
                                    int zanNum = Integer.parseInt(zanTv.getText().toString().trim());
                                    zanTv.setText((++zanNum) + "");
                                } catch (Exception e) {
                                }
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
            case R.id.cai_iv:
                userOperation("1", "2", "", new GetContentResult() {
                    @Override
                    public void success(String json) {
                        BaseResponse response = JSON.parseObject(json, BaseResponse.class);
                        if (null != response) {
                            if ("0".equals(response.getRespCode())) {
                                try {
                                    int caiNum = Integer.parseInt(caiTv.getText().toString().trim());
                                    caiTv.setText((++caiNum) + "");
                                } catch (Exception e) {
                                }
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
            case R.id.comment_iv:
                KeyboardUtils.showSoftInput(this);
                break;
            case R.id.send_btn:
                String commentContent = commentEt.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {
                    userOperation("2", "2", commentContent, new GetContentResult() {
                        @Override
                        public void success(String json) {
                            BaseResponse response = JSON.parseObject(json, BaseResponse.class);
                            if (null != response) {
                                if ("0".equals(response.getRespCode())) {
                                    try {
                                        int commentNum = Integer.parseInt(commentTv.getText().toString().trim());
                                        commentTv.setText((++commentNum) + "");
                                        loadComent(0);
                                        commentEt.setText("");
                                        ToastUtils.showShort("发送成功");
                                    } catch (Exception e) {

                                    }
                                } else {
                                    ToastUtils.showShort(response.getRespMessage());
                                }
                            }
                        }

                        @Override
                        public void error(Exception ex) {

                        }
                    });
                } else {
                    ToastUtils.showShort("请输入内容");
                }
                break;
            case R.id.empty_iv:
                KeyboardUtils.showSoftInput(this);
                break;

            default:
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
    public void userOperation(String type, String operator, String remark, final GetContentResult result) {
        if (UserInfo.getLoginState()) {
            UserOperationRequest request = new UserOperationRequest();
            request.setDyID(UserInfo.getDyId());
            request.setDeviceID(UserInfo.getDeviceId());
            request.setToken(UserInfo.getToken());
            List<Integer> mList = new ArrayList<>();
            mList.add(bean.getDyContextID());
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
            jumpTo(LoginActivity.class);
        }
    }


    /**
     * 获取评论\更多评论
     *
     * @param benginCommentID 要获取的评论开始ID（一次获取最多20条评论，每条评论最多获取3条回复），倒序获取，若是第一次获取则传0，非0时不再发送热门评论
     */
    public void loadComent(int benginCommentID) {
        GetCommentRequest request = new GetCommentRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDyContextID(bean.getDyContextID() + "");
        request.setBenginCommentID(benginCommentID + "");
        NetUtil.getData(Api.getComment, this, request, new ResultCallback() {
            @Override
            public void onResult(String jsonResult) {
                GetCommentResponse response = JSON.parseObject(jsonResult, GetCommentResponse.class);
                if (null != response) {
                    if ("0".equals(response.getRespCode())) {
                        if (refreshTag) {
                            mlist.clear();
                            refreshLayout.finishRefreshing();
                        } else {
                            refreshLayout.finishLoadmore();
                        }
                        if (response.getCommentsNew().size() > 0) {
                            mlist.addAll(response.getCommentsNew());
                            commentAdapter.notifyDataSetChanged();
                        } else if (!refreshTag) {
                            ToastUtils.showShort(getResources().getString(R.string.no_more));
                        }
                        if (mlist.size() == 0) {
                            emptyIv.setVisibility(View.VISIBLE);
                        } else {
                            emptyIv.setVisibility(View.GONE);
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

}
