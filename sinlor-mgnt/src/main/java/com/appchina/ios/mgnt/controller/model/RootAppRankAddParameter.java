package com.appchina.ios.mgnt.controller.model;

/**
 * Created by zhouyanhui on 12/4/15.
 */
public class RootAppRankAddParameter {

    private Integer rootId;
    private Integer srcId;
    private Integer srcType;
    private Integer rankId;
    private Integer pos;
    private Integer status;

    public RootAppRankAddParameter(Integer rootId, Integer srcId, Integer srcType,
                                   Integer rankId, Integer pos) {
        this.rootId = rootId;
        this.srcId = srcId;
        this.srcType = srcType;
        this.rankId = rankId;
        this.pos = pos;
        this.status = null;
    }

    public RootAppRankAddParameter(Integer rootId, Integer srcId, Integer srcType,
                                   Integer rankId, Integer pos, Integer status) {
        this.rootId = rootId;
        this.srcId = srcId;
        this.srcType = srcType;
        this.rankId = rankId;
        this.pos = pos;
        this.status = status;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Integer getSrcType() {
        return srcType;
    }

    public void setSrcType(Integer srcType) {
        this.srcType = srcType;
    }

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RootAppRankAddParameter{" +
                "rootId=" + rootId +
                ", srcId=" + srcId +
                ", srcType=" + srcType +
                ", rankId=" + rankId +
                ", pos=" + pos +
                ", status=" + status +
                '}';
    }
}
