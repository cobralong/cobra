package com.appchina.ios.mgnt.controller.model.info;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.appchina.ios.core.dto.info.AppStoreArticle;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * Created by zhouyanhui on 7/20/15.
 */
public class AppStoreArticleParameter extends StatusStartSizeParameter {
    private List<String> bundleIdList;
    private String bundleId;
    private Integer id;
    private String title;
    private String articleUrl;
    private String iconUrl;
    private String shortDesc;
    private int tagId;
    private String relatedRootids;
    private String content;
    private String contentText;

    public static AppStoreArticleParameter instance(AppStoreArticle orig) {
        AppStoreArticleParameter ret = new AppStoreArticleParameter();
        try {
            BeanUtils.copyProperties(ret, orig);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return ret;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getRelatedRootids() {
        return relatedRootids;
    }

    public void setRelatedRootids(String relatedRootids) {
        this.relatedRootids = relatedRootids;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public List<String> getBundleIdList() {
        return bundleIdList;
    }

    public void setBundleIdList(List<String> bundleIdList) {
        this.bundleIdList = bundleIdList;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getBundleId() {
        return bundleId;
    }
}
