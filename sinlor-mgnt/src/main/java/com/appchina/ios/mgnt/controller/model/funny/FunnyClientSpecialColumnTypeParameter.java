package com.appchina.ios.mgnt.controller.model.funny;

import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;


public class FunnyClientSpecialColumnTypeParameter extends StatusStartSizeParameter {

    private int id;
    private String name;
    private Integer appPromoteStatus;
    private Integer appNotPromoteStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAppPromoteStatus() {
        return appPromoteStatus;
    }

    public void setAppPromoteStatus(Integer appPromoteStatus) {
        this.appPromoteStatus = appPromoteStatus;
    }

    public Integer getAppNotPromoteStatus() {
        return appNotPromoteStatus;
    }

    public void setAppNotPromoteStatus(Integer appNotPromoteStatus) {
        this.appNotPromoteStatus = appNotPromoteStatus;
    }

}
