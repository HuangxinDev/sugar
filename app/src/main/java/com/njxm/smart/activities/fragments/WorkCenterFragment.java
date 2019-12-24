package com.njxm.smart.activities.fragments;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.activities.DailyCheckActivity;
import com.njxm.smart.activities.DailyCheckDetailActivity;
import com.njxm.smart.activities.SearchActivity;
import com.njxm.smart.activities.SuggestionsActivity;
import com.njxm.smart.activities.adapter.TestAdapter;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作中心Fragment
 */
public class WorkCenterFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private TestAdapter mAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main_workcenter;
    }

    @Override
    protected void init() {
        super.init();
        mRecyclerView = getContentView().findViewById(R.id.recycler_view);
    }

    @Override
    protected void setUpView() {
        AppCompatTextView appCompatTextView = getContentView().findViewById(R.id.suggestion_box);
        appCompatTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SuggestionsActivity.class));
            }
        });

    }

    @Override
    protected void setUpData() {
        mAdapter = new TestAdapter(getDatas());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4,
                GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            WorkCenterData workCenterData = ((WorkCenterData) adapter.getItem(position));
            if (workCenterData.getItemType() == WorkCenterData.ITEM_TYPE_CONTENT) {
                switch (workCenterData.getIconText()) {
                    case "日常巡检":
                        startActivity(new Intent(getActivity(), DailyCheckActivity.class));
                        break;
                    case "日常巡检详情":
                        startActivity(new Intent(getActivity(), DailyCheckDetailActivity.class));
                        break;
                    case "查找联系人":
                        startActivity(new Intent(getActivity(), SearchActivity.class));
                        break;
                }
            }

        });
        mRecyclerView.setAdapter(mAdapter);
    }

    public static class WorkCenterData implements MultiItemEntity {

        public static final int ITEM_TYPE_TITLE = 100;
        public static final int ITEM_TYPE_CONTENT = 101;

        private int itemType;

        private String title;
        private int iconRes = 0;
        private String iconText;

        /**
         * 设置ITEM_TYPE_TITLE类型数据
         *
         * @param title 大标题名字
         */
        public WorkCenterData(String title) {
            this.itemType = ITEM_TYPE_TITLE;
            this.title = title;
        }

        /**
         * 设置子标题
         *
         * @param iconRes
         * @param iconText
         */
        public WorkCenterData(int iconRes, String iconText) {
            this.itemType = ITEM_TYPE_CONTENT;
            this.iconRes = iconRes;
            this.iconText = iconText;
        }

        public String getTitle() {
            return title;
        }

        public int getIconRes() {
            return iconRes;
        }

        public String getIconText() {
            return iconText;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }

    private List<WorkCenterData> getDatas() {
        List<WorkCenterData> titles = new ArrayList<>();
        titles.add(new WorkCenterData("内外勤管理"));
        titles.add(new WorkCenterData(-1, "考勤1"));
        titles.add(new WorkCenterData(-1, "考勤2"));
        titles.add(new WorkCenterData(-1, "考勤3"));
        titles.add(new WorkCenterData(-1, "考勤4"));
        titles.add(new WorkCenterData(-1, "考勤5"));
        titles.add(new WorkCenterData("安全"));
        titles.add(new WorkCenterData(-1, "日常巡检"));
        titles.add(new WorkCenterData(-1, "日常巡检详情"));
        titles.add(new WorkCenterData(-1, "查找联系人"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全3"));
        titles.add(new WorkCenterData(-1, "安全4"));
        titles.add(new WorkCenterData(-1, "安全5"));
        titles.add(new WorkCenterData("质量"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量1"));
        titles.add(new WorkCenterData(-1, "质量2"));
        return titles;
    }
}
