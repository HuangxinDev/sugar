/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.njxm.smart

import android.app.Application
import android.content.Context
import android.os.Vibrator
import android.util.Log
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.location.LocationClientOption
import com.njxm.smart.service.LocationService
import com.njxm.smart.utils.LogTool
import com.njxm.smart.utils.SPUtils
import com.tencent.bugly.crashreport.CrashReport

class SmartCloudApplication : Application() {
    @JvmField
    var locationService: LocationService? = null
    var mVibrator: Vibrator? = null
    override fun onCreate() {
        super.onCreate()
//        AppInitializer.getInstance(this).initializeComponent(LocationInitialization::class)

        smartCloudApplication = this
        SPUtils.initSharedPreferences(this)
        CrashReport.initCrashReport(this.applicationContext, "2af2871764", true)
        Thread.setDefaultUncaughtExceptionHandler(object : Thread.UncaughtExceptionHandler {
            override fun uncaughtException(t: Thread, e: Throwable) {
                LogTool.printE(
                    "%s: \n%s", this.javaClass.simpleName,
                    Log.getStackTraceString(e)
                )
            }
        })

        ARouter.init(smartCloudApplication)
        initLocation()
    }

    private fun initLocation() {
        locationService = LocationService(this.applicationContext)
        mVibrator = this.applicationContext.getSystemService(VIBRATOR_SERVICE) as Vibrator
        val locationClientOption = LocationService.getOption()
        locationClientOption.setLocationPurpose(LocationClientOption.BDLocationPurpose.SignIn)
        //        locationService.setLocationOption(locationClientOption);
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        private var smartCloudApplication: SmartCloudApplication? = null

        @JvmStatic
        val application: Application?
            get() = smartCloudApplication
    }
}