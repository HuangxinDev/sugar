package com.njxm.smart.view.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArrayCompat<View> mViews;

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArrayCompat<>();
    }

    public <V extends View> V getItemView(int viewResId) {
        View view = mViews.get(viewResId);
        if (view == null) {
            view = itemView.findViewById(viewResId);
            mViews.put(viewResId, view);
        }
        return (V) view;
    }
}
