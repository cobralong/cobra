// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dto;

import com.appchina.ios.core.DbStatus;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class MgntMenu extends DbStatus{
    public static final int STATUS_OK = 0;
    public static final int STATUS_DEL = -1;
    private int id;
    private Integer parent;
    private String name;
    private String url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static int getStatusOk() {
        return STATUS_OK;
    }

    @Override
    public String toString() {
        return "MgntMenu{" +
                "id=" + id +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
