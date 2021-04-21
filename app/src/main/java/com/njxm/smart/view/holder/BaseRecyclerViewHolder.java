/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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
