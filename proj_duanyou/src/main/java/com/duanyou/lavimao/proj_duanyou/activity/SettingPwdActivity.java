package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetVerificationCodeRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.RegisterRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.VerifyCodeRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.VerifyCodeResponse;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.DeviceUtils;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;
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
    private String password;
    private String password2;
    private int type = 1;

    @Override
    public void setView() {
        setContentView(R.layout.activity_setting_pwd);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

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
                    passwordEt.setText("");
                    passwordEt.setHint("请再次输入密码");
                    type = 2;
//                    if (password.length() == 6) {


//                    } else {
//                        ToastUtils.showShort("请输入6位密码");
//                    }
                } else {//确认密码
                    password2 = passwordEt.getText().toString().trim();
//                    if (password2.length() == 6) {
                        if (password.equals(password2)) {


                            register(SettingPwdActivity.this, SpUtil.getStringSp(SpUtil.mobilePhone),
                                    SpUtil.getStringSp(SpUtil.TOKEN), password2, new GetContentResult() {
                                        @Override
                                        public void success(String json) {
                                            Intent intent = new Intent(SettingPwdActivity.this, RegisterSuccessActivity.class);
//
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void error(Exception ex) {

                                        }
                                    });

                        } else {
                            ToastUtils.showShort("请输入相同密码");
                        }

//                    } else {
//                        ToastUtils.showShort("请输入6位密码");
//                    }
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
    private void register(final Activity context, String phone, String token, String password, final GetContentResult result) {
        RegisterRequest request = new RegisterRequest();
        request.setMobilePhone(phone);
        request.setDeviceID(DeviceUtils.getAndroidID());
        request.setToken(token);
        request.setPassword(password);
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
     * 效验验证码
     */
    private void getCode(final Activity context, String phone, final GetContentResult result) {
        GetVerificationCodeRequest request = new GetVerificationCodeRequest();
        request.setMobilePhone(phone);
        request.setCodeType(Contents.REGISTER_CODE_TYPE);
        NetUtil.getData(Api.getVerificationCode, context, request, new ResultCallback() {
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
