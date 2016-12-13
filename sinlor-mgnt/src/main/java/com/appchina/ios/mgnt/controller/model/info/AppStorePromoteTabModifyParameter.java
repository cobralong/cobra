package com.appchina.ios.mgnt.controller.model.info;

/**
 * Created by zhouyanhui on 8/5/15.
 */
public class AppStorePromoteTabModifyParameter {
    private int tabId;
    private Integer status;
    private Integer rank;
    private String color;
    private String icon;
    private String name;

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getRank() {
        return rank;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "AppStorePromoteTabModifyParameter [tabId=" + tabId + ", status=" + status + ", rank=" + rank
                + ", color=" + color + ", icon=" + icon + ", name=" + name + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
