package com.njxm.smart.activities.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.activities.fragments.WorkCenterFragment;
import com.ns.demo.R;

import java.util.List;

public class TestAdapter extends BaseMultiItemQuickAdapter<WorkCenterFragment.WorkCenterData,
        BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TestAdapter(List<WorkCenterFragment.WorkCenterData> data) {
        super(data);
        addItemType(100, R.layout.item_fragment_workcenter_list);
        addItemType(101, R.layout.item_workcenter);
    }

    public TestAdapter(Context context, List<WorkCenterFragment.WorkCenterData> data) {
        super(data);
    }

    private boolean zhankai = true;

    @Override
    protected void convert(BaseViewHolder helper, WorkCenterFragment.WorkCenterData item) {
        switch (getItemViewType(helper.getAdapterPosition())) {
            case 100:
                helper.setText(R.id.title, item.getTitle());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (zhankai) {
                            collapse(helper.getAdapterPosition());
                        } else {
                            expand(helper.getAdapterPosition());
                        }
                        zhankai = !zhankai;
                    }
                });
                break;
            case 101:
                helper.setText(R.id.item_text, item.getIconText());
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == WorkCenterFragment.WorkCenterData.ITEM_TYPE_TITLE) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }
}
