// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
import com.appchina.ios.mgnt.service.BackendPcSuiteApiService;
import com.appchina.ios.mgnt.utils.ParameterUtils;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * @author luofei@appchina.com create date: 2016年3月9日
 *
 */
@Service("backendPcSuiteApiService")
public class BackendPcSuiteApiServiceImpl implements BackendPcSuiteApiService {
    private static final Logger log = Logger.getLogger(BackendPcSuiteApiServiceImpl.class);
    // pcsuite version
    @Value("${ios.mgnt.api.listpcsuiteversionversionurl}")
    private String listPcSuiteVersionVersionUrl = "http://127.0.0.1:11111/admin/pcsuite/version/listpure.json";
    @Value("${ios.mgnt.api.listpcsuiteversionurl}")
    private String listPcSuiteVersionUrl = "http://127.0.0.1:11111/admin/pcsuite/version/list.json";
    @Value("${ios.mgnt.api.detailpcsuiteversionurl}")
    private String detailPcSuiteVersionUrl = "http://127.0.0.1:11111/admin/pcsuite/version/detail.json";
    @Value("${ios.mgnt.api.modifypcsuiteversionurl}")
    private String modifyPcSuiteVersionUrl = "http://127.0.0.1:11111/admin/pcsuite/version//modify.json";
    @Value("${ios.mgnt.api.addpcsuiteversionurl}")
    private String addPcSuiteVersionUrl = "http://127.0.0.1:11111/admin/pcsuite/version/add.json";

    // pcsuite channel
    @Value("${ios.mgnt.api.listpcsuitechannelurl}")
    private String listPcSuiteChannelUrl = "http://127.0.0.1:11111/admin/pcsuite/channel/list.json";
    @Value("${ios.mgnt.api.addpcsuitechannelurl}")
    private String addPcSuiteChannelUrl = "http://127.0.0.1:11111/admin/pcsuite/channel/add.json";

    // pcsuite comm mobileclient
    @Value("${ios.mgnt.api.listpcsuitecommmobileclientinfourl}")
    private String listPcSuiteCommMobileClientInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/comm/mobileclient/info/list.json";
    @Value("${ios.mgnt.api.modifypcsuitecommmobileclientinfourl}")
    private String modifyPcSuiteCommMobileClientInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/comm/mobileclient/info/modify.json";
    @Value("${ios.mgnt.api.addpcsuitecommmobileclientinfourl}")
    private String addPcSuiteCommMobileClientInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/comm/mobileclient/info/add.json";

