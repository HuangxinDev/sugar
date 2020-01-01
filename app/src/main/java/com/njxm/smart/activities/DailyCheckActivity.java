package com.njxm.smart.activities;


import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.activities.adapter.DailyCheckAdapter;
import com.njxm.smart.model.jsonbean.DailyCheckTaskBean;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 日常巡检-页面（默认无数据、查看数据、高管有权限创建任务）
 *
 * 页面逻辑-请求巡检记录数据、
 * 无: 显示默认
 * 有: 展示数据，（高管并显示右侧状态栏按钮，可以创建巡检项目）
 */
@Route(path = "/app/safety/inspect")
public class DailyCheckActivity extends BaseActivity {

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
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        setActionBarTitle("日常巡检");
        showRightBtn(true, R.mipmap.new_add);


        mDailyCheckAdapter = new DailyCheckAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDailyCheckAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mData = getData();
                SystemClock.sleep(2000);
                updateView();
            }
        }).start();

        updateView();
    }

    private void updateView() {
        invoke(new Runnable() {
            @Override
            public void run() {
                boolean hasData = mData.size() > 0;
                rlVoidData.setVisibility(hasData ? View.GONE : View.VISIBLE);
                mRecyclerView.setVisibility(hasData ? View.VISIBLE : View.GONE);
                if (hasData) {
                    mDailyCheckAdapter.setNewData(mData);
                }
            }
        });
    }


    private List<DailyCheckTaskBean> getData() {
        mData.add(new DailyCheckTaskBean("任务1", "任务1ccsac " +
                "jahjjjjjjskjdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                "内容", 0, "2019-12-22 10:00:00"));
        mData.add(new DailyCheckTaskBean("任务2", "任务2内容", 1, "2019-12-23 10:00:00"));
        mData.add(new DailyCheckTaskBean("任务3", "任务3内容", 2, "2019-12-24 10:00:00"));
        mData.add(new DailyCheckTaskBean("任务4", "任务4内容", 1, "2019-12-25 10:00:00"));
        mData.add(new DailyCheckTaskBean("任务5", "任务5内容", 2, "2019-12-26 10:00:00"));
        return mData;
    }
}
