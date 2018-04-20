package com.xiben.ebs.esbsdk.esb;

import java.io.Serializable;

/**
 *
 * @author Xugang
 * @date 2016/8/11
 * 实体基类
 */
public class BaseResponse implements Serializable {

    private String privatefield;
    private String restime;
    private int code;
    private String msg;


    public String getPrivatefield() {
        return privatefield;
    }

    public void setPrivatefield(String privatefield) {
        this.privatefield = privatefield;
    }

    public String getRestime() {
        return restime;
    }

    public void setRestime(String restime) {
        this.restime = restime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "privatefield='" + privatefield + '\'' +
                ", restime='" + restime + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}

