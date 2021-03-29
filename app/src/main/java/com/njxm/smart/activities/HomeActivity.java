package com.njxm.smart.activities;

import com.njxm.smart.fragments.BaseFragmentAdapter;
import com.njxm.smart.fragments.HomeFragment;
import com.njxm.smart.view.DotsView;
import com.ntxm.smart.R;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

/**
 * 引导页
 */
public class HomeActivity extends BaseActivity {


    // mdskds
    private final int[] res = {R.mipmap.startup_one, R.mipmap.startup_three, R.mipmap.startup_two};

    private SharedPreferences mSharedPreferences;

    // 立即体验
    private TextView mTextView;

    private DotsView mDotsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isFirstLaunch = this.mSharedPreferences.getBoolean("isFirst", true);
        if (!isFirstLaunch) {
            this.startLoginActivity();
            return;
        }

        this.mDotsView = this.findViewById(R.id.dots_view);
        this.mTextView = this.findViewById(R.id.experience);
        ViewPager mViewPager = this.findViewById(R.id.home_view_pager);

        this.mDotsView.setDotSize(this.res.length);
        this.mDotsView.setSelected(0);
        FragmentManager mFragmentManager = this.getSupportFragmentManager();
        List<Fragment> fragments = new ArrayList<>();
        for (int resId : this.res) {
            fragments.add(new HomeFragment(resId));
        }
        mViewPager.setAdapter(new BaseFragmentAdapter(mFragmentManager, fragments));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                com.njxm.smart.activities.HomeActivity.this.mDotsView.setSelected(position);
                com.njxm.smart.activities.HomeActivity.this.mTextView.setVisibility((position == com.njxm.smart.activities.HomeActivity.this.res.length - 1) ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onPageSelected(int position) {
                //
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//
            }
        });

        this.mTextView.setOnClickListener(v -> {
            this.mSharedPreferences.edit().putBoolean("isFirst", false).apply();
            this.startLoginActivity();
        });
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_home;
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}
