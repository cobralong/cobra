// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import com.appchina.ios.core.cahe.model.app.IpaFileResp;
import com.appchina.ios.core.crawler.model.AppStoreApplication;
import com.appchina.ios.core.crawler.model.OnlineAppResult;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface DaemonApiService {
    /**
     * 获取企业签名IPA的存储路径
     * 
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<String> getEnterpriseTmpFilePath() throws ServiceException;

    ApiRespWrapper<IpaFileResp> getEnterpriseIpaFilePath(String signature, String md5) throws ServiceException;

    ApiRespWrapper<Boolean> offlineApp(Integer rootId) throws ServiceException;

    ApiRespWrapper<OnlineAppResult> onlineCommitClientApi(AppStoreApplication data) throws ServiceException;

}
