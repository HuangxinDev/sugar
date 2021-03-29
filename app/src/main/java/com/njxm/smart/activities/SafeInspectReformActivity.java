package com.njxm.smart.activities;

import com.ntxm.smart.R;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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
