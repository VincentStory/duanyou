package com.duanyou.lavimao.proj_duanyou.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.LoginActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.BaseReply;
import com.duanyou.lavimao.proj_duanyou.net.response.DyContextsBean;
import com.duanyou.lavimao.proj_duanyou.util.TimeConstants;
import com.duanyou.lavimao.proj_duanyou.util.TimeUtils;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.xiben.ebs.esbsdk.callback.ResultCallback;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luojialun on 2018/4/23.
 */

public class ReplyAdapter extends CommonAdapter<BaseReply> {


    public ReplyAdapter(Context context, int layoutId, List<BaseReply> datas) {
        super(context, layoutId, datas);
    }


    @Override
    protected void convert(ViewHolder helper, BaseReply item, int position) {
        GlideApp.with(mContext).load(item.getHeadPortraitUrl()).into((ImageView) helper.getView(R.id.head_riv));
        helper.setText(R.id.name_tv, item.getNickName());
        helper.setText(R.id.time_tv, TimeUtils.getCommentTime(item.getUploadDate(), TimeConstants.SEC));
        helper.setText(R.id.comment_tv, item.getContextText());
        helper.setText(R.id.zan_tv, item.getPraiseNum() + "");
    }

    /**
     * 用户操作
     *
     * @param type     1-段子 2-评论 3-回复 4-审核 5-用户
     * @param operator 1-点赞 2-点踩 3-举报 4-视频播放 5-转发 6-收藏/关注 7-取消收藏/取消关注 8-删除（只能删除自己的） 9-用户反馈（type为9）
     * @param remark
     */
    public void userOperation(String type, String operator, String remark, DyContextsBean item, final GetContentResult result) {
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
}
