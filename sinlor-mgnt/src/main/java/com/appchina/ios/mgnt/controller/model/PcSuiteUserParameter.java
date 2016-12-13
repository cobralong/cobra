// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com create date: 2016年3月30日
 *
 */
public class PcSuiteUserParameter extends TimedStatusStartSizeParameter {
    private Integer id;
    private String channel;
    private String pcsuiteVersion;
    private String arch;
    private String clientId;
    private String guid;
    private String system;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPcsuiteVersion() {
        return pcsuiteVersion;
    }

    public void setPcsuiteVersion(String pcsuiteVersion) {
        this.pcsuiteVersion = pcsuiteVersion;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "PcSuiteUserParameter [id=" + id + ", channel=" + channel + ", pcsuiteVersion=" + pcsuiteVersion
                + ", arch=" + arch + ", clientId=" + clientId + ", guid=" + guid + ", system=" + system
                + ", toString()=" + super.toString() + "]";
    }

}
