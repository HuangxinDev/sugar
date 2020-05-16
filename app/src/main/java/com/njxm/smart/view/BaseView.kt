package com.njxm.smart.view

/**
 * Created by Hxin on 2020/5/14
 * Function: 用于限定MVP的视图View类型
 */
interface BaseView {

    /**
     * 显示加载，可以显示当前的一个Dialog和进度条
     */
    fun showLoading()

    /**
     *
     */
    fun hideLoading()


    /**
     * 错误异常[throwable]
     */
    fun onError(throwable: Throwable)


    fun onError(err: String)
}