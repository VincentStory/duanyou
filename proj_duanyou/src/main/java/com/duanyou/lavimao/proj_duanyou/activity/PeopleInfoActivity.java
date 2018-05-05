package com.duanyou.lavimao.proj_duanyou.activity;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TagFragment;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.Contents;

import butterknife.ButterKnife;

/**
 * Created by vincent on 2018/5/5.
 */

public class PeopleInfoActivity extends BaseActivity {
    private String targetId;
    private String beginContentId;

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
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, TagFragment.newInstance("5", targetId,beginContentId)).commitAllowingStateLoss();
    }


}
