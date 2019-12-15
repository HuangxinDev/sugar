package com.njxm.smart.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
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
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.BuildConfig;
import com.ns.demo.R;

import java.io.File;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 实名认证页面
 */
public class RealNameAuthenticationActivity extends BaseActivity implements HttpCallBack {

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

        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("id", SPUtils.getStringValue(KeyConstant.KEY_USE_ID))
                .addFormDataPart("name", etCardName.getText().toString().trim())
                .addFormDataPart("idCardNum", etCardId.getText().toString().trim())
                .addFormDataPart("frontFile", "1zm.jpg", RequestBody.create(imageType,
                        sparseArray.get(0)))
                .addFormDataPart("backFile", "2fm.jpg", RequestBody.create(imageType,
                        sparseArray.get(1)))
                .addFormDataPart("faceFile", "11.jpg", RequestBody.create(imageType,
                        sparseArray.get(2)))
                .build();

        Request request = new Request.Builder().url("http://119.3.136.127:7776/api/sys/user/personVerify")
                .header("Platform", "APP")
                .header("Content-Type", HttpUrlGlobal.CONTENT_TEXT_TYPE)
                .header("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .header("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .post(body)
                .build();

        HttpUtils.getInstance().postData(REQUEST_UPLOAD_BITMAP, request, this);
    }

    public void start(int requestId, String fileName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        UUID uuid = UUID.randomUUID();
        File file = new File(getFilesDir(), fileName + ".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +
                    ".fileProvider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        startActivityForResult(intent, requestId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (photoFile == null || !photoFile.exists() || photoFile.length() == 0) {
            return;
        }

        if (requestCode == REQUEST_CARD_1) {
            File file = new File(getFilesDir(), "zm.jpg");
            Glide.with(this).load(BitmapFactory.decodeFile(file.getAbsolutePath())).into(ivCard1);
            sparseArray.put(0, file);
        } else if (requestCode == REQUEST_CARD_2) {
            File file = new File(getFilesDir(), "fm.jpg");
            Glide.with(this).load(BitmapFactory.decodeFile(file.getAbsolutePath())).into(ivCard2);
            sparseArray.put(1, file);
        } else if (requestCode == REQUEST_FACE_1) {
            File file = new File(getFilesDir(), "rl.jpg");
            Glide.with(this).load(BitmapFactory.decodeFile(file.getAbsolutePath())).into(ivFace);
            sparseArray.put(2, file);
        }

    }

    @Override
    public void onClickLeftBtn() {
        super.onBackPressed();
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, final String data) {
        if (!success) {
            invoke(new Runnable() {
                @Override
                public void run() {
                    showToast(data);
                }
            });
        }
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
