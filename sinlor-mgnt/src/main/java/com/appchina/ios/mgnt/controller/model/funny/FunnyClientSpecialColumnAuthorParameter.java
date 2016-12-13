package com.appchina.ios.mgnt.controller.model.funny;

import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;


public class FunnyClientSpecialColumnAuthorParameter extends StatusStartSizeParameter {

    private int id;
    private String userName;
    private String name;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
