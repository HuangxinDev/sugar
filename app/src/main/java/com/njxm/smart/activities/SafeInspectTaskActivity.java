/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ntxm.smart.R;

import butterknife.BindView;

public class SafeInspectTaskActivity extends BaseActivity {

    @BindView(R.id.nav_tab)
    protected View mNavTab;

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_inspect_task_detail_new_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("创建巡检任务");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.mNavTab.setVisibility(View.GONE);
    }
}
