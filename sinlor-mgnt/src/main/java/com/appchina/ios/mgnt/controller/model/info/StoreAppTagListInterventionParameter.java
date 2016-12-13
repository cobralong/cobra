package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.dto.info.AppStoreAppTagAppleTagMap;
import com.appchina.ios.core.dto.info.AppTagListIntervention;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * Created by zhouyanhui on 8/18/15.
 */
public class StoreAppTagListInterventionParameter extends StatusStartSizeParameter {
    private Integer id;
    private Integer appTagId;
    private Integer rootId;
    private Integer rank;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppTagId() {
        return appTagId;
    }

    public void setAppTagId(Integer appTagId) {
        this.appTagId = appTagId;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public void transfer(int size) {
        super.transfer(size);
        if (status == null || status.intValue() == DbStatus.STATUS_ALL) {
            status = AppTagListIntervention.STATUS_OK;
        }
    }

    @Override
    public void transfer() {
        super.transfer();
        if (status == null || status.intValue() == DbStatus.STATUS_ALL) {
            status = AppStoreAppTagAppleTagMap.STATUS_OK;
        }
    }

    public void transfer(Integer appTagId) {
        this.transfer();
        if (appTagId != null) {
            this.appTagId = appTagId;
        }
    }

    @Override
    public String toString() {
        return "StoreAppTagListInterventionParameter{" + "id=" + id + ", appTagId=" + appTagId + ", rootId=" + rootId
                + ", rank=" + rank + '}';
    }
}
