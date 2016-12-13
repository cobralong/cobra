// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;



/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class IpaPlistModifyParameter extends PlistParameter {
    private Boolean able;
    private Boolean intervene;
    private int plistId;

    public Boolean getAble() {
        return able;
    }

    public void setAble(Boolean able) {
        this.able = able;
    }

    public Boolean getIntervene() {
        return intervene;
    }

    public void setIntervene(Boolean intervene) {
        this.intervene = intervene;
    }

    public int getPlistId() {
        return plistId;
    }

    public void setPlistId(int plistId) {
        this.plistId = plistId;
    }

    @Override
    public String toString() {
        return "IpaPlistModifyParameter [able=" + able + ", intervene=" + intervene + ", plistId=" + plistId + "]";
    }
}
