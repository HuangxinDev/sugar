package com.njxm.smart;

import android.app.Application;
import android.util.Log;

import com.njxm.smart.utils.LogTool;

public class SmartCloudApplication extends Application {

    private static SmartCloudApplication smartCloudApplication;

    public static Application getApplication() {
        return smartCloudApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        smartCloudApplication = this;
//        CrashReport.initCrashReport(this, "2af2871764", true);

        LogTool.enableDebug(true);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LogTool.printE("%s: \n%s", this.getClass().getSimpleName(),
                        Log.getStackTraceString(e));
            }
        });
    }
}
