package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.activities.fragments.AttendanceFragment;
import com.njxm.smart.activities.fragments.MessagesFragment;
import com.njxm.smart.activities.fragments.PersonalFragment;
import com.njxm.smart.activities.fragments.WorkCenterFragment;
import com.njxm.smart.activities.fragments.adapter.MainFragmentAdapter;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.NoScrollViewPager;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 */
@Route(path = "/app/main")
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

    @BindView(R.id.attendance_icon)
    protected ImageButton ibAttendanceBtn;

    @BindView(R.id.attendance_text)
    protected TextView tvAttendanceBtn;

    @BindView(R.id.workcenter_icon)
    protected ImageButton ibWorkcenterBtn;

    @BindView(R.id.workcenter_text)
    protected TextView tvWorkcenterBtn;

    @BindView(R.id.message_icon)
    protected ImageButton ibMessageBtn;

    @BindView(R.id.message_text)
    protected TextView tvMessageBtn;

    @BindView(R.id.my_icon)
    protected ImageButton ibMyBtn;

    @BindView(R.id.my_text)
    protected TextView tvMyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = findViewById(R.id.view_pager);
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
        setViewPage(0);

        if (StringUtils.isEmpty(SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST))
                || SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST).equals("[]")) {
            HttpUtils.getInstance()
                    .request(RequestEvent.newBuilder()
                            .url(HttpUrlGlobal.HTTP_COMMON_CITY_URL).build());
        }
        AttendanceFragment.initLocationOption();
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

    @OnClick({R.id.attendance_btn, R.id.attendance_icon, R.id.attendance_text,
            R.id.workcenter_btn, R.id.workcenter_icon, R.id.workcenter_text,
            R.id.messages_btn, R.id.message_icon, R.id.message_text,
            R.id.my_btn, R.id.my_icon, R.id.my_text})
    protected void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.attendance_btn:
            case R.id.attendance_icon:
            case R.id.attendance_text:
                setViewPage(0);
                break;
            case R.id.workcenter_btn:
            case R.id.workcenter_icon:
            case R.id.workcenter_text:
                setViewPage(1);
                break;
            case R.id.messages_btn:
            case R.id.message_icon:
            case R.id.message_text:
                setViewPage(2);
                break;
            case R.id.my_btn:
            case R.id.my_icon:
            case R.id.my_text:
                setViewPage(3);
                break;
        }

    }

    /**
     * 设置View的属性状态
     *
     * @param position
     */
    private void setViewPage(int position) {
        mCurrentPosition = position;
        mViewPager.setCurrentItem(position);
        ibAttendanceBtn.setEnabled(position != 0);
        tvAttendanceBtn.setEnabled(position != 0);
        ibWorkcenterBtn.setEnabled(position != 1);
        tvWorkcenterBtn.setEnabled(position != 1);
        ibMessageBtn.setEnabled(position != 2);
        tvMessageBtn.setEnabled(position != 2);
        ibMyBtn.setEnabled(position != 3);
        tvMyBtn.setEnabled(position != 3);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mCurrentPosition == 0) {
            super.onBackPressed();
        } else {
            setViewPage(0);
        }
    }
}