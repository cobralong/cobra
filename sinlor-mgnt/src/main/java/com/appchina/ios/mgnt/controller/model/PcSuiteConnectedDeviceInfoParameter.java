// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com create date: 2016年3月30日
 *
 */
public class PcSuiteConnectedDeviceInfoParameter extends TimedStatusStartSizeParameter {
    private Integer id;
    private String clientId;
    private String guid;
    private String platform;
    private String deviceGuid;
    private String imei;
    private String udid;
    private String color;
    private String iosVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceGuid() {
        return deviceGuid;
    }

    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    @Override
    public String toString() {
        return "PcSuiteConnectedDeviceInfoParameter [id=" + id + ", clientId=" + clientId + ", guid=" + guid
                + ", platform=" + platform + ", deviceGuid=" + deviceGuid + ", imei=" + imei + ", udid=" + udid
                + ", color=" + color + ", iosVersion=" + iosVersion + ", toString()=" + super.toString() + "]";
    }

}
