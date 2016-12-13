// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.web.model.Pager;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class AppTitleSearchParameter {
    private String title;
    private int type;
    private String rootIds;
    private Pager pager = new Pager();

    public int getStart() {
        return (pager.getPage() - 1) * pager.getSize();
    }

    public int getSize() {
        return pager.getSize();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRootIds() {
        return rootIds;
    }

    public void setRootIds(String rootIds) {
        this.rootIds = rootIds;
    }

}
