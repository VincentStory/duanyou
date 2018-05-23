package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

/**
 * Created by luojialun on 2018/4/21.
 */

public class GetCommentRequest extends BaseRequest{
    private String dyContextID;
    private String benginCommentID;

    public String getDyContextID() {
        return dyContextID;
    }

    public void setDyContextID(String dyContextID) {
        this.dyContextID = dyContextID;
    }

    public String getBenginCommentID() {
        return benginCommentID;
    }

    public void setBenginCommentID(String benginCommentID) {
        this.benginCommentID = benginCommentID;
    }

}
