/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ntxm.smart.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SafeExamActivity extends BaseActivity {

    @BindView(R.id.action_bar)
    protected View actionBarLayout;

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_exam_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.actionBarLayout.setBackgroundColor(Color.parseColor("#3F98FA"));
        this.showView(this.mActionBarTitle, false);
        this.showLeftBtn(true, R.mipmap.arrow_back);
        this.showRightBtn(true, "考试记录");
        this.tvActionBarRightText.setTextColor(Color.WHITE);
    }

    @OnClick(R.id.start_exam)
    protected void startExam() {
        this.startActivity(new Intent(this, SafeExamAnswerActivity.class));
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        this.startActivity(new Intent(this, SafeExamRecordActivity.class));
    }
}
