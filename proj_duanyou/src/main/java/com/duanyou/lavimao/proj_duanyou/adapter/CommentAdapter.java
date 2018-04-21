package com.duanyou.lavimao.proj_duanyou.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.net.response.GetCommentResponse;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by luojialun on 2018/4/21.
 */

public class CommentAdapter extends CommonAdapter<GetCommentResponse.CommentsNewBean> {


    public CommentAdapter(Context context, int layoutId, List<GetCommentResponse.CommentsNewBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder helper, GetCommentResponse.CommentsNewBean item, int position) {
        GlideApp.with(mContext)
                .load(item.getHeadPortraitUrl())
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .into((ImageView) helper.getView(R.id.head_iv));
        helper.setText(R.id.name_tv, item.getNickName());
        helper.setText(R.id.time_tv, item.getUploadDate());
        helper.setText(R.id.comment_tv, item.getContextText());
        helper.setText(R.id.zan_tv, item.getPraiseNum() + "");
    }
}
