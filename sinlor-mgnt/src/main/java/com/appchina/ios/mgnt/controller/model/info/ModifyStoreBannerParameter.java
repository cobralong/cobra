package com.appchina.ios.mgnt.controller.model.info;

/**
 * Created by zhouyanhui on 8/4/15.
 */
public class ModifyStoreBannerParameter {
    private int id;
    private Integer status;
    private Integer rank;
    private String et;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getRank() {
        return rank;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public String getEt() {
        return et;
    }
}
