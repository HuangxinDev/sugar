package com.njxm.smart.js;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.utils.SPUtils;

/**
 * 考勤页面Js接口, 此类变动需和服务器确认
 */
public class CheckJsApi {

    public interface OnCheckJsApiListener {
        void requestImage();
        void requestLocation();
    }

    private OnCheckJsApiListener listener;

    public void setCheckJsApiListener(OnCheckJsApiListener listener) {
        this.listener = listener;
    }

    /**
     * 请求经纬度信息
     */
    @JavascriptInterface
    public void checkLocation(Object paramObject) {
        if (listener != null) {
            listener.requestLocation();
        }
    }

    /**
     * 请求用户信息
     *
     * @param paramString 登录返回的信息
     * @return 个人信息json数据
     */
    @JavascriptInterface
    public String checkUserInfo(Object paramString) {
        JSONObject object = new JSONObject();
        object.put("code", "200");
        object.put("data", SPUtils.getStringValue("login_message"));
        return object.toJSONString();
    }

    /**
     * 请求相机拍照
     *
     * @param object 默认参数
     */
    @JavascriptInterface
    public void checkImage(Object object) {
        if (listener != null) {
            listener.requestImage();
        }
    }
}
