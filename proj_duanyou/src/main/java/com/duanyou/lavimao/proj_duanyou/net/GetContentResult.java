package com.duanyou.lavimao.proj_duanyou.net;

public interface GetContentResult {
    void success(String json);

    void error(Exception ex);
}
