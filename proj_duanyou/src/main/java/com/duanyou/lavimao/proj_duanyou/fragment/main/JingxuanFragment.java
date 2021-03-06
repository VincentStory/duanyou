package com.duanyou.lavimao.proj_duanyou.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.duanyou.lavimao.proj_duanyou.MyApplication;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.MainContentAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.response.DyContextsBean;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JingxuanFragment extends BaseFragment {

    @BindView(R.id.list)
    ListView listView;

    private List<DyContextsBean> mList;
    private MainContentAdapter mAdapter;
    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jingxuan, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {

    }

    @Override
    public void startInvoke(View view) {
            initList();
            getContent();
    }

    /**
     * 初始化列表
     */
    private void initList() {
        if (mAdapter == null) {
            mList = new ArrayList<>();
            mAdapter = new MainContentAdapter(MyApplication.getInstance(), mList, R.layout.item_duanyouxiu);
            listView.setAdapter(mAdapter);
        } else {

        }

    }

    private void getContent() {
        getContent(getActivity(), "4", new GetContentResult() {
            @Override
            public void success(String json) {

                GetContentResponse response = JSON.parseObject(json, GetContentResponse.class);
                mList.clear();
                mList.addAll(response.getDyContexts());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void error(Exception ex) {

            }
        });
    }


}
