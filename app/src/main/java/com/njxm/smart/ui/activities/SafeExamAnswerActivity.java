/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.ui.fragments.ExamFragment;
import com.ntxm.smart.R;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/app/safe/activity")
public class SafeExamAnswerActivity extends BaseActivity {

    @BindView(R.id.next_step)
    protected View mNextBtn;
    protected int currentPostion = 0;
    private FragmentManager mFragmentManager;
    private List<QuestionBean> data = new ArrayList<>();

    public static List<QuestionBean> loadData() {

        List<QuestionBean> data = new ArrayList<>();
        data.add(new QuestionBean("单选", "单项选择"));

        data.add(new QuestionBean("多选", "多项选择"));

        data.add(new QuestionBean("判断", "判断选择"));

        return data;
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_exam_answer_activity;
    }

    @OnClick(R.id.next_step)
    public void clickNext() {
        if (this.currentPostion >= this.data.size()) {
            this.currentPostion = 0;
        }
        this.replaceFragment(this.data.get(this.currentPostion).getExamType());
        this.currentPostion++;
        this.mNextBtn.setEnabled(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mFragmentManager = this.getSupportFragmentManager();
        this.data = com.njxm.smart.ui.activities.SafeExamAnswerActivity.loadData();
        this.clickNext();
    }

    public void updateResult() {
        this.mNextBtn.setEnabled(true);
    }

    @OnClick(R.id.cancel)
    protected void cancel() {
        if (this.currentPostion < this.data.size() - 1) {
            Context context;
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("确定要退出吗？")
                    .setMessage("退出, 考试成绩将以0分计算,并且消耗一次考试机会。")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            com.njxm.smart.ui.activities.SafeExamAnswerActivity.this.finish();
                        }
                    })
                    .setPositiveButton("继续答题", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        } else {
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        this.cancel();
    }

    public void replaceFragment(String name) {
        FragmentTransaction transaction = this.mFragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, ExamFragment.newInstance(name))
                .commit();

    }

    public static final class QuestionBean {
        public String examType;
        public String examMessage;

        public List<String> examResult;
        public List<String> examUserResult;

        public QuestionBean(String examType, String examMessage) {
            this.examType = examType;
            this.examMessage = examMessage;
        }

        public String getExamType() {
            return this.examType;
        }

        public void setExamType(String examType) {
            this.examType = examType;
        }

        public String getExamMessage() {
            return this.examMessage;
        }

        public void setExamMessage(String examMessage) {
            this.examMessage = examMessage;
        }

        public List<String> getExamResult() {
            return this.examResult;
        }

        public void setExamResult(List<String> examResult) {
            this.examResult = examResult;
        }

        public List<String> getExamUserResult() {
            return this.examUserResult;
        }

        public void setExamUserResult(List<String> examUserResult) {
            this.examUserResult = examUserResult;
        }
    }

}
