/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities.adapter;

import java.util.List;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.component.SuggestionDetailItem;
import com.ntxm.smart.R;

public class SuggestionDetailAdapter extends BaseQuickAdapter<SuggestionDetailItem, BaseViewHolder> {

    public SuggestionDetailAdapter(int layoutResId, @Nullable List<SuggestionDetailItem> data) {
        super(layoutResId, data);
    }

    public SuggestionDetailAdapter(@Nullable List<SuggestionDetailItem> data) {
        super(data);
    }

    public SuggestionDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SuggestionDetailItem item) {
        helper.setText(R.id.suggestion_user_name, item.getName());
        helper.setText(R.id.suggestion_user_work, item.getWork());
        helper.setText(R.id.suggestion_message, item.getMessage());
        helper.setText(R.id.suggestion_time, item.getTime());
    }
}
