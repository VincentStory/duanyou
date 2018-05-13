package com.duanyou.lavimao.proj_duanyou.net.response;

import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class GetContentResponse2 extends BaseResponse implements Serializable {

    private List<DyContextsBean> dyContents;

    public List<DyContextsBean> getDyContents() {
        return dyContents;
    }

    public void setDyContents(List<DyContextsBean> dyContents) {
        this.dyContents = dyContents;
    }


}
