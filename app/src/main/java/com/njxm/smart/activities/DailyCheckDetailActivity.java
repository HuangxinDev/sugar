package com.njxm.smart.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ns.demo.R;

/**
 * 默认
 */
public class DailyCheckDetailActivity extends BaseActivity {

    private boolean isEdit = false;

    @Override
    protected int setContentLayoutId() {
        return R.layout.workcenter_daily_check_detail_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        setActionBarTitle("巡检详情");
    }

    /**
     * 设置控件属性
     * @param tvId
     * @param resId
     */
    public void setText(int tvId, int resId) {
    }
}
