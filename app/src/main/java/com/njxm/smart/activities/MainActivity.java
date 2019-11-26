package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.njxm.smart.activities.fragments.AttendanceFragment;
import com.njxm.smart.activities.fragments.MessagesFragment;
import com.njxm.smart.activities.fragments.PersonalFragment;
import com.njxm.smart.activities.fragments.WorkCenterFragment;
import com.njxm.smart.activities.fragments.adapter.MainFragmentAdapter;
import com.njxm.smart.view.NoScrollViewPager;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    // ViewPager
    private NoScrollViewPager mViewPager;

    private int mCurrentPostion = 0;

    // FragmentManager
    private FragmentManager mFragmentManager;

    // FragmentAdapter
    private MainFragmentAdapter mFragmentAdapter;

    // FragmentAdapter 数据
    private List<Fragment> fragments = new ArrayList<>();

    private String[] mFragmentTabs = {"考勤", "工作中心", "消息", "我的"};

    private View mAttendanceBtn;
    private View mWorkCenterBtn;
    private View mMessagesBtn;
    private View mPersonalBtn;

    private View mPictureAttendance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.view_pager);
//        mViewPager.setNeedScroll(true);
        mFragmentManager = getSupportFragmentManager();

        AttendanceFragment attendanceFragment = new AttendanceFragment();
        WorkCenterFragment workCenterFragment = new WorkCenterFragment();
        MessagesFragment messagesFragment = new MessagesFragment();
        PersonalFragment personalFragment = new PersonalFragment();
        fragments.add(attendanceFragment);
        fragments.add(workCenterFragment);
        fragments.add(messagesFragment);
        fragments.add(personalFragment);
        mFragmentAdapter = new MainFragmentAdapter(mFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentAdapter.setFragmentDatas(fragments);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(mCurrentPostion);


        mAttendanceBtn = findViewById(R.id.attendance_btn);
        mAttendanceBtn.setActivated(true);
        mAttendanceBtn.setOnClickListener(this);
        mWorkCenterBtn = findViewById(R.id.workcenter_btn);
        mWorkCenterBtn.setOnClickListener(this);
        mMessagesBtn = findViewById(R.id.messages_btn);
        mMessagesBtn.setOnClickListener(this);
        mPersonalBtn = findViewById(R.id.personal_btn);
        mPersonalBtn.setOnClickListener(this);

        mPictureAttendance = findViewById(R.id.picture_attendance_icon);
        mPictureAttendance.setActivated(false);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attendance_btn:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.workcenter_btn:
                mViewPager.setCurrentItem(1);
                mAttendanceBtn.setActivated(false);
                mPictureAttendance.setActivated(true);
                break;
            case R.id.messages_btn:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.personal_btn:
                mViewPager.setCurrentItem(3);
                break;

        }
    }
}