package com.njxm.smart.activities;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.njxm.smart.activities.adapter.DailyCheckAdapter;
import com.njxm.smart.model.jsonbean.DailyCheckTaskBean;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 日常巡检-页面
 */
public class DailyCheckActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Override
    protected int setContentLayoutId() {
        return R.layout.workcenter_daily_check_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        setActionBarTitle("日常巡检");
        showRightBtn(true, R.mipmap.new_add);

        DailyCheckAdapter mDailyCheckAdapter = new DailyCheckAdapter(getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDailyCheckAdapter);
    }

    private List<DailyCheckTaskBean> getData() {
        List<DailyCheckTaskBean> datas = new ArrayList<>();
        datas.add(new DailyCheckTaskBean("任务1", "任务1ccsac " +
                "jahjjjjjjskjdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                "内容", 0, "2019-12-22 10:00:00"));
        datas.add(new DailyCheckTaskBean("任务2", "任务2内容", 1, "2019-12-23 10:00:00"));
        datas.add(new DailyCheckTaskBean("任务3", "任务3内容", 2, "2019-12-24 10:00:00"));
        datas.add(new DailyCheckTaskBean("任务4", "任务4内容", 1, "2019-12-25 10:00:00"));
        datas.add(new DailyCheckTaskBean("任务5", "任务5内容", 2, "2019-12-26 10:00:00"));
        return datas;
    }
}
