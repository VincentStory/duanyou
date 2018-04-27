package com.duanyou.lavimao.proj_duanyou.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.duanyou.lavimao.proj_duanyou.Event.UpdateFragemntEvent;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.VerticalVpAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.GetContentUnreviewedRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentUnreviewedResponse;
import com.duanyou.lavimao.proj_duanyou.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.MyViewPager;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 审核fragment
 */
public class CheckFragment extends BaseFragment {

    //@BindView(R.id.refreshlayout)
    //MyTwinklingRefreshLayout refreshlayout;
    @BindView(R.id.vertical_vp)
    MyViewPager verticalVp;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.load_again_tv)
    TextView loadAgainTv;

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
        verticalVp.setOffscreenPageLimit(5);
        verticalVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == mList.size() - 1) {
                    refreshTag = false;
                    getContentUnreviewed();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //第一个item下滑监听
        verticalVp.setUpSlideListener(new MyViewPager.UpSlideListener() {
            @Override
            public void upSlide() {
                if (pb.getVisibility() == View.GONE) {
                    refreshTag = true;
                    getContentUnreviewed();
                }
            }
        });
        loadAgainTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTag = true;
                getContentUnreviewed();
                loadAgainTv.setVisibility(View.GONE);
            }
        });
    }

    private void initTitle(View view) {
        setTitle(view, "审核");
        setLeftImg(view, R.drawable.icon_logo);
        setRightIv(view, R.drawable.check);

    }

    public void getContentUnreviewed() {
        pb.setVisibility(View.VISIBLE);
        GetContentUnreviewedRequest request = new GetContentUnreviewedRequest();
        request.setDyID(UserInfo.getDyId());

        NetUtil.getData(Api.getContentUnreviewed, getActivity(), request, new ResultCallback() {
            @Override
            public void onResult(String jsonResult) {
                pb.setVisibility(View.GONE);
                try {
                    GetContentUnreviewedResponse response = JSON.parseObject(jsonResult, GetContentUnreviewedResponse.class);
                    if (null != response) {
                        if ("0".equals(response.getRespCode())) {
                            if (refreshTag) {
                                mList.clear();
                                mList.addAll(response.getDyContexts());
                                adapter.notifyDataSetChanged();
                                EventBus.getDefault().post(new UpdateFragemntEvent(mList));
                            } else {
                                mList.addAll(response.getDyContexts());
                                adapter.notifyDataSetChanged();
                            }

                            if (mList.size() == 0 && !refreshTag) {
                                ToastUtils.showShort(getResources().getString(R.string.no_more));
                            }

                        } else {
                            ToastUtils.showShort(response.getRespMessage());
                        }
                    }
                } catch (Exception e) {
                    Log.i("TAG", "json解析异常");
                    loadAgainTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Exception ex) {

            }
        });
    }


}
