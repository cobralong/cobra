// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@refanqie.com (Your Name Here)
 */
public class AppTagListParameter {
    private String errMsg;
    private Integer parent;

    public AppTagListParameter() {
        super();
    }

    public void transfer() {
        if (parent != null && parent.intValue() == 0) {
            parent = null;
        }
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "AppTagListParameter{" +
                "errMsg='" + errMsg + '\'' +
                ", parent=" + parent +
                '}';
    }
}
