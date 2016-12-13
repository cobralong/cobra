// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class OsVersionParameter {
    private String osVersion;
    private Integer defaultStatus;

    public OsVersionParameter(String osVersion, Integer defaultStatus) {
        super();
        this.osVersion = osVersion;
        this.defaultStatus = defaultStatus;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    @Override
    public String toString() {
        return "OsVersionParameter [osVersion=" + osVersion + ", defaultStatus=" + defaultStatus + "]";
    }

}
