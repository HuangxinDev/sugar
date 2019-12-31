package com.njxm.smart;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

public class SmartCloudApplication extends Application {

    private static SmartCloudApplication smartCloudApplication;

    public static Application getApplication() {
        return smartCloudApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        smartCloudApplication = this;
        SPUtils.initSharedPreferences(this);
        CrashReport.initCrashReport(getApplicationContext(), "2af2871764", true);

        LogTool.enableDebug(true);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LogTool.printE("%s: \n%s", this.getClass().getSimpleName(),
                        Log.getStackTraceString(e));
            }
        });

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(smartCloudApplication);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
