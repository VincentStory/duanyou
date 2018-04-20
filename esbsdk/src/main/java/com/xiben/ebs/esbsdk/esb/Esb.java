package com.xiben.ebs.esbsdk.esb;

import android.content.Context;

/**
 *
 * @author temp
 * @date 2017/12/20
 */

public class Esb {
    private static boolean debug = true;
    public static void init(Context context, boolean isDebug) {
        //debug = (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        debug = isDebug;

    }

    public static boolean isDebug(){
        return debug;
    }


}
