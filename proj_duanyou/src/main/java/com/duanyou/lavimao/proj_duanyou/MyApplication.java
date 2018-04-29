package com.duanyou.lavimao.proj_duanyou;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.blankj.utilcode.util.Utils;
import com.duanyou.lavimao.proj_duanyou.util.SpUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


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


        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        MobclickAgent.setSessionContinueMillis(1000);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5ad04becb27b0a50cd00002f", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "");
        UMShareAPI.get(this);

    }


    public MyApplication() {
        activitys = new LinkedList<Activity>();
    }




    {
        PlatformConfig.setWeixin("wx862040dc83b4edb1", "5ee1d5cd9e9afd5509b04c44231d41ea");
//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("4234486505", "8f3d9660e41d163dcc65fc14e53eb100", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1106520694", "BMAIyEzCyOU8E6fd");
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
