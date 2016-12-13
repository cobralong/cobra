// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class VideoInfoModifyParameter extends StatusStartSizeParameter {
    private Integer videoInfoId;
    private String title;
    private Integer mainCategory;
    private String relateRootIds;
    private String desc;
    private String videoImg;
    private Integer cateId;
    private String bundleId;

    @Override
    public void transfer() {
        if (mainCategory != null && mainCategory.intValue() == 0) {
            mainCategory = null;
        }
        super.transfer();
    }


    public Integer getVideoInfoId() {
        return videoInfoId;
    }

    public void setVideoInfoId(Integer videoInfoId) {
        this.videoInfoId = videoInfoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Integer mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getRelateRootIds() {
        return relateRootIds;
    }

    public void setRelateRootIds(String relateRootIds) {
        this.relateRootIds = relateRootIds;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    @Override
    public String toString() {
        return "VideoInfoModifyParameter [videoInfoId=" + videoInfoId + ", title=" + title + ", mainCategory="
                + mainCategory + ", relateRootIds=" + relateRootIds + ", desc=" + desc + ", videoImg=" + videoImg
                + ", status=" + status + ", cateId=" + cateId + ", bundleId=" + bundleId + "]";
    }
}
