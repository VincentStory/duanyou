package com.xiben.ebs.esbsdk.esb;

import com.xiben.ebs.esbsdk.BaseRequest;

/**
 *
 * @author Lavimao
 * @date 2017/10/10
 */

public class RefreshRequest extends BaseRequest {
    private ReqBean reqdata;

    public RefreshRequest() {
        this.reqdata = new ReqBean();
    }

    public ReqBean getReqdata() {
        return reqdata;
    }

    public void setReqdata(ReqBean reqdata) {
        this.reqdata = reqdata;
    }

    public static class ReqBean{
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String refreshToken;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getGrantType() {
            return grantType;
        }

        public void setGrantType(String grantType) {
            this.grantType = grantType;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
