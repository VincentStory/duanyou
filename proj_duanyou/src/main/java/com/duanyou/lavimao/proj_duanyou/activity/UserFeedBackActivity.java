package com.duanyou.lavimao.proj_duanyou.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.DyContextsBean;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户反馈
 */
public class UserFeedBackActivity extends BaseActivity {
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.et_contact_info)
    EditText et_contact_info;
    @BindView(R.id.tv_hint)
    TextView tv_hint;

    @Override
    public void setView() {
        setContentView(R.layout.activity_user_feed_back);
        ButterKnife.bind(this);

        //http://39.104.121.233/userageeement/index.html
    }

    @Override
    public void initData() {
        setTitle("用户反馈 ");
        setRightTitle("提交");
    }

    @Override
    public void startInvoke() {
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int size = 500 - s.length();
                tv_hint.setText("您还可以输入" + size + "字");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.iv_left, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.right_tv:
                userOperation("5", "9", et_content.getText().toString() + " 联系方式：" + et_contact_info.getText().toString(), new GetContentResult() {
                    @Override
                    public void success(String json) {
                        ToastUtils.showShort("已提交");
                        finish();
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
    public void userOperation(String type, String operator, String remark, final GetContentResult result) {
//        if (UserInfo.getLoginState()) {
        UserOperationRequest request = new UserOperationRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(UserInfo.getDeviceId());
        request.setToken(UserInfo.getToken());
        List<Integer> mList = new ArrayList<>();
        mList.add(Integer.parseInt(UserInfo.getDyId()));
        request.setDyDataID(mList);
        request.setType(type);
        request.setOperator(operator);
        request.setRemark(remark);
        NetUtil.getData(Api.userOperation, UserFeedBackActivity.this, request, new ResultCallback() {
            @Override
            public void onResult(String jsonResult) {
                result.success(jsonResult);
            }

            @Override
            public void onError(Exception ex) {
                result.error(ex);
            }
        });
//        } else {
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            getActivity().startActivity(intent);
//        }
    }

}
