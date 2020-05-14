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
    private String name;

    @BindView(R.id.title_type)
    protected TextView mQuestionTypeTv;

    @BindView(R.id.title)
    protected TextView mQuestionTitleTv;

    @Nullable
    @BindView(R.id.select_group)
    protected RadioGroup mSelectGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(PARAM_EXAM);
    }

    public static Fragment newInstance(String name) {
        Fragment fragment = new ExamFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_EXAM, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mQuestionTypeTv.setText(name);

        if (mSelectGroup != null) {
            mSelectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (getActivity() != null) {
                        ((SafeExamAnswerActivity) getActivity()).updateResult();
                    }
                }
            });
        }
    }

    @Override
    protected int setLayoutResourceID() {

        if ("判断".equals(name)) {
            return R.layout.item_safe_exam_judge;
        } else if ("多选".equals(name)) {
            return R.layout.item_safe_exam_mul_selection;
        } else {
            return R.layout.item_safe_exam_single_selection;
        }

    }
}