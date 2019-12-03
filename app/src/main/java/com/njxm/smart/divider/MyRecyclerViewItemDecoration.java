package com.njxm.smart.divider;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.njxm.smart.model.component.BaseItem;
import com.njxm.smart.utils.ResolutionUtil;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    private int left = 0;
    private int top = 0;
    private int right = 0;
    private int bottom = 0;

    private int[] positions;

    private static final int divider = 1;

    private int speacialDividerHeight = divider;

    public void setPositions(int dividerHeight, int... positions) {
        speacialDividerHeight = dividerHeight;
        this.positions = positions;
    }

    public MyRecyclerViewItemDecoration() {

    }


    public MyRecyclerViewItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    private List<? extends BaseItem> baseItems = new ArrayList<BaseItem>();

    public void setDatas(List<? extends BaseItem> baseItems) {
        this.baseItems = baseItems;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int childAt = parent.getChildAdapterPosition(view);
        if (childAt < baseItems.size()) {
            outRect.top = baseItems.get(childAt).paddingTop;
        } else {
            outRect.top = ResolutionUtil.dp2Px(divider);
        }
    }
}
