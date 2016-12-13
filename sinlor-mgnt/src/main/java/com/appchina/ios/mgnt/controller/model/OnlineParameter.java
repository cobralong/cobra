// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class OnlineParameter {
    private Integer itemId;
    private String url;
    private String locale;

    public Integer getItemId() {

        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "OnlineParameter [itemId=" + itemId + ", url=" + url + ", locale=" + locale + "]";
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
