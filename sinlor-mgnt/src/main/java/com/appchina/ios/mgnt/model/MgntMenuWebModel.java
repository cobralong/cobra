// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.model;

import java.util.List;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class MgntMenuWebModel {
    private int id;
    private Integer parent;
    private String name;
    private String url;
    private List<MgntMenuWebModel> children;

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

    public List<MgntMenuWebModel> getChildren() {
        return children;
    }

    public void setChildren(List<MgntMenuWebModel> children) {
        this.children = children;
    }

}
