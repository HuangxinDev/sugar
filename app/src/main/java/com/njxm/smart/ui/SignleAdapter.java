package com.njxm.smart.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.njxm.smart.view.holder.BaseRecyclerViewHolder;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class SignleAdapter extends CombineAdapter<BaseRecyclerViewHolder> {
    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
