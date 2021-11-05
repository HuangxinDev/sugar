package com.njxm.smart

import java.text.MessageFormat

/**
 * 测试环境全局变量
 */
object GlobalConst {
    /**
     * Http 协议
     */
    private const val HTTP_PROTOCOL = "http"

    /**
     * Url格式
     */
    private const val HOST_MESSAGE = "{1}://{2}:{3}"

    /**
     * H5 URL
     */
    val h5UrlPrefix: String
        get() = MessageFormat.format(HOST_MESSAGE, HTTP_PROTOCOL, HttpConstant.HOST, HttpConstant.PORT_H5)

    /**
     * Biz URL
     */
    val bizUrl: String
        get() = MessageFormat.format(HOST_MESSAGE, HTTP_PROTOCOL, HttpConstant.BIZ_PORT)

    /**
     * VER URL
     */
    val keyVerify: String
        get() = MessageFormat.format(HOST_MESSAGE, HTTP_PROTOCOL, HttpConstant.VER_PORT)
}