package com.duanyou.lavimao.proj_duanyou.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.duanyou.lavimao.proj_duanyou.util.PermissionRequest;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import com.yanzhenjie.permission.AndPermission;

import java.util.List;

/**
 *
 * @author temp
 * @date 2017/11/21
 */

abstract public class BaseTakePhotoDialogActivity extends Activity implements TakePhoto.TakeResultListener,InvokeListener {

    private static final String TAG = BaseTakePhotoDialogActivity.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    protected Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type= PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }
    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());
    }
    @Override
    public void takeFail(TResult result,String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }
    @Override
    public void takeCancel() {
        Log.i(TAG, "takeCancel");
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }

/////////////////////////
    public void onPermissionSuccessful(){

    }

    public void getTakePhotoPermission(){
        PermissionRequest permissionRequest;
        permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onSuccessful(List<String> grantedPermissions) {
                // 权限申请成功回调。
                onPermissionSuccessful();
            }

            @Override
            public void onFailure(List<String> deniedPermissions) {

                if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                    // 第一种：用默认的提示语。
                    AndPermission.defaultSettingDialog(activity, 100).show();

                }
            }
        });

        String[] p = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        // 先判断是否有权限。
        if (permissionRequest.hasPermission(p)) {
            // 有权限，直接do anything.
            onPermissionSuccessful();
        } else {
            permissionRequest.request(p);
        }

    }

    public void getPermission(String[] p){
        PermissionRequest permissionRequest;
        permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onSuccessful(List<String> grantedPermissions) {
                // 权限申请成功回调。
                onPermissionSuccessful();
            }

            @Override
            public void onFailure(List<String> deniedPermissions) {

                if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                    // 第一种：用默认的提示语。
                    AndPermission.defaultSettingDialog(activity, 100).show();

                }
            }
        });

        // 先判断是否有权限。
        if (permissionRequest.hasPermission(p)) {
            // 有权限，直接do anything.
            onPermissionSuccessful();
        } else {
            permissionRequest.request(p);
        }

    }


}
