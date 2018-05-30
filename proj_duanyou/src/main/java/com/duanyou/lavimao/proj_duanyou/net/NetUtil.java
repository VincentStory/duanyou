package com.duanyou.lavimao.proj_duanyou.net;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import com.blankj.utilcode.util.ToastUtils;
import com.duanyou.lavimao.proj_duanyou.activity.LoginActivity;
import com.duanyou.lavimao.proj_duanyou.net.request.GetContentRequest;

import com.xiben.ebs.esbsdk.callback.InvokeCallback;
import com.xiben.ebs.esbsdk.callback.ResultCallback;
import com.xiben.ebs.esbsdk.esb.BaseClientProxy;

import java.io.File;

public class NetUtil {
    private static BaseClientProxy esbPoxy = new BaseClientProxy();
//        public static String SERVICES_URL = "http://www.dyouclub.com/restful/request/";
        public static String SERVICES_URL = "http://123.56.8.153/restful/request/";
    private static final String TAG = "NetUtil";

    public static void getData(final String serviceId, final Activity context,
                               final BaseRequest request,
                               final ResultCallback resultCallback) {

//    public static String SERVICES_URL = "http://39.104.121.233/restful/request/";

        esbPoxy.invoke(SERVICES_URL, serviceId, JSON.toJSONString(request), new InvokeCallback<String>() {
            @Override
            public void onComplete(String headerResult, final String body) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "result---->: " + body);
                        BaseResponse response = JSON.parseObject(body, BaseResponse.class);
                        if (!response.getRespCode().equals("0")) {
                            if (response.getRespCode().equals("6")) {
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            }else{
                                Log.i(TAG, "showError: "+response.getRespMessage());
                                ToastUtils.showShort(response.getRespMessage());
                            }
                        }else{

                            resultCallback.onResult(body);
                        }
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
                Log.i(TAG, "result---->: " + body);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BaseResponse response = JSON.parseObject(body, BaseResponse.class);
                        if (!response.getRespCode().equals("0")) {
                            if (response.getRespCode().equals("6")) {
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            }else{
                                Log.i(TAG, "showError: "+response.getRespMessage());
                                ToastUtils.showShort(response.getRespMessage());
                            }
                        }else{

                            resultCallback.onResult(body);
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                resultCallback.onError(e);
            }
        });
    }

    /**
     * 上传视频
     *
     * @param serviceId
     * @param context
     * @param path           视频路径
     * @param request
     * @param resultCallback
     */
    public static void postVideo(final String serviceId, final Activity context, final String path,
                                 final BaseRequest request,
                                 final ResultCallback resultCallback) {

        esbPoxy.upVideo(SERVICES_URL, serviceId, path, JSON.toJSONString(request), new InvokeCallback<String>() {
            @Override
            public void onComplete(String headerResult, final String body) {
                Log.i(TAG, "result---->: " + body);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BaseResponse response = JSON.parseObject(body, BaseResponse.class);
                        if (!response.getRespCode().equals("0")) {
                            if (response.getRespCode().equals("6")) {
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            }else{
                                Log.i(TAG, "showError: "+response.getRespMessage());
                                ToastUtils.showShort(response.getRespMessage());
                            }
                        }else{

                            resultCallback.onResult(body);
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                resultCallback.onError(e);
            }
        });
    }

    /**
     * 上传视频
     *
     * @param serviceId
     * @param context
     * @param path1          视频
     * @param path2          缩略图
     * @param request
     * @param resultCallback
     */
    public static void postVideo(final String serviceId, final Activity context, final String path1, String path2,
                                 final BaseRequest request,
                                 final ResultCallback resultCallback) {

        esbPoxy.upVideo(SERVICES_URL, serviceId, path1, path2, JSON.toJSONString(request), new InvokeCallback<String>() {
            @Override
            public void onComplete(String headerResult, final String body) {
                Log.i(TAG, "result---->: " + body);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BaseResponse response = JSON.parseObject(body, BaseResponse.class);
                        if (!response.getRespCode().equals("0")) {
                            if (response.getRespCode().equals("6")) {
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            }else{
                                Log.i(TAG, "showError: "+response.getRespMessage());
                                ToastUtils.showShort(response.getRespMessage());
                            }
                        }else{

                            resultCallback.onResult(body);
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                resultCallback.onError(e);
            }
        });
    }

    public static void downloadFile(final String url, final String saveDir, final ResultCallback resultCallback) {

        esbPoxy.download(url, saveDir, new BaseClientProxy.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                resultCallback.onResult("");
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed() {
                resultCallback.onError(null);
            }
        });
    }


}
