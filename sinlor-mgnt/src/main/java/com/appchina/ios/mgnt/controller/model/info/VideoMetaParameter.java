// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class VideoMetaParameter extends StatusStartSizeParameter {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "VideoMetaParameter [id=" + id + ", toString()=" + super.toString() + "]";
    }

}
