package com.duanyou.lavimao.proj_duanyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.net.response.BaseReply;
import com.duanyou.lavimao.proj_duanyou.net.response.GetCommentResponse;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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
        helper.setText(R.id.name_tv, item.getNickName());
        helper.setText(R.id.reply_tv, item.getContextText());
    }
}
