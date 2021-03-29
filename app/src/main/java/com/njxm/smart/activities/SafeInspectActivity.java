package com.njxm.smart.activities;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.activities.adapter.DailyCheckAdapter;
import com.njxm.smart.model.jsonbean.DailyCheckTaskBean;
import com.ntxm.smart.R;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

/**
 * 日常巡检-页面（默认无数据、查看数据、高管有权限创建任务）
 *
 * 页面逻辑-请求巡检记录数据、
 * 无: 显示默认
 * 有: 展示数据，（高管并显示右侧状态栏按钮，可以创建巡检项目）
 */
@Route(path = "/app/safety/inspect")
public class SafeInspectActivity extends BaseActivity {

    /**
     * 展示巡检数据列表
     */
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    /**
     * 对应无数据的选项
     */
    @BindView(R.id.default_1)
    protected RelativeLayout rlVoidData;


    private List<DailyCheckTaskBean> mData = new ArrayList<>();

    private DailyCheckAdapter mDailyCheckAdapter;



    @Override
    protected int setContentLayoutId() {
        return R.layout.workcenter_daily_check_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.setActionBarTitle("日常巡检");
        this.showRightBtn(true, R.mipmap.new_add);


        this.mDailyCheckAdapter = new DailyCheckAdapter(this.mData);
        this.mDailyCheckAdapter.setOnItemClickListener((adapter, view, position) -> {
            EventBus.getDefault().postSticky((adapter.getItem(position)));
            this.startActivity(new Intent(this, SafeInspectTaskDetailActivity.class));
        });
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mRecyclerView.setAdapter(this.mDailyCheckAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                com.njxm.smart.activities.SafeInspectActivity.this.mData = com.njxm.smart.activities.SafeInspectActivity.this.getData();
                SystemClock.sleep(2000);
                com.njxm.smart.activities.SafeInspectActivity.this.updateView();
            }
        }).start();

        this.updateView();
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        this.startActivity(new Intent(this, SafeInspectTaskActivity.class));
    }

    private void updateView() {
        this.invoke(new Runnable() {
            @Override
            public void run() {
                boolean hasData = com.njxm.smart.activities.SafeInspectActivity.this.mData.size() > 0;
                com.njxm.smart.activities.SafeInspectActivity.this.rlVoidData.setVisibility(hasData ? View.GONE : View.VISIBLE);
                com.njxm.smart.activities.SafeInspectActivity.this.mRecyclerView.setVisibility(hasData ? View.VISIBLE : View.GONE);
                if (hasData) {
                    com.njxm.smart.activities.SafeInspectActivity.this.mDailyCheckAdapter.setNewData(com.njxm.smart.activities.SafeInspectActivity.this.mData);
                }
            }
        });
    }


    private List<DailyCheckTaskBean> getData() {
        this.mData.add(new DailyCheckTaskBean("任务1", "任务1ccsac " +
                "jahjjjjjjskjdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                "内容", 0, "2019-12-22 10:00:00"));
        this.mData.add(new DailyCheckTaskBean("任务2", "任务2内容", 1, "2019-12-23 10:00:00"));
        this.mData.add(new DailyCheckTaskBean("任务3", "任务3内容", 2, "2019-12-24 10:00:00"));
        this.mData.add(new DailyCheckTaskBean("任务4", "任务4内容", 1, "2019-12-25 10:00:00"));
        this.mData.add(new DailyCheckTaskBean("任务5", "任务5内容", 2, "2019-12-26 10:00:00"));
        return this.mData;
    }
}
