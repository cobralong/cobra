// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.core.dto.online.AppOnlineType;
import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class AppUploadStatParameter extends TimedStatusStartSizeParameter {
    private Integer type=AppOnlineType.CLIENT_API_CRAWLER_ONLINE.getType();

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
