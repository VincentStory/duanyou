package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

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
import com.xiben.ebs.esbsdk.callback.ResultCallback;

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


    @OnClick({R.id.no_account_tv, R.id.login_btn, R.id.qq_iv, R.id.weixin_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no_account_tv:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:

                if (checkContent(accountEt.getText().toString().trim(), pwdEt.getText().toString().trim())){

                    login(LoginActivity.this, accountEt.getText().toString().trim(), pwdEt.getText().toString().trim(), new GetContentResult() {
                        @Override
                        public void success(String json) {
                            LoginResponse response = JSON.parseObject(json, LoginResponse.class);
                            SpUtil.saveStringSP(SpUtil.dyID, response.getDyID());
                            SpUtil.saveStringSP(SpUtil.TOKEN, response.getToken());
                            SpUtil.saveStringSP(SpUtil.nickName, response.getNickName());
                            SpUtil.saveStringSP(SpUtil.currentLocation, response.getCurrentLocation());

                            finish();

                        }

                        @Override
                        public void error(Exception ex) {

                        }
                    });
                }




                break;
            case R.id.qq_iv:
                ToastUtils.showShort("正在维护");
                break;
            case R.id.weixin_iv:
                ToastUtils.showShort("正在维护");
                break;
        }
    }


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
        request.setPassword(password);


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
