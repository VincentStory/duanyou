package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class VerifyCodeRequest extends BaseRequest {

    private String mobilePhone;
    private String deviceID;
    private String erificationCode;


    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String getDeviceID() {
        return deviceID;
    }

    @Override
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getErificationCode() {
        return erificationCode;
    }

    public void setErificationCode(String erificationCode) {
        this.erificationCode = erificationCode;
    }
}
