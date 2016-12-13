// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.utils;

import com.appchina.ios.core.dto.info.AppStoreArticle;
import com.appchina.ios.mgnt.controller.model.info.*;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.model.PostParametersHandle;
import com.appchina.ios.web.utils.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luofei@refanqie.com (Your Name Here)
 */
public class StoreParameterUtils {
    public static final PostParametersHandle<AppStoreArticleParameter> APPSTORE_ARTICLE_ADD_PARAMETER_PD_HANDLE = new PostParametersHandle<AppStoreArticleParameter>() {

        @Override
        public Map<String, Object> handle(final AppStoreArticleParameter param) {
            return new HashMap<String, Object>() {
                /**
                 * 
                 */
                private static final long serialVersionUID = 7726902287048811394L;

                {
                    put("bundleIdList", param.getBundleIdList());
                    put("id", param.getId());
                    put("articleUrl", param.getArticleUrl());
                    put("content", param.getContent());
                    put("contentText", param.getContentText());
                    put("iconUrl", param.getIconUrl());
                    put("rootIds", param.getRelatedRootids());
                    put("shortDesc", param.getShortDesc());
                    put("tagId", param.getTagId());
                    put("title", param.getTitle());
                }
            };
        }
    };

    public static final ParametersHandle<List<Integer>> APPSTORE_ARTICLE_TAG_IDS_PD_HANDLE = new ParametersHandle<List<Integer>>() {
        @Override
        public String handle(List<Integer> values) {
            return "?tagids=" + CollectionUtils.listToString(values, ",");
        }
    };

    public static final ParametersHandle<AppStoreArticleTagParameter> APPSTORE_ARTICLE_TAG_PD_HANDLE = new ParametersHandle<AppStoreArticleTagParameter>() {
        @Override
        public String handle(AppStoreArticleTagParameter appStoreArticleTagParameter) {
            return "?" + formatUrlParam("id", appStoreArticleTagParameter.getId()) + "&"
                    + formatUrlParam("name", appStoreArticleTagParameter.getName()) + "&"
                    + formatUrlParam("color", appStoreArticleTagParameter.getColor()) + "&"
                    + formatUrlParam("status", appStoreArticleTagParameter.getStatus());
        }
    };

    public static final ParametersHandle<Integer> ID_PD_HANDLE = new ParametersHandle<Integer>() {
        @Override
        public String handle(Integer value) {
            return "?" + formatUrlParam("id", value.intValue());
        }
    };

    public static final ParametersHandle<ModifyStoreBannerParameter> MODIFY_STORE_BANNER_PD_HANDLE = new ParametersHandle<ModifyStoreBannerParameter>() {
        @Override
        public String handle(ModifyStoreBannerParameter modifyStoreBannerParameter) {
            return "?" + formatUrlParam("id", modifyStoreBannerParameter.getId()) + "&"
                    + formatUrlParam("et", modifyStoreBannerParameter.getEt()) + "&"
                    + formatUrlParam("rank", modifyStoreBannerParameter.getRank()) + "&"
                    + formatUrlParam("status", modifyStoreBannerParameter.getStatus());
        }
    };

    public static final ParametersHandle<TimedStatusStartSizeParameter> TIMED_STATUS_START_SIZE_PD_HANDLE = new ParametersHandle<TimedStatusStartSizeParameter>() {
        @Override
        public String handle(TimedStatusStartSizeParameter timedStatusStartSizeParameter) {
            return "?" + formatUrlParam("status", timedStatusStartSizeParameter.getStatus()) + "&"
                    + formatUrlParam("start", timedStatusStartSizeParameter.getStart()) + "&"
                    + formatUrlParam("size", timedStatusStartSizeParameter.getSize()) + "&"
                    + formatUrlParam("st", timedStatusStartSizeParameter.getSt()) + "&"
                    + formatUrlParam("et", timedStatusStartSizeParameter.getEt());
        }
    };

    public static final PostParametersHandle<AppStoreArticle> APPSTORE_ARTICLE_OBJ_PD_HANDLE = new PostParametersHandle<AppStoreArticle>() {
        @Override
        public Map<String, Object> handle(final AppStoreArticle article) {
            return new HashMap<String, Object>() {
                /**
                 * 
                 */
                private static final long serialVersionUID = 2380045896681064025L;

                {
                    put("id", article.getId());
                    put("articleUrl", article.getArticleUrl());
                    put("content", article.getContent());
                    put("contentText", article.getContentText());
                    put("iconUrl", article.getIconUrl());
                    put("relatedRootids", article.getRelatedRootids());
                    put("shortDesc", article.getShortDesc());
                    put("tagId", article.getTagId());
                    put("title", article.getTitle());
                }
            };
        }
    };

    public static final PostParametersHandle<AppStoreFunnyColumnDetailParameter> APPSTORE_COLUMN_ADDPARAMETER_PD_HANDLE = new PostParametersHandle<AppStoreFunnyColumnDetailParameter>() {

        @Override
        public Map<String, Object> handle(final AppStoreFunnyColumnDetailParameter param) {
            return new HashMap<String, Object>() {
                /**
                 * 
                 */
                private static final long serialVersionUID = 8291965983307498589L;
                {
                    put("title", param.getTitle());
                    put("id", param.getId());
                    put("viewUrl", param.getViewUrl());
                    put("viewNoApplicationUrl", param.getViewNoApplicationUrl());
                    put("content", param.getContent());
                    put("contentText", param.getContentText());
                    put("showDate", param.getShowDate());
                    put("authorId", param.getAuthorId());
                    put("ctypeId", param.getCtypeId());
                    put("referenceColumnId", param.getReferenceColumnId());
                    put("publish", param.isPublish());
                    put("currentType", param.getCurrentType());
                    put("noBundleIds", param.getNoBundleIds());
                    put("columnContentId", param.getColumnContentId());
                    put("applicationSetsId", param.getApplicationSetsId());
                    put("applicationDescription", param.getApplicationDescription());
                }
            };
        }
    };


    private static String formatUrlParam(String key, Object value) {
        try {
            if (value == null) {
                return key + "=";
            }
            if (value instanceof String) {
                return key + "=" + URLEncoder.encode(value.toString(), "utf-8");
            }
            return key + "=" + value.toString();
        } catch (UnsupportedEncodingException e) {
            return value == null ? key + "=" : key + "=" + value.toString();
        }
    }

}
