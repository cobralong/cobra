// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com create date: 2016年2月29日
 *
 */
public class AppleAccountAutomatedInfoParameter extends TimedStatusStartSizeParameter {
    private String appleId;
    private Integer activeStatus;
    private Integer automatedType;

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Integer getAutomatedType() {
        return automatedType;
    }

    public void setAutomatedType(Integer automatedType) {
        this.automatedType = automatedType;
    }

    public String getAppleId() {
        return appleId;
    }

    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }

}
