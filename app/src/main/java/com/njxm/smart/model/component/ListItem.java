package com.njxm.smart.model.component;

import com.ntxm.smart.R;

import java.util.List;

public class ListItem extends BaseItem {

    private String title;

    private String subTitleText;

    private int subTitleRes = R.mipmap.arrow_detail;

    private int subTitleResPaddingEnd = 0;

    public List<ListItem> subData;

    public boolean isOpen = false;

    public ListItem(String title) {
        this.title = title;
    }

    public void setSubTitleRes(int resId) {
        this.subTitleRes = resId;
    }

    public void addSubListItem(List<ListItem> datas) {
        subData = datas;
    }

    public ListItem(String title, String subTitleText) {
        this.title = title;
        this.subTitleText = subTitleText;
    }

    public ListItem(String title, String subTitleText, int subTitleRes) {
        this.title = title;
        this.subTitleText = subTitleText;
        this.subTitleRes = subTitleRes;
    }

    public ListItem(String title, String subTitleText, int subTitleRes, int subTitleResPaddingEnd) {
        this.title = title;
        this.subTitleText = subTitleText;
        this.subTitleRes = subTitleRes;
        this.subTitleResPaddingEnd = subTitleResPaddingEnd;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitleText() {
        return subTitleText;
    }

    public int getSubTitleRes() {
        return subTitleRes;
    }

    public int getSubTitleResPaddingEnd() {
        return subTitleResPaddingEnd;
    }
}
