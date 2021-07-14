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
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.model.jsonbean.SafeInspectReformBean;
import com.njxm.smart.model.jsonbean.ZSSimpleTitleBean;
import com.ntxm.smart.R;

import butterknife.BindView;

public class SafeInspectRecordActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    private static List<MultiItemEntity> loadData(int count) {
        List<MultiItemEntity> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ZSSimpleTitleBean<SafeInspectReformBean> bean = new ZSSimpleTitleBean<SafeInspectReformBean>("问题 " + (i + 1) + "  整改完成");
            bean.addSubItem(new SafeInspectReformBean());
            data.add(bean);
            data.add(bean);
        }
        return data;
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_inspect_record_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("巡检记录");

        View headerView = LayoutInflater.from(this).inflate(R.layout.safe_inspect_record_recycler_header_item, null);
        boolean hasProblem = this.getIntent().getBooleanExtra("has_problem", false);

        View footView = LayoutInflater.from(this).inflate(hasProblem ?
                R.layout.safe_inspect_record_recycler_footer_problem_item :
                R.layout.safe_inspect_record_recycler_footer_item, null);
        SafeInspectRecordAdapter adapter = new SafeInspectRecordAdapter(hasProblem ? com.njxm.smart.ui.activities.SafeInspectRecordActivity.loadData(5) : null);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.look_detail) {
                    SafeInspectRecordActivity.this.startActivity(new Intent(SafeInspectRecordActivity.this,
                            SafeInspectReformActivity.class));
                }
            }
        });


        adapter.addHeaderView(headerView);
        adapter.addFooterView(footView);
        this.mRecyclerView.setAdapter(adapter);

    }

    private static final class SafeInspectRecordAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public SafeInspectRecordAdapter(List<MultiItemEntity> data) {
            super(data);
            this.addItemType(ZSSimpleTitleBean.ITEM_TITLE, R.layout.item_fragment_workcenter_list);
            this.addItemType(ZSSimpleTitleBean.ITEM_SUB, R.layout.safe_inspect_record_reform_recycler_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultiItemEntity item) {
            switch (helper.getItemViewType()) {
                case ZSSimpleTitleBean.ITEM_TITLE:
                    ZSSimpleTitleBean bean = (ZSSimpleTitleBean) item;
                    helper.setText(R.id.title, bean.getTitle());
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (bean.isExpanded()) {
                                com.njxm.smart.ui.activities.SafeInspectRecordActivity.SafeInspectRecordAdapter.this.collapse(helper.getAdapterPosition());
                            } else {
                                com.njxm.smart.ui.activities.SafeInspectRecordActivity.SafeInspectRecordAdapter.this.expand(helper.getAdapterPosition());
                            }

                        }
                    });
                    break;
                case ZSSimpleTitleBean.ITEM_SUB:
                    helper.setNestView(R.id.look_detail);
                    break;
            }
        }
    }

}
