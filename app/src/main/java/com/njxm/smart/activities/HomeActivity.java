package com.njxm.smart.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.njxm.smart.fragments.BaseFragmentAdapter;
import com.njxm.smart.fragments.HomeFragment;
import com.njxm.smart.view.DotsView;
import com.ntxm.smart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class HomeActivity extends BaseActivity {


    private int[] res = {R.mipmap.startup_one, R.mipmap.startup_three, R.mipmap.startup_two};

    private SharedPreferences mSharedPreferences;

    private FragmentManager mFragmentManager;

    // 立即体验
    private TextView mTextView;

    private ViewPager mViewPager;

    private DotsView mDotsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        boolean isFirstLaunch = mSharedPreferences.getBoolean("isFirst", true);
        if (!isFirstLaunch) {
            startLoginActivity();
            return;
        }

        mDotsView = findViewById(R.id.dots_view);
        mTextView = findViewById(R.id.experience);
        mViewPager = findViewById(R.id.home_view_pager);

        mDotsView.setDotSize(res.length);
        mDotsView.setSelected(0);
        mFragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = new ArrayList<>();
        for (int resId : res) {
            fragments.add(new HomeFragment(resId));
        }
        mViewPager.setAdapter(new BaseFragmentAdapter(mFragmentManager, fragments));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mDotsView.setSelected(position);
                mTextView.setVisibility((position == res.length - 1) ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharedPreferences.edit().putBoolean("isFirst", false).apply();
                startLoginActivity();
            }
        });
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_home;
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
