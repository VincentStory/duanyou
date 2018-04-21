package com.duanyou.lavimao.proj_duanyou.util;

import android.text.TextUtils;

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

    public static boolean getLoginState() {
        if (!TextUtils.isEmpty(getToken())) {
            return true;
        } else {
            return false;
        }
    }

}
