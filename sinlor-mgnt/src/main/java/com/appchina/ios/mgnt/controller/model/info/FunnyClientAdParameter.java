package com.appchina.ios.mgnt.controller.model.info;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.info.AppStoreClientInfo;


/**
 * 
 * @author liuxinglong
 * @date 2016年9月1日
 */
public class FunnyClientAdParameter extends TimedStatusStartSizeParameter {
    private Integer id;
    private String bundleId;
    private String imgUrl;

    @Override
    public void transfer() {
        if (StringUtils.equals(bundleId, AppStoreClientInfo.ALL_BUNDLE)) {
            bundleId = null;
        }
        super.transfer();
    }

    @Override
    public void transfer(int pageSize) {
        super.transfer(pageSize);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
