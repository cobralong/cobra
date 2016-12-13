// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class PcSuiteVersionParameter extends StatusStartSizeParameter {
    private Integer id;
    private String upgradeInfo;
    private String upgradeInfoEn;
    private String md5;
    private String url;
    private String version;
    private Integer versionCode;
    private Integer productIpSwitch;
    private String versionName;
    private String channel;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpgradeInfo() {
        return upgradeInfo;
    }

    public void setUpgradeInfo(String upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getProductIpSwitch() {
        return productIpSwitch;
    }

    public void setProductIpSwitch(Integer productIpSwitch) {
        this.productIpSwitch = productIpSwitch;
    }

    public String getUpgradeInfoEn() {
        return upgradeInfoEn;
    }

    public void setUpgradeInfoEn(String upgradeInfoEn) {
        this.upgradeInfoEn = upgradeInfoEn;
    }

    @Override
    public String toString() {
        return "PcSuiteVersionParameter [id=" + id + ", upgradeInfo=" + upgradeInfo + ", upgradeInfoEn="
                + upgradeInfoEn + ", md5=" + md5 + ", url=" + url + ", version=" + version + ", versionCode="
                + versionCode + ", productIpSwitch=" + productIpSwitch + ", versionName=" + versionName + ", channel="
                + channel + ", toString()=" + super.toString() + "]";
    }
}
