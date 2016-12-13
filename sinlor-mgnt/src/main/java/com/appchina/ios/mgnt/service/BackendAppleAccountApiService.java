// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import java.util.List;

import com.appchina.ios.core.dto.account.AppleAuthorizerAccount;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountAuthDeviceInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountAuthPcMachineInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountAuthPcServerInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountBuyAppInfo;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountAuthDeviceInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountAuthPcMachineInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountAuthPcServerInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountBuyAppInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountParameter;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * @author luofei@appchina.com create date: 2016年3月23日
 *
 */
public interface BackendAppleAccountApiService {
    // version
    ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> listAppleAuthorizerAccount(AppleAuthorizerAccountParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> listAppleAuthorizerAccount(List<Integer> ids)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppleAuthorizerAccount(AppleAuthorizerAccountParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAppleAuthorizerAccount(AppleAuthorizerAccountParameter para) throws ServiceException;


    ApiRespWrapper<AppleAuthorizerAccount> detailAppleAuthorizerAccount(AppleAuthorizerAccountParameter para)
            throws ServiceException;


    ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountBuyAppInfo>> listAppleAuthorizerAccountBuyAppInfo(
            AppleAuthorizerAccountBuyAppInfoParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountBuyAppInfo>> listAppleAuthorizerAccountBuyAppInfo(
            List<Integer> ids) throws ServiceException;

    ApiRespWrapper<AppleAuthorizerAccountBuyAppInfo> detailAppleAuthorizerAccountBuyAppInfo(
            AppleAuthorizerAccountBuyAppInfoParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAppleAuthorizerAccountBuyAppInfo(AppleAuthorizerAccountBuyAppInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountBuyAppInfo(AppleAuthorizerAccountBuyAppInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountAuthDeviceInfo>> listAppleAuthorizerAccountAuthDeviceInfo(
            AppleAuthorizerAccountAuthDeviceInfoParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountAuthDeviceInfo(
            AppleAuthorizerAccountAuthDeviceInfoParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountAuthPcServerInfo>> listAppleAuthorizerAccountAuthPcServerInfo(
            AppleAuthorizerAccountAuthPcServerInfoParameter para) throws ServiceException;


    ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountAuthPcServerInfo(
            AppleAuthorizerAccountAuthPcServerInfoParameter para) throws ServiceException;


    ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountAuthPcMachineInfo>> listAppleAuthorizerAccountAuthPcMachineInfo(
            AppleAuthorizerAccountAuthPcMachineInfoParameter para) throws ServiceException;


    ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountAuthPcMachineInfo(
            AppleAuthorizerAccountAuthPcMachineInfoParameter para) throws ServiceException;


}
