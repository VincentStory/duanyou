package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

/**
 * Created by luojialun on 2018/4/23.
 */

public class UserCommentRequest extends BaseRequest {

    private int contextID;
    private String commentText;
    private String replyToDyID;

    public int getContextID() {
        return contextID;
    }

    public void setContextID(int contextID) {
        this.contextID = contextID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getReplyToDyID() {
        return replyToDyID;
    }

    public void setReplyToDyID(String replyToDyID) {
        this.replyToDyID = replyToDyID;
    }
}
