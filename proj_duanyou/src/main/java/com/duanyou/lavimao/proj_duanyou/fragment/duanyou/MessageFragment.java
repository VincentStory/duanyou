package com.duanyou.lavimao.proj_duanyou.fragment.duanyou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment {


    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }

    @Override
    public void initWidget(View view) {

    }

    @Override
    public void startInvoke(View view) {

    }
}
