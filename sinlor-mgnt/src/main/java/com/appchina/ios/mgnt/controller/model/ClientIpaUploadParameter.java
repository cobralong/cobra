// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.StatusType;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class ClientIpaUploadParameter extends StatusStartSizeParameter {
    public static final int ISTEST = 1;
    public static final int ISPRODUCT = 0;
    public static final Map<String, String> ALL_STATUS = DbStatus.STATUS;
    public static final Map<String, String> ALL_TEST = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -5110661221727298515L;

        {
            put(String.valueOf(StatusType.STATUS_ALL.getStatus()), "全部");
            put(String.valueOf(ISTEST), "测试版");
            put(String.valueOf(ISPRODUCT), "发布版");
        }
    };
    private Integer test;
    private String upgradeInfo;
    private String channel;

    @Override
    public void transfer(int size) {
        super.transfer(size);
        if (test == null || test.intValue() == StatusType.STATUS_ALL.getStatus()) {
            test = null;
        }
    }

    @Override
    public void transfer() {
        super.transfer();
        if (test == null || test.intValue() == StatusType.STATUS_ALL.getStatus()) {
            test = null;
        }
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

    public String getUpgradeInfo() {
        return upgradeInfo;
    }

    public void setUpgradeInfo(String upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "ClientIpaUploadParameter [test=" + test + ", upgradeInfo=" + upgradeInfo + ", channel=" + channel + "]";
    }
}
