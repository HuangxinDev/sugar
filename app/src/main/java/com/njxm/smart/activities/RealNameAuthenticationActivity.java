/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.FileUtils;
import com.njxm.smart.utils.ResolutionUtil;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ntxm.smart.BuildConfig;
import com.ntxm.smart.R;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 实名认证页面
 */
public class RealNameAuthenticationActivity extends BaseActivity {

    public static final int REQUEST_CARD_1 = 100;
    private static final int REQUEST_CARD_2 = 161;
    private static final int REQUEST_FACE_1 = 878;
    private static final int REQUEST_UPLOAD_BITMAP = 321;
    private final SparseArray<File> sparseArray = new SparseArray<>();
    private ImageView ivCard1;
    private ImageView ivCard2;
    private ImageView ivFace;
    private EditText etCardId;
    private EditText etCardName;
    private AppCompatTextView tvCommitBtn;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_realname_authentication;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setActionBarTitle("实名认证");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);

        this.ivCard1 = this.findViewById(R.id.card_id_face);
        this.ivCard2 = this.findViewById(R.id.card_id_native);
        this.ivFace = this.findViewById(R.id.face_detect);
        this.tvCommitBtn = this.findViewById(R.id.commitBtn);
        this.tvCommitBtn.setOnClickListener(this);

        this.etCardName = this.findViewById(R.id.card_name);
        this.etCardId = this.findViewById(R.id.card_id);

        this.ivCard1.setOnClickListener(this);
        this.ivCard2.setOnClickListener(this);
        this.ivFace.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == this.ivCard1) {
            this.start(com.njxm.smart.activities.RealNameAuthenticationActivity.REQUEST_CARD_1, "zm");
        } else if (v == this.ivCard2) {
            this.start(com.njxm.smart.activities.RealNameAuthenticationActivity.REQUEST_CARD_2, "fm");
        } else if (v == this.ivFace) {
            this.start(com.njxm.smart.activities.RealNameAuthenticationActivity.REQUEST_FACE_1, "rl");
        } else if (v == this.tvCommitBtn) {
            this.uploadBitmap();
        }
    }

    private void uploadBitmap() {

        if (StringUtils.isEmpty(this.etCardId.getText().toString().trim()) || StringUtils.isEmpty(this.etCardName.getText().toString().trim())) {
            com.njxm.smart.activities.BaseActivity.showToast("请输入姓名和身份证号");
            return;
        }
        if (this.sparseArray.size() < 3) {
            com.njxm.smart.activities.BaseActivity.showToast("请补全照片");
            return;
        }

        MediaType imageType = MediaType.parse("image/jpg");
        HttpUtils.getInstance().doPostFile(new RequestEvent.Builder()
                .url(UrlPath.PATH_USER_REAL_AUTH.getUrl())
                .addPart(MultipartBody.Part.createFormData("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID)))
                .addPart(MultipartBody.Part.createFormData("name", this.etCardName.getText().toString().trim()))
                .addPart(MultipartBody.Part.createFormData("idCardNum", this.etCardId.getText().toString().trim()))
                .addPart(MultipartBody.Part.createFormData("frontFile", "1zm.jpg", RequestBody.create(imageType, BitmapUtils.compressFile(this.sparseArray.get(0)))))
                .addPart(MultipartBody.Part.createFormData("backFile", "2fm.jpg", RequestBody.create(imageType,
                        BitmapUtils.compressFile(this.sparseArray.get(1)))))
                .addPart(MultipartBody.Part.createFormData("faceFile", "11.jpg", RequestBody.create(imageType,
                        BitmapUtils.compressFile(this.sparseArray.get(2)))))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build());
    }

    public void start(int requestId, String fileName) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.photoFile == null || !this.photoFile.exists() || this.photoFile.length() == 0) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = Glide.with(RealNameAuthenticationActivity.this)
                            .asBitmap()
                            .load(com.njxm.smart.activities.RealNameAuthenticationActivity.this.photoFile)
                            .submit(ResolutionUtil.dp2Px(172), ResolutionUtil.dp2Px(109))
                            .get();
                    com.njxm.smart.activities.RealNameAuthenticationActivity.this.invoke(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(RealNameAuthenticationActivity.this)
                                    .load(bitmap)
                                    .apply(new RequestOptions().centerCrop())
                                    .into(requestCode == com.njxm.smart.activities.RealNameAuthenticationActivity.REQUEST_CARD_1 ? com.njxm.smart.activities.RealNameAuthenticationActivity.this.ivCard1 :
                                            (requestCode == com.njxm.smart.activities.RealNameAuthenticationActivity.REQUEST_CARD_2 ? com.njxm.smart.activities.RealNameAuthenticationActivity.this.ivCard2 : com.njxm.smart.activities.RealNameAuthenticationActivity.this.ivFace));
                        }
                    });

                    BitmapUtils.saveBitmap(bitmap, com.njxm.smart.activities.RealNameAuthenticationActivity.this.photoFile);
                    int i = -1;
                    switch (requestCode) {
                        case com.njxm.smart.activities.RealNameAuthenticationActivity.REQUEST_CARD_1:
                            i = 0;
                            break;
                        case com.njxm.smart.activities.RealNameAuthenticationActivity.REQUEST_CARD_2:
                            i = 1;
                            break;
                        case com.njxm.smart.activities.RealNameAuthenticationActivity.REQUEST_FACE_1:
                            i = 2;
                            break;
                    }
                    com.njxm.smart.activities.RealNameAuthenticationActivity.this.sparseArray.put(i, com.njxm.smart.activities.RealNameAuthenticationActivity.this.photoFile);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    @Override
    public void onClickLeftBtn() {
        super.onBackPressed();
    }

}
