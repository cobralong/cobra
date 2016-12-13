// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.web.model.StartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class AppStoreClientAuditInfoParamter extends StartSizeParameter {
    private Integer id;
    private String clientVersion;
    private Integer auditStatus;
    private String auditMessage;
    private String appStoreUrl;
    private String bundleId;

    public AppStoreClientAuditInfoParamter() {
    }

    public AppStoreClientAuditInfoParamter(String clientVersion, String bundleId) {
        super();
        this.clientVersion = clientVersion;
        this.bundleId = bundleId;
    }

    public AppStoreClientAuditInfoParamter(Integer id, Integer auditStatus, String auditMessage, String appStoreUrl) {
        super();
        this.id = id;
        this.auditStatus = auditStatus;
        this.auditMessage = auditMessage;
        this.appStoreUrl = appStoreUrl;
    }

    @Override
    public void transfer() {
        if (StringUtils.equalsIgnoreCase("all", bundleId)) {
            bundleId = null;
        }
        super.transfer();
    }

    @Override
    public void transfer(int size) {
        if (StringUtils.equalsIgnoreCase("all", bundleId)) {
            bundleId = null;
        }
        super.transfer(size);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditMessage() {
        return auditMessage;
    }

    public void setAuditMessage(String auditMessage) {
        this.auditMessage = auditMessage;
    }

    public String getAppStoreUrl() {
        return appStoreUrl;
    }

    public void setAppStoreUrl(String appStoreUrl) {
        this.appStoreUrl = appStoreUrl;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    @Override
    public String toString() {
        return "AppStoreClientAuditInfoParamter [id=" + id + ", clientVersion=" + clientVersion + ", auditStatus="
                + auditStatus + ", auditMessage=" + auditMessage + ", appStoreUrl=" + appStoreUrl + ", bundleId="
                + bundleId + "]";
    }

}
