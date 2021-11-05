package com.njxm.smart.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class CombineAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
