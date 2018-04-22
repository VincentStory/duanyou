package com.duanyou.lavimao.proj_duanyou.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.DuanziDetailsActivity;
import com.duanyou.lavimao.proj_duanyou.adapter.MainContentAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

/**
 * 标签feagment
 * Created by luojialun on 2018/4/21.
 */

public class TagFragment extends BaseFragment {

    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;

    private String type; //内容类型。0-精选，1-热吧，2-段子，3-图片，4-视频
    private boolean refreshTag = false;  //下拉刷新  true   加载更多  false
    private List<GetContentResponse.DyContextsBean> mList;
    private MainContentAdapter mAdapter;

    public static TagFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.type, type);
        TagFragment fragment = new TagFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {

    }

    @Override
    public void startInvoke(View view) {
        initParam();
        initView();
        initList();
        getContent();
    }

    private void initView() {
        refreshLayout.setHeaderView(new SinaRefreshView(getActivity()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshTag = true;
                getContent();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshTag = false;
                getContent();
            }
        });

    }

    private void initParam() {
        type = getArguments().getString(Constants.type);
    }

    /**
     * 初始化列表
     */
    private void initList() {
        if (mAdapter == null) {
            mList = new ArrayList<>();
            mAdapter = new MainContentAdapter(getActivity(), mList, R.layout.item_duanyouxiu);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), DuanziDetailsActivity.class);
                    intent.putExtra(Constants.CLICK_BEAN, mList.get(position));
                    startActivity(intent);
                }
            });
        }

    }

    private void getContent() {
        if (!TextUtils.isEmpty(type)) {
            getContent(getActivity(), type, new GetContentResult() {
                @Override
                public void success(String json) {
                    GetContentResponse response = JSON.parseObject(json, GetContentResponse.class);
                    if (refreshTag) {
                        mList.clear();
                        refreshLayout.finishRefreshing();
                    } else {
                        refreshLayout.finishLoadmore();
                    }

                    if (null != response) {
                        if ("0".equals(response.getRespCode())) {
                            if (response.getDyContexts().size() > 0) {
                                mList.clear();
                                mList.addAll(response.getDyContexts());
                                mAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.showShort(getActivity().getResources().getString(R.string.no_more));
                            }
                        } else {
                            ToastUtils.showShort(response.getRespMessage());
                        }
                    }
                }

                @Override
                public void error(Exception ex) {

                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
