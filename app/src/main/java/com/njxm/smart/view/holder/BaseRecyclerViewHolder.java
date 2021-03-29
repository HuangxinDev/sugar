package com.njxm.smart.view.holder;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final SparseArrayCompat<View> mViews;

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mViews = new SparseArrayCompat<>();
    }

    public <V extends View> V getItemView(int viewResId) {
        View view = this.mViews.get(viewResId);
        if (view == null) {
            view = this.itemView.findViewById(viewResId);
            this.mViews.put(viewResId, view);
        }
        return (V) view;
    }
}
