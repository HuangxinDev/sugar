package com.njxm.smart.tools.network;

import android.util.Log;

import com.njxm.smart.eventbus.LogoutEvent;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import retrofit2.Retrofit;

public final class HttpUtils {

    private static HttpUtils sInstance = null;

    private static final Object sLock = new Object();

    private static OkHttpClient sOkHttpClient;

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


    /**
     * 普通网络请求-请求数据
     *
     * @param requestEvent
     */
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
            body = FormBody.create(MediaType.parse("application/json"),
                    requestEvent.bodyJson);
        } else {
            body = new FormBody.Builder().build();
        }

        Request request;
        if (requestEvent.httpMethod == HttpMethod.GET) {
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
                onSuccess(response, requestEvent);
            }
        });
    }

    /**
     * 上传资源文件
     *
     * @param requestEvent
     */
    public void doPostFile(RequestEvent requestEvent) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (requestEvent.parts != null && requestEvent.parts.size() > 0) {
            for (MultipartBody.Part part : requestEvent.parts) {
                builder.addPart(part);
            }
        }

        Request.Builder requestBuilder = getRequestBuilder(requestEvent.url);

        if (requestEvent.headers != null && requestEvent.headers.size() > 0) {
            for (Map.Entry<String, String> entry : requestEvent.headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        requestBuilder.post(builder.build());

        sOkHttpClient.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new ToastEvent("网络异常，请稍后再试"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onSuccess(response, requestEvent);
            }
        });
    }

    /**
     * 网络请求成功回调
     *
     * @param response
     * @param requestEvent
     */
    private void onSuccess(Response response, RequestEvent requestEvent) {
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

    /**
     * 获取默认Header信息
     *
     * @param url 请求url
     * @return 带默认头的Builder
     */
    private static Request.Builder getRequestBuilder(String url) {
        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", "application/json")
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN));
        return builder;
    }

    /**
     * 请求头信息
     *
     * @return 请求头Map
     */
    public static HashMap<String, String> getRequestHeaders() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Platform", "APP");
        map.put("Content-Type", "application/json");
        map.put("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT));
        map.put("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN));
        return map;
    }

    /**
     * 获取Retrofit的指定Api
     *
     * @param tClass 网络请求的Api
     * @param <T>    泛型
     * @return 对应类型的网络请求Api
     */
    public <T> T getApi(Class<T> tClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.136.127:7776")
                .client(sOkHttpClient)
                .build();
        return retrofit.create(tClass);
    }
}
