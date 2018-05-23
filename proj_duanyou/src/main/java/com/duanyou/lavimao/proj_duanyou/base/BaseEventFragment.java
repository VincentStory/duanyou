package com.duanyou.lavimao.proj_duanyou.base;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

/**
 *
 * @author temp
 * @date 2017/11/21
 */

abstract public class BaseEventFragment extends BaseFragment{


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }





}
