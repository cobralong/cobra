// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.appchina.ios.core.dto.info.AppStoreClientAuditSwitchIpConf;
import com.appchina.ios.core.dto.info.AppStoreClientShareInfo;
import com.appchina.ios.core.utils.ReturnDataHandleUtils;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientAuditSwitchIpConfParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientShareInfoParameter;
import com.appchina.ios.mgnt.service.AppStoreClientApiService;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * @author luofei@appchina.com create date: 2016年3月25日
 *
 */
@Service("appStoreClientApiService")
public class AppStoreClientApiServiceImpl implements AppStoreClientApiService {
    @Value("${ios.mgnt.api.listappstoreclientauditswitchipconfurl}")
    private String listAppStoreClientAuditSwitchIpConfUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/store/admin/client/audit/switch/ip/conf/list.json";
    @Value("${ios.mgnt.api.addappstoreclientauditswitchipconfurl}")
    private String addAppStoreClientAuditSwitchIpConfUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/store/admin/client/audit/switch/ip/conf/add.json";
    @Value("${ios.mgnt.api.modifyappstoreclientauditswitchipconfurl}")
    private String modifyAppStoreClientAuditSwitchIpConfUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/store/admin/client/audit/switch/ip/conf/modify.json";

    @Value("${ios.mgnt.api.listappstoreclientshareinfourl}")
    private String listAppStoreClientShareInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/store/admin/client/share/info/list.json";
    @Value("${ios.mgnt.api.detailappstoreclientshareinfourl}")
    private String detailAppStoreClientShareInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/store/admin/client/share/info/detail.json";
    @Value("${ios.mgnt.api.addappstoreclientshareinfourl}")
    private String addAppStoreClientShareInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/store/admin/client/share/info/add.json";
    @Value("${ios.mgnt.api.modifyappstoreclientshareinfourl}")
    private String modifyAppStoreClientShareInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/store/admin/client/share/info/modify.json";


    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreClientAuditSwitchIpConf>> listAppStoreClientAuditSwitchIpConf(
            AppStoreClientAuditSwitchIpConfParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAppStoreClientAuditSwitchIpConfUrl, para,
                ReturnDataHandleUtils.APPSTORECLIENTAUDITSWITCHIPCONF_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreClientAuditSwitchIpConf(AppStoreClientAuditSwitchIpConfParameter g)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(addAppStoreClientAuditSwitchIpConfUrl, g);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreClientAuditSwitchIpConf(AppStoreClientAuditSwitchIpConfParameter g)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAppStoreClientAuditSwitchIpConfUrl, g);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreClientShareInfo>> listAppStoreClientShareInfo(
            AppStoreClientShareInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAppStoreClientShareInfoUrl, para,
                ReturnDataHandleUtils.APPSTORECLIENTSHAREINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreClientShareInfo(AppStoreClientShareInfoParameter g)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(addAppStoreClientShareInfoUrl, g);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreClientShareInfo(AppStoreClientShareInfoParameter g)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAppStoreClientShareInfoUrl, g);
    }

    @Override
    public ApiRespWrapper<AppStoreClientShareInfo> detailAppStoreClientShareInfo(int id) throws ServiceException {
        return RemoteDataUtil.get(detailAppStoreClientShareInfoUrl, id, ParametersHandle.PS_ID_HANDLE,
                ReturnDataHandleUtils.APPSTORECLIENTSHAREINFO_RD_HANDLE);
    }
}
