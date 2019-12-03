package com.njxm.smart.activities.adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.component.ListItem;
import com.ns.demo.R;

import java.util.List;

public class StringRecyclerAdapter extends BaseQuickAdapter<ListItem, BaseViewHolder> {


    public StringRecyclerAdapter(int layoutResId, @Nullable List<ListItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListItem item) {
        ((AppCompatTextView) helper.getView(R.id.item_title)).setText(item.getTitle());
        AppCompatTextView subTitle = helper.getView(R.id.item_sub_title);

        subTitle.setText(item.getSubTitleText());

        subTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, item.getSubTitleRes(), 0);
//        subTitle.setCompoundDrawables(null, null, item.getSubTitleRes(), null);

        if (item.getSubTitleResPaddingEnd() != 0) {
            subTitle.setPadding(0, 0, item.getSubTitleResPaddingEnd(), 0);
        }
    }
}
