package com.duanyou.lavimao.proj_duanyou.Event;

import com.duanyou.lavimao.proj_duanyou.net.response.GetContentUnreviewedResponse;

import java.util.List;

/**
 * Created by luojialun on 2018/4/27.
 */

public class UpdateFragemntEvent {
    private List<GetContentUnreviewedResponse.DyContextsBean> mList;

    public List<GetContentUnreviewedResponse.DyContextsBean> getmList() {
        return mList;
    }

    public void setmList(List<GetContentUnreviewedResponse.DyContextsBean> mList) {
        this.mList = mList;
    }

    public UpdateFragemntEvent(List<GetContentUnreviewedResponse.DyContextsBean> mList) {
        this.mList = mList;
    }
}
