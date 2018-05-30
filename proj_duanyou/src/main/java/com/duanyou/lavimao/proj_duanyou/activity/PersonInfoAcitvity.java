package com.duanyou.lavimao.proj_duanyou.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
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
import com.duanyou.lavimao.proj_duanyou.net.request.SetUserInfoRequest;
import com.duanyou.lavimao.proj_duanyou.net.request.UserInfoRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.LoginResponse;
import com.duanyou.lavimao.proj_duanyou.net.response.UserInfoResponse;
import com.duanyou.lavimao.proj_duanyou.util.AddressPickTask;
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
import java.io.IOException;
import java.util.Date;
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
import cn.addapp.pickers.picker.DateTimePicker;
import cn.addapp.pickers.picker.SinglePicker;
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
    @BindView(R.id.nickname_et)
    EditText nicknameEt;
    @BindView(R.id.hunyin_tv)
    TextView hunyinTv;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.birthy_tv)
    TextView birthyTv;
    @BindView(R.id.work_et)
    EditText workEt;
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


        nicknameEt.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    setUserInfo(PersonInfoAcitvity.this, nicknameEt.getText().toString(), birthyTv.getText().toString(), areaTv.getText().toString(),
                            sexTv.getText().toString(), hunyinTv.getText().toString(), workEt.getText().toString(), signatureEt.getText().toString(), new GetContentResult() {
                                @Override
                                public void success(String json) {
                                    SpUtil.saveStringSP(SpUtil.nickName, nicknameEt.getText().toString());
                                }

                                @Override
                                public void error(Exception ex) {
                                }
                            });
                }
            }
        });
        workEt.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    setUserInfo(PersonInfoAcitvity.this, nicknameEt.getText().toString(), birthyTv.getText().toString(), areaTv.getText().toString(),
                            sexTv.getText().toString(), hunyinTv.getText().toString(), workEt.getText().toString(), signatureEt.getText().toString(), new GetContentResult() {
                                @Override
                                public void success(String json) {

                                }

                                @Override
                                public void error(Exception ex) {
                                }
                            });
                }
            }
        });
        signatureEt.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    setUserInfo(PersonInfoAcitvity.this, nicknameEt.getText().toString(), birthyTv.getText().toString(), areaTv.getText().toString(),
                            sexTv.getText().toString(), hunyinTv.getText().toString(), workEt.getText().toString(), signatureEt.getText().toString(), new GetContentResult() {
                                @Override
                                public void success(String json) {
                                }

                                @Override
                                public void error(Exception ex) {
                                }
                            });
                }
            }
        });


    }

    @Override
    public void startInvoke() {
        getUserInfo(PersonInfoAcitvity.this, UserInfo.getDyId(), new GetContentResult() {
            @Override
            public void success(String json) {
                UserInfoResponse response = JSON.parseObject(json, UserInfoResponse.class);
                if (response.getUserInfo().size() > 0) {
                    UserInfoResponse.UserInfo userInfo = response.getUserInfo().get(0);
                    String picurl = userInfo.getHeadPortraitUrl();
                    if (picurl != null)
                        Glide.with(PersonInfoAcitvity.this).load(picurl).into(headIv);
                    duanyouidTv.setText(userInfo.getDyID());
                    nicknameEt.setText(userInfo.getNickName());
                    hunyinTv.setText(userInfo.getMaritalStatus());
                    sexTv.setText(userInfo.getGender());
                    birthyTv.setText(userInfo.getBirthday());
                    workEt.setText(userInfo.getOccupation());
                    areaTv.setText(userInfo.getRegion());
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


    @OnClick({R.id.iv_left, R.id.headimage_rl, R.id.hunyin_rl, R.id.sex_rl, R.id.birthy_rl, R.id.area_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                setUserInfo(PersonInfoAcitvity.this, nicknameEt.getText().toString(), birthyTv.getText().toString(), areaTv.getText().toString(),
                        sexTv.getText().toString(), hunyinTv.getText().toString(), workEt.getText().toString(), signatureEt.getText().toString(), new GetContentResult() {
                            @Override
                            public void success(String json) {
                                finish();
                            }

                            @Override
                            public void error(Exception ex) {
                                ToastUtils.showShort(ex.getMessage());
                                finish();
                            }
                        });


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
            case R.id.hunyin_rl:
                onHunyinPicker();
                break;

            case R.id.sex_rl:
                onConstellationPicker();
                break;
            case R.id.birthy_rl:
                onYearMonthDayPicker();
                break;
            case R.id.area_rl:
                onAddressPicker();
                break;


            default:
                break;
        }
    }


    /**
     * 性别
     */
    public void onConstellationPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        SinglePicker<String> picker = new SinglePicker<>(this,
                isChinese ? new String[]{
                        "男", "女"
                } : new String[]{
                        "boy", "girle"
                });
        picker.setCanLoop(false);//不禁用循环
        picker.setTopBackgroundColor(0xFFEEEEEE);
        picker.setTopHeight(50);
        picker.setTopLineColor(0xFF33B5E5);
        picker.setTopLineHeight(1);
        picker.setTitleText(isChinese ? "请选择" : "Please pick");
        picker.setTitleTextColor(0xFF999999);
        picker.setTitleTextSize(12);
        picker.setCancelTextColor(0xFF33B5E5);
        picker.setCancelTextSize(13);
        picker.setSubmitTextColor(0xFF33B5E5);
        picker.setSubmitTextSize(13);
        picker.setSelectedTextColor(0xFFEE0000);
        picker.setUnSelectedTextColor(0xFF999999);
        picker.setWheelModeEnable(false);
        LineConfig config = new LineConfig();
        config.setColor(Color.BLUE);//线颜色
        config.setAlpha(120);//线透明度
//        config.setRatio(1);//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(0xFFE1E1E1);
        //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
        picker.setSelectedIndex(0);
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
//                ToastUtils.showShort("index=" + index + ", item=" + item);
                sexTv.setText(item);
                setUserInfo(PersonInfoAcitvity.this, nicknameEt.getText().toString(), birthyTv.getText().toString(), areaTv.getText().toString(),
                        sexTv.getText().toString(), hunyinTv.getText().toString(), workEt.getText().toString(), signatureEt.getText().toString(), new GetContentResult() {
                            @Override
                            public void success(String json) {
                            }

                            @Override
                            public void error(Exception ex) {
                            }
                        });
            }
        });
        picker.show();
    }

    /**
     * 性别
     */
    public void onHunyinPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        SinglePicker<String> picker = new SinglePicker<>(this,
                isChinese ? new String[]{
                        "单身", "已婚", "未婚", "保密"
                } : new String[]{
                        "boy", "girle", "boy", "girle"
                });
        picker.setCanLoop(false);//不禁用循环
        picker.setTopBackgroundColor(0xFFEEEEEE);
        picker.setTopHeight(50);
        picker.setTopLineColor(0xFF33B5E5);
        picker.setTopLineHeight(1);
        picker.setTitleText(isChinese ? "请选择" : "Please pick");
        picker.setTitleTextColor(0xFF999999);
        picker.setTitleTextSize(12);
        picker.setCancelTextColor(0xFF33B5E5);
        picker.setCancelTextSize(13);
        picker.setSubmitTextColor(0xFF33B5E5);
        picker.setSubmitTextSize(13);
        picker.setSelectedTextColor(0xFFEE0000);
        picker.setUnSelectedTextColor(0xFF999999);
        picker.setWheelModeEnable(false);
        LineConfig config = new LineConfig();
        config.setColor(Color.BLUE);//线颜色
        config.setAlpha(120);//线透明度
//        config.setRatio(1);//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(0xFFE1E1E1);
        //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
        picker.setSelectedIndex(0);
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
//                ToastUtils.showShort("index=" + index + ", item=" + item);
                hunyinTv.setText(item);
                setUserInfo(PersonInfoAcitvity.this, nicknameEt.getText().toString(), birthyTv.getText().toString(), areaTv.getText().toString(),
                        sexTv.getText().toString(), hunyinTv.getText().toString(), workEt.getText().toString(), signatureEt.getText().toString(), new GetContentResult() {
                            @Override
                            public void success(String json) {
                            }

                            @Override
                            public void error(Exception ex) {
                            }
                        });
            }
        });
        picker.show();
    }

    /**
     * 时间选择
     */
    public void onYearMonthDayPicker() {
        final DatePicker picker = new DatePicker(this);
        picker.setCanLoop(true);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(1900, 1, 1);
        picker.setRangeEnd(2111, 1, 1);
        picker.setSelectedItem(2000, 1, 1);
        picker.setWeightEnable(true);
        picker.setLineColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                ToastUtils.showShort(year + "-" + month + "-" + day);
                birthyTv.setText(year + "-" + month + "-" + day);
                setUserInfo(PersonInfoAcitvity.this, nicknameEt.getText().toString(), birthyTv.getText().toString(), areaTv.getText().toString(),
                        sexTv.getText().toString(), hunyinTv.getText().toString(), workEt.getText().toString(), signatureEt.getText().toString(), new GetContentResult() {
                            @Override
                            public void success(String json) {
                            }

                            @Override
                            public void error(Exception ex) {
                            }
                        });

            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {

                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 选择地区
     */
    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtils.showShort("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    ToastUtils.showShort(province.getAreaName() + city.getAreaName());
                } else {
                    ToastUtils.showShort(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }

                areaTv.setText(province.getAreaName() + "-" + city.getAreaName() + "-" + county.getAreaName());
                setUserInfo(PersonInfoAcitvity.this, nicknameEt.getText().toString(), birthyTv.getText().toString(), areaTv.getText().toString(),
                        sexTv.getText().toString(), hunyinTv.getText().toString(), workEt.getText().toString(), signatureEt.getText().toString(), new GetContentResult() {
                            @Override
                            public void success(String json) {
                                SpUtil.saveStringSP(SpUtil.getRegion, areaTv.getText().toString());
                            }

                            @Override
                            public void error(Exception ex) {
                            }
                        });
            }
        });
        task.execute("上海", "上海", "长宁");
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
//        /**
//         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
//         */
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
                        upImage(CROP_PHOTO, PersonInfoAcitvity.this, picturePath, new GetContentResult() {
                            @Override
                            public void success(String json) {
                                Log.i(TAG, "success: " + json);
                                Glide.with(PersonInfoAcitvity.this).load(picturePath).into(headIv);
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

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        picturePath = cursor.getString(columnIndex);
        cursor.close();
    }


//    private String newPath = null;


    /**
     * 修改个人头像
     *
     * @param type
     * @param context
     * @param oldPath
     * @param result
     */
    private void upImage(int type, final Activity context, String oldPath, final GetContentResult result) {
//        Double db = FileSizeUtil.getFileOrFilesSize(oldPath, FileSizeUtil.SIZETYPE_KB);
//        if (db > 300 || type == CROP_PHOTO) {
//            CompressTools.getInstance(this).compressToFile(new File(oldPath), new CompressTools.OnCompressListener() {
//                @Override
//                public void onStart() {
//
//                }
//
//                @Override
//                public void onFail(String error) {
//                    LogUtil.log(error);
//                }
//
//                @Override
//                public void onSuccess(File file) {
//                    newPath = file.getPath();
//
//                }
//            });
//        } else {
//            newPath = oldPath;
//        }


        uploadImage(context, oldPath, result);

    }


    private void uploadImage(Activity context, String path, final GetContentResult result) {
        Log.i(TAG, "upImage: newPath" + path);

        ModifyHeadImageRequest request = new ModifyHeadImageRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(DeviceUtils.getAndroidID());
        request.setToken(UserInfo.getToken());

        NetUtil.postFile(Api.modifyHeadPortrait, context, path, request, new ResultCallback() {
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
     * 修改个人信息
     */
    protected void setUserInfo(final Activity context, String nickname, String birthday, String area, String sex, String hunyin, String work, String signature,
                               final GetContentResult result) {
        String ss;
        SetUserInfoRequest request = new SetUserInfoRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(UserInfo.getDeviceId());
        request.setNickName(nickname);
        request.setToken(UserInfo.getToken());
        request.setBirthday(birthday);
        if (sex.equals("女")) {
            ss = "0";
        } else if (sex.equals("男")) {
            ss = "1";
        } else {
            ss = "2";
        }
        request.setGender(ss);
        request.setMaritalStatus(hunyin);
        request.setOccupation(work);
        request.setSignature(signature);
        request.setRegion(area);


        NetUtil.getData(Api.setUserInfo, context, request, new ResultCallback() {
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
