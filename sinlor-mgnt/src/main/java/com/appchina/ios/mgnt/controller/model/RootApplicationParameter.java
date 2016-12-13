// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.appchina.ios.core.utils.SQLUtils;
import com.appchina.ios.web.model.Pager;


/**
 * @author yhx@refanqie.com (Your Name Here)
 *
 */
public class RootApplicationParameter {
    public static final Map<String, String> ORDERS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 6761387623396682980L;

        {
            put("DESC", "降序");
            put("ASC", "升序");
        }
    };
    public static final Map<String, String> ORDER_TYPE = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -8560097258332837655L;

        {
            put("0", "Id");
            put("1", "更新时间");
            put("2", "价格");
            put("3", "大小");

        }
    };
    private String rootId;
    private String title;

    private String sortType;
    private String order;
    private Pager pager = new Pager();
    // transfer
    private String orderByString = "id";;
    private String orderString;
    private int start;
    private int size;
    private Integer appType;

    public void transfer() {
        // order by id
        if (sortType == null) {
            orderByString = "id";
        } else {
            switch (sortType) {
                case "1":
                    orderByString = "update_time";
                    break;
                case "2":
                    orderByString = "price";
                    break;
                case "3":
                    orderByString = "size";
                    break;
                default:
                    orderByString = "id";
                    break;
            }
        }
        orderString = SQLUtils.formatOrder(order);
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
    }


    public String getRootId() {
        return rootId;
    }


    public void setRootId(String rootId) {
        this.rootId = rootId;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrderByString() {
        return orderByString;
    }

    public void setOrderByString(String orderByString) {
        this.orderByString = orderByString;
    }

    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    @Override
    public String toString() {
        return "RootApplicationParameter [rootId=" + rootId + ", title=" + title + ", sortType=" + sortType
                + ", order=" + order + ", pager=" + pager + ", orderByString=" + orderByString + ", orderString="
                + orderString + ", start=" + start + ", size=" + size + ", appType=" + appType + "]";
    }


}
