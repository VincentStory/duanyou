package com.duanyou.lavimao.proj_duanyou.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.activity.CollectionActivity;
import com.duanyou.lavimao.proj_duanyou.activity.DyFriendCircleAcitvity;
import com.duanyou.lavimao.proj_duanyou.activity.FollowActivity;
import com.duanyou.lavimao.proj_duanyou.activity.HuDongActivity;
import com.duanyou.lavimao.proj_duanyou.activity.LoginActivity;
import com.duanyou.lavimao.proj_duanyou.activity.NearbyActivity;
import com.duanyou.lavimao.proj_duanyou.activity.PersonInfoAcitvity;
import com.duanyou.lavimao.proj_duanyou.activity.SettingActivity;
import com.duanyou.lavimao.proj_duanyou.base.BaseFragment;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.ModifyHeadImageRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.UserInfoRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.UserInfoResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.Contents;
import com.duanyou.lavimao.proj_duanyou.util.DeviceUtils;
import com.duanyou.lavimao.proj_duanyou.util.FileSizeUtil;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;
import com.duanyou.lavimao.proj_duanyou.util.StringUtil;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.BottomPopupWindow;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;
import com.xiben.ebs.esbsdk.util.LogUtil;

import net.bither.util.CompressTools;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

public class MineFragment extends BaseFragment {
    @BindView(R.id.login_register_tv)
    TextView loginTv;
    @BindView(R.id.nickname_tv)
    TextView nicknameTv;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.headimage_iv)
    RoundedImageView headIv;
    @BindView(R.id.bg_iv)
    ImageView bgIv;


    private String picturePath;

    private static final int CROP_PHOTO = 2;
    public static final int REQUEST_CODE_PICK_IMAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private static final int BAIDU_READ_PHONE_STATE = 100;
    //    private File output;
    private Uri imageUri;

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View view) {


    }

    @Override
    public void startInvoke(View view) {
//        refreshInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserInfo.getLoginState()) {
            refreshInfo();
            nicknameTv.setText(UserInfo.getNickName());
            locationTv.setText(SpUtil.getStringSp(SpUtil.getRegion));
            loginTv.setVisibility(View.GONE);
//            Log.i(TAG, "onResume: " + UserInfo.getBgUrl());
            if (!UserInfo.getBgUrl().isEmpty())
                Glide.with(getActivity()).load(UserInfo.getBgUrl()).into(bgIv);
            if (!UserInfo.getHeadUrl().isEmpty())
                Glide.with(getActivity()).load(UserInfo.getHeadUrl()).into(headIv);

        } else {
            nicknameTv.setText("我的窝");
            locationTv.setText("火星上");
            loginTv.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(R.drawable.background2x).into(bgIv);
            Glide.with(getActivity()).load(R.drawable.head42x).into(headIv);

        }

    }

    private void refreshInfo() {
        if (UserInfo.getLoginState() && Contents.IS_REFRESH) {
            Contents.IS_REFRESH = false;
            getUserInfo(getActivity(), new GetContentResult() {
                @Override
                public void success(String json) {
                    UserInfoResponse response = JSON.parseObject(json, UserInfoResponse.class);
                    if (response.getUserInfo().size() > 0) {
                        UserInfoResponse.UserInfo userInfo = response.getUserInfo().get(0);
                        String picurl = userInfo.getHeadPortraitUrl();
                        if (picurl != null)
                            Glide.with(getActivity()).load(picurl).into(headIv);
                        nicknameTv.setText(userInfo.getNickName());
                        locationTv.setText(userInfo.getCurrentLocation());
                        if (!TextUtils.isEmpty(userInfo.getBackgroundWall())) {
                            Glide.with(getActivity()).load(userInfo.getBackgroundWall()).into(bgIv);
                            UserInfo.setHeadUrl(userInfo.getHeadPortraitUrl());
                            UserInfo.setBgUrl(userInfo.getBackgroundWall());
                        }
                        SpUtil.saveStringSP(SpUtil.nickName, userInfo.getNickName());
                    }
                }

                @Override
                public void error(Exception ex) {

                }
            });
        }
    }


    @OnClick({R.id.login_register_tv, R.id.edit_tv, R.id.bg_iv, R.id.setting_tv, R.id.nearby_duanyou_tv,
            R.id.duanyou_circle_tv, R.id.follow_tv, R.id.collection_tv, R.id.tougao_tv, R.id.hudong_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register_tv:
                gotoActivity(LoginActivity.class);
                break;
            case R.id.setting_tv:
                gotoActivity(SettingActivity.class);
                break;
            case R.id.edit_tv:
                if (UserInfo.getLoginState()) {
                    gotoActivity(PersonInfoAcitvity.class);

                } else {
                    gotoActivity(LoginActivity.class);
                }
                break;
            case R.id.bg_iv:
                new BottomPopupWindow(getActivity()).builder()
                        .setTitle("更换背景图片").setCancelable(false).setCanceled(true)
                        .addSheetItem("拍照", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //需要对相机进行运行时权限的申请
                                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                                } else {
                                    //权限已经被授予，在这里直接写要执行的相应方法即可
                                    takePhoto();
                                }


                            }
                        })
                        .addSheetItem("选择相册", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //调用手机相册的方法,该方法在下面有具体实现
                                if (ContextCompat.checkSelfPermission(getActivity(),
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

                                } else {
                                    choosePhoto();
                                }
                            }
                        })

                        .show();

                break;
            case R.id.nearby_duanyou_tv:
                if (UserInfo.getLoginState()) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
                    } else {
                        gotoActivity(NearbyActivity.class);
                    }
                } else {
                    gotoActivity(LoginActivity.class);
                }
                break;
            case R.id.duanyou_circle_tv:
                if (UserInfo.getLoginState()) {

                    gotoActivity(DyFriendCircleAcitvity.class);
                } else {
                    gotoActivity(LoginActivity.class);
                }
                break;
            case R.id.follow_tv:
                if (UserInfo.getLoginState()) {
                    gotoActivity(FollowActivity.class);
                } else {
                    gotoActivity(LoginActivity.class);
                }
                break;
            case R.id.collection_tv:
                if (UserInfo.getLoginState()) {
                    Intent intent = new Intent(getActivity(), CollectionActivity.class);
                    intent.putExtra("type", Contents.COLLECTION_TYPE);
                    startActivity(intent);
//                    gotoActivity(CollectionActivity.class);
                } else {
                    gotoActivity(LoginActivity.class);
                }
                break;
            case R.id.tougao_tv:
                if (UserInfo.getLoginState()) {
                    Intent intent = new Intent(getActivity(), CollectionActivity.class);
                    intent.putExtra("type", Contents.TOUGAO_TYPE);
                    startActivity(intent);
                } else {
                    gotoActivity(LoginActivity.class);
                }
                break;
            case R.id.hudong_tv:
                if (UserInfo.getLoginState()) {
                    gotoActivity(HuDongActivity.class);
                } else {
                    gotoActivity(LoginActivity.class);
                }
                break;
        }
    }


    /**
     * 拍照
     */
    void takePhoto() {
        String path = StringUtil.getPath(); //获取路径
        String fileName = StringUtil.getPath() + File.separator + new Date().getTime() + ".jpg";//定义文件名
        File file = new File(path, fileName);
        if (!file.getParentFile().exists()) {//文件夹不存在
            file.getParentFile().mkdirs();
        }
        imageUri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);//takePhotoRequestCode是自己定义的一个请求码


//        /**
//         * 最后一个参数是文件夹的名称，可以随便起
//         */
//        File file = new File(Environment.getExternalStorageDirectory(), "拍照");
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        /**
//         * 这里将时间作为不同照片的名称
//         */
//        output = new File(file, System.currentTimeMillis() + ".jpg");
//
//        /**
//         * 如果该文件夹已经存在，则删除它，否则创建一个
//         */
//        try {
//            if (output.exists()) {
//                output.delete();
//            }
//            output.createNewFile();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        /**
         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
         */
//        imageUri = Uri.fromFile(output);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, CROP_PHOTO);

    }

    /**
     * 从相册选取图片
     */
    void choosePhoto() {
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                // Permission Denied
                ToastUtils.showShort("权限拒绝");

            }
        }


        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                // Permission Denied
                ToastUtils.showShort("权限拒绝");
            }
        }

        if (requestCode == BAIDU_READ_PHONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                gotoActivity(NearbyActivity.class);
            } else {
                // 没有获取到权限，做特殊处理
                ToastUtils.showShort("获取位置权限失败，请手动开启");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            /**
             * 拍照的请求标志
             */
            case CROP_PHOTO:
                if (res == RESULT_OK) {
//                    choosePhoto();

                    try {
                        /**
                         * 该uri就是照片文件夹对应的uri
                         */
                        upImage(CROP_PHOTO, getActivity(), imageUri.getPath(), new GetContentResult() {
                            @Override
                            public void success(String json) {
                                Log.i(TAG, "success: " + json);
                                Glide.with(getActivity()).load(imageUri.getPath()).into(bgIv);
                                Contents.IS_REFRESH = true;
                            }

                            @Override
                            public void error(Exception ex) {

                            }
                        });

                    } catch (Exception e) {
                        ToastUtils.showShort("程序崩溃");
                    }
                } else {
                    Log.i("tag", "失败");
                }

                break;
            /**
             * 从相册中选取图片的请求标志
             */
            case REQUEST_CODE_PICK_IMAGE:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri是上一个Activity返回的
                         */
                        getGalleryPath(data);
                        Log.i(TAG, "onActivityResult: " + picturePath);
                        upImage(CROP_PHOTO, getActivity(), picturePath, new GetContentResult() {
                            @Override
                            public void success(String json) {
                                Log.i(TAG, "success: " + json);
                                Glide.with(getActivity()).load(picturePath).into(bgIv);
                                Contents.IS_REFRESH = true;
                            }

                            @Override
                            public void error(Exception ex) {

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("tag", e.getMessage());
                        ToastUtils.showShort("程序崩溃");

                    }
                } else {
                    Log.i("liang", "失败");
                }

                break;

            default:
                break;
        }
    }

    /**
     * 根据uri得到String路径
     *
     * @param data
     */
    private void getGalleryPath(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        picturePath = cursor.getString(columnIndex);
        cursor.close();
    }


    private String newPath = null;

    /**
     * 获取个人信息
     */
    private void getUserInfo(final Activity context, final GetContentResult result) {
        UserInfoRequest request = new UserInfoRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(UserInfo.getDeviceId());
        request.setFriendDyID(UserInfo.getDyId());
        request.setToken(UserInfo.getToken());

        NetUtil.getData(Api.getUserInfo, context, request, new ResultCallback() {
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


    /**
     * 修改个人背景
     *
     * @param type
     * @param context
     * @param oldPath
     * @param result
     */
    private void upImage(int type, final Activity context, final String oldPath, final GetContentResult result) {
//        int db = (int) FileSizeUtil.getFileOrFilesSize(oldPath, FileSizeUtil.SIZETYPE_KB);
//        Log.i(TAG, "upImage: " + db + "KB");
//        if (db > 50 || type == CROP_PHOTO) {
//        Log.i(TAG, "upImage: " + db);
//        Luban.with(getActivity())
//                .load(oldPath)
//                .ignoreBy(100)
//                .setTargetDir(StringUtil.getPath())
//                .filter(new CompressionPredicate() {
//                    @Override
//                    public boolean apply(String path) {
//                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                    }
//                })
//                .setCompressListener(new OnCompressListener() {
//                    @Override
//                    public void onStart() {
//                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                        Log.i(TAG, "onStart: " + oldPath);
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                        newPath = file.getPath();
//                        int db = (int) FileSizeUtil.getFileOrFilesSize(newPath, FileSizeUtil.SIZETYPE_KB);
//                        Log.i(TAG, "upImage-newPath: " + db + "KB");
//                        Log.i(TAG, "onSuccess: " + newPath);
//                        uploadImage(context, newPath, result);
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: " + e.toString());
//                        // TODO 当压缩过程出现问题时调用
//                    }
//                }).launch();
//        Log.i(TAG, "oldPath: " + oldPath);
        uploadImage(context, oldPath, result);


    }

    private void uploadImage(Activity context, String path, final GetContentResult result) {
        Log.i(TAG, "upImage: newPath" + path);

        ModifyHeadImageRequest request = new ModifyHeadImageRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(DeviceUtils.getAndroidID());
        request.setToken(UserInfo.getToken());

        NetUtil.postFile(Api.modifyBackgroundWall, context, path, request, new ResultCallback() {
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
