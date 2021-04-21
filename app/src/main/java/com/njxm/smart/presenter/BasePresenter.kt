/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.presenter

import androidx.annotation.UiThread
import com.njxm.smart.view.BaseView

/**
 * Created by Hxin on 2020/5/14
 * Function: 基础Presenter
 */
abstract class BasePresenter<T : BaseView>(protected val TAG: String = BasePresenter::class.java.simpleName) {

    protected var mView: T? = null

    @UiThread
    fun attachView(paramView: T): Unit {
        this.mView = paramView
    }

    @UiThread
    fun detachView() {
        this.mView = null
    }

    fun isViewAttached() = mView != null
}