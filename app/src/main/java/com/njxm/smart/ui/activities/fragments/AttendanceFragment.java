/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.hxin.common.perrmission.IPermission;
import com.hxin.common.perrmission.PermissionRequestActivity;
import com.njxm.smart.SmartCloudApplication;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.service.LocationService;
import com.njxm.smart.ui.activities.BaseActivity;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.FileUtils;
import com.njxm.smart.utils.ScreenUtils;
import com.ntxm.smart.BuildConfig;
import com.ntxm.smart.R;
import com.ntxm.smart.databinding.FragmentAttendaceBinding;
import com.sugar.android.common.utils.Logger;
import com.sugar.android.common.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.UUID;

/**
 * 考勤Fragment
 */
public class AttendanceFragment extends BaseFragment implements IPermission {
    private static final String TAG = "AttendanceFragment";

    private static final String H_5_LOCATION = "h5Location";

    private FragmentAttendaceBinding layoutBinding;

    private LocationService mLocationService;

    private File photoFile;

    private final BDAbstractLocationListener mBdAbstractLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Logger.d(TAG, "==baidu location success==" + bdLocation.getAddrStr());
            LocationService.unregisterListener(this);
            mLocationService.stop();
            JSONObject object = new JSONObject();
            object.put("x", bdLocation.getLongitude());
            object.put("y", bdLocation.getLatitude());
            object.put("address", bdLocation.getAddrStr());
            layoutBinding.webviewKit.callHandler(H_5_LOCATION, new Object[]{object.toJSONString()});
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = FragmentAttendaceBinding.inflate(inflater);
        return layoutBinding.getRoot();
    }

    /**
     * Js页面获取用户个人信息
     *
     * @param object DWebView相应Js固定参数
     *
     * @return 个人信息
     */
    @JavascriptInterface
    public static String checkUserInfo(Object object) {
        Logger.d(TAG, "==checkUserInfo()==");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("data", SPUtils.getStringValue("login_message"));
        return jsonObject.toJSONString();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void init() {
        WebSettings settings = layoutBinding.webviewKit.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    protected void setUpView() {
        LinearLayout llRootView = this.getContentView().findViewById(R.id.ll_root);
        if (this.getActivity() instanceof BaseActivity) {
            llRootView.setPadding(0, ScreenUtils.getStatusBarHeight(this.getActivity()), 0, 0);
        }
    }

    @Override
    protected void setUpData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutBinding.webviewKit.addJavascriptObject(this, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationService.unregisterListener(this.mBdAbstractLocationListener);
        this.mLocationService.stop();
        layoutBinding.webviewKit.removeJavascriptObject(null);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onLazyLoad() {
        layoutBinding.webviewKit.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Logger.d(TAG, "url load start");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.i(TAG, "url load finshed");
            }
        });
        layoutBinding.webviewKit.loadUrl(UrlPath.PATH_MAIN_KAO_QIN.getUrl());
        this.mLocationService = ((SmartCloudApplication) SmartCloudApplication.getApplication()).locationService;
    }

    private void takePhoto(int requestId) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.photoFile = new File(FileUtils.getCameraDir(), UUID.randomUUID() + ".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(SmartCloudApplication.getApplication(),
                    BuildConfig.APPLICATION_ID +
                            ".fileProvider", this.photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(this.photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        if (this.getActivity() != null) {
            this.startActivityForResult(intent, requestId);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == Activity.RESULT_OK) {
            new Thread(() -> {
                if (getActivity() == null) {
                    return;
                }
                try {
                    final int IMAGE_SIZE = 200;
                    Bitmap bitmap = Glide.with(getActivity()).asBitmap().load(photoFile).submit(IMAGE_SIZE, IMAGE_SIZE).get();
                    String bitmapStr = Base64.encodeToString(BitmapUtils.transform(bitmap), Base64.DEFAULT);
                    layoutBinding.webviewKit.callHandler("h5Photos", new Object[]{bitmapStr});
                    EventBus.getDefault().post(new ToastEvent("图片大小: " + bitmapStr.length()));
                } catch (Exception e) {
                    Logger.e(TAG, "no found.");
                    EventBus.getDefault().post(new ToastEvent("图片处理异常"));
                }
            }).start();
        }
    }

    /**
     * Js页面请求设备地址信息
     *
     * @param object
     */
    @JavascriptInterface
    public void checkLocation(Object object) {
        PermissionRequestActivity.startPermissionRequest(this.getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101, this);
    }

    /**
     * Js页面调用设备相机获取图片拍摄信息
     *
     * @param object
     */
    @JavascriptInterface
    public void checkImage(Object object) {
        Logger.d(TAG, "==checkImage()==");
        PermissionRequestActivity.startPermissionRequest(this.getActivity(),
                new String[]{Manifest.permission.CAMERA}, 100, this);
    }

    @Override
    public void onPermissionSuccess(int requestCode) {
        if (requestCode == 100) { // 相机
            this.takePhoto(999);
        } else if (requestCode == 101) {// 定位
            com.njxm.smart.service.LocationService.registerListener(this.mBdAbstractLocationListener);
            this.mLocationService.start();
            com.njxm.smart.service.LocationService.requestLocation();
        }
    }

    @Override
    public void onPermissionCanceled(int requestCode) {
        if (requestCode == 100) {
            EventBus.getDefault().post(new ToastEvent("拒绝相机权限将无法完成考勤"));
        }
    }

    @Override
    public void onPermissionDenied(int requestCode) {
    }
}
