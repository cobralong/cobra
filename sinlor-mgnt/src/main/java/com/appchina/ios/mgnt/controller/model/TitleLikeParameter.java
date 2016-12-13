// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.web.model.StartSizeParameter;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class TitleLikeParameter extends StartSizeParameter {
    private String title;
    private String channel;

    public TitleLikeParameter(int start, int size, String query, Integer cid) {
        this.start = size;
        this.size = size;
        this.query = query;
        this.cid = cid;
    }

    private String query;
    private Integer cid;

    public TitleLikeParameter(String title, int start, int size) {
        this.start = start;
        this.size = size;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TitleLikeParameter [title=" + title + ", toString()=" + super.toString() + "]";
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

}
