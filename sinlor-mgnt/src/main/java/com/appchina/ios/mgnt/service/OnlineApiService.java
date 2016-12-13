// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import com.appchina.ios.core.crawler.model.CrawlerApplication;
import com.appchina.ios.core.dto.online.AppOnlineRecord;
import com.appchina.ios.core.dto.online.AppOnlineRecordStat;
import com.appchina.ios.mgnt.controller.model.AppUploadRecordParameter;
import com.appchina.ios.mgnt.controller.model.AppUploadStatParameter;
import com.appchina.ios.mgnt.controller.model.CrawlerApplicationParameter;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface OnlineApiService {

    ApiRespWrapper<ListWrapResp<AppOnlineRecordStat>> listAppUploadStat(AppUploadStatParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppOnlineRecord>> listAppUploadRecord(AppUploadRecordParameter para)
            throws ServiceException;

    ApiRespWrapper<CrawlerApplication> detailCrawlerApplication(CrawlerApplicationParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<CrawlerApplication>> listCrawlerApplication(CrawlerApplicationParameter para)
            throws ServiceException;

    ApiRespWrapper<Long> countWaitOnline(AppUploadStatParameter para) throws ServiceException;

    ApiRespWrapper<Integer> getMaxOnlineRecordId(CrawlerApplicationParameter para) throws ServiceException;

}
