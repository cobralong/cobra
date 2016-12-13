// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model.info;

/**
 * 子推荐
 * 
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class SubReom {
    private int id;
    private String name;
    private int position;
    private boolean checked;

    public SubReom(int id, String name, int position, boolean checked) {
        super();
        this.id = id;
        this.name = name;
        this.position = position;
        this.checked = checked;
    }

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "SubReom [id=" + id + ", name=" + name + ", position=" + position + ", checked=" + checked + "]";
    }

}
