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
import com.njxm.smart.activities.fragments.ZoomOutPageTransformer;
import com.njxm.smart.activities.fragments.adapter.MainFragmentAdapter;
import com.njxm.smart.view.NoScrollViewPager;
import com.ntxm.smart.R;

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
    @BindView(R.id.view_pager)
    public NoScrollViewPager mViewPager;

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
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setCurrentItem(mCurrentPosition);
        setViewPage(0);

//        if (StringUtils.isEmpty(SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST))
//                || SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST).equals("[]")) {
//            HttpUtils.getInstance()
//                    .request(RequestEvent.newBuilder()
//                            .url(UrlPath.PATH_PROVINCE_CITY_AREA.getUrl()).build());
//        }

//        GetAreaApi getAreaApi = HttpUtils.getInstance().getApi(GetAreaApi.class);
//        Call<ResponseBody> call = getAreaApi.getAreaData();
//        Call<List<AddressBean>> call = getAreaApi.testArrayTest();
//        Call<ResponseBody> call2 = getAreaApi.getAreaData();
//        call.enqueue(new Callback<List<AddressBean>>() {
//            @Override
//            public void onResponse(Call<List<AddressBean>> call, Response<List<AddressBean>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    LogTool.printD("Sugar", "Address size: " + response.body().size());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<AddressBean>> call, Throwable t) {
//                LogTool.printD("Sugar", "Get Area Failed: " + Log.getStackTraceString(t));
//            }
//        });

//        call2.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                ResponseBody responseBody = response.body();
//                if (responseBody != null) {
//
//                    try {
//                        HttpBean bean = JSON.parseObject(responseBody.string(), HttpBean.class);
//
//                        LogTool.printD("Sugar", "Get Area Data: " + responseBody.string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                LogTool.printE("Sugar", "Get Area Data: " + Log.getStackTraceString(t));
//            }
//        });
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