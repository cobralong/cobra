package com.appchina.ios.mgnt.controller.model;

/**
 * Created by zhouyanhui on 12/4/15.
 */
public class RootAppRankInterventionUpdateParameter {
    private int id;
    private Integer status;
    private Integer pos;

    public RootAppRankInterventionUpdateParameter(int interventId, Integer status, Integer pos) {
        this.id = interventId;
        this.status = status;
        this.pos = pos;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RootAppRankInterventionUpdateParameter{" +
                "id=" + id +
                ", status=" + status +
                ", pos=" + pos +
                '}';
    }
}
