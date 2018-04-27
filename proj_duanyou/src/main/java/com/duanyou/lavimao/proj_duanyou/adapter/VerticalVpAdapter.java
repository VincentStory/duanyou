package com.duanyou.lavimao.proj_duanyou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.duanyou.lavimao.proj_duanyou.fragment.CheckFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.CheckItemFragment;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentUnreviewedResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luojialun on 2018/4/25.
 */

public class VerticalVpAdapter extends FragmentPagerAdapter {
    private List<GetContentUnreviewedResponse.DyContextsBean> mList;

    FragmentManager fm;

    public VerticalVpAdapter(FragmentManager fm, List<GetContentUnreviewedResponse.DyContextsBean> mList) {
        super(fm);
        this.fm = fm;
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        return CheckItemFragment.newInstance(mList.get(position), position);
    }


    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }


}
