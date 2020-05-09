package com.njxm.smart.activities;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.activities.fragments.AttendanceFragment;
import com.njxm.smart.activities.fragments.MessagesFragment;
import com.njxm.smart.activities.fragments.PersonalFragment;
import com.njxm.smart.activities.fragments.WorkCenterFragment;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ntxm.smart.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 主页 RadioGroup + Fragment 实现不可滑动的切换效果
 */
@Route(path = "/app/main")
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    // FragmentManager
    private FragmentManager mFragmentManager;

    private final Map<Integer, Fragment> fragments = new HashMap<>();
    @BindView(R.id.radio_group)
    protected RadioGroup mBarGroup;
    private int mLastFragmentIndex = R.id.first_btn;

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        mBarGroup.setOnCheckedChangeListener(this);
        mBarGroup.check(R.id.first_btn);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (StringUtils.isEmpty(SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST))
                || SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST).equals("[]")) {
            HttpUtils.getInstance()
                    .request(RequestEvent.newBuilder()
                            .url(UrlPath.PATH_PROVINCE_CITY_AREA.getUrl()).build());
        }
    }

    /**
     * 切换Fragment显示
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragmnet) {
        if (toFragmnet == null || fromFragment == null) {
            return;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (!toFragmnet.isAdded()) {
            transaction.add(R.id.fragment_container, toFragmnet);
        }

        if (fromFragment == toFragmnet) {
            transaction.show(toFragmnet);
        } else {
            transaction.hide(fromFragment).show(toFragmnet);
        }

        transaction.commitNow();
    }

    /**
     * 在考勤页面退出应用
     */
    @Override
    public void onBackPressed() {
        if (mLastFragmentIndex == R.id.first_btn) {
            super.onBackPressed();
        } else {
            mBarGroup.check(R.id.first_btn);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Fragment fragment = fragments.get(checkedId);

        if (fragment == null) {
            switch (checkedId) {
                case R.id.first_btn:
                    fragment = new AttendanceFragment();
                    break;
                case R.id.btn2:
                    fragment = new WorkCenterFragment();
                    break;

                case R.id.btn3:
                    fragment = new MessagesFragment();
                    break;

                case R.id.btn4:
                    fragment = new PersonalFragment();
                    break;
                default:
                    return;
            }
            fragments.put(checkedId, fragment);

        }

        switchFragment(fragments.get(mLastFragmentIndex), fragment);
        mLastFragmentIndex = checkedId;
    }
}