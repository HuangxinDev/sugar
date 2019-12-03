package com.njxm.smart.model.component;

import com.njxm.smart.utils.ResolutionUtil;

public abstract class BaseItem {
    public int paddingTop = ResolutionUtil.dp2Px(1);

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = ResolutionUtil.dp2Px(paddingTop);
    }
}
