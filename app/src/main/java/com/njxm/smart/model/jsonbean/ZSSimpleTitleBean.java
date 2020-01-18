package com.njxm.smart.model.jsonbean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ZSSimpleTitleBean<T extends MultiItemEntity> extends AbstractExpandableItem<T> implements MultiItemEntity {
    public static final int ITEM_TITLE = 265;
    public static final int ITEM_SUB = 294;

    @Override
    public int getItemType() {
        return ITEM_TITLE;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    private String title;

    public ZSSimpleTitleBean(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
