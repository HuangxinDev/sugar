package com.njxm.smart.activities;

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
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.FileUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.ResolutionUtil;
import com.njxm.smart.utils.SPUtils;
import com.ntxm.smart.BuildConfig;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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
                .load(UrlPath.PATH_PICTURE_PREFIX.getUrl() + faceUrl)
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
        UploadInputFaceApi api = HttpUtils.getInstance().getApi(UploadInputFaceApi.class);
        api.uploadFacePhoto(MultipartBody.Part.createFormData("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID)),
                MultipartBody.Part.createFormData("file", photoFile.getName(), RequestBody.create(MediaType.parse("image/png"), photoFile)))
                .enqueue(new Callback<ServerResponseBean>() {
                    @Override
                    public void onResponse(Call<ServerResponseBean> call, Response<ServerResponseBean> response) {

                        ServerResponseBean bean = response.body();

                        if (bean == null) {
                            LogTool.printE(TAG, "文件上传出现问题");
                            return;
                        }

                        String msg = bean.getCode() == 200 ? "录入成功" : bean.getMessage();

                        EventBus.getDefault().post(new ToastEvent(msg));
                    }

                    @Override
                    public void onFailure(Call<ServerResponseBean> call, Throwable t) {
                        LogTool.printD(TAG, "上传失败: " + Log.getStackTraceString(t));
                    }
                });
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshUI(UserBean bean) {
        Glide.with(this).load(bean.getFaceUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.realname_face_detect).centerCrop())
                .into(ivPhoto);
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
}
