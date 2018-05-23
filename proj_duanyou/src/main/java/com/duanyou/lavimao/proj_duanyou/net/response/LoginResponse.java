package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.util.List;

public class LoginResponse extends BaseResponse {

    /**
     * 参数名
     数据类型
     是否必传
     备注
     token
     String
     是
     身份令牌，验证成功后返回
     nickName
     String
     是
     用户昵称
     headPortraitUrl
     String
     是
     头像URL
     fansNum
     Int
     是
     粉丝数量
     followNum
     Int
     是
     关注数
     gender
     String
     是
     性别
     currentLocation
     String
     是
     当前所在地理位置
     signature
     String
     是
     个性签名
     backgroundWall
     String
     是
     背景图URL
     integral
     Int
     是
     积分
     region
     String
     是
     出生地
     occupation
     String
     是
     职业
     birthday
     String
     是
     生日
     maritalStatus
     String
     是
     是否已婚
     dyID
     String
     是
     段友号，与手机号必传其一
     mobilePhone
     String
     是
     手机号，与段友号必传其一

     */


    private String mobilePhone;
    private String deviceID;
    private String dyID;


    private String thirdPartyID;
    private String nickName;
    private String gender;
    private String fansNum;
    private String followNum;
    private String currentLocation;
    private String headPortraitUrl;
    private String signature;
    private String backgroundWall;
    private String integral;
    private String region;
    private String occupation;
    private String birthday;
    private String maritalStatus;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getDyID() {
        return dyID;
    }

    public void setDyID(String dyID) {
        this.dyID = dyID;
    }

    public String getThirdPartyID() {
        return thirdPartyID;
    }

    public void setThirdPartyID(String thirdPartyID) {
        this.thirdPartyID = thirdPartyID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBackgroundWall() {
        return backgroundWall;
    }

    public void setBackgroundWall(String backgroundWall) {
        this.backgroundWall = backgroundWall;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
