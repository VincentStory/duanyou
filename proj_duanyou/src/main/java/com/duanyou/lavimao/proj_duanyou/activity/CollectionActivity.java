package com.duanyou.lavimao.proj_duanyou.activity;

import android.app.Activity;
import android.content.Intent;
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


    @OnClick({R.id.iv_left, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.right_tv:
                if (operateType == 1) {//编辑
                    operateType = 2;
                    setRightTitle("编辑");
                    tagFragment.setIsEdit(true);


                } else if (operateType == 2) {//取消
                    operateType = 1;
                    setRightTitle("取消");
                    tagFragment.setIsEdit(false);

                }
                break;


        }
    }


}
