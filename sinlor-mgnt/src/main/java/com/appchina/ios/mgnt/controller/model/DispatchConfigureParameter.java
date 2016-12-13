// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.dto.account.DispatchConfigure;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class DispatchConfigureParameter extends StatusStartSizeParameter {
    private String channel;
    private String rc;
    private String fc;
    private String sc;
    private String lc;
    public static final int TOP_CHANNEL = -8888;
    public static final Map<String, String> TOP_CHANNELS = new HashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -8974718961770794498L;
        {
            put(String.valueOf(DbStatus.STATUS_ALL), "全部渠道");
            put(String.valueOf(TOP_CHANNEL), "顶级渠道");
        }
    };

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public void transfer() {
        super.transfer();
        if (StringUtils.equals(String.valueOf(DbStatus.STATUS_ALL), rc)) {
            channel = DispatchConfigure.ALL_CHANNEL;
        } else if (StringUtils.equals("-1", fc)) {
            channel = DispatchConfigure.ALL_CHANNEL;
        } else if (StringUtils.equals("-1", sc)) {
            channel = fc;
        } else if (StringUtils.equals("-1", lc)) {
            channel = sc;
        } else {
            if (!StringUtils.isEmpty(lc)) {
                channel = lc;
            } else if (!StringUtils.isEmpty(sc)) {
                channel = sc;
            } else if (!StringUtils.isEmpty(fc)) {
                channel = fc;
            } else if (!StringUtils.isEmpty(rc)) {
                channel = rc;
            }
        }
    }

    @Override
    public String toString() {
        return "DispatchConfigureParameter [channel=" + channel + "]";
    }

    public String getFc() {
        return fc;
    }

    public void setFc(String fc) {
        this.fc = fc;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }



}
