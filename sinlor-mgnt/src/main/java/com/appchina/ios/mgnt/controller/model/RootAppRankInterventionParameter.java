package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.dto.app.Category;
import com.appchina.ios.core.dto.app.RootAppRankMetaMap;
import com.appchina.ios.core.dto.app.RootAppRankMetaType;
import com.appchina.ios.web.model.Pager;

/**
 * Created by zhouyanhui on 12/4/15.
 */
public class RootAppRankInterventionParameter {
    private String errMsg;
    private Pager pager = new Pager();
    private int start;
    private int size;
    private Integer srcType;
    private Integer srcId;
    private Integer rankId;
    private Integer status;

    public RootAppRankInterventionParameter(){
        super();
    }

    public RootAppRankInterventionParameter(Integer srcType, Integer srcId, Integer rankId) {
        super();
        this.srcType = srcType;
        this.srcId = srcId;
        this.rankId = rankId;
    }

    public void transfer() {
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
        if (rankId == null || srcId == null || srcType == null) {
            srcType = RootAppRankMetaMap.RankSrcType.CATEGORY.getValue();
            srcId = Category.GAME_PARENT_ID;
            rankId = RootAppRankMetaType.LATEST_RANK_ID;
        }
        if (status == null) {
            status = DbStatus.STATUS_OK;
        }
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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

    public Integer getSrcType() {
        return srcType;
    }

    public void setSrcType(Integer srcType) {
        this.srcType = srcType;
    }

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RootAppRankInterventionParameter{" +
                "errMsg='" + errMsg + '\'' +
                ", pager=" + pager +
                ", start=" + start +
                ", size=" + size +
                ", srcType=" + srcType +
                ", srcId=" + srcId +
                ", rankId=" + rankId +
                ", status=" + status +
                '}';
    }
}
