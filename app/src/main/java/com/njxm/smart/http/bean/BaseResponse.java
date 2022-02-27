package com.njxm.smart.http.bean;

/**
 * @author huangxin
 * @date 2022/2/27
 */
public class BaseResponse<T> {
    private boolean isSuccess;

    private int code;

    private String error;

    private T data;

    public BaseResponse() {
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
