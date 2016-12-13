// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.appchina.ios.core.dto.account.AppleAuthorizerAccount;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountAuthDeviceInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountAuthPcMachineInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountAuthPcServerInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountBuyAppInfo;
import com.appchina.ios.core.utils.ReturnDataHandleUtils;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountAuthDeviceInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountAuthPcMachineInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountAuthPcServerInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountBuyAppInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountParameter;
import com.appchina.ios.mgnt.service.BackendAppleAccountApiService;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * @author luofei@appchina.com create date: 2016年3月23日
 *
 */
@Service("backendAppleAccountApiService")
public class BackendAppleAccountApiServiceImpl implements BackendAppleAccountApiService {
    @Value("${ios.mgnt.api.listappleauthorizeraccounturl}")
    private String listAppleAuthorizerAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/list.json";
    @Value("${ios.mgnt.api.listappleauthorizeraccountbyidsurl}")
    private String listAppleAuthorizerAccountByIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/list/byids.json";
    @Value("${ios.mgnt.api.detailappleauthorizeraccounturl}")
    private String detailAppleAuthorizerAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/detail.json";
    @Value("${ios.mgnt.api.addappleauthorizeraccounturl}")
    private String addAppleAuthorizerAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/add.json";
    @Value("${ios.mgnt.api.modifyappleauthorizeraccounturl}")
    private String modifyAppleAuthorizerAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/modify.json";


    @Value("${ios.mgnt.api.listappleauthorizeraccountbuyappinfourl}")
    private String listAppleAuthorizerAccountBuyAppInfoUrl = "http://127.0.0.1:8081/ios-backend/admin/apple/authorizer/account/buy/app/info/list.json";
    @Value("${ios.mgnt.api.listappleauthorizeraccountbuyappinfobyidsurl}")
    private String listAppleAuthorizerAccountBuyAppInfoByIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/buyapp/info/list/byids.json";
    @Value("${ios.mgnt.api.detailappleauthorizeraccountbuyappinfourl}")
    private String detailAppleAuthorizerAccountBuyAppInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/buy/app/info/detail.json";
    @Value("${ios.mgnt.api.addappleauthorizeraccountbuyappinfourl}")
    private String addAppleAuthorizerAccountBuyAppInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/buyapp/info/add.json";
    @Value("${ios.mgnt.api.modifyappleauthorizeraccountbuyappinfourl}")
    private String modifyAppleAuthorizerAccountBuyAppInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/buyapp/info/modify.json";

    @Value("${ios.mgnt.api.listappleauthorizeraccountauthdeviceinfourl}")
    private String listAppleAuthorizerAccountAuthDeviceInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/auth/device/info/list.json";
    @Value("${ios.mgnt.api.modifyappleauthorizeraccountauthdeviceinfourl}")
    private String modifyAppleAuthorizerAccountAuthDeviceInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/auth/device/info/modify.json";

    @Value("${ios.mgnt.api.listappleauthorizeraccountauthpcmachineinfourl}")
    private String listAppleAuthorizerAccountAuthPcMachineInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/auth/pc/machine/info/list.json";
    @Value("${ios.mgnt.api.modifyappleauthorizeraccountauthpcmachineinfourl}")
    private String modifyAppleAuthorizerAccountAuthPcMachineInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/auth/pc/machine/info/modify.json";

    @Value("${ios.mgnt.api.listappleauthorizeraccountauthpcserverinfourl}")
    private String listAppleAuthorizerAccountAuthPcServerInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/auth/pc/server/info/list.json";
    @Value("${ios.mgnt.api.modifyappleauthorizeraccountauthpcserverinfourl}")
    private String modifyAppleAuthorizerAccountAuthPcServerInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/apple/authorizer/account/auth/pc/server/info/modify.json";

