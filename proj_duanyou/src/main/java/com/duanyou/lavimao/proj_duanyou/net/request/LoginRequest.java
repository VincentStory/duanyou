package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class LoginRequest extends BaseRequest {

    /**
     * 参数名
     * 数据类型
     * 是否必传
     * 备注
     * mobilePhone
     * String
     * 否
     * 手机号，段友登录时与段友号必传其一
     * dyID
     * String
     * 否
     * 段友号，段友登录时与手机号必传其一
     * password
     * String
     * 否
     * 密码
     * deviceID
     * String
     * 否
     * 设备ID
     * loginType
     * String
     * 否
     * 登录类型  1-QQ 2-微信 3-微博
     * thirdPartyID
     * String
     * 否
     * 第三方登录ID，第三方登录时必传
     * nickName
     * String
     * 否
     * 昵称
     * sex
     * String
     * 否
     * 性别
     * thirdHeadUrl
     * String
     * 否
     * 第三方头像
     */

    private String mobilePhone;
    private String deviceID;
    private String dyID;
    private String password;
    private String loginType;
    private String thirdPartyID;
    private String nickName;
    private String sex;
    private String thirdHeadUrl;


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

    @Override
    public String getDyID() {
        return dyID;
    }

    @Override
    public void setDyID(String dyID) {
        this.dyID = dyID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getThirdHeadUrl() {
        return thirdHeadUrl;
    }

    public void setThirdHeadUrl(String thirdHeadUrl) {
        this.thirdHeadUrl = thirdHeadUrl;
    }
}
