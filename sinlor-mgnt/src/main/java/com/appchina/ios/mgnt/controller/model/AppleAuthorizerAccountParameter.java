// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com create date: 2016年3月23日
 *
 */
public class AppleAuthorizerAccountParameter extends TimedStatusStartSizeParameter {
    private Integer id;
    private Integer type;
    private String appleId;
    private String password;
    private String icon;
    private String name;
    private String locale;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppleId() {
        return appleId;
    }

    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
