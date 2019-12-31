package com.njxm.smart.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.FileUtils;
import com.njxm.smart.utils.ResolutionUtil;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.BuildConfig;
import com.ns.demo.R;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

@Route(path = GlobalRouter.USER_INPUT_FACE)
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

        final String faceUrl = SPUtils.getStringValue(KeyConstant.KEY_USER_FACE_URL);
        Glide.with(this)
                .asBitmap()
                .load(HttpUrlGlobal.HTTP_MY_USER_HEAD_URL_PREFIX + faceUrl)
                .apply(new RequestOptions().centerCrop().placeholder(R.mipmap.realname_face_detect))
                .into(ivPhoto);
    }

    private File photoFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && photoFile != null && photoFile.exists() && photoFile.length() > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        final Bitmap drawable =
                                Glide.with(InputFaceActivity.this)
                                        .asBitmap().load(photoFile)
                                        .submit(ResolutionUtil.dp2Px(172), ResolutionUtil.dp2Px(109))
                                        .get();
                        invoke(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(InputFaceActivity.this)
                                        .load(drawable)
                                        .apply(new RequestOptions().centerCrop())
                                        .into(ivPhoto);
                            }
                        });

                        BitmapUtils.saveBitmap(drawable, photoFile);
                        uploadInputFace();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 上传录入人脸图像
     */
    public void uploadInputFace() {

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addFormDataPart("file", photoFile.getName(), RequestBody.create(MediaType.parse("image/png"),
                        photoFile))
                .build();

        Request request = new Request.Builder()
                .url(HttpUrlGlobal.HTTP_MY_USER_INPUT_FACE)
                .headers(HttpUtils.getPostHeaders())
                .post(body)
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
        photoFile = new File(FileUtils.getCameraDir(), UUID.randomUUID() + ".jpg");
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
        super.onSuccess(requestId, success, code, data);
        photoFile.delete();
        if (requestId == REQUEST_INPUT_FACE) {
            showToast("录入成功");
        }
    }
}
