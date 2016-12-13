// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class PlatformOsVersionParameter {
    private int id;
    private int type;
    private Integer status;
    private Integer defaultStatus;

    public PlatformOsVersionParameter(int id, int type, Integer status, Integer defaultStatus) {
        super();
        this.id = id;
        this.type = type;
        this.status = status;
        this.defaultStatus = defaultStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

}
