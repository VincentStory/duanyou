package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luojialun on 2018/4/25.
 */

public class GetContentUnreviewedResponse extends BaseResponse {

    List<DyContextsBean> dyContexts;

    public List<DyContextsBean> getDyContexts() {
        return dyContexts;
    }

    public void setDyContexts(List<DyContextsBean> dyContexts) {
        this.dyContexts = dyContexts;
    }

    public class DyContextsBean implements Serializable{
        private int dyContextID;
        private String publisherDyID;
        private String uploadDate;
        private String contextText;
        private String contextUrl;
        private String videoDisplay;
        private int pixelWidth;
        private int pixelHeight;
        private String duration;
        private String fileSize;
        private String contextType;
        private int praiseNum;
        private int trampleNum;


        public int getDyContextID() {
            return dyContextID;
        }

        public void setDyContextID(int dyContextID) {
            this.dyContextID = dyContextID;
        }

        public String getPublisherDyID() {
            return publisherDyID;
        }

        public void setPublisherDyID(String publisherDyID) {
            this.publisherDyID = publisherDyID;
        }

        public String getUploadDate() {
            return uploadDate;
        }

        public void setUploadDate(String uploadDate) {
            this.uploadDate = uploadDate;
        }

        public String getContextText() {
            return contextText;
        }

        public void setContextText(String contextText) {
            this.contextText = contextText;
        }

        public String getContextUrl() {
            return contextUrl;
        }

        public void setContextUrl(String contextUrl) {
            this.contextUrl = contextUrl;
        }

        public String getVideoDisplay() {
            return videoDisplay;
        }

        public void setVideoDisplay(String videoDisplay) {
            this.videoDisplay = videoDisplay;
        }

        public int getPixelWidth() {
            return pixelWidth;
        }

        public void setPixelWidth(int pixelWidth) {
            this.pixelWidth = pixelWidth;
        }

        public int getPixelHeight() {
            return pixelHeight;
        }

        public void setPixelHeight(int pixelHeight) {
            this.pixelHeight = pixelHeight;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getContextType() {
            return contextType;
        }

        public void setContextType(String contextType) {
            this.contextType = contextType;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public int getTrampleNum() {
            return trampleNum;
        }

        public void setTrampleNum(int trampleNum) {
            this.trampleNum = trampleNum;
        }
    }

}
