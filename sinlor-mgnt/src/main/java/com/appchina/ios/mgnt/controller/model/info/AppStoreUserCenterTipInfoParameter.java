package com.appchina.ios.mgnt.controller.model.info;

public class AppStoreUserCenterTipInfoParameter extends BundleIdTimedStatusStartSizeParameter {
    private Integer id;
    private Integer articleId;
    private String iconUrl;
    private Integer marketFlag;
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getMarketFlag() {
        return marketFlag;
    }

    public void setMarketFlag(Integer marketFlag) {
        this.marketFlag = marketFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AppStoreUserCenterTipInfoParameter [id=" + id + ", articleId=" + articleId + ", iconUrl=" + iconUrl
                + ", marketFlag=" + marketFlag + ", title=" + title + "]";
    }
}
