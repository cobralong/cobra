// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.mgnt.controller.model.BundleIdStatusStartSizeParameter;

/**
 * @author luofei@appchina.com create date: 2016年3月25日
 *
 */
public class AppStoreClientShareInfoParameter extends BundleIdStatusStartSizeParameter {
    private Integer id;
    private Integer showInType;
    private String title;
    private String content;
    private String url;
    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShowInType() {
        return showInType;
    }

    public void setShowInType(Integer showInType) {
        this.showInType = showInType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "AppStoreClientShareInfoParameter [id=" + id + ", showInType=" + showInType + ", title=" + title
                + ", content=" + content + ", url=" + url + ", icon=" + icon + ", toString()=" + super.toString() + "]";
    }

}
