package com.njxm.smart.activities.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.component.SuggestionDetailItem;
import com.ns.demo.R;

import java.util.List;

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
