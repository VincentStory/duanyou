package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luojialun on 2018/4/21.
 */

public class GetCommentResponse extends BaseResponse {
    private String commentHaveMore;
    private List<CommentsNewBean> commentsNew;
    private List<CommentsNewBean> commentsHot;

    public String getCommentHaveMore() {
        return commentHaveMore;
    }

    public void setCommentHaveMore(String commentHaveMore) {
        this.commentHaveMore = commentHaveMore;
    }

    public List<CommentsNewBean> getCommentsNew() {
        return commentsNew;
    }

    public void setCommentsNew(List<CommentsNewBean> commentsNew) {
        this.commentsNew = commentsNew;
    }

    public List<CommentsNewBean> getCommentsHot() {
        return commentsHot;
    }

    public void setCommentsHot(List<CommentsNewBean> commentsHot) {
        this.commentsHot = commentsHot;
    }

    public static class CommentsNewBean implements Serializable {
        private String nickName;
        private String headPortraitUrl;
        private int commentID;
        private String commentDyID;
        private String uploadDate;
        private String contextText;
        private int praiseNum;
        private int replyNum;
        private boolean replyHaveMore;
        private List<BaseReply> reply;

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

        public String getCommentDyID() {
            return commentDyID;
        }

        public void setCommentDyID(String commentDyID) {
            this.commentDyID = commentDyID;
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

        public int getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(int replyNum) {
            this.replyNum = replyNum;
        }

        public boolean isReplyHaveMore() {
            return replyHaveMore;
        }

        public void setReplyHaveMore(boolean replyHaveMore) {
            this.replyHaveMore = replyHaveMore;
        }

        public List<BaseReply> getReply() {
            return reply;
        }

        public void setReply(List<BaseReply> reply) {
            this.reply = reply;
        }
    }

}
