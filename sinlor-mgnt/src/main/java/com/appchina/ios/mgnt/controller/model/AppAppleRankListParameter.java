// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.web.model.StartSizeParameter;



/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class AppAppleRankListParameter extends StartSizeParameter {
    private Integer feedType;
    private Integer locale;

    public Integer getFeedType() {
        return feedType;
    }

    public void setFeedType(Integer feedType) {
        this.feedType = feedType;
    }

    public Integer getLocale() {
        return locale;
    }

    public void setLocale(Integer locale) {
        this.locale = locale;
    }
}
