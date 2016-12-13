// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.cahe.model.app.Channel;
import com.appchina.ios.web.model.Pager;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class SearchPlaceholderParameter {
    private int id;
    private String name;
    private int status;
    private String channel;
    private int page;
    private int start;
    private int size;
    private String errMsg;
    private Pager pager = new Pager();

    public void transfer() {
        if (StringUtils.isEmpty(channel)) {
            channel = Channel.MOBILECLIENT.getChannel();
        }
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
    }

    public SearchPlaceholderParameter() {
    }


    public SearchPlaceholderParameter(String name, String channel, int page) {
        this.name = name;
        this.channel = channel;
        this.page = page;
    }

    public SearchPlaceholderParameter(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public SearchPlaceholderParameter(String channel, int status, int start, int size) {
        this.start = start;
        this.size = size;
        this.channel = channel;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "SearchPlaceholderParameter [id=" + id + ", name=" + name + ", status=" + status + ", channel="
                + channel + ", page=" + page + ", start=" + start + ", size=" + size + ", errMsg=" + errMsg
                + ", pager=" + pager + "]";
    }

}
