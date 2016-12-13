package com.appchina.ios.mgnt.controller.model.info;

/**
 * Created by zhouyanhui on 7/23/15.
 */
public class AppStoreArticleTagParameter {
    private int id;
    private String name;
    private Integer status;
    private String color;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }


    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
