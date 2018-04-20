package com.xiben.ebs.esbsdk.esb;

import com.xiben.ebs.esbsdk.BaseRequest;

/**
 *
 * @author Lavimao
 * @date 2017/10/30
 */

public class GetUpLoadTokenRequest extends BaseRequest {
    private Reqdata reqdata;

    public GetUpLoadTokenRequest() {
        this.reqdata = new Reqdata();
    }

    public Reqdata getReqdata() {
        return reqdata;
    }

    public void setReqdata(Reqdata reqdata) {
        this.reqdata = reqdata;
    }


    public static class Reqdata {
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
