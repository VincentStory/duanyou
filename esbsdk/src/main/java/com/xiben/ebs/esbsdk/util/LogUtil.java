package com.xiben.ebs.esbsdk.util;

import android.util.Log;

import com.xiben.ebs.esbsdk.esb.Esb;

/**
 *
 * @author Lavimao
 * @date 2017/10/17
 */

public class LogUtil {
    public static void log(String str){
        if(Esb.isDebug()) {
            Log.i("esb", str);
        }
    }
}
