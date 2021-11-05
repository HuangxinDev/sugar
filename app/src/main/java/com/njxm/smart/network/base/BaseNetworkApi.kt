package com.njxm.smart.network.base

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit

/**
 *
 * @author huangxin
 * @date 2021/9/10
 */
abstract class BaseNetworkApi<I>(private val baseUrl: String) : IService<I> {
    protected val service: I by lazy {
        getRetrofit()
    }

    protected fun getRetrofit(): I {
        val build = Retrofit.Builder()
        build.baseUrl(baseUrl)
        return build.build().create(getServiceCLass());
    }

    protected fun getResult(): BaseResponse<*>? {
        return null
    }


    protected fun getServiceCLass(): Class<I> {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        return type.actualTypeArguments[0] as Class<I>
    }

    private fun getOkhttpClient(): OkHttpClient {
        return getCommonOKHttpClient() ?: defaultClient;
    }

    private fun getCommonOKHttpClient(): OkHttpClient? {
        return null
    }

    private val defaultClient: OkHttpClient by lazy {
        val client = OkHttpClient.Builder().callTimeout(2000, TimeUnit.MILLISECONDS).build()
        return@lazy client
    }

}