package com.njxm.smart.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.SimpleImageAdapter;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.ResolutionUtil;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.BuildConfig;
import com.ns.demo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;


public class MedicalReportActivity extends BaseActivity implements HttpCallBack {


    private SimpleImageAdapter adapter;

    private static final int MEDICAL_VOID = 0; // 未上传
    private static final int MEDICAL_CHECK_WAIT = 1; // 未审批
    private static final int MEDICAL_CHECK_SUCCESS = 2; // 审核完成
    private static final int MEDICAL_CHECK_FAILED = 3; // 审批未通过

    private static final int MEDICAL_COMMIT = 194;

    private int mMedicalState = MEDICAL_VOID;

    View mMedicalVoidLayout;
    View mMedicalCommitLayout;
    View mMedicalCheckWaitLayout;
    View mMedicalCheckRetryLayout;
    View mMedicalCheckSuccessHeaderLayout;

    // 提交图片资源
    TextView mCommitBtn;

    // 开始上传图片
    TextView mUploadBtn;

    // 重新申请
    TextView mRetryUploadBtn;

    private List<File> medicalFiles = new ArrayList<>();
    private static final int REQUEST_UPLOAD_MEDICAL = 100;
    private static final int REQUEST_UPDATE_LIST = 783;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_medical_report;
    }

    private RecyclerView mRecyclerView;
    public static final int default_icon = R.mipmap.camera_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("体检报告");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        mMedicalVoidLayout = findViewById(R.id.default_layout);
        mMedicalCommitLayout = findViewById(R.id.commit_layout);
        mMedicalCheckWaitLayout = findViewById(R.id.wait_check_layout);
        mMedicalCheckSuccessHeaderLayout = findViewById(R.id.upload_success_title);
        mMedicalCheckRetryLayout = findViewById(R.id.check_failed_layout);
        mCommitBtn = findViewById(R.id.commit_btn);
        mCommitBtn.setOnClickListener(this);
        mUploadBtn = findViewById(R.id.upload_btn);
        mUploadBtn.setOnClickListener(this);
        mRetryUploadBtn = findViewById(R.id.retry_upload_btn);
        mRetryUploadBtn.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(getDrawable(default_icon));
        adapter = new SimpleImageAdapter(drawables);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (adapter.getData().size() >= 9) {
                    showToast("上传图片已达上限");
                    return;
                }

                if (position == adapter.getData().size() - 1) {
                    takePhoto(100);
                }
            }
        });
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) > 2) {
                    outRect.top += ResolutionUtil.dp2Px(10);
                }
            }
        });
        mRecyclerView.setAdapter(adapter);

        mMedicalState = Integer.parseInt(SPUtils.getStringValue(KeyConstant.KEY_MEDICAL_STATUS));
        if (mMedicalState == 2) {
            showRightBtn(true, "更新");
            updateImages();
        }

        invalidateLayoutState();
    }

    /**
     * 更新图片
     */
    private void updateImages() {

        JSONObject object = new JSONObject();
        object.put("id", SPUtils.getStringValue(KeyConstant.KEY_USE_ID));
        RequestBody requestBody =
                FormBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE), object.toString());
        Request request = new Request.Builder().url(HttpUrlGlobal.HTTP_MEDICAL_GET_IMAGE)
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .post(requestBody)
                .build();

        HttpUtils.getInstance().postData(REQUEST_UPDATE_LIST, request, this);
    }

    private void invalidateLayoutState() {
        switch (mMedicalState) { // 未上传
            case MEDICAL_VOID:
                mMedicalVoidLayout.setVisibility(View.VISIBLE);
                mMedicalCommitLayout.setVisibility(View.GONE);
                mMedicalCheckWaitLayout.setVisibility(View.GONE);
                mMedicalCheckRetryLayout.setVisibility(View.GONE);
                break;
            case MEDICAL_CHECK_WAIT: // 未审查
                mMedicalVoidLayout.setVisibility(View.GONE);
                mMedicalCommitLayout.setVisibility(View.GONE);
                mMedicalCheckWaitLayout.setVisibility(View.VISIBLE);
                mMedicalCheckRetryLayout.setVisibility(View.GONE);
                break;
            case MEDICAL_CHECK_SUCCESS:
                mMedicalVoidLayout.setVisibility(View.GONE);
                mMedicalCommitLayout.setVisibility(View.VISIBLE);
                mMedicalCheckSuccessHeaderLayout.setVisibility(View.VISIBLE);
                mCommitBtn.setVisibility(View.GONE);
                mMedicalCheckWaitLayout.setVisibility(View.GONE);
                mMedicalCheckRetryLayout.setVisibility(View.GONE);
                break;
            case MEDICAL_CHECK_FAILED:
                mMedicalVoidLayout.setVisibility(View.GONE);
                mMedicalCommitLayout.setVisibility(View.GONE);
                mCommitBtn.setVisibility(View.GONE);
                mMedicalCheckWaitLayout.setVisibility(View.GONE);
                mMedicalCheckRetryLayout.setVisibility(View.VISIBLE);
                break;

            case MEDICAL_COMMIT: // 提交页面
                mMedicalVoidLayout.setVisibility(View.GONE);
                mMedicalCommitLayout.setVisibility(View.VISIBLE);
                mMedicalCheckSuccessHeaderLayout.setVisibility(View.GONE);
                mCommitBtn.setVisibility(View.VISIBLE);
                mMedicalCheckWaitLayout.setVisibility(View.GONE);
                mMedicalCheckRetryLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (photoFile != null && photoFile.exists() && photoFile.length() > 0) {
            medicalFiles.add(photoFile);
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            adapter.getData().add(0, new BitmapDrawable(getResources(), bitmap));
            adapter.notifyDataSetChanged();
            invalidateLayoutState();
        }
    }

    @Override
    public void onBackPressed() {
        onClickLeftBtn();
    }

    @Override
    public void onClickLeftBtn() {
        switch (mMedicalState) {
            case MEDICAL_COMMIT:
                invalidateLayoutState();
                break;
            case MEDICAL_VOID:
            case MEDICAL_CHECK_WAIT:
            case MEDICAL_CHECK_FAILED:
            default:
                finish();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mUploadBtn || v == mRetryUploadBtn) {
            mMedicalState = MEDICAL_COMMIT;
            takePhoto(100);
        } else if (v == mCommitBtn) {
            mMedicalState = MEDICAL_CHECK_WAIT;
            invalidateLayoutState();
            uploadMedicalReports();
        }
    }

    private void uploadMedicalReports() {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("sumrUserId", SPUtils.getStringValue(KeyConstant.KEY_USE_ID));

        for (int i = 0; i < medicalFiles.size(); i++) {
            builder.addFormDataPart("files", medicalFiles.get(i).getName(),
                    RequestBody.create(MediaType.parse("image/png"), medicalFiles.get(i)));
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(HttpUrlGlobal.HTTP_MEDICAL_COMMIT_UPDATE)
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .post(requestBody)
                .build();

        HttpUtils.getInstance().postData(REQUEST_UPLOAD_MEDICAL, request, this);
    }

    private File photoFile;

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
    public void onSuccess(int requestId, boolean success, int code, String data) {

        if (requestId == REQUEST_UPLOAD_MEDICAL) {
            for (File item : medicalFiles) {
                LogTool.printD("delete file: %s state: %s", item.getName(), item.delete());
            }
            medicalFiles.clear();
        } else if (requestId == REQUEST_UPDATE_LIST) {
            LogTool.printD("data: %s,", data);
        }
    }

    @Override
    public void onFailed(String errMsg) {
        for (File item : medicalFiles) {
            LogTool.printD("delete file: %s state: %s", item.getName(), item.delete());
        }
        medicalFiles.clear();
    }
}
