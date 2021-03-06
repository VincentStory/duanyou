package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
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
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 输入手机号注册
 * Created by vincent on 2018/4/20.
 */

public class FindPwdActivity extends BaseActivity {

    @BindView(R.id.phone_et)
    TextView phoneEt;
    @BindView(R.id.read_cb)
    CheckBox read_cb;

    @Override
    public void setView() {
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void startInvoke() {

    }


    @OnClick({R.id.next_tv, R.id.back_tv, R.id.goto_main_tv, R.id.read_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_tv:
                finish();
                break;
            case R.id.read_tv:
                read_cb.setChecked(!read_cb.isChecked());
                jumpTo(UserProtocolActivity.class);
                break;
            case R.id.next_tv:
//                Intent intent = new Intent(FindPwdActivity.this, CodeActivity.class);
//                intent.putExtra("phone", phoneEt.getText().toString());
//                intent.putExtra("type", Contents.PWD_CODE_TYPE);
//                startActivity(intent);
                if (phoneEt.getText().toString().trim().isEmpty()) {
                    ToastUtils.showShort("请输入正确的手机号");
                    break;
                }
                if (!read_cb.isChecked()) {
                    ToastUtils.showShort("请阅读使用条款");
                    break;
                }

                getCode(FindPwdActivity.this, phoneEt.getText().toString(), new GetContentResult() {
                    @Override
                    public void success(String json) {
                        BaseResponse response = JSON.parseObject(json, BaseResponse.class);
                        SpUtil.saveStringSP(SpUtil.mobilePhone, phoneEt.getText().toString().trim());
                        Intent intent = new Intent(FindPwdActivity.this, CodeActivity.class);
                        intent.putExtra("phone", phoneEt.getText().toString());
                        intent.putExtra("type", Contents.PWD_CODE_TYPE);
                        startActivity(intent);

                    }

                    @Override
                    public void error(Exception ex) {

                    }
                });
                break;
            case R.id.goto_main_tv:
                jumpTo(MainActivity.class);
                break;
            default:
                break;
        }
    }


    /**
     * 获取验证码
     */
    protected void getCode(final Activity context, String phone, final GetContentResult result) {
        GetVerificationCodeRequest request = new GetVerificationCodeRequest();
        request.setMobilePhone(phone);
        request.setCodeType(Contents.PWD_CODE_TYPE);
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
