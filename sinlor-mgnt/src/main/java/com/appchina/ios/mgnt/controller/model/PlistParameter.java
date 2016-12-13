// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.Map;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.web.model.Pager;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class PlistParameter {
    protected String errMsg;
    protected int status;
    protected Pager pager = new Pager();
    protected int start;
    protected int size;
    protected Integer rootId;

    public static final Map<String, String> STATUS = DbStatus.STATUS;


    public void transfer() {
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    @Override
    public String toString() {
        return "PlistParameter [errMsg=" + errMsg + ", status=" + status + ", pager=" + pager + ", start=" + start
                + ", size=" + size + ", rootId=" + rootId + "]";
    }

}
