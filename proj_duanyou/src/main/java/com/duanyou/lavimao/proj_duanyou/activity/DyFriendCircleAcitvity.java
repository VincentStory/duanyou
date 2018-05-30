package com.duanyou.lavimao.proj_duanyou.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.fragment.main.TagFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.FriendCircleRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.ModifyHeadImageRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.SetUserInfoRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.UserInfoRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.UserInfoResponse;
import com.duanyou.lavimao.proj_duanyou.util.AddressPickTask;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.DeviceUtils;
import com.duanyou.lavimao.proj_duanyou.util.FileSizeUtil;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.BottomPopupWindow;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;
import com.xiben.ebs.esbsdk.util.LogUtil;

import net.bither.util.CompressTools;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.SinglePicker;

/**
 * Created by vincent on 2018/4/22.
 */

public class DyFriendCircleAcitvity extends BaseActivity {

    TagFragment tagFragment;
//    @BindView(R.id.nickname_tv)
    TextView nicknameTv;
//    @BindView(R.id.headimage_iv)
    RoundedImageView headIv;
//    @BindView(R.id.bg_iv)
    ImageView bgIv;

    @Override
    public void setView() {
        setContentView(R.layout.activity_dyfriend_circle);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
//        initTitle();
        Contents.FIRST_REFRESH=true;
        //必需继承FragmentActivity,嵌套fragment只需要这行代码
        tagFragment =TagFragment.newInstance("6", "");
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, tagFragment).commitAllowingStateLoss();

    }

    @Override
    public void startInvoke() {
        getUserInfo(DyFriendCircleAcitvity.this, UserInfo.getDyId(), new GetContentResult() {
            @Override
            public void success(String json) {
                UserInfoResponse response = JSON.parseObject(json, UserInfoResponse.class);
                if (response.getUserInfo().size() > 0) {
                    UserInfoResponse.UserInfo userInfo = response.getUserInfo().get(0);
                    String picurl = userInfo.getHeadPortraitUrl();
                    if (tagFragment != null) {
                        View view = View.inflate(DyFriendCircleAcitvity.this, R.layout.head_dy_circle, null);
                        nicknameTv = view.findViewById(R.id.nickname_tv);
                        headIv = view.findViewById(R.id.headimage_iv);
                        bgIv = view.findViewById(R.id.bg_iv);

                        nicknameTv.setText(userInfo.getNickName());

                        if (picurl != null) {
                            if (!picurl.isEmpty())
                                Glide.with(DyFriendCircleAcitvity.this).load(picurl).into(headIv);
                        }
                        if (userInfo.getBackgroundWall() != null) {
                            if (!userInfo.getBackgroundWall().isEmpty())
                                Glide.with(DyFriendCircleAcitvity.this).load(userInfo.getBackgroundWall()).into(bgIv);

                        }
                        setTitle("段友圈");
                        tagFragment.getListView().addHeaderView(view);

                    }




                }
            }

            @Override
            public void error(Exception ex) {

            }
        });

    }


//    private void initTitle() {
//        setTitle("段友圈");
//        if (!UserInfo.getBgUrl().isEmpty())
//            Glide.with(DyFriendCircleAcitvity.this).load(UserInfo.getBgUrl()).into(bgIv);
//        if (!UserInfo.getHeadUrl().isEmpty())
//            Glide.with(DyFriendCircleAcitvity.this).load(UserInfo.getHeadUrl()).into(headIv);
//
//        nicknameTv.setText(UserInfo.getNickName());
//
//    }


    @OnClick({R.id.iv_left, R.id.nav_right_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.nav_right_iv:
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


            default:
                break;
        }
    }

    private String galleryPath;  //相册路径
    private Uri imageUri;

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
            imageUri = FileProvider.getUriForFile(this, "com.dyouclub.jokefriends.fileprovider", outputImage);
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


    /**
     * 获取段友圈
     */
    private void getDyCoterie(final Activity context, String beginContentId, final GetContentResult result) {
        FriendCircleRequest request = new FriendCircleRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(UserInfo.getDeviceId());
        request.setBeginContentID(beginContentId);
        request.setToken(UserInfo.getToken());

        NetUtil.getData(Api.getDyCoterie, context, request, new ResultCallback() {
            @Override
            public void onResult(final String jsonResult) {
                BaseResponse response = JSON.parseObject(jsonResult, BaseResponse.class);
                if (response.getRespCode().equals("0")) {

                    result.success(jsonResult);


                } else {
                    ToastUtils.showShort(response.getRespMessage());
                }

            }

            @Override
            public void onError(Exception ex) {

                result.error(ex);
            }
        });

    }


}
