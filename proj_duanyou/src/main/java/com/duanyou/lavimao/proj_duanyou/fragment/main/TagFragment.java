package com.duanyou.lavimao.proj_duanyou.fragment.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.DuanziDetailsActivity;
import com.duanyou.lavimao.proj_duanyou.activity.LoginActivity;
import com.duanyou.lavimao.proj_duanyou.adapter.MainContentAdapter;
import com.duanyou.lavimao.proj_duanyou.adapter.MainContentAdapter2;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse;
import com.duanyou.lavimao.proj_duanyou.net.response.GetContentResponse2;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.ShareDialog;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

/**
 * 标签feagment
 * Created by luojialun on 2018/4/21.
 */

public class TagFragment extends BaseFragment implements MainContentAdapter.OnItemClickListener, MainContentAdapter2.OnItemClickListener {

    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;

    private String type; //内容类型。0-精选，1-热吧，2-段子，3-图片，4-视频 5-段友段子 ,6-段友圈,7-收藏的段子
    private boolean refreshTag = true;  //下拉刷新  true   加载更多  false
    private List<GetContentResponse.DyContextsBean> mList;
    private List<GetContentResponse2.DyContextsBean> mList2;
    private MainContentAdapter mAdapter;
    private MainContentAdapter2 mAdapter2;
    //    private UMImage imageurl;
    private ShareDialog shareDialog;
    private String targetId;
    private String beginContentId;
    private int page = 1;

