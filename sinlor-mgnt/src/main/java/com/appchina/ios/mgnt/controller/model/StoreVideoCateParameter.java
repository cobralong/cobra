// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class StoreVideoCateParameter {
    private Integer id;
    private Integer position;
    private String name;
    private String shortDesc;
    private String icon;

    public StoreVideoCateParameter() {
        super();
    }

    public StoreVideoCateParameter(Integer position, String name, String shortDesc, String icon) {
        super();
        this.position = position;
        this.name = name;
        this.shortDesc = shortDesc;
        this.icon = icon;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "StoreVideoCateParameter [id=" + id + ", position=" + position + ", name=" + name + ", shortDesc="
                + shortDesc + ", icon=" + icon + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
