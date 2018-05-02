package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.MyApplication;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetVerificationCodeRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.RegisterRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.SetPasswordRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.VerifyCodeRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.VerifyCodeResponse;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.DeviceUtils;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;
import com.duanyou.lavimao.proj_duanyou.util.StringUtil;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 效验验证码
 * Created by vincent on 2018/4/21.
 */

public class SettingPwdActivity extends BaseActivity {
    @BindView(R.id.password_et)
    TextView passwordEt;
    @BindView(R.id.title_tv)
    TextView titleTv;
    private String password;
    private String password2;
    private int type = 1;
    private String pageType;

    @Override
    public void setView() {
        setContentView(R.layout.activity_setting_pwd);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        pageType = getIntent().getStringExtra("pageType");

        if (pageType.equals(Contents.PWD_CODE_TYPE)) {
            titleTv.setText("设置新的密码");
            passwordEt.setHint("新密码不能与原密码相同");
        }

        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void startInvoke() {

    }


    @OnClick({R.id.next_tv, R.id.back_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_tv:


                if (type == 1) {//第一次输入密码
                    password = passwordEt.getText().toString().trim();

                    if (password.length() >= 6) {

                        passwordEt.setText("");
                        passwordEt.setHint("请再次输入密码");
                        type = 2;
                    } else {
                        ToastUtils.showShort("请输入6位以上密码");
                    }
                } else {//确认密码
                    password2 = passwordEt.getText().toString().trim();
                    if (password2.length() >= 6) {
                        if (password.equals(password2)) {

                            if (pageType.equals(Contents.REGISTER_CODE_TYPE)) {
                                register(SettingPwdActivity.this
                                        , password2, new GetContentResult() {
                                            @Override
                                            public void success(String json) {
                                                Intent intent = new Intent(SettingPwdActivity.this, RegisterSuccessActivity.class);
                                                intent.putExtra("pageType",pageType);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void error(Exception ex) {

                                            }
                                        });
                            } else if (pageType.equals(Contents.PWD_CODE_TYPE)) {

                                setPassword(SettingPwdActivity.this, password2, new GetContentResult() {

                                    @Override
                                    public void success(String json) {
                                        Intent intent = new Intent(SettingPwdActivity.this, RegisterSuccessActivity.class);
                                        intent.putExtra("pageType",pageType);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void error(Exception ex) {

                                    }
                                });

                            }


                        } else {
                            ToastUtils.showShort("请输入相同密码");
                        }

                    } else {
                        ToastUtils.showShort("请输入6位以上密码");
                    }
                }


//                (SettingPwdActivity.this, passwordEt.getText().toString(), new GetContentResult() {
//                    @Override
//                    public void success(String json) {
//                        VerifyCodeResponse response = JSON.parseObject(json, VerifyCodeResponse.class);
//                        String token = response.getToken();
//
//                    }
//
//                    @Override
//                    public void error(Exception ex) {
//
//                    }
//                });
                break;
            case R.id.back_tv:
                finish();
                break;

        }
    }


    /**
     * 注册
     */
    private void register(final Activity context, String password, final GetContentResult result) {
        RegisterRequest request = new RegisterRequest();
        request.setMobilePhone(SpUtil.getStringSp(SpUtil.mobilePhone));
        request.setDeviceID(DeviceUtils.getAndroidID());
        request.setToken(SpUtil.getStringSp(SpUtil.TOKEN));
        request.setPassword(StringUtil.md5(password));
        NetUtil.getData(Api.REGISTER, context, request, new ResultCallback() {
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

    /**
     * 设置密码
     */
    private void setPassword(final Activity context, String password, final GetContentResult result) {
        SetPasswordRequest request = new SetPasswordRequest();
        request.setMobilePhone(SpUtil.getStringSp(SpUtil.mobilePhone));
        request.setDeviceID(UserInfo.getDeviceId());
        request.setNewPassword(password);
        request.setToken(UserInfo.getToken());
        NetUtil.getData(Api.setPassword, context, request, new ResultCallback() {
            @Override
            public void onResult(final String jsonResult) {
                BaseResponse response = JSON.parseObject(jsonResult, BaseResponse.class);
                if (response.getRespCode().equals("0")) {

                    result.success(jsonResult);


                } else {

                }

            }

            @Override
            public void onError(Exception ex) {

                result.error(ex);
            }
        });
    }
}
