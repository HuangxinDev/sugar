package com.njxm.smart.tools.network;

public interface HttpCallBack {

    void onSuccess(int requestId, boolean success, int code, String data);

    void onFailed(String errMsg);
}
