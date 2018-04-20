package com.xiben.ebs.esbsdk.callback;

/**
 * @author Lavimao
 * @date 2017/10/9
 */

public abstract class ResultCallback {
    /**
     * 返回Header为ESB_SUCCESS的body信息
     *
     * @param jsonResult
     */
    public abstract void onResult(String jsonResult);

    /**
     * 返回Header为ESB_SUCCESS的body信息  非ui线程
     *
     * @param jsonResult
     */
    public void onResultThread(String jsonResult){};


    /**
     * 报错信息
     *
     * @param ex
     */
    public abstract void onError(Exception ex);





}
