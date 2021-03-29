package com.njxm.smart.fragments;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class BaseFragmentAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragments;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> paramFragments) {
        super(fm);
        this.mFragments = paramFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }
}
