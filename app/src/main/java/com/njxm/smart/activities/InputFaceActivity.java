package com.njxm.smart.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.BuildConfig;
import com.ns.demo.R;

import java.io.File;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class InputFaceActivity extends BaseActivity implements HttpCallBack {


    private static final int REQUEST_INPUT_FACE = 204;
    private static final int TAKE_PHOTO = 389;
    private ImageView ivPhoto;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_news_input_face;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showLeftBtn(true, R.mipmap.arrow_back_blue);
        setActionBarTitle("录入人脸");
        showRightBtn(true, R.mipmap.new_add);

        ivPhoto = findViewById(R.id.news_user_input_face);
        ivPhoto.setOnClickListener(this);
    }

    private File photoFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && photoFile != null && photoFile.exists() && photoFile.length() > 0) {
            Glide.with(this).load(photoFile).into(ivPhoto);
            uploadInputFace();
        }
    }

    /**
     * 上传录入人脸图像
     */
    public void uploadInputFace() {

        MultipartBody builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", SPUtils.getStringValue(KeyConstant.KEY_USE_ID))
                .addFormDataPart("file", photoFile.getName(),
                        RequestBody.create(MediaType.parse("image/png"), photoFile))
                .build();


        Request request = new Request.Builder()
                .url(HttpUrlGlobal.HTTP_MY_USER_INPUT_FACE)
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .post(builder)
                .build();

        HttpUtils.getInstance().postData(REQUEST_INPUT_FACE, request, this);
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        takePhoto(TAKE_PHOTO);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == ivPhoto) {
            takePhoto(TAKE_PHOTO);
        }
    }

    public void takePhoto(int requestId) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        UUID uuid = UUID.randomUUID();
        photoFile = new File(getFilesDir(), uuid + ".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +
                    ".fileProvider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        startActivityForResult(intent, requestId);
    }

    @Override
    public void onSuccess(final int requestId, final boolean success, int code, final String data) {
        invoke(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    showToast("上传成功");
                    finish();
                } else {
                    showToast(data);
                }

                photoFile.delete();
            }
        });
    }

    @Override
    public void onFailed(final String errMsg) {
        invoke(new Runnable() {
            @Override
            public void run() {
                showToast(errMsg);
            }
        });
    }
}
