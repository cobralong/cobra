// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;


/**
 * @author luofei@appchina.com create date: 2016年3月29日
 *
 */
public class AuthorizerAppIpaParameter extends StatusStartSizeParameter {
    private Integer id;
    private Integer rootId;
    private String version;
    private String installIpa;
    private String installIpaMd5;
    private String ipaPlist;
    private String appleId;
    private Integer appleAccountId;
    private String pureIpa;
    private String pureIpaMd5;
    private String itunesMetadata;
    private String itunesArtwork;
    private String sinf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInstallIpa() {
        return installIpa;
    }

    public void setInstallIpa(String installIpa) {
        this.installIpa = installIpa;
    }

    public String getInstallIpaMd5() {
        return installIpaMd5;
    }

    public void setInstallIpaMd5(String installIpaMd5) {
        this.installIpaMd5 = installIpaMd5;
    }

    public String getIpaPlist() {
        return ipaPlist;
    }

    public void setIpaPlist(String ipaPlist) {
        this.ipaPlist = ipaPlist;
    }

    public String getAppleId() {
        return appleId;
    }

    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }

    public Integer getAppleAccountId() {
        return appleAccountId;
    }

    public void setAppleAccountId(Integer appleAccountId) {
        this.appleAccountId = appleAccountId;
    }

    public String getPureIpa() {
        return pureIpa;
    }

    public void setPureIpa(String pureIpa) {
        this.pureIpa = pureIpa;
    }

    public String getPureIpaMd5() {
        return pureIpaMd5;
    }

    public void setPureIpaMd5(String pureIpaMd5) {
        this.pureIpaMd5 = pureIpaMd5;
    }

    public String getItunesMetadata() {
        return itunesMetadata;
    }

    public void setItunesMetadata(String itunesMetadata) {
        this.itunesMetadata = itunesMetadata;
    }

    public String getItunesArtwork() {
        return itunesArtwork;
    }

    public void setItunesArtwork(String itunesArtwork) {
        this.itunesArtwork = itunesArtwork;
    }

    public String getSinf() {
        return sinf;
    }

    public void setSinf(String sinf) {
        this.sinf = sinf;
    }

    @Override
    public String toString() {
        return "AuthorizerAppIpaParameter [id=" + id + ", rootId=" + rootId + ", version=" + version + ", installIpa="
                + installIpa + ", installIpaMd5=" + installIpaMd5 + ", ipaPlist=" + ipaPlist + ", appleId=" + appleId
                + ", appleAccountId=" + appleAccountId + ", pureIpa=" + pureIpa + ", pureIpaMd5=" + pureIpaMd5
                + ", itunesMetadata=" + itunesMetadata + ", itunesArtwork=" + itunesArtwork + ", sinf=" + sinf
                + ", toString()=" + super.toString() + "]";
    }
}
