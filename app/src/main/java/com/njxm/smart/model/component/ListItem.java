/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.model.component;

import java.util.List;

import com.ntxm.smart.R;

public class ListItem extends BaseItem {

    private final String title;
    public List<ListItem> subData;
    public boolean isOpen = false;
    private String subTitleText;
    private int subTitleRes = R.mipmap.arrow_detail;
    private int subTitleResPaddingEnd = 0;

    public ListItem(String title) {
        this.title = title;
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

    public void addSubListItem(List<ListItem> datas) {
        this.subData = datas;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubTitleText() {
        return this.subTitleText;
    }

    public int getSubTitleRes() {
        return this.subTitleRes;
    }

    public void setSubTitleRes(int resId) {
        this.subTitleRes = resId;
    }

    public int getSubTitleResPaddingEnd() {
        return this.subTitleResPaddingEnd;
    }
}
