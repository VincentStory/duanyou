package com.duanyou.lavimao.proj_duanyou.util;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.duanyou.lavimao.proj_duanyou.MyApplication;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : 吐司相关工具类
 * </pre>
 */
public  class ToastUtils {

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShort(@NonNull final CharSequence text) {
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(@NonNull final CharSequence text) {
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

}
