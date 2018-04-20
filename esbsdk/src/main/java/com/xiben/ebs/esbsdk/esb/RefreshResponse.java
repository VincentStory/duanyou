package com.xiben.ebs.esbsdk.esb;

/**
 *
 * @author Lavimao
 * @date 2017/10/11
 */

public class RefreshResponse extends BaseResponse {
    private Resdata resdata;

    public Resdata getResdata() {
        return resdata;
    }

    public void setResdata(Resdata resdata) {
        this.resdata = resdata;
    }

    public class Resdata {
        private String accessToken;
        private String refreshToken;
        private String accessTokenExpirein;
        private String accessTokenCreateTimesTamp;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getAccessTokenExpirein() {
            return accessTokenExpirein;
        }

        public void setAccessTokenExpirein(String accessTokenExpirein) {
            this.accessTokenExpirein = accessTokenExpirein;
        }

        public String getAccessTokenCreateTimesTamp() {
            return accessTokenCreateTimesTamp;
        }

        public void setAccessTokenCreateTimesTamp(String accessTokenCreateTimesTamp) {
            this.accessTokenCreateTimesTamp = accessTokenCreateTimesTamp;
        }
    }
}
