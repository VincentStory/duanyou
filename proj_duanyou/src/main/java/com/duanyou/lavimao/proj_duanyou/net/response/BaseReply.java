package com.duanyou.lavimao.proj_duanyou.net.response;

/**
 * Created by luojialun on 2018/4/21.
 */

public class BaseReply {
    private String nickName;
    private String headPortraitUrl;
    private int commentID;
    private int replyID;
    private String replyDyID;
    private String replyToDyID;
    private String uploadDate;
    private String contextText;
    private int praiseNum;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getReplyID() {
        return replyID;
    }

    public void setReplyID(int replyID) {
        this.replyID = replyID;
    }

    public String getReplyDyID() {
        return replyDyID;
    }

    public void setReplyDyID(String replyDyID) {
        this.replyDyID = replyDyID;
    }

    public String getReplyToDyID() {
        return replyToDyID;
    }

    public void setReplyToDyID(String replyToDyID) {
        this.replyToDyID = replyToDyID;
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

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }
}
