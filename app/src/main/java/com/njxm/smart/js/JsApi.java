package com.njxm.smart.js;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.widget.DatePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.activities.BaseActivity;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;

import java.util.Calendar;

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

    public void setTakePhoto(OnTakePhoto takePhoto) {
        this.onTakePhoto = takePhoto;
    }

    public JsApi(Activity activity, DWebView webView) {
        this.activity = activity;
        this.mDWebView = webView;
    }

    /**
     * 获取经纬度信息
     *
     * @return
     */
    @JavascriptInterface
    public String checkLocation(Object paramObject) {
        JSONObject object = new JSONObject();
        object.put("x", "116.39564504");
        object.put("y", "39.92998578");
        return object.toJSONString();
    }

    /**
     * 返回当前时间
     *
     * @return
     */
    @JavascriptInterface
    public void checkTimestamp(Object time) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(Long.parseLong((String) time));

        LogTool.printD("JsApi", "invoke checkTimestamp");

        DatePickerDialog dialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                calendar.getTimeInMillis();
                uploadTime(String.valueOf(calendar.getTimeInMillis()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }


    @JavascriptInterface
    public String checkUserInfo(Object paramString) {
        JSONObject object = new JSONObject();
        object.put("code", "200");
        object.put("data", SPUtils.getStringValue("login_message"));
        return object.toJSONString();
    }

    @JavascriptInterface
    public void checkImage(Object object) {
        if (onTakePhoto != null) {
            onTakePhoto.requestImage();
        }
    }

    private void uploadTime(String time) {
        mDWebView.callHandler("h5DateTimePicker", new Object[]{time});
    }

    public void uploadImage(byte[] bytes) {
        String bitmapStr = Base64.encodeToString(bytes, Base64.DEFAULT);
        mDWebView.callHandler("h5Photos", new Object[]{bitmapStr});
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).invoke(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "图片大小: " + bitmapStr.length(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
