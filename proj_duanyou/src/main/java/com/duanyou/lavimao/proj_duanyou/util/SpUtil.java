package com.duanyou.lavimao.proj_duanyou.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.duanyou.lavimao.proj_duanyou.MyApplication;


/**
 * Created by vincent on 2018/4/21.
 */

public class SpUtil {
    private final static String XIANCHUN_SHARE_PREFRENCES = "share_prefrences";

    public final static String NETWORK="network";

    public final static String PAY_WAY="payway";
    public final static int ORDER_PAY=1;
    public final static int CHARGE_PAY=2;

    /**
     * 登录记录信息
     *
     * token
     String
     是
     身份令牌，验证成功后返回
     nickName
     String
     是
     用户昵称
     headPortraitUrl
     String
     是
     头像URL
     fansNum
     Int
     是
     粉丝数量
     followNum
     Int
     是
     关注数
     gender
     String
     是
     性别
     currentLocation
     String
     是
     当前所在地理位置
     signature
     String
     是
     个性签名
     backgroundWall
     String
     是
     背景图URL
     integral
     Int
     是
     积分
     region
     String
     是
     出生地
     occupation
     String
     是
     职业
     birthday
     String
     是
     生日
     maritalStatus
     String
     是
     是否已婚
     dyID
     String
     是
     段友号，与手机号必传其一
     mobilePhone
     String
     是
     手机号，与段友号必传其一

     */
    public final static String dyID = "dyID";
    public final static String mobilePhone = "mobilePhone";
    public final static String EMAIL = "email";
    public final static String TOKEN = "token";
    public final static String PHOTO_URL = "photourl";
    public final static String NICKNAME = "nickname";
    public final static String MYSF = "mysf";
    public final static String USERNAME = "name";
    public final static String MAJOR = "major";
    public final static String BIRTH = "birth";
    public final static String SID = "sid";
    public final static String SCHOOL = "sname";
    public final static String RXYEAR = "rxyear";
    public final static String CARDNO = "cardno";
    public final static String ADDRESS = "address";
    public final static String VALIDTIME = "validtime";//有效期
    public final static String INVITECODE = "invitecode";
    public final static String JIFEN = "jifen";
    public final static String HONORLEVEL = "honorlevel";
    public final static String ACTIVCOUNT = "activcount";//激活人数
    public final static String CARDISVALID = "cardisvalid";//1-有效，当值为1时，不允许再修改身份认证信息

    public final static String LOCAL_CITY = "local_city";
    public final static String LOCAL_ID = "local_id";
    public final static String LONGITUDE = "longitude";//经度
    public final static String LATITUDE = "latitude";//维度

    public final static String IMG_URL_ZM = "img_url_zm";
    public final static String IMG_URL_BM = "img_url_bm";
    public final static String IMG_URL_SC = "img_url_sc";

//    public final static String LOGIN_NAME = "login_name";
//    public final static String LOGIN_PWD = "login_pwd";


    public final static String ORDER_NO = "order_no";
    public final static String PAY_SUCCESS = "pay_success";
    public final static String COMMIT_SUCCESS = "commit_success";
    public final static String EDIT_USERINFO_SUCCESS = "edit_userinfo_success";

    /**
     * 第一次启动MyApplication
     */
    public final static String IS_FIRST_START_MyApplication = "first_start";
    /**
     * 第一次进入主页
     */
    public final static String IS_FIRST_START_MAIN_ACTIVITY = "first_start_main_activity";

    /**
     * 手机号
     */
    public static final String PHONE_NUMBER = "phone_number";
    /**
     * 区域id
     */
    public static final String AREA_ID = "area_id";

    /**
     * 区域名称
     */
    public static final String AREA_NAME = "area_name";
    /**
     * 套餐编号
     */
    public static final String CBID = "cbid";
    /**
     * 套餐名称：0-乐享4G套餐 1-个人定制套餐
     */
    public static final String COMBO_NAME = "combotname";
    /**
     * 短信
     */
    public static final String SMS = "sms";
    /**
     * 语音
     */
    public static final String VOICE = "voice";
    /**
     * 流量
     */
    public static final String TRAFFIC = "traffic";
    /**
     * 预付款
     */
    public static final String PRESTORE = "prestore";
    public static final String Q_DOWNLOAD_URL = "download_url";
    public static final String COMBO_TYPE = "combo_type";//0-非个人定制套餐 1-个人定制套餐


    public final static String SP_PUSH_MSG = "push_msg";
    public final static String SP_PUSH_UNREAD = "push_unread";

    public static String getStringSp(String key) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static boolean saveStringSP(String key, String value) {
        SharedPreferences sharedPreferences = MyApplication
                .getInstance()
                .getApplicationContext()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor.putString(key, value).commit();
    }

    public static boolean getBooleanSp(String key) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    public static boolean saveBooleanSP(String key, boolean value) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor.putBoolean(key, value).commit();
    }

    public static int getIntSp(String key) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static boolean saveIntSP(String key, int value) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor.putInt(key, value).commit();
    }

    public static long getLongSp(String key) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public static boolean saveLongSP(String key, long value) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor.putLong(key, value).commit();
    }


    public static boolean saveFloatSP(String key, float value) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor.putFloat(key, value).commit();
    }

    public static float getFloatSp(String key) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0);
    }

    public static boolean removeSP(String key) {
        SharedPreferences sharedPreferences = MyApplication.getInstance()
                .getSharedPreferences(XIANCHUN_SHARE_PREFRENCES,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor.remove(key).commit();
    }

    public final static String SEE_EVALUATION_TOP_DATA = "TOP_data";


}
