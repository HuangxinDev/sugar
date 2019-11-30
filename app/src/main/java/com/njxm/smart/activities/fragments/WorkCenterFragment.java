package com.njxm.smart.activities.fragments;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.njxm.smart.activities.fragments.adapter.WorkCenterFragmentAdapter;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作中心Fragment
 */
public class WorkCenterFragment extends BaseFragment {


    private RecyclerView mRecyclerView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main_workcenter;
    }

    @Override
    protected void setUpView() {
        mRecyclerView = getContentView().findViewById(R.id.type_data);


        List<WorkCenterData> titles = new ArrayList<>();


        titles.add(new WorkCenterData(true, "内外勤管理", -1, ""));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤2"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤4"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤5"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤6"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤7"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤8"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤9"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤10"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "考勤11"));
        titles.add(new WorkCenterData(true, "安全", -1, ""));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全2"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全3"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全4"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "安全5"));
        titles.add(new WorkCenterData(true, "质量", -1, ""));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量1"));
        titles.add(new WorkCenterData(false, "内外勤管理", -1, "质量2"));


        RecyclerView.Adapter baseViewHolderAdapter = new WorkCenterFragmentAdapter<WorkCenterData>(titles);
//        mRecyclerView.addItemDecoration(null); 设置item之间的间距
//        mRecyclerView.addItemDecoration(new RecyclerViewDecoration());
        LinearLayoutManager layoutManager =  new GridLayoutManager(this.getContext(), 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(baseViewHolderAdapter);

    }

    private static class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = 24 * 3;
        }
    }

    @Override
    protected void setUpData() {

    }

    public static class WorkCenterData {
        private boolean isTilteOrNot = true;
        private String title;
        private int iconRes = 0;
        private String iconText;

        public WorkCenterData(boolean isTilteOrNot, String title, int iconRes, String iconText) {
            this.isTilteOrNot = isTilteOrNot;
            this.title = title;
            this.iconRes = iconRes;
            this.iconText = iconText;
        }

        public boolean isTilteOrNot() {
            return isTilteOrNot;
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
    }
}
