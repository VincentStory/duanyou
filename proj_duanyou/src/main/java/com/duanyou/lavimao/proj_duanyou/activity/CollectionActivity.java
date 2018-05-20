package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.FansAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TagFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.PeopleNearbyRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.DyContextsBean;
import com.duanyou.lavimao.proj_duanyou.net.response.GetFollowsBean;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectionActivity extends BaseActivity {
    private int type;
    private int operateType = 1;
    private TagFragment tagFragment;

    @BindView(R.id.tv_delete)
    TextView tv_delete;

    @Override
    public void setView() {
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", 0);
        if (type == Contents.COLLECTION_TYPE) {
            setTitle("收藏");

        } else {
            setTitle("投稿");
        }

        setRightTitle("编辑");
        tagFragment = TagFragment.newInstance(type + "", UserInfo.getDyId(), false);

//必需继承FragmentActivity,嵌套fragment只需要这行代码
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, tagFragment).commitAllowingStateLoss();

    }


    @Override
    public void startInvoke() {

    }


    @OnClick({R.id.iv_left, R.id.right_tv, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.right_tv:
                if (operateType == 1) {//编辑
                    operateType = 2;
                    setRightTitle("取消");
                    tagFragment.setIsEdit(true);
                    tv_delete.setVisibility(View.VISIBLE);

                } else if (operateType == 2) {//取消
                    operateType = 1;
                    setRightTitle("编辑");
                    tagFragment.setIsEdit(false);
                    tv_delete.setVisibility(View.GONE);

                }
                break;

            case R.id.tv_delete:
                List<DyContextsBean> bean = tagFragment.getSelectedList();
                Log.i(TAG, "onClick: " + bean.toString());
                userOperation("1", "8", "", bean, new GetContentResult() {
                    @Override
                    public void success(String json) {

                        tagFragment.refreshData();


                        operateType = 1;
                        setRightTitle("编辑");
                        tagFragment.setIsEdit(false);
                        tv_delete.setVisibility(View.GONE);
                    }

                    @Override
                    public void error(Exception ex) {
                        ToastUtils.showShort(ex.toString());
                    }
                });
                break;

        }
    }


    /**
     * 用户操作
     *
     * @param type     1-段子 2-评论 3-回复 4-审核 5-用户
     * @param operator 1-点赞 2-点踩 3-举报 4-视频播放 5-转发 6-收藏/关注 7-取消收藏/取消关注 8-删除（只能删除自己的） 9-用户反馈（type为9）
     * @param remark
     */
    public void userOperation(String type, String operator, String remark, List<DyContextsBean> list, final GetContentResult result) {
        if (UserInfo.getLoginState()) {
            UserOperationRequest request = new UserOperationRequest();
            request.setDyID(UserInfo.getDyId());
            request.setDeviceID(UserInfo.getDeviceId());
            request.setToken(UserInfo.getToken());
            List<Integer> mList = new ArrayList<>();
            for (DyContextsBean bean : list) {
                mList.add(bean.getDyContextID());
            }

            request.setDyDataID(mList);
            request.setType(type);
            request.setOperator(operator);
            request.setRemark(remark);
            NetUtil.getData(Api.userOperation, this, request, new ResultCallback() {
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
            jumpTo(LoginActivity.class);
        }
    }

}
