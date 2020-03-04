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
        setActionBarTitle("创建巡检任务");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        mNavTab.setVisibility(View.GONE);
    }
}
