/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.njxm.smart.base

import java.lang.ref.WeakReference

/**
 * Created by Hxin on 2021/4/2 Function:
 */
abstract class BasePresenter<V : BaseView?> {
    private var viewReference: WeakReference<V>? = null
    fun attachView(view: V) {
        viewReference = WeakReference(view)
    }

    val view: V?
        get() = viewReference?.get()
    val isAttached: Boolean
        get() = view != null

    fun detachView() {
        viewReference?.clear()
    }
}