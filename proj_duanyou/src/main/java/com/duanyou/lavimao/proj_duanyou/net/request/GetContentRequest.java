package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class GetContentRequest extends BaseRequest {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
