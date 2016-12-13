// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.info.AppStoreClientInfo;
import com.appchina.ios.web.model.StartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class StoreClientConfParameter extends StartSizeParameter {
    private Integer id;
    private String bundleId;
    private String clientVersion;
    private Integer index;
    private Integer homeModel;
    private String shareUrl;
    private Integer localAuthInstallSupport;
    private Integer promoteAppSupport;
    private Integer appModel;
    private String browserUrl;
    private String webClipUrl;
    private String webClipDesc;
    private Integer weight;

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getHomeModel() {
        return homeModel;
    }

    public void setHomeModel(Integer homeModel) {
        this.homeModel = homeModel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

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

    public Integer getAppModel() {
        return appModel;
    }

    public void setAppModel(Integer appModel) {
        this.appModel = appModel;
    }

    public String getWebClipUrl() {
        return webClipUrl;
    }

    public void setWebClipUrl(String webClipUrl) {
        this.webClipUrl = webClipUrl;
    }

    public String getWebClipDesc() {
        return webClipDesc;
    }

    public void setWebClipDesc(String webClipDesc) {
        this.webClipDesc = webClipDesc;
    }

    public String getBrowserUrl() {
        return browserUrl;
    }

    public void setBrowserUrl(String browserUrl) {
        this.browserUrl = browserUrl;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }


    public Integer getLocalAuthInstallSupport() {
        return localAuthInstallSupport;
    }

    public void setLocalAuthInstallSupport(Integer localAuthInstallSupport) {
        this.localAuthInstallSupport = localAuthInstallSupport;
    }

    public Integer getPromoteAppSupport() {
        return promoteAppSupport;
    }

    public void setPromoteAppSupport(Integer promoteAppSupport) {
        this.promoteAppSupport = promoteAppSupport;
    }

    @Override
    public String toString() {
        return "StoreClientConfParameter [id=" + id + ", bundleId=" + bundleId + ", clientVersion=" + clientVersion
                + ", index=" + index + ", homeModel=" + homeModel + ", shareUrl=" + shareUrl
                + ", localAuthInstallSupport=" + localAuthInstallSupport + ", promoteAppSupport=" + promoteAppSupport
                + ", appModel=" + appModel + ", browserUrl=" + browserUrl + ", webClipUrl=" + webClipUrl
                + ", webClipDesc=" + webClipDesc + ", weight=" + weight + "]";
    }

}
