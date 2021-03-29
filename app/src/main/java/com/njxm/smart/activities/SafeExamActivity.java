package com.njxm.smart.activities;

import com.ntxm.smart.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;

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
