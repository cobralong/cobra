package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;


/**
 * Created by zhouyanhui on 7/31/15.
 */
public class DailyRecomParameter extends StatusStartSizeParameter {
    private Integer id;
    private Integer recomId;
    private String bundleId;
    private Integer type;
    private String st;
    private String et;
    private String mainTag;
    private String img;
    private String title;
    private Integer showInAudit;
    private Integer auditingPosition;
    private Integer auditedPosition;

    public DailyRecomParameter() {
    }

    public DailyRecomParameter(int recomId, int type, String bundleId, String st, String et, String mainTag,
            String img, String title, int showInAudit, Integer auditingPosition, Integer auditedPosition) {
        super();
        this.recomId = recomId;
        this.type = type;
        this.bundleId = bundleId;
        this.st = st;
        this.et = et;
        this.mainTag = mainTag;
        this.img = img;
        this.title = title;
        this.showInAudit = showInAudit;
        this.auditingPosition = auditingPosition;
        this.auditedPosition = auditedPosition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecomId() {
        return recomId;
    }

    public void setRecomId(Integer recomId) {
        this.recomId = recomId;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public String getMainTag() {
        return mainTag;
    }

    public void setMainTag(String mainTag) {
        this.mainTag = mainTag;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getShowInAudit() {
        return showInAudit;
    }

    public void setShowInAudit(Integer showInAudit) {
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

    @Override
    public String toString() {
        return "DailyRecomParameter [id=" + id + ", recomId=" + recomId + ", bundleId=" + bundleId + ", type=" + type
                + ", st=" + st + ", et=" + et + ", mainTag=" + mainTag + ", img=" + img + ", title=" + title
                + ", showInAudit=" + showInAudit + ", auditingPosition=" + auditingPosition + ", auditedPosition="
                + auditedPosition + "]";
    }
}
