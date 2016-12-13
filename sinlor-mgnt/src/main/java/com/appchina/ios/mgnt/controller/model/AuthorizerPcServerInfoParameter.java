// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;


/**
 * @author luofei@appchina.com create date: 2016年3月28日
 *
 */
public class AuthorizerPcServerInfoParameter extends AuthorizerPcMachineInfoParameter {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AppleAuthorizerPcServerInfoParameter [url=" + url + ", toString()=" + super.toString() + "]";
    }

}
