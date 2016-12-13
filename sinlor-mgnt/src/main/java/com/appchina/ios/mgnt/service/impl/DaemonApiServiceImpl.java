// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.appchina.ios.core.cahe.model.app.IpaFileResp;
import com.appchina.ios.core.crawler.model.AppStoreApplication;
import com.appchina.ios.core.crawler.model.OnlineAppResult;
import com.appchina.ios.mgnt.controller.model.EnterpriseFilePathParameter;
import com.appchina.ios.mgnt.service.DaemonApiService;
import com.appchina.ios.mgnt.utils.ParameterUtils;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Service("daemonApiService")
public class DaemonApiServiceImpl implements DaemonApiService {
    private static final Logger log = Logger.getLogger(DaemonApiServiceImpl.class);
    @Value("${ios.mgnt.daemon.enterpriseipatmpfilepathurl}")
    private String enterpriseIpaTmpFilePathUrl = "http://127.0.0.1:44444/ios-daemon/scheduler/enterprise/ipafileloader/path.json";
    @Value("${ios.mgnt.daemon.enterpriseipafilepathurl}")
    private String enterpriseIpaFilePathUrl = "http://127.0.0.1:44444/ios-daemon/scheduler/enterprise/enteripafilepath.json";
    @Value("${ios.mgnt.daemon.onlineitemurl}")
    private String onlineItemUrl = "http://127.0.0.1:44444/ios-daemon/online/online.json";
    @Value("${ios.mgnt.daemon.offlineUrl}")
    private String offlineUrl = "http://10.18.0.54:44444/ios-daemon/appoffline/off.json";
    @Value("${ios.mgnt.demon.onlinecommitclientapiappurl}")
    private String daemonOnlineCommitClientApiUrl = "http://10.18.0.54:44444/ios-daemon/app/onlinecommitclientapiapp.json";

    @Override
    public ApiRespWrapper<String> getEnterpriseTmpFilePath() throws ServiceException {
        try {
            return RemoteDataUtil.get(enterpriseIpaTmpFilePathUrl, null, ParameterUtils.DEFAULT_PD_HANDLE,
                    ParameterUtils.STRING_RD_HANDLE, false);
        } catch (Exception e) {
            String errmsg = "Get enterprise tmp ipa file path faile.Url:" + enterpriseIpaTmpFilePathUrl + ", errmsg:"
                    + e.getMessage();
            log.error(errmsg, e);
            throw ServiceException.getInternalException(e.getMessage());
        }
    }

    @Override
    public ApiRespWrapper<IpaFileResp> getEnterpriseIpaFilePath(String sign, String md5) throws ServiceException {
        EnterpriseFilePathParameter para = new EnterpriseFilePathParameter(sign, md5);
        try {
            return RemoteDataUtil.get(enterpriseIpaFilePathUrl, para, ParameterUtils.ENTERPRISEFILEPATH_PD_HANDLE,
                    ParameterUtils.IPAFILERESP_RD_HANDLE, false);
        } catch (Exception e) {
            String errmsg = "Get the enterprise ipa file path failed.Sign:" + sign + ",md5:" + md5 + ", errmsg:"
                    + e.getMessage();
            log.error(errmsg, e);
            throw ServiceException.getInternalException("上架应用失败，远程服务器错误!");
        }
    }

    @Override
    public ApiRespWrapper<Boolean> offlineApp(Integer rootId) throws ServiceException {
        try {
            return RemoteDataUtil.get(offlineUrl, rootId, ParameterUtils.ROOTID_PD_HANDLE,
                    ParameterUtils.BOOLEAN_RD_HANDLE, false);
        } catch (Exception e) {
            String errmsg = "Offline the root application failed.RootId:" + rootId + ", errmsg:" + e.getMessage();
            log.error(errmsg, e);
            throw ServiceException.getInternalException("下架应用失败，远程服务器错误!");
        }
    }

    @Override
    public ApiRespWrapper<OnlineAppResult> onlineCommitClientApi(AppStoreApplication data) throws ServiceException {
        try {
            return RemoteDataUtil.get(daemonOnlineCommitClientApiUrl, data, ParameterUtils.DATAS_POST_HANDLE,
                    ParameterUtils.ONLINEAPPRESULT_RD_HANDLE, false);
        } catch (Exception e) {
            String errmsg = "Offline the root application failed.RootId:" + data.getId() + ", errmsg:" + e.getMessage();
            log.error(errmsg, e);
            throw ServiceException.getInternalException("上架应用失败，远程服务器错误!");
        }
    }
}
