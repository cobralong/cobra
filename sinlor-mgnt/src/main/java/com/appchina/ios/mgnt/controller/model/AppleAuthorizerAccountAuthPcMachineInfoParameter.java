// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com create date: 2016年3月28日
 *
 */
public class AppleAuthorizerAccountAuthPcMachineInfoParameter extends StatusStartSizeParameter {
    private Integer id;
    private Integer appleAccountId;
    private Integer pcServerId;
    private Integer pcInfoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppleAccountId() {
        return appleAccountId;
    }

    public void setAppleAccountId(Integer appleAccountId) {
        this.appleAccountId = appleAccountId;
    }

    public Integer getPcServerId() {
        return pcServerId;
    }

    public void setPcServerId(Integer pcServerId) {
        this.pcServerId = pcServerId;
    }

    public Integer getPcInfoId() {
        return pcInfoId;
    }

    public void setPcInfoId(Integer pcInfoId) {
        this.pcInfoId = pcInfoId;
    }

    @Override
    public String toString() {
        return "AppleAuthorizerAccountAuthPcMachineInfoParameter [id=" + id + ", appleAccountId=" + appleAccountId
                + ", pcServerId=" + pcServerId + ", pcInfoId=" + pcInfoId + ", toString()=" + super.toString() + "]";
    }
}
