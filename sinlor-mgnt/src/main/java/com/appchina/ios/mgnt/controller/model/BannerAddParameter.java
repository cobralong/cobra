// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;



/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class BannerAddParameter {
    private int rootId;
    private String channel;
    private String st;
    private String et;
    private String bannerUrl;
    private int rank;

    public BannerAddParameter() {
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "BannerAddParameter [rootId=" + rootId + ", channel=" + channel + ", st=" + st + ", et=" + et
                + ", bannerUrl=" + bannerUrl + ", rank=" + rank + "]";
    }
}
