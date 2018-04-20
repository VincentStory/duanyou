package com.duanyou.lavimao.proj_duanyou.net;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.duanyou.lavimao.proj_duanyou.net.request.GetContentRequest;
import com.xiben.ebs.esbsdk.callback.InvokeCallback;
import com.xiben.ebs.esbsdk.callback.ResultCallback;
import com.xiben.ebs.esbsdk.esb.BaseClientProxy;

import java.text.SimpleDateFormat;

public class NetUtil {
    private static BaseClientProxy esbPoxy = new BaseClientProxy();
    public static String SERVICES_URL = "http://www.dyouclub.com:8080/restful/request/";

    public static void getData(final String serviceId, final Activity context,
                               final BaseRequest request,
                               final ResultCallback resultCallback) {


        esbPoxy.invoke(SERVICES_URL, serviceId, JSON.toJSONString(request), new InvokeCallback<String>() {

            @Override
            public void onComplete(String headerResult, final String body) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultCallback.onResult(body);
                    }
                });



            }

            @Override
            public void onError(final Exception e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultCallback.onError(e);
                    }
                });



            }
        });
    }


}
