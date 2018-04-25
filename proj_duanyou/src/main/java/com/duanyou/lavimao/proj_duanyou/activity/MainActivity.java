package com.duanyou.lavimao.proj_duanyou.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.fragment.CheckFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.FriendFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.MainFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.MineFragment;

import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetContentRequest;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.id_activity_main_img_btn_dyou)
    ImageView img_btn_dyou;
    @BindView(R.id.id_activity_main_tv_dyou)
    TextView tv_dyou;
    @BindView(R.id.id_activity_main_img_btn_home)
    ImageView img_btn_home;
    @BindView(R.id.id_activity_main_tv_home)
    TextView tv_home;
    @BindView(R.id.id_activity_main_img_btn_examain)
    ImageView img_btn_examain;
    @BindView(R.id.id_activity_main_tv_examin)
    TextView tv_examin;
    @BindView(R.id.id_activity_main_img_btn_mine)
    ImageView img_btn_mine;
    @BindView(R.id.id_activity_main_tv_mine)
    TextView tv_mine;

    private List<Fragment> fragments;
    private MainFragment mainFragment;
    private FriendFragment friendFragment;
    private CheckFragment checkFragment;
    private MineFragment mineFragment;

    @Override
    public void setView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initEnableSlide(false);
        initFragments();
    }

    @Override
    public void startInvoke() {
//        netRequest();
    }

    /**
     * 初始化四个fragment
     */
    private void initFragments() {
        fragments = new ArrayList<>();
        mainFragment = new MainFragment();
        friendFragment = new FriendFragment();
        checkFragment = new CheckFragment();
        mineFragment = new MineFragment();
        fragments.add(mainFragment);
        fragments.add(friendFragment);
        fragments.add(checkFragment);
        fragments.add(mineFragment);
        addFragmentStack(fragments, R.id.layFrame, 0);
    }

    @OnClick({R.id.id_activity_dymain_lay_home,
            R.id.id_activity_dymain_lay_dyou,
            R.id.id_activity_dymain_lay_examain,
            R.id.id_activity_dymain_lay_mine,
            R.id.more_ll})
    void click(View view) {
        switch (view.getId()) {
            case R.id.id_activity_dymain_lay_home:
                showHome();
                break;
            case R.id.id_activity_dymain_lay_dyou:
                showDyou();
                break;
            case R.id.id_activity_dymain_lay_examain:
                showExamain();
                break;
            case R.id.id_activity_dymain_lay_mine:
                showMine();
                break;
            case R.id.more_ll:
                jumpTo(MoreActivity.class);
                break;

            default:

                break;
        }
    }

    private void recoverDefaultState() {
        img_btn_home.setImageResource(R.drawable.icon_main_home_normal);
        img_btn_dyou.setImageResource(R.drawable.icon_main_friend_normal);
        img_btn_examain.setImageResource(R.drawable.icon_main_examine_normal);
        img_btn_mine.setImageResource(R.drawable.icon_main_mine_normal);

        tv_home.setTextColor(Color.parseColor("#b3b3b3"));
        tv_dyou.setTextColor(Color.parseColor("#b3b3b3"));
        tv_examin.setTextColor(Color.parseColor("#b3b3b3"));
        tv_mine.setTextColor(Color.parseColor("#b3b3b3"));
    }

    //显示我的界面
    private void showMine() {
        recoverDefaultState();
        img_btn_mine.setImageResource(R.drawable.icon_main_mine_selected);
        tv_mine.setTextColor(Color.parseColor("#4d4d4d"));
        addFragmentStack(fragments, R.id.layFrame, 3);
    }

    //显示审核界面
    private void showExamain() {
        recoverDefaultState();
        img_btn_examain.setImageResource(R.drawable.icon_main_examine_selected);
        tv_examin.setTextColor(Color.parseColor("#4d4d4d"));
        addFragmentStack(fragments, R.id.layFrame, 2);

    }

    private void showMore() {
        recoverDefaultState();
    }

    //显示段友界面
    private void showDyou() {
        recoverDefaultState();
        img_btn_dyou.setImageResource(R.drawable.icon_main_friend_selected);
        tv_dyou.setTextColor(Color.parseColor("#4d4d4d"));
        addFragmentStack(fragments, R.id.layFrame, 1);


    }

    //显示主页
    private void showHome() {
        recoverDefaultState();
        img_btn_home.setImageResource(R.drawable.icon_main_home_selected);
        tv_home.setTextColor(Color.parseColor("#4d4d4d"));
        addFragmentStack(fragments, R.id.layFrame, 0);

    }


}
