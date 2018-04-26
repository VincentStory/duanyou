package com.duanyou.lavimao.proj_duanyou.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.VerticalVpAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetContentUnreviewedRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentUnreviewedResponse;
import com.duanyou.lavimao.proj_duanyou.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.MyTwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;


/**
 * 审核fragment
 */
public class CheckFragment extends BaseFragment {

    //@BindView(R.id.refreshlayout)
    //MyTwinklingRefreshLayout refreshlayout;
    @BindView(R.id.vertical_vp)
    VerticalViewPager verticalVp;

    private VerticalVpAdapter adapter;
    private List<GetContentUnreviewedResponse.DyContextsBean> mList = new ArrayList<>();
    private boolean refreshTag = true;

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {
        initTitle(view);
    }

    @Override
    public void startInvoke(View view) {
        initView();
        getContentUnreviewed();

    }

    private void initView() {
        /*refreshlayout.setLoadEnable(false);
        refreshlayout.setHeaderView(new SinaRefreshView(getActivity()));
        refreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshTag = true;
                getContentUnreviewed();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshTag = false;
                getContentUnreviewed();
            }
        });*/
        adapter = new VerticalVpAdapter(getChildFragmentManager(), mList);
        verticalVp.setAdapter(adapter);
        verticalVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initTitle(View view) {
        setTitle(view, "审核");
        setLeftImg(view, R.drawable.icon_logo);
        setRightIv(view, R.drawable.check);

    }

    public void getContentUnreviewed() {
        GetContentUnreviewedRequest request = new GetContentUnreviewedRequest();
        request.setDyID(UserInfo.getDyId());

        NetUtil.getData(Api.getContentUnreviewed, getActivity(), request, new ResultCallback() {
            @Override
            public void onResult(String jsonResult) {
                GetContentUnreviewedResponse response = JSON.parseObject(jsonResult, GetContentUnreviewedResponse.class);
                if (null != response) {
                    if ("0".equals(response.getRespCode())) {
                        if (refreshTag) {
                            mList.clear();
                           // refreshlayout.finishRefreshing();
                        } else {
                          //  refreshlayout.finishLoadmore();
                        }
                        mList.addAll(response.getDyContexts());
                        adapter.notifyDataSetChanged();
                        if (mList.size() == 0 && !refreshTag) {
                            ToastUtils.showShort(getResources().getString(R.string.no_more));
                        }

                        if (mList.size() == 1) {
                           // refreshlayout.setEnableRefresh(true);
                           // refreshlayout.setEnableLoadmore(true);
                        }
                    } else {
                        ToastUtils.showShort(response.getRespMessage());
                    }
                }
            }

            @Override
            public void onError(Exception ex) {

            }
        });
    }


}
