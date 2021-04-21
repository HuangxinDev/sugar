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

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.activities.AppBaseActivity;
import com.njxm.smart.activities.fragments.AttendanceFragment;
import com.njxm.smart.activities.fragments.MessagesFragment;
import com.njxm.smart.activities.fragments.PersonalFragment;
import com.njxm.smart.activities.fragments.WorkCenterFragment;
import com.ntxm.smart.R;
import com.ntxm.smart.databinding.ActivityMainBinding;

/**
 * 主页 RadioGroup + Fragment 实现不可滑动的切换效果
 */
@Route(path = "/app/main")
public class MainActivity extends AppBaseActivity<MainView, MainPresenter> implements MainView {

//    private final Map<Integer, Fragment> fragments = new HashMap<>();

    private final List<Fragment> fragments = new ArrayList<>();
    private final int mLastFragmentIndex = R.id.first_btn;
    private Fragment currentFragment;
    // FragmentManager
    private FragmentManager mFragmentManager;
    private ActivityMainBinding layoutBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.layoutBinding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.layoutBinding.getRoot());
        this.initVariants();
        this.initViews();
        this.fragments.add(new AttendanceFragment());
        this.fragments.add(new WorkCenterFragment());
        this.fragments.add(new MessagesFragment());
        this.fragments.add(new PersonalFragment());
        this.currentFragment = this.fragments.get(0);
        this.changeFragment(this.currentFragment, this.currentFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainPresenter.fetchCountryAddresses();
    }

    private void initVariants() {
        this.mFragmentManager = this.getSupportFragmentManager();
    }

    @SuppressLint("NonConstantResourceId")
    private void initViews() {
        this.layoutBinding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Fragment fragment = this.fragments.get(checkedId);
            this.changeFragment(this.currentFragment, fragment);
            this.currentFragment = fragment;
        });
        this.layoutBinding.firstBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });
    }

    @NonNull
    @Override
    protected MainPresenter newPresenter() {
        return new MainPresenter();
    }


    /**
     * 切换Fragment显示
     */
    private void changeFragment(Fragment fromFragment, Fragment toFragment) {
        if (toFragment == null || fromFragment == null) {
            return;
        }
        FragmentTransaction transaction = this.mFragmentManager.beginTransaction();
        if (!toFragment.isAdded()) {
            transaction.add(R.id.fragment_container, toFragment);
        }
        if (fromFragment == toFragment) {
            transaction.show(toFragment);
        } else {
            transaction.hide(fromFragment).show(toFragment);
        }
        transaction.commitNow();
    }

    /**
     * 在考勤页面退出应用
     */
    @Override
    public void onBackPressed() {
        if (this.isExitPage()) {
            super.onBackPressed();
            return;
        }
        this.layoutBinding.radioGroup.check(R.id.first_btn);
    }

    private boolean isExitPage() {
        return this.mLastFragmentIndex == R.id.first_btn;
    }
}