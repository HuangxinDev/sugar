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