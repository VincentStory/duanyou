/**
  * Copyright 2018 bejson.com 
  */
package com.duanyou.lavimao.proj_duanyou.net.response;
import java.util.List;

/**
 * Auto-generated: 2018-05-13 13:4:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetFollowsBean {

    private String type;
    private String respCode;
    private String respMessage;
    private List<Fans> fans;
    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setRespCode(String respCode) {
         this.respCode = respCode;
     }
     public String getRespCode() {
         return respCode;
     }

    public void setRespMessage(String respMessage) {
         this.respMessage = respMessage;
     }
     public String getRespMessage() {
         return respMessage;
     }

    public void setFans(List<Fans> fans) {
         this.fans = fans;
     }
     public List<Fans> getFans() {
         return fans;
     }



    /**
     * Auto-generated: 2018-05-13 13:4:47
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Fans {

        private String dyID;
        private String headPortraitUrl;
        private String mutual;
        private String nickName;
        private String sex;
        private String signature;
        public void setDyID(String dyID) {
            this.dyID = dyID;
        }
        public String getDyID() {
            return dyID;
        }

        public void setHeadPortraitUrl(String headPortraitUrl) {
            this.headPortraitUrl = headPortraitUrl;
        }
        public String getHeadPortraitUrl() {
            return headPortraitUrl;
        }

        public void setMutual(String mutual) {
            this.mutual = mutual;
        }
        public String getMutual() {
            return mutual;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
        public String getNickName() {
            return nickName;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
        public String getSex() {
            return sex;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
        public String getSignature() {
            return signature;
        }

    }

}