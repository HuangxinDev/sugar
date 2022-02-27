/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.sugar.android.common.utils;

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

    public static void putBoolean(String key, boolean value) {
        sSharedPreferences.edit().putBoolean(key, value);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sSharedPreferences.getBoolean(key, defaultValue);
    }
}
