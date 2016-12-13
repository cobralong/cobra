package com.appchina.ios.mgnt.controller.model.funny;

import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;

/**
 * @author liuxinglong
 * @date 2016年10月18日
 */
public class WechatServiceAccountMenuParamter extends StatusStartSizeParameter {

    private String account;
    private String param;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
