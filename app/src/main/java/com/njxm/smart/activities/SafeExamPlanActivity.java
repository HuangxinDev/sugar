package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = "/app/safety/examination")
public class SafeExamPlanActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;


    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_exam_plan_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SafeExamPlanAdapter adapter = new SafeExamPlanAdapter(R.layout.safe_exam_list_item, getData());
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(SafeExamPlanActivity.this, SafeExamActivity.class));
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    public List<String> getData() {
        List<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("aaa");
        strings.add("安全教育特种工人教育安全教育特种工人 教育安全教育特种工人教育安全教育特种 工人教育安全教育特种工人教育安全教育特种工人 教育安全教育特种工人教育安全教育特种 工人教育");
        strings.add("安全教");
        return strings;
    }

    private static final class SafeExamPlanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public SafeExamPlanAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        public SafeExamPlanAdapter(@Nullable List<String> data) {
            super(data);
        }

        public SafeExamPlanAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setNestView(R.id.start_exam);
            helper.setGone(R.id.divider1, helper.getAdapterPosition() != mData.size() - 1);
            TextView textView = helper.getView(R.id.exam_content);
            textView.setText(item);
            textView.setGravity(textView.getLineCount() > 1 ? Gravity.LEFT : Gravity.RIGHT);
        }
    }
}
