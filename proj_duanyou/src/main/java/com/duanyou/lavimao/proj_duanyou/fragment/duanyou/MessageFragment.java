package com.duanyou.lavimao.proj_duanyou.fragment.duanyou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.MainActivity;
import com.duanyou.lavimao.proj_duanyou.activity.NearbyActivity;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageFragment extends BaseFragment {

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {

    }

    @Override
    public void startInvoke(View view) {

    }

    @OnClick({R.id.search_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_tv:
                ((MainActivity) getActivity()).jumpTo(NearbyActivity.class);
                break;
        }
    }
}
