// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class AccountWishProgressInfoParameter extends StatusStartSizeParameter {
    private Integer wishId;
    private Integer accountaId;
    private Integer rootId;
    private Integer aims;
    private Integer done;
    private Float progress;

    public Integer getWishId() {
        return wishId;
    }

    public void setWishId(Integer wishId) {
        this.wishId = wishId;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getAims() {
        return aims;
    }

    public void setAims(Integer aims) {
        this.aims = aims;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getAccountaId() {
        return accountaId;
    }

    public void setAccountaId(Integer accountaId) {
        this.accountaId = accountaId;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }
}
