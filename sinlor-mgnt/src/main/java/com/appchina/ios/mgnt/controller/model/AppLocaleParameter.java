// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class AppLocaleParameter {
    private int id;
    private Integer position;
    private Integer status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "AppLocaleParameter [id=" + id + ", position=" + position + ", status=" + status + "]";
    }
}
