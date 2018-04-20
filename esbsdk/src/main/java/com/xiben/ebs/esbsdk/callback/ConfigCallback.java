package com.xiben.ebs.esbsdk.callback;

/**
 *
 * @author Lavimao
 * @date 2017/10/9
 */

public interface ConfigCallback {
    /**
     * Header中的客户端ID
     * @return
     */
    String getClientId();

    /**
     * Header中的临时accesstoken
     * @return
     */
    String getAccessToken();

    /**
     * RefreshToken
     * @return
     */
    String getRefreshToken();


}
