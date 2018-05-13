package com.duanyou.lavimao.proj_duanyou.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.net.response.GetFollowsBean;
import com.duanyou.lavimao.proj_duanyou.net.response.GetInteraction;
import com.duanyou.lavimao.proj_duanyou.util.CommonAdapter;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 标签fragment的适配器
 */
public class InteractionAdapter extends CommonAdapter<GetInteraction.Interactions> {



    public InteractionAdapter(Context context, List<GetInteraction.Interactions> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, GetInteraction.Interactions item) {
        RoundedImageView  headIv = helper.getView(R.id.head_iv);
        RoundedImageView  content_iv = helper.getView(R.id.content_iv);
        TextView nameTv = helper.getView(R.id.name_tv);
        TextView dyNameTv = helper.getView(R.id.dy_name_tv);
        TextView contentTv = helper.getView(R.id.content_tv);
        TextView upload_date_tv = helper.getView(R.id.upload_date_tv);
        TextView replay_name_tv = helper.getView(R.id.replay_name_tv);
        TextView content_text_tv = helper.getView(R.id.content_text_tv);




        Glide.with(mContext).load(UserInfo.getHeadUrl()).into(headIv);
        Glide.with(mContext).load(item.getVideoDisplay()).into(content_iv);
        nameTv.setText(item.getNickName());
        dyNameTv.setText(item.getReplyToNickName());
        contentTv.setText(item.getCommentContent());
        upload_date_tv.setText(item.getUploadDate());
        replay_name_tv.setText(item.getReplyToNickName());
        content_text_tv.setText(item.getContentText());

    }




}
