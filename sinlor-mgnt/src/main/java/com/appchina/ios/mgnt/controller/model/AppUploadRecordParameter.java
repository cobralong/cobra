// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.web.model.StartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class AppUploadRecordParameter extends StartSizeParameter {
    private Integer type;
    private Integer onlineStatus;
    private int statId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public int getStatId() {
        return statId;
    }

    public void setStatId(int statId) {
        this.statId = statId;
    }
}
