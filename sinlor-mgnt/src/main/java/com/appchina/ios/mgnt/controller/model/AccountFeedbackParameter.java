// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.core.StatusType;
import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class AccountFeedbackParameter extends TimedStatusStartSizeParameter {
    private String channel;
    private String uid;
    private Integer id;
    private String responses;

    @Override
    public void transfer() {
        super.transfer();
        if (status == null) {
            status = StatusType.STATUS_ALL.getStatus();
        }
    }

    @Override
    public void transfer(int pageSize) {
        super.transfer(pageSize);
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

    @Override
    public String toString() {
        return "AccountFeedbackParameter [channel=" + channel + ", uid=" + uid + ", id=" + id + ", responses="
                + responses + ", toString()=" + super.toString() + "]";
    }
}
