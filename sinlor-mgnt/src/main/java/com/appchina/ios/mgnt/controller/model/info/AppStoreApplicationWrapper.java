// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.app.Author;
import com.appchina.ios.core.dto.app.Category;
import com.appchina.ios.core.dto.app.MarketInfo;
import com.appchina.ios.core.utils.AppStoreApplicationUtils;

/**
 * @author
 *
 */
public class AppStoreApplicationWrapper {
    /**
     * 应用名称
     */
    private String title;
    private String editorTitle;
    private int id;
    private String bundleId;
    private int rootId;
    /**
     * 作者信息
     */
    private Author author;
    private String icon;
    /**
     * 特殊支持
     */
    private String specialSupport;
    private String priceSymbol;
    /**
     * 价格
     */
    private float price;
    private float originPrice;
    /**
     * 更新日期
     */
    private Date releaseDate;
    /**
     * 软件大小
     */
    private String version;
    /**
     * 软件大小
     */
    private long size;
    private double realSize;
    /**
     * 支持语言
     */
    private String language;
    /**
     * 兼容性
     */
    private String requirements;
    private float score;
    private int scoreCount;
    /**
     * 内容提要
     */
    private String content;
    /**
     * 版本新功能
     */
    private String productReview;
    /**
     * 截图
     */
    private List<String> screens;
    /**
     * 应用所属分类
     */
    private Category category;

    private String downloadUrl;
    private String appStoreUrl;
    private String shortDesc;
    // 特别提醒
    private String editorReview;
    private int downloadCount;
    private MarketInfo marketInfo = new MarketInfo(1, "苹果官方市场", "https://itunes.apple.com/cn/genre/ios/id36?mt=8");
    private String detailUrl;
    private Integer shareAccountId;
    private int favorite;
    private boolean authorizerIpa;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSpecialSupport() {
        return specialSupport;
    }

    public void setSpecialSupport(String specialSupport) {
        this.specialSupport = specialSupport;
    }

    public String getPriceSymbol() {
        return priceSymbol;
    }

    public void setPriceSymbol(String priceSymbol) {
        this.priceSymbol = priceSymbol;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProductReview() {
        return productReview;
    }

    public void setProductReview(String productReview) {
        this.productReview = productReview;
    }

    public List<String> getScreens() {
        return screens;
    }

    public void setScreens(List<String> screens) {
        this.screens = screens;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getEditorReview() {
        return editorReview;
    }

    public void setEditorReview(String editorReview) {
        this.editorReview = editorReview;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public MarketInfo getMarketInfo() {
        return marketInfo;
    }

    public void setMarketInfo(MarketInfo marketInfo) {
        this.marketInfo = marketInfo;
    }

    public String getAppStoreUrl() {
        return appStoreUrl;
    }

    public void setAppStoreUrl(String appStoreUrl) {
        this.appStoreUrl = appStoreUrl;
    }


    public String getItunesUrl() {
        return appStoreUrl;
    }

    public int getAppStoreId() {
        if (StringUtils.isEmpty(appStoreUrl)) {
            return -1;
        }
        return AppStoreApplicationUtils.getAppId(appStoreUrl);
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }


    public String getEditorTitle() {
        return editorTitle;
    }

    public void setEditorTitle(String editorTitle) {
        this.editorTitle = editorTitle;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDetailUrl() {
        return "http://ios.appchina.com/app/" + rootId + "/";
    }

    public float getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(float originPrice) {
        this.originPrice = originPrice;
    }

    public Integer getShareAccountId() {
        return shareAccountId;
    }

    public void setShareAccountId(Integer shareAccountId) {
        this.shareAccountId = shareAccountId;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "ApplicationWrapper [title=" + title + ", editorTitle=" + editorTitle + ", id=" + id + ", bundleId="
                + bundleId + ", rootId=" + rootId + ", author=" + author + ", icon=" + icon + ", specialSupport="
                + specialSupport + ", priceSymbol=" + priceSymbol + ", price=" + price + ", originPrice=" + originPrice
                + ", releaseDate=" + releaseDate + ", version=" + version + ", size=" + size + ", language=" + language
                + ", requirements=" + requirements + ", score=" + score + ", scoreCount=" + scoreCount + ", content="
                + content + ", productReview=" + productReview + ", screens=" + screens + ", category=" + category
                + ", downloadUrl=" + downloadUrl + ", appStoreUrl=" + appStoreUrl + ", shortDesc=" + shortDesc
                + ", editorReview=" + editorReview + ", downloadCount=" + downloadCount + ", marketInfo=" + marketInfo
                + ", detailUrl=" + detailUrl + ", shareAccountId=" + shareAccountId + ", favorite=" + favorite
                + ", authorizerIpa=" + authorizerIpa + ", toString()=" + super.toString() + "]";
    }

    public boolean isAuthorizerIpa() {
        return authorizerIpa;
    }

    public void setAuthorizerIpa(boolean authorizerIpa) {
        this.authorizerIpa = authorizerIpa;
    }

    public double getRealSize() {
        return realSize;
    }

    public void setRealSize(double realSize) {
        this.realSize = realSize;
    }

}