    // apple device driver
    @Value("${ios.mgnt.api.listpcsuiteappledevicedriverinfourl}")
    private String listPcSuiteAppleDeviceDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/driver/info/list.json";
    @Value("${ios.mgnt.api.modifypcsuiteappledevicedriverinfourl}")
    private String modifyPcSuiteAppleDeviceDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/driver/info/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteappledevicedriverinfourl}")
    private String addPcSuiteAppleDeviceDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/driver/info/add.json";

    // apple device driver detail
    @Value("${ios.mgnt.api.listpcsuiteappledevicedriverdetailinfourl}")
    private String listPcSuiteAppleDeviceDriverDetailInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/driver/detail/info/list.json";
    @Value("${ios.mgnt.api.modifypcsuiteappledevicedriverdetailinfourl}")
    private String modifyPcSuiteAppleDeviceDriverDetailInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/driver/detail/info/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteappledevicedriverdetailinfourl}")
    private String addPcSuiteAppleDeviceDriverDetailInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/driver/detail/info/add.json";


    // apple device connect driver
    @Value("${ios.mgnt.api.listpcsuiteappledeviceconnectdriverinfourl}")
    private String listPcSuiteAppleDeviceConnectDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/connect/driver/info/list.json";
    @Value("${ios.mgnt.api.modifypcsuiteappledeviceconnectdriverinfourl}")
    private String modifyPcSuiteAppleDeviceConnectDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/connect/driver/info/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteappledeviceconnectdriverinfourl}")
    private String addPcSuiteAppleDeviceConnectDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/connect/driver/info/add.json";

    // apple device authorizer driver
    @Value("${ios.mgnt.api.listpcsuiteappledeviceauthorizerdriverinfourl}")
    private String listPcSuiteAppleDeviceAuthorizerDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/authorizer/driver/info/list.json";
    @Value("${ios.mgnt.api.modifypcsuiteappledeviceauthorizerdriverinfourl}")
    private String modifyPcSuiteAppleDeviceAuthorizerDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/authorizer/driver/info/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteappledeviceauthorizerdriverinfourl}")
    private String addPcSuiteAppleDeviceAuthorizerDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/authorizer/driver/info/add.json";

    // apple device full driver
    @Value("${ios.mgnt.api.listpcsuiteappledevicefulldriverinfourl}")
    private String listPcSuiteAppleDeviceFullDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/full/driver/info/list.json";
    @Value("${ios.mgnt.api.modifypcsuiteappledevicefulldriverinfourl}")
    private String modifyPcSuiteAppleDeviceFullDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/full/driver/info/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteappledevicefulldriverinfourl}")
    private String addPcSuiteAppleDeviceFullDriverInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/apple/device/full/driver/info/add.json";

    // pc upgrade ip switch
    @Value("${ios.mgnt.api.listpcsuiteupgradeswitchipconfurl}")
    private String listPcSuiteUpgradeSwitchIpConfUrl = "http://127.0.0.1:11111/admin/pcsuite/upgrade/switch/ip/conf/list.json";
    @Value("${ios.mgnt.api.modifypcsuiteupgradeswitchipconfurl}")
    private String modifyPcSuiteUpgradeSwitchIpConfUrl = "http://127.0.0.1:11111/admin/pcsuite/upgrade/switch/ip/conf/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteupgradeswitchipconfurl}")
    private String addPcSuiteUpgradeSwitchIpConfUrl = "http://127.0.0.1:11111/admin/pcsuite/upgrade/switch/ip/conf/add.json";


    @Value("${ios.mgnt.api.listpcsuiteuserurl}")
    private String listPcSuiteUserUrl = "http://127.0.0.1:11111/admin/pcsuite/user/list.json";
    @Value("${ios.mgnt.api.listpcsuiteconnecteddeviceinfourl}")
    private String listPcSuiteConnectedDeviceInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/connected/device/info/list.json";


    @Value("${ios.mgnt.api.listpcsuitelanginfourl}")
    private String listPcSuiteLangInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/lang/info/list.json";
    @Value("${ios.mgnt.api.modifypcsuitelanginfourl}")
    private String modifyPcSuiteLangInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/lang/info/modify.json";

    @Value("${ios.mgnt.api.detailpcsuitewebclipnavigationinfourl}")
    private String detailPcSuiteWebClipNavigationInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/webclip/navigation/info/detail.json";
    @Value("${ios.mgnt.api.modifypcsuitewebclipnavigationinfourl}")
    private String modifyPcSuiteWebClipNavigationInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/webclip/navigation/info/modify.json";

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteVersion>> listPcSuiteVersion(PcSuiteVersionParameter para)
            throws ServiceException {
        return get(listPcSuiteVersionUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.PCSUITEVERSIONLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<PcSuiteVersion> detailPcSuiteVersion(int id) throws ServiceException {
        return get(detailPcSuiteVersionUrl, id, ParametersHandle.PS_ID_HANDLE, ParameterUtils.PCSUITEVERSION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<List<String>> listPcSuiteVersionVersion() throws ServiceException {
        return get(listPcSuiteVersionVersionUrl, null, null, ParameterUtils.STRINGLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteVersion(PcSuiteVersionParameter para) throws ServiceException {
        return get(addPcSuiteVersionUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteVersion(PcSuiteVersionParameter para) throws ServiceException {
        return get(modifyPcSuiteVersionUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    // channel
    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteChannel>> listPcSuiteChannel(PcSuiteChannelParameter para)
            throws ServiceException {
        return get(listPcSuiteChannelUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.PCSUITECHANNELLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteChannel(PcSuiteChannelParameter para) throws ServiceException {
        return get(addPcSuiteChannelUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteCommMobileClientInfo>> listPcsuiteCommMobileClientInfo(
            StatusStartSizeParameter g) throws ServiceException {
        return get(listPcSuiteCommMobileClientInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITECOMMMOBILECLIENTINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcsuiteCommMobileClientInfo(IdStatusParameter g) throws ServiceException {
        return get(modifyPcSuiteCommMobileClientInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcsuiteCommMobileClientInfo(PcSuiteCommMobileClientInfo g)
            throws ServiceException {
        return get(addPcSuiteCommMobileClientInfoUrl, g, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>> listPcSuiteAppleDeviceDriverInfo(
            PcSuiteAppleDeviceDriverInfoParameter g) throws ServiceException {
        return get(listPcSuiteAppleDeviceDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEAPPLEDEVICEDRIVERINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteAppleDeviceDriverInfo(PcSuiteAppleDeviceDriverInfoParameter g)
            throws ServiceException {
        return get(addPcSuiteAppleDeviceDriverInfoUrl, g, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceDriverInfo(PcSuiteAppleDeviceDriverInfoParameter g)
            throws ServiceException {
        return get(modifyPcSuiteAppleDeviceDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceConnectDriverInfo>> listPcSuiteAppleDeviceConnectDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater g) throws ServiceException {
        return get(listPcSuiteAppleDeviceConnectDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEAPPLEDEVICECONNECTDRIVERINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteAppleDeviceConnectDriverInfo(PcSuiteAppleDeviceConnectDriverInfoParamater g)
            throws ServiceException {
        return get(addPcSuiteAppleDeviceConnectDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceConnectDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater g) throws ServiceException {
        return get(modifyPcSuiteAppleDeviceConnectDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceAuthorizerDriverInfo>> listPcSuiteAppleDeviceAuthorizerDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater g) throws ServiceException {
        return get(listPcSuiteAppleDeviceAuthorizerDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEAPPLEDEVICEAUTHORIZERDRIVERINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteAppleDeviceAuthorizerDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater g) throws ServiceException {
        return get(addPcSuiteAppleDeviceAuthorizerDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceAuthorizerDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater g) throws ServiceException {
        return get(modifyPcSuiteAppleDeviceAuthorizerDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceFullDriverInfo>> listPcSuiteAppleDeviceFullDriverInfo(
            PcSuiteAppleDeviceConnectDriverInfoParamater g) throws ServiceException {
        return get(listPcSuiteAppleDeviceFullDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEAPPLEDEVICEFULLDRIVERINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteAppleDeviceFullDriverInfo(PcSuiteAppleDeviceConnectDriverInfoParamater g)
            throws ServiceException {
        return get(addPcSuiteAppleDeviceFullDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceFullDriverInfo(PcSuiteAppleDeviceConnectDriverInfoParamater g)
            throws ServiceException {
        return get(modifyPcSuiteAppleDeviceFullDriverInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    private static <K, V> V get(String url, K para, ParametersHandle<K> parametersHandle,
            ReturnDataHandle<V> returnDataHandle) {
        try {
            return RemoteDataUtil.get(url, para, parametersHandle, returnDataHandle, false);
        } catch (Exception e) {
            String errmsg = "Get data failed.. Url:" + url + ", errmsg:" + e.getMessage();
            log.error(errmsg, e);
            throw ServiceException.getInternalException(e.getMessage());
        }
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverDetailInfo>> listPcSuiteAppleDeviceDriverDetailInfo(
            PcSuiteAppleDeviceDriverDetailInfo g) throws ServiceException {
        return get(listPcSuiteAppleDeviceDriverDetailInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEAPPLEDEVICEDRIVERDETAILINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteAppleDeviceDriverDetailInfo(PcSuiteAppleDeviceDriverDetailInfoParameter g)
            throws ServiceException {
        return get(addPcSuiteAppleDeviceDriverDetailInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteAppleDeviceDriverDetailInfo(
            PcSuiteAppleDeviceDriverDetailInfoParameter g) throws ServiceException {
        return get(modifyPcSuiteAppleDeviceDriverDetailInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteUser>> listPcSuiteUser(PcSuiteUserParameter para) throws ServiceException {
        return RemoteDataUtil.get(listPcSuiteUserUrl, para, ParameterUtils.PCSUITEUSER_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteConnectedDeviceInfo>> listPcSuiteConnectedDeviceInfo(
            PcSuiteConnectedDeviceInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(listPcSuiteConnectedDeviceInfoUrl, para,
                ParameterUtils.PCSUITECONNECTEDDEVICEINFO_LIST_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteUpgradeSwitchIpConf>> listPcSuiteUpgradeSwitchIpConf(
            PcSuiteUpgradeSwitchIpConfParamater g) throws ServiceException {
        return get(listPcSuiteUpgradeSwitchIpConfUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEUPGRADESWITCHIPCONF_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteUpgradeSwitchIpConf(PcSuiteUpgradeSwitchIpConfParamater g)
            throws ServiceException {
        return get(addPcSuiteUpgradeSwitchIpConfUrl, g, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteUpgradeSwitchIpConf(PcSuiteUpgradeSwitchIpConfParamater g)
            throws ServiceException {
        return get(modifyPcSuiteUpgradeSwitchIpConfUrl, g, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteLangInfo>> listPcSuiteLangInfo(PcSuiteLangInfoParamater g)
            throws ServiceException {
        return get(listPcSuiteLangInfoUrl, g, ParametersHandle.PS_HANDLE, ParameterUtils.PCSUITELANGINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteLangInfo(PcSuiteLangInfoParamater g) throws ServiceException {
        return get(modifyPcSuiteLangInfoUrl, g, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<PcSuiteWebClipNavigationInfo> detailPcSuiteWebClipNavigationInfo() throws ServiceException {
        return get(detailPcSuiteWebClipNavigationInfoUrl, null, null,
                ParameterUtils.PCSUITEWEBCLIPNAVIGATIONINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteWebClipNavigationInfo(PcSuiteWebClipNavigationInfo g)
            throws ServiceException {
        return get(modifyPcSuiteWebClipNavigationInfoUrl, g, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }
}
