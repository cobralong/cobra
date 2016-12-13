// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.core.dto.app.Category;
import com.appchina.ios.core.dto.app.RootAppRankMetaMap;
import com.appchina.ios.core.dto.app.RootAppRankMetaType;
import com.appchina.ios.web.model.Pager;

/**
 * @author luofei@refanqie.com (Your Name Here)
 */
public class RootAppRankParameter {
    private String errMsg;
    private Pager pager = new Pager();
    private int start;
    private int size;
    private int srcType;
    private int srcId;
    private int rankId;

    public RootAppRankParameter() {
        super();
    }

    public RootAppRankParameter(Integer srcType, Integer srcId, Integer rankId) {
        super();
        this.srcType = srcType;
        this.srcId = srcId;
        this.rankId = rankId;
    }

    public void transfer() {
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
        if (rankId == 0 || srcId == 0 || srcType == 0) {
            srcType = RootAppRankMetaMap.RankSrcType.CATEGORY.getValue();
            srcId = Category.GAME_PARENT_ID;
            rankId = RootAppRankMetaType.LATEST_RANK_ID;
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

    public int getSrcType() {
        return srcType;
    }

    public void setSrcType(int srcType) {
        this.srcType = srcType;
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    @Override
    public String toString() {
        return "RootAppRankParameter{" +
                "errMsg='" + errMsg + '\'' +
                ", pager=" + pager +
                ", start=" + start +
                ", size=" + size +
                ", srcType=" + srcType +
                ", srcId=" + srcId +
                ", rankId=" + rankId +
                '}';
    }
}
