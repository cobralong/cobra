// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.StatusType;


/**
 * @author luofei@appchina.com create date: 2016年3月9日
 *
 */
public class PcSuiteAppleDeviceConnectDriverInfoParamater extends StatusStartSizeParameter {
    private Integer id;
    private String system;// support windows system version
    private String systemDesc;//
    private String arch;// amd64/x86
    private Integer defaultFlag;// 在用户未找到自己版本号对应的驱动时自动对应的驱动 0 not default 1
    private Integer driverId;// reference PcSuiteAppleDeviceDriverInfo.id

    @Override
    public void transfer() {
        super.transfer();
        if (StringUtils.equals(system, String.valueOf(StatusType.STATUS_ALL.getStatus()))) {
            system = null;
        }
        if (StringUtils.equals(arch, String.valueOf(StatusType.STATUS_ALL.getStatus()))) {
            arch = null;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystemDesc() {
        return systemDesc;
    }

    public void setSystemDesc(String systemDesc) {
        this.systemDesc = systemDesc;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public Integer getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Integer defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        return "PcSuiteAppleDeviceConnectDriverInfoParamater [id=" + id + ", system=" + system + ", systemDesc="
                + systemDesc + ", arch=" + arch + ", defaultFlag=" + defaultFlag + ", driverId=" + driverId
                + ", toString()=" + super.toString() + "]";
    }

}
