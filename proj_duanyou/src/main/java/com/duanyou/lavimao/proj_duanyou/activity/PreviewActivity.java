package com.duanyou.lavimao.proj_duanyou.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.MCache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 预览图片、视频
 */
public class PreviewActivity extends BaseActivity {

    @BindView(R.id.preview_iv)
    ImageView previewIv;
    @BindView(R.id.play_iv)
    ImageView playIv;
    @BindView(R.id.videoView)
    VideoView videoView;

    private String type;
    private String filePath;

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
        type = getIntent().getStringExtra(Constants.type);
        filePath = getIntent().getStringExtra(Constants.FILE_PATH);

        if ("3".equals(type)) {
            displayImage(filePath);
        } else {
            Bitmap bitmap = getVideoThumbnail(filePath);
            previewIv.setImageBitmap(bitmap);
            videoView.setMediaController(new MediaController(this));
            videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
            Uri uri = Uri.parse(filePath);
            videoView.setVideoURI(uri);
        }
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            playIv.setVisibility(View.VISIBLE);
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 1;
            opts.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opts);
            playIv.setVisibility(View.GONE);
            previewIv.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取视频文件的缩略图
     *
     * @param filePath
     * @return
     */
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap b = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            b = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.back_iv, R.id.complete_ll, R.id.play_iv})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.complete_ll:
            case R.id.back_iv:
                finish();
                break;
            case R.id.play_iv:
                previewIv.setVisibility(View.GONE);
                playIv.setVisibility(View.GONE);
                videoView.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
