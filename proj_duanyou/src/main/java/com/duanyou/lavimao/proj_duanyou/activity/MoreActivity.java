package com.duanyou.lavimao.proj_duanyou.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.BottomPopupWindow;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreActivity extends BaseActivity {

    private String galleryPath;  //相册路径
    private Uri imageUri;

    @Override
    public void setView() {
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void startInvoke() {

    }

    @OnClick({R.id.close_iv, R.id.duanzi_ll, R.id.duanyouxiu_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                finish();
                break;
            case R.id.duanzi_ll:
                if (UserInfo.getLoginState()) {
                    jumpTo(UploadDuanziActivity.class);
                } else {
                    jumpTo(LoginActivity.class);
                }
                finish();
                break;
            case R.id.duanyouxiu_ll:
                if (UserInfo.getLoginState()) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constants.MY_PERMISSIONS_REQUEST_CALL_PHOTO);
                    } else {
                        showBottomDialog();
                    }
                } else {
                    jumpTo(LoginActivity.class);
                }
                break;

        }
    }

    public void showBottomDialog() {
        galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        new BottomPopupWindow(this).builder().setTitle("选择模式")
                .addSheetItem("拍照", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        photoStart();
                    }
                })
                .addSheetItem("摄像", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        videoStart();
                    }
                }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_CALL_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showBottomDialog();
                } else {
                    ToastUtils.showShort("you denied the permission");
                }
                break;
        }
    }

    private void photoStart() {
        //创建file对象，用于存储拍照后的图片；
        File outputImage = new File(galleryPath, "output_image.png");

        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(this, "com.gyq.cameraalbumtest.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, Constants.REQUEST_CODE_TAKE_PHOTO);
    }

    private void videoStart() {
        //创建file对象，用于存储摄像后的图片；
        File outputImage = new File(galleryPath, "output_video.mp4");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(this, "com.dyouclub.jokefriends.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, Constants.REQUEST_CODE_TAKE_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //Bitmap bm = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        //cameraIv.setImageBitmap(bm);
                        Intent photoIntent = new Intent(this, UploadDuanziActivity.class);
                        photoIntent.putExtra(Constants.FILE_PATH, galleryPath + "output_image.png");
                        photoIntent.putExtra(Constants.type, "3");
                        startActivity(photoIntent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constants.REQUEST_CODE_TAKE_VIDEO:
                if (resultCode == RESULT_OK) {
                    try {
                        Intent videoIntent = new Intent(this, UploadDuanziActivity.class);
                        videoIntent.putExtra(Constants.FILE_PATH, galleryPath + "output_video.mp4");
                        videoIntent.putExtra(Constants.type, "4");
                        startActivity(videoIntent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }
}
