package com.njxm.smart.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
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
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.SimpleImageAdapter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.FileUtils;
import com.njxm.smart.utils.ResolutionUtil;
import com.njxm.smart.utils.SPUtils;
import com.ntxm.smart.BuildConfig;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class MedicalReportActivity extends BaseActivity {


    private SimpleImageAdapter adapter;

    /**
     * 个人信息-详细信息接口中的
     */
    private static final int MEDICAL_VOID = 0; // 未上传
    private static final int MEDICAL_CHECK_WAIT = 1; // 未审批
    private static final int MEDICAL_CHECK_SUCCESS = 2; // 审核完成
    private static final int MEDICAL_CHECK_FAILED = 3; // 审批不通过

    private static final int MEDICAL_COMMIT = 194;

    private int mLastMedicalState = MEDICAL_VOID;
    private int mMedicalState = MEDICAL_VOID;

    @BindView(R.id.default_layout)
    protected View mMedicalVoidLayout;

    @BindView(R.id.commit_layout)
    protected View mMedicalCommitLayout;

    @BindView(R.id.wait_check_layout)
    protected View mMedicalCheckWaitLayout;

    @BindView(R.id.check_failed_layout)
    protected View mMedicalCheckRetryLayout;

    @BindView(R.id.upload_success_title)
    protected View mMedicalCheckSuccessHeaderLayout;

    @BindView(R.id.upload_time)
    protected TextView tvModifyTime;

    // 提交图片资源
    @BindView(R.id.commit_btn)
    protected TextView mCommitBtn;

    // 开始上传图片
    @BindView(R.id.upload_btn)
    protected TextView mUploadBtn;

    // 重新申请
    @BindView(R.id.retry_upload_btn)
    protected TextView mRetryUploadBtn;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_medical_report;
    }

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    public static final int default_icon = R.mipmap.camera_photo;

    private List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("体检报告");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        mCommitBtn.setOnClickListener(this);
        mUploadBtn.setOnClickListener(this);
        mRetryUploadBtn.setOnClickListener(this);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(getDrawable(default_icon));
        adapter = new SimpleImageAdapter(this, mDatas);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (mDatas.size() >= 9) {
                    showToast("上传图片已达上限");
                    return;
                }

                if (position == mDatas.size() - 1) {
                    takePhoto(100);
                }
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final String delFilePath = mDatas.get(position);
                mDatas.remove(position);
                adapter.setNewData(mDatas);
                new File(delFilePath).delete();
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
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshUI(UserBean bean) {
        try {
            mMedicalState = Integer.parseInt(bean.getMedicalStatus());
        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
        invalidateLayoutState(mMedicalState);
        if (mMedicalState == MEDICAL_CHECK_SUCCESS) {
            updateImages();
        }
    }

    /**
     * 更新图片
     */
    private void updateImages() {
        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(UrlPath.PATH_MEDICAL_REPORT_PULL.getUrl())
                .addBodyJson("userId", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .build());
    }

    @Override
    public void onResponse(ResponseEvent event) {

        final String url = event.getUrl();

        if (url.equals(UrlPath.PATH_MEDICAL_REPORT_PULL.getUrl())) {
            MedicalBean bean = JSONObject.parseObject(event.getData(), MedicalBean.class);
            EventBus.getDefault().post(bean);
        } else if (url.equals(UrlPath.PATH_MEDICAL_REPORT_COMMIT.getUrl())) {
            if (event.isSuccess()) {
                EventBus.getDefault().post(new ToastEvent("上传成功"));
                finish();
            }
        } else {
            super.onResponse(event);
        }
    }

    private void invalidateLayoutState(int mMedicalState) {
        mLastMedicalState = this.mMedicalState;
        this.mMedicalState = mMedicalState;
        mMedicalVoidLayout.setVisibility(mMedicalState == MEDICAL_VOID ? View.VISIBLE : View.GONE);
        mMedicalCheckWaitLayout.setVisibility(mMedicalState == MEDICAL_CHECK_WAIT ? View.VISIBLE : View.GONE);
        mMedicalCheckRetryLayout.setVisibility(mMedicalState == MEDICAL_CHECK_FAILED ? View.VISIBLE : View.GONE);
        mMedicalCheckSuccessHeaderLayout.setVisibility(mMedicalState == MEDICAL_CHECK_SUCCESS ? View.VISIBLE : View.GONE);
        mCommitBtn.setVisibility(mMedicalState == MEDICAL_COMMIT ? View.VISIBLE : View.GONE);
        mMedicalCommitLayout.setVisibility((mMedicalState == MEDICAL_COMMIT || mMedicalState == MEDICAL_CHECK_SUCCESS) ? View.VISIBLE : View.GONE);

        if (mMedicalState == MEDICAL_COMMIT) {
            mDatas.clear();
            File defalutFile = new File(getCacheDir(), "camera_default.png");
            BitmapUtils.saveBitmap(BitmapFactory.decodeResource(getResources(),
                    R.mipmap.camera_photo), defalutFile);
            mDatas.add(defalutFile.getPath());
            adapter.setShowDelete(true);
            adapter.setNewData(mDatas);
        }

        showRightBtn(mMedicalState == MEDICAL_CHECK_SUCCESS, "更新");
    }

    @Override
    public void onClickRightBtn() {
        invalidateLayoutState(MEDICAL_COMMIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && photoFile != null && photoFile.exists() && photoFile.length() > 0) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = null;
                    try {
                        bitmap = Glide
                                .with(MedicalReportActivity.this)
                                .asBitmap()
                                .load(photoFile)
                                .submit(ResolutionUtil.dp2Px(109), ResolutionUtil.dp2Px(109))
                                .get();
                        BitmapUtils.saveBitmap(bitmap, photoFile);
                        invoke(new Runnable() {
                            @Override
                            public void run() {
                                update(photoFile.getAbsolutePath());
                            }
                        });
//                        EventBus.getDefault().post(photoFile.getPath());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(String filePath) {
        mDatas.add(0, photoFile.getAbsolutePath());
        adapter.setNewData(mDatas);
    }

    @Override
    public void onBackPressed() {
        onClickLeftBtn();
    }

    @Override
    public void onClickLeftBtn() {
        if (mMedicalState == MEDICAL_COMMIT) {
            invalidateLayoutState(mLastMedicalState);
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mUploadBtn || v == mRetryUploadBtn) {
            invalidateLayoutState(MEDICAL_COMMIT);
        } else if (v == mCommitBtn) {
            uploadMedicalReports();
        }
    }

    private void uploadMedicalReports() {
        RequestEvent.Builder requestBuilder = new RequestEvent.Builder()
                .url(UrlPath.PATH_MEDICAL_REPORT_COMMIT.getUrl())
                .addPart(MultipartBody.Part.createFormData("sumrUserId", SPUtils.getStringValue(KeyConstant.KEY_USER_ID)));

        for (String filePath : mDatas) {
            if (filePath.endsWith("camera_default.png")) {
                continue;
            }
            File file = new File(filePath);
            requestBuilder.addPart(MultipartBody.Part.createFormData("files", file.getName(),
                    RequestBody.create(MediaType.parse("image/png"), file)));
        }

        HttpUtils.getInstance().doPostFile(requestBuilder.build());
    }

    private File photoFile;

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

    public static class MedicalBean {
        protected String id;
        private int delFlag;
        private String createTime;
        private String createUser;
        private String modifyTime;
        private String modifyUser;
        private String sumrStatus;
        private String sumrUserId;
        private String files;
        private List<String> pathList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getModifyUser() {
            return modifyUser;
        }

        public void setModifyUser(String modifyUser) {
            this.modifyUser = modifyUser;
        }

        public String getSumrStatus() {
            return sumrStatus;
        }

        public void setSumrStatus(String sumrStatus) {
            this.sumrStatus = sumrStatus;
        }

        public String getSumrUserId() {
            return sumrUserId;
        }

        public void setSumrUserId(String sumrUserId) {
            this.sumrUserId = sumrUserId;
        }

        public String getFiles() {
            return files;
        }

        public void setFiles(String files) {
            this.files = files;
        }

        public List<String> getPathList() {
            return pathList;
        }

        public void setPathList(List<String> pathList) {
            this.pathList = pathList;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateView(MedicalBean bean) {
        tvModifyTime.setText("上传时间:  " + bean.getCreateTime());

        mDatas.clear();
        for (String path : bean.getPathList()) {
            mDatas.add(UrlPath.PATH_PICTURE_PREFIX.getUrl() + path);
        }

        adapter.setShowDelete(false);
        adapter.setNewData(mDatas);
    }
}
