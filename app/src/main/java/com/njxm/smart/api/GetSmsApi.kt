package com.njxm.smart.api

import retrofit2.Converter
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Hxin on 2020/5/16
 * Function: 获取短信息
 */
interface GetSmsApi {

    @POST("/auth/sms/sendSms")
    fun sendSms(@Body converter: Converter<String, String>)
}