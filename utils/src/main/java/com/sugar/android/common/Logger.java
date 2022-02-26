package com.sugar.android.common;

/**
 * 日志打印工具
 *
 * @author huangxin
 * @date 2022/2/26
 */
public final class Logger {
    private static ILog logTool;

    static {
        setLogTool(new DefaultLogTool());
    }

    private Logger() {
    }

    public static void setLogTool(ILog logTool) {
        Logger.logTool = logTool;
    }

    public static void i(String tag, String log) {
        logTool.i(tag, log);
    }

    public static void d(String tag, String log) {
        logTool.d(tag, log);
    }

    public static void w(String tag, String log) {
        logTool.w(tag, log);
    }

    public static void e(String tag, String log) {
        logTool.e(tag, log);
    }
}
