// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.web.model.Pager;

import java.util.Map;
import java.util.Map.Entry;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class AppLimitDiscountListParameter {
    private String errMsg;
    private Pager pager = new Pager();
    private int start;
    private int size;
    private Integer type;
    private Integer rank;
    private Integer rootId;
    private Integer status;
    private Integer id;

    public AppLimitDiscountListParameter() {
        super();
    }

    public AppLimitDiscountListParameter(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public AppLimitDiscountListParameter(Integer rootId, Integer type, Integer rank) {
        super();
        this.rootId = rootId;
        this.type = type;
        this.rank = rank;
    }

    public void transfer(Map<String, String> types) {
        if (type == null || type.intValue() == 0) {
            for (Entry<String, String> entry : types.entrySet()) {
                type = Integer.parseInt(entry.getKey());
                break;
            }
        }
        transfer(type);
    }

    public void transfer(int type) {
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
        this.type = type;
        if (status == null) {
            this.status = DbStatus.STATUS_OK;
        }
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    @Override
    public String toString() {
        return "AppLimitDiscountListParameter{" +
                "errMsg='" + errMsg + '\'' +
                ", pager=" + pager +
                ", start=" + start +
                ", size=" + size +
                ", type=" + type +
                ", rank=" + rank +
                ", rootId=" + rootId +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
