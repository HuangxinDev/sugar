package com.njxm.smart.mvvm

import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author huangxin
 * @date 2021/8/16
 */
interface Webservice {
    @GET("/users/{user}")
    suspend fun getUser(@Path("user") userId: String): User
}