package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class FriendCircleRequest extends BaseRequest {

    /**


     */


    private String deviceID;
    private String dyID;
    private String token;

    private String beginContentID;

    @Override
    public String getDeviceID() {
        return deviceID;
    }

    @Override
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    @Override
    public String getDyID() {
        return dyID;
    }

    @Override
    public void setDyID(String dyID) {
        this.dyID = dyID;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    public String getBeginContentID() {
        return beginContentID;
    }

    public void setBeginContentID(String beginContentID) {
        this.beginContentID = beginContentID;
    }
}
