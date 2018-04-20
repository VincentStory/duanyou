package com.xiben.ebs.esbsdk.callback;

/**
 *
 * @author Lavimao
 * @date 2017/10/9
 */
public interface InvokeCallback<T> {
    /**
     *  返回接口返回的数据
     * @param headerResult Header的部分
     * @param body  body的部分
     */
    void onComplete(T headerResult, T body);

    /**
     * 请求接口的报错信息
     * @param e
     */
    void onError(Exception e);
}
