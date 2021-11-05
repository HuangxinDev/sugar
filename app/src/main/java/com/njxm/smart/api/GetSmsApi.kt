/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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