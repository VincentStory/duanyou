package com.duanyou.lavimao.proj_duanyou.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.duanyou.lavimao.proj_duanyou.MyApplication;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.DuanziDetailsActivity;
import com.duanyou.lavimao.proj_duanyou.adapter.MainContentAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.xiben.ebs.esbsdk.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuanzhuFragment extends BaseFragment {
    @BindView(R.id.list)
    ListView listView;

    private List<GetContentResponse.DyContextsBean> mList;
    private MainContentAdapter mAdapter;


    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guanzhu, container, false);
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
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getActivity(), DuanziDetailsActivity.class);
                    intent.putExtra(Constants.targetDyID,mList.get(position).getPublisherDyID());
                    intent.putExtra(Constants.beginContentID,mList.get(position).getDyContextID());
                    startActivity(intent);
                }
            });
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
