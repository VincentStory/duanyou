package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class SetPasswordRequest extends BaseRequest {

    private String mobilePhone;
    private String deviceID;
    private String token;
    private String newPassword;


    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
