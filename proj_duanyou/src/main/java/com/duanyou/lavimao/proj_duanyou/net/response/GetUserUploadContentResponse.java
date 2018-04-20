package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.util.List;

/**
 * Created by luojialun on 2018/4/20.
 */

public class GetUserUploadContentResponse extends BaseResponse {
    private List<DetailsDyContextsBean> dyContexts;


    public static class DetailsDyContextsBean extends GetContentResponse.DyContextsBean{
        private String adopt;
        private String chatGroupID;

        public String getAdopt() {
            return adopt;
        }

        public void setAdopt(String adopt) {
            this.adopt = adopt;
        }

        public String getChatGroupID() {
            return chatGroupID;
        }

        public void setChatGroupID(String chatGroupID) {
            this.chatGroupID = chatGroupID;
        }
    }
}
