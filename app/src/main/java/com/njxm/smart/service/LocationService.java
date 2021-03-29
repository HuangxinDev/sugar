package com.njxm.smart.service;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

/**
 * 定位服务LocationClient 相关
 *
 * @author baidu
 */
public class LocationService {
    private static LocationClient client = null;
    private static LocationClientOption mOption;
    private static LocationClientOption DIYoption;
    private final Object objLock = new Object();

    /***
     * 初始化 LocationClient
     *
     * @param locationContext
     */
    public LocationService(Context locationContext) {
        synchronized (this.objLock) {
            if (com.njxm.smart.service.LocationService.client == null) {
                com.njxm.smart.service.LocationService.client = new LocationClient(locationContext);
                com.njxm.smart.service.LocationService.client.setLocOption(com.njxm.smart.service.LocationService.getDefaultLocationClientOption());
            }
        }
    }

    /***
     * 设置定位参数
     *
     * @param option
     * @return isSuccessSetOption
     */
    public static boolean setLocationOption(LocationClientOption option) {
        boolean isSuccess = false;
        if (option != null) {
            if (com.njxm.smart.service.LocationService.client.isStarted()) {
                com.njxm.smart.service.LocationService.client.stop();
            }
            com.njxm.smart.service.LocationService.DIYoption = option;
            com.njxm.smart.service.LocationService.client.setLocOption(option);
        }
        return isSuccess;
    }

    /***
     * 注册定位监听
     *
     * @param listener
     * @return
     */

    public static boolean registerListener(BDAbstractLocationListener listener) {
        boolean isSuccess = false;
        if (listener != null) {
            com.njxm.smart.service.LocationService.client.registerLocationListener(listener);
            isSuccess = true;
        }
        return isSuccess;
    }

    public static void unregisterListener(BDAbstractLocationListener listener) {
        if (listener != null) {
            com.njxm.smart.service.LocationService.client.unRegisterLocationListener(listener);
        }
    }

    /**
     * @return 获取sdk版本
     */
    public static String getSDKVersion() {
        if (com.njxm.smart.service.LocationService.client != null) {
            return com.njxm.smart.service.LocationService.client.getVersion();
        }
        return null;
    }

    /**
     * 开发者应用如果有H5页面使用了百度JS接口，该接口可以辅助百度JS更好的进行定位
     *
     * @param webView 传入webView控件
     */
    public static void enableAssistanLocation(WebView webView) {
        if (com.njxm.smart.service.LocationService.client != null) {
            com.njxm.smart.service.LocationService.client.enableAssistantLocation(webView);
        }
    }

    /**
     * 停止H5辅助定位
     */
    public static void disableAssistantLocation() {
        if (com.njxm.smart.service.LocationService.client != null) {
            com.njxm.smart.service.LocationService.client.disableAssistantLocation();
        }
    }

    /***
     *
     * @return DefaultLocationClientOption  默认O设置
     */
    private static LocationClientOption getDefaultLocationClientOption() {
        if (com.njxm.smart.service.LocationService.mOption == null) {
            com.njxm.smart.service.LocationService.mOption = new LocationClientOption();
            com.njxm.smart.service.LocationService.mOption.setLocationMode(LocationMode.Hight_Accuracy); // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            com.njxm.smart.service.LocationService.mOption.setCoorType("bd09ll"); // 可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            com.njxm.smart.service.LocationService.mOption.setScanSpan(1000); // 可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
            com.njxm.smart.service.LocationService.mOption.setOnceLocation(true);
            com.njxm.smart.service.LocationService.mOption.setIsNeedAddress(true); // 可选，设置是否需要地址信息，默认不需要
            com.njxm.smart.service.LocationService.mOption.setIsNeedLocationDescribe(true); // 可选，设置是否需要地址描述
            com.njxm.smart.service.LocationService.mOption.setNeedDeviceDirect(false); // 可选，设置是否需要设备方向结果
            com.njxm.smart.service.LocationService.mOption.setLocationNotify(false); // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            com.njxm.smart.service.LocationService.mOption.setIgnoreKillProcess(true); // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop
            com.njxm.smart.service.LocationService.mOption.setIsNeedLocationDescribe(true); // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
            com.njxm.smart.service.LocationService.mOption.setIsNeedLocationPoiList(true); // 可选，默认false，设置是否需要POI结果，可以在BDLocation
            com.njxm.smart.service.LocationService.mOption.SetIgnoreCacheException(false); // 可选，默认false，设置是否收集CRASH信息，默认收集
            com.njxm.smart.service.LocationService.mOption.setOpenGps(true); // 可选，默认false，设置是否开启Gps定位
            com.njxm.smart.service.LocationService.mOption.setIsNeedAltitude(false); // 可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return com.njxm.smart.service.LocationService.mOption;
    }


    /**
     * @return DIYOption 自定义Option设置
     */
    public static LocationClientOption getOption() {
        if (com.njxm.smart.service.LocationService.DIYoption == null) {
            com.njxm.smart.service.LocationService.DIYoption = new LocationClientOption();
        }
        return com.njxm.smart.service.LocationService.DIYoption;
    }

    public static void requestLocation() {
        if (com.njxm.smart.service.LocationService.client != null) {
            Log.d("BDLocation", "BDLocation invoke JPI");
            com.njxm.smart.service.LocationService.client.requestLocation();
        }
    }

    public static boolean isStart() {
        return com.njxm.smart.service.LocationService.client.isStarted();
    }

    public static boolean requestHotSpotState() {
        return com.njxm.smart.service.LocationService.client.requestHotSpotState();
    }

    public void start() {
        synchronized (this.objLock) {
            if (com.njxm.smart.service.LocationService.client != null && !com.njxm.smart.service.LocationService.client.isStarted()) {
                com.njxm.smart.service.LocationService.client.start();
            }
        }
    }

    public void stop() {
        synchronized (this.objLock) {
            if (com.njxm.smart.service.LocationService.client != null && com.njxm.smart.service.LocationService.client.isStarted()) {
                com.njxm.smart.service.LocationService.client.stop();
            }
        }
    }
}
