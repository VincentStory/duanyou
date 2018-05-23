package com.duanyou.lavimao.proj_duanyou.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TagFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.UserInfoResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;

import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.BottomPopupWindow;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2018/5/5.
 */

public class PeopleInfoActivity extends BaseActivity {
    private String targetId;
    private String beginContentId;
    private String followSts;

    @BindView(R.id.nickname_tv)
    TextView nicknameTv;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.fansnum_tv)
    TextView fansnumTv;
    @BindView(R.id.integral_tv)
    TextView integralTv;
    @BindView(R.id.follow_tv)
    TextView followTv;
    @BindView(R.id.add_friend_tv)
    TextView friendTv;
    @BindView(R.id.headimage_iv)
    RoundedImageView headIv;
    @BindView(R.id.bg_iv)
    ImageView bgIv;

    @Override
    public void setView() {
        setContentView(R.layout.activity_people_info);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        setTitle("");
        targetId = getIntent().getStringExtra(Constants.targetDyID);
        beginContentId = getIntent().getStringExtra(Constants.beginContentID);
    }

    @Override
    public void startInvoke() {

        //必需继承FragmentActivity,嵌套fragment只需要这行代码
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, TagFragment.newInstance("5", targetId, beginContentId)).commitAllowingStateLoss();

        getUserInfo(PeopleInfoActivity.this, targetId, new GetContentResult() {
            @Override
            public void success(String json) {
                UserInfoResponse response = JSON.parseObject(json, UserInfoResponse.class);
                if (response.getUserInfo().size() > 0) {
                    UserInfoResponse.UserInfo userInfo = response.getUserInfo().get(0);
                    String picurl = userInfo.getHeadPortraitUrl();
                    if (picurl != null) {
                        if (!picurl.isEmpty())
                            Glide.with(PeopleInfoActivity.this).load(picurl).into(headIv);
                    }
                    if (userInfo.getBackgroundWall() != null) {
                        if (!userInfo.getBackgroundWall().isEmpty())
                            Glide.with(PeopleInfoActivity.this).load(userInfo.getBackgroundWall()).into(bgIv);

                    }
                    nicknameTv.setText(userInfo.getNickName());
                    locationTv.setText(userInfo.getCurrentLocation());
                    integralTv.setText("积分：" + userInfo.getIntegral());
                    fansnumTv.setText("粉丝：" + userInfo.getFansNum());
                    followSts = userInfo.getFollowSts();
                    setTitle(userInfo.getNickName());
                    if (followSts.equals("1")) {
                        followTv.setText("已关注");
                    }

                }
            }

            @Override
            public void error(Exception ex) {

            }
        });
    }


    @OnClick({R.id.iv_left, R.id.follow_tv, R.id.add_friend_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.follow_tv:
                if (followSts.equals("1")) {
                    userOperation("5", "7", Integer.parseInt(targetId), new GetContentResult() {
                        @Override
                        public void success(String json) {
                            followTv.setText("关注");
                        }

                        @Override
                        public void error(Exception ex) {
                            ToastUtils.showShort(ex.toString());

                        }
                    });
                } else {

                    userOperation("5", "6", Integer.parseInt(targetId), new GetContentResult() {
                        @Override
                        public void success(String json) {
                            followTv.setText("已关注");
                        }

                        @Override
                        public void error(Exception ex) {
                            ToastUtils.showShort(ex.toString());

                        }
                    });
                }
                break;
            case R.id.add_friend_tv:

                break;

            default:
                break;
        }
    }


    /**
     * 用户操作
     *
     * @param type     1-段子 2-评论 3-回复 4-审核 5-用户
     * @param operator 1-点赞 2-点踩 3-举报 4-视频播放 5-转发 6-收藏/关注 7-取消收藏/取消关注 8-删除（只能删除自己的） 9-用户反馈（type为9）
     */
    public void userOperation(String type, String operator, int dyId, final GetContentResult result) {
        if (UserInfo.getLoginState()) {
            UserOperationRequest request = new UserOperationRequest();
            request.setDyID(UserInfo.getDyId());
            request.setDeviceID(UserInfo.getDeviceId());
            request.setToken(UserInfo.getToken());
            List<Integer> mList = new ArrayList<>();
            mList.add(dyId);
            request.setDyDataID(mList);
            request.setType(type);
            request.setOperator(operator);
//            request.setRemark(remark);
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
