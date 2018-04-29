package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.MyApplication;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetVerificationCodeRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.LoginRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.LoginResponse;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.DeviceUtils;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;
import com.duanyou.lavimao.proj_duanyou.util.StringUtil;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2018/4/20.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.account_et)
    EditText accountEt;
    @BindView(R.id.pwd_et)
    EditText pwdEt;


    @Override
    public void setView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
//        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void startInvoke() {

    }


    @OnClick({R.id.forget_pwd_tv, R.id.no_account_tv, R.id.login_btn, R.id.qq_iv, R.id.weixin_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_pwd_tv:
                jumpTo(FindPwdActivity.class);
                break;
            case R.id.no_account_tv:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:

                if (checkContent(accountEt.getText().toString().trim(), pwdEt.getText().toString().trim())) {

                    login(LoginActivity.this, accountEt.getText().toString().trim(), pwdEt.getText().toString().trim(), new GetContentResult() {
                        @Override
                        public void success(String json) {
                            LoginResponse response = JSON.parseObject(json, LoginResponse.class);
                            SpUtil.saveStringSP(SpUtil.dyID, response.getDyID());
                            SpUtil.saveStringSP(SpUtil.TOKEN, response.getToken());
                            SpUtil.saveStringSP(SpUtil.nickName, response.getNickName());
                            SpUtil.saveStringSP(SpUtil.currentLocation, response.getCurrentLocation());
                            UserInfo.setBgUrl(response.getBackgroundWall());
                            UserInfo.setHeadUrl(response.getHeadPortraitUrl());
                            finish();

                        }

                        @Override
                        public void error(Exception ex) {

                        }
                    });
                }

                break;
            case R.id.qq_iv:
//                ToastUtils.showShort("正在维护");

                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        StringBuilder sb = new StringBuilder();
                        for (String key : map.keySet()) {
                            sb.append(key).append(" : ").append(map.get(key)).append("\n");
                        }
//                        result.setText(sb.toString());
                        ToastUtils.showShort(sb.toString());
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//                        result.setText("错误" + throwable.getMessage());
                        ToastUtils.showShort(throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
//                        result.setText("用户已取消");
                        ToastUtils.showShort("用户已取消");
                    }
                });

//                UMShareAPI.get(LoginActivity.this).doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
//                ToastUtils.showShort("正在维护");
                break;

            case R.id.weixin_iv:
//               UMShareAPI.init();
//                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, authListener);

                UMShareAPI.get(LoginActivity.this).doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        StringBuilder sb = new StringBuilder();
                        for (String key : map.keySet()) {
                            sb.append(key).append(" : ").append(map.get(key)).append("\n");
                        }
//                        result.setText(sb.toString());
                        ToastUtils.showShort(sb.toString());
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//                        result.setText("错误" + throwable.getMessage());
                        ToastUtils.showShort(throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
//                        result.setText("用户已取消");
                        ToastUtils.showShort("用户已取消");
                    }
                });

//                UMShareAPI.get(LoginActivity.this).doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
//                ToastUtils.showShort("正在维护");
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
            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "onError: " + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        UMShareAPI.get(this).release();
//
//    }
    private boolean checkContent(String name, String pwd) {
        if (name.isEmpty() || pwd.isEmpty()) {

            ToastUtils.showShort("账号或密码为空");
            return false;
        }
        return true;
    }

    /**
     * 登录
     */
    protected void login(final Activity context, String phone, String password, final GetContentResult result) {
        LoginRequest request = new LoginRequest();
        request.setMobilePhone(phone);
        request.setDeviceID(DeviceUtils.getAndroidID());
        request.setPassword(StringUtil.md5(password));
        NetUtil.getData(Api.LOGIN, context, request, new ResultCallback() {
            @Override
            public void onResult(final String jsonResult) {
                BaseResponse response = JSON.parseObject(jsonResult, BaseResponse.class);
                if (response.getRespCode().equals("0")) {
                    result.success(jsonResult);
                } else {
                    ToastUtils.showShort(response.getRespMessage());
                }

            }

            @Override
            public void onError(Exception ex) {
                result.error(ex);
            }
        });
    }
}
