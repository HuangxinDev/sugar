package com.njxm.smart.network.base

/**
 *
 * @author huangxin
 * @date 2021/9/12
 */
class BaseResponse<T> {
    private var code: Int = 0;
    private var message: String = ""
    private val value: T? = null
}