package com.appchina.ios.mgnt.controller.model.info;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * Created by zhouyanhui on 7/31/15.
 */
public class TimedStatusStartSizeParameter extends StatusStartSizeParameter {
    private String st;
    private String et;

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public void transfer() {
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
        if (StringUtils.isEmpty(st)) {
            st = DateUtils.getDeltaNow(-356);
        }
        if (StringUtils.isEmpty(et)) {
            et = DateUtils.getDeltaNow(356);
        }
    }

    public void transfer(int pageSize) {
        pager.setSize(pageSize);
        transfer();
    }

}
