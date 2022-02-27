package com.sugar.android.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭工具类
 *
 * @author huangxin
 * @date 2022/2/27
 */
public final class CloseUtils {
    private static final String TAG = "CloseUtils";

    private CloseUtils() {
    }

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            Logger.e(TAG, "close exception.");
        }
    }
}
