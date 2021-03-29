package com.njxm.smart.model.component;

import android.content.res.Resources;

public class PersonListItem {

    private final int iconRes;

    private final String titleRes;

    private final String subTitle;

    private boolean showStar = false;

    private int paddingTop;

    public PersonListItem(int iconRes, String title, String subTitle, boolean showStar,
                          int paddintRes) {
        this.iconRes = iconRes;
        this.subTitle = subTitle;
        this.titleRes = title;
        this.showStar = showStar;
//        this.paddingTop = Resources.getSystem().getDimensionPixelSize(paddintRes);
    }


    public PersonListItem(int iconRes, int titleRes, int subTitle, boolean showStar) {
        this.iconRes = iconRes;
        this.titleRes = Resources.getSystem().getString(titleRes);
        this.subTitle = Resources.getSystem().getString(subTitle);
        this.showStar = showStar;
    }

    public int getIconRes() {
        return this.iconRes;
    }

    public String getTitleRes() {
        return this.titleRes;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public boolean isShowStar() {
        return this.showStar;
    }
}
