package com.duanyou.lavimao.proj_duanyou.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;

/**
 * 审核的子item 的 fragment
 */
public class CheckItemFragment extends BaseFragment {

    public static CheckItemFragment newInstance() {

        Bundle args = new Bundle();

        CheckItemFragment fragment = new CheckItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_item, container, false);
    }

    @Override
    public void initWidget(View view) {

    }

    @Override
    public void startInvoke(View view) {
            initParams();
    }

    private void initParams() {

    }

}
