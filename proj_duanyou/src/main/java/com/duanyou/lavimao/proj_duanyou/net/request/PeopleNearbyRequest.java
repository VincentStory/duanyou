package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class PeopleNearbyRequest extends BaseRequest {

    /**
     dyID	String	是	段友ID
     deviceID	String	是	设备ID
     token	String	是	身份令牌，校验验证码成功后获取
     longitude	Double	是	经度
     latitude	Double	是	维度
     range	Float	是	区域范围（单位KM）
     page	Int	是	第几页
     sex	String	是	性别


     */


    private String deviceID;
    private String dyID;
    private String token;
    private String sex;
    private Double longitude;
    private Double latitude;
    private Float range;
    private int page;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Float getRange() {
        return range;
    }

    public void setRange(Float range) {
        this.range = range;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
