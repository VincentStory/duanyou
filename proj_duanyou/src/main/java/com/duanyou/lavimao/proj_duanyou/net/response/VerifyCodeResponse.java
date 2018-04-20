package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.util.List;

public class VerifyCodeResponse extends BaseResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
