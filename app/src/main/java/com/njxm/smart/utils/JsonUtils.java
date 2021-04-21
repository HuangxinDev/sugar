/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;

public final class JsonUtils {


    public static <T> T getJsonObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


    public static <T> List<T> getJsonArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

}
