/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.model.component;

import com.njxm.smart.utils.ResolutionUtil;

public abstract class BaseItem {
    public int paddingTop = ResolutionUtil.dp2Px(1);

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = ResolutionUtil.dp2Px(paddingTop);
    }
}
