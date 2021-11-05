package com.njxm.smart;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author huangxin
 * @date 2021/7/29
 */
public class ApiResponse<T> {
    private String json;

    public List<T> transformer(Class<T> clazz) {
        try {
            return JSON.parseArray(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}
