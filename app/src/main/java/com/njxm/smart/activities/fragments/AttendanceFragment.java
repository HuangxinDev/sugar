package com.njxm.smart.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.njxm.smart.GlobalConst;
import com.njxm.smart.SmartCloudApplication;
import com.njxm.smart.activities.BaseActivity;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.js.JsApi;
import com.njxm.smart.service.LocationService;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.FileUtils;
import com.ns.demo.BuildConfig;
import com.ns.demo.R;

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

    private JsApi mJsApi;

    private LocationService mLocationService;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.my_attendance_activity;
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void setUpView() {
        LinearLayout llRootView = getContentView().findViewById(R.id.ll_root);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mLocationService = new LocationService(getActivity());
        mLocationService.enableAssistanLocation(mWebView);
        mLocationService.registerListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                JSONObject object = new JSONObject();
                object.put("x", bdLocation.getLongitude());
                object.put("y", bdLocation.getLatitude());
                object.put("address", bdLocation.getAddrStr());
                mJsApi.uploadLocation(object.toJSONString());
            }
        });
        if (getActivity() instanceof BaseActivity) {
            llRootView.setPadding(0,
                    ((BaseActivity) getActivity()).getStatusBarHeight(getActivity()), 0, 0);
        }
    }

    @Override
    protected void setUpData() {
        mJsApi = new JsApi(getActivity(), mWebView, mLocationService);
        mWebView.addJavascriptObject(mJsApi, null);
        mJsApi.setTakePhoto(new JsApi.OnTakePhoto() {
            @Override
            public void requestImage() {
                takePhoto(999);
            }
        });

        mWebView.loadUrl(GlobalConst.URL_H5_PREFIX + "/#/attendance/sign");
    }

    @Override
    public void onStart() {
        super.onStart();
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
                        mJsApi.uploadImage(BitmapUtils.bitmap2Bytes(bitmap));
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
//        mWebView.addJavascriptObject(mJsApi, "jsintenface");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
//        mWebView.removeJavascriptObject(null);
        super.onStop();
    }
}
