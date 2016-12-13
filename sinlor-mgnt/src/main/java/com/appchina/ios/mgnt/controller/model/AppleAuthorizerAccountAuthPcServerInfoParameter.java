// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com create date: 2016年3月28日
 *
 */
public class AppleAuthorizerAccountAuthPcServerInfoParameter extends StatusStartSizeParameter {
    private Integer id;
    private Integer appleAccountId;
    private Integer pcServerId;

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

    @Override
    public String toString() {
        return "AppleAuthorizerAccountAuthPcServerInfoParameter [id=" + id + ", appleAccountId=" + appleAccountId
                + ", pcServerId=" + pcServerId + ", toString()=" + super.toString() + "]";
    }
}
