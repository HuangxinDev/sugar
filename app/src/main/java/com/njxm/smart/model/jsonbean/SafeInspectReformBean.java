package com.njxm.smart.model.jsonbean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 巡检记录-整改记录Bean
 */
public class SafeInspectReformBean implements MultiItemEntity {
    @Override
    public int getItemType() {
        return ZSSimpleTitleBean.ITEM_SUB;
    }
}
