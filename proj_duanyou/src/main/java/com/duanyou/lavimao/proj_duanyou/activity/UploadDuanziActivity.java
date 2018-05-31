package com.duanyou.lavimao.proj_duanyou.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.UploadContentRequest;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.DeviceUtils;
import com.duanyou.lavimao.proj_duanyou.util.FileSizeUtil;
import com.duanyou.lavimao.proj_duanyou.util.FileUtils;
import com.duanyou.lavimao.proj_duanyou.util.MCache;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UploadDuanziActivity extends BaseActivity {

    @BindView(R.id.nav_title)
    TextView titleTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.anonymous_iv)
    ImageView anonymousIv;
    @BindView(R.id.pre_show_iv)
    ImageView preShowIv;
    @BindView(R.id.play_iv)
    ImageView playIv;
    @BindView(R.id.pre_show_rl)
    RelativeLayout preShowRl;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.num_tv)
    TextView numTv;
    @BindView(R.id.pb)
    ProgressBar pb;

    private boolean selected = false;
    private String uploadPath = "";
    private BitmapFactory.Options opts;
    private String type = "2";  //段子类型 2-段子，3-图片，4-视频
    private String videoPath;
    private int videoDuration;
    private long videoSize;
    private Bitmap videoThumbnail;
    private String videoThumbnailPath;
    private Bitmap preBitmap;

    @Override
    public void setView() {
        setContentView(R.layout.activity_upload_duanzi);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initTitle();
        initParams();
    }

    private void initParams() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra(Constants.type))) {
            type = getIntent().getStringExtra(Constants.type);
            uploadPath = getIntent().getStringExtra(Constants.FILE_PATH);
            if ("3".equals(type)) {
                displayImage(uploadPath);
            } else if ("4".equals(type)) {
                Bitmap bitmap = getVideoThumbnail(uploadPath);
                FileUtils.saveBmp2Gallery(bitmap, "videoThumbnail");
                preBitmap = bitmap;
                videoThumbnail = bitmap;
                preShowIv.setImageBitmap(bitmap);
                playIv.setVisibility(View.VISIBLE);
                preShowRl.setVisibility(View.VISIBLE);
                videoDuration = getVideoPlayer(uploadPath).getDuration() / 1000;
            }
        }
    }

    private void initTitle() {
        titleTv.setText("段子");
        rightTv.setText("发布");
    }

    @Override
    public void startInvoke() {
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int nums = 500 - s.length();
                numTv.setText("还可以输入" + nums + "字哟！");
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.anonymous_iv, R.id.right_tv, R.id.photo_iv, R.id.delete_iv, R.id.video_iv, R.id.pre_show_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.anonymous_iv:  //匿名
                if (selected) {
                    anonymousIv.setImageResource(R.drawable.anonymous_check);
                } else {
                    anonymousIv.setImageResource(R.drawable.anonymous);
                }
                selected = !selected;
                break;
            case R.id.right_tv:  //发布操作
                upLoadDuanzi(type);
                break;
            case R.id.photo_iv:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.MY_PERMISSIONS_REQUEST_CALL_PHOTO);
                } else {
                    choosePhoto();
                }
                break;
            case R.id.delete_iv:
                preShowIv.setImageBitmap(null);
                preShowRl.setVisibility(View.INVISIBLE);
                type = "2";
                break;

            case R.id.video_iv:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.MY_PERMISSIONS_REQUEST_CALL_VIDEO);
                } else {
                    chooseVideo();
                }
                break;
            case R.id.pre_show_iv:
                Intent intent = new Intent(this, PreviewActivity.class);
                intent.putExtra(Constants.type, type);
                intent.putExtra(Constants.FILE_PATH, uploadPath);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == Constants.MY_PERMISSIONS_REQUEST_CALL_PHOTO) {
                choosePhoto();
            } else if (requestCode == Constants.MY_PERMISSIONS_REQUEST_CALL_VIDEO) {
                chooseVideo();
            }
        } else {
            ToastUtils.showShort("权限拒绝");
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 从相册选取图片
     */
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, Constants.REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * 从相册选取视频
     */
    private void chooseVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constants.REQUEST_CODE_PICK_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_PICK_IMAGE: //从相册中选取图片的请求标志
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {  //4.4及以上的系统使用这个方法处理图片；
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);  //4.4及以下的系统使用这个方法处理图片
                    }
                }
                break;
            case Constants.REQUEST_CODE_PICK_VIDEO:
                if (resultCode == RESULT_OK) {
                    getVideo(data);
                }
                break;
            default:
                break;
        }
    }


    private void getVideo(Intent data) {
        Uri uri = data.getData();
        ContentResolver cr = this.getContentResolver();
        /** 数据库查询操作。
         * 第一个参数 uri：为要查询的数据库+表的名称。
         * 第二个参数 projection ： 要查询的列。
         * 第三个参数 selection ： 查询的条件，相当于SQL where。
         * 第三个参数 selectionArgs ： 查询条件的参数，相当于 ？。
         * 第四个参数 sortOrder ： 结果排序。
         */
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // 视频ID:MediaStore.Audio.Media._ID
                int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                // 视频名称：MediaStore.Audio.Media.TITLE
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                // 视频路径：MediaStore.Audio.Media.DATA
                uploadPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                // 视频时长：MediaStore.Audio.Media.DURATION
                videoDuration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                // 视频大小：MediaStore.Audio.Media.SIZE
                videoSize = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                // 视频缩略图路径：MediaStore.Images.Media.DATA
                videoThumbnailPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                // 缩略图ID:MediaStore.Audio.Media._ID
                int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));

                // 方法一 Thumbnails 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                // 第一个参数为 ContentResolver，第二个参数为视频缩略图ID， 第三个参数kind有两种为：MICRO_KIND和MINI_KIND 字面意思理解为微型和迷你两种缩略模式，前者分辨率更低一些。
                //Bitmap bitmap1 = MediaStore.Video.Thumbnails.getThumbnail(cr, imageId, MediaStore.Video.Thumbnails.MICRO_KIND, null);

                // 方法二 ThumbnailUtils 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                // 第一个参数为 视频/缩略图的位置，第二个依旧是分辨率相关的kind
                videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoThumbnailPath, MediaStore.Video.Thumbnails.MICRO_KIND);
                // 如果追求更好的话可以利用 ThumbnailUtils.extractThumbnail 把缩略图转化为的制定大小
