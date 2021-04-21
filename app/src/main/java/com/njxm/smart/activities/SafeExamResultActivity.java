/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ntxm.smart.R;

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
