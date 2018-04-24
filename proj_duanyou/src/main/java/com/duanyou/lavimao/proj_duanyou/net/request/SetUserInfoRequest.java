package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class SetUserInfoRequest extends BaseRequest {

    /**
     参数名
     数据类型
     是否必传
     备注
     dyID
     String
     是
     段友ID
     deviceID
     String
     是
     设备ID
     token
     String
     是
     身份令牌，校验验证码成功后获取
     nickName
     String
     否
     昵称
     signature
     String
     否
     签名
     gender
     String
     否
     性别
     maritalStatus
     String
     否
     婚姻状态
     birthday
     String
     否
     生日
     occupation
     String
     否
     职业
     region
     String
     否
     地区
     currentLocation
     String
     否
     目前所在地

     */

    private String token;
    private String deviceID;
    private String dyID;
    private String signature;
    private String gender;
    private String maritalStatus;
    private String nickName;
    private String birthday;
    private String occupation;
    private String region;
    private String currentLocation;

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
