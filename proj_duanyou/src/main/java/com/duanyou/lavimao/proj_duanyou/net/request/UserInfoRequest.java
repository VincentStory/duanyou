package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class UserInfoRequest extends BaseRequest {

    /**
     * 参数名	数据类型	是否必传	备注
     dyID	String	是	段友ID
     deviceID	String	是	设备ID
     token	String	是	身份令牌，校验验证码成功后获取
     friendDyID	String	是	要查询的段友ID

     */


    private String deviceID;
    private String dyID;
    private String token;
    private String friendDyID;

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

    public String getFriendDyID() {
        return friendDyID;
    }

    public void setFriendDyID(String friendDyID) {
        this.friendDyID = friendDyID;
    }
}