    @Override
    public ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> listAppleAuthorizerAccount(
            AppleAuthorizerAccountParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAppleAuthorizerAccountUrl, para,
                ReturnDataHandleUtils.APPLEAUTHORIZERACCOUNT_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> listAppleAuthorizerAccount(List<Integer> ids)
            throws ServiceException {
        return RemoteDataUtil.get(listAppleAuthorizerAccountByIdsUrl, ids, ParametersHandle.IDS_ARRAY_PD_HANDLE,
                ReturnDataHandleUtils.APPLEAUTHORIZERACCOUNT_LIST_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccount(AppleAuthorizerAccountParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAppleAuthorizerAccountUrl, para);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppleAuthorizerAccount(AppleAuthorizerAccountParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(addAppleAuthorizerAccountUrl, para);
    }

    @Override
    public ApiRespWrapper<AppleAuthorizerAccount> detailAppleAuthorizerAccount(AppleAuthorizerAccountParameter para)
            throws ServiceException {
        return RemoteDataUtil.get(detailAppleAuthorizerAccountUrl, para,
                ReturnDataHandleUtils.APPLEAUTHORIZERACCOUNT_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountBuyAppInfo>> listAppleAuthorizerAccountBuyAppInfo(
            AppleAuthorizerAccountBuyAppInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAppleAuthorizerAccountBuyAppInfoUrl, para,
                ReturnDataHandleUtils.APPLEAUTHORIZERACCOUNTBUYAPPINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountBuyAppInfo>> listAppleAuthorizerAccountBuyAppInfo(
            List<Integer> ids) throws ServiceException {
        return RemoteDataUtil.get(listAppleAuthorizerAccountBuyAppInfoByIdsUrl, ids,
                ReturnDataHandleUtils.APPLEAUTHORIZERACCOUNTBUYAPPINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppleAuthorizerAccountBuyAppInfo> detailAppleAuthorizerAccountBuyAppInfo(
            AppleAuthorizerAccountBuyAppInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(detailAppleAuthorizerAccountBuyAppInfoUrl, para,
                ReturnDataHandleUtils.APPLEAUTHORIZERACCOUNTBUYAPPINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppleAuthorizerAccountBuyAppInfo(AppleAuthorizerAccountBuyAppInfoParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(addAppleAuthorizerAccountBuyAppInfoUrl, para);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountBuyAppInfo(AppleAuthorizerAccountBuyAppInfoParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAppleAuthorizerAccountBuyAppInfoUrl, para);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountAuthDeviceInfo>> listAppleAuthorizerAccountAuthDeviceInfo(
            AppleAuthorizerAccountAuthDeviceInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAppleAuthorizerAccountAuthDeviceInfoUrl, para,
                ReturnDataHandleUtils.APPLEAUTHORIZERACCOUNTAUTHDEVICEINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountAuthDeviceInfo(
            AppleAuthorizerAccountAuthDeviceInfoParameter para) throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAppleAuthorizerAccountAuthDeviceInfoUrl, para);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountAuthPcServerInfo>> listAppleAuthorizerAccountAuthPcServerInfo(
            AppleAuthorizerAccountAuthPcServerInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAppleAuthorizerAccountAuthPcServerInfoUrl, para,
                ReturnDataHandleUtils.APPLEAUTHORIZERACCOUNTAUTHPCSERVERINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountAuthPcServerInfo(
            AppleAuthorizerAccountAuthPcServerInfoParameter para) throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAppleAuthorizerAccountAuthPcServerInfoUrl, para);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountAuthPcMachineInfo>> listAppleAuthorizerAccountAuthPcMachineInfo(
            AppleAuthorizerAccountAuthPcMachineInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAppleAuthorizerAccountAuthPcMachineInfoUrl, para,
                ReturnDataHandleUtils.APPLEAUTHORIZERACCOUNTAUTHPCMACHINEINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountAuthPcMachineInfo(
            AppleAuthorizerAccountAuthPcMachineInfoParameter para) throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAppleAuthorizerAccountAuthPcMachineInfoUrl, para);
    }

}
