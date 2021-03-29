package com.njxm.smart.eventbus;

/**
 * 网络响应事件
 */
public class ResponseEvent {

    public String url;

    private boolean success;

    private int code;

    private String message;

    private String data;

    private int requestId;


    public ResponseEvent() {

    }

    public ResponseEvent(boolean success, int code, String message, String data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRequestId() {
        return this.requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
