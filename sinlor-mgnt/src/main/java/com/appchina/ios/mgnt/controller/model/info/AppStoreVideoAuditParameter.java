package com.appchina.ios.mgnt.controller.model.info;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;



/**
 * Created by zhouyanhui on 7/20/15.
 */
public class AppStoreVideoAuditParameter {
    private int id;
    private String videoImg;
    private String[] bundleIds;
    private String title;
    private int mainCategory;
    private Integer[] categoryIds;
    private String[] videoUrls;
    private String relateRootIds;
    private String content;

    public void transfer(boolean headVideoChecked, String headVideoUrl) {
        if (categoryIds == null || categoryIds.length == 0) {
            if (mainCategory != 0) {
                categoryIds = new Integer[] { mainCategory };
            }
        } else {

        }
        if (!StringUtils.isEmpty(relateRootIds)) {
            relateRootIds = relateRootIds.replaceAll("，", ",");
        }
        int reallyVideoUrlSize = headVideoChecked ? 1 : 0;
        for (int i = 0; i < videoUrls.length; i++) {
            if (!StringUtils.isEmpty(videoUrls[i])) {
                reallyVideoUrlSize++;
            }
        }
        String[] reallyVideoUrls = new String[reallyVideoUrlSize];
        if (headVideoChecked) {
            reallyVideoUrls[0] = headVideoUrl;
        }
        int reallyVideoUrlsIndex = 0;
        for (int i = 0; i < videoUrls.length; i++) {
            if (!StringUtils.isEmpty(videoUrls[i])) {
                reallyVideoUrls[reallyVideoUrlsIndex] = videoUrls[i];
                reallyVideoUrlsIndex++;
            }
            videoUrls[i] = null;
        }
        videoUrls = reallyVideoUrls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(int mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String[] getBundleIds() {
        return bundleIds;
    }

    public void setBundleIds(String[] bundleIds) {
        this.bundleIds = bundleIds;
    }

    public Integer[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Integer[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String[] getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(String[] videoUrls) {
        this.videoUrls = videoUrls;
    }

    public String getRelateRootIds() {
        return relateRootIds;
    }

    public void setRelateRootIds(String relateRootIds) {
        this.relateRootIds = relateRootIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AppStoreVideoAuditParameter [id=" + id + ", videoImg=" + videoImg + ", bundleIds="
                + Arrays.toString(bundleIds) + ", title=" + title + ", mainCategory=" + mainCategory + ", categoryIds="
                + Arrays.toString(categoryIds) + ", videoUrls=" + Arrays.toString(videoUrls) + ", relateRootIds="
                + relateRootIds + ", content=" + content + "]";
    }

}
