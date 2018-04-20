package com.duanyou.lavimao.proj_duanyou.net;

public class BaseRequest {

    private String token;
    private String dyID;
    private String deviceID;

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
