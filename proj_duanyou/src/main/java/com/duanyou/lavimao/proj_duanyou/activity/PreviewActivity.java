package com.duanyou.lavimao.proj_duanyou.activity;

import android.view.View;
import android.widget.ImageView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.util.MCache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预览图片
 */
public class PreviewActivity extends BaseActivity {

    @BindView(R.id.preview_iv)
    ImageView previewIv;

    @Override
    public void setView() {
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initParams();

    }

    private void initParams() {
        if (null != MCache.previewBitmap) {
            previewIv.setImageBitmap(MCache.previewBitmap);
        }

    }

    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.back_iv, R.id.complete_ll})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.complete_ll:
            case R.id.back_iv:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MCache.previewBitmap = null;
    }

}
