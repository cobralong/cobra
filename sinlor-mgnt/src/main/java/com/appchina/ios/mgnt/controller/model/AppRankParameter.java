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
public class AppRankParameter {
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


    public static final Map<String, String> ORDER_TYPE_MAPS = new LinkedHashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = -4718092716709087361L;
        {
            put("1", "更新时间");
            put("2", "价格");
            put("3", "用户评分");
            put("4", "应用热度");
            put("5", "苹果排行");
            put("6", "人工排行");
            put("7", "应用大小");
        }
    };
    private String category;
    private String categoryId;
    private String parentCategoryId;
    private String rid;
    private String sortType = "1";
    private String order = "DESC";
    private Pager pager = new Pager();
    // transfer
    private String orderByString = "id";;
    private String orderString;
    private int start;
    private int size;
    private Float hot;
    private Float appleTop;
    private Float manualTop;
    private Float userScore;

    // `promote`, `hot`, `apple_top`, `manual_top`,
    // `user_score`, `price`, `release_date`, `size`
    public void transfer() {
        // order by id
        if (category == null) {
            parentCategoryId = "100";
        } else {
            switch (category) {
                case "100":
                    parentCategoryId = "100";
                    categoryId = null;
                    break;
                case "200":
                    parentCategoryId = "200";
                    categoryId = null;
                    break;
                default:
                    parentCategoryId = null;
                    categoryId = category;
                    break;
            }
        }
        if (sortType == null) {
            orderByString = "release_date";
        } else {
            switch (sortType) {
                case "1":
                    orderByString = "release_date";
                    break;
                case "2":
                    orderByString = "price";
                    break;
                case "3":
                    orderByString = "user_score";
                    userScore = 0f;
                    break;
                case "4":
                    orderByString = "hot";
                    hot = 0f;
                    break;
                case "5":
                    orderByString = "apple_top";
                    appleTop = 0f;
                    break;
                case "6":
                    orderByString = "manual_top";
                    manualTop = 0f;
                    break;
                case "7":
                    orderByString = "size";
                    break;
                default:
                    orderByString = "release_date";
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

    @Override
    public String toString() {
        return "AppRankParameter [rid=" + rid + ", sortType=" + sortType + ", order=" + order + ", pager=" + pager
                + ", orderByString=" + orderByString + ", orderString=" + orderString + ", start=" + start + ", size="
                + size + "]";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Float getHot() {
        return hot;
    }

    public void setHot(Float hot) {
        this.hot = hot;
    }

    public Float getAppleTop() {
        return appleTop;
    }

    public void setAppleTop(Float appleTop) {
        this.appleTop = appleTop;
    }

    public Float getManualTop() {
        return manualTop;
    }

    public void setManualTop(Float manualTop) {
        this.manualTop = manualTop;
    }

    public Float getUserScore() {
        return userScore;
    }

    public void setUserScore(Float userScore) {
        this.userScore = userScore;
    }

}
