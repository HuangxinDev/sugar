package com.sugar.android.common.utils;

import android.util.Log;

/**
 * 默认打印工具
 *
 * @author huangxin
 * @date 2022/2/26
 */
public class DefaultLogTool implements ILog {
    @Override
    public String getAppTag() {
        return "App";
    }

    @Override
    public void i(String tag, String log) {
        Log.i(constructorTag(tag), log);
    }

    @Override
    public void d(String tag, String log) {
        Log.d(constructorTag(tag), log);
    }

    @Override
    public void w(String tag, String log) {
        Log.w(constructorTag(tag), log);
    }

    @Override
    public void e(String tag, String log) {
        Log.e(constructorTag(tag), log);
    }

    private String constructorTag(String tag) {
        return String.format("[%s] %s", getAppTag(), tag);
    }
}
