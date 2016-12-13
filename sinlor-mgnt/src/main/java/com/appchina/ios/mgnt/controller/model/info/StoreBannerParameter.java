package com.appchina.ios.mgnt.controller.model.info;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.info.AppStoreClientInfo;
import com.appchina.ios.core.dto.info.InfoType;

/**
 * Created by zhouyanhui on 7/31/15.
 */
public class StoreBannerParameter extends TimedStatusStartSizeParameter {
    public static final Map<String, String> ALL_TYPES = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -946142763137321572L;

        {
            put(String.valueOf(InfoType.INFO.getType()), InfoType.INFO.getName());
            put(String.valueOf(InfoType.APP.getType()), InfoType.APP.getName());
        }
    };

    @Override
    public void transfer(int size) {
        if (StringUtils.equals(bundleId, AppStoreClientInfo.ALL_BUNDLE)) {
            bundleId = null;
        }
        super.transfer(size);
    }

    @Override
    public void transfer() {
        if (StringUtils.equals(bundleId, AppStoreClientInfo.ALL_BUNDLE)) {
            bundleId = null;
        }
        super.transfer();
    }

    private Integer id;
    private int type;
    private int showInAudit;
    private Integer auditingPosition;
    private Integer auditedPosition;
    private String bundleId;
    private String bannerUrl;
    private String desc;
    private Integer rootId;
    private Integer articleId;
    private Integer refId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getShowInAudit() {
        return showInAudit;
    }

    public void setShowInAudit(int showInAudit) {
        this.showInAudit = showInAudit;
    }

    public Integer getAuditingPosition() {
        return auditingPosition;
    }

    public void setAuditingPosition(Integer auditingPosition) {
        this.auditingPosition = auditingPosition;
    }

    public Integer getAuditedPosition() {
        return auditedPosition;
    }

    public void setAuditedPosition(Integer auditedPosition) {
        this.auditedPosition = auditedPosition;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "StoreBannerParameter [id=" + id + ", type=" + type + ", showInAudit=" + showInAudit
                + ", auditingPosition=" + auditingPosition + ", auditedPosition=" + auditedPosition + ", bundleId="
                + bundleId + ", bannerUrl=" + bannerUrl + ", desc=" + desc + ", rootId=" + rootId + ", articleId="
                + articleId + ", toString()=" + super.toString() + "]";
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }
}
