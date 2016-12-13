package com.appchina.ios.mgnt.controller.model.info;

/**
 * Created by zhouyanhui on 8/6/15.
 */
public class StorePromoteAppsAddParameter {
    private Integer rank;
    private String st;
    private String et;
    private Integer rootId;
    private Integer tabId;

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getRank() {
        return rank;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getSt() {
        return st;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public String getEt() {
        return et;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getRootId() {
        return rootId;
    }

	@Override
	public String toString() {
		return "StorePromoteAppsAddParameter [rank=" + rank + ", st=" + st
				+ ", et=" + et + ", rootId=" + rootId + ", tabId=" + tabId
				+ "]";
	}

	public Integer getTabId() {
		return tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}
}
