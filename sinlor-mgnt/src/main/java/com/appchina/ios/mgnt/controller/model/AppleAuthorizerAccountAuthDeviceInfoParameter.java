// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;


/**
 * @author luofei@appchina.com create date: 2016年3月28日
 *
 */
public class AppleAuthorizerAccountAuthDeviceInfoParameter extends StatusStartSizeParameter {
    private Integer id;
    private String imei;
    private String udid;
    private Integer appleAccountId;
    private String appleAccountEmail;
    private Integer writeStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAppleAccountId() {
        return appleAccountId;
    }

    public void setAppleAccountId(Integer appleAccountId) {
        this.appleAccountId = appleAccountId;
    }

    public String getAppleAccountEmail() {
        return appleAccountEmail;
    }

    public void setAppleAccountEmail(String appleAccountEmail) {
        this.appleAccountEmail = appleAccountEmail;
    }

    public Integer getWriteStatus() {
        return writeStatus;
    }

    public void setWriteStatus(Integer writeStatus) {
        this.writeStatus = writeStatus;
    }

    @Override
    public String toString() {
        return "AppleAuthorizerAccountAuthDeviceInfoParameter [id=" + id + ", imei=" + imei + ", udid=" + udid
                + ", appleAccountId=" + appleAccountId + ", appleAccountEmail=" + appleAccountEmail + ", writeStatus="
                + writeStatus + ", toString()=" + super.toString() + "]";
    }

}
