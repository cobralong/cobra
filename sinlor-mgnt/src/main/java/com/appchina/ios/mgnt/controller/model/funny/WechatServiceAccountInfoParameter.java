package com.appchina.ios.mgnt.controller.model.funny;

import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;


public class WechatServiceAccountInfoParameter extends StatusStartSizeParameter {

    private int id;
    private String serviceAccount;// 公众号
    private String appId;// 应用ID
    private String appSecret;// 应用密钥

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceAccount() {
        return serviceAccount;
    }

    public void setServiceAccount(String serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

}
