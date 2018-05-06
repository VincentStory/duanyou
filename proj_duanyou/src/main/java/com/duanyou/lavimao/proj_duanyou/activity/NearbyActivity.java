package com.duanyou.lavimao.proj_duanyou.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.adapter.NearbyPeopleAdapter;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.PeopleNearbyRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.NearbyPeopleResponse;
import com.duanyou.lavimao.proj_duanyou.util.Constants;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.duanyou.lavimao.proj_duanyou.widgets.BottomPopupWindow;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2018/5/2.
 */

public class NearbyActivity extends BaseActivity {
    @BindView(R.id.nav_title)
    TextView titleTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    List<NearbyPeopleResponse.UserInfo> mUserInfoList = new ArrayList<>();
    private static final int BAIDU_READ_PHONE_STATE = 100;

    private final int LOCATION_CODE = 0x01;
    private double latitude;
    private double longitude;
    private int page;
    private String sex = "";
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;
    private boolean refreshTag = true;  //下拉刷新  true   加载更多  false
    private NearbyPeopleAdapter mAdapter;

    @Override
    public void setView() {
        setContentView(R.layout.activity_nearby);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        titleTv.setText("附近");
        rightTv.setText("筛选");

    }

    @Override
    public void startInvoke() {
        initView();
        initData();
        initList();
        //判断是否为android6.0系统版本，如果是，需要动态添加权限
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts();
        } else {
            getLngAndLat(NearbyActivity.this);
        }

    }

    private void initView() {
        refreshLayout.setHeaderView(new SinaRefreshView(NearbyActivity.this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshTag = true;
                page = 1;
                getData(latitude, longitude, page, sex);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshTag = false;
                page++;
                getData(latitude, longitude, page, sex);
            }
        });

    }

    /**
     * 初始化列表
     */
    private void initList() {
        if (mAdapter == null) {

            mAdapter = new NearbyPeopleAdapter(NearbyActivity.this, mUserInfoList, R.layout.adapter_nearby_people);

            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(NearbyActivity.this, PeopleInfoActivity.class);
                    intent.putExtra(Constants.targetDyID, mUserInfoList.get(position).getDyID());

                    startActivity(intent);


                }
            });
        }

    }


    public void showContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(NearbyActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
        } else {
            getLngAndLat(NearbyActivity.this);
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    getLngAndLat(NearbyActivity.this);
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    private String getLngAndLat(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork();
            }
        } else {    //从网络获取经纬度
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        getData(latitude, longitude, page, sex);
        return longitude + "," + latitude;
    }

    //从网络获取经纬度

    @SuppressLint("MissingPermission")
    public String getLngAndLatWithNetwork() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        getData(latitude, longitude, page, sex);
        return longitude + "," + latitude;
    }


    private void getData(Double latitude, Double longitude, final int page, String sex) {
        if (latitude == 0) {
            ToastUtils.showShort("定位失败");
        } else {
            getPeopleNearby(NearbyActivity.this, latitude, longitude, page, sex, new GetContentResult() {
                @Override
                public void success(String json) {
                    NearbyPeopleResponse response = JSON.parseObject(json, NearbyPeopleResponse.class);

                    if (refreshTag) {
                        mUserInfoList.clear();
                        refreshLayout.finishRefreshing();
                    } else {
                        refreshLayout.finishLoadmore();
                    }

                    if (response.getUserInfo().size() > 0) {
                        mUserInfoList.addAll(response.getUserInfo());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShort(getResources().getString(R.string.no_more));
                    }
                }

                @Override
                public void error(Exception ex) {

                }
            });

        }
    }

    LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
        }
    };

    @OnClick({R.id.iv_left, R.id.right_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.right_tv:
                new BottomPopupWindow(NearbyActivity.this).builder()
                        .setTitle("筛选附近人").setCancelable(false).setCanceled(true)
                        .addSheetItem("只看男生", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                page = 1;
                                getData(latitude, longitude, page, "男");

                            }
                        })
                        .addSheetItem("只看女生", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                page = 1;
                                getData(latitude, longitude, page, "女");

                            }
                        }).addSheetItem("神秘人士", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        page = 1;
                        getData(latitude, longitude, page, "0");
                    }
                }).addSheetItem("查看全部", BottomPopupWindow.SheetItemColor.Blue, new BottomPopupWindow.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        page = 1;
                        getData(latitude, longitude, page, "");

                    }
                })

                        .show();
                break;
            default:
                break;
        }
    }

    /**
     * 1.4.18	获取附近段友
     */
    private void getPeopleNearby(final Activity context, Double latitude, Double longitude, int page, String sex, final GetContentResult result) {
        PeopleNearbyRequest request = new PeopleNearbyRequest();
        request.setDyID(UserInfo.getDyId());
        request.setDeviceID(UserInfo.getDeviceId());
        request.setToken(UserInfo.getToken());
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setPage(page);
        request.setRange((float) 50);
        request.setSex(sex);

        NetUtil.getData(Api.peopleNearby, context, request, new ResultCallback() {
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
