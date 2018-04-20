package com.xiben.ebs.esbsdk.esb;

import java.io.Serializable;

/**
 * 上传附件
 * @author Admin
 * @date 2016/9/20
 */
public class AttachsEntity implements Serializable {

    /**
     *  "fk": "文件ID",
     *  "fn": "文件名带文件扩展名",
     *  "fs": "文件大小",
     *  "ft": "文件类型枚举：Image，Audio，Video，File",
     * */
    private String fk;
    private String fn;
    private String fs;
    private String ft;
    private String id;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFk() {
        return fk;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public String getFt() {
        return ft;
    }

    public void setFt(String ft) {
        this.ft = ft;
    }
}
