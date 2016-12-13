// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class IpaLoadStatParameter extends TimedStatusStartSizeParameter {
    private Integer marketId;

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    @Override
    public void transfer() {
        super.transfer();
        if (marketId != null && marketId == -2) {
            marketId = null;
        }
    }

    @Override
    public void transfer(int pageSize) {
        super.transfer(pageSize);
        transfer();
    }
}
