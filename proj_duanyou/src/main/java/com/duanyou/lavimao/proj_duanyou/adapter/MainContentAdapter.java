package com.duanyou.lavimao.proj_duanyou.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;
import com.duanyou.lavimao.proj_duanyou.util.CommonAdapter;
import com.xiben.ebs.esbsdk.util.LogUtil;
import com.zhy.adapter.abslistview.ViewHolder;

import java.net.URI;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;


public class MainContentAdapter extends CommonAdapter<GetContentResponse.DyContextsBean> {
    public MainContentAdapter(Context context, List<GetContentResponse.DyContextsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, GetContentResponse.DyContextsBean item) {
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_content = helper.getView(R.id.tv_content);
        ImageView iv_sex = helper.getView(R.id.iv_sex);
        TextView tv_liked = helper.getView(R.id.tv_liked);
        TextView tv_unliked = helper.getView(R.id.tv_unliked);
        TextView tv_comments = helper.getView(R.id.tv_comments);

//        JZVideoPlayerStandard jz = helper.getView(R.id.jz);
//        jz.setUp(item.getContextUrl(),JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");
//        GlideApp.with(mContext).load(item.getVideoDisplay()).into(jz.thumbImageView);
//        GlideApp.with(mContext).load(item.getHeadPortraitUrl()).into(iv_avatar);
        tv_name.setText(item.getNickName());
        tv_content.setText(item.getContextText());
        //0-女 1-男
        if (item.getSex().equals("0")) {
//            GlideApp.with(mContext).load(R.drawable.icon_female).into(iv_sex);
        } else {
//            GlideApp.with(mContext).load(R.drawable.icon_male).into(iv_sex);
        }
        tv_liked.setText(item.getPraiseNum() + "");
        tv_unliked.setText(item.getTrampleNum() + "");
        tv_comments.setText(item.getCommentNum() + "");

    }


}
