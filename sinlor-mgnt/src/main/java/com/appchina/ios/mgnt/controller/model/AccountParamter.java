// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.mgnt.controller.model.info.BundleIdTimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class AccountParamter extends BundleIdTimedStatusStartSizeParameter {
    private Integer id;
    private String email;
    private String name;
    private String bundleId;
    private String password;
    private String avator;
    private String channel;
    private Integer channelId;
    private Integer downloaded;
    private Float saved;
    private Integer applyCount;
    private Long lastActiveTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Integer downloaded) {
        this.downloaded = downloaded;
    }

    public Float getSaved() {
        return saved;
    }

    public void setSaved(Float saved) {
        this.saved = saved;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public Long getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

}
