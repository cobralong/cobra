// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.core.dto.app.AppBanned;
import com.appchina.ios.core.dto.app.RootApplication;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class AppBannedResult extends StatusStartSizeParameter {
    private AppBanned banned;
    private RootApplication root;

    public AppBannedResult(AppBanned banned, RootApplication root) {
        super();
        this.banned = banned;
        this.root = root;
    }

    public AppBanned getBanned() {
        return banned;
    }

    public void setBanned(AppBanned banned) {
        this.banned = banned;
    }

    public RootApplication getRoot() {
        return root;
    }

    public void setRoot(RootApplication root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "AppBannedResult [banned=" + banned + ", root=" + root + "]";
    }
}
