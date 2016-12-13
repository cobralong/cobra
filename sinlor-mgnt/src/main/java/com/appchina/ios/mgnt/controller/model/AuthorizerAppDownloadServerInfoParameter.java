// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com create date: 2016年4月15日
 *
 */
public class AuthorizerAppDownloadServerInfoParameter extends StatusStartSizeParameter {
    private Integer id;
    private String url;
    private String name;
    private String testUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestUrl() {
        return testUrl;
    }

    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }

    @Override
    public String toString() {
        return "AuthorizerAppDownloadServerInfoParameter [id=" + id + ", url=" + url + ", name=" + name + ", testUrl="
                + testUrl + ", toString()=" + super.toString() + "]";
    }

}
