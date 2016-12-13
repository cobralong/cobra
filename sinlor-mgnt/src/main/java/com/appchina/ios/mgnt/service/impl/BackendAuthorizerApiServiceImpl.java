// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.appchina.ios.core.dto.app.AuthorizerAppDownloadFeedback;
import com.appchina.ios.core.dto.app.AuthorizerAppDownloadTask;
import com.appchina.ios.core.dto.app.AuthorizerAppIpa;
import com.appchina.ios.core.dto.system.AuthorizerAppDownloadServerInfo;
import com.appchina.ios.core.dto.system.AuthorizerPcMachineInfo;
import com.appchina.ios.core.dto.system.AuthorizerPcServerInfo;
import com.appchina.ios.core.utils.ReturnDataHandleUtils;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppDownloadFeedbackParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppDownloadServerInfoParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppDownloadTaskParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppIpaParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerPcMachineInfoParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerPcServerInfoParameter;
import com.appchina.ios.mgnt.service.BackendAuthorizerApiService;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * @author luofei@appchina.com create date: 2016年3月28日
 *
 */
@Service("backendAuthorizerApiService")
public class BackendAuthorizerApiServiceImpl implements BackendAuthorizerApiService {
    @Value("${ios.mgnt.api.listappleauthorizerpcserverinfourl}")
    private String listAuthorizerPcServerInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/pc/server/info/list.json";
    @Value("${ios.mgnt.api.listauthorizerpcserverinfobyidsurl}")
    private String listAuthorizerPcServerInfoByIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/pc/server/info/list/byids.json";
    @Value("${ios.mgnt.api.addappleauthorizerpcserverinfourl}")
    private String addAuthorizerPcServerInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/pc/server/info/add.json";
    @Value("${ios.mgnt.api.modifyappleauthorizerpcserverinfourl}")
    private String modifyAuthorizerPcServerInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/pc/server/info/modify.json";

    @Value("${ios.mgnt.api.listappleauthorizerpcmachineinfourl}")
    private String listAuthorizerPcMachineInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/pc/machine/info/list.json";
    @Value("${ios.mgnt.api.listauthorizerpcmachineinfobyidsurl}")
    private String listAuthorizerPcMachineInfoByIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/pc/machine/info/list/byids.json";
    @Value("${ios.mgnt.api.addappleauthorizerpcmachineinfourl}")
    private String addAuthorizerPcMachineInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/pc/machine/info/add.json";
    @Value("${ios.mgnt.api.modifyappleauthorizerpcmachineinfourl}")
    private String modifyAuthorizerPcMachineInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/pc/machine/info/modify.json";

    @Value("${ios.mgnt.api.listauthorizerappipaurl}")
    private String listAuthorizerAppIpaUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/ipa/app/list.json";
    @Value("${ios.mgnt.api.detailauthorizerappipaurl}")
    private String detailAuthorizerAppIpaUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/ipa/app/detail.json";
    @Value("${ios.mgnt.api.modifyauthorizerappipaurl}")
    private String modifyAuthorizerAppIpaUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/ipa/app/modify.json";
    @Value("${ios.mgnt.api.addauthorizerappipaurl}")
    private String addAuthorizerAppIpaUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/ipa/app/add.json";

    @Value("${ios.mgnt.api.listauthorizerappdownloadtaskurl}")
    private String listAuthorizerAppDownloadTaskUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/task/list.json";
    @Value("${ios.mgnt.api.listauthorizerappdownloadtaskbyidsurl}")
    private String listAuthorizerAppDownloadTaskByIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/task/list/byids.json";
    @Value("${ios.mgnt.api.detailauthorizerappdownloadtaskurl}")
    private String detailAuthorizerAppDownloadTaskUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/task/detail.json";
    @Value("${ios.mgnt.api.modifyauthorizerappdownloadtaskurl}")
    private String modifyAuthorizerAppDownloadTaskUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/task/modify.json";

    @Value("${ios.mgnt.api.listauthorizerappdownloadfeedbackurl}")
    private String listAuthorizerAppDownloadFeedbackUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/feedback/list.json";
    @Value("${ios.mgnt.api.detailauthorizerappdownloadfeedbackurl}")
    private String detailAuthorizerAppDownloadFeedbackUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/feedback/detail.json";
    @Value("${ios.mgnt.api.modifyauthorizerappdownloadfeedbackurl}")
    private String modifyAuthorizerAppDownloadFeedbackUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/feedback/modify.json";

    @Value("${ios.mgnt.api.listauthorizerappdownloadserverinfourl}")
    private String listAuthorizerAppDownloadServerInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/server/info/list.json";
    @Value("${ios.mgnt.api.listauthorizerappdownloadserverinfobyidsurl}")
    private String listAuthorizerAppDownloadServerInfoByIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/server/info/list/byids.json";
    @Value("${ios.mgnt.api.addauthorizerappdownloadserverinfourl}")
    private String addAuthorizerAppDownloadServerInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/server/info/add.json";
    @Value("${ios.mgnt.api.modifyauthorizerappdownloadserverinfourl}")
    private String modifyAuthorizerAppDownloadServerInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/authorizer/app/download/server/info/modify.json";


    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerPcServerInfo>> listAuthorizerPcServceInfo(
            AuthorizerPcServerInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerPcServerInfoUrl, para,
                ReturnDataHandleUtils.AUTHORIZERPCSERVERINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerPcServerInfo>> listAuthorizerPcServerInfo(List<Integer> pcServerIds)
            throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerPcServerInfoByIdsUrl, pcServerIds,
                ParametersHandle.IDS_ARRAY_PD_HANDLE, ReturnDataHandleUtils.AUTHORIZERPCSERVERINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAuthorizerPcServceInfo(AuthorizerPcServerInfoParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAuthorizerPcServerInfoUrl, para);
    }

