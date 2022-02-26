package com.sugar.android.common.utils;


import android.os.Handler;
import android.os.Looper;

/**
 * Handler 工具类
 *
 * @author huangxin
 * @date 2022/2/26
 */
public final class HandlerUtils {
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private HandlerUtils() {
    }

    public static void postMain(Runnable runnable) {
        if (runnable != null) {
            mainHandler.post(runnable);
        }
    }

    public static void postToMainDelay(Runnable runnable, long delay) {
        if (runnable != null) {
            mainHandler.postDelayed(runnable, delay);
        }
    }


}
