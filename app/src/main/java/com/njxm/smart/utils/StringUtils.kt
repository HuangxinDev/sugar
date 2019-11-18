package com.njxm.smart.utils

/**
 * 字符串处理类
 */
object StringUtils {

    /**
     * 字符串判空
     *
     * @param paramString 判定字符
     */
    fun isEmpty(paramString: String?): Boolean {
        return paramString == null || paramString.isEmpty()
    }

    /**
     * 字符串判非空
     *
     * @param paramString 判定字符
     */
    fun isNotEmpty(paramString: String?): Boolean {
        return paramString != null && paramString.isNotEmpty()
    }
}