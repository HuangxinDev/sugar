package com.njxm.smart.bean;

/**
 * Created by Hxin on 2020/3/28
 * Function: Http请求参数
 */
public class ServerResponseBean<T> {

    /**
     * success : false
     * code : 301
     * message : 验证码已超时，请重新获取验证码
     * data : []
     */

    private boolean success;
    private int code;
    private String message;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
