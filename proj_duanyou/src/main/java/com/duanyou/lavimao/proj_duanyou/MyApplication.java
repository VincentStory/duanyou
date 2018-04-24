package com.duanyou.lavimao.proj_duanyou;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.blankj.utilcode.util.Utils;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;


import java.util.LinkedList;
import java.util.List;


/**
 * @author Lavimao
 * @date 2017/10/9
 */

public class MyApplication extends MultiDexApplication {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;
    private List<Activity> activitys = null;

    @Override
    public void onCreate() {
        super.onCreate();

        String curProcess = getProcessName(this, android.os.Process.myPid());
        Log.e(TAG, "curProcess:    " + curProcess);
        if (!getPackageName().equals(curProcess)) {
            return;
        }


        mInstance = this;


        // 初始化utils
        Utils.init(this);

        /*
        拍照权限
         */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


    }


    public MyApplication() {
        activitys = new LinkedList<Activity>();
    }


    public static boolean isLogin() {
        if (SpUtil.getStringSp(SpUtil.dyID).isEmpty()) {
            return false;
        }
        return true;
    }

    public static MyApplication getInstance() {
        if (null == mInstance) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }

        return null;
    }


    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if (!activitys.contains(activity)) {
                activitys.add(activity);
            }
        } else {
            activitys.add(activity);
        }

    }

    // 遍历所有Activity并finish
    public void finishActivity() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }

    }


}
