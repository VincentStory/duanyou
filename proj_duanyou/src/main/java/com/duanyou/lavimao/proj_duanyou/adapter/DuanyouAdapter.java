package com.duanyou.lavimao.proj_duanyou.adapter;

import android.content.Context;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.net.bean.DuanyouBean;


import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by luojialun on 2018/5/20.
 */

public class DuanyouAdapter extends CommonAdapter<DuanyouBean> {


    public DuanyouAdapter(Context context, int layoutId, List<DuanyouBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder helper, DuanyouBean duanyouBean, int position) {
        helper.setImageResource(R.id.photo_iv, duanyouBean.getPhoto());
        helper.setText(R.id.name_tv, duanyouBean.getName());
    }
}
