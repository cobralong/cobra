// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.appchina.ios.core.cahe.model.app.DownloadTypeInfo;



/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class DownTypeInterventionParameter extends StatusStartSizeParameter {
    private Integer rootId;
    private String url;
    private Integer type;

    public static final Map<String, String> TYPES = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -9221566924993522166L;
        {
            put(String.valueOf(DownloadTypeInfo.APPSTORE.getType()), DownloadTypeInfo.APPSTORE.getDesc());
            put(String.valueOf(DownloadTypeInfo.OTA.getType()), DownloadTypeInfo.OTA.getDesc());
            put(String.valueOf(DownloadTypeInfo.ACCOUNT.getType()), DownloadTypeInfo.ACCOUNT.getDesc());
        }
    };

    public static final Map<String, String> ALL_TYPES = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -9221566924993522166L;
        {
            put(String.valueOf(DownloadTypeInfo.ALL.getType()), DownloadTypeInfo.ALL.getDesc());
            putAll(TYPES);
        }
    };

    @Override
    public void transfer() {
        this.type = (this.type == null || this.type.intValue() == DownloadTypeInfo.ALL.getType()) ? null : this.type;
        super.transfer();
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
