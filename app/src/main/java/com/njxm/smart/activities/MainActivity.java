package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.njxm.smart.activities.fragments.AttendanceFragment;
import com.njxm.smart.activities.fragments.MessagesFragment;
import com.njxm.smart.activities.fragments.PersonalFragment;
import com.njxm.smart.activities.fragments.WorkCenterFragment;
import com.njxm.smart.activities.fragments.adapter.MainFragmentAdapter;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.view.ButtonBarItem;
import com.njxm.smart.view.NoScrollViewPager;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    // ViewPager
    private NoScrollViewPager mViewPager;

    private int mCurrentPosition = 0;

    // FragmentManager
    private FragmentManager mFragmentManager;

    // FragmentAdapter
    private MainFragmentAdapter mFragmentAdapter;

    // FragmentAdapter 数据
    private List<Fragment> fragments = new ArrayList<>();

    private String[] mFragmentTabs = {"考勤", "工作中心", "消息", "我的"};

    private ButtonBarItem mAttendanceBtn;
    private ButtonBarItem mWorkCenterBtn;
    private ButtonBarItem mMessagesBtn;
    private ButtonBarItem mPersonalBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mViewPager.setCurrentItem(mCurrentPosition);

        mAttendanceBtn = findViewById(R.id.attendance_btn);
        mAttendanceBtn.setActivated(true);
        mAttendanceBtn.setOnClickListener(this);
        mWorkCenterBtn = findViewById(R.id.workcenter_btn);
        mWorkCenterBtn.setOnClickListener(this);
        mWorkCenterBtn.setActivated(false);
        mMessagesBtn = findViewById(R.id.messages_btn);
        mMessagesBtn.setOnClickListener(this);
        mMessagesBtn.setActivated(false);
        mPersonalBtn = findViewById(R.id.personal_btn);
        mPersonalBtn.setOnClickListener(this);
        mPersonalBtn.setActivated(false);
        setViewPage(0);

        HttpUtils.getInstance().postDataWithParams(-1, HttpUrlGlobal.HTTP_COMMON_CITY_URL, null,
                this);
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        mViewPager.setCurrentItem(position);
        showFragment(position);
    }

    public void showFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (fragments.get(mCurrentPosition) != null) {
            transaction.hide(fragments.get(mCurrentPosition));
        }
        mCurrentPosition = index;
        if (fragments.get(mCurrentPosition) != null) {
            transaction.show(fragments.get(mCurrentPosition));
        }
        transaction.commitAllowingStateLoss();

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attendance_btn:
                setViewPage(0);
                break;
            case R.id.workcenter_btn:
                setViewPage(1);
                break;
            case R.id.messages_btn:
                setViewPage(2);
                break;
            case R.id.personal_btn:
                setViewPage(3);
                break;
        }
    }

    private void setViewPage(int position) {
        mViewPager.setCurrentItem(position);
        mAttendanceBtn.setActivated(position == 0);
        mWorkCenterBtn.setActivated(position == 1);
        mMessagesBtn.setActivated(position == 2);
        mPersonalBtn.setActivated(position == 3);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        super.onSuccess(requestId, success, code, data);
        if (requestId == -1 && code == 200) {
            SPUtils.putValue(KeyConstant.KEY_COMMON_ADDRESS_LIST, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setViewPage(0);
    }

}