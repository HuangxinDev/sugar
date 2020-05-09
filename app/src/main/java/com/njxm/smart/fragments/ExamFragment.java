package com.njxm.smart.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.njxm.smart.activities.fragments.BaseFragment;
import com.ntxm.smart.R;

/**
 * Created by Hxin on 2020/5/9
 * Function: 考试Fragment
 */
public class ExamFragment extends BaseFragment {

    private static final String PARAM_EXAM = "exam_param";
    private String name;

    public static Fragment newInstance(String name) {
        Fragment fragment = new ExamFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_EXAM, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(PARAM_EXAM);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResourceID() {

        if ("考题一".equals(name)) {
            return R.layout.item_safe_exam_judge;
        } else if ("考题二".equals(name)) {
            return R.layout.item_safe_exam_mul_selection;
        } else {
            return R.layout.item_safe_exam_single_selection;
        }

    }


}