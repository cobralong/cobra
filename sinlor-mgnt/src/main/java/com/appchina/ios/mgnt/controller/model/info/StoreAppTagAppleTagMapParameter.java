package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.dto.info.AppStoreAppTagAppleTagMap;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * Created by zhouyanhui on 8/18/15.
 */
public class StoreAppTagAppleTagMapParameter extends StatusStartSizeParameter {
    private Integer appleCateId;
    private Integer appTagId;

    public Integer getAppleCateId() {
        return appleCateId;
    }

    public void setAppleCateId(Integer appleCateId) {
        this.appleCateId = appleCateId;
    }

    public Integer getAppTagId() {
        return appTagId;
    }

    public void setAppTagId(Integer appTagId) {
        this.appTagId = appTagId;
    }

    @Override
    public String toString() {
        return "StoreAppTagAppleTagMapParameter{" + "appleCateId=" + appleCateId + ", appTagId=" + appTagId + '}';
    }

    @Override
    public void transfer(int size) {
        super.transfer(size);
        if (status == null || status.intValue() == DbStatus.STATUS_ALL) {
            status = AppStoreAppTagAppleTagMap.STATUS_OK;
        }
    }

    @Override
    public void transfer() {
        super.transfer();
        if (status == null || status.intValue() == DbStatus.STATUS_ALL) {
            status = AppStoreAppTagAppleTagMap.STATUS_OK;
        }
    }
}
