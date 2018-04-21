package com.duanyou.lavimao.proj_duanyou.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;
import com.duanyou.lavimao.proj_duanyou.util.CommonAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * 标签fragment的适配器
 */
public class MainContentAdapter extends CommonAdapter<GetContentResponse.DyContextsBean> {

    public MainContentAdapter(Context context, List<GetContentResponse.DyContextsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, GetContentResponse.DyContextsBean item) {

        RoundedImageView iv_avatar = helper.getView(R.id.iv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_content = helper.getView(R.id.tv_content);
        ImageView contentIv = helper.getView(R.id.content_iv);
        ImageView iv_sex = helper.getView(R.id.iv_sex);
        TextView tv_liked = helper.getView(R.id.tv_liked);
        TextView tv_unliked = helper.getView(R.id.tv_unliked);
        TextView tv_comments = helper.getView(R.id.tv_comments);
        JZVideoPlayerStandard jz = helper.getView(R.id.jz_video);

        String type = item.getContextType();
        switch (type) {
            case "2"://段子
                tv_content.setVisibility(View.VISIBLE);
                contentIv.setVisibility(View.GONE);
                jz.setVisibility(View.GONE);
                tv_content.setText(item.getContextText());

                break;
            case "3"://图片
                tv_content.setVisibility(View.GONE);
                contentIv.setVisibility(View.VISIBLE);
                jz.setVisibility(View.GONE);
                Glide.with(mContext).load(item.getContextUrl()).into(contentIv);
                break;
            case "4"://视频
                tv_content.setVisibility(View.GONE);
                contentIv.setVisibility(View.GONE);
                jz.setVisibility(View.VISIBLE);

                jz.setUp(item.getContextUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                GlideApp.with(mContext)
                        .load(item.getVideoDisplay())
                        .error(R.drawable.default_pic)
                        .placeholder(R.drawable.default_pic)
                        .into(jz.thumbImageView);
                break;
        }


        tv_name.setText(item.getNickName());
        GlideApp.with(mContext)
                .load(item.getHeadPortraitUrl())
                .error(R.drawable.default_pic)
                .placeholder(R.drawable.default_pic)
                .into(iv_avatar);
        tv_content.setText(item.getContextText());
        //0-女 1-男
        if (item.getSex().equals("0")) {
            Glide.with(mContext).load(R.drawable.icon_female).into(iv_sex);
        } else {
            Glide.with(mContext).load(R.drawable.icon_male).into(iv_sex);
        }
        tv_liked.setText(item.getPraiseNum() + "");
        tv_unliked.setText(item.getTrampleNum() + "");
        tv_comments.setText(item.getCommentNum() + "");

    }

}
