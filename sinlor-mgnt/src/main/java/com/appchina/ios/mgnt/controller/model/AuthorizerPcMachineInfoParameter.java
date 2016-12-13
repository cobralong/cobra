// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;


/**
 * @author luofei@appchina.com create date: 2016年3月28日
 *
 */
public class AuthorizerPcMachineInfoParameter extends StatusStartSizeParameter {
    private Integer id;
    private String osGuid;
    private String osName;
    private String kmachineIda;
    private String kmachineIdb;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOsGuid() {
        return osGuid;
    }

    public void setOsGuid(String osGuid) {
        this.osGuid = osGuid;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getKmachineIda() {
        return kmachineIda;
    }

    public void setKmachineIda(String kmachineIda) {
        this.kmachineIda = kmachineIda;
    }

    public String getKmachineIdb() {
        return kmachineIdb;
    }

    public void setKmachineIdb(String kmachineIdb) {
        this.kmachineIdb = kmachineIdb;
    }

    @Override
    public String toString() {
        return "AuthorizerPcMachineInfoParameter [id=" + id + ", osGuid=" + osGuid + ", osName=" + osName
                + ", kmachineIda=" + kmachineIda + ", kmachineIdb=" + kmachineIdb + ", toString()=" + super.toString()
                + "]";
    }

}
