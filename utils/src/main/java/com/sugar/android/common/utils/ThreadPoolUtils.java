package com.sugar.android.common.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author huangxin
 * @date 2022/2/26
 */
public final class ThreadPoolUtils {
    private static ScheduledExecutorService timer = Executors.newScheduledThreadPool(2);

    private ThreadPoolUtils() {
    }

    public static Future<?> scheduleTask(Runnable runnable, long delay) {
        return timer.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    public static Future<?> schedulePeriodTask(Runnable runnable, long initDelay, long period) {
        return timer.scheduleAtFixedRate(runnable, initDelay, period, TimeUnit.MILLISECONDS);
    }
}
