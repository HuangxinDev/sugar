/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

import android.content.Context;
import android.os.Looper;

import com.ntxm.smart.BuildConfig;
import com.ntxm.smart.R;

/**
 * App常用工具类
 */
public class AppUtils {

    /**
     * 判断是否是主线程
     *
     * @return return true if current thread is main thread, else return false
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static String getAppName(Context context) {
        return context.getString(R.string.app_name);
    }

    public static String getAppVersion() {
        return BuildConfig.VERSION_NAME;
    }

}
