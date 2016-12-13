// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.mgnt.controller.model.info.BundleIdTimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class ClientApnCertInfoParameter extends BundleIdTimedStatusStartSizeParameter {
    private Integer id;
    private String testCertFile;
    private String productCertFile;
    private String testCertPwd;
    private String productCertPwd;
    private Integer testPoolSize;
    private Integer productPoolSize;

    public String getTestCertFile() {
        return testCertFile;
    }

    public void setTestCertFile(String testCertFile) {
        this.testCertFile = testCertFile;
    }

    public String getProductCertFile() {
        return productCertFile;
    }

    public void setProductCertFile(String productCertFile) {
        this.productCertFile = productCertFile;
    }

    public String getTestCertPwd() {
        return testCertPwd;
    }

    public void setTestCertPwd(String testCertPwd) {
        this.testCertPwd = testCertPwd;
    }

    public String getProductCertPwd() {
        return productCertPwd;
    }

    public void setProductCertPwd(String productCertPwd) {
        this.productCertPwd = productCertPwd;
    }

    public Integer getTestPoolSize() {
        return testPoolSize;
    }

    public void setTestPoolSize(Integer testPoolSize) {
        this.testPoolSize = testPoolSize;
    }

    public Integer getProductPoolSize() {
        return productPoolSize;
    }

    public void setProductPoolSize(Integer productPoolSize) {
        this.productPoolSize = productPoolSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
