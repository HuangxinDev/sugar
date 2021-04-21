/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntDef;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.tools.network.HttpMethod;

import okhttp3.MultipartBody;

/**
 *
 */
public class RequestEvent {

    public static final int UPLOAD_FILE = 425;
    public static final int REQUEST_PARAMS = 642;
    /**
     * 上传类型
     */
    private final int type;
    public String url;
    public HttpMethod httpMethod; // 默认为POST
    public HashMap<String, String> headers;
    public HashMap<String, String> params;
    public List<MultipartBody.Part> parts;
    public String bodyJson;
    public boolean newBuilder = false;

    public RequestEvent(@RequestType int type, int requestId) {
        this.type = type;
    }

    private RequestEvent(Builder builder) {
        this.url = builder.url;
        this.headers = builder.headers;
        this.params = builder.params;
        this.type = com.njxm.smart.eventbus.RequestEvent.REQUEST_PARAMS;
        this.newBuilder = builder.newBuilder;
        this.parts = builder.parts;
        this.httpMethod = builder.method;
        if (builder.bodyJsonMap.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, String> bodyJson : builder.bodyJsonMap.entrySet()) {
                jsonObject.put(bodyJson.getKey(), bodyJson.getValue());
            }
            this.bodyJson = jsonObject.toJSONString();
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int getRequestType() {
        return this.type;
    }

    @IntDef({com.njxm.smart.eventbus.RequestEvent.UPLOAD_FILE, com.njxm.smart.eventbus.RequestEvent.REQUEST_PARAMS})
    @interface RequestType {

    }

    public static final class Builder {
        private final HashMap<String, String> headers = new HashMap<>();
        private final HashMap<String, String> params = new HashMap<>();
        private final HashMap<String, String> bodyJsonMap = new HashMap<>();
        private final List<MultipartBody.Part> parts = new ArrayList<>();
        public int requestId;
        protected boolean newBuilder = false;
        private String url;
        private String bodyJson;
        private HttpMethod method = HttpMethod.POST;

        /**
         * Get/Post
         *
         * @param method
         * @return
         */
        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder voidBuilder(boolean newBuilder) {
            this.newBuilder = newBuilder;
            return this;
        }

        public Builder addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder addParam(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        public Builder addBodyJson(String key, String value) {
            this.bodyJsonMap.put(key, value);
            return this;
        }

        public Builder addPart(MultipartBody.Part part) {
            if (part != null) {
                this.parts.add(part);
            }
            return this;
        }


        public RequestEvent build() {
            return new RequestEvent(this);
        }
    }

}
