package com.duanyou.lavimao.proj_duanyou.util;

import android.text.TextUtils;

import com.duanyou.lavimao.proj_duanyou.Event.LoginEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by luojialun on 2018/4/21.
 */

public class UserInfo {

    public static String getToken() {
        return SpUtil.getStringSp(SpUtil.TOKEN);
    }

    public static String getDyId() {
        return SpUtil.getStringSp(SpUtil.dyID);
    }

    public static String getDeviceId() {
        return DeviceUtils.getAndroidID();
    }

    public static String getNickName() {
        return SpUtil.getStringSp(SpUtil.nickName);
    }

    public static String getBgUrl() {
        return SpUtil.getStringSp(SpUtil.backgroundUrl);
    }

    public static String getHeadUrl() {
        return SpUtil.getStringSp(SpUtil.headUrl);
    }

    public static void setHeadUrl(String url) {
        SpUtil.saveStringSP(SpUtil.headUrl, url);
    }

    public static void setBgUrl(String url) {
        SpUtil.saveStringSP(SpUtil.backgroundUrl, url);
    }

    public static boolean getLoginState() {
        if (!TextUtils.isEmpty(getToken())) {
            return true;
        } else {
            return false;
        }
    }

    public static void clearUserInfo() {
        SpUtil.saveStringSP(SpUtil.TOKEN, "");
        EventBus.getDefault().post(new LoginEvent());
    }

}
