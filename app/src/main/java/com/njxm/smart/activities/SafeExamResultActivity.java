package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ntxm.smart.R;

public class SafeExamResultActivity extends BaseActivity {

    private boolean mSuccess; // 考试是否过关

    @Override
    protected int setContentLayoutId() {
        return mSuccess ? R.layout.safe_exam_success : R.layout.safe_exam_failed;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mSuccess = intent.getBooleanExtra("result", false);
        }
        super.onCreate(savedInstanceState);
        showView(mActionBarTitle, false);
    }
}
