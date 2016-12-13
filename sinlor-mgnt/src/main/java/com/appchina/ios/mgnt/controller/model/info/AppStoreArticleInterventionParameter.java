package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.dto.info.AppStoreArticleRecomIntervention;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * Created by zhouyanhui on 8/25/15.
 */
public class AppStoreArticleInterventionParameter extends StatusStartSizeParameter {
    private String bundleId;
    private Integer articleId;
    private Integer position;
    private Integer id;

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getArticleId() {
        return articleId;
    }


    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    @Override
    public void transfer() {
        super.transfer();
        if (status == null) {
            status = AppStoreArticleRecomIntervention.STATUS_OK;
        }
    }

    @Override
    public void transfer(int size) {
        super.transfer(size);
        if (status == null || status.intValue() == DbStatus.STATUS_ALL) {
            status = AppStoreArticleRecomIntervention.STATUS_OK;
        }
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
