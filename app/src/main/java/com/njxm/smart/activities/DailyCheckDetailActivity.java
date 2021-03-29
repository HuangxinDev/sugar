package com.njxm.smart.activities;

import com.ntxm.smart.R;

import android.os.Bundle;
import androidx.annotation.Nullable;


/**
 * 默认
 */
public class DailyCheckDetailActivity extends BaseActivity {

    private final boolean isEdit = false;

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_inspect_task_detail_new_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.setActionBarTitle("巡检详情");
    }

    /**
     * 设置控件属性
     *
     * @param tvId
     * @param resId
     */
    public void setText(int tvId, int resId) {
    }
}