    @Override
    public ApiRespWrapper<Boolean> addAuthorizerPcServceInfo(AuthorizerPcServerInfoParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(addAuthorizerPcServerInfoUrl, para);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerPcMachineInfo>> listAuthorizerPcMachineInfo(
            AuthorizerPcMachineInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerPcMachineInfoUrl, para,
                ReturnDataHandleUtils.AUTHORIZERPCMACHINEINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerPcMachineInfo>> listAuthorizerPcMachineInfo(List<Integer> pcMachineIds)
            throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerPcMachineInfoByIdsUrl, pcMachineIds,
                ParametersHandle.IDS_ARRAY_PD_HANDLE, ReturnDataHandleUtils.AUTHORIZERPCMACHINEINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAuthorizerPcMachineInfo(AuthorizerPcMachineInfoParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAuthorizerPcMachineInfoUrl, para);
    }

    @Override
    public ApiRespWrapper<Boolean> addAuthorizerPcMachineInfo(AuthorizerPcMachineInfoParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(addAuthorizerPcMachineInfoUrl, para);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerAppIpa>> listAuthorizerAppIpa(AuthorizerAppIpaParameter para)
            throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerAppIpaUrl, para, ReturnDataHandleUtils.AUTHORIZERAPPIPA_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AuthorizerAppIpa> detailAuthorizerAppIpa(AuthorizerAppIpaParameter para)
            throws ServiceException {
        return RemoteDataUtil.get(detailAuthorizerAppIpaUrl, para, ReturnDataHandleUtils.AUTHORIZERAPPIPA_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAuthorizerAppIpa(AuthorizerAppIpaParameter para) throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAuthorizerAppIpaUrl, para);
    }

    @Override
    public ApiRespWrapper<Boolean> addAuthorizerAppIpa(AuthorizerAppIpaParameter para) throws ServiceException {
        return RemoteDataUtil.getBoolean(addAuthorizerAppIpaUrl, para);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadTask>> listAuthorizerAppDownloadTask(
            AuthorizerAppDownloadTaskParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerAppDownloadTaskUrl, para,
                ReturnDataHandleUtils.AUTHORIZERAPPDOWNLOADTASK_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadTask>> listAuthorizerAppDownloadTask(List<Integer> ids)
            throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerAppDownloadTaskByIdsUrl, ids,
                ReturnDataHandleUtils.AUTHORIZERAPPDOWNLOADTASK_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AuthorizerAppDownloadTask> detailAuthorizerAppDownloadTask(
            AuthorizerAppDownloadTaskParameter para) throws ServiceException {
        return RemoteDataUtil.get(detailAuthorizerAppDownloadTaskUrl, para,
                ReturnDataHandleUtils.AUTHORIZERAPPDOWNLOADTASK_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAuthorizerAppDownloadTask(AuthorizerAppDownloadTaskParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAuthorizerAppDownloadTaskUrl, para);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadFeedback>> listAuthorizerAppDownloadFeedback(
            AuthorizerAppDownloadFeedbackParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerAppDownloadFeedbackUrl, para,
                ReturnDataHandleUtils.AUTHORIZERAPPDOWNLOADFEEDBACK_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AuthorizerAppDownloadFeedback> detailAuthorizerAppDownloadFeedback(
            AuthorizerAppDownloadFeedbackParameter para) throws ServiceException {
        return RemoteDataUtil.get(detailAuthorizerAppDownloadFeedbackUrl, para,
                ReturnDataHandleUtils.AUTHORIZERAPPDOWNLOADFEEDBACK_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAuthorizerAppDownloadFeedback(AuthorizerAppDownloadFeedbackParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAuthorizerAppDownloadFeedbackUrl, para);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadServerInfo>> listAuthorizerAppDownloadServerInfo(
            AuthorizerAppDownloadServerInfoParameter para) throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerAppDownloadServerInfoUrl, para,
                ReturnDataHandleUtils.AUTHORIZERAPPDOWNLOADSERVERINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadServerInfo>> listAuthorizerAppDownloadServerInfo(
            List<Integer> ids) throws ServiceException {
        return RemoteDataUtil.get(listAuthorizerAppDownloadServerInfoByIdsUrl, ids,
                ReturnDataHandleUtils.AUTHORIZERAPPDOWNLOADSERVERINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAuthorizerAppDownloadServerInfo(AuthorizerAppDownloadServerInfoParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(modifyAuthorizerAppDownloadServerInfoUrl, para);
    }

    @Override
    public ApiRespWrapper<Boolean> addAuthorizerAppDownloadServerInfo(AuthorizerAppDownloadServerInfoParameter para)
            throws ServiceException {
        return RemoteDataUtil.getBoolean(addAuthorizerAppDownloadServerInfoUrl, para);
    }

}
