package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

import java.io.File;

/**
 * Created by luojialun on 2018/4/21.
 */

public class ModifyHeadImageRequest extends BaseRequest {
    private String dyID;
    private String deviceID;
    private String token;

    public String getDyID() {
        return dyID;
    }

    public void setDyID(String dyID) {
        this.dyID = dyID;
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

}
