package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetContentRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.GetVerificationCodeRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2018/4/20.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.phone_et)
    TextView phoneEt;

    @Override
    public void setView() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void startInvoke() {

    }


    @OnClick(R.id.next_tv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_tv:
                getContent(RegisterActivity.this, phoneEt.getText().toString(), new GetContentResult() {
                    @Override
                    public void success(String json) {
                        BaseResponse response = JSON.parseObject(json, BaseResponse.class);

                        Intent intent = new Intent(RegisterActivity.this, CodeActivity.class);
                        intent.putExtra("phone",phoneEt.getText().toString());
                        startActivity(intent);
//                        mList.clear();
//                        mList.addAll(response.getDyContexts());
//                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void error(Exception ex) {

                    }
                });
                break;
        }
    }


    /**
     * 获取验证码
     */
    protected void getContent(final Activity context, String phone, final GetContentResult result) {
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
