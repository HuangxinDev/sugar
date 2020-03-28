package com.njxm.smart.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.activities.adapter.WorkCenterItemAdapter;

import java.util.List;

/**
 * Created by Hxin on 2020/3/28
 * Function: 权限列表
 */
public class PermissionBean extends AbstractExpandableItem<PermissionBean> implements MultiItemEntity {

    /**
     * srStatus : 0
     * children : [{"srStatus":0,"name":"考勤","icon":"group1/M00/00/08/wKgDd14JaYOAInTwAAOUD4nL5Ac962.jpg","extraIcon":"","id":"c8694be76648f7a461d4a7667a5d3da4","sr_sort":1,"sr_parent_id":"81b80db13061db9fd0ec60d72070d578","srLinkType":1,"url":"http://119.3.136.127:7775/#/attendance/statistics","sr_level":2},{"srStatus":0,"name":"请假","icon":"group1/M00/00/08/wKgDd14Jaj-AGf9EABXnGZmFOvc763.png","extraIcon":"","id":"19e86e05c36d77364eda5e317cb6360f","sr_sort":2,"sr_parent_id":"81b80db13061db9fd0ec60d72070d578","srLinkType":1,"url":"http://119.3.136.127:7775/#/attendance/leave","sr_level":2},{"srStatus":0,"name":"调休","icon":"group1/M00/00/08/wKgDd14Jat-AB1UTAACMZXFXcNs727.jpg","extraIcon":"","id":"5dad00c0351c7052915010af9027e3b8","sr_sort":3,"sr_parent_id":"81b80db13061db9fd0ec60d72070d578","srLinkType":1,"url":"http://119.3.136.127:7775/#/attendance/break","sr_level":2},{"srStatus":0,"name":"出差","icon":"group1/M00/00/08/wKgDd14JbCiAHzjnABXnGZmFOvc122.png","extraIcon":"","id":"a0df025c8c8b6b2a35010b5067935eb2","sr_sort":4,"sr_parent_id":"81b80db13061db9fd0ec60d72070d578","srLinkType":1,"url":"http://119.3.136.127:7775/#/attendance/travel","sr_level":2},{"srStatus":0,"name":"通讯录","icon":"group1/M00/00/08/wKgDd14JbOCAceWuAACMZXFXcNs406.jpg","extraIcon":"","id":"f4af95295a95b134e71a26d4acc96e99","sr_sort":5,"sr_parent_id":"81b80db13061db9fd0ec60d72070d578","srLinkType":0,"url":"app:labor:addressList","sr_level":2}]
     * name : 内外勤管理
     * icon :
     * extraIcon :
     * id : 81b80db13061db9fd0ec60d72070d578
     * sr_sort : 1
     * sr_parent_id :
     * srLinkType : 0
     * url : app:labor
     * sr_level : 1
     */

    private int srStatus;
    private String name;
    private String icon;
    private String extraIcon;
    private String id;
    private int sr_sort;
    private String sr_parent_id;
    private int srLinkType;
    private String url;
    private int sr_level;
    private List<PermissionBean> children;

    public int getSrStatus() {
        return srStatus;
    }

    public void setSrStatus(int srStatus) {
        this.srStatus = srStatus;
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
    }

    public String getExtraIcon() {
        return extraIcon;
    }

    public void setExtraIcon(String extraIcon) {
        this.extraIcon = extraIcon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSr_sort() {
        return sr_sort;
    }

    public void setSr_sort(int sr_sort) {
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
//        this.url = "/" + url.replace(":", "/");
        this.url = url;
    }

    public int getSr_level() {
        return sr_level;
    }

    public void setSr_level(int sr_level) {
        this.sr_level = sr_level;
    }

    public List<PermissionBean> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionBean> children) {
        this.children = children;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        if (sr_level == 1) {
            return WorkCenterItemAdapter.ITEM_TITLE_TYPE;
        } else if (sr_level == 2) {
            return WorkCenterItemAdapter.ITEM_CONTENT_TYPE;
        }
        throw new RuntimeException("工作重心列表非法层级");
    }
}
