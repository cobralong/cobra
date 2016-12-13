package com.appchina.ios.mgnt.controller.model.info;

import com.appchina.ios.core.dto.info.InfoType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouyanhui on 8/5/15.
 */
public class AppStorePromoteTabParameter {
    private String name;
    private int type;
    private int rank;
    private String color;
    private String icon;

    public static Map<String, String> PROMOTE_TAB_TYPES = new HashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 8579589099985477731L;

        {
            put(String.valueOf(InfoType.APP_LIST.getType()), InfoType.APP_LIST.getName());
            put(String.valueOf(InfoType.INFO_LIST.getType()), InfoType.INFO_LIST.getName());
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "AppStorePromoteTabParameter [name=" + name + ", type=" + type + ", rank=" + rank + ", color=" + color
                + ", icon=" + icon + "]";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
