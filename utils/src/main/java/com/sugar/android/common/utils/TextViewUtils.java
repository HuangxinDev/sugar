package com.sugar.android.common.utils;

import android.widget.TextView;

import androidx.annotation.StringRes;

/**
 * 文本视图工具类
 *
 * @author huangxin
 * @date 2022/2/26
 */
public final class TextViewUtils {
    private TextViewUtils() {
    }

    /**
     * 设置文本
     *
     * @param view 文本控件
     * @param text 文本内容
     */
    public static void setText(TextView view, String text) {
        if (view != null) {
            view.setText(text);
        }
    }

    /**
     * 设置文本
     *
     * @param view     文本控件
     * @param stringId 字符串 id
     */
    public static void setText(TextView view, @StringRes int stringId) {
        if (view != null) {
            view.setText(stringId);
        }
    }
}
