// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class PcSuiteItunesDriverParameter extends StatusStartSizeParameter {
    private Integer id;
    private String system;
    private String desc;
    private String arch;
    private String pcsuiteVersion;
    private String url;
    private String md5;

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getPcsuiteVersion() {
        return pcsuiteVersion;
    }

    public void setPcsuiteVersion(String pcsuiteVersion) {
        this.pcsuiteVersion = pcsuiteVersion;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PcSuiteItunesDriverParameter [id=" + id + ", system=" + system + ", desc=" + desc + ", arch=" + arch
                + ", pcsuiteVersion=" + pcsuiteVersion + ", url=" + url + ", md5=" + md5 + ", toString()="
                + super.toString() + "]";
    }
}
