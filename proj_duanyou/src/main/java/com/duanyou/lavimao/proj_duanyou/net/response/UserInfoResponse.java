package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.util.List;

public class UserInfoResponse extends BaseResponse {
    private String type;

    private List<UserInfo> userInfo;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }


    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }

    public List<UserInfo> getUserInfo() {
        return this.userInfo;
    }


    public class UserInfo {


        private String backgroundWall;

        private String birthday;

        private String currentLocation;

        private String dyID;

        private int fansNum;

        private int followNum;

        private String followSts;

        private String gender;

        private String headPortraitUrl;

        private int integral;

        private String isFriend;

        private String level;

        private String maritalStatus;

        private String mobilePhone;

        private String nickName;

        private String occupation;

        private String region;

        private String signature;

        public void setBackgroundWall(String backgroundWall) {
            this.backgroundWall = backgroundWall;
        }

        public String getBackgroundWall() {
            return this.backgroundWall;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBirthday() {
            return this.birthday;
        }

        public void setCurrentLocation(String currentLocation) {
            this.currentLocation = currentLocation;
        }

        public String getCurrentLocation() {
            return this.currentLocation;
        }

        public void setDyID(String dyID) {
            this.dyID = dyID;
        }

        public String getDyID() {
            return this.dyID;
        }

        public void setFansNum(int fansNum) {
            this.fansNum = fansNum;
        }

        public int getFansNum() {
            return this.fansNum;
        }

        public void setFollowNum(int followNum) {
            this.followNum = followNum;
        }

        public int getFollowNum() {
            return this.followNum;
        }

        public void setFollowSts(String followSts) {
            this.followSts = followSts;
        }

        public String getFollowSts() {
            return this.followSts;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getGender() {
            return this.gender;
        }

        public void setHeadPortraitUrl(String headPortraitUrl) {
            this.headPortraitUrl = headPortraitUrl;
        }

        public String getHeadPortraitUrl() {
            return this.headPortraitUrl;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getIntegral() {
            return this.integral;
        }

        public void setIsFriend(String isFriend) {
            this.isFriend = isFriend;
        }

        public String getIsFriend() {
            return this.isFriend;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevel() {
            return this.level;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getMaritalStatus() {
            return this.maritalStatus;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getMobilePhone() {
            return this.mobilePhone;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getNickName() {
            return this.nickName;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getOccupation() {
            return this.occupation;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRegion() {
            return this.region;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getSignature() {
            return this.signature;
        }
    }
}
