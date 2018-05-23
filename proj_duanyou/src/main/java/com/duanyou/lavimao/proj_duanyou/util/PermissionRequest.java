package com.duanyou.lavimao.proj_duanyou.util;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * Created by admin on 2017/5/23.
 */

public class PermissionRequest {
    // Manifest.permission.WRITE_SETTINGS
    // ,, Manifest.permission.ACCESS_COARSE_LOCATION,
    //, ,Manifest.permission.ACCESS_FINE_LOCATION
    private String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};


    private Context mContext;
    private PermissionCallback mCallback;

    public PermissionRequest(Context context, PermissionCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    public void request() {
        AndPermission.with(mContext)
                .requestCode(110)
                .permission(permission)
                .callback(this)
                .start();
    }

    public void request(String[] p) {
        this.permission = p;
        AndPermission.with(mContext)
                .requestCode(110)
                .permission(permission)
                .callback(this)
                .start();
    }

    public boolean hasPermission(String[] p){
        return AndPermission.hasPermission(mContext, p);
    }

    public boolean hasPermission(){
        return AndPermission.hasPermission(mContext, permission);
    }

    @PermissionYes(110)
    public void yes(List<String> permissions) {
        this.mCallback.onSuccessful(permissions);
    }

    @PermissionNo(110)
    public void no(List<String> permissions) {
        this.mCallback.onFailure(permissions);
    }

    /**
     * Rationale支持，这里自定义对话框。
     */

    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            UtilsDialog.ShowTips(mContext, "请开启权限！", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    rationale.resume();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    rationale.cancel();
                }
            });
        }
    };



    public interface PermissionCallback {
        void onSuccessful(List<String> grantedPermissions);

        void onFailure(List<String> deniedPermissions);
    }

}