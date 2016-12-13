// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.web.model.Pager;



/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class IpaPlistIntervenelistParameter {
    private String errMsg;
    private Pager pager = new Pager();
    private int start;
    private int size;


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

}
