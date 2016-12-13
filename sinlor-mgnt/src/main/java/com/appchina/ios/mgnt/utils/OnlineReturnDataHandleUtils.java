// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.utils;

import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.crawler.model.CrawlerApplication;
import com.appchina.ios.core.dto.online.AppOnlineRecord;
import com.appchina.ios.core.dto.online.AppOnlineRecordStat;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class OnlineReturnDataHandleUtils {
    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppOnlineRecordStat>>> APPONLINERECORDSTAT_LIST_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppOnlineRecordStat>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppOnlineRecordStat>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppOnlineRecordStat>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppOnlineRecord>>> APPONLINERECORD_LIST_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppOnlineRecord>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppOnlineRecord>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppOnlineRecord>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<CrawlerApplication>>> CRAWLERAPPLICATION_LIST_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<CrawlerApplication>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<CrawlerApplication>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<CrawlerApplication>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<CrawlerApplication>> CRAWLERAPPLICATION_HANDLE = new ReturnDataHandle<ApiRespWrapper<CrawlerApplication>>() {
        @Override
        public ApiRespWrapper<CrawlerApplication> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<CrawlerApplication>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
}
