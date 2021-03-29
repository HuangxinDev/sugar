package com.njxm.smart.activities;

import com.ntxm.smart.R;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

public class SafeExamResultActivity extends BaseActivity {

    private boolean mSuccess; // 考试是否过关

    @Override
    protected int setContentLayoutId() {
        return this.mSuccess ? R.layout.safe_exam_success : R.layout.safe_exam_failed;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = this.getIntent();
        if (intent != null) {
            this.mSuccess = intent.getBooleanExtra("result", false);
        }
        super.onCreate(savedInstanceState);
        this.showView(this.mActionBarTitle, false);
    }
}
