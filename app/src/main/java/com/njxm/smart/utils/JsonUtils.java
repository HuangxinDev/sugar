package com.njxm.smart.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

public final class JsonUtils {


    public static <T> T getJsonObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


    public static <T> List<T> getJsonArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

}
