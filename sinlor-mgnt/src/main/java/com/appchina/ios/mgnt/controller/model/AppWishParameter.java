// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class AppWishParameter extends TimedStatusStartSizeParameter {
    private Integer rootId;
    private Integer hadBuy;
    private String column;

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getHadBuy() {
        return hadBuy;
    }

    public void setHadBuy(Integer hadBuy) {
        this.hadBuy = hadBuy;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "AppWishParameter [rootId=" + rootId + ", hadBuy=" + hadBuy + ", column=" + column + "]";
    }
}
