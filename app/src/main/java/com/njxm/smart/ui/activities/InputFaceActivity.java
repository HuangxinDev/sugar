/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.njxm.smart.api.UploadInputFaceApi;
import com.njxm.smart.bean.ServerResponseBean;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.FileUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.ResolutionUtil;
import com.njxm.smart.utils.SPUtils;
import com.ntxm.smart.BuildConfig;
import com.ntxm.smart.R;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Route(path = GlobalRouter.USER_INPUT_FACE)
public class InputFaceActivity extends BaseActivity {

    private static final int TAKE_PHOTO = 389;

    @BindView(R.id.news_user_input_face)
    ImageView ivPhoto;
    private File photoFile;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_news_input_face;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.setActionBarTitle("录入人脸");
        this.showRightBtn(true, R.mipmap.new_add);

        this.ivPhoto = this.findViewById(R.id.news_user_input_face);
        this.ivPhoto.setOnClickListener(this);

        String faceUrl = SPUtils.getStringValue(KeyConstant.KEY_USER_FACE_URL);
        Glide.with(this)
                .asBitmap()
                .load(UrlPath.PATH_PICTURE_PREFIX.getUrl() + faceUrl)
                .apply(new RequestOptions().centerCrop().placeholder(R.mipmap.realname_face_detect))
                .into(this.ivPhoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == InputFaceActivity.TAKE_PHOTO && this.photoFile != null && this.photoFile.exists() && this.photoFile.length() > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Bitmap drawable =
                                Glide.with(InputFaceActivity.this)
                                        .asBitmap().load(InputFaceActivity.this.photoFile)
                                        .submit(ResolutionUtil.dp2Px(172), ResolutionUtil.dp2Px(109))
                                        .get();
                        InputFaceActivity.this.invoke(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(InputFaceActivity.this)
                                        .load(drawable)
                                        .apply(new RequestOptions().centerCrop())
                                        .into(InputFaceActivity.this.ivPhoto);
                            }
                        });

                        BitmapUtils.saveBitmap(drawable, InputFaceActivity.this.photoFile);
                        InputFaceActivity.this.uploadInputFace();
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
    private void uploadInputFace() {
        UploadInputFaceApi api = com.njxm.smart.tools.network.HttpUtils.getApi(UploadInputFaceApi.class);
        api.uploadFacePhoto(MultipartBody.Part.createFormData("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID)),
                MultipartBody.Part.createFormData("file", this.photoFile.getName(), RequestBody.create(MediaType.parse("image/png"), this.photoFile)))
                .enqueue(new Callback<ServerResponseBean>() {
                    @Override
                    public void onResponse(Call<ServerResponseBean> call, Response<ServerResponseBean> response) {

                        ServerResponseBean bean = response.body();

                        if (bean == null) {
                            LogTool.printE(InputFaceActivity.class, "文件上传出现问题");
                            return;
                        }

                        String msg = bean.getCode() == 200 ? "录入成功" : bean.getMessage();

                        EventBus.getDefault().post(new ToastEvent(msg));
                    }

                    @Override
                    public void onFailure(Call<ServerResponseBean> call, Throwable t) {
                        LogTool.printD(InputFaceActivity.class, "上传失败: " + Log.getStackTraceString(t));
                    }
                });
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        this.takePhoto(InputFaceActivity.TAKE_PHOTO);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == this.ivPhoto) {
            this.takePhoto(InputFaceActivity.TAKE_PHOTO);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshUI(UserBean bean) {
        Glide.with(this).load(bean.getFaceUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.realname_face_detect).centerCrop())
                .into(this.ivPhoto);
    }

    @Override
    public void takePhoto(int requestId) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.photoFile = new File(FileUtils.getCameraDir(), UUID.randomUUID() + ".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +
                    ".fileProvider", this.photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(this.photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        this.startActivityForResult(intent, requestId);
    }
}
