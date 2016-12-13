// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.web.model.StartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class CrawlerApplicationParameter extends StartSizeParameter {
    private Integer type;
    private Integer itemId;
    private Integer id;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

}
