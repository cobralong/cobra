// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class PcSuiteIosProgrammerDriverParameter extends StatusStartSizeParameter {
    private Integer id;
    private String version;
    private String fileUrl;
    private String fileMd5;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    @Override
    public String toString() {
        return "PcSuiteIosProgrammerDriverProgrammer [id=" + id + ", version=" + version + ", fileUrl=" + fileUrl
                + ", fileMd5=" + fileMd5 + ", toString()=" + super.toString() + "]";
    }
}
