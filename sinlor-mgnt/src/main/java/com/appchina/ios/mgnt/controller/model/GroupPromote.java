// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.List;

import com.appchina.ios.core.dto.app.PromoteApplication;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class GroupPromote {
    private int rootId;
    private List<PromoteApplication> promotes;

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public List<PromoteApplication> getPromotes() {
        return promotes;
    }

    public void setPromotes(List<PromoteApplication> promotes) {
        this.promotes = promotes;
    }
}
