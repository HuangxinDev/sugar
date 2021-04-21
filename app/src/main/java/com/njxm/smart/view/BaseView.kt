/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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