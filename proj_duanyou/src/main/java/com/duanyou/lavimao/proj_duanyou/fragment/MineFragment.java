package com.duanyou.lavimao.proj_duanyou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.LoginActivity;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {


    @BindView(R.id.login_register_tv)
    TextView loginTv;

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {


    }

    @Override
    public void startInvoke(View view) {

    }


    @OnClick(R.id.login_register_tv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register_tv:
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
