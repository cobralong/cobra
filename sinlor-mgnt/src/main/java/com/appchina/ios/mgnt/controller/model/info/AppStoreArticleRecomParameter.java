package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * Created by zhouyanhui on 7/20/15.
 */
public class AppStoreArticleRecomParameter extends StatusStartSizeParameter {
    private String bundleId;
    private Integer articleId;

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "AppStoreArticleRecomParameter [bundleId=" + bundleId + ", articleId=" + articleId + ", toString()="
                + super.toString() + "]";
    }

}
