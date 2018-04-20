package com.xiben.ebs.esbsdk.esb;

/**
 *
 * @author Lavimao
 * @date 2017/10/24
 */

public class GetUpLoadTokenResponse extends BaseResponse {
    private Resdata resdata;

    public Resdata getResdata() {
        return resdata;
    }

    public void setResdata(Resdata resdata) {
        this.resdata = resdata;
    }

    public class Resdata {
        private String uptoken;

        public String getUptoken() {
            return uptoken;
        }

        public void setUptoken(String uptoken) {
            this.uptoken = uptoken;
        }
    }
}
