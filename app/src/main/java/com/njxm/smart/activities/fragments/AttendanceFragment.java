package com.njxm.smart.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.njxm.smart.GlobalConst;
import com.njxm.smart.SmartCloudApplication;
import com.njxm.smart.activities.BaseActivity;
import com.njxm.smart.js.JsApi;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.FileUtils;
import com.njxm.smart.utils.LogTool;
import com.ns.demo.BuildConfig;
import com.ns.demo.R;

import java.io.File;
import java.util.UUID;

import butterknife.BindView;
import wendu.dsbridge.DWebView;

/**
 * 考勤Fragment
 */
public class AttendanceFragment extends BaseFragment {

    @BindView(R.id.webview_kit)
    protected DWebView mWebView;

    private JsApi mJsApi;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.my_attendance_activity;
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void setUpView() {
        LinearLayout llRootView = getContentView().findViewById(R.id.ll_root);
//        mWebView = getContentView().findViewById(R.id.webview_kit);
        mWebView.getSettings().setJavaScriptEnabled(true);
//        DWebView.setWebContentsDebuggingEnabled(true);
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);

//        webSettings.setDisplayZoomControls(false);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                LogTool.printE("WebView request failed : %s", url);
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                LogTool.printD("WebView request start url : %s", url);
//            }
//        });

        if (getActivity() instanceof BaseActivity) {
            llRootView.setPadding(0,
                    ((BaseActivity) getActivity()).getStatusBarHeight(getActivity()), 0, 0);
        }
    }

    @Override
    protected void setUpData() {
        mJsApi = new JsApi(getActivity(), mWebView);

        mJsApi.setTakePhoto(new JsApi.OnTakePhoto() {
            @Override
            public void requestImage() {
                takePhoto(999);
            }
        });

        mWebView.loadUrl("http://" + GlobalConst.URL_H5_PREFIX + "/#/attendance/sign");

        initLocationOption();
    }

    @Override
    public void onStart() {
        super.onStart();
        mWebView.addJavascriptObject(mJsApi, null);

    }

    private File photoFile;

    public void takePhoto(int requestId) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = new File(FileUtils.getCameraDir(), UUID.randomUUID() + ".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(SmartCloudApplication.getApplication(),
                    BuildConfig.APPLICATION_ID +
                            ".fileProvider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        getActivity().startActivityForResult(intent, requestId);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999 && resultCode == Activity.RESULT_OK) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() == null) {
                        return;
                    }
                    try {
                        Bitmap bitmap =
                                Glide.with(getActivity()).asBitmap().load(photoFile).submit(200,
                                        200).get();
                        mJsApi.uploadImage(BitmapUtils.bitmap2Bytes(bitmap));
                    } catch (Exception e) {
                        e.printStackTrace();
                        invoke(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "图片处理异常", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        mWebView.addJavascriptObject(mJsApi, "jsintenface");
    }

    private boolean isLintener = false;

    public static void initLocationOption() {
        LocationClient locationClient = new LocationClient(SmartCloudApplication.getApplication());
        LocationClientOption locationOption = new LocationClientOption();
        //注册监听函数
        locationClient.registerLocationListener(new MyLocationListener());
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
//开始定位
        locationClient.start();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        mWebView.removeJavascriptObject(null);
        super.onStop();
    }

    private static class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();

            LogTool.printD("latitude: %s, longitude: %s, errorCode: %s", latitude, longitude, errorCode);
        }
    }
}
