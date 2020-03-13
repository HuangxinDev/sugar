package com.njxm.smart.activities;

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

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 实名认证页面
 */
public class RealNameAuthenticationActivity extends BaseActivity {

    private ImageView ivCard1;
    private ImageView ivCard2;
    private ImageView ivFace;

    private EditText etCardId;
    private EditText etCardName;

    private AppCompatTextView tvCommitBtn;

    public static final int REQUEST_CARD_1 = 100;
    private static final int REQUEST_CARD_2 = 161;
    private static final int REQUEST_FACE_1 = 878;

    private static final int REQUEST_UPLOAD_BITMAP = 321;

    private SparseArray<File> sparseArray = new SparseArray<>();

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_realname_authentication;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBarTitle("实名认证");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        ivCard1 = findViewById(R.id.card_id_face);
        ivCard2 = findViewById(R.id.card_id_native);
        ivFace = findViewById(R.id.face_detect);
        tvCommitBtn = findViewById(R.id.commitBtn);
        tvCommitBtn.setOnClickListener(this);

        etCardName = findViewById(R.id.card_name);
        etCardId = findViewById(R.id.card_id);

        ivCard1.setOnClickListener(this);
        ivCard2.setOnClickListener(this);
        ivFace.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == ivCard1) {
            start(REQUEST_CARD_1, "zm");
        } else if (v == ivCard2) {
            start(REQUEST_CARD_2, "fm");
        } else if (v == ivFace) {
            start(REQUEST_FACE_1, "rl");
        } else if (v == tvCommitBtn) {
            uploadBitmap();
        }
    }

    private void uploadBitmap() {

        if (StringUtils.isEmpty(etCardId.getText().toString().trim()) || StringUtils.isEmpty(etCardName.getText().toString().trim())) {
            showToast("请输入姓名和身份证号");
            return;
        }
        if (sparseArray.size() < 3) {
            showToast("请补全照片");
            return;
        }

        MediaType imageType = MediaType.parse("image/jpg");
        HttpUtils.getInstance().doPostFile(new RequestEvent.Builder()
                .url(UrlPath.PATH_USER_REAL_AUTH.getUrl())
                .addPart(MultipartBody.Part.createFormData("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID)))
                .addPart(MultipartBody.Part.createFormData("name", etCardName.getText().toString().trim()))
                .addPart(MultipartBody.Part.createFormData("idCardNum", etCardId.getText().toString().trim()))
                .addPart(MultipartBody.Part.createFormData("frontFile", "1zm.jpg", RequestBody.create(imageType, BitmapUtils.compressFile(sparseArray.get(0)))))
                .addPart(MultipartBody.Part.createFormData("backFile", "2fm.jpg", RequestBody.create(imageType,
                        BitmapUtils.compressFile(sparseArray.get(1)))))
                .addPart(MultipartBody.Part.createFormData("faceFile", "11.jpg", RequestBody.create(imageType,
                        BitmapUtils.compressFile(sparseArray.get(2)))))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build());
    }

    public void start(int requestId, String fileName) {
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
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (photoFile == null || !photoFile.exists() || photoFile.length() == 0) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = Glide.with(RealNameAuthenticationActivity.this)
                            .asBitmap()
                            .load(photoFile)
                            .submit(ResolutionUtil.dp2Px(172), ResolutionUtil.dp2Px(109))
                            .get();
                    invoke(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(RealNameAuthenticationActivity.this)
                                    .load(bitmap)
                                    .apply(new RequestOptions().centerCrop())
                                    .into(requestCode == REQUEST_CARD_1 ? ivCard1 :
                                            (requestCode == REQUEST_CARD_2 ? ivCard2 : ivFace));
                        }
                    });

                    BitmapUtils.saveBitmap(bitmap, photoFile);
                    int i = -1;
                    switch (requestCode) {
                        case REQUEST_CARD_1:
                            i = 0;
                            break;
                        case REQUEST_CARD_2:
                            i = 1;
                            break;
                        case REQUEST_FACE_1:
                            i = 2;
                            break;
                    }
                    sparseArray.put(i, photoFile);
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
