// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import java.io.Serializable;
import java.util.List;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.info.VideoInfo;
import com.appchina.ios.core.dto.info.VideoMetaData;
import com.appchina.ios.web.utils.CollectionUtils;
import com.appchina.ios.web.utils.GsonUtils;


/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class VideoInfoModel extends DbStatus implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -580387113885162679L;
    public static final int STATUS_INIT = 1;
    private int videoInfoId;
    private String title;
    private String videoImg;
    private int mainCategory;
    private long videoSize;
    private int videoDuration;
    private int views;
    private int likes;
    private int hates;
    private String desc;
    private String videoUrl;// list<String> to a,b,c,d
    private String relateRootIds;// List<String> to a,b,c,d
    private List<VideoMetaData> metas;
    private List<ApplicationSimple> apps;

    public static VideoInfoModel newInstance(VideoInfo videoInfo, List<VideoMetaData> meats,
            List<ApplicationSimple> apps) {
        VideoInfoModel ret = new VideoInfoModel();
        ret.videoInfoId = videoInfo.getVideoInfoId();
        ret.title = videoInfo.getTitle();
        ret.videoImg = videoInfo.getVideoImg();
        ret.mainCategory = videoInfo.getMainCategory();
        try {
            ret.videoUrl = CollectionUtils.listToString(GsonUtils.fromJsonStrToStrList(videoInfo.getVideoUrl()), ",");
        } catch (Exception e) {
        }
        try {
            ret.relateRootIds = CollectionUtils.listToString(
                    GsonUtils.fromJsonStrToStrList(videoInfo.getRelateRootIds()), ",");
        } catch (Exception e) {
        }
        ret.videoDuration = videoInfo.getVideoDuration();
        ret.videoSize = videoInfo.getVideoSize();
        ret.desc = videoInfo.getDesc();
        ret.views = videoInfo.getViews();
        ret.likes = videoInfo.getLikes();
        ret.hates = videoInfo.getHates();
        ret.metas = meats;
        ret.apps = apps;
        return ret;
    }

    public int getVideoInfoId() {
        return videoInfoId;
    }

    public void setVideoInfoId(int videoInfoId) {
        this.videoInfoId = videoInfoId;
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

    public long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(long videoSize) {
        this.videoSize = videoSize;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getHates() {
        return hates;
    }

    public void setHates(int hates) {
        this.hates = hates;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getRelateRootIds() {
        return relateRootIds;
    }

    public void setRelateRootIds(String relateRootIds) {
        this.relateRootIds = relateRootIds;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public List<VideoMetaData> getMetas() {
        return metas;
    }

    public void setMetas(List<VideoMetaData> metas) {
        this.metas = metas;
    }

    public List<ApplicationSimple> getApps() {
        return apps;
    }

    public void setApps(List<ApplicationSimple> apps) {
        this.apps = apps;
    }

    @Override
    public String toString() {
        return "VideoInfoModel [videoInfoId=" + videoInfoId + ", title=" + title + ", videoImg=" + videoImg
                + ", mainCategory=" + mainCategory + ", videoSize=" + videoSize + ", videoDuration=" + videoDuration
                + ", views=" + views + ", likes=" + likes + ", hates=" + hates + ", desc=" + desc + ", videoUrl="
                + videoUrl + ", relateRootIds=" + relateRootIds + ", metas=" + metas + ", apps=" + apps + "]";
    }

}
