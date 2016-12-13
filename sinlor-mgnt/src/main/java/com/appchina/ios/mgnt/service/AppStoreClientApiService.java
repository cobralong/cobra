// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import com.appchina.ios.core.dto.info.AppStoreClientAuditSwitchIpConf;
import com.appchina.ios.core.dto.info.AppStoreClientShareInfo;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientAuditSwitchIpConfParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientShareInfoParameter;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * @author luofei@appchina.com create date: 2016年3月25日
 *
 */
public interface AppStoreClientApiService {
    ApiRespWrapper<ListWrapResp<AppStoreClientAuditSwitchIpConf>> listAppStoreClientAuditSwitchIpConf(
            AppStoreClientAuditSwitchIpConfParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAppStoreClientAuditSwitchIpConf(AppStoreClientAuditSwitchIpConfParameter g)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreClientAuditSwitchIpConf(AppStoreClientAuditSwitchIpConfParameter g)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreClientShareInfo>> listAppStoreClientShareInfo(
            AppStoreClientShareInfoParameter para) throws ServiceException;

    ApiRespWrapper<AppStoreClientShareInfo> detailAppStoreClientShareInfo(int id) throws ServiceException;

    ApiRespWrapper<Boolean> addAppStoreClientShareInfo(AppStoreClientShareInfoParameter g) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreClientShareInfo(AppStoreClientShareInfoParameter g) throws ServiceException;
}
