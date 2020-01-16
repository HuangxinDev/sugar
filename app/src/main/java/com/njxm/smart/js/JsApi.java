package com.njxm.smart.js;

import android.app.Activity;
import android.util.Base64;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.service.LocationService;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import wendu.dsbridge.DWebView;

/**
 * 考勤页面Js接口, 此类变动需和服务器确认
 */
public class JsApi {
    private Activity activity;

    private DWebView mDWebView;

    public interface OnTakePhoto {
        void requestImage();
    }

    private OnTakePhoto onTakePhoto;
    private LocationService mLocationService;

    public void setTakePhoto(OnTakePhoto takePhoto) {
        this.onTakePhoto = takePhoto;
    }

    public JsApi(Activity activity, DWebView webView, LocationService locationService) {
        this.activity = activity;
        this.mDWebView = webView;
        this.mLocationService = locationService;
    }

    /**
     * 获取经纬度信息
     *
     * @return
     */
    @JavascriptInterface
    public void checkLocation(Object paramObject) {
        if (mLocationService != null) {
            if (!mLocationService.isStart()) {
                mLocationService.start();
            }
            mLocationService.requestLocation();
        }
    }

    @JavascriptInterface
    public String checkUserInfo(Object paramString) {
        JSONObject object = new JSONObject();
        object.put("code", "200");
        object.put("data", SPUtils.getStringValue("login_message"));

        LogTool.printD("Sugar2", object.toString());

        String str = object.toJSONString();

        return str;
    }

    @JavascriptInterface
    public void checkImage(Object object) {
        if (onTakePhoto != null) {
            onTakePhoto.requestImage();
        }
    }

    public void uploadImage(byte[] bytes) {
        String bitmapStr = Base64.encodeToString(bytes, Base64.DEFAULT);
        mDWebView.callHandler("h5Photos", new Object[]{bitmapStr});
        EventBus.getDefault().post(new ToastEvent("图片大小: " + bitmapStr.length()));
    }

    public void uploadLocation(String json) {
        mDWebView.callHandler("h5Location", new Object[]{json});
        if (mLocationService != null && mLocationService.isStart()) {
            mLocationService.stop();
        }
    }
}
