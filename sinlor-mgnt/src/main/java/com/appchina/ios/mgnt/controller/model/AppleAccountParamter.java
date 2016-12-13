// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class AppleAccountParamter extends StatusStartSizeParameter {
    private String email;
    private Integer source;
    private Boolean bind;
    private static final int ALL_SOURCE = -1;
    public static final Map<String, String> SOURCE_MAP = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -194643192057715513L;

        {
            put(String.valueOf(ALL_SOURCE), "全部渠道");
        }
    };
    public static final Map<String, String> BINDMAPS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -194643192057715513L;

        {
            put("false", "解除绑定");
            put("true", "已绑定");
        }
    };

    @Override
    public void transfer() {
        super.transfer();
        if (source != null && source.intValue() == ALL_SOURCE) {
            source = null;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Boolean getBind() {
        return bind;
    }

    public void setBind(Boolean bind) {
        this.bind = bind;
    }

}
