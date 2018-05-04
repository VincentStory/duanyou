package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.util.List;

/**
 * Created by czxyl171151 on 2018/5/4.
 */

public class NearbyPeopleResponse extends BaseResponse {


    private List<UserInfo> userInfo ;

    public void setUserInfo(List<UserInfo> userInfo){
        this.userInfo = userInfo;
    }
    public List<UserInfo> getUserInfo(){
        return this.userInfo;
    }

    public class UserInfo {
        private String distance;

        private String dyID;

        private String headPortraitUrl;

        private String latelyTime;

        private String nickName;

        private String sex;

        public void setDistance(String distance){
            this.distance = distance;
        }
        public String getDistance(){
            return this.distance;
        }
        public void setDyID(String dyID){
            this.dyID = dyID;
        }
        public String getDyID(){
            return this.dyID;
        }
        public void setHeadPortraitUrl(String headPortraitUrl){
            this.headPortraitUrl = headPortraitUrl;
        }
        public String getHeadPortraitUrl(){
            return this.headPortraitUrl;
        }
        public void setLatelyTime(String latelyTime){
            this.latelyTime = latelyTime;
        }
        public String getLatelyTime(){
            return this.latelyTime;
        }
        public void setNickName(String nickName){
            this.nickName = nickName;
        }
        public String getNickName(){
            return this.nickName;
        }
        public void setSex(String sex){
            this.sex = sex;
        }
        public String getSex(){
            return this.sex;
        }
    }
}
