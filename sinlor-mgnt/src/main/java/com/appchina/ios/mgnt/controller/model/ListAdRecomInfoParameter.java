// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class ListAdRecomInfoParameter extends TimedStatusStartSizeParameter {
    private Integer id;
    private Integer listAdInfoId;
    private String channel;
    private String place;
    private Integer position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getListAdInfoId() {
        return listAdInfoId;
    }

    public void setListAdInfoId(Integer listAdInfoId) {
        this.listAdInfoId = listAdInfoId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ListAdRecomInfoParameter [id=" + id + ", listAdInfoId=" + listAdInfoId + ", channel=" + channel
                + ", place=" + place + ", position=" + position + ", toString()=" + super.toString() + "]";
    }

}
