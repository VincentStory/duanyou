package com.duanyou.lavimao.proj_duanyou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.duanyou.lavimao.proj_duanyou.GlideApp;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentUnreviewedResponse;
import com.duanyou.lavimao.proj_duanyou.util.ImageUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by luojialun on 2018/4/26.
 */

public class CheckAdapter extends CommonAdapter<GetContentUnreviewedResponse.DyContextsBean> {


    public CheckAdapter(Context context, int layoutId, List<GetContentUnreviewedResponse.DyContextsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder helper, GetContentUnreviewedResponse.DyContextsBean item, int position) {

        TextView contentTv = helper.getView(R.id.content_tv);
        ImageView contentIv = helper.getView(R.id.content_iv);
        JZVideoPlayerStandard jz = helper.getView(R.id.jz_video);
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
                        .error(R.drawable.default_load)
                        .placeholder(R.drawable.default_load)
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
                        .error(R.drawable.default_load)
                        .placeholder(R.drawable.default_load)
                        .into(jz.thumbImageView);
                break;
        }

        if (TextUtils.isEmpty(item.getContextText())) {
            contentTv.setVisibility(View.GONE);
        } else {
            contentTv.setVisibility(View.VISIBLE);
            contentTv.setText(item.getContextText());
        }
    }

}
