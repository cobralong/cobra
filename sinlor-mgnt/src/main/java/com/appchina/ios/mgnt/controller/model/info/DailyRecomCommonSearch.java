// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.core.cahe.model.info.VideoInfoWrapper;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.info.StoreCategory;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class DailyRecomCommonSearch {
    private int id;
    private String mainTag;
    private String name;
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainTag() {
        return mainTag;
    }

    public void setMainTag(String mainTag) {
        this.mainTag = mainTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "DailyRecomCommonSearch [id=" + id + ", mainTag=" + mainTag + ", name=" + name + ", link=" + link + "]";
    }

    public static DailyRecomCommonSearch instance(ApplicationSimple application) {
        DailyRecomCommonSearch ret = new DailyRecomCommonSearch();
        ret.setId(application.getRootId());
        ret.setLink("http://ios.appchina.com/app/" + application.getRootId());
        ret.setMainTag(application.getCategoryName());
        ret.setName(application.getName());
        return ret;
    }

    public static DailyRecomCommonSearch instance(VideoInfoWrapper application) {
        DailyRecomCommonSearch ret = new DailyRecomCommonSearch();
        ret.setId(application.getVideoInfoId());
        ret.setMainTag(application.getMainCategory());
        ret.setName(application.getTitle());
        return ret;
    }

    public static DailyRecomCommonSearch instance(StoreCategory appTag) {
        DailyRecomCommonSearch ret = new DailyRecomCommonSearch();
        ret.setId(appTag.getId());
        ret.setLink(null);
        ret.setMainTag(appTag.getName());
        ret.setName(appTag.getName());
        return ret;
    }
}
