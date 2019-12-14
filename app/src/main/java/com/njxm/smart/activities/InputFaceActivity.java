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
import com.ns.demo.BuildConfig;
import com.ns.demo.R;

import java.io.File;
import java.util.UUID;

public class InputFaceActivity extends BaseActivity {


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
        if (requestCode == TAKE_PHOTO && photoFile != null) {
            Glide.with(this).load(photoFile).into(ivPhoto);
        }
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

}
