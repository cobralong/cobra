// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.info.AppStoreClientInfo;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class StoreCateRecomParameter extends StatusStartSizeParameter {
    private Integer id;
    private String bundleId;
    private String name;
    private String color;
    private Integer auditingPosition;
    private Integer auditedPosition;
    private Integer showInAudit;
    private Integer cateId;
    private Integer catePosition;
    private Integer type;

    @Override
    public void transfer() {
        if (StringUtils.equalsIgnoreCase(AppStoreClientInfo.ALL_BUNDLE, bundleId)) {
            bundleId = null;
        }
        super.transfer();
    }

    public StoreCateRecomParameter() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Integer getShowInAudit() {
        return showInAudit;
    }

    public void setShowInAudit(Integer showInAudit) {
        this.showInAudit = showInAudit;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getCatePosition() {
        return catePosition;
    }

    public void setCatePosition(Integer catePosition) {
        this.catePosition = catePosition;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "StoreCateRecomParameter [id=" + id + ", bundleId=" + bundleId + ", name=" + name + ", color=" + color
                + ", auditingPosition=" + auditingPosition + ", auditedPosition=" + auditedPosition + ", showInAudit="
                + showInAudit + ", cateId=" + cateId + ", catePosition=" + catePosition + ", type=" + type
                + ", toString()=" + super.toString() + "]";
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

}
