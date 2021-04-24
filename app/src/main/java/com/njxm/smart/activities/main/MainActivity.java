/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.activities.AppBaseActivity;
import com.ntxm.smart.R;

/**
 * 主页 RadioGroup + Fragment 实现不可滑动的切换效果
 */
@Route(path = "/app/main")
public class MainActivity extends AppBaseActivity<MainView, MainPresenter> implements MainView {
    private int lastSelected = R.id.first_btn;

    private ConstraintLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initViews();
        this.container = this.findViewById(R.id.ll_root);
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainPresenter.fetchCountryAddresses();

    }

    @SuppressLint("NonConstantResourceId")
    private void initViews() {
        RadioGroup radioGroup = this.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int selectedId = this.lastSelected;
            if (this.lastSelected == checkedId) {
                return;
            }
            this.lastSelected = checkedId;
            int navId = -1;
            switch (selectedId) {
                case R.id.first_btn:
                    if (checkedId == R.id.btn2) {
                        navId = R.id.action_attendanceFragment_to_workCenterFragment;
                    } else if (checkedId == R.id.btn3) {
                        navId = R.id.action_attendanceFragment_to_messageFragment;
                    } else if (checkedId == R.id.btn4) {
                        navId = R.id.action_attendanceFragment_to_personalFragment;
                    }
                    break;
                case R.id.btn2:
                    if (checkedId == R.id.first_btn) {
                        navId = R.id.action_workCenterFragment_to_attendanceFragment;
                    } else if (checkedId == R.id.btn3) {
                        navId = R.id.action_workCenterFragment_to_messageFragment;
                    } else if (checkedId == R.id.btn4) {
                        navId = R.id.action_workCenterFragment_to_personalFragment;
                    }
                    break;
                case R.id.btn3:
                    if (checkedId == R.id.first_btn) {
                        navId = R.id.action_messageFragment_to_attendanceFragment;
                    } else if (checkedId == R.id.btn2) {
                        navId = R.id.action_messageFragment_to_workCenterFragment;
                    } else if (checkedId == R.id.btn4) {
                        navId = R.id.action_messageFragment_to_personalFragment;
                    }
                    break;
                case R.id.btn4:
                    if (checkedId == R.id.first_btn) {
                        navId = R.id.action_personalFragment_to_attendanceFragment;
                    } else if (checkedId == R.id.btn2) {
                        navId = R.id.action_personalFragment_to_workCenterFragment;
                    } else if (checkedId == R.id.btn3) {
                        navId = R.id.action_personalFragment_to_messageFragment;
                    }
                    break;
                default:
            }
            if (navId != -1) {
                Navigation.findNavController(this, R.id.fragment_container).navigate(navId);
            }
        });
    }

    @NonNull
    @Override
    protected MainPresenter newPresenter() {
        return new MainPresenter();
    }
}