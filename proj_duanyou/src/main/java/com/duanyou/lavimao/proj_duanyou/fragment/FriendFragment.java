package com.duanyou.lavimao.proj_duanyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.SearchDyActivity;
import com.duanyou.lavimao.proj_duanyou.adapter.MainPagerAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TagFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendFragment extends BaseFragment {
    @BindView(R.id.message_tv)
    TextView message_tv;
    @BindView(R.id.friend_tv)
    TextView friend_tv;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private MainPagerAdapter pagerAdapter;
    private List<Fragment> fragments;


    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {
        initPagers();
    }

    @Override
    public void startInvoke(View view) {
        message_tv.setTextColor(getResources().getColor(R.color.white));
        message_tv.setSelected(true);
    }


    private void recoverDefaultState() {

        message_tv.setSelected(false);
        friend_tv.setSelected(false);
        message_tv.setTextColor(getResources().getColor(R.color.black2));
        friend_tv.setTextColor(getResources().getColor(R.color.black2));
    }

    @OnClick({R.id.message_tv, R.id.friend_tv, R.id.search_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_tv:
                recoverDefaultState();
                message_tv.setTextColor(getResources().getColor(R.color.white));
                message_tv.setSelected(true);
                break;
            case R.id.friend_tv:
                recoverDefaultState();
                friend_tv.setTextColor(getResources().getColor(R.color.white));
                friend_tv.setSelected(true);
                break;
            case R.id.search_rl:
                gotoActivity(SearchDyActivity.class);
                break;
        }
    }


    /**
     * 初始化ViewPager
     */
    private void initPagers() {
        fragments = new ArrayList<>();
        fragments.add(TagFragment.newInstance("4"));//关注
        fragments.add(TagFragment.newInstance("1"));//推荐

        pagerAdapter = new MainPagerAdapter(getActivity().getSupportFragmentManager(), fragments, getActivity());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    recoverDefaultState();
                    message_tv.setTextColor(getResources().getColor(R.color.white));
                    message_tv.setSelected(true);
                } else {
                    recoverDefaultState();
                    friend_tv.setTextColor(getResources().getColor(R.color.white));
                    friend_tv.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
