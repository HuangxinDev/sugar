package com.sugar.android.common;

/**
 * 日志接口
 *
 * @author huangxin
 * @date 2022/2/26
 */
public interface ILog {
    String getAppTag();

    void i(String tag, String log);

    void d(String tag, String log);

    void w(String tag, String log);

    void e(String tag, String log);
}
