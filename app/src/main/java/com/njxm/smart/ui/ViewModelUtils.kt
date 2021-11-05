package com.njxm.smart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * ViewModel工具类
 * @author huangxin
 * @date 2021/8/6
 */


object ViewModelUtils {
    fun <T : ViewModel> findViewModel(owner: ViewModelStoreOwner, viewModel: Class<T>): T {
        return ViewModelProvider(owner)[viewModel]
    }
}


