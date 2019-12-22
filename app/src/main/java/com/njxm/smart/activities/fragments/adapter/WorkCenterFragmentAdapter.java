package com.njxm.smart.activities.fragments.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.activities.fragments.WorkCenterFragment;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

public class WorkCenterFragmentAdapter extends BaseQuickAdapter<WorkCenterFragment.WorkCenterData, BaseViewHolder> {

    private static final int TYPE_TITLE = 1;
    private static final int TYPE_CONTENT = 2;

    private List<WorkCenterFragment.WorkCenterData> mData;

    public WorkCenterFragmentAdapter(List<WorkCenterFragment.WorkCenterData> mData) {
        super(mData);
        this.mData = mData == null ? new ArrayList<>() : mData;
    }


    @Nullable
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case TYPE_TITLE:
                view = inflater.inflate(R.layout.item_fragment_workcenter_list, parent, false);
                break;
            case TYPE_CONTENT:
                view = inflater.inflate(R.layout.item_workcenter, parent, false);
                break;
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
        return new BaseViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        WorkCenterFragment.WorkCenterData item = mData.get(position);
        if (item == null) {
            return super.getItemViewType(position);
        }

        if (item.isTilteOrNot()) {
            return TYPE_TITLE;
        } else
            return TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        if (manager == null) {
            super.onAttachedToRecyclerView(recyclerView);
            return;
        }
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mData.get(position).isTilteOrNot()) {
                    return 4;
                }
                return 1;
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkCenterFragment.WorkCenterData item) {
        if (getItemViewType(helper.getAdapterPosition()) == TYPE_TITLE) {
            helper.setText(R.id.title, item.getTitle());
        } else {
            helper.setText(R.id.item_text, item.getIconText());
        }
    }
}
