package com.njxm.smart.base

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

/**
 * Fragment基类
 * @author huangxin
 * @date 2021/11/5
 */
abstract class BaseFragment : Fragment() {
    protected fun finishActivity() {
        activity?.finish()
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onHandleBackPressed()
            }
        })
    }

    public fun onHandleBackPressed() {
        activity?.finish()
    }
}