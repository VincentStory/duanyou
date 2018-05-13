/**
  * Copyright 2018 bejson.com 
  */
package com.duanyou.lavimao.proj_duanyou.net.response;
import java.util.List;

/**
 * Auto-generated: 2018-05-13 18:10:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetInteraction {

    private String type;
    private String respCode;
    private String respMessage;
    private String haveMore;
    private List<Interactions> interactions;
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

    public void setHaveMore(String haveMore) {
         this.haveMore = haveMore;
     }
     public String getHaveMore() {
         return haveMore;
     }

    public void setInteractions(List<Interactions> interactions) {
         this.interactions = interactions;
     }
     public List<Interactions> getInteractions() {
         return interactions;
     }




    /**
     * Auto-generated: 2018-05-13 18:10:4
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Interactions {

        private String commentContent;
        private String commentDyID;
        private String commentID;
        private String contentText;
        private String contentType;
        private String contentUrl;
        private String dyContentID;
        private String dyID;
        private String nickName;
        private String replyToDyID;
        private String replyToNickName;
        private String sex;
        private String uploadDate;
        private String videoDisplay;
        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }
        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentDyID(String commentDyID) {
            this.commentDyID = commentDyID;
        }
        public String getCommentDyID() {
            return commentDyID;
        }

        public void setCommentID(String commentID) {
            this.commentID = commentID;
        }
        public String getCommentID() {
            return commentID;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }
        public String getContentText() {
            return contentText;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }
        public String getContentType() {
            return contentType;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }
        public String getContentUrl() {
            return contentUrl;
        }

        public void setDyContentID(String dyContentID) {
            this.dyContentID = dyContentID;
        }
        public String getDyContentID() {
            return dyContentID;
        }

        public void setDyID(String dyID) {
            this.dyID = dyID;
        }
        public String getDyID() {
            return dyID;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
        public String getNickName() {
            return nickName;
        }

        public void setReplyToDyID(String replyToDyID) {
            this.replyToDyID = replyToDyID;
        }
        public String getReplyToDyID() {
            return replyToDyID;
        }

        public void setReplyToNickName(String replyToNickName) {
            this.replyToNickName = replyToNickName;
        }
        public String getReplyToNickName() {
            return replyToNickName;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
        public String getSex() {
            return sex;
        }

        public void setUploadDate(String uploadDate) {
            this.uploadDate = uploadDate;
        }
        public String getUploadDate() {
            return uploadDate;
        }

        public void setVideoDisplay(String videoDisplay) {
            this.videoDisplay = videoDisplay;
        }
        public String getVideoDisplay() {
            return videoDisplay;
        }

    }

}