package com.duanyou.lavimao.proj_duanyou.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.LoginRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.ModifyHeadImageRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.UserInfoRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.LoginResponse;
import com.duanyou.lavimao.proj_duanyou.net.response.UserInfoResponse;
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
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by vincent on 2018/4/22.
 */

public class PersonInfoAcitvity extends BaseActivity {


    @BindView(R.id.iv_left)
    ImageView leftIv;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.head_iv)
    RoundedImageView headIv;
    @BindView(R.id.duanyouid_tv)
    TextView duanyouidTv;
    @BindView(R.id.nickname_tv)
    TextView nicknameTv;
    @BindView(R.id.hunyin_tv)
    TextView hunyinTv;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.birthy_tv)
    TextView birthyTv;
    @BindView(R.id.work_tv)
    TextView workTv;
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.signature_et)
    EditText signatureEt;

    private String picturePath;
    private static final int CROP_PHOTO = 2;
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private File output;


    private static final String TAG = "PersonInfoAcitvity";
    private Uri imageUri;

    @Override
    public void setView() {
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initTitle();
    }

    @Override
    public void startInvoke() {
        getUserInfo(PersonInfoAcitvity.this, new GetContentResult() {
            @Override
            public void success(String json) {
                UserInfoResponse response = JSON.parseObject(json, UserInfoResponse.class);
                if(response.getUserInfo().size()>0){
                    UserInfoResponse.UserInfo userInfo=response.getUserInfo().get(0);
                    String picurl =userInfo.getHeadPortraitUrl();
                    Glide.with(PersonInfoAcitvity.this).load(picurl).into(headIv);
                    duanyouidTv.setText(userInfo.getDyID());
                    nicknameTv.setText(userInfo.getNickName());
                    hunyinTv.setText(userInfo.getMaritalStatus());
                    sexTv.setText(userInfo.getGender());
                    birthyTv.setText(userInfo.getBirthday());
                    workTv.setText(userInfo.getOccupation());
                    areaTv.setText(userInfo.getCurrentLocation());
                    signatureEt.setText(userInfo.getSignature());
                }
            }

            @Override
            public void error(Exception ex) {

            }
        });
    }


    private void initTitle() {
        leftIv.setImageResource(R.drawable.black_back);
        navTitle.setText("个人资料");
    }


    @OnClick({R.id.iv_left, R.id.headimage_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.headimage_rl:
                new BottomPopupWindow(this).builder()
                        .setTitle("更换头像").setCancelable(false).setCanceled(true)
                        .addSheetItem("拍照", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //需要对相机进行运行时权限的申请
                                if (ContextCompat.checkSelfPermission(PersonInfoAcitvity.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(PersonInfoAcitvity.this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

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
                                if (ContextCompat.checkSelfPermission(PersonInfoAcitvity.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(PersonInfoAcitvity.this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

                                } else {
                                    choosePhoto();
                                }
                            }
                        })

                        .show();


                break;


            default:
                break;
        }
    }


    /**
     * 拍照
     */
    void takePhoto() {
        /**
         * 最后一个参数是文件夹的名称，可以随便起
         */
        File file = new File(Environment.getExternalStorageDirectory(), "拍照");
        if (!file.exists()) {
            file.mkdir();
        }
        /**
         * 这里将时间作为不同照片的名称
         */
        output = new File(file, System.currentTimeMillis() + ".jpg");

        /**
         * 如果该文件夹已经存在，则删除它，否则创建一个
         */
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
         */
        imageUri = Uri.fromFile(output);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);

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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            /**
             * 拍照的请求标志
             */
            case CROP_PHOTO:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri就是照片文件夹对应的uri
                         */
                        Log.i(TAG, "onActivityResult: " + imageUri.getPath());

                        upImage(CROP_PHOTO, PersonInfoAcitvity.this, imageUri.getPath(), new GetContentResult() {
                            @Override
                            public void success(String json) {
                                Log.i(TAG, "success: " + json);
                                Glide.with(PersonInfoAcitvity.this).load(imageUri.getPath()).into(headIv);
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
                        upImage(CROP_PHOTO, PersonInfoAcitvity.this, picturePath, new GetContentResult() {
                            @Override
                            public void success(String json) {
                                Log.i(TAG, "success: " + json);
                                Glide.with(PersonInfoAcitvity.this).load(picturePath).into(headIv);
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

        Cursor cursor = getContentResolver().query(selectedImage,
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
     * 修改个人头像
     *
     * @param type
     * @param context
     * @param oldPath
     * @param result
     */
    private void upImage(int type, final Activity context, String oldPath, final GetContentResult result) {
        Double db = FileSizeUtil.getFileOrFilesSize(oldPath, FileSizeUtil.SIZETYPE_KB);
        if (db > 300 || type == CROP_PHOTO) {
            CompressTools.getInstance(this).compressToFile(new File(oldPath), new CompressTools.OnCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onFail(String error) {
                    LogUtil.log(error);
                }

                @Override
                public void onSuccess(File file) {
                    newPath = file.getPath();

                }
            });
        } else {
            newPath = oldPath;
        }


        ModifyHeadImageRequest request = new ModifyHeadImageRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(DeviceUtils.getAndroidID());
        request.setToken(UserInfo.getToken());

        NetUtil.postFile(Api.modifyHeadPortrait, context, newPath, request, new ResultCallback() {
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
