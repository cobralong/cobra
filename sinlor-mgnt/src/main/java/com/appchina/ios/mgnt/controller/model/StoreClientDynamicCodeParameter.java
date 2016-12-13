// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.info.AppStoreClientAuditInfo;
import com.appchina.ios.core.dto.info.AppStoreClientInfo;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class StoreClientDynamicCodeParameter extends StatusStartSizeParameter {
    private Integer id;
    private String bundleId;
    private String clientVersion;
    private String url;
    private String name;
    private String md5;

    @Override
    public void transfer() {
        if (StringUtils.equals(AppStoreClientInfo.ALL_BUNDLE, bundleId)) {
            bundleId = null;
        }
        if (StringUtils.equalsIgnoreCase(AppStoreClientAuditInfo.ALL_VERSION, clientVersion)) {
            clientVersion = null;
        }
        super.transfer();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "StoreClientDynamicCodeParameter [id=" + id + ", bundleId=" + bundleId + ", clientVersion="
                + clientVersion + ", url=" + url + ", name=" + name + ", md5=" + md5 + "]";
    }

}
