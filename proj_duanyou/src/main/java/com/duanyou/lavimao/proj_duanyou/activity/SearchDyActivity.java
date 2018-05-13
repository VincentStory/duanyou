package com.duanyou.lavimao.proj_duanyou.activity;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by vincent on 2018/5/12.
 */

public class SearchDyActivity extends BaseActivity {
    @Override
    public void setView() {
        setContentView(R.layout.activity_search_dy);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        setTitle("搜索");
    }

    @Override
    public void startInvoke() {

    }
}
