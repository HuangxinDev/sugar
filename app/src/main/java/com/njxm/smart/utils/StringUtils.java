/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

/**
 * 字符串处理类
 */
public final class StringUtils {

    /**
     * 字符串判空
     *
     * @param paramString 判定字符
     */
    public static boolean isEmpty(String paramString) {
        return paramString == null || paramString.trim().isEmpty();
    }

    /**
     * 字符串判非空
     *
     * @param paramString 判定字符
     */
    public static boolean isNotEmpty(String paramString) {
        return paramString != null && paramString.trim().length() != 0;
    }
}