/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimeUtils {

    public static final String FORMAT_TIME_1 = "yyyy-MM-dd HH:mm";

    public static String formatTime(final String format, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(time);
    }
}
