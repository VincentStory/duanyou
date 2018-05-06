package com.duanyou.lavimao.proj_duanyou.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.fragment.CheckFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.FriendFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.MainFragment;
import com.duanyou.lavimao.proj_duanyou.fragment.MineFragment;

import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetContentRequest;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
//                UMImage imageurl = new UMImage(this, R.drawable.back);
//                imageurl.setThumb(new UMImage(this, R.drawable.add2x));
//                new ShareAction(MainActivity.this).withMedia(imageurl)
//                        .setPlatform(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform)
//                        .setCallback(shareListener).share();

//                UMShareAPI.get(MainActivity.this).doOauthVerify(MainActivity.this, SHARE_MEDIA.WEIXIN, authListener);
//UMShareAPI.get(MainActivity.this).getPlatformInfo();

                showMine();
                break;
            case R.id.more_ll:
                jumpTo(MoreActivity.class);
                break;

            default:

                break;
        }
    }


    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Set<Map.Entry<String, String>> set = data.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iterator.next();

                Log.i(TAG, "onComplete: " + mapEntry.getValue());
            }
            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "onError: " + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(MainActivity.this);
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

//            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
//            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(MainActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
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
