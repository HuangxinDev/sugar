/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import java.util.List;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ntxm.smart.R;

/**
 * Created by Hxin on 2021/4/21 Function:
 */
class MMAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public MMAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public MMAdapter(@Nullable List<T> data) {
        super(data);
    }

    public MMAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (item instanceof ContactsBean) {
            helper.setText(R.id.user_name, ((ContactsBean) item).getName());
            helper.setText(R.id.job, ((ContactsBean) item).getJob());
            helper.setVisible(R.id.select_state, ((ContactsBean) item).isSelected());
            helper.setVisible(R.id.job_state, ((ContactsBean) item).isSuperWorker());
        }
    }
}
