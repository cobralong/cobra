// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.cahe.model.app.Channel;
import com.appchina.ios.web.model.Pager;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class SearchDefaultResultParameter {
    private int id;
    private int status;
    private String channel;
    private int rootId;
    private int rank;
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

    public SearchDefaultResultParameter() {
    }


    public SearchDefaultResultParameter(int rootId, int rank, String channel) {
        this.rootId = rootId;
        this.rank = rank;
        this.channel = channel;
    }

    public SearchDefaultResultParameter(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public SearchDefaultResultParameter(String channel, int status, int start, int size) {
        this.start = start;
        this.size = size;
        this.channel = channel;
        this.status = status;
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

    @Override
    public String toString() {
        return "SearchHotwordParameter [id=" + id + ", status=" + status + ", channel=" + channel + ", rank=" + rank
                + ", start=" + start + ", size=" + size + ", errMsg=" + errMsg + ", pager=" + pager + "]";
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }
}
