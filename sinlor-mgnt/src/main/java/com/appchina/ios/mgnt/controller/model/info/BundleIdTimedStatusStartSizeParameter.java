// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.info.AppStoreClientInfo;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class BundleIdTimedStatusStartSizeParameter extends TimedStatusStartSizeParameter {
    private String bundleId;

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    @Override
    public void transfer() {
        if (StringUtils.equals(AppStoreClientInfo.ALL_BUNDLE, bundleId)) {
            bundleId = null;
        }
        super.transfer();
    }

    @Override
    public void transfer(int pageSize) {
        if (StringUtils.equals(AppStoreClientInfo.ALL_BUNDLE, bundleId)) {
            bundleId = null;
        }
        super.transfer(pageSize);
    }

}
