package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.util.List;

public class GetContentResponse extends BaseResponse {

    private List<DyContextsBean> dyContexts;

    public List<DyContextsBean> getDyContexts() {
        return dyContexts;
    }

    public void setDyContexts(List<DyContextsBean> dyContexts) {
        this.dyContexts = dyContexts;
    }

    public static class DyContextsBean {

        /**
         * commentNum : 1
         * contextText :
         * contextType : 4
         * contextUrl : http://resource.dyouclub.com/video/3333333320223241.mp4
         * duration : 17
         * dyContextID : 1191
         * fileSize : 1376
         * headPortraitUrl : http://resource.dyouclub.com/headPortrait/e591bea354475ab0!200x200.jpg
         * isLike : 0
         * isSelected : 0
         * nickName : ☆孤独☞患者℡ζ
         * pixelHeight : 368
         * pixelWidth : 640
         * praiseNum : 0
         * publisherDyID : 3001741
         * sex :
         * shareNum : 0
         * shareUrl : http://www.dyouclub.com/dist/index.html#/dyContentID=1191
         * trampleNum : 0
         * uploadDate : 2018-04-17 18:09:14
         * videoDisplay : http://resource.dyouclub.com/picture/3333333320223241.jpg
         * videoPlayCount : 0
         */

        private int commentNum;
        private String contextText;
        private String contextType;
        private String contextUrl;
        private String duration;
        private int dyContextID;
        private String fileSize;
        private String headPortraitUrl;
        private String isLike;
        private String isSelected;
        private String nickName;
        private int pixelHeight;
        private int pixelWidth;
        private int praiseNum;
        private String publisherDyID;
        private String sex;
        private int shareNum;
        private String shareUrl;
        private int trampleNum;
        private String uploadDate;
        private String videoDisplay;
        private int videoPlayCount;

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getContextText() {
            return contextText;
        }

        public void setContextText(String contextText) {
            this.contextText = contextText;
        }

        public String getContextType() {
            return contextType;
        }

        public void setContextType(String contextType) {
            this.contextType = contextType;
        }

        public String getContextUrl() {
            return contextUrl;
        }

        public void setContextUrl(String contextUrl) {
            this.contextUrl = contextUrl;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public int getDyContextID() {
            return dyContextID;
        }

        public void setDyContextID(int dyContextID) {
            this.dyContextID = dyContextID;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getHeadPortraitUrl() {
            return headPortraitUrl;
        }

        public void setHeadPortraitUrl(String headPortraitUrl) {
            this.headPortraitUrl = headPortraitUrl;
        }

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }

        public String getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(String isSelected) {
            this.isSelected = isSelected;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getPixelHeight() {
            return pixelHeight;
        }

        public void setPixelHeight(int pixelHeight) {
            this.pixelHeight = pixelHeight;
        }

        public int getPixelWidth() {
            return pixelWidth;
        }

        public void setPixelWidth(int pixelWidth) {
            this.pixelWidth = pixelWidth;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public String getPublisherDyID() {
            return publisherDyID;
        }

        public void setPublisherDyID(String publisherDyID) {
            this.publisherDyID = publisherDyID;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public int getTrampleNum() {
            return trampleNum;
        }

        public void setTrampleNum(int trampleNum) {
            this.trampleNum = trampleNum;
        }

        public String getUploadDate() {
            return uploadDate;
        }

        public void setUploadDate(String uploadDate) {
            this.uploadDate = uploadDate;
        }

        public String getVideoDisplay() {
            return videoDisplay;
        }

        public void setVideoDisplay(String videoDisplay) {
            this.videoDisplay = videoDisplay;
        }

        public int getVideoPlayCount() {
            return videoPlayCount;
        }

        public void setVideoPlayCount(int videoPlayCount) {
            this.videoPlayCount = videoPlayCount;
        }
    }
}
