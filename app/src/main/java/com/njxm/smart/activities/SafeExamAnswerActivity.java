package com.njxm.smart.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.fragments.ExamFragment;
import com.ntxm.smart.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/app/safe/activity")
public class SafeExamAnswerActivity extends BaseActivity {

    @BindView(R.id.next_step)
    protected View mNextBtn;

    private FragmentManager mFragmentManager;

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_exam_answer_activity;
    }

    protected int currentPostion = 0;
    private List<QuestionBean> data = new ArrayList<>();

    @OnClick(R.id.next_step)
    public void clickNext() {
        if (currentPostion >= data.size()) {
            currentPostion = 0;
        }
        replaceFragment(data.get(currentPostion).getExamType());
        currentPostion++;
        mNextBtn.setEnabled(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        data = loadData();
        clickNext();
    }

    public void updateResult() {
        mNextBtn.setEnabled(true);
    }

    @OnClick(R.id.cancel)
    protected void cancel() {
        if (currentPostion < data.size() - 1) {
            Context context;
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("确定要退出吗？")
                    .setMessage("退出, 考试成绩将以0分计算,并且消耗一次考试机会。")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
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
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

    public void replaceFragment(String name) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, ExamFragment.newInstance(name))
                .commit();

    }

    public List<QuestionBean> loadData() {

        List<QuestionBean> data = new ArrayList<>();
        data.add(new QuestionBean("单选", "单项选择"));

        data.add(new QuestionBean("多选", "多项选择"));

        data.add(new QuestionBean("判断", "判断选择"));

        return data;
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
            return examType;
        }

        public void setExamType(String examType) {
            this.examType = examType;
        }

        public String getExamMessage() {
            return examMessage;
        }

        public void setExamMessage(String examMessage) {
            this.examMessage = examMessage;
        }

        public List<String> getExamResult() {
            return examResult;
        }

        public void setExamResult(List<String> examResult) {
            this.examResult = examResult;
        }

        public List<String> getExamUserResult() {
            return examUserResult;
        }

        public void setExamUserResult(List<String> examUserResult) {
            this.examUserResult = examUserResult;
        }
    }

}
