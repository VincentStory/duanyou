package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
    private String phone;

    @Override
    public void setView() {
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    public void startInvoke() {

    }


    @OnClick({R.id.next_tv, R.id.back_tv, R.id.get_code_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_tv:
                VerifyCode(CodeActivity.this, codeEt.getText().toString(), new GetContentResult() {
                    @Override
                    public void success(String json) {
                        VerifyCodeResponse response = JSON.parseObject(json, VerifyCodeResponse.class);
                        String token = response.getToken();

                        SpUtil.saveStringSP(SpUtil.TOKEN, token);

                        Intent intent = new Intent(CodeActivity.this, SettingPwdActivity.class);

                        startActivity(intent);
                    }

                    @Override
                    public void error(Exception ex) {

                    }
                });
                break;
            case R.id.back_tv:
                finish();
                break;
            case R.id.get_code_tv:
                getCode(CodeActivity.this, phone, new GetContentResult() {
                    @Override
                    public void success(String json) {


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
