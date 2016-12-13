// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.app.ListAdInfo;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class ListAdInfoSimple {
    private ApplicationSimple app;
    private ListAdInfo info;

    public ListAdInfoSimple(ApplicationSimple app, ListAdInfo info) {
        super();
        this.app = app;
        this.info = info;
    }

    public ApplicationSimple getApp() {
        return app;
    }

    public void setApp(ApplicationSimple app) {
        this.app = app;
    }

    public ListAdInfo getInfo() {
        return info;
    }

    public void setInfo(ListAdInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ListAdInfoSimple [app=" + app + ", info=" + info + "]";
    }
}
