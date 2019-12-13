package com.njxm.smart.utils;

import android.os.Looper;

public class AppUtils {


    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
