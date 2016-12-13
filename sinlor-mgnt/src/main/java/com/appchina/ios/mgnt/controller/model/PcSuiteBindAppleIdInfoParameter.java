// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;


/**
 * @author luofei@appchina.com create date: 2016年6月26日
 *
 */
public class PcSuiteBindAppleIdInfoParameter extends StatusStartSizeParameter {

    /**
     * 
     */
    private int id;
    private int pcSuiteUserId;
    private String appleId;
    private String passwd;
    private int loginStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPcSuiteUserId() {
        return pcSuiteUserId;
    }

    public void setPcSuiteUserId(int pcSuiteUserId) {
        this.pcSuiteUserId = pcSuiteUserId;
    }

    public String getAppleId() {
        return appleId;
    }

    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    @Override
    public String toString() {
        return "PcSuiteBindAppleIdInfo [id=" + id + ", pcSuiteUserId=" + pcSuiteUserId + ", appleId=" + appleId
                + ", passwd=" + passwd + ", loginStatus=" + loginStatus + "]";
    }

}
