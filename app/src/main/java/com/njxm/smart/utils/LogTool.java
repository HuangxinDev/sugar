package com.njxm.smart.utils;

import android.util.Log;

import com.ntxm.smart.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

/**
 * 日志打印工具
 * 对android.util.Log进行了封装
 */
public final class LogTool {
    // 日志输出开关
    private static boolean DEBUG = true;

    // App 日志TAG
    private static final String TAG = "SmartFactory";

    /**
     * 是否允许打印日志
     */
    public static void enableDebug() {
        if (BuildConfig.DEBUG) {
            printW("[%s] %s", TAG, "项目DEBUG模式开关已打开, 发版请将其关闭");
        }
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

        String tempStr = (format == null) ? "null" : ((objects != null && objects.length != 0) ?
                String.format(Locale.US, format, objects) : format);

        switch (level) {
            case Log.INFO:
                Log.i(TAG, tempStr);
                return true;
            case Log.DEBUG:
                Log.d(TAG, tempStr);
                return true;
            case Log.WARN:
                Log.w(TAG, tempStr);
                return true;
            case Log.ERROR:
                Log.e(TAG, tempStr);
                return true;
            default:
                return false;
        }
    }

    /**
     * 打印Debug日志
     *
     * @param format  string or string format
     * @param objects null or 相应格式对应的值
     * @return 是否成功打印
     */
    public static boolean printD(String format, Object... objects) {
        return print(Log.DEBUG, format, objects);
    }

    /**
     * 打印Info日志
     *
     * @param format  string or string format
     * @param objects null or 相应格式对应的值
     * @return 是否成功打印
     */
    public static boolean printI(String format, Object... objects) {
        return print(Log.INFO, format, objects);
    }

    public static boolean printD(String tag, String format, Object... objects) {
        return print(Log.DEBUG, "[ %s ] %s ", tag, format, objects);
    }

    /**
     * 打印Warn日志
     *
     * @param format  string or string format
     * @param objects null or 相应格式对应的值
     * @return 是否成功打印
     */
    public static boolean printW(String format, Object... objects) {
        return print(Log.WARN, format, objects);
    }

    /**
     * 打印Error日志
     *
     * @param format  string or string format
     * @param objects null or 相应格式对应的值
     * @return 是否成功打印
     */
//    public static boolean printE(String format, Object... objects) {
//        return print(Log.ERROR, format, objects);
//    }
    public static boolean printE(String tag, String format, Object... objects) {
        return print(Log.ERROR, String.format(Locale.CHINA, "[%s] %s", tag, format), objects);
    }


    /**
     * 打印堆栈信息
     *
     * @param paramThrowable 异常堆栈
     * @return 是否打印成功
     */
    public static boolean printStack(Throwable paramThrowable) {
        if (!DEBUG) {
            return false;
        }

        return print(3, getStackTrace(paramThrowable));
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
            return "failed";
        }
    }

}