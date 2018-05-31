package com.xiben.ebs.esbsdk.esb;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.xiben.ebs.esbsdk.callback.InvokeCallback;
import com.xiben.ebs.esbsdk.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import okhttp3.MultipartBody;
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

    public void invoke(String serviceAddr, String serviceId, String jsonRequest,
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
                        if (response.code() != 502)
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

    public void upImage(String serviceAddr, String serviceId, String path, String jsonRequest, final InvokeCallback<String> callback) {
        try {
            LogUtil.log(
                    "\n" + "url:" + serviceAddr + serviceId + "\n" + "path:" + path
                            + "\n" + "jsonRequest:" + jsonRequest);

            MultipartBody.Builder builder;
            if (TextUtils.isEmpty(path)) {

                builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("parameter", jsonRequest);
            } else {
                File file = new File(path);
                builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file1", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
                        .addFormDataPart("parameter", jsonRequest);
                LogUtil.log(
                        "\n" + "url:" + serviceAddr + serviceId + "\n" + "file:" + file.getName()
                                + "\n" + "jsonRequest:" + jsonRequest);

            }
            RequestBody requestBody = builder.build();

            Request request = new Request.Builder()
                    .url(serviceAddr + serviceId)
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
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
                        if (response.code() != 502)
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

    public void upVideo(String serviceAddr, String serviceId, String path, String jsonRequest, final InvokeCallback<String> callback) {
        try {
            LogUtil.log(
                    "\n" + "url:" + serviceAddr + serviceId
                            + "\n" + "jsonRequest:" + jsonRequest);

            File file = new File(path);
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file1", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .addFormDataPart("parameter", jsonRequest);

            RequestBody requestBody = builder.build();

            Request request = new Request.Builder()
                    .url(serviceAddr + serviceId)
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
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
                        if (response.code() != 502)
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

    public void upVideo(String serviceAddr, String serviceId, String path1, String path2, String jsonRequest, final InvokeCallback<String> callback) {
        try {
            LogUtil.log("\n" + "url:" + serviceAddr + serviceId + "\n" + "jsonRequest:" + jsonRequest);

            File file = new File(path1);
            File file2 = new File(path2);
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file1", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .addFormDataPart("file2", file2.getName(), RequestBody.create(MediaType.parse("image/png"), file2))
                    .addFormDataPart("parameter", jsonRequest);

            RequestBody requestBody = builder.build();

            Request request = new Request.Builder()
                    .url(serviceAddr + serviceId)
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
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
                        LogUtil.log("" + response + "-->" + result);
                        if (response.code() != 502)
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

    public static final int DOWNLOAD_FAIL = 0;
    public static final int DOWNLOAD_PROGRESS = 1;
    public static final int DOWNLOAD_SUCCESS = 2;

    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        this.listener = listener;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = DOWNLOAD_FAIL;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                //储存下载文件的目录
                String savePath = saveDir;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        //下载中
                        Message message = Message.obtain();
                        message.what = DOWNLOAD_PROGRESS;
                        message.obj = progress;
                        mHandler.sendMessage(message);

                    }
                    fos.flush();
                    //下载完成
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_SUCCESS;
                    message.obj = file.getAbsolutePath();
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_FAIL;
                    mHandler.sendMessage(message);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {

                    }
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {

                    }
                }
            }
        });
    }

    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    private String isExistDir(String saveDir) throws IOException {
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_PROGRESS:
                    listener.onDownloading((Integer) msg.obj);
                    break;
                case DOWNLOAD_FAIL:
                    listener.onDownloadFailed();
                    break;
                case DOWNLOAD_SUCCESS:
                    listener.onDownloadSuccess((String) msg.obj);
                    break;
            }
        }
    };


    OnDownloadListener listener;

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String path);

        /**
         * 下载进度
         *
         * @param progress
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
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
