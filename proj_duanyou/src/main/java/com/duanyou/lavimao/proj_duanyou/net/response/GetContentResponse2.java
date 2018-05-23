package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class GetContentResponse2 extends BaseResponse implements Serializable {

    private List<DyContextsBean> dyContents;
    private String haveMore;

    public List<DyContextsBean> getDyContents() {
        return dyContents;
    }

    public String getHaveMore() {
        return haveMore;
    }

    public void setHaveMore(String haveMore) {
        this.haveMore = haveMore;
    }

    public void setDyContents(List<DyContextsBean> dyContents) {
        this.dyContents = dyContents;
    }


}
