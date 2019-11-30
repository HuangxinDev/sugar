package com.njxm.smart.activities.fragments.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.activities.fragments.WorkCenterFragment;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

public class WorkCenterFragmentAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TITLE = 1;
    private static final int TYPE_CONTENT = 2;

    private List<T> mData;

    public WorkCenterFragmentAdapter(List<T> mData) {
        this.mData = mData == null ? new ArrayList<T>() : mData;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
                return null;
        }
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_TITLE) {
            ((TextView) holder.itemView.findViewById(R.id.title)).setText(((WorkCenterFragment.WorkCenterData) mData.get(position)).getTitle());
        } else {
            ((TextView) holder.itemView.findViewById(R.id.item_text)).setText(((WorkCenterFragment.WorkCenterData) mData.get(position)).getIconText());
        }
    }

    @Override
    public int getItemViewType(int position) {
        WorkCenterFragment.WorkCenterData item = (WorkCenterFragment.WorkCenterData) mData.get(position);
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
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (((WorkCenterFragment.WorkCenterData) mData.get(position)).isTilteOrNot()) {
                    return 4;
                }
                return 1;
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }
}
