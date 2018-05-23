package com.duanyou.lavimao.proj_duanyou.util;

import android.content.Context;

/**
 * @author zhouyong
 * @date 2014-5-27 下午4:16:27
 */
public class DipUtil {

    /**
     * dip 转 pixels
     */
    public static int dipToPixels(float dip) {
        return (int) (dip * Screen.getInstance().density + 0.5f);
    }

    /**
     * pixels 转 dip
     */
    public static int pixelsToDip(float pixels) {
        return (int) (pixels / Screen.getInstance().density + 0.5f);
    }

    /**
     * sp 转 pixels
     */
    public static int spToPixels(float sp) {
        return (int) (sp * Screen.getInstance().scaledDensity + 0.5f);
    }

    /**
     * pixels 转 sp
     */
    public static int pixelsToSp(float pixels) {
        return (int) (pixels / Screen.getInstance().scaledDensity + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return (int) (dpValue * Screen.getInstance().scaledDensity + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / Screen.getInstance().scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
