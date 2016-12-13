// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class UploadImgResp extends UploadFileResp {
    // CAL
    private int height;
    // CAL
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "UploadImgResp [height=" + height + ", width=" + width + ", toString()=" + super.toString() + "]";
    }

}
