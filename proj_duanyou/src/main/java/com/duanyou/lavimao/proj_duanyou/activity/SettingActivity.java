package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.LoginRequest;
import com.duanyou.lavimao.proj_duanyou.util.DeviceUtils;
import com.duanyou.lavimao.proj_duanyou.util.StringUtil;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.EnsureDialog;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by czxyl171151 on 2018/4/25.
 */

public class SettingActivity extends BaseActivity {
    private EnsureDialog ensureDialog;

    @Override
    public void setView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.login_out_tv, R.id.iv_left, R.id.change_pwd_rl, R.id.opinion_rl, R.id.about_duanyou_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_out_tv:
                showEnsureDialogOne();
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.change_pwd_rl:
                jumpTo(FindPwdActivity.class);
                break;
            case R.id.opinion_rl:
                jumpTo(UserFeedBackActivity.class);
                break;
                case R.id.about_duanyou_rl:
                jumpTo(UserProtocolActivity.class);
                break;
        }
    }

    /**
     * EnsureDialog
     */
    private void showEnsureDialogOne() {
        ensureDialog = new EnsureDialog(this).builder()
                .setGravity(Gravity.CENTER)//默认居中，可以不设置
                .setTitle("确定退出当前账号", getResources().getColor(R.color.sd_color_black))//可以不设置标题颜色，默认系统颜色
                .setCancelable(false)
                .setNegativeButton("退出", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                    @Override
                    public void onClick(View view) {
                        loginOut();
                    }
                })
                .setPositiveButton("取消", getResources().getColor(R.color.sd_color_red), new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                    @Override
                    public void onClick(View view) {

                        ensureDialog.dismiss();

                    }
                });
        ensureDialog.show();

    }

    /**
     * 注销
     */
    protected void loginOut() {
        UserInfo.clearUserInfo();
        finish();
    }

}
