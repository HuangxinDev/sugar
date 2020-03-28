package com.njxm.smart.model.jsonbean;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.activities.adapter.WorkCenterItemAdapter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.utils.StringUtils;

public class WorkCenterSubBean implements MultiItemEntity {

    @JSONField(name = "srStatus")
    private int status;

    private String name;

    private String icon;

    private String id;

    private String sr_sort;

    private String sr_parent_id;

    private int srLinkType;

    private String url;

    private int sr_level;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        if (TextUtils.isEmpty(icon)) {
            this.icon = UrlPath.PATH_PICTURE_PREFIX.getUrl() + icon;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSr_sort() {
        return sr_sort;
    }

    public void setSr_sort(String sr_sort) {
        this.sr_sort = sr_sort;
    }

    public String getSr_parent_id() {
        return sr_parent_id;
    }

    public void setSr_parent_id(String sr_parent_id) {
        this.sr_parent_id = sr_parent_id;
    }

    public int getSrLinkType() {
        return srLinkType;
    }

    public void setSrLinkType(int srLinkType) {
        this.srLinkType = srLinkType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (StringUtils.isNotEmpty(url) && !url.startsWith("http")) {
            // 处理路由配置
            this.url = "/" + url.replaceAll(":", "/");
        } else {
            this.url = url;
        }
    }

    public int getSr_level() {
        return sr_level;
    }

    public void setSr_level(int sr_level) {
        this.sr_level = sr_level;
    }

    @Override
    public int getItemType() {
        return WorkCenterItemAdapter.ITEM_CONTENT_TYPE;
    }
}
