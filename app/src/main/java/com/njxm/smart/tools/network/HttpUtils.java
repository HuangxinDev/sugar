package com.njxm.smart.tools.network;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.eventbus.LogoutEvent;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.utils.JsonUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public final class HttpUtils {

    private static HttpUtils sInstance = null;

    private static final Object sLock = new Object();

    private static OkHttpClient sOkHttpClient;

    // SMS接口
    public static final int REQUEST_SMS = 2;

    ///////////////////////////////////////////////////////////////////////////
    // 单例模式，避免创建多个HttpClient
    ///////////////////////////////////////////////////////////////////////////
    private HttpUtils() {
        //no instance
        sOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .protocols(Util.immutableList(Protocol.HTTP_2, Protocol.HTTP_1_1))
                .build();
    }

    public static HttpUtils getInstance() {

        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new HttpUtils();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取OKHttpClient实例
     *
     * @return OKHttpClient实例
     */
    public OkHttpClient getOkHttpClient() {
        return sOkHttpClient;
    }


    public void request(RequestEvent requestEvent) {
        RequestBody body;
        StringBuilder url = new StringBuilder(requestEvent.url);
        if (requestEvent.params != null && requestEvent.params.size() > 0) {
            url.append("?");
            for (Map.Entry<String, String> entry : requestEvent.params.entrySet()) {
                url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            url.deleteCharAt(url.length() - 1);
        }

        Request.Builder builder = getRequestBuilder(url.toString());

        if (requestEvent.headers != null && requestEvent.headers.size() > 0) {
            for (Map.Entry<String, String> entry : requestEvent.headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if (StringUtils.isNotEmpty(requestEvent.bodyJson)) {
            body = FormBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE),
                    requestEvent.bodyJson);
        } else {
            body = new FormBody.Builder().build();
        }

        Request request;
        if (requestEvent.requestMethod.equals("GET")) {
            request = builder.build();
        } else {
            request = builder.post(body).build();
        }

        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new ToastEvent("网络异常，请稍后再试"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseEvent responseEvent = JsonUtils.getJsonObject(response.body().string(),
                            ResponseEvent.class);
                    responseEvent.setUrl(requestEvent.url);

                    if (!responseEvent.isSuccess()) {
                        EventBus.getDefault().post(new ToastEvent(responseEvent.getMessage()));
                        return;
                    }

                    int code = responseEvent.getCode();
                    if (code == 401 || code == 999) {
                        // 身份信息过期,需要重新登录
                        EventBus.getDefault().post(new LogoutEvent());
                        EventBus.getDefault().post(new ToastEvent("身份信息过期,需要重新登录"));
                        return;
                    }
                    EventBus.getDefault().post(responseEvent);
                } catch (IOException e) {
                    LogTool.printE(Log.getStackTraceString(e));
                }
            }
        });
    }

    public static void doPostFile(RequestEvent requestEvent) {

    }

    public void postData(final int requestId, final Request request, final HttpCallBack callBack) {
        sOkHttpClient.newCall(request).enqueue(new OKHttpCallback(requestId, callBack));
    }

    /**
     * 获取默认Header信息
     *
     * @param url 请求url
     * @return 带默认头的Builder
     */
    public static Request.Builder getRequestBuilder(String url) {
        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN));
        return builder;
    }

    public static Headers getPostHeaders() {
        Headers.Builder builder = new Headers.Builder();
        builder.add("Platform", "APP")
                .add("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .add("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .add("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN));
        return builder.build();
    }

    public void postDataWithBody(final int requestId, String url,
                                 HashMap<String, String> paramMaps, final HttpCallBack callBack) {

        JSONObject body = new JSONObject();
        if (paramMaps != null && paramMaps.size() > 0) {
            for (Map.Entry<String, String> entry : paramMaps.entrySet()) {
                body.put(entry.getKey(), entry.getValue());
            }
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE),
                body.toString());

        Request request = new Request.Builder().url(url)
                .header("Content-Type", "application/json")
                .post(requestBody)
                .build();

        sOkHttpClient.newCall(request).enqueue(new OKHttpCallback(callBack));

    }

    /**
     * 获取body里面放json的Request
     */
    public static Request getJsonRequest(String url, HashMap<String, String> jsonMap) {
        JSONObject object = new JSONObject();
        if (jsonMap != null && jsonMap.size() > 0) {
            for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                object.put(entry.getKey(), entry.getValue());
            }
        }

        RequestBody requestBody = FormBody.create(MediaType.parse(MimeType.JSON),
                object.toJSONString());

        return new Request.Builder()
                .url(url)
                .headers(getPostHeaders())
                .post(requestBody)
                .build();
    }

    /**
     * 网络回调
     */
    private static class OKHttpCallback implements Callback {

        private HttpCallBack httpCallBack;
        private int requestId;

        private OKHttpCallback(HttpCallBack httpCallBack) {
            this.httpCallBack = httpCallBack;
        }

        private OKHttpCallback(int requestId, HttpCallBack httpCallBack) {
            this.requestId = requestId;
            this.httpCallBack = httpCallBack;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            LogTool.printE("url: %s, exception: %s", call.request().url(), e.getMessage());
            EventBus.getDefault().post(new ToastEvent("网络异常,稍后再试"));
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try {
                String result = response.body().string();
                JSONObject resultObj = JSONObject.parseObject(result);
                boolean success = resultObj.getBoolean("success");

                if (httpCallBack == null) {
                    return;
                }

                int code = 0;
                code = resultObj.getInteger("code");
                LogTool.printD("url: %s, code: %s, content: %s", call.request().url(), code,
                        resultObj.getString((code == 200) ? "data" : "message"));
                if (success && code == 200) {
                    String data = resultObj.getString("data");
                    if (data.equals("[]") || data.equals("null")) {
                        data = "{}";
                    }
                    httpCallBack.onSuccess(requestId, true, 200, data);
                } else if (code == 401 || code == 999) {
                    EventBus.getDefault().post(new LogoutEvent());
                } else {
                    httpCallBack.onFailed(resultObj.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                EventBus.getDefault().post(new ToastEvent("数据解析异常"));
            }
        }
    }


    public static final class MimeType {
        public static final String JSON = "application/json";
        public static final String TEXT = "application/x-www-form-urlencoded";
    }

}
