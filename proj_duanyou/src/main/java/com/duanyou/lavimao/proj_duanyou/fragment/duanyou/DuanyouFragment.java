package com.duanyou.lavimao.proj_duanyou.fragment.duanyou;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.FriendsRequestActivity;
import com.duanyou.lavimao.proj_duanyou.activity.GroupListActivity;
import com.duanyou.lavimao.proj_duanyou.activity.MainActivity;
import com.duanyou.lavimao.proj_duanyou.adapter.DuanyouAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.bean.DuanyouBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DuanyouFragment extends BaseFragment {

    @BindView(R.id.duanyou_rv)
    RecyclerView duanyouRv;

    private List<DuanyouBean> mList = new ArrayList<>();

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_duanyou, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {
        DuanyouBean duanyouBean1 = new DuanyouBean();
        duanyouBean1.setPhoto(R.drawable.newfriends);
        duanyouBean1.setName("新的段友");
        DuanyouBean duanyouBean2 = new DuanyouBean();
        duanyouBean2.setPhoto(R.drawable.groupchat);
        duanyouBean2.setName("群聊");
        mList.add(duanyouBean1);
        mList.add(duanyouBean2);

        DuanyouAdapter adapter = new DuanyouAdapter(getActivity(), R.layout.item_duanyou, mList);
        duanyouRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        duanyouRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (0 == position) {
                    ((MainActivity) getActivity()).jumpTo(FriendsRequestActivity.class);
                } else if (1 == position) {
                    ((MainActivity) getActivity()).jumpTo(GroupListActivity.class);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void startInvoke(View view) {

    }
}
