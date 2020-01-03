package com.njxm.smart.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.eventbus.ToastEvent;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/app/safety/examination")
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
        actionBarLayout.setBackgroundColor(Color.parseColor("#3F98FA"));
        showView(mActionBarTitle, false);
        showLeftBtn(true, R.mipmap.arrow_back);
        showRightBtn(true, "考试记录");
        tvActionBarRightText.setTextColor(Color.WHITE);
    }

    @OnClick(R.id.start_exam)
    protected void startExam() {
        EventBus.getDefault().post(new ToastEvent("开始考试"));
    }
}
