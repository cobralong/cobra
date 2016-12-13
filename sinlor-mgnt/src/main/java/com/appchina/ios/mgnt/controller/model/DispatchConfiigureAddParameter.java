// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class DispatchConfiigureAddParameter {
    private String st;
    private String et;
    private int dayDispatchs;
    private String channel;
    private boolean rewrite;
    private boolean reinit;

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

    public int getDayDispatchs() {
        return dayDispatchs;
    }

    public void setDayDispatchs(int dayDispatchs) {
        this.dayDispatchs = dayDispatchs;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isRewrite() {
        return rewrite;
    }

    public void setRewrite(boolean rewrite) {
        this.rewrite = rewrite;
    }

    public boolean isReinit() {
        return reinit;
    }

    public void setReinit(boolean reinit) {
        this.reinit = reinit;
    }
}
