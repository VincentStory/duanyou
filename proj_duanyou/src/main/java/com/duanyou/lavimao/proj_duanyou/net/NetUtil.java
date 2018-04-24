package com.duanyou.lavimao.proj_duanyou.net;

import android.app.Activity;

import com.alibaba.fastjson.JSON;

import com.duanyou.lavimao.proj_duanyou.net.request.GetContentRequest;

import com.xiben.ebs.esbsdk.callback.InvokeCallback;
import com.xiben.ebs.esbsdk.callback.ResultCallback;
import com.xiben.ebs.esbsdk.esb.BaseClientProxy;

import java.io.File;

public class NetUtil {
    private static BaseClientProxy esbPoxy = new BaseClientProxy();
    public static String SERVICES_URL = "http://www.dyouclub.com/restful/request/";
//    public static String SERVICES_URL = "123.56.8.153：8080/restful/request/";

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

    public static void postFile(final String serviceId, final Activity context, final String path,
                                final BaseRequest request,
                                final ResultCallback resultCallback) {

        esbPoxy.upImage(SERVICES_URL, serviceId, path, JSON.toJSONString(request), new InvokeCallback<String>() {
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
            public void onError(Exception e) {
                resultCallback.onError(e);
            }
        });
    }


}
