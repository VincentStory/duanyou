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
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.DyContextsBean;
import com.duanyou.lavimao.proj_duanyou.util.CommonAdapter;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
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
public class MainContentAdapter extends CommonAdapter<DyContextsBean> {
    private OnItemClickListener listener;
    private String mType;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MainContentAdapter(Context context, List<DyContextsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    public void setType(String type) {
        mType = type;
    }

    @Override
    public void convert(final ViewHolder helper, final DyContextsBean item) {

        RoundedImageView iv_avatar = helper.getView(R.id.iv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_duanzi_status = helper.getView(R.id.tv_duanzi_status);
        TextView tv_create_time = helper.getView(R.id.tv_create_time);
        TextView contentTv = helper.getView(R.id.tv_content);
        final TextView checked_tv = helper.getView(R.id.checked_tv);
        ImageView contentIv = helper.getView(R.id.content_iv);
        ImageView iv_sex = helper.getView(R.id.iv_sex);
        final TextView zanTv = helper.getView(R.id.zan_tv);
        final TextView caiTv = helper.getView(R.id.tv_unliked);
        final TextView commentTv = helper.getView(R.id.tv_comments);
        JZVideoPlayerStandard jz = helper.getView(R.id.jz_video);
        checked_tv.setVisibility(item.isEdit() ? View.VISIBLE : View.GONE);
        checked_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked_tv.setSelected(!checked_tv.isSelected());
                item.setChecked(checked_tv.isSelected());
            }
        });

        if (mType != null)
            if (mType.equals(Contents.TOUGAO_TYPE)) {
                tv_duanzi_status.setVisibility(View.VISIBLE);
                tv_create_time.setVisibility(View.VISIBLE);
                if (item.getAdopt().equals("0")) {
                    tv_duanzi_status.setText("审核中，请耐心等待");
                }else  if (item.getAdopt().equals("1")) {
                    tv_duanzi_status.setText("恭喜你，已通过");
                }else  if (item.getAdopt().equals("2")) {
                    tv_duanzi_status.setText("未通过，可能哪里有问题需要改改");
                }
                tv_create_time.setText(item.getUploadDate());
            }


        String type = item.getContextType();
        switch (type) {
            case "2"://段子
                contentIv.setVisibility(View.GONE);
                jz.setVisibility(View.GONE);
                break;
            case "3"://图片
                contentIv.setVisibility(View.VISIBLE);
                jz.setVisibility(View.GONE);

                ImageUtils.reCalculateImage(helper.getView(R.id.content_iv),
                        item.getPixelWidth(),
                        item.getPixelHeight(),
                        ScreenUtils.getScreenWidth());

                GlideApp.with(mContext)
                        .load(item.getContextUrl())
                        .error(R.drawable.default_load_long)
                        .placeholder(R.drawable.default_load_long)
                        .into(contentIv);
                break;
            case "4"://视频
                contentIv.setVisibility(View.GONE);
                jz.setVisibility(View.VISIBLE);

                ImageUtils.reCalculateJzVideo(jz,
                        item.getPixelWidth(),
                        item.getPixelHeight(),
                        ScreenUtils.getScreenWidth());

                jz.setUp(item.getContextUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                GlideApp.with(mContext)
                        .load(item.getVideoDisplay())
                        .into(jz.thumbImageView);
                /*.error(R.drawable.default_load)
                        .placeholder(R.drawable.default_load)
                        .centerCrop()*/
                break;
        }

        tv_name.setText(item.getNickName());
        GlideApp.with(mContext)
                .load(item.getHeadPortraitUrl())
                .error(R.drawable.default_pic)
                .placeholder(R.drawable.default_pic)
                .into(iv_avatar);
        if (TextUtils.isEmpty(item.getContextText())) {
            contentTv.setVisibility(View.GONE);
        } else {
            contentTv.setVisibility(View.VISIBLE);
            contentTv.setText(item.getContextText());
        }
        //0-女 1-男
        if (item.getSex().equals("0")) {
            Glide.with(mContext).load(R.drawable.icon_female).into(iv_sex);
        } else {
            Glide.with(mContext).load(R.drawable.icon_male).into(iv_sex);
        }
        zanTv.setText(item.getPraiseNum() + "");
        caiTv.setText(item.getTrampleNum() + "");
        commentTv.setText(item.getCommentNum() + "");
        zanTv.setSelected("1".equals(item.getIsLike()));
        caiTv.setSelected("2".equals(item.getIsLike()));
//        if ("1".equals(item.getIsLike())) {
//            helper.setImageResource(R.id.zan_iv, R.drawable.good1);
//        } else if ("2".equals(item.getIsLike())) {
//            helper.setImageResource(R.id.cai_iv, R.drawable.fuck1);
//        } else {
//            helper.setImageResource(R.id.zan_iv, R.drawable.good2);
//            helper.setImageResource(R.id.cai_iv, R.drawable.fuck2);
//        }
        //赞
        helper.setOnClickListener(R.id.zan_ll, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOperation("1", "1", "", item, new GetContentResult() {
                    @Override
                    public void success(String json) {
                        BaseResponse response = JSON.parseObject(json, BaseResponse.class);
                        if (null != response) {
                            if ("0".equals(response.getRespCode())) {
                                try {
                                    int zanNum = Integer.parseInt(zanTv.getText().toString().trim());
                                    zanTv.setText((++zanNum) + "");
                                    helper.getView(R.id.zan_iv).setSelected(true);
                                    item.setIsLike("1");
//                                    helper.setImageResource(R.id.zan_iv, R.drawable.good1);
                                    ToastUtils.showShort("点赞成功");
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
        //踩
        helper.setOnClickListener(R.id.cai_ll, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOperation("1", "2", "", item, new GetContentResult() {
                    @Override
                    public void success(String json) {
                        BaseResponse response = JSON.parseObject(json, BaseResponse.class);
                        if (null != response) {
                            if ("0".equals(response.getRespCode())) {
                                try {
                                    int caiNum = Integer.parseInt(caiTv.getText().toString().trim());
                                    caiTv.setText((++caiNum) + "");
                                    helper.getView(R.id.cai_iv).setSelected(true);
                                    item.setIsLike("2");
//                                    helper.setImageResource(R.id.cai_iv, R.drawable.fuck1);
                                    ToastUtils.showShort("点踩成功");
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
        //评论
        helper.setOnClickListener(R.id.comment_ll, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.comment(item);
            }
        });
        helper.setOnClickListener(R.id.rl_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
        //分享
        helper.setOnClickListener(R.id.share_iv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.share(item);
            }
        });
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

    public interface OnItemClickListener {

        void comment(DyContextsBean bean);

        void onItemClick(DyContextsBean bean);

        void share(DyContextsBean bean);
    }

}
