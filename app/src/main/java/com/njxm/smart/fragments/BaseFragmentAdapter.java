package com.njxm.smart.fragments;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class BaseFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> paramFragments) {
        super(fm);
        this.mFragments = paramFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