//                 ThumbnailUtils.extractThumbnail(bitmap, width,height ,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

                //setText(tv_VideoPath, R.string.path, videoPath);
                //setText(tv_VideoDuration, R.string.duration, String.valueOf(duration));
                //setText(tv_VideoSize, R.string.size, String.valueOf(size));
                //setText(tv_VideoTitle, R.string.title, title);
                FileUtils.saveBmp2Gallery(videoThumbnail, "videoThumbnail");
                preBitmap = videoThumbnail;
                preShowIv.setImageBitmap(videoThumbnail);
                playIv.setVisibility(View.VISIBLE);
                preShowRl.setVisibility(View.VISIBLE);
                type = "4";
            }
            cursor.close();
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

    /**
     * 4.4以下的系统使用这个方法处理图片
     *
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        uploadPath = imagePath;
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 4.4及以上的系统使用这个方法处理图片
     *
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果document类型的Uri,则通过document来处理
            String docID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docID.split(":")[1];     //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;

                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/piblic_downloads"), Long.valueOf(docID));
                imagePath = getImagePath(contentUri, null);

            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式使用
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取路径即可
            imagePath = uri.getPath();

        }
        uploadPath = imagePath;
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            opts = new BitmapFactory.Options();
            opts.inSampleSize = 1;
            opts.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opts);
            preBitmap = bitmap;
            preShowIv.setImageBitmap(bitmap);
            playIv.setVisibility(View.GONE);
            preShowRl.setVisibility(View.VISIBLE);
            type = "3";
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private MediaPlayer getVideoPlayer(String path) {
        //File file = new File("{您的视频所在的路径}")

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mediaPlayer;

    }

    /**
     * 上传段子
     *
     * @param type 段子类型 2-段子，3-图片，4-视频
     */
    public void upLoadDuanzi(String type) {
        UploadContentRequest request = new UploadContentRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(DeviceUtils.getAndroidID());
        request.setToken(UserInfo.getToken());
        request.setContentText(contentEt.getText().toString().trim());
        request.setContentType(type);
        if (selected) {
            request.setIsAnonymous("1");
        } else {
            request.setIsAnonymous("2");
        }

        if ("2".equals(type)) {
            if (TextUtils.isEmpty(contentEt.getText().toString().trim())) {
                ToastUtils.showShort("请输入发布内容");
                return;
            }
            request.setPixelHeight(0);
            request.setPixelWidth(0);
            request.setDuration(0);
            request.setFileSize(0);

        } else if ("3".equals(type)) {
            request.setPixelHeight(opts.outHeight);
            request.setPixelWidth(opts.outWidth);
            request.setDuration(0);
            request.setFileSize((int) FileSizeUtil.getFileOrFilesSize(uploadPath, 2));
        } else if ("4".equals(type)) {
            request.setPixelHeight(videoThumbnail.getHeight());
            request.setPixelWidth(videoThumbnail.getWidth());
            request.setDuration(videoDuration);
            request.setFileSize((int) FileSizeUtil.getFileOrFilesSize(uploadPath, 2));
        }
        pb.setVisibility(View.VISIBLE);

        if ("4".equals(type)) {  //视频
            NetUtil.postVideo(Api.uploadContent, this, uploadPath, FileUtils.galleryPath + "videoThumbnail.jpg", request, new ResultCallback() {
                @Override
                public void onResult(String jsonResult) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                        }
                    });
                    BaseResponse response = JSON.parseObject(jsonResult, BaseResponse.class);
                    if ("0".equals(response.getRespCode())) {
                        ToastUtils.showShort("上传成功");
                        finish();
                    }
                }

                @Override
                public void onError(Exception ex) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                        }
                    });
                    ToastUtils.showShort("上传失败，请重试");
                }
            });
        } else  {  //文字和图片
            NetUtil.postFile(Api.uploadContent, this, uploadPath, request, new ResultCallback() {
                @Override
                public void onResult(String jsonResult) {
                    pb.setVisibility(View.GONE);

                    BaseResponse response = JSON.parseObject(jsonResult, BaseResponse.class);
                    if ("0".equals(response.getRespCode())) {
                        ToastUtils.showShort("上传成功");
                        finish();
                    }
                }

                @Override
                public void onError(Exception ex) {
                    pb.setVisibility(View.GONE);

                    ToastUtils.showShort("上传失败，请重试");
                }
            });
        }
    }

}
