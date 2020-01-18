package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.jsonbean.SafeInspectRecordBean;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 巡检任务详情
 */
public class SafeInspectTaskDetailActivity extends BaseActivity {

    @BindView(R.id.confirm)
    protected View mCommitBtn;


    @BindView(R.id.tab1)
    protected RelativeLayout rlTaskDetailTab;

    @BindView(R.id.tab1_content)
    protected View mTab1Content;

    @BindView(R.id.tab2)
    protected RelativeLayout rlInspectRecordTab;


    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_inspect_task_detail_new_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("任务详情");
        mCommitBtn.setVisibility(View.GONE);
        initPage(rlTaskDetailTab);
        SimpleAdapter adapter = new SimpleAdapter(R.layout.safe_inspect_recycler_item_record, loadData());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SafeInspectTaskDetailActivity.this, SafeInspectRecordActivity.class);
                intent.putExtra("has_problem", ((SafeInspectRecordBean) adapter.getItem(position)).isExistProblem());
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider_gray)));
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(adapter);
    }

    private void initPage(RelativeLayout view) {
        enableChildView(rlTaskDetailTab, view == rlTaskDetailTab);
        enableChildView(rlInspectRecordTab, view == rlInspectRecordTab);
        mTab1Content.setVisibility(view == rlTaskDetailTab ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(view == rlInspectRecordTab ? View.VISIBLE : View.GONE);
    }

    public List<SafeInspectRecordBean> loadData() {
        List<SafeInspectRecordBean> data = new ArrayList<>();
        data.add(new SafeInspectRecordBean(true, "2019-11-22 15:30"));
        data.add(new SafeInspectRecordBean(false, "2019-11-22 15:30"));
        data.add(new SafeInspectRecordBean(true, "2019-11-22 15:30"));
        data.add(new SafeInspectRecordBean(false, "2019-11-22 15:30"));
        return data;
    }

    private void enableChildView(View view, boolean enable) {
        if (view == null) {
            return;
        }

        view.setClickable(!enable);

        if (!(view instanceof ViewGroup)) {
            view.setEnabled(enable);
            return;
        }

        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = viewGroup.getChildAt(i);
            child.setEnabled(enable);
        }
    }

    @OnClick({R.id.tab1, R.id.tab2})
    protected void switchTab(RelativeLayout view) {
        initPage(view);
    }

    private static class SimpleAdapter extends BaseQuickAdapter<SafeInspectRecordBean, BaseViewHolder> {
        public SimpleAdapter(int layoutResId, @Nullable List<SafeInspectRecordBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SafeInspectRecordBean item) {
            helper.setText(R.id.state_flag, item.isExistProblem() ? R.string.has_problem : R.string.no_problem);
            helper.getView(R.id.state_flag).setEnabled(item.isExistProblem());
            helper.setText(R.id.item_time, item.getInspectTime());
        }
    }
}
