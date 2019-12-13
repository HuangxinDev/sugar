package com.njxm.smart.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.LruCache;

/**
 * SharedPreference工具类
 */
public final class SPUtils {

    private static SharedPreferences sSharedPreferences;
    private static LruCache<String, String> sLruCache;
    private static boolean init = false;

    public static boolean initSharedPreferences(Context context) {
        if (sSharedPreferences == null) {
            sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            init = true;
        }

        sLruCache = new LruCache<String, String>(4 * 1024 * 1024) {
            @Override
            protected int sizeOf(String key, String value) {
                return value.length() * 2 + 40;
            }
        };

        return sSharedPreferences != null;
    }

    public static void putValue(String key, String value) {
        if (StringUtils.isEmpty(key) || value == null) {
            return;
        }
        sLruCache.put(key, value);

        if (init) {
            SharedPreferences.Editor editor = sSharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    public static String getStringValue(String key) {
        return getValue(key, "");
    }

    public static String getValue(String key, String defValue) {
        String result = sLruCache.get(key);
        if (StringUtils.isEmpty(result) && init) {
            result = sSharedPreferences.getString(key, defValue);
        }
        if (StringUtils.isEmpty(result)) {
            return defValue;
        }

        return result;
    }

//    public static <T> T getValue(Context context, String key, T def) {
//        if (context == null || StringUtils.isEmpty(key)) {
//            return null;
//        }
//
//        if (def instanceof Boolean) {
//            return sSharedPreferences.getBoolean(key, false);
//        } else if (def instanceof String) {
//            sSharedPreferences.getString(key, (String) def);
//        } else if (def instanceof Integer) {
//            sSharedPreferences.getInt(key, (Integer) def);
//        } else if (def instanceof Long) {
//            sSharedPreferences.getLong(key, (Long) def);
//        } else {
//            throw new IllegalArgumentException("不支持该类型的参数");
//        }
//
//        return null;
//    }

}
