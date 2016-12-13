// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.appchina.ios.core.utils.SQLUtils;
import com.appchina.ios.web.model.Pager;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class ApplicationParameter {
    public static final Map<String, String> ORDERS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 2640479043684126647L;

        {
            put("DESC", "降序");
            put("ASC", "升序");
        }
    };
    public static final Map<String, String> ORDER_TYPE = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 2640479043684126647L;

        {
            put("0", "Id");
            put("1", "更新时间");
            put("2", "价格");
            put("3", "大小");

        }
    };
    private String rid;
    private String name;
    private String startTime;
    private String endTime;
    private String minPrice;
    private String maxPrice;

    private String sortType;
    private String order;
    private Pager pager = new Pager();
    // transfer
    private String orderByString = "id";;
    private String orderString;
    private int start;
    private int size;

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

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
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

    @Override
    public String toString() {
        return "ApplicationParameter [rid=" + rid + ", name=" + name + ", st=" + startTime + ", et=" + endTime
                + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", sortType=" + sortType + ", order=" + order
                + "]";
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
