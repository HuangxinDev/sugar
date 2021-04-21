/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities.fragments.adapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public MainFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void setFragmentDatas(List<Fragment> fragments) {
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (this.mFragments == null || this.mFragments.isEmpty()) {
            return null;
        }
        return this.mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (this.mFragments == null) {
            return 0;
        }
        return this.mFragments.size();
    }

}
