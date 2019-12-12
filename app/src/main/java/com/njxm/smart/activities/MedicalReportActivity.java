package com.njxm.smart.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.SimpleImageAdapter;
import com.njxm.smart.utils.ResolutionUtil;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;


public class MedicalReportActivity extends BaseActivity {


    private SimpleImageAdapter adapter;

    private static final int MEDICAL_VOID = 704;
    private static final int MEDICAL_COMMIT = 5;
    private static final int MEDICAL_CHECK_WAIT = 854;
    private static final int MEDICAL_CHECK_SUCCESS = 751;
    private static final int MEDICAL_CHECK_RETRY = 328;

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


        new Thread(new Runnable() {

            @Override
            public void run() {
                SystemClock.sleep(500);

                MedicalReportActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMedicalState = MEDICAL_CHECK_RETRY;
                        invalidateLayoutState();
                    }
                });
            }
        }).start();


        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(getDrawable(default_icon));
        adapter = new SimpleImageAdapter(drawables);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == adapter.getData().size() - 1) {
                    takePhoto();
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
        invalidateLayoutState();

    }

    private void invalidateLayoutState() {
        switch (mMedicalState) {
            case MEDICAL_VOID:
                mMedicalVoidLayout.setVisibility(View.VISIBLE);
                mMedicalCommitLayout.setVisibility(View.GONE);
                mMedicalCheckWaitLayout.setVisibility(View.GONE);
                mMedicalCheckRetryLayout.setVisibility(View.GONE);
                break;
            case MEDICAL_COMMIT:
                mMedicalVoidLayout.setVisibility(View.GONE);
                mMedicalCommitLayout.setVisibility(View.VISIBLE);
                mMedicalCheckSuccessHeaderLayout.setVisibility(View.GONE);
                mCommitBtn.setVisibility(View.VISIBLE);
                mMedicalCheckWaitLayout.setVisibility(View.GONE);
                mMedicalCheckRetryLayout.setVisibility(View.GONE);
                break;
            case MEDICAL_CHECK_WAIT:
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
            case MEDICAL_CHECK_RETRY:
                mMedicalVoidLayout.setVisibility(View.GONE);
                mMedicalCommitLayout.setVisibility(View.GONE);
                mCommitBtn.setVisibility(View.GONE);
                mMedicalCheckWaitLayout.setVisibility(View.GONE);
                mMedicalCheckRetryLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
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
            case MEDICAL_CHECK_SUCCESS:
            default:
                finish();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mUploadBtn || v == mRetryUploadBtn) {
            mMedicalState = MEDICAL_COMMIT;
            takePhoto();
        } else if (v == mCommitBtn) {
            mMedicalState = MEDICAL_CHECK_WAIT;
            invalidateLayoutState();
        }
    }

    private void takePhoto() {
        Intent starter = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(starter, 100);
    }
}
