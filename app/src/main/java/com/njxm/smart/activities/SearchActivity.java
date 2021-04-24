/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ntxm.smart.R;

import butterknife.BindView;

/**
 * 查找联系人列表
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    private MMAdapter<ContactsBean> mAdapter;

    @VisibleForTesting
    private static List<ContactsBean> getContactsBeans() {
        List<ContactsBean> contactsBeans = new ArrayList<>();
        contactsBeans.add(new ContactsBean("联系人1", "", "普工", false));
        contactsBeans.add(new ContactsBean("联系人2", "", "普工", false));
        contactsBeans.add(new ContactsBean("联系人3", "", "特工", true));
        contactsBeans.add(new ContactsBean("联系人4", "", "普工", false));
        contactsBeans.add(new ContactsBean("联系人5", "", "特工", true));
        contactsBeans.add(new ContactsBean("联系人6", "", "普工", false));
        return contactsBeans;
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.layout_search_page;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAdapter = new MMAdapter<>(R.layout.layout_contacts_item, com.njxm.smart.activities.SearchActivity.getContactsBeans());
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        this.mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ContactsBean bean = ((ContactsBean) adapter.getItem(position));
            bean.setSelected(!bean.isSelected());
            adapter.notifyDataSetChanged();
        });
        this.mRecyclerView.setAdapter(this.mAdapter);
    }
}

