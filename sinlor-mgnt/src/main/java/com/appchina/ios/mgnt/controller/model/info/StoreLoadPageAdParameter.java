package com.appchina.ios.mgnt.controller.model.info;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.info.AppStoreClientInfo;


/**
 * Created by zhouyanhui on 8/11/15.
 */
public class StoreLoadPageAdParameter extends TimedStatusStartSizeParameter {
    private Integer id;
    private String bundleId;
    private String rootId;
    private Integer itemId;
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

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

}
