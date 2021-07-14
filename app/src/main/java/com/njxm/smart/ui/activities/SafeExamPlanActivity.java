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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.tools.network.HttpUtils;
import com.ntxm.smart.R;

import butterknife.BindView;

@Route(path = "/app/safety/examination")
public class SafeExamPlanActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    public static List<String> getData() {
        List<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("aaa");
        strings.add("安全教育特种工人教育安全教育特种工人教育安全教育特种工人教育安全教育特种工人教育安全教育特种工人教育安全教育特种工人教育安全教育特种工人教育安全教育特种工人教育");
        strings.add("安全教");
        return strings;
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_exam_plan_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("考试计划");
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SafeExamPlanAdapter adapter = new SafeExamPlanAdapter(R.layout.safe_exam_list_item, com.njxm.smart.ui.activities.SafeExamPlanActivity.getData());
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.start_exam:
                        com.njxm.smart.ui.activities.SafeExamPlanActivity.this.startActivity(new Intent(SafeExamPlanActivity.this, SafeExamActivity.class));
                        break;
                    case R.id.exam_study:
                        com.njxm.smart.ui.activities.SafeExamPlanActivity.this.startActivity(new Intent(SafeExamPlanActivity.this, SafeExamStudyActivity.class));
                        break;
                }
            }
        });
        this.mRecyclerView.setAdapter(adapter);

        HttpUtils.getInstance().request(new RequestEvent.Builder().url("http://119.3.136" +
                ".127:7776/api/safety/educationPlan/findPageByOrg").addBodyJson("sepoOrgId",
                "b83f455303a5fce45a402d2ab8bf2c4c").addBodyJson("permissionId",
                "a8da5dd8b935eb444dc25d87a34e2287").build());
    }

    private static final class SafeExamPlanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public SafeExamPlanAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        public SafeExamPlanAdapter(@Nullable List<String> data) {
            super(data);
        }

        public SafeExamPlanAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setNestView(R.id.start_exam);
            helper.setNestView(R.id.exam_study);
            helper.setText(R.id.exam_content, item);
            helper.setGone(R.id.divider1, helper.getAdapterPosition() != this.mData.size() - 1);
        }
    }
}
