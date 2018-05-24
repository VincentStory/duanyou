package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class GetContentResponse extends BaseResponse implements Serializable {

    private List<DyContextsBean> dyContexts;
    private String haveMore;

    public String getHaveMore() {
        return haveMore;
    }

    public void setHaveMore(String haveMore) {
        this.haveMore = haveMore;
    }

    public List<DyContextsBean> getDyContexts() {
        return dyContexts;
    }

    public void setDyContexts(List<DyContextsBean> dyContexts) {
        this.dyContexts = dyContexts;
    }


}
