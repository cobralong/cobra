// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.cahe.model.app.Channel;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class ProductPageDictionaryParameter {
    private String channel;
    private int page;
    private String desc;
    private String errMsg;

    public void transfer() {
        if (StringUtils.isEmpty(channel)) {
            channel = Channel.MOBILECLIENT.getChannel();
        }
    }

    public ProductPageDictionaryParameter() {
    }

    public ProductPageDictionaryParameter(String channel, int page, String desc) {
        super();
        this.channel = channel;
        this.page = page;
        this.desc = desc;
    }


    public ProductPageDictionaryParameter(String channel) {
        super();
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "ProductPageDictionaryParameter [channel=" + channel + ", page=" + page + ", desc=" + desc + ", errMsg="
                + errMsg + "]";
    }

}
