package com.duanyou.lavimao.proj_duanyou.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.ReportAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UserOperationRequest;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 举报
 */
public class ReportActivity extends BaseActivity {

    @BindView(R.id.nav_title)
    TextView titleTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.report_rv)
    RecyclerView reportRv;
    @BindView(R.id.report_et)
    EditText reportEt;

    private List<String> mList = new ArrayList<>();
    private int dyContextID;
    private ReportAdapter reportAdapter;

    @Override
    public void setView() {
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        titleTv.setText("举报");
        rightTv.setText("提交");
    }

    @Override
    public void initData() {
        initParams();

        mList.add("淫秽色情");
        mList.add("广告营销");
        mList.add("侮辱谩骂");
        mList.add("诈骗信息");
        mList.add("抄袭我的东东");
        mList.add("其他问题");

        reportRv.setLayoutManager(new GridLayoutManager(this, 2));
        reportAdapter = new ReportAdapter(this, R.layout.item_report, mList);
        reportRv.setAdapter(reportAdapter);
    }

    private void initParams() {
        dyContextID = getIntent().getIntExtra(Constants.dyContextID, -1);

    }

    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.iv_left, R.id.right_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.right_tv:
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < reportAdapter.getSelectList().size(); i++) {
                    if (reportAdapter.getSelectList().get(i)) {
                        sb.append(mList.get(i) + " ");
                    }
                }
                sb.append(reportEt.getText().toString().trim());
                userOperation("1", "3", sb.toString());
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
     * @param remark
     */
    public void userOperation(String type, String operator, String remark) {
        if (UserInfo.getLoginState()) {
            UserOperationRequest request = new UserOperationRequest();
            request.setDyID(UserInfo.getDyId());
            request.setDeviceID(UserInfo.getDeviceId());
            request.setToken(UserInfo.getToken());
            List<Integer> mList = new ArrayList<>();
            mList.add(dyContextID);
            request.setDyDataID(mList);
            request.setType(type);
            request.setOperator(operator);
            request.setRemark(remark);
            NetUtil.getData(Api.userOperation, this, request, new ResultCallback() {
                @Override
                public void onResult(String jsonResult) {
                    BaseResponse response = JSON.parseObject(jsonResult, BaseResponse.class);
                    if (null != response) {
                        if ("0".equals(response.getRespCode())) {
                            ToastUtils.showShort("提交成功");
                            finish();
                        } else {
                            ToastUtils.showShort(response.getRespMessage());
                        }
                    }
                }

                @Override
                public void onError(Exception ex) {

                }
            });
        } else {
            jumpTo(LoginActivity.class);
        }
    }


}
