/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.fragments;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.njxm.smart.activities.SafeExamAnswerActivity;
import com.njxm.smart.activities.fragments.BaseFragment;
import com.ntxm.smart.R;

import butterknife.BindView;

/**
 * Created by Hxin on 2020/5/9
 * Function: 考试Fragment
 */
public class ExamFragment extends BaseFragment {

    private static final String PARAM_EXAM = "exam_param";
    @BindView(R.id.title_type)
    protected TextView mQuestionTypeTv;
    @BindView(R.id.title)
    protected TextView mQuestionTitleTv;
    @Nullable
    @BindView(R.id.select_group)
    protected RadioGroup mSelectGroup;
    private String name;

    public static Fragment newInstance(String name) {
        Fragment fragment = new ExamFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ExamFragment.PARAM_EXAM, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.name = this.getArguments().getString(ExamFragment.PARAM_EXAM);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
        this.mQuestionTypeTv.setText(this.name);

        if (this.mSelectGroup != null) {
            this.mSelectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (ExamFragment.this.getActivity() != null) {
                        ((SafeExamAnswerActivity) ExamFragment.this.getActivity()).updateResult();
                    }
                }
            });
        }
    }

    @Override
    protected int setLayoutResourceID() {

        if ("判断".equals(this.name)) {
            return R.layout.item_safe_exam_judge;
        } else if ("多选".equals(this.name)) {
            return R.layout.item_safe_exam_mul_selection;
        } else {
            return R.layout.item_safe_exam_single_selection;
        }

    }
}