package com.duanyou.lavimao.proj_duanyou.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.duanyou.lavimao.proj_duanyou.R;
import com.duanyou.lavimao.proj_duanyou.base.BaseActivity;
import com.duanyou.lavimao.proj_duanyou.net.Api;
import com.duanyou.lavimao.proj_duanyou.net.BaseResponse;
import com.duanyou.lavimao.proj_duanyou.net.GetContentResult;
import com.duanyou.lavimao.proj_duanyou.net.NetUtil;
import com.duanyou.lavimao.proj_duanyou.net.request.PeopleNearbyRequest;
import com.duanyou.lavimao.proj_duanyou.net.response.NearbyPeopleResponse;
import com.duanyou.lavimao.proj_duanyou.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.util.UserInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.xiben.ebs.esbsdk.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;

/**
 * Created by vincent on 2018/5/2.
 */

public class NearbyActivity extends BaseActivity {
    @BindView(R.id.nav_title)
    TextView titleTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    List<NearbyPeopleResponse.UserInfo> mUserInfoList = new ArrayList<>();

    private final int LOCATION_CODE = 0x01;
    private double latitude;
    private double longitude;
    private int page;
    private String sex = "";
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

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
        getLngAndLat(NearbyActivity.this);

        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);
        mPullLoadMoreRecyclerView.setLinearLayout();
        recyclerViewAdapter = new RecyclerViewAdapter();
        mPullLoadMoreRecyclerView.setAdapter(recyclerViewAdapter);
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(latitude, longitude, page, sex);
            }

            @Override
            public void onLoadMore() {
                page++;
                getData(latitude, longitude, page, sex);
            }
        });

    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        public RecyclerViewAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_nearby_people, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(NearbyActivity.this).load(mUserInfoList.get(position).getHeadPortraitUrl()).into(holder.headIv);
            holder.nameTv.setText(mUserInfoList.get(position).getNickName());
            int dis = (Integer.parseInt(mUserInfoList.get(position).getDistance())) / 1000;
            holder.distanceTv.setText(dis + "km | " + mUserInfoList.get(position).getLatelyTime());
        }

        @Override
        public int getItemCount() {
            return mUserInfoList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private RoundedImageView headIv;
            private TextView nameTv;
            private TextView distanceTv;

            public ViewHolder(View itemView) {
                super(itemView);
                headIv = itemView.findViewById(R.id.head_iv);
                nameTv = itemView.findViewById(R.id.name_tv);
                distanceTv = itemView.findViewById(R.id.distance_tv);
            }
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
        getPeopleNearby(NearbyActivity.this, latitude, longitude, page, sex, new GetContentResult() {
            @Override
            public void success(String json) {
                NearbyPeopleResponse response = JSON.parseObject(json, NearbyPeopleResponse.class);
                if (page == 1) {
                    mUserInfoList.clear();
                }
                mUserInfoList.addAll(response.getUserInfo());
                recyclerViewAdapter.notifyDataSetChanged();
                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            }

            @Override
            public void error(Exception ex) {

            }
        });
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
