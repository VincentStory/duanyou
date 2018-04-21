package com.duanyou.lavimao.proj_duanyou;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.blankj.utilcode.util.Utils;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;


import java.util.List;


/**
 * @author Lavimao
 * @date 2017/10/9
 */

public class MyApplication extends MultiDexApplication {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;

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


    }

    public MyApplication() {

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


}
