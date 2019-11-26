package com.njxm.smart.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreference工具类
 */
public final class SPUtils {

    private static SharedPreferences sSharedPreferences;

    private static boolean initSharedPreferences(Context context) {
        if (sSharedPreferences == null) {
            sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return sSharedPreferences != null;
    }

    public static boolean putVaule(Context context, String key, Object object) {
        if (context == null || StringUtils.isEmpty(key) || object == null || !initSharedPreferences(context)) {
            return false;
        }

        SharedPreferences.Editor editor = sSharedPreferences.edit();
        if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            throw new IllegalArgumentException("不支持该类型的参数");
        }
        return editor.commit();
    }

//    public static <T> T getValue(Context context, String key, T def) {
//        if (context == null || StringUtils.isEmpty(key)) {
//            return null;
//        }
//
//        if (def instanceof Boolean) {
//            return (T) sSharedPreferences.getBoolean(key, def);
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
