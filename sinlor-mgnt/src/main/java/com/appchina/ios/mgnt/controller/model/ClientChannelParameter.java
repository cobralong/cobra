// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.appchina.ios.core.dto.system.ClientChannelType;
import com.appchina.ios.web.model.Pager;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class ClientChannelParameter extends StatusStartSizeParameter {
    private Integer parentId;
    private Integer leaf;
    private String channels;
    private String channel;
    private String desc;
    private String ids;
    protected Pager pager = new Pager();

    public static final int TOP_CHANNEL = ClientChannelType.TOP.getType();

    public static final Map<String, String> TOP_CHANNE = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1696507918446938986L;

        {
            put(String.valueOf(ClientChannelType.TOP.getType()), ClientChannelType.TOP.getDesc());
        }
    };

    public static final Map<String, String> ALL_CHANNE = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1696507918446938986L;

        {
            for (ClientChannelType type : ClientChannelType.values()) {

                put(String.valueOf(type.getType()), type.getDesc());
            }
        }
    };

    public void transfer() {
        if (this.parentId != null && this.parentId.intValue() == ClientChannelType.ALL.getType()) {
            this.parentId = null;
        }
        this.setStart((pager.getPage() - 1) * pager.getSize());
        this.setSize(pager.getSize());
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getIds() {
        return ids;
    }


    public void setIds(String ids) {
        this.ids = ids;
    }


    public Integer getLeaf() {
        return leaf;
    }


    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }


    @Override
    public String toString() {
        return "ClientChannelParameter [parentId=" + parentId + ", leaf=" + leaf + ", channel=" + channel + ", desc="
                + desc + ", ids=" + ids + ", pager=" + pager + "]";
    }


    public String getChannels() {
        return channels;
    }


    public void setChannels(String channels) {
        this.channels = channels;
    }
}
