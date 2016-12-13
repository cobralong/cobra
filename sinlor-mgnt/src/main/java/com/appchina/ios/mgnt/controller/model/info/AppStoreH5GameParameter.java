package com.appchina.ios.mgnt.controller.model.info;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.info.AppStoreClientInfo;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * Created by zhouyanhui on 7/20/15.
 */
public class AppStoreH5GameParameter extends StatusStartSizeParameter {
    private String bundleId;
    private Integer id;
    private String name;
    private String icon;
    private String desc;
    private String url;
    private Integer click;

    @Override
    public void transfer() {
        super.transfer();
        if (StringUtils.equalsIgnoreCase(bundleId, AppStoreClientInfo.ALL_BUNDLE)) {
            bundleId = null;
        }
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    @Override
    public String toString() {
        return "AppStoreH5GameParameter [bundleId=" + bundleId + ", id=" + id + ", name=" + name + ", icon=" + icon
                + ", desc=" + desc + ", url=" + url + ", click=" + click + ", toString()=" + super.toString() + "]";
    }

}
