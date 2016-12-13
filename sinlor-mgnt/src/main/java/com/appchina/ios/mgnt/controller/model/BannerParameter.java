// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.cahe.model.app.Channel;
import com.appchina.ios.core.dto.app.PromoteApplication;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.web.model.Pager;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class BannerParameter {
    private String errMsg;
    private String status;
    private String channel;
    private String startTime = DateUtils.getToday();
    private String endTime = DateUtils.getDeltaNow(30);
    private Pager pager = new Pager();
    private int start;
    private int size;

    public static final Map<String, String> STATUS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -5110661221727298515L;

        {
            put("all", "全部");
            put(String.valueOf(PromoteApplication.STATUS_OK), "正常");
            put(String.valueOf(PromoteApplication.STATUS_DEL), "删除");
        }
    };

    public static final Map<String, String> WEB_CHANNELS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -5110661221727298515L;

        {
            put("all", "全部");
            for (Channel channel : Channel.CHANNELS) {
                put(channel.getChannel(), channel.getDesc());
            }
        }
    };

    public void transfer() {
        this.channel = formatChannel(channel);
        this.status = fromatStatus(status);
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
    }

    private String fromatStatus(String status) {
        if (StringUtils.equalsIgnoreCase("all", status)) {
            return null;
        }
        return status;

    }

    private String formatChannel(String channel) {
        if (StringUtils.equalsIgnoreCase("all", channel)) {
            return null;
        }
        return channel;

    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

}
