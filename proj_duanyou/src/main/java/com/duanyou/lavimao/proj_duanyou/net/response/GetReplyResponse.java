package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.util.List;

/**
 * Created by luojialun on 2018/5/21.
 */

public class GetReplyResponse extends BaseResponse {
    private String replyHaveMore;
    private List<BaseReply> reply;

    public String getReplyHaveMore() {
        return replyHaveMore;
    }

    public void setReplyHaveMore(String replyHaveMore) {
        this.replyHaveMore = replyHaveMore;
    }

    public List<BaseReply> getReply() {
        return reply;
    }

    public void setReply(List<BaseReply> reply) {
        this.reply = reply;
    }
}
