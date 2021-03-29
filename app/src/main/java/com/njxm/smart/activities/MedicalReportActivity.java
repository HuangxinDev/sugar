package com.njxm.smart.activities;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.SimpleImageAdapter;
import com.njxm.smart.api.UploadFileApi;
import com.njxm.smart.bean.ServerResponseBean;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalReportActivity extends BaseActivity {


    public static final int default_icon = R.mipmap.camera_photo;
    /**
     * 个人信息-详细信息接口中的
     */
    private static final int MEDICAL_VOID = 0; // 未上传
    private static final int MEDICAL_CHECK_WAIT = 1; // 未审批
    private static final int MEDICAL_CHECK_SUCCESS = 2; // 审核完成
    private static final int MEDICAL_CHECK_FAILED = 3; // 审批不通过

    private static final int MEDICAL_COMMIT = 194;
    private final List<String> mDatas = new ArrayList<>();
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
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    private SimpleImageAdapter adapter;
    private int mLastMedicalState = com.njxm.smart.activities.MedicalReportActivity.MEDICAL_VOID;
    private int mMedicalState = com.njxm.smart.activities.MedicalReportActivity.MEDICAL_VOID;
    private File photoFile;

    /**
     * 更新图片
     */
    private static void updateImages() {
        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(UrlPath.PATH_MEDICAL_REPORT_PULL.getUrl())
                .addBodyJson("userId", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .build());
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_medical_report;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("体检报告");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);

        this.mCommitBtn.setOnClickListener(this);
        this.mUploadBtn.setOnClickListener(this);
        this.mRetryUploadBtn.setOnClickListener(this);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 3);
        this.mRecyclerView.setLayoutManager(layoutManager);
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(this.getDrawable(com.njxm.smart.activities.MedicalReportActivity.default_icon));
        this.adapter = new SimpleImageAdapter(this, this.mDatas);
        this.adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (com.njxm.smart.activities.MedicalReportActivity.this.mDatas.size() >= 9) {
                    com.njxm.smart.activities.BaseActivity.showToast("上传图片已达上限");
                    return;
                }

                if (position == com.njxm.smart.activities.MedicalReportActivity.this.mDatas.size() - 1) {
                    com.njxm.smart.activities.MedicalReportActivity.this.takePhoto(100);
                }
            }
        });

        this.adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String delFilePath = com.njxm.smart.activities.MedicalReportActivity.this.mDatas.get(position);
                com.njxm.smart.activities.MedicalReportActivity.this.mDatas.remove(position);
                adapter.setNewData(com.njxm.smart.activities.MedicalReportActivity.this.mDatas);
                new File(delFilePath).delete();
            }
        });
        this.mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) > 2) {
                    outRect.top += ResolutionUtil.dp2Px(10);
                }
            }
        });
        this.mRecyclerView.setAdapter(this.adapter);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshUI(UserBean bean) {
        try {
            this.mMedicalState = Integer.parseInt(bean.getMedicalStatus());
        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
        this.invalidateLayoutState(this.mMedicalState);
        if (this.mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_CHECK_SUCCESS) {
            com.njxm.smart.activities.MedicalReportActivity.updateImages();
        }
    }

    @Override
    public void onResponse(ResponseEvent event) {

        String url = event.getUrl();

        if (url.equals(UrlPath.PATH_MEDICAL_REPORT_PULL.getUrl())) {
            MedicalBean bean = JSONObject.parseObject(event.getData(), MedicalBean.class);
            EventBus.getDefault().post(bean);
        } else if (url.equals(UrlPath.PATH_MEDICAL_REPORT_COMMIT.getUrl())) {
            if (event.isSuccess()) {
                EventBus.getDefault().post(new ToastEvent("上传成功"));
                this.finish();
            }
        } else {
            super.onResponse(event);
        }
    }

    private void invalidateLayoutState(int mMedicalState) {
        this.mLastMedicalState = this.mMedicalState;
        this.mMedicalState = mMedicalState;
        this.mMedicalVoidLayout.setVisibility(mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_VOID ? View.VISIBLE : View.GONE);
        this.mMedicalCheckWaitLayout.setVisibility(mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_CHECK_WAIT ? View.VISIBLE : View.GONE);
        this.mMedicalCheckRetryLayout.setVisibility(mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_CHECK_FAILED ? View.VISIBLE : View.GONE);
        this.mMedicalCheckSuccessHeaderLayout.setVisibility(mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_CHECK_SUCCESS ? View.VISIBLE : View.GONE);
        this.mCommitBtn.setVisibility(mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_COMMIT ? View.VISIBLE : View.GONE);
        this.mMedicalCommitLayout.setVisibility((mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_COMMIT || mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_CHECK_SUCCESS) ? View.VISIBLE : View.GONE);

        if (mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_COMMIT) {
            this.mDatas.clear();
            File defalutFile = new File(this.getCacheDir(), "camera_default.png");
            BitmapUtils.saveBitmap(BitmapFactory.decodeResource(this.getResources(),
                    R.mipmap.camera_photo), defalutFile);
            this.mDatas.add(defalutFile.getPath());
            this.adapter.setShowDelete(true);
            this.adapter.setNewData(this.mDatas);
        }

        this.showRightBtn(mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_CHECK_SUCCESS, "更新");
    }

    @Override
    public void onClickRightBtn() {
        this.invalidateLayoutState(com.njxm.smart.activities.MedicalReportActivity.MEDICAL_COMMIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && this.photoFile != null && this.photoFile.exists() && this.photoFile.length() > 0) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = null;
                    try {
                        bitmap = Glide
                                .with(MedicalReportActivity.this)
                                .asBitmap()
                                .load(com.njxm.smart.activities.MedicalReportActivity.this.photoFile)
                                .submit(ResolutionUtil.dp2Px(109), ResolutionUtil.dp2Px(109))
                                .get();
                        BitmapUtils.saveBitmap(bitmap, com.njxm.smart.activities.MedicalReportActivity.this.photoFile);
                        com.njxm.smart.activities.MedicalReportActivity.this.invoke(new Runnable() {
                            @Override
                            public void run() {
                                com.njxm.smart.activities.MedicalReportActivity.this.update(com.njxm.smart.activities.MedicalReportActivity.this.photoFile.getAbsolutePath());
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
        this.mDatas.add(0, this.photoFile.getAbsolutePath());
        this.adapter.setNewData(this.mDatas);
    }

    @Override
    public void onBackPressed() {
        this.onClickLeftBtn();
    }

    @Override
    public void onClickLeftBtn() {
        if (this.mMedicalState == com.njxm.smart.activities.MedicalReportActivity.MEDICAL_COMMIT) {
            this.invalidateLayoutState(this.mLastMedicalState);
        } else {
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == this.mUploadBtn || v == this.mRetryUploadBtn) {
            this.invalidateLayoutState(com.njxm.smart.activities.MedicalReportActivity.MEDICAL_COMMIT);
        } else if (v == this.mCommitBtn) {
            this.uploadMedicalReports();
        }
    }

    private void uploadMedicalReports() {

        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(MultipartBody.Part.createFormData("sumrUserId", SPUtils.getStringValue(KeyConstant.KEY_USER_ID)));
        for (String filePath : this.mDatas) {
            if (filePath.endsWith("camera_default.png")) {
                continue;
            }
            File file = new File(filePath);
            parts.add(MultipartBody.Part.createFormData("files", file.getName(),
                    RequestBody.create(MediaType.parse("image/png"), file)));
        }

        UploadFileApi api = HttpUtils.getInstance().getApi(UploadFileApi.class);

        api.uploadFile(UrlPath.PATH_MEDICAL_REPORT_COMMIT.getPath(), parts).enqueue(new Callback<ServerResponseBean>() {
            @Override
            public void onResponse(Call<ServerResponseBean> call, Response<ServerResponseBean> response) {
                EventBus.getDefault().post(new ToastEvent(response.code() == 200 ? "上传成功" : response.message()));
            }

            @Override
            public void onFailure(Call<ServerResponseBean> call, Throwable t) {
                EventBus.getDefault().post(new ToastEvent("网络异常"));
            }
        });

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateView(MedicalBean bean) {
        this.tvModifyTime.setText("上传时间:  " + bean.getCreateTime());

        this.mDatas.clear();
        for (String path : bean.getPathList()) {
            this.mDatas.add(UrlPath.PATH_PICTURE_PREFIX.getUrl() + path);
        }

        this.adapter.setShowDelete(false);
        this.adapter.setNewData(this.mDatas);
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
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getDelFlag() {
            return this.delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return this.createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getModifyTime() {
            return this.modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getModifyUser() {
            return this.modifyUser;
        }

        public void setModifyUser(String modifyUser) {
            this.modifyUser = modifyUser;
        }

        public String getSumrStatus() {
            return this.sumrStatus;
        }

        public void setSumrStatus(String sumrStatus) {
            this.sumrStatus = sumrStatus;
        }

        public String getSumrUserId() {
            return this.sumrUserId;
        }

        public void setSumrUserId(String sumrUserId) {
            this.sumrUserId = sumrUserId;
        }

        public String getFiles() {
            return this.files;
        }

        public void setFiles(String files) {
            this.files = files;
        }

        public List<String> getPathList() {
            return this.pathList;
        }

        public void setPathList(List<String> pathList) {
            this.pathList = pathList;
        }
    }
}
