// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.dto.info.AppStorePromoteApplication;
import com.appchina.ios.core.dto.info.AppStorePromoteTab;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;


/**
 * @author luofei@refanqie.com (Your Name Here)
 */
public class StorePromoteAppsParameter extends StatusStartSizeParameter {
    private Integer tabId;
    private String st;
    private String et;
    private Integer rank;
    private Integer rootId;
    private Integer id;
    private String ids;
    public static final Map<String, String> STATUS = AppStorePromoteApplication.STATUS;

    public Integer getTabId() {
        return tabId;
    }

    public void setTabId(Integer tabId) {
        this.tabId = tabId;
    }

    public void transfer() {
        if(tabId == null){
            tabId = AppStorePromoteTab.DEFAULT_TABID;
        }
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
        if (StringUtils.isEmpty(st)) {
            st = DateUtils.getNow();
        }
        if (StringUtils.isEmpty(et)) {
            et = DateUtils.getDeltaNow(30);
        }
        if (super.status == null) {
            super.status = DbStatus.STATUS_OK;
        }
    }

    public void transfer(int pageSize) {
        pager.setSize(pageSize);
        transfer();
    }

    public StorePromoteAppsParameter() {
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getRootId() {
        return rootId;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "StorePromoteAppsParameter{" +
                "tabId=" + tabId +
                ", st='" + st + '\'' +
                ", et='" + et + '\'' +
                ", rank=" + rank +
                ", rootId=" + rootId +
                ", id=" + id +
                ", ids='" + ids + '\'' +
                '}';
    }
}
