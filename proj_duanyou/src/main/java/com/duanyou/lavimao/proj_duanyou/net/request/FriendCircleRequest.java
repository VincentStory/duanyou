package com.duanyou.lavimao.proj_duanyou.net.request;

import com.duanyou.lavimao.proj_duanyou.net.BaseRequest;

public class FriendCircleRequest extends BaseRequest {




    private int page;

    private String beginContentID;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }



    public String getBeginContentID() {
        return beginContentID;
    }

    public void setBeginContentID(String beginContentID) {
        this.beginContentID = beginContentID;
    }
}
