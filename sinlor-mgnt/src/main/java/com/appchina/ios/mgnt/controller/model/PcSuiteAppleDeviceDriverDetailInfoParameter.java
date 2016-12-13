// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;


/**
 * @author luofei@appchina.com create date: 2016年3月14日
 *
 */
public class PcSuiteAppleDeviceDriverDetailInfoParameter {
    private Integer id;
    private String name;
    private String version;
    private Integer driverId;
    private String installParam;
    private Integer installOrder;
    private String installDesc;
    private String uninstallStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getInstallParam() {
        return installParam;
    }

    public void setInstallParam(String installParam) {
        this.installParam = installParam;
    }

    public Integer getInstallOrder() {
        return installOrder;
    }

    public void setInstallOrder(Integer installOrder) {
        this.installOrder = installOrder;
    }

    public String getInstallDesc() {
        return installDesc;
    }

    public void setInstallDesc(String installDesc) {
        this.installDesc = installDesc;
    }

    public String getUninstallStr() {
        return uninstallStr;
    }

    public void setUninstallStr(String uninstallStr) {
        this.uninstallStr = uninstallStr;
    }
}
