// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com create date: 2016年3月17日
 *
 */
public class AppleDeviceBgModelParameter {
    private String color;// alias rgb
    private String platform;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "AppleDeviceBgModelParameter [color=" + color + ", platform=" + platform + "]";
    }
}
