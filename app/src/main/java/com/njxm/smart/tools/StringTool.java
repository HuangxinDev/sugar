/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.tools;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * 对一段字符串做处理，譬如对一段文字中部分文字颜色、字体大小、背景做处理.
 * <p>
 * 具体可以查看 Android 富文本的开发，此处提供一些简单的功能
 */
public final class StringTool {

    /**
     * 更改指定区间的文字颜色
     */
    public static SpannableString getTextColor(String text, int color, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 更改指定区间文字的字体大小
     *
     * @param text
     * @param size
     * @param start
     * @param end
     * @return
     */
    public static SpannableString getTextSize(String text, int size, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new AbsoluteSizeSpan(size, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
