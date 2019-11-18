package com.njxm.smart.utils

import android.util.Log

/**
 * 日志打印工具
 * 对android.util.Log进行了封装
 */
object LogTool {
    // 日志输出开关
    private const val DEBUG = false

    // App 日志TAG
    private const val TAG = "SmartFactory"

    private fun print(param0: Int, param1: String?, vararg param2: Any?): Boolean {
        if (!DEBUG) {
            return false
        }

        return when (param0) {
            0 -> {
                Log.i(TAG, param1)
                true
            }

            else -> false
        }
    }
}