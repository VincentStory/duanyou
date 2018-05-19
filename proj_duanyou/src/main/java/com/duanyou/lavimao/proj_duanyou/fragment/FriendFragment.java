package com.duanyou.lavimao.proj_duanyou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.duanyou.lavimao.proj_duanyou.MyApplication;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.SearchDyActivity;
import com.duanyou.lavimao.proj_duanyou.adapter.MainPagerAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TagFragment;
import com.duanyou.lavimao.proj_duanyou.util.Md5Utils;

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

    @OnClick({R.id.message_tv, R.id.friend_tv, R.id.search_rl, R.id.iv_right})
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
            case R.id.iv_right:
                initChat();
                break;
        }
    }

    private void initChat() {
        //开始登录
        String userid = "7748480";
        String password = "zhanglei";
        String md5Str=Md5Utils.getMD5(password);
        IYWLoginService loginService = MyApplication.getImKit().getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, Md5Utils.getMD5(password));

        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                Log.i(TAG, "onSuccess: "+arg0);
                Intent intent = MyApplication.getImKit().getConversationActivityIntent();
                startActivity(intent);
            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                Log.i("TAG", "chat error-->" + errCode + " " + description);
            }
        });
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
