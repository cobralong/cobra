// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.DbStatus;


/**
 * @author luofei@appchina.com create date: 2016年4月8日
 *
 */
public class AuthorizerAppDownloadFeedbackParameter extends StatusStartSizeParameter {
    private Integer id;
    private Integer taskId;
    private Integer feedbackStatus;
    private Integer rootId;
    private String appleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(Integer feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public String getAppleId() {
        return appleId;
    }

    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }

    @Override
    public void transfer() {
        if (StringUtils.equalsIgnoreCase(appleId, DbStatus.ALL_DESC)
                || StringUtils.equals(appleId, String.valueOf(DbStatus.ALL_ID_DESC))) {
            appleId = null;
        }
        super.transfer();
    }

}
