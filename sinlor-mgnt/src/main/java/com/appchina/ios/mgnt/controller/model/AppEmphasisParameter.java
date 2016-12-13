// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.core.DbStatus;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class AppEmphasisParameter extends StatusStartSizeParameter {
    private Integer rootId;

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    @Override
    public void transfer() {
        super.transfer();
        if (super.status == null) {
            super.status = DbStatus.STATUS_OK;
        }
    }

    @Override
    public void transfer(int size) {
        super.transfer(size);
        if (super.status == null) {
            super.status = DbStatus.STATUS_OK;
        }
    }
}
