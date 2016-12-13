// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.DbStatus;


/**
 * 具体某个应用的某个版本的下载任务 version可能会改 如果下的ipa版本高于当前任务所存储的版本，则会将当前任务所存储的版本进行变化
 * 
 * @author luofei@appchina.com create date: 2016年3月29日
 *
 */
public class AuthorizerAppDownloadTaskParameter extends StatusStartSizeParameter {
    private Integer id;
    private Integer itemId;
    private String version;
    private Integer taskStatus;
    private Integer buyInfoId;// 在用户购买的应用升级后，那条记录又会生成一条新的下载任务，它与version合成一个unique
    private Integer pureIpa;
    private String appleId;

    @Override
    public void transfer() {
        if (StringUtils.equalsIgnoreCase(appleId, DbStatus.ALL_DESC)
                || StringUtils.equals(appleId, String.valueOf(DbStatus.ALL_ID_DESC))) {
            appleId = null;
        }
        super.transfer();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getBuyInfoId() {
        return buyInfoId;
    }

    public void setBuyInfoId(Integer buyInfoId) {
        this.buyInfoId = buyInfoId;
    }

    @Override
    public String toString() {
        return "AuthorizerAppDownloadTaskParameter [id=" + id + ", itemId=" + itemId + ", version=" + version
                + ", taskStatus=" + taskStatus + ", buyInfoId=" + buyInfoId + ", pureIpa=" + pureIpa + ", appleId="
                + appleId + ", toString()=" + super.toString() + "]";
    }

    public Integer getPureIpa() {
        return pureIpa;
    }

    public void setPureIpa(Integer pureIpa) {
        this.pureIpa = pureIpa;
    }


    public String getAppleId() {
        return appleId;
    }


    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }
}
