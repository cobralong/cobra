// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.dto.info.AppStoreClientInfo;

/**
 * @author luofei@appchina.com create date: 2016年3月27日
 *
 */
public class BundleIdStatusStartSizeParameter extends StatusStartSizeParameter {
    private String bundleId;

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    @Override
    public String toString() {
        return "BundleIdStatusStartSizeParameter [bundleId=" + bundleId + ", toString()=" + super.toString() + "]";
    }

    @Override
    public void transfer(int size) {
        super.transfer(size);
        formatBundleId();
    }

    @Override
    public void transfer() {
        super.transfer();
        formatBundleId();
    }

    private void formatBundleId() {
        if (StringUtils.equalsIgnoreCase(StatusType.STATUS_ALL.getDesc(), bundleId)
                || StringUtils.equalsIgnoreCase(AppStoreClientInfo.ALL_BUNDLE, bundleId)) {
            bundleId = null;
        }
    }
}
