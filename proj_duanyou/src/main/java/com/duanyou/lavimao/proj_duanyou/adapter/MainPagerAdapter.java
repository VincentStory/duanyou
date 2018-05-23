package com.duanyou.lavimao.proj_duanyou.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private Activity context;


    public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments, Activity context) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
