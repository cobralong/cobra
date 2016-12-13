// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;



/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class PcSuiteIphoneModelParameter extends StatusStartSizeParameter {
    private Integer id;
    private Integer appleDeviceId;
    private String imgUrl;
    // CAL
    private Integer height;
    // CAL
    private Integer width;
    private Integer paddingLeft;
    private Integer paddingTop;
    private Integer paddingBottom;
    private Integer paddingRight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(Integer paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public Integer getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(Integer paddingTop) {
        this.paddingTop = paddingTop;
    }

    public Integer getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(Integer paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public Integer getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(Integer paddingRight) {
        this.paddingRight = paddingRight;
    }

    @Override
    public String toString() {
        return "PcSuiteIphoneModelParameter [id=" + id + ", appleDeviceId=" + appleDeviceId + ", imgUrl=" + imgUrl
                + ", height=" + height + ", width=" + width + ", paddingLeft=" + paddingLeft + ", paddingTop="
                + paddingTop + ", paddingBottom=" + paddingBottom + ", paddingRight=" + paddingRight + "]";
    }

    public Integer getAppleDeviceId() {
        return appleDeviceId;
    }

    public void setAppleDeviceId(Integer appleDeviceId) {
        this.appleDeviceId = appleDeviceId;
    }

}
