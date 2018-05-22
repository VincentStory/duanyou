package com.duanyou.lavimao.proj_duanyou.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.net.response.GetFollowsBean;
import com.duanyou.lavimao.proj_duanyou.net.response.NearbyPeopleResponse;
import com.duanyou.lavimao.proj_duanyou.util.CommonAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 标签fragment的适配器
 */
public class FansAdapter extends CommonAdapter<GetFollowsBean.Fans> {

    private int type;

    public FansAdapter(Context context, List<GetFollowsBean.Fans> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);

    }


    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void convert(ViewHolder helper, GetFollowsBean.Fans item) {
        RoundedImageView headIv = helper.getView(R.id.head_iv);
        TextView nameTv = helper.getView(R.id.name_tv);
        TextView statusTv = helper.getView(R.id.tv_status);

        if (item.getMutual().equals("1")) {
            statusTv.setText("互相关注");

        } else {
            if (type == 1) {
                statusTv.setText("已关注");
            } else {
                statusTv.setText("关注");
            }

        }


        Glide.with(mContext).load(item.getHeadPortraitUrl()).into(headIv);
        nameTv.setText(item.getNickName());

    }


}
