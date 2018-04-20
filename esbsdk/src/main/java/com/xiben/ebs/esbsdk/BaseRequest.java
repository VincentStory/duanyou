package com.xiben.ebs.esbsdk;

/**
 * @author Lavimao
 * @date 2017/10/9
 */

public class BaseRequest {

    private String privatefield;
    private String reqtime;
    private String version;
    private String accesstoken;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getPrivatefield() {
        return privatefield;
    }

    public void setPrivatefield(String privatefield) {
        this.privatefield = privatefield;
    }

    public String getReqtime() {
        return reqtime;
    }

    public void setReqtime(String reqtime) {
        this.reqtime = reqtime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
