package com.xiben.ebs.esbsdk.callback;


import com.xiben.ebs.esbsdk.esb.AttachsEntity;

import java.util.List;

/**
 *
 * @author Lavimao
 * @date 2017/10/9
 */

public abstract class AttanchsResultCallback {

    /**
     * 文件进度
     * @param percent
     * @param index
     * @param fileName
     */
    public void onProgress(double percent, int index, String fileName){}

    /**
     * 文件上传成功后获得对应的对象数组
     * @param attachs 文件数组对象
     */
    public abstract void onResult(List<AttachsEntity> attachs);

    /**
     * 报错信息
     * @param ex
     */
    public abstract void onError(Exception ex);
}
