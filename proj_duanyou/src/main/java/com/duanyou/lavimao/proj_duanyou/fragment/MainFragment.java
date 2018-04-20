package com.duanyou.lavimao.proj_duanyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.MainPagerAdapter;
import com.duanyou.lavimao.proj_duanyou.adapter.TitlesAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.DuanyouxiuFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.DuanziFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.GuanzhuFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.JingxuanFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.QutuFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.ShipinFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TuijianFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<String> mList;
    private TitlesAdapter mAdapter;
    private List<Fragment> fragments;
    private MainPagerAdapter pagerAdapter;

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {
        setTitle(view,"首 页");
        setLeftImg(view,R.drawable.icon_logo);
        setRightIv(view,R.drawable.icon_camera);
        initTitles();
        initPagers();
    }

    @Override
    public void startInvoke(View view) {

    }

    /**
     * 初始化标签行
     */
    private void initTitles() {
        mList = new ArrayList<>();
        mList.add("关注");
        mList.add("推荐");
        mList.add("视频");
        mList.add("趣图");
        mList.add("段子");
        mList.add("精选");
        mList.add("段友秀");
        mAdapter = new TitlesAdapter(mList, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new TitlesAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                viewPager.setCurrentItem(position);
            }
        });
    }

    /**
     * 初始化ViewPager
     */
    private void initPagers() {
        fragments = new ArrayList<>();
        fragments.add(new GuanzhuFragment());
        fragments.add(new TuijianFragment());
        fragments.add(new ShipinFragment());
        fragments.add(new QutuFragment());
        fragments.add(new DuanziFragment());
        fragments.add(new JingxuanFragment());
        fragments.add(new DuanyouxiuFragment());
        pagerAdapter = new MainPagerAdapter(getFragmentManager(), fragments,getActivity());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(7);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mAdapter.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
