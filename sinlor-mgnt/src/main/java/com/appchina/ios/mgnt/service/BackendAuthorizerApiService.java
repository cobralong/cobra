// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import java.util.List;

import com.appchina.ios.core.dto.app.AuthorizerAppDownloadFeedback;
import com.appchina.ios.core.dto.app.AuthorizerAppDownloadTask;
import com.appchina.ios.core.dto.app.AuthorizerAppIpa;
import com.appchina.ios.core.dto.system.AuthorizerAppDownloadServerInfo;
import com.appchina.ios.core.dto.system.AuthorizerPcMachineInfo;
import com.appchina.ios.core.dto.system.AuthorizerPcServerInfo;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppDownloadFeedbackParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppDownloadServerInfoParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppDownloadTaskParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppIpaParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerPcMachineInfoParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerPcServerInfoParameter;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * @author luofei@appchina.com create date: 2016年3月23日
 *
 */
public interface BackendAuthorizerApiService {
    // auth pc server info
    ApiRespWrapper<ListWrapResp<AuthorizerPcServerInfo>> listAuthorizerPcServceInfo(AuthorizerPcServerInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAuthorizerPcServceInfo(AuthorizerPcServerInfoParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAuthorizerPcServceInfo(AuthorizerPcServerInfoParameter para) throws ServiceException;

    // auth pc machine info
    ApiRespWrapper<ListWrapResp<AuthorizerPcMachineInfo>> listAuthorizerPcMachineInfo(
            AuthorizerPcMachineInfoParameter para) throws ServiceException;


    ApiRespWrapper<Boolean> modifyAuthorizerPcMachineInfo(AuthorizerPcMachineInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addAuthorizerPcMachineInfo(AuthorizerPcMachineInfoParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AuthorizerPcServerInfo>> listAuthorizerPcServerInfo(List<Integer> pcServerIds)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AuthorizerPcMachineInfo>> listAuthorizerPcMachineInfo(List<Integer> pcIds)
            throws ServiceException;

    // authorizer app ipa info
    ApiRespWrapper<ListWrapResp<AuthorizerAppIpa>> listAuthorizerAppIpa(AuthorizerAppIpaParameter para)
            throws ServiceException;

    ApiRespWrapper<AuthorizerAppIpa> detailAuthorizerAppIpa(AuthorizerAppIpaParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAuthorizerAppIpa(AuthorizerAppIpaParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAuthorizerAppIpa(AuthorizerAppIpaParameter para) throws ServiceException;

    // authorizer app ipa download task info
    ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadTask>> listAuthorizerAppDownloadTask(
            AuthorizerAppDownloadTaskParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadTask>> listAuthorizerAppDownloadTask(List<Integer> ids)
            throws ServiceException;

    ApiRespWrapper<AuthorizerAppDownloadTask> detailAuthorizerAppDownloadTask(AuthorizerAppDownloadTaskParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAuthorizerAppDownloadTask(AuthorizerAppDownloadTaskParameter para)
            throws ServiceException;

    // authorizer app ipa download feedback info
    ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadFeedback>> listAuthorizerAppDownloadFeedback(
            AuthorizerAppDownloadFeedbackParameter para) throws ServiceException;

    ApiRespWrapper<AuthorizerAppDownloadFeedback> detailAuthorizerAppDownloadFeedback(
            AuthorizerAppDownloadFeedbackParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAuthorizerAppDownloadFeedback(AuthorizerAppDownloadFeedbackParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadServerInfo>> listAuthorizerAppDownloadServerInfo(
            AuthorizerAppDownloadServerInfoParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadServerInfo>> listAuthorizerAppDownloadServerInfo(List<Integer> ids)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAuthorizerAppDownloadServerInfo(AuthorizerAppDownloadServerInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addAuthorizerAppDownloadServerInfo(AuthorizerAppDownloadServerInfoParameter para)
            throws ServiceException;

}
