package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

/**
 * Created by luojialun on 2018/4/20.
 */

public class GetUserUploadContentRequest extends BaseRequest {

    private String targetDyID;
    private String beginContentID;

    public String getTargetDyID() {
        return targetDyID;
    }

    public void setTargetDyID(String targetDyID) {
        this.targetDyID = targetDyID;
    }

    public String getBeginContentID() {
        return beginContentID;
    }

    public void setBeginContentID(String beginContentID) {
        this.beginContentID = beginContentID;
    }
}
