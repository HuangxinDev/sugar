package com.sugar.android.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * 活动安全类
 *
 * @author huangxin
 * @date 2022/2/27
 */
public final class ActivityUtils {
    private static final String TAG = "ActivityUtils";

    private ActivityUtils() {
    }

    public static void startActivity(Context context, Intent intent) {
        if (context == null) {
            Logger.e(TAG, "startSafeActivity context is null.");
            return;
        }
        if (intent == null) {
            Logger.e(TAG, "startSafeActivity intent is null.");
            return;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.resolveActivity(intent, 0) == null) {
            Logger.e(TAG, "startSafeActivity activity is absent.");
            return;
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.e(TAG, "startSafeActivity start error.");
        }
    }
}
