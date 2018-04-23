package com.duanyou.lavimao.proj_duanyou.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.LoginActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UserCommentRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetCommentResponse;
import com.duanyou.lavimao.proj_duanyou.util.KeyboardUtils;
import com.duanyou.lavimao.proj_duanyou.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.xiben.ebs.esbsdk.callback.ResultCallback;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luojialun on 2018/4/21.
 */

public class CommentAdapter extends CommonAdapter<GetCommentResponse.CommentsNewBean> {

    public CommentAdapter(Context context, int layoutId, List<GetCommentResponse.CommentsNewBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(final com.zhy.adapter.recyclerview.base.ViewHolder helper, final GetCommentResponse.CommentsNewBean item, int position) {
        GlideApp.with(mContext)
                .load(item.getHeadPortraitUrl())
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .into((ImageView) helper.getView(R.id.head_iv));
        helper.setText(R.id.name_tv, item.getNickName());
        helper.setText(R.id.time_tv, item.getUploadDate());
        helper.setText(R.id.comment_tv, item.getContextText());
        helper.setText(R.id.zan_tv, item.getPraiseNum() + "");
        helper.setOnClickListener(R.id.zan_ll, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOperation("2", "1", "", item, new GetContentResult() {
                    @Override
                    public void success(String json) {
                        BaseResponse response = JSON.parseObject(json, BaseResponse.class);
                        if (null != response) {
                            if ("0".equals(response.getRespCode())) {
                                TextView tv = helper.getView(R.id.zan_tv);
                                try {
                                    int zanNum = Integer.parseInt(tv.getText().toString().trim());
                                    tv.setText((++zanNum) + "");
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
            }
        });

        helper.setOnClickListener(R.id.comment_ll, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.showSoftInput((Activity) mContext);
                if (null != replyClickListener) {
                    replyClickListener.replyClick(item);
                }
            }
        });

        if (item.getReply().size() > 0) {
            RecyclerView replyRv = helper.getView(R.id.reply_rv);
            replyRv.setLayoutManager(new LinearLayoutManager(mContext));
            ReplyAdapter replyAdapter = new ReplyAdapter(mContext, R.layout.item_reply, item.getReply());
            replyRv.setAdapter(replyAdapter);
            helper.setVisible(R.id.reply_view, true);
        } else {
            helper.setVisible(R.id.reply_view, false);
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
            NetUtil.getData(Api.userOperation, (Activity) mContext, request, new ResultCallback() {
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
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }
    }


    public interface ReplyClickListener {
        void replyClick(GetCommentResponse.CommentsNewBean item);
    }

    public ReplyClickListener replyClickListener;

    public ReplyClickListener getReplyClickListener() {
        return replyClickListener;
    }

    public void setReplyClickListener(ReplyClickListener replyClickListener) {
        this.replyClickListener = replyClickListener;
    }
}
