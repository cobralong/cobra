// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.core.dto.app.Category;
import com.appchina.ios.core.dto.app.EnterpriseIpaFile;
import com.appchina.ios.core.dto.app.IpaPlist;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class IpaPlistParameter extends PlistParameter {
    private Integer itemId;
    private String bundleId;
    private String name;
    private Integer type;
    private Float price;
    private Integer categoryId;
    private Integer intervene;
    private Integer ipaSiteId;
    private String orderBy = "update_time";

    public static final Map<String, String> TYPES = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -5110661221727298515L;

        {
            put("-1", "全部");
            put(String.valueOf(EnterpriseIpaFile.TYPE_TMP), "爬虫");
            put(String.valueOf(EnterpriseIpaFile.TYPE_EDITOR), "编辑");
            put(String.valueOf(EnterpriseIpaFile.TYPE_ONLINE), "上架系统");
        }
    };


    public static final Map<String, String> ORDERBYS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -5110661221727298515L;

        {
            put("create_time", "创建时间倒序");
            put("update_time", "更新时间倒序");
        }
    };

    public static final Map<String, String> CATES = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -5110661221727298515L;

        {
            put("-1", "全部"); // All game
            putAll(Category.PARENTS);
        }
    };
    public static final Map<String, String> INTERVENES = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -5110661221727298515L;

        {
            put("-2", "全部"); // All game
            put(String.valueOf(IpaPlist.INTERVENE_YES), "干预");
            put(String.valueOf(IpaPlist.INTERVENE_NO), "未干预");
        }
    };


    public void transfer() {
        type = type == null || type == -1 ? null : type;
        ipaSiteId = (ipaSiteId == null || ipaSiteId.intValue() <= 0) ? null : ipaSiteId;
        categoryId = categoryId == null || categoryId == -1 ? null : categoryId;
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
    }


    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "IpaPlistParameter [itemId=" + itemId + ", bundleId=" + bundleId + ", name=" + name + ", type=" + type
                + ", price=" + price + ", categoryId=" + categoryId + ", intervene=" + intervene + "]";
    }


    public Integer getIntervene() {
        return intervene;
    }


    public void setIntervene(Integer intervene) {
        this.intervene = intervene;
    }


    public Integer getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


    public static Map<String, String> getCates() {
        return CATES;
    }


    public String getOrderBy() {
        return orderBy;
    }


    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }


    public Integer getIpaSiteId() {
        return ipaSiteId;
    }


    public void setIpaSiteId(Integer ipaSiteId) {
        this.ipaSiteId = ipaSiteId;
    }
}
