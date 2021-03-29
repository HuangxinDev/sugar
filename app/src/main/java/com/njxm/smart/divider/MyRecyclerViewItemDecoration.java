package com.njxm.smart.divider;

import com.njxm.smart.model.component.BaseItem;
import com.njxm.smart.utils.ResolutionUtil;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewItemDecoration extends RecyclerView.ItemDecoration {


    private static final int divider = 1;

    private List<? extends BaseItem> baseItems = new ArrayList<>();

    public void setDatas(List<? extends BaseItem> baseItems) {
        this.baseItems = baseItems;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int childAt = parent.getChildAdapterPosition(view);
        if (childAt < this.baseItems.size()) {
            outRect.top = this.baseItems.get(childAt).paddingTop;
        } else {
            outRect.top = ResolutionUtil.dp2Px(MyRecyclerViewItemDecoration.divider);
        }
    }
}
