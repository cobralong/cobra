// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.dto.app.EnterpriseIpaFile;



/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class IpaUploadListParameter extends StatusStartSizeParameter {
    private String filePath;
    private String md5;
    private Integer type;
    public static final Map<String, String> ALL_STATUS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -5110661221727298515L;

        {
            put(String.valueOf(StatusType.STATUS_ALL.getStatus()), "全部");
            put(String.valueOf(EnterpriseIpaFile.STATUS_INIT), "待解析");
            put(String.valueOf(EnterpriseIpaFile.STATUS_DEL), "解析失败");
            put(String.valueOf(EnterpriseIpaFile.STATUS_OK), "解析成功");
        }
    };
    public static final Map<String, String> TYPES = IpaPlistParameter.TYPES;


    @Override
    public void transfer(int size) {
        super.transfer(size);
        if (type == null || type.intValue() == -1) {
            type = null;
        }
    }

    @Override
    public void transfer() {
        super.transfer();
        if (type == null || type.intValue() == -1) {
            type = null;
        }
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IpaUploadListParameter [filePath=" + filePath + ", md5=" + md5 + ", type=" + type + "]";
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
