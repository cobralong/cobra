// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.mgnt.controller.model.BundleIdStatusStartSizeParameter;

/**
 * @author luofei@appchina.com create date: 2016年3月25日
 *
 */
public class AppStoreClientAuditSwitchIpConfParameter extends BundleIdStatusStartSizeParameter {
    private Integer id;
    private String ip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "AppStoreClientAuditSwitchIpConfParameter [id=" + id + ", ip=" + ip + ", toString()=" + super.toString()
                + "]";
    }


}
