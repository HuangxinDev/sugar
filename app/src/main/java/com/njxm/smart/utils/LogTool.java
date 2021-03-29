package com.njxm.smart.utils;

import com.ntxm.smart.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志打印工具
 * 对android.util.Log进行了封装
 */
public final class LogTool {
    // App 日志TAG
    private static final String TAG = "SmartFactory";

    private LogTool() {

    }

    /**
     * 日志打印
     *
     * @param level   日志等级
     * @param format  format格式
     * @param objects 可伸缩变量
     */
    private static boolean print(int level, String format, Object... objects) {
        if (!BuildConfig.DEBUG) {
            return false;
        }

        if (TextUtils.isEmpty(format)) {
            format = "format格式信息不对";
        }

        if (objects != null && objects.length > 0) {
            format = String.format(Locale.US, format, objects);
        }

        switch (level) {
            case Log.INFO:
                Log.i(LogTool.TAG, format);
                return true;
            case Log.DEBUG:
                Log.d(LogTool.TAG, format);
                return true;
            case Log.WARN:
                Log.w(LogTool.TAG, format);
                return true;
            case Log.ERROR:
                Log.e(LogTool.TAG, format);
                return true;
            default:
                return false;
        }
    }

    private static String formatTag(Class<?> klass, String format) {
        return String.format(Locale.US, "[%s] %s", klass.getSimpleName(), format);
    }

    /**
     * 打印Debug日志
     *
     * @param format  string or string format
     * @param objects null or 相应格式对应的值
     * @return 是否成功打印
     */
    public static boolean printD(String format, Object... objects) {
        return LogTool.print(Log.DEBUG, format, objects);
    }

    public static boolean printD(Class<?> klass, String format, Object... objects) {
        return LogTool.print(Log.DEBUG, LogTool.formatTag(klass, format), objects);
    }

    /**
     * 打印Info日志
     *
     * @param format  string or string format
     * @param objects null or 相应格式对应的值
     * @return 是否成功打印
     */
    public static boolean printI(String format, Object... objects) {
        return LogTool.print(Log.INFO, format, objects);
    }

    public static boolean printI(Class<?> klass, String format, Object... objects) {
        return LogTool.print(Log.INFO, LogTool.formatTag(klass, format), objects);
    }


    /**
     * 打印Warn日志
     *
     * @param format  string or string format
     * @param objects null or 相应格式对应的值
     * @return 是否成功打印
     */
    private static boolean printW(String format, Object... objects) {
        return LogTool.print(Log.WARN, format, objects);
    }

    public static boolean printW(Class<?> klass, String format, Object... objects) {
        return LogTool.print(Log.WARN, LogTool.formatTag(klass, format), objects);
    }

    /**
     * 打印Error日志
     *
     * @param format  string or string format
     * @param objects null or 相应格式对应的值
     * @return 是否成功打印
     */
    public static boolean printE(String format, Object... objects) {
        return LogTool.print(Log.ERROR, format, objects);
    }

    public static boolean printE(Class<?> klass, String format, Object... objects) {
        return LogTool.print(Log.ERROR, LogTool.formatTag(klass, format), objects);
    }


    /**
     * 打印堆栈信息
     *
     * @param paramThrowable 异常堆栈
     * @return 是否打印成功
     */
    static boolean printStack(Throwable paramThrowable) {
        if (!BuildConfig.DEBUG) {
            return false;
        }
        String errMsg = LogTool.getStackTrace(paramThrowable);

        return LogTool.print(Log.ERROR, errMsg);
    }

    /**
     * 将异常堆栈转化为String字符
     *
     * @param paramThrowable 异常堆栈
     * @return 字符串
     */
    private static String getStackTrace(Throwable paramThrowable) {
        if (paramThrowable == null) {
            return "";
        }

        try {
            StringWriter writer = new StringWriter();
            paramThrowable.printStackTrace(new PrintWriter(writer));
            return writer.getBuffer().toString();
        } catch (Throwable throwable) {
            if (!LogTool.printStack(throwable)) {
                throwable.printStackTrace();
            }

            return "failed";
        }
    }
}