package com.njxm.smart;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.LocationClientOption;
import com.njxm.smart.service.LocationService;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.tencent.bugly.crashreport.CrashReport;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import androidx.multidex.MultiDex;

public class SmartCloudApplication extends Application {

    private static SmartCloudApplication smartCloudApplication;
    public LocationService locationService;
    public Vibrator mVibrator;

    public static Application getApplication() {
        return SmartCloudApplication.smartCloudApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SmartCloudApplication.smartCloudApplication = this;
        SPUtils.initSharedPreferences(this);
        CrashReport.initCrashReport(this.getApplicationContext(), "2af2871764", true);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LogTool.printE("%s: \n%s", this.getClass().getSimpleName(),
                        Log.getStackTraceString(e));
            }
        });

//        if (BuildConfig.DEBUG) {
//            ARouter.openLog();
//            ARouter.openDebug();
//        }
        ARouter.init(SmartCloudApplication.smartCloudApplication);

        this.initLocation();
    }

    private void initLocation() {
        this.locationService = new LocationService(this.getApplicationContext());
        this.mVibrator = (Vibrator) this.getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        LocationClientOption locationClientOption = com.njxm.smart.service.LocationService.getOption();
        locationClientOption.setLocationPurpose(LocationClientOption.BDLocationPurpose.SignIn);
//        locationService.setLocationOption(locationClientOption);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