    public static TagFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.type, type);
        TagFragment fragment = new TagFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TagFragment newInstance(String type, String targetId, String beginContentId) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.type, type);
        bundle.putString(Constants.targetDyID, targetId);
        bundle.putString(Constants.beginContentID, beginContentId);
        TagFragment fragment = new TagFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TagFragment newInstance(String type, String beginContentId) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.type, type);

        bundle.putString(Constants.beginContentID, beginContentId);
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
                page = 1;
                getContent();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshTag = false;
                if (!TextUtils.isEmpty(type)) {
                    page++;
                    getContent();
                } else {
                    refreshLayout.finishLoadmore();
                }
            }
        });

    }

    private void initParam() {
        type = getArguments().getString(Constants.type);
        targetId = getArguments().getString(Constants.targetDyID);
        beginContentId = getArguments().getString(Constants.beginContentID);
    }

    /**
     * 初始化列表
     */
    private void initList() {
        if (mAdapter == null) {
            if (!TextUtils.isEmpty(type) && type.equals("6") || type.equals("7")) {
                mList2 = new ArrayList<>();
                mAdapter2 = new MainContentAdapter2(getActivity(), mList2, R.layout.item_duanyouxiu);
                mAdapter2.setListener(this);
                listView.setAdapter(mAdapter2);
            } else {
                mList = new ArrayList<>();
                mAdapter = new MainContentAdapter(getActivity(), mList, R.layout.item_duanyouxiu);
                mAdapter.setListener(this);
                listView.setAdapter(mAdapter);
            }

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
        if (!TextUtils.isEmpty(type) && type.equals("5")) {
            getUserUploadContent(getActivity(), targetId, beginContentId, new GetContentResult() {
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
                                mList.addAll(response.getDyContexts());
                                beginContentId = mList.get(mList.size() - 1).getDyContextID() + "";
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
        } else if (!TextUtils.isEmpty(type) && type.equals("6")) {
            getDyCoterie(getActivity(), beginContentId, new GetContentResult() {
                @Override
                public void success(String json) {
                    GetContentResponse2 response = JSON.parseObject(json, GetContentResponse2.class);
                    if (refreshTag) {
                        mList2.clear();
                        refreshLayout.finishRefreshing();
                    } else {
                        refreshLayout.finishLoadmore();
                    }

                    if (null != response) {
                        if ("0".equals(response.getRespCode())) {
                            if (response.getDyContents().size() > 0) {
                                mList2.addAll(response.getDyContents());
                                beginContentId = mList2.get(mList2.size() - 1).getDyContextID() + "";
                                mAdapter2.notifyDataSetChanged();
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
        } else if (!TextUtils.isEmpty(type) && type.equals("7")) {
            getCollection(getActivity(), page, new GetContentResult() {
                @Override
                public void success(String json) {
                    GetContentResponse2 response = JSON.parseObject(json, GetContentResponse2.class);
                    if (refreshTag) {
                        mList2.clear();
                        refreshLayout.finishRefreshing();
                    } else {
                        refreshLayout.finishLoadmore();
                    }

                    if (null != response) {
                        if ("0".equals(response.getRespCode())) {
                            if (response.getDyContents().size() > 0) {
                                mList2.addAll(response.getDyContents());

                                mAdapter2.notifyDataSetChanged();
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
        } else if (!TextUtils.isEmpty(type)) {
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


    @Override
    public void comment(GetContentResponse.DyContextsBean bean) {
        Intent intent = new Intent(getActivity(), DuanziDetailsActivity.class);
        intent.putExtra(Constants.CLICK_BEAN, bean);
        startActivity(intent);
    }

    @Override
    public void share(final GetContentResponse.DyContextsBean bean) {
//        imageurl = new UMImage(getActivity(), R.drawable.ic_launcher);
//        imageurl.setThumb(new UMImage(getActivity(), R.drawable.ic_launcher));
        final UMWeb web = new UMWeb(bean.getShareUrl());
        web.setTitle("段友");
        web.setThumb(new UMImage(getActivity(), R.drawable.ic_launcher));
        web.setDescription(bean.getContextText());

        shareDialog = new ShareDialog(getActivity(), new ShareDialog.onClickListener() {
            @Override
            public void sinaClick() {

                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.SINA.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void qqClick() {
                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.QQ.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void circleClick() {
                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void wechatClick() {
                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void qqspaceClick() {
                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.QZONE.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void copyClick() {
//                ToastUtils.showShort("已复制");

            }

            @Override
            public void collectionClick() {
//                ToastUtils.showShort("已收藏");
                userOperation("1", "6", "", bean, new GetContentResult() {
                    @Override
                    public void success(String json) {
                        ToastUtils.showShort("已收藏");
                    }

                    @Override
                    public void error(Exception ex) {

                    }
                });

            }

            @Override
            public void reportClick() {
//                ToastUtils.showShort("已举报");
            }

            @Override
            public void saveClick() {
//                ToastUtils.showShort("已保存");
            }

            @Override
            public void cancleClick() {
                shareDialog.dismiss();
            }
        }).builder()
                .setGravity(Gravity.BOTTOM);//默认居中，可以不设置
        // ;
        shareDialog.show();

    }


    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

//            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
//            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(getActivity(), "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(getActivity(), "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void comment(GetContentResponse2.DyContextsBean bean) {
        Intent intent = new Intent(getActivity(), DuanziDetailsActivity.class);
        intent.putExtra(Constants.CLICK_BEAN, bean);
        startActivity(intent);
    }

    @Override
    public void share(final GetContentResponse2.DyContextsBean bean) {
        final UMWeb web = new UMWeb(bean.getShareUrl());
        web.setTitle("段友");
        web.setThumb(new UMImage(getActivity(), R.drawable.ic_launcher));
        web.setDescription(bean.getContextText());

        shareDialog = new ShareDialog(getActivity(), new ShareDialog.onClickListener() {
            @Override
            public void sinaClick() {

                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.SINA.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void qqClick() {
                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.QQ.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void circleClick() {
                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void wechatClick() {
                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void qqspaceClick() {
                new ShareAction(getActivity()).withMedia(web).withText(bean.getContextText())
                        .setPlatform(SHARE_MEDIA.QZONE.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
            }

            @Override
            public void copyClick() {
//                ToastUtils.showShort("已复制");

            }

            @Override
            public void collectionClick() {
//                ToastUtils.showShort("已收藏");

                userOperation2("1", "6", "", bean, new GetContentResult() {
                    @Override
                    public void success(String json) {
                        ToastUtils.showShort("已收藏");
                    }

                    @Override
                    public void error(Exception ex) {

                    }
                });

            }

            @Override
            public void reportClick() {
//                ToastUtils.showShort("已举报");
            }

            @Override
            public void saveClick() {
//                ToastUtils.showShort("已保存");
            }

            @Override
            public void cancleClick() {
                shareDialog.dismiss();
            }
        }).builder()
                .setGravity(Gravity.BOTTOM);//默认居中，可以不设置
        // ;
        shareDialog.show();
    }

    /**
     * 用户操作
     *
     * @param type     1-段子 2-评论 3-回复 4-审核 5-用户
     * @param operator 1-点赞 2-点踩 3-举报 4-视频播放 5-转发 6-收藏/关注 7-取消收藏/取消关注 8-删除（只能删除自己的） 9-用户反馈（type为9）
     * @param remark
     */
    public void userOperation(String type, String operator, String remark, GetContentResponse.DyContextsBean item, final GetContentResult result) {
        if (UserInfo.getLoginState()) {
            UserOperationRequest request = new UserOperationRequest();
            request.setDyID(UserInfo.getDyId());
            request.setDeviceID(UserInfo.getDeviceId());
            request.setToken(UserInfo.getToken());
            List<Integer> mList = new ArrayList<>();
            mList.add(item.getDyContextID());
            request.setDyDataID(mList);
            request.setType(type);
            request.setOperator(operator);
            request.setRemark(remark);
            NetUtil.getData(Api.userOperation, getActivity(), request, new ResultCallback() {
                @Override
                public void onResult(String jsonResult) {
                    result.success(jsonResult);
                }

                @Override
                public void onError(Exception ex) {
                    result.error(ex);
                }
            });
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
        }
    }
    /**
     * 用户操作
     *
     * @param type     1-段子 2-评论 3-回复 4-审核 5-用户
     * @param operator 1-点赞 2-点踩 3-举报 4-视频播放 5-转发 6-收藏/关注 7-取消收藏/取消关注 8-删除（只能删除自己的） 9-用户反馈（type为9）
     * @param remark
     */
    public void userOperation2(String type, String operator, String remark, GetContentResponse2.DyContextsBean item, final GetContentResult result) {
        if (UserInfo.getLoginState()) {
            UserOperationRequest request = new UserOperationRequest();
            request.setDyID(UserInfo.getDyId());
            request.setDeviceID(UserInfo.getDeviceId());
            request.setToken(UserInfo.getToken());
            List<Integer> mList = new ArrayList<>();
            mList.add(item.getDyContextID());
            request.setDyDataID(mList);
            request.setType(type);
            request.setOperator(operator);
            request.setRemark(remark);
            NetUtil.getData(Api.userOperation, getActivity(), request, new ResultCallback() {
                @Override
                public void onResult(String jsonResult) {
                    result.success(jsonResult);
                }

                @Override
                public void onError(Exception ex) {
                    result.error(ex);
                }
            });
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
        }
    }

}
