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



    public FansAdapter(Context context, List<GetFollowsBean.Fans> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, GetFollowsBean.Fans item) {
        RoundedImageView  headIv = helper.getView(R.id.head_iv);
        TextView nameTv = helper.getView(R.id.name_tv);




        Glide.with(mContext).load(item.getHeadPortraitUrl()).into(headIv);
        nameTv.setText(item.getNickName());

    }




}
