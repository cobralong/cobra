// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;


/**
 * @author luofei@appchina.com create date: 2016年3月11日
 *
 */
public class AppStoreUserCenterConfigureParameter extends BundleIdTimedStatusStartSizeParameter {
    private Integer id;
    private String clientVersion;
    private Integer marketFlag;
    private Integer defaultFlag;
    private String iconUrl;
    private String title;
    private String buttom;

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public Integer getMarketFlag() {
        return marketFlag;
    }

    public void setMarketFlag(Integer marketFlag) {
        this.marketFlag = marketFlag;
    }

    public Integer getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Integer defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getButtom() {
        return buttom;
    }

    public void setButtom(String buttom) {
        this.buttom = buttom;
    }

    @Override
    public String toString() {
        return "AppStoreUserCenterConfigureParameter [id=" + id + ", clientVersion=" + clientVersion + ", marketFlag="
                + marketFlag + ", defaultFlag=" + defaultFlag + ", iconUrl=" + iconUrl + ", title=" + title
                + ", buttom=" + buttom + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
