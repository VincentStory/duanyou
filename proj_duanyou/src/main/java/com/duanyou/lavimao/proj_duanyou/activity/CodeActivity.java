package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

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
import com.duanyou.lavimao.proj_duanyou.net.request.VerifyCodeRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;
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

public class CodeActivity extends BaseActivity {
    @BindView(R.id.code_et)
    TextView codeEt;
    @BindView(R.id.get_code_tv)
    TextView getCodeTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    private String phone;
    private String type;
    private TimeCount time;

    @Override
    public void setView() {
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        phone = getIntent().getStringExtra("phone");
        type = getIntent().getStringExtra("type");
//        if(type.equals(Contents.PWD_CODE_TYPE)){
//            titleTv.setText("验证码已发送到您的手机号，请登录查看");
//        }
        MyApplication.getInstance().addActivity(this);

        time = new TimeCount(90000, 1000);
        time.start();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            getCodeTv.setBackgroundColor(Color.parseColor("#B6B6D8"));
            getCodeTv.setClickable(false);
            getCodeTv.setText("重新发送（" + getTime(millisUntilFinished / 1000) + ")");
        }

        @Override
        public void onFinish() {
            getCodeTv.setText("重新获取验证码");
            getCodeTv.setClickable(true);
//            getCodeTv.setBackgroundColor(Color.parseColor("#4EB84A"));

        }
    }

    private String getTime(long second) {
        String time = "";
        int minute = 1;
        int second1;
        if (second > 60) {
            time = minute + "分" + (second - 60) + "秒";
        } else {
            time = second + "秒";
        }


        return time;
    }

    @Override
    public void startInvoke() {

    }


    @OnClick({R.id.next_tv, R.id.back_tv, R.id.get_code_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_tv:
                Intent intent = new Intent(this, SettingPwdActivity.class);
                intent.putExtra("pageType",type);
                startActivity(intent);


//                if (codeEt.getText().toString().trim().isEmpty()) {
//                    ToastUtils.showShort("请输入正确的验证码");
//                    break;
//                }
//                VerifyCode(CodeActivity.this, codeEt.getText().toString(), new GetContentResult() {
//                    @Override
//                    public void success(String json) {
//                        VerifyCodeResponse response = JSON.parseObject(json, VerifyCodeResponse.class);
//                        String token = response.getToken();
//
//                        SpUtil.saveStringSP(SpUtil.TOKEN, token);
//
//                        jumpTo(SettingPwdActivity.class);
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
            case R.id.get_code_tv:


                getCode(CodeActivity.this, phone, new GetContentResult() {
                    @Override
                    public void success(String json) {

                        time = new TimeCount(90000, 1000);
                        time.start();
                    }

                    @Override
                    public void error(Exception ex) {

                    }
                });
                break;
        }
    }


    /**
     * 效验验证码
     */
    private void VerifyCode(final Activity context, String code, final GetContentResult result) {
        VerifyCodeRequest request = new VerifyCodeRequest();
        request.setMobilePhone(phone);
        request.setDeviceID(DeviceUtils.getAndroidID());
        request.setErificationCode(code);
        NetUtil.getData(Api.verifyCode, context, request, new ResultCallback() {
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
        request.setCodeType(type);
        NetUtil.getData(Api.getVerificationCode, context, request, new ResultCallback() {
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
