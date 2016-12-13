// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.StatusType;

/**
 * @author luofei@appchina.com create date: 2016年3月16日
 *
 */
public class PcSuiteAppleDeviceDriverInfoParameter extends StatusStartSizeParameter {
    private Integer id;
    private String name;
    private String arch;
    private String desc;
    private String version;// the zip version may equal to itunes version
    private String itunesVersion;// the detail itunes install version
    private String url;
    private String md5;

    @Override
    public void transfer() {
        super.transfer();
        if (StringUtils.equals(String.valueOf(StatusType.STATUS_ALL.getStatus()), arch)) {
            arch = null;
        }
    }

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

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getItunesVersion() {
        return itunesVersion;
    }

    public void setItunesVersion(String itunesVersion) {
        this.itunesVersion = itunesVersion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "PcSuiteAppleDeviceDriverInfoParameter [id=" + id + ", name=" + name + ", arch=" + arch + ", desc="
                + desc + ", version=" + version + ", itunesVersion=" + itunesVersion + ", url=" + url + ", md5=" + md5
                + ", toString()=" + super.toString() + "]";
    }

}
