package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class VerifyCodeRequest extends BaseRequest {

    private String mobilePhone;
    private String devideID;
    private String erificationCode;


    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDevideID() {
        return devideID;
    }

    public void setDevideID(String devideID) {
        this.devideID = devideID;
    }

    public String getErificationCode() {
        return erificationCode;
    }

    public void setErificationCode(String erificationCode) {
        this.erificationCode = erificationCode;
    }
}
