package com.njxm.smart.utils;

import android.os.Looper;

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
}
