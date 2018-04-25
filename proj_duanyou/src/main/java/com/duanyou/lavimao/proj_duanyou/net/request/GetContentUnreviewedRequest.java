package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

/**
 * Created by luojialun on 2018/4/25.
 */

public class GetContentUnreviewedRequest extends BaseRequest {

    private String dyID;

    @Override
    public String getDyID() {
        return dyID;
    }

    @Override
    public void setDyID(String dyID) {
        this.dyID = dyID;
    }
}
