// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.cahe.model.app.Channel;
import com.appchina.ios.core.utils.ChannelUtils;
import com.appchina.ios.core.utils.DateUtils;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class PromoteAppsParameter extends StatusStartSizeParameter {
    private String channel;
    private String st;
    private String et;
    private Integer rank;
    private Integer rootId;
    private Integer id;
    private String ids;
    public static final Map<String, String> STATUS = DbStatus.STATUS;
    public static final Map<String, String> ALL_CHANNELS = ChannelUtils.ALL_CAHNNELS;
    public static final Map<String, String> CHANNELS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -5110661221727298515L;

        {
            for (Channel channel : Channel.CHANNELS) {
                put(channel.getChannel(), channel.getDesc());
            }
        }
    };


    public void transfer() {
        if (StringUtils.isEmpty(channel)) {
            channel = Channel.MOBILECLIENT.getChannel();
        }
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
        if (StringUtils.isEmpty(st)) {
            st = DateUtils.getNow();
        }
        if (StringUtils.isEmpty(et)) {
            et = DateUtils.getDeltaNow(30);
        }
        if (super.status == null) {
            super.status = DbStatus.STATUS_OK;
        }
    }

    public void transfer(int pageSize) {
        pager.setSize(pageSize);
        transfer();
    }

    public PromoteAppsParameter() {
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getRootId() {
        return rootId;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PromoteAppsParameter [channel=" + channel + ", st=" + st + ", et=" + et + ", rank=" + rank
                + ", rootId=" + rootId + ", id=" + id + ", ids=" + ids + "]";
    }


}
