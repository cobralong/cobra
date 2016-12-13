// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class EnterpriseFilePathParameter {
    private String sign;
    private String md5;

    public EnterpriseFilePathParameter(String sign, String md5) {
        super();
        this.sign = sign;
        this.md5 = md5;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "EnterpriseFilePathParameter [sign=" + sign + ", md5=" + md5 + "]";
    }
}
