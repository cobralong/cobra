// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.Map;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.web.model.Pager;
import com.appchina.ios.web.model.StartSizeParameter;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class StatusStartSizeParameter extends StartSizeParameter {
    protected Integer status;

    public static final Map<String, String> ALL_STATUS = DbStatus.STATUS;

    public StatusStartSizeParameter() {
        super();
        status = null;
    }

    @Override
    public void transfer(int size) {
        super.transfer(size);
        if (status == null || status.intValue() == DbStatus.STATUS_ALL) {
            status = null;
        }
    }


    public void transfer() {
        super.transfer();
        if (status == null || status.intValue() == DbStatus.STATUS_ALL) {
            status = null;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

}
