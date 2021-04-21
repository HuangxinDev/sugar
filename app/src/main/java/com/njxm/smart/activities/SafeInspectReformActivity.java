/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ntxm.smart.R;

import butterknife.BindView;

public class SafeInspectReformActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_inspect_reform_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle(R.string.reform_record);
    }
}
