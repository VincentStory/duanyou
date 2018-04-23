package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

/**
 * Created by luojialun on 2018/4/23.
 */

public class UserReplyRequest extends BaseRequest {
    private int commentID;
    private String replyToDyID;
    private String replyText;
    private String contentID;

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getReplyToDyID() {
        return replyToDyID;
    }

    public void setReplyToDyID(String replyToDyID) {
        this.replyToDyID = replyToDyID;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }
}
