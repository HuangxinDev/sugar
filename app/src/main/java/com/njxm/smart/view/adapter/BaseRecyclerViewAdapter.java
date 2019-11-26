package com.njxm.smart.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.njxm.smart.view.holder.BaseRecyclerViewHolder;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private View convertView;

    private List<T> mDatas;

    private Context mContext;

    public BaseRecyclerViewAdapter(Context paramContext) {
        mDatas = new ArrayList<>();
        this.mContext = paramContext;
    }

    public void setAdapterData(List<T> data) {
        if (data == null) {
            return;
        }
        this.mDatas = data;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder holder;
//        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(setConvertId(),
                    parent, false);

            holder = new BaseRecyclerViewHolder(convertView);
            convertView.setTag(R.id.holder, holder);
//            LogTool.printD("1 %s : %s \n %s", "Sugar", holder.itemView.getParent() == null,
//                    Log.getStackTraceString(new Throwable()));
//        } /*else {
//            holder = (BaseRecyclerViewHolder) convertView.getTag(R.id.holder);
//        }*/


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        convertView(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }


    public abstract int setConvertId();

    public abstract void convertView(BaseRecyclerViewHolder holder, int position);
}
