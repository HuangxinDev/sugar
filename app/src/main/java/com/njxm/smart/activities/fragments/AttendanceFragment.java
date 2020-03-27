package com.njxm.smart.activities.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.hxin.common.perrmission.IPermission;
import com.hxin.common.perrmission.PermissionRequestActivity;
import com.njxm.smart.SmartCloudApplication;
import com.njxm.smart.activities.BaseActivity;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.service.LocationService;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.FileUtils;
import com.njxm.smart.utils.SPUtils;
import com.ntxm.smart.BuildConfig;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.EventBus;

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

    private LocationService mLocationService;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.my_attendance_activity;
    }

    private BDAbstractLocationListener mBdAbstractLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            JSONObject object = new JSONObject();
            object.put("x", bdLocation.getLongitude());
            object.put("y", bdLocation.getLatitude());
            object.put("address", bdLocation.getAddrStr());
            Log.d("BDLocation", "upload data: " + object.toJSONString());
            mWebView.callHandler("h5Location", new Object[]{object.toJSONString()});
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void init() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return true;
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (getActivity() != null) {
                    getActivity().getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        mWebView.loadUrl(UrlPath.PATH_MAIN_KAO_QIN.getUrl());
    }

    @Override
    protected void setUpView() {
        LinearLayout llRootView = getContentView().findViewById(R.id.ll_root);
        if (getActivity() instanceof BaseActivity) {
            llRootView.setPadding(0, ((BaseActivity) getActivity()).getStatusBarHeight(getActivity()), 0, 0);
        }
    }

    @Override
    protected void setUpData() {

    }

    private File photoFile;

    private void takePhoto(int requestId) {
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
        if (getActivity() != null) {
//            getActivity().startActivityForResult(intent, requestId);
            startActivityForResult(intent, requestId);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mWebView.addJavascriptObject(this, null);
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
                        Bitmap bitmap = Glide.with(getActivity()).asBitmap().load(photoFile).submit(200, 200).get();
                        String bitmapStr = Base64.encodeToString(BitmapUtils.bitmap2Bytes(bitmap), Base64.DEFAULT);
                        mWebView.callHandler("h5Photos", new Object[]{bitmapStr});
                        EventBus.getDefault().post(new ToastEvent("图片大小: " + bitmapStr.length()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        EventBus.getDefault().post(new ToastEvent("图片处理异常"));
                    }
                }
            }).start();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        if (mLocationService != null) {
            mLocationService.unregisterListener(mBdAbstractLocationListener);
            mLocationService.stop();
        }
        mWebView.removeJavascriptObject(null);
        super.onStop();
    }

    /**
     * Js页面请求设备地址信息
     *
     * @param object
     */
    @JavascriptInterface
    public void checkLocation(Object object) {

        PermissionRequestActivity.startPermissionRequest(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101, new IPermission() {
            @Override
            public void onPermissionSuccess() {
                mLocationService = ((SmartCloudApplication) getActivity().getApplication()).locationService;
                mLocationService.registerListener(mBdAbstractLocationListener);
                mLocationService.enableAssistanLocation(mWebView);

                if (mLocationService != null) {
                    mLocationService.start();
                }
            }

            @Override
            public void onPermissionCanceled() {

            }

            @Override
            public void onPermissionDenied() {

            }
        });
    }

    /**
     * Js页面获取用户个人信息
     *
     * @param object DWebView相应Js固定参数
     * @return 个人信息
     */
    @JavascriptInterface
    public String checkUserInfo(Object object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("data", SPUtils.getStringValue("login_message"));
        return jsonObject.toJSONString();
    }

    /**
     * Js页面调用设备相机获取图片拍摄信息
     *
     * @param object
     */
    @JavascriptInterface
    public void checkImage(Object object) {

        PermissionRequestActivity.startPermissionRequest(getActivity(), new String[]{Manifest.permission.CAMERA}, 101, new IPermission() {
            @Override
            public void onPermissionSuccess() {
                takePhoto(999);
            }

            @Override
            public void onPermissionCanceled() {

            }

            @Override
            public void onPermissionDenied() {

            }
        });
    }
}
