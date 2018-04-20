package com.xiben.ebs.esbsdk.esb;

import android.annotation.SuppressLint;


import com.xiben.ebs.esbsdk.callback.InvokeCallback;
import com.xiben.ebs.esbsdk.util.LogUtil;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class BaseClientProxy {
    private OkHttpClient client;
    private Call call;




    public BaseClientProxy() {
        initHttp();
    }

    /**
     * 设置证书
     */
    private void initHttp() {
        //初始化OkHttp
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .build();

    }

    public void invoke( String serviceAddr, String serviceId, String jsonRequest,
                       final InvokeCallback<String> callback) {
        try {
            LogUtil.log(


                    "\n" + "url:" + serviceAddr + serviceId
                            + "\n" + "jsonRequest:" + jsonRequest);


            // Create request for remote resource.
            final Request request = new Request.Builder()
                    .url(serviceAddr + serviceId)
                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonRequest))
                    .build();
            call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.log("onFailure Exception:" + e.toString());
                    callback.onError(e);

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        LogUtil.log("" + response + "==>" + result);
                        callback.onComplete("", result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


        } catch (Exception e) {
            LogUtil.log("Exception:" + e.toString());
            callback.onError(e);
        }
    }


    /**
     * 默认信任所有的证书 *
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


    /**
     * 取消网络请求
     */
    public void cancel() {
        if (call.isExecuted()) {
            call.cancel();
        }
    }

}
