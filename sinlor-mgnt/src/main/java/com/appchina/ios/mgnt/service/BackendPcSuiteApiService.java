// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import java.util.List;

import com.appchina.ios.core.dto.account.PcSuiteConnectedDeviceInfo;
import com.appchina.ios.core.dto.account.PcSuiteUser;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceAuthorizerDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceConnectDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceDriverDetailInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceFullDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteChannel;
import com.appchina.ios.core.dto.system.PcSuiteCommMobileClientInfo;
import com.appchina.ios.core.dto.system.PcSuiteLangInfo;
import com.appchina.ios.core.dto.system.PcSuiteUpgradeSwitchIpConf;
import com.appchina.ios.core.dto.system.PcSuiteVersion;
import com.appchina.ios.core.dto.system.PcSuiteWebClipNavigationInfo;
import com.appchina.ios.mgnt.controller.model.IdStatusParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteAppleDeviceConnectDriverInfoParamater;
import com.appchina.ios.mgnt.controller.model.PcSuiteAppleDeviceDriverDetailInfoParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteAppleDeviceDriverInfoParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteChannelParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteConnectedDeviceInfoParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteLangInfoParamater;
import com.appchina.ios.mgnt.controller.model.PcSuiteUpgradeSwitchIpConfParamater;
import com.appchina.ios.mgnt.controller.model.PcSuiteUserParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteVersionParameter;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface BackendPcSuiteApiService {
    // user
    ApiRespWrapper<ListWrapResp<PcSuiteUser>> listPcSuiteUser(PcSuiteUserParameter para) throws ServiceException;

    // connected device info
    ApiRespWrapper<ListWrapResp<PcSuiteConnectedDeviceInfo>> listPcSuiteConnectedDeviceInfo(
            PcSuiteConnectedDeviceInfoParameter para) throws ServiceException;

    // version
    ApiRespWrapper<ListWrapResp<PcSuiteVersion>> listPcSuiteVersion(PcSuiteVersionParameter para)
            throws ServiceException;

    ApiRespWrapper<PcSuiteVersion> detailPcSuiteVersion(int id) throws ServiceException;

    ApiRespWrapper<List<String>> listPcSuiteVersionVersion() throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteVersion(PcSuiteVersionParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteVersion(PcSuiteVersionParameter para) throws ServiceException;

    // channel
    ApiRespWrapper<ListWrapResp<PcSuiteChannel>> listPcSuiteChannel(PcSuiteChannelParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteChannel(PcSuiteChannelParameter para) throws ServiceException;


    ApiRespWrapper<ListWrapResp<PcSuiteCommMobileClientInfo>> listPcsuiteCommMobileClientInfo(
            StatusStartSizeParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcsuiteCommMobileClientInfo(IdStatusParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addPcsuiteCommMobileClientInfo(PcSuiteCommMobileClientInfo para) throws ServiceException;

    // driver
    ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>> listPcSuiteAppleDeviceDriverInfo(
            PcSuiteAppleDeviceDriverInfoParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteAppleDeviceDriverInfo(PcSuiteAppleDeviceDriverInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceDriverInfo(PcSuiteAppleDeviceDriverInfoParameter para)
            throws ServiceException;

    // connect
    ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceConnectDriverInfo>> listPcSuiteAppleDeviceConnectDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater statusParam) throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteAppleDeviceConnectDriverInfo(PcSuiteAppleDeviceConnectDriverInfoParamater para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceConnectDriverInfo(PcSuiteAppleDeviceConnectDriverInfoParamater para)
            throws ServiceException;

    // authorizer
    ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceAuthorizerDriverInfo>> listPcSuiteAppleDeviceAuthorizerDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater statusParam) throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteAppleDeviceAuthorizerDriverInfo(PcSuiteAppleDeviceConnectDriverInfoParamater para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceAuthorizerDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater para) throws ServiceException;

    // full
    ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceFullDriverInfo>> listPcSuiteAppleDeviceFullDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater statusParam) throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteAppleDeviceFullDriverInfo(PcSuiteAppleDeviceConnectDriverInfoParamater para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceFullDriverInfo(PcSuiteAppleDeviceConnectDriverInfoParamater para)
            throws ServiceException;

    // detail
    ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverDetailInfo>> listPcSuiteAppleDeviceDriverDetailInfo(
            PcSuiteAppleDeviceDriverDetailInfo detail) throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteAppleDeviceDriverDetailInfo(PcSuiteAppleDeviceDriverDetailInfoParameter detail)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceDriverDetailInfo(PcSuiteAppleDeviceDriverDetailInfoParameter detail)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<PcSuiteUpgradeSwitchIpConf>> listPcSuiteUpgradeSwitchIpConf(
            PcSuiteUpgradeSwitchIpConfParamater g) throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteUpgradeSwitchIpConf(PcSuiteUpgradeSwitchIpConfParamater g)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteUpgradeSwitchIpConf(PcSuiteUpgradeSwitchIpConfParamater g)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<PcSuiteLangInfo>> listPcSuiteLangInfo(PcSuiteLangInfoParamater para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteLangInfo(PcSuiteLangInfoParamater para) throws ServiceException;

    ApiRespWrapper<PcSuiteWebClipNavigationInfo> detailPcSuiteWebClipNavigationInfo() throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteWebClipNavigationInfo(PcSuiteWebClipNavigationInfo g) throws ServiceException;

}
