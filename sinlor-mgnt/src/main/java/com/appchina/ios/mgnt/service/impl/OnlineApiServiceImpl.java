// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.appchina.ios.core.crawler.model.CrawlerApplication;
import com.appchina.ios.core.dto.online.AppOnlineRecord;
import com.appchina.ios.core.dto.online.AppOnlineRecordStat;
import com.appchina.ios.mgnt.controller.model.AppUploadRecordParameter;
import com.appchina.ios.mgnt.controller.model.AppUploadStatParameter;
import com.appchina.ios.mgnt.controller.model.CrawlerApplicationParameter;
import com.appchina.ios.mgnt.service.OnlineApiService;
import com.appchina.ios.mgnt.utils.OnlineReturnDataHandleUtils;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
@Service("onlineApiService")
public class OnlineApiServiceImpl implements OnlineApiService {
    @Value("${app.ios.online.countwaitonlineurl}")
    private String countWaitOnlineUrl = "http://103.231.68.2:17417/stat//waitonline/count";
    @Value("${app.ios.online.listappuploadstaturl}")
    private String listAppUploadStatUrl = "http://103.231.68.2:17417/stat/list.json";
    @Value("${app.ios.online.listappuploadrecordurl}")
    private String listAppUploadRecorUrl = "http://103.231.68.2:17417/stat/record/list.json";
    @Value("${app.ios.online.maxappuploadrecoridurl}")
    private String maxAppUploadRecorIdUrl = "http://103.231.68.2:17417/stat/record/maxId.json";
    @Value("${app.ios.online.detailcrawlerapplicationurl}")
    private String detailCrawlerApplicationUrl = "http://103.231.68.2:17417/stat/crawler/application/detail.json";
    @Value("${app.ios.online.listcrawlerapplicationurl}")
    private String listCrawlerApplicationUrl = "http://103.231.68.2:17417/stat/crawler/application/list.json";

    @Override
    public ApiRespWrapper<ListWrapResp<AppOnlineRecordStat>> listAppUploadStat(AppUploadStatParameter para)
            throws ServiceException {
        return RemoteDataUtil.get(listAppUploadStatUrl, para, ParametersHandle.PS_HANDLE,
                OnlineReturnDataHandleUtils.APPONLINERECORDSTAT_LIST_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppOnlineRecord>> listAppUploadRecord(AppUploadRecordParameter para)
            throws ServiceException {
        return RemoteDataUtil.get(listAppUploadRecorUrl, para, ParametersHandle.PS_HANDLE,
                OnlineReturnDataHandleUtils.APPONLINERECORD_LIST_HANDLE);
    }

    @Override
    public ApiRespWrapper<CrawlerApplication> detailCrawlerApplication(CrawlerApplicationParameter para)
            throws ServiceException {
        return RemoteDataUtil.get(detailCrawlerApplicationUrl, para, ParametersHandle.PS_HANDLE,
                OnlineReturnDataHandleUtils.CRAWLERAPPLICATION_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<CrawlerApplication>> listCrawlerApplication(CrawlerApplicationParameter para)
            throws ServiceException {
        return RemoteDataUtil.get(listCrawlerApplicationUrl, para, ParametersHandle.PS_HANDLE,
                OnlineReturnDataHandleUtils.CRAWLERAPPLICATION_LIST_HANDLE);
    }

    @Override
    public ApiRespWrapper<Long> countWaitOnline(AppUploadStatParameter para) throws ServiceException {
        return RemoteDataUtil
                .get(countWaitOnlineUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.LONG_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Integer> getMaxOnlineRecordId(CrawlerApplicationParameter para) throws ServiceException {
        return RemoteDataUtil.get(maxAppUploadRecorIdUrl, para, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.INTEGER_RD_HANDLE);
    }
}
