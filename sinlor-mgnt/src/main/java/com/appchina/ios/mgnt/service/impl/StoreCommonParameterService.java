// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.dto.info.AppStoreClientAuditInfo;
import com.appchina.ios.core.dto.info.AppStoreClientInfo;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientAuditInfoParamter;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.StartSizeParameter;
import com.appchina.ios.web.utils.CollectionUtils;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
@Service("storeCommonParameterService")
public class StoreCommonParameterService {
    @Autowired
    private IosStoreApiService iosStoreApiService;

    public LinkedHashMap<String, String> addBundles(String key, Model model, Boolean... notAll) {
        ApiRespWrapper<ListWrapResp<AppStoreClientInfo>> clientResp = iosStoreApiService
                .listAppStoreClientInfo(new StartSizeParameter(Integer.MAX_VALUE));
        if (clientResp != null && clientResp.getData() != null
                && !CollectionUtils.emptyOrNull(clientResp.getData().getResultList())) {
            LinkedHashMap<String, String> bundleIds = new LinkedHashMap<String, String>();
            if (CollectionUtils.emptyOrNull(notAll) || notAll[0] == null || !notAll[0].booleanValue()) {
                bundleIds.put(AppStoreClientInfo.ALL_BUNDLE, "全部产品");
            }
            for (AppStoreClientInfo appStoreClientInfo : clientResp.getData().getResultList()) {
                bundleIds.put(appStoreClientInfo.getBundleId(), appStoreClientInfo.getName());
            }
            model.addAttribute(key, bundleIds);
            return bundleIds;
        }
        return null;
    }

    /**
     * 返回所有的产品的审核信息
     * 
     * @param key
     * @param model
     */
    public void addBundleIdAuditInfo(String key, Model model) {
        AppStoreClientAuditInfoParamter innerPara = new AppStoreClientAuditInfoParamter();
        innerPara.getPager().setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<AppStoreClientAuditInfo>> bundleIdAuditInfoResp = iosStoreApiService
                .listAppStoreClientAuditInfo(innerPara);
        if (bundleIdAuditInfoResp != null && bundleIdAuditInfoResp.getData() != null
                && !CollectionUtils.emptyOrNull(bundleIdAuditInfoResp.getData().getResultList())) {
            model.addAttribute(key, bundleIdAuditInfoResp.getData().getResultList());
        }
    }

    public void addStatus(String key, Model model) {
        model.addAttribute(key, DbStatus.STATUS);
    }
}
