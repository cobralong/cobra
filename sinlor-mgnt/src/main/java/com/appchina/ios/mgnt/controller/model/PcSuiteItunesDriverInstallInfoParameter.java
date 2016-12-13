// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import java.util.Arrays;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class PcSuiteItunesDriverInstallInfoParameter extends StatusStartSizeParameter {
    private Integer id;
    private Integer driverId;
    private String[] names;
    private String[] params;
    private Integer[] indexes;
    private Integer status;

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer[] getIndexes() {
        return indexes;
    }

    public void setIndexes(Integer[] indexes) {
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return "PcSuiteItunesDriverInstallInfoParameter [id=" + id + ", driverId=" + driverId + ", names="
                + Arrays.toString(names) + ", params=" + Arrays.toString(params) + ", indexes="
                + Arrays.toString(indexes) + ", status=" + status + "]";
    }

}
