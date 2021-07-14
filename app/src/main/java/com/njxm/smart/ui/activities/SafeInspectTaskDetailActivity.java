/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.jsonbean.SafeInspectRecordBean;
import com.njxm.smart.view.ItemView;
import com.ntxm.smart.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 巡检任务详情
 */
public class SafeInspectTaskDetailActivity extends BaseActivity {

    @BindView(R.id.confirm)
    protected View mCommitBtn;


    @BindView(R.id.tab1)
    protected RelativeLayout rlTaskDetailTab;

    @BindView(R.id.tab1_content)
    protected View mTab1Content;

    @BindView(R.id.tab2)
    protected RelativeLayout rlInspectRecordTab;


    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.inspect_contact)
    protected ItemView mInspectContact;

    public static List<SafeInspectRecordBean> loadData() {
        List<SafeInspectRecordBean> data = new ArrayList<>();
        data.add(new SafeInspectRecordBean(true, "2019-11-22 15:30"));
        data.add(new SafeInspectRecordBean(false, "2019-11-22 15:30"));
        data.add(new SafeInspectRecordBean(true, "2019-11-22 15:30"));
        data.add(new SafeInspectRecordBean(false, "2019-11-22 15:30"));
        return data;
    }

    private static void enableChildView(View view, boolean enable) {
        if (view == null) {
            return;
        }

        view.setClickable(!enable);

        if (!(view instanceof ViewGroup)) {
            view.setEnabled(enable);
            return;
        }

        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = viewGroup.getChildAt(i);
            child.setEnabled(enable);
        }
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_inspect_task_detail_new_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("任务详情");
        this.mCommitBtn.setVisibility(View.GONE);
        this.initPage(this.rlTaskDetailTab);
        SimpleAdapter adapter = new SimpleAdapter(R.layout.safe_inspect_recycler_item_record, com.njxm.smart.ui.activities.SafeInspectTaskDetailActivity.loadData());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SafeInspectTaskDetailActivity.this, SafeInspectRecordActivity.class);
                intent.putExtra("has_problem", ((SafeInspectRecordBean) adapter.getItem(position)).isExistProblem());
                com.njxm.smart.ui.activities.SafeInspectTaskDetailActivity.this.startActivity(intent);
            }
        });
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider_gray)));
        this.mRecyclerView.addItemDecoration(itemDecoration);
        this.mRecyclerView.setAdapter(adapter);
    }

    private void initPage(RelativeLayout view) {
        com.njxm.smart.ui.activities.SafeInspectTaskDetailActivity.enableChildView(this.rlTaskDetailTab, view == this.rlTaskDetailTab);
        com.njxm.smart.ui.activities.SafeInspectTaskDetailActivity.enableChildView(this.rlInspectRecordTab, view == this.rlInspectRecordTab);
        this.mTab1Content.setVisibility(view == this.rlTaskDetailTab ? View.VISIBLE : View.GONE);
        this.mRecyclerView.setVisibility(view == this.rlInspectRecordTab ? View.VISIBLE : View.GONE);

        if (view == this.rlTaskDetailTab) {
            this.mInspectContact.showAdd(false);
        }
    }

    @OnClick({R.id.tab1, R.id.tab2})
    protected void switchTab(RelativeLayout view) {
        this.initPage(view);
    }

    private static class SimpleAdapter extends BaseQuickAdapter<SafeInspectRecordBean, BaseViewHolder> {
        public SimpleAdapter(int layoutResId, @Nullable List<SafeInspectRecordBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SafeInspectRecordBean item) {
            helper.setText(R.id.state_flag, item.isExistProblem() ? R.string.has_problem : R.string.no_problem);
            helper.getView(R.id.state_flag).setEnabled(item.isExistProblem());
            helper.setText(R.id.item_time, item.getInspectTime());
        }
    }
}
