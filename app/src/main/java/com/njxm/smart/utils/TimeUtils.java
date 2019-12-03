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
