package com.appchina.ios.mgnt.controller.model.funny;

import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;


public class FunnyClientKeywordSearchParameter extends TimedStatusStartSizeParameter {

    private int id;
    private int rootId;
    private String keyword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
