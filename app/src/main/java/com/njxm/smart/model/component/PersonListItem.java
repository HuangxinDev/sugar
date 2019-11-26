package com.njxm.smart.model.component;

import android.content.res.Resources;

public class PersonListItem {

    private int iconRes;

    private String titleRes;

    private String subTitle;

    private boolean showStar = false;

    public PersonListItem(int iconRes, String title, String subTitle, boolean showStar) {
        this.iconRes = iconRes;
        this.subTitle = subTitle;
        this.titleRes = title;
    }


    public PersonListItem(int iconRes, int titleRes, int subTitle, boolean showStar) {
        this.iconRes = iconRes;
        this.titleRes = Resources.getSystem().getString(titleRes);
        this.subTitle = Resources.getSystem().getString(subTitle);
        this.showStar = showStar;
    }

    public int getIconRes() {
        return iconRes;
    }

    public String getTitleRes() {
        return titleRes;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public boolean isShowStar() {
        return showStar;
    }
}
