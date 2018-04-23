package com.duanyou.lavimao.proj_duanyou.adapter;


import android.content.Context;
import android.view.View;

import com.duanyou.lavimao.proj_duanyou.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luojialun on 2018/4/23.
 */

public class ReportAdapter extends CommonAdapter<String> {

    public List<Boolean> selectList = new ArrayList<>();

    public ReportAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        for (int i = 0; i < datas.size(); i++) {
            selectList.add(false);
        }
    }

    @Override
    protected void convert(final ViewHolder helper, String s, final int position) {
        helper.setText(R.id.content_tv, s);
        helper.setOnClickListener(R.id.report_ll, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectList.get(position)) {
                    selectList.set(position, false);
                    helper.setImageResource(R.id.select_iv, R.drawable.unselect);
                } else {
                    selectList.set(position, true);
                    helper.setImageResource(R.id.select_iv, R.drawable.select);
                }
            }
        });
    }

    public List<Boolean> getSelectList() {
        return selectList;
    }
}
