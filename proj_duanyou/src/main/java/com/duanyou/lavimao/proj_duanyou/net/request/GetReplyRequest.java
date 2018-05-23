package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

/**
 * Created by luojialun on 2018/5/21.
 */

public class GetReplyRequest extends BaseRequest {
    private int dyCommentID;
    private int benginReplyID;

    public int getDyCommentID() {
        return dyCommentID;
    }

    public void setDyCommentID(int dyCommentID) {
        this.dyCommentID = dyCommentID;
    }

    public int getBenginReplyID() {
        return benginReplyID;
    }

    public void setBenginReplyID(int benginReplyID) {
        this.benginReplyID = benginReplyID;
    }
}
