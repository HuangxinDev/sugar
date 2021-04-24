/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.activities.AppBaseActivity;
import com.ntxm.smart.R;

/**
 * 主页 RadioGroup + Fragment 实现不可滑动的切换效果
 */
@Route(path = "/app/main")
public class MainActivity extends AppBaseActivity<MainView, MainPresenter> implements MainView {
    private static final int INVALID_ACTION = -1;
    private final List<NavTab> navTabs = new ArrayList<>();
    private int lastSelected = R.id.first_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initViews();
        this.initNavTabs();
    }

    @SuppressLint("NonConstantResourceId")
    private void initViews() {
        RadioGroup radioGroup = this.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int actionId = this.getNavActionId(this.lastSelected, checkedId);
            this.lastSelected = checkedId;
            if (actionId != MainActivity.INVALID_ACTION) {
                Navigation.findNavController(this, R.id.fragment_container).navigate(actionId);
            }
        });
    }

    /**
     * 添加页面和导航action的映射关系
     */
    private void initNavTabs() {
        this.navTabs.add(new NavTab(R.id.first_btn, R.id.btn2, R.id.action_attendanceFragment_to_workCenterFragment));
        this.navTabs.add(new NavTab(R.id.first_btn, R.id.btn3, R.id.action_attendanceFragment_to_messageFragment));
        this.navTabs.add(new NavTab(R.id.first_btn, R.id.btn4, R.id.action_attendanceFragment_to_personalFragment));
        this.navTabs.add(new NavTab(R.id.btn2, R.id.first_btn, R.id.action_workCenterFragment_to_attendanceFragment));
        this.navTabs.add(new NavTab(R.id.btn2, R.id.btn3, R.id.action_workCenterFragment_to_messageFragment));
        this.navTabs.add(new NavTab(R.id.btn2, R.id.btn4, R.id.action_workCenterFragment_to_personalFragment));
        this.navTabs.add(new NavTab(R.id.btn3, R.id.first_btn, R.id.action_messageFragment_to_attendanceFragment));
        this.navTabs.add(new NavTab(R.id.btn3, R.id.btn2, R.id.action_messageFragment_to_workCenterFragment));
        this.navTabs.add(new NavTab(R.id.btn3, R.id.btn4, R.id.action_messageFragment_to_personalFragment));
        this.navTabs.add(new NavTab(R.id.btn4, R.id.first_btn, R.id.action_personalFragment_to_attendanceFragment));
        this.navTabs.add(new NavTab(R.id.btn4, R.id.btn2, R.id.action_personalFragment_to_workCenterFragment));
        this.navTabs.add(new NavTab(R.id.btn4, R.id.btn3, R.id.action_personalFragment_to_messageFragment));
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

    private int getNavActionId(int from, int to) {
        for (NavTab navTab : this.navTabs) {
            if (navTab.equals(new NavTab(from, to, -1))) {
                return navTab.action;
            }
        }
        return MainActivity.INVALID_ACTION;
    }

    @NonNull
    @Override
    protected MainPresenter newPresenter() {
        return new MainPresenter();
    }

    private static class NavTab {
        int from;
        int to;
        int action;

        public NavTab(int from, int to, int action) {
            this.from = from;
            this.to = to;
            this.action = action;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            NavTab navTab = (NavTab) o;
            return this.from == navTab.from &&
                    this.to == navTab.to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.from, this.to);
        }
    }
}