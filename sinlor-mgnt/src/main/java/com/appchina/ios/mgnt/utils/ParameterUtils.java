// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.cahe.model.app.AppResCacheModel;
import com.appchina.ios.core.cahe.model.app.IpaFileResp;
import com.appchina.ios.core.cahe.model.app.RootAppRankSimple;
import com.appchina.ios.core.cahe.model.app.SearchResp;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.crawler.model.AuthoAppDownBoughtInfoResp;
import com.appchina.ios.core.crawler.model.LimitDiscountApplicationSimple;
import com.appchina.ios.core.crawler.model.OnlineAppResult;
import com.appchina.ios.core.dto.account.Account;
import com.appchina.ios.core.dto.account.AccountFeedback;
import com.appchina.ios.core.dto.account.AppleAccount;
import com.appchina.ios.core.dto.account.AppleAccountAutomatedInfo;
import com.appchina.ios.core.dto.account.AppleAccountSource;
import com.appchina.ios.core.dto.account.AppleIdBuyHistory;
import com.appchina.ios.core.dto.account.AppleShareAccount;
import com.appchina.ios.core.dto.account.DispatchConfigure;
import com.appchina.ios.core.dto.account.DispatchDesc;
import com.appchina.ios.core.dto.account.PcSuiteBindAppleIdInfo;
import com.appchina.ios.core.dto.account.PcSuiteConnectedDeviceInfo;
import com.appchina.ios.core.dto.account.PcSuiteUser;
import com.appchina.ios.core.dto.app.AppAppleRank;
import com.appchina.ios.core.dto.app.AppBanned;
import com.appchina.ios.core.dto.app.AppDownloadCodeInfo;
import com.appchina.ios.core.dto.app.AppInstallReportInfo;
import com.appchina.ios.core.dto.app.AppLimitDiscount;
import com.appchina.ios.core.dto.app.AppLimitDiscountIntervention;
import com.appchina.ios.core.dto.app.AppLocale;
import com.appchina.ios.core.dto.app.AppRank;
import com.appchina.ios.core.dto.app.AppRankIntervention;
import com.appchina.ios.core.dto.app.AppTag;
import com.appchina.ios.core.dto.app.AppTagRank;
import com.appchina.ios.core.dto.app.AppTagRankIntervention;
import com.appchina.ios.core.dto.app.AppWishHistory;
import com.appchina.ios.core.dto.app.AppWishInfo;
import com.appchina.ios.core.dto.app.Application;
import com.appchina.ios.core.dto.app.ApplicationItunesImgRes;
import com.appchina.ios.core.dto.app.Banner;
import com.appchina.ios.core.dto.app.Category;
import com.appchina.ios.core.dto.app.ClientUpgradeInfo;
import com.appchina.ios.core.dto.app.DownTypeIntervention;
import com.appchina.ios.core.dto.app.EnterpriseIpaFile;
import com.appchina.ios.core.dto.app.IpaCertSignature;
import com.appchina.ios.core.dto.app.IpaItunesMetaData;
import com.appchina.ios.core.dto.app.IpaLoadStat;
import com.appchina.ios.core.dto.app.IpaPlist;
import com.appchina.ios.core.dto.app.ListAdInfo;
import com.appchina.ios.core.dto.app.ListAdPlace;
import com.appchina.ios.core.dto.app.ListAdRecomInfo;
import com.appchina.ios.core.dto.app.MarketInfo;
import com.appchina.ios.core.dto.app.ProductPageDictionary;
import com.appchina.ios.core.dto.app.PromoteApplication;
import com.appchina.ios.core.dto.app.RootApplication;
import com.appchina.ios.core.dto.app.SearchDefaultResult;
import com.appchina.ios.core.dto.app.SearchHotword;
import com.appchina.ios.core.dto.app.SearchPlaceholder;
import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetail;
import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetailEdit;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumn;
import com.appchina.ios.core.dto.system.AppleColorDict;
import com.appchina.ios.core.dto.system.AppleDevice;
import com.appchina.ios.core.dto.system.AppleOsVersion;
import com.appchina.ios.core.dto.system.ApplePlatform;
import com.appchina.ios.core.dto.system.ClientChannel;
import com.appchina.ios.core.dto.system.ClientConf;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceAuthorizerDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceConnectDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceDriverDetailInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceFullDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteBgLookupInfo;
import com.appchina.ios.core.dto.system.PcSuiteChannel;
import com.appchina.ios.core.dto.system.PcSuiteCommMobileClientInfo;
import com.appchina.ios.core.dto.system.PcSuiteIosProgrammerDriver;
import com.appchina.ios.core.dto.system.PcSuiteIphoneModel;
import com.appchina.ios.core.dto.system.PcSuiteItunesDriver;
import com.appchina.ios.core.dto.system.PcSuiteItunesDriverInstallInfo;
import com.appchina.ios.core.dto.system.PcSuiteLangInfo;
import com.appchina.ios.core.dto.system.PcSuiteUpgradeSwitchIpConf;
import com.appchina.ios.core.dto.system.PcSuiteVersion;
import com.appchina.ios.core.dto.system.PcSuiteWebClipNavigationInfo;
import com.appchina.ios.core.dto.system.SystemEmergency;
import com.appchina.ios.core.utils.AppStoreFunnyColumnDetailUtils;
import com.appchina.ios.mgnt.controller.model.AppAppleRankListParameter;
import com.appchina.ios.mgnt.controller.model.AppBannedParameter;
import com.appchina.ios.mgnt.controller.model.AppEmphasisParameter;
import com.appchina.ios.mgnt.controller.model.AppLimitDiscountListParameter;
import com.appchina.ios.mgnt.controller.model.AppLocaleParameter;
import com.appchina.ios.mgnt.controller.model.AppRankListParameter;
import com.appchina.ios.mgnt.controller.model.AppTagRankListParameter;
import com.appchina.ios.mgnt.controller.model.AppleAccountParamter;
import com.appchina.ios.mgnt.controller.model.AppleAccountSourceParamter;
import com.appchina.ios.mgnt.controller.model.ApplicationModifyParameter;
import com.appchina.ios.mgnt.controller.model.ApplicationParameter;
import com.appchina.ios.mgnt.controller.model.ClientChannelParameter;
import com.appchina.ios.mgnt.controller.model.ClientConfModifyParameter;
import com.appchina.ios.mgnt.controller.model.DispatchConfigureParameter;
import com.appchina.ios.mgnt.controller.model.DispatchConfiigureAddParameter;
import com.appchina.ios.mgnt.controller.model.DispatchDescParameter;
import com.appchina.ios.mgnt.controller.model.DownTypeInterventionParameter;
import com.appchina.ios.mgnt.controller.model.EnterpriseFilePathParameter;
import com.appchina.ios.mgnt.controller.model.IpaPlistModifyParameter;
import com.appchina.ios.mgnt.controller.model.IpaPlistParameter;
import com.appchina.ios.mgnt.controller.model.MarketInfoParameter;
import com.appchina.ios.mgnt.controller.model.OnlineParameter;
import com.appchina.ios.mgnt.controller.model.OsVersionParameter;
import com.appchina.ios.mgnt.controller.model.PlatformOsVersionParameter;
import com.appchina.ios.mgnt.controller.model.PlatformParameter;
import com.appchina.ios.mgnt.controller.model.PlistParameter;
import com.appchina.ios.mgnt.controller.model.ProductPageDictionaryParameter;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.RootApplicationParameter;
import com.appchina.ios.mgnt.controller.model.SearchDefaultResultParameter;
import com.appchina.ios.mgnt.controller.model.SearchHotwordParameter;
import com.appchina.ios.mgnt.controller.model.SearchPlaceholderParameter;
import com.appchina.ios.mgnt.controller.model.SignatureIpasParameter;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.TitleLikeParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreApplicationWrapper;
import com.appchina.ios.mgnt.controller.model.info.AppStoreFunnyColumnDetailParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreRootApplicationPara;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.model.PostParametersHandle;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.model.StartSizeParameter;
import com.appchina.ios.web.utils.CollectionUtils;
import com.appchina.ios.web.utils.GsonUtils;
import com.appchina.ios.web.utils.UrlUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @author luofei@refanqie.com (Your Name Here)
 */
public class ParameterUtils {

    public static final ParametersHandle<List<Integer>> ROOT_APPSIMPLE_HANDLE = new ParametersHandle<List<Integer>>() {

        @Override
        public String handle(List<Integer> values) {
            return "?rootIds=" + CollectionUtils.listToString(values, ",");
        }
    };

    public static final ParametersHandle<List<Integer>> ROOT_LIMIT_DISCOUNT_APPSIMPLE_HANDLE = new ParametersHandle<List<Integer>>() {

        @Override
        public String handle(List<Integer> values) {
            return "?rootIds=" + CollectionUtils.listToString(values, ",");
        }
    };

    public static final ParametersHandle<List<Integer>> ITEMID_APPSIMPLE_HANDLE = new ParametersHandle<List<Integer>>() {

        @Override
        public String handle(List<Integer> values) {
            return "?itemIds=" + CollectionUtils.listToString(values, ",");
        }
    };

    public static final ParametersHandle<String> FUNNY_CLIENT_SPECIAL_COLUMN_HANDLE_PD_HANDLE = new ParametersHandle<String>() {
        @Override
        public String handle(String value) {
            return "?ids=" + value;
        }
    };

    public static final ParametersHandle<AppStoreRootApplicationPara> FUNNY_SEARCH_ROOTAPPLICATION_PD_HANDLE = new ParametersHandle<AppStoreRootApplicationPara>() {
        @Override
        public String handle(AppStoreRootApplicationPara para) {
            return "?bundleId=" + para.getBundleId() + "&itemId=" + para.getItemId();
        }
    };

    public static final ParametersHandle<List<String>> BUNDLEID_APPSIMPLE_HANDLE = new ParametersHandle<List<String>>() {

        @Override
        public String handle(List<String> values) {
            return "?bundleIds=" + CollectionUtils.listToString(values, ",");
        }
    };

    public static final ParametersHandle<StartSizeParameter> START_SIZE_HANDLE = new ParametersHandle<StartSizeParameter>() {
        @Override
        public String handle(StartSizeParameter param) {
            return "?start=" + param.getStart() + "&size=" + param.getSize();
        }
    };

    public static final ParametersHandle<PlistParameter> PLIST_PD_HANDLE = new ParametersHandle<PlistParameter>() {
        @Override
        public String handle(PlistParameter param) {
            return "?start=" + param.getStart() + "&size=" + param.getSize() + "&"
                    + formatUrlParam("rootId", param.getRootId()) + "&" + formatUrlParam("status", param.getStatus());
        }
    };

    public static final ParametersHandle<IpaPlistParameter> IPAPLIST_PD_HANDLE = new ParametersHandle<IpaPlistParameter>() {
        @Override
        public String handle(IpaPlistParameter param) {
            return "?start=" + param.getStart() + "&size=" + param.getSize() + "&"
                    + formatUrlParam("bundleId", param.getBundleId()) + "&"
                    + formatUrlParam("itemId", param.getItemId()) + "&" + formatUrlParam("name", param.getName()) + "&"
                    + formatUrlParam("price", param.getPrice()) + "&" + formatUrlParam("rootId", param.getRootId())
                    + "&" + formatUrlParam("type", param.getType()) + "&"
                    + formatUrlParam("categoryId", param.getCategoryId()) + "&"
                    + formatUrlParam("status", param.getStatus()) + "&" + formatUrlParam("orderBy", param.getOrderBy())
                    + "&" + formatUrlParam("intervene", param.getIntervene()) + "&"
                    + formatUrlParam("ipaSiteId", param.getIpaSiteId());
        }
    };

    public static final ParametersHandle<DownTypeInterventionParameter> DOWNTYPEINTERVENTION_PD_HANDLE = new ParametersHandle<DownTypeInterventionParameter>() {
        @Override
        public String handle(DownTypeInterventionParameter param) {
            return "?start=" + param.getStart() + "&size=" + param.getSize() + "&"
                    + formatUrlParam("url", param.getUrl()) + "&" + formatUrlParam("status", param.getStatus()) + "&"
                    + formatUrlParam("type", param.getType()) + "&" + formatUrlParam("rootId", param.getRootId());

        }
    };

    public static final ParametersHandle<DispatchDescParameter> DISPATCHDESCPARAMETER_PD_HANDLE = new ParametersHandle<DispatchDescParameter>() {
        @Override
        public String handle(DispatchDescParameter param) {
            return "?id=" + param.getId() + "&" + formatUrlParam("desc", param.getDesc());

        }
    };

    public static final ParametersHandle<ClientChannelParameter> CLIENTCHANNELPARAMETER_PD_HANDLE = new ParametersHandle<ClientChannelParameter>() {
        @Override
        public String handle(ClientChannelParameter param) {
            return "?" + formatUrlParam("ids", param.getIds()) + "&" + formatUrlParam("parentId", param.getParentId())
                    + "&" + formatUrlParam("leaf", param.getLeaf()) + "&"
                    + formatUrlParam("channel", param.getChannel()) + "&" + formatUrlParam("desc", param.getDesc())
                    + "&" + formatUrlParam("start", param.getStart()) + "&" + formatUrlParam("size", param.getSize());

        }
    };

    public static final ParametersHandle<TitleLikeParameter> TITLE_LIKE_PD_HANDLE = new ParametersHandle<TitleLikeParameter>() {
        @Override
        public String handle(TitleLikeParameter param) {
            return "?channel=www.&" + formatUrlParam("title", param.getTitle()) + "&"
                    + formatUrlParam("query", param.getQuery()) + "&" + formatUrlParam("cid", param.getCid())
                    + "&start=" + param.getStart() + "&size=" + param.getSize();
        }
    };

    public static final ParametersHandle<PromoteAppsParameter> PROMOTE_PD_HANDLE = new ParametersHandle<PromoteAppsParameter>() {
        @Override
        public String handle(PromoteAppsParameter param) {
            return "?" + formatUrlParam("channel", param.getChannel()) + "&" + formatUrlParam("rank", param.getRank())
                    + "&" + formatUrlParam("rootId", param.getRootId()) + "&" + formatUrlParam("id", param.getId())
                    + "&" + formatUrlParam("ids", param.getIds()) + "&" + formatUrlParam("st", param.getSt()) + "&"
                    + formatUrlParam("et", param.getEt()) + "&" + formatUrlParam("status", param.getStatus()) + "&"
                    + formatUrlParam("start", param.getStart()) + "&" + formatUrlParam("size", param.getSize());

        }
    };

    public static final ParametersHandle<IpaPlistModifyParameter> PLISTMODIFY_PD_HANDLE = new ParametersHandle<IpaPlistModifyParameter>() {
        @Override
        public String handle(IpaPlistModifyParameter params) {
            return "?plistId=" + params.getPlistId() + "&" + formatUrlParam("able", params.getAble()) + "&"
                    + formatUrlParam("intervene", params.getIntervene());
        }
    };

    public static final ParametersHandle<ApplicationParameter> APPLICATION_PD_HANDLE = new ParametersHandle<ApplicationParameter>() {
        @Override
        public String handle(ApplicationParameter param) {
            return "?" + formatUrlParam("rid", param.getRid()) + "&" + formatUrlParam("name", param.getName()) + "&"
                    + formatUrlParam("startTime", param.getStartTime()) + "&"
                    + formatUrlParam("endTime", param.getEndTime()) + "&"
                    + formatUrlParam("minPrice", param.getMinPrice()) + "&"
                    + formatUrlParam("maxPrice", param.getMaxPrice()) + "&"
                    + formatUrlParam("sortType", param.getSortType()) + "&" + formatUrlParam("order", param.getOrder())
                    + "&" + formatUrlParam("orderByString", param.getOrderByString()) + "&"
                    + formatUrlParam("orderString", param.getOrderString()) + "&"
                    + formatUrlParam("start", param.getStart()) + "&" + formatUrlParam("size", param.getSize());
        }
    };

    public static final ParametersHandle<AppEmphasisParameter> APPEMPHASIS_PD_HANDLE = new ParametersHandle<AppEmphasisParameter>() {
        @Override
        public String handle(AppEmphasisParameter param) {
            return "?" + formatUrlParam("rootId", param.getRootId()) + "&"
                    + formatUrlParam("status", param.getStatus()) + "&" + formatUrlParam("start", param.getStart())
                    + "&" + formatUrlParam("size", param.getSize());
        }
    };

    public static final ParametersHandle<AppBannedParameter> APPBANNED_PD_HANDLE = new ParametersHandle<AppBannedParameter>() {
        @Override
        public String handle(AppBannedParameter param) {
            return "?" + formatUrlParam("rootId", param.getRootId()) + "&"
                    + formatUrlParam("adminId", param.getAdminId()) + "&" + formatUrlParam("info", param.getInfo())
                    + "&" + formatUrlParam("status", param.getStatus()) + "&"
                    + formatUrlParam("start", param.getStart()) + "&" + formatUrlParam("size", param.getSize());
        }
    };

    public static final ParametersHandle<StatusStartSizeParameter> STATUSSTARTSIZE_PD_HANDLE = new ParametersHandle<StatusStartSizeParameter>() {
        @Override
        public String handle(StatusStartSizeParameter param) {
            return "?" + formatUrlParam("status", param.getStatus()) + "&" + formatUrlParam("start", param.getStart())
                    + "&" + formatUrlParam("size", param.getSize());
        }
    };

    public static final ParametersHandle<SignatureIpasParameter> SIGNATUREIPASPARAMETER_PD_HANDLE = new ParametersHandle<SignatureIpasParameter>() {
        @Override
        public String handle(SignatureIpasParameter param) {
            return "?" + formatUrlParam("status", param.getStatus()) + "&" + formatUrlParam("start", param.getStart())
                    + "&" + formatUrlParam("size", param.getSize()) + "&"
                    + formatUrlParam("certSerial", param.getCertSerial());
        }
    };
    public static final ParametersHandle<MarketInfoParameter> MARKETINFPARAMETER_PD_HANDLE = new ParametersHandle<MarketInfoParameter>() {
        @Override
        public String handle(MarketInfoParameter param) {
            return "?" + formatUrlParam("id", param.getId()) + "&" + formatUrlParam("name", param.getName()) + "&"
                    + formatUrlParam("site", param.getSite());
        }
    };

    public static final ParametersHandle<ApplicationModifyParameter> APPLICATIONMODIFY_PD_HANDLE = new ParametersHandle<ApplicationModifyParameter>() {
        @Override
        public String handle(ApplicationModifyParameter param) {
            return "?" + formatUrlParam("rootId", param.getRootId()) + "&"
                    + formatUrlParam("editorTitle", param.getEditorTitle()) + "&"
                    + formatUrlParam("editorReview", param.getEditorReview());
        }
    };

    public static final ParametersHandle<ClientConfModifyParameter> CLIENTCONFMODIFY_PD_HANDLE = new ParametersHandle<ClientConfModifyParameter>() {
        @Override
        public String handle(ClientConfModifyParameter param) {
            return "?" + formatUrlParam("id", param.getId()) + "&" + formatUrlParam("type", param.getType()) + "&"
                    + formatUrlParam("value", param.getValue());
        }
    };

    public static final ParametersHandle<SearchHotwordParameter> HOTWORDS_PD_HANDLE = new ParametersHandle<SearchHotwordParameter>() {
        @Override
        public String handle(SearchHotwordParameter param) {
            return "?" + formatUrlParam("id", param.getId()) + "&" + formatUrlParam("name", param.getName()) + "&"
                    + formatUrlParam("rank", param.getRank()) + "&" + formatUrlParam("channel", param.getChannel())
                    + "&status=" + param.getStatus() + "&start=" + param.getStart() + "&size=" + param.getSize();

        }
    };

    public static final ParametersHandle<SearchPlaceholderParameter> PLACEHOLDER_PD_HANDLE = new ParametersHandle<SearchPlaceholderParameter>() {
        @Override
        public String handle(SearchPlaceholderParameter param) {
            return "?" + formatUrlParam("id", param.getId()) + "&" + formatUrlParam("name", param.getName()) + "&"
                    + formatUrlParam("page", param.getPage()) + "&" + formatUrlParam("channel", param.getChannel())
                    + "&status=" + param.getStatus() + "&start=" + param.getStart() + "&size=" + param.getSize();

        }
    };

    public static final ParametersHandle<SearchDefaultResultParameter> DEFAULT_RESULT_PD_HANDLE = new ParametersHandle<SearchDefaultResultParameter>() {
        @Override
        public String handle(SearchDefaultResultParameter param) {
            return "?" + formatUrlParam("id", param.getId()) + "&" + formatUrlParam("rootId", param.getRootId()) + "&"
                    + formatUrlParam("rank", param.getRank()) + "&" + formatUrlParam("channel", param.getChannel())
                    + "&status=" + param.getStatus() + "&start=" + param.getStart() + "&size=" + param.getSize();

        }
    };

    public static final ParametersHandle<ProductPageDictionaryParameter> PAGEDICTIONARY_PD_HANDLE = new ParametersHandle<ProductPageDictionaryParameter>() {
        @Override
        public String handle(ProductPageDictionaryParameter param) {
            return "?" + formatUrlParam("page", param.getPage()) + "&" + formatUrlParam("desc", param.getDesc()) + "&"
                    + formatUrlParam("channel", param.getChannel());

        }
    };

    public static final ParametersHandle<AppRankListParameter> APPRANKLIST_PD_HANDLE = new ParametersHandle<AppRankListParameter>() {
        @Override
        public String handle(AppRankListParameter param) {
            return "?" + formatUrlParam("id", param.getId()) + "&" + formatUrlParam("type", param.getType()) + "&"
                    + formatUrlParam("cid", param.getCid()) + "&" + formatUrlParam("status", param.getStatus()) + "&"
                    + formatUrlParam("rootId", param.getRootId()) + "&" + formatUrlParam("rank", param.getRank()) + "&"
                    + formatUrlParam("start", param.getStart()) + "&" + formatUrlParam("size", param.getSize());

        }
    };

    public static final ParametersHandle<AppLimitDiscountListParameter> APPLIMITDISCOUNTLIST_PD_HANDLE = new ParametersHandle<AppLimitDiscountListParameter>() {
        @Override
        public String handle(AppLimitDiscountListParameter param) {
            return "?" + formatUrlParam("id", param.getId()) + "&" + formatUrlParam("type", param.getType()) + "&"
                    + formatUrlParam("status", param.getStatus()) + "&" + formatUrlParam("rootId", param.getRootId())
                    + "&" + formatUrlParam("rank", param.getRank()) + "&" + formatUrlParam("start", param.getStart())
                    + "&" + formatUrlParam("size", param.getSize());

        }
    };
    public static final ParametersHandle<AppStoreFunnyColumnDetailParameter> FUNNY_COLUMN_PD_HANDLE = new ParametersHandle<AppStoreFunnyColumnDetailParameter>() {
        @Override
        public String handle(AppStoreFunnyColumnDetailParameter param) {
            return "?"
                    + formatUrlParam("type", param.getType())
                    + "&"
                    + formatUrlParam("start", param.getStart())
                    + "&"
                    + formatUrlParam("size", param.getSize())
                    + "&"
                    + formatUrlParam("id", param.getId())
                    + "&"
                    + formatUrlParam("title", param.getTitle())
                    + "&"
                    + formatUrlParam(
                            "ctypeId",
                            (param != null && param.getCtypeId() != null
                                    && param.getCtypeId().equals(AppStoreFunnyColumnDetailUtils.FUNNY_ALL) ? null
                                    : param.getCtypeId()))
                    + "&"
                    + formatUrlParam(
                            "applicationSetsTypeId",
                            (param != null
                                    && param.getApplicationSetsTypeId() != null
                                    && param.getApplicationSetsTypeId().equals(
                                            AppStoreFunnyColumnDetailUtils.FUNNY_ALL_INTEGER) ? null : param
                                    .getApplicationSetsTypeId()))
                    + "&"
                    + formatUrlParam("status", (param != null && param.getStatus() != null
                            && param.getStatus() == Integer.valueOf(AppStoreFunnyColumnDetailUtils.FUNNY_ALL_INTEGER)
                            ? null : param.getStatus()))
                    + "&"
                    + formatUrlParam(
                            "contentSrc",
                            (param != null && param.getContentSrc() != null && param.getContentSrc().equals(
                                    AppStoreFunnyColumnDetailUtils.FUNNY_ALL_INTEGER)) ? null : param.getContentSrc())
                    + "&"
                    + formatUrlParam(
                            "authorId",
                            (param != null && param.getAuthorId() != null
                                    && param.getAuthorId().equals(AppStoreFunnyColumnDetailUtils.FUNNY_ALL) ? null
                                    : param.getAuthorId())) + "&" + formatUrlParam("showDate", param.getShowDate());
        }
    };

    public static final ParametersHandle<Object> DEFAULT_PD_HANDLE = new ParametersHandle<Object>() {
        @Override
        public String handle(Object t) {
            if (t == null) {
                return "";
            }
            return GsonUtils.toJson(t);
        }
    };

    public static final PostParametersHandle<OnlineParameter> ONLINE_PD_HANDLE = new PostParametersHandle<OnlineParameter>() {
        @Override
        public Map<String, Object> handle(final OnlineParameter param) {
            return new HashMap<String, Object>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("pwd", "16021incloud");
                    if (param.getItemId() != null) {
                        put("itemId", param.getItemId());
                    }
                    if (!StringUtils.isEmpty(param.getUrl())) {
                        put("url", param.getUrl());
                    }
                    if (!StringUtils.isEmpty(param.getLocale())) {
                        put("locale", param.getLocale());
                    }
                }
            };
        }
    };

    public static final ParametersHandle<EnterpriseFilePathParameter> ENTERPRISEFILEPATH_PD_HANDLE = new ParametersHandle<EnterpriseFilePathParameter>() {
        @Override
        public String handle(EnterpriseFilePathParameter param) {
            return "?sign=" + param.getSign() + "&md5=" + param.getMd5();

        }
    };
    public static final ParametersHandle<Integer> ID_PD_HANDLE = new ParametersHandle<Integer>() {
        @Override
        public String handle(Integer param) {
            return "?channel=www.&id=" + param.intValue();

        }
    };

    public static final ParametersHandle<PlatformParameter> PLATFORMPARAMETER_PD_HANDLE = new ParametersHandle<PlatformParameter>() {
        @Override
        public String handle(PlatformParameter param) {
            return "?" + formatUrlParam("platform", param.getPlatform()) + "&"
                    + formatUrlParam("content", param.getContent()) + "&"
                    + formatUrlParam("defaultStatus", param.getDefaultStatus());

        }
    };

    public static final ParametersHandle<Integer> ROOTAPPLICATION_ROOTID_PD_HANDLE = new ParametersHandle<Integer>() {
        @Override
        public String handle(Integer rootId) {
            return "?rootid=" + rootId;
        }
    };

    public static final ParametersHandle<AppleAccountParamter> APPLEACCOUNTPARAMETER_PD_HANDLE = new ParametersHandle<AppleAccountParamter>() {
        @Override
        public String handle(AppleAccountParamter param) {
            return "?" + formatUrlParam("bind", param.getBind()) + "&" + formatUrlParam("source", param.getSource())
                    + "&" + formatUrlParam("status", param.getStatus()) + "&"
                    + formatUrlParam("start", param.getStart()) + "&" + formatUrlParam("size", param.getSize());

        }
    };

    public static final ParametersHandle<AppleAccountSourceParamter> APPLEACCOUNTSOURCEPARAMETER_PD_HANDLE = new ParametersHandle<AppleAccountSourceParamter>() {
        @Override
        public String handle(AppleAccountSourceParamter param) {
            return "?" + formatUrlParam("desc", param.getDesc()) + "&" + formatUrlParam("url", param.getUrl());
        }
    };
    public static final ParametersHandle<DispatchConfiigureAddParameter> DISPATCHCONFIIGUREADDPARAMETER_PD_HANDLE = new ParametersHandle<DispatchConfiigureAddParameter>() {
        @Override
        public String handle(DispatchConfiigureAddParameter param) {
            return "?" + formatUrlParam("st", param.getSt()) + "&" + formatUrlParam("et", param.getEt()) + "&"
                    + formatUrlParam("dayDispatchs", param.getDayDispatchs()) + "&"
                    + formatUrlParam("channel", param.getChannel()) + "&"
                    + formatUrlParam("rewrite", param.isRewrite()) + "&" + formatUrlParam("reinit", param.isReinit());
        }
    };

    public static final ParametersHandle<DispatchConfigureParameter> DISPATCHCONFIGUREPARAMETER_PD_HANDLE = new ParametersHandle<DispatchConfigureParameter>() {
        @Override
        public String handle(DispatchConfigureParameter param) {
            return "?" + formatUrlParam("channel", param.getChannel()) + "&"
                    + formatUrlParam("status", param.getStatus());
        }
    };
    public static final ParametersHandle<AppAppleRankListParameter> APPAPPLERANKLISTPARAMETER_PD_HANDLE = new ParametersHandle<AppAppleRankListParameter>() {
        @Override
        public String handle(AppAppleRankListParameter param) {
            return UrlUtils.objectToUrlQuery(param);
        }
    };

    public static final ParametersHandle<AppLocaleParameter> APPLOCALEPARAMETER_PD_HANDLE = new ParametersHandle<AppLocaleParameter>() {
        @Override
        public String handle(AppLocaleParameter param) {
            return UrlUtils.objectToUrlQuery(param);
        }
    };

    public static final ParametersHandle<OsVersionParameter> OSVERSIONPARAMETER_PD_HANDLE = new ParametersHandle<OsVersionParameter>() {
        @Override
        public String handle(OsVersionParameter param) {
            return "?" + formatUrlParam("osVersion", param.getOsVersion()) + "&"
                    + formatUrlParam("defaultStatus", param.getDefaultStatus());

        }
    };

    public static final ParametersHandle<PlatformOsVersionParameter> PLATFORMOSVERSIONPARAMETER_PD_HANDLE = new ParametersHandle<PlatformOsVersionParameter>() {
        @Override
        public String handle(PlatformOsVersionParameter param) {
            return "?" + formatUrlParam("id", param.getId()) + "&" + formatUrlParam("type", param.getType()) + "&"
                    + formatUrlParam("status", param.getStatus()) + "&"
                    + formatUrlParam("defaultStatus", param.getDefaultStatus());

        }
    };

    public static final ParametersHandle<List<Integer>> IDS_PD_HANDLE = ParametersHandle.PS_IDS_HANDLE;

    public static final ParametersHandle<Integer> ROOTID_PD_HANDLE = new ParametersHandle<Integer>() {
        @Override
        public String handle(Integer rootId) {
            return "?rootId=" + rootId;
        }
    };
    public static final ParametersHandle<AppTagRankListParameter> APPTAGRANKLIST_PD_HANDLE = new ParametersHandle<AppTagRankListParameter>() {
        @Override
        public String handle(AppTagRankListParameter param) {
            return "?" + formatUrlParam("id", param.getId()) + "&" + formatUrlParam("type", param.getType()) + "&"
                    + formatUrlParam("cid", param.getCid()) + "&" + formatUrlParam("status", param.getStatus()) + "&"
                    + formatUrlParam("rootId", param.getRootId()) + "&" + formatUrlParam("rank", param.getRank()) + "&"
                    + formatUrlParam("start", param.getStart()) + "&" + formatUrlParam("size", param.getSize());
        }
    };

    public static final ParametersHandle<List<Integer>> AUTHOR_APPDOWN_BOUGHT_INFO_RESP_PD_HANDLE = new ParametersHandle<List<Integer>>() {
        @Override
        public String handle(List<Integer> values) {
            return "?rootIds=" + CollectionUtils.listToString(values, ",");
        }
    };

    public static final ParametersHandle<List<Integer>> INTEGER_ROOTIDS_HANDLE = new ParametersHandle<List<Integer>>() {
        @Override
        public String handle(List<Integer> values) {
            return "?rootIds=" + CollectionUtils.listToString(values, ",");
        }
    };

    public static final ParametersHandle<RootApplicationParameter> ROOTAPPLICATION_PD_HANDLE = new ParametersHandle<RootApplicationParameter>() {
        @Override
        public String handle(RootApplicationParameter param) {
            return "?" + formatUrlParam("rootId", param.getRootId()) + "&" + formatUrlParam("title", param.getTitle())
                    + "&" + formatUrlParam("appType", param.getAppType()) + "&"
                    + formatUrlParam("sortType", param.getSortType()) + "&" + formatUrlParam("order", param.getOrder())
                    + "&" + formatUrlParam("orderByString", param.getOrderByString()) + "&"
                    + formatUrlParam("orderString", param.getOrderString()) + "&"
                    + formatUrlParam("start", param.getStart()) + "&" + formatUrlParam("size", param.getSize());
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppTagRank>>> APPTAGRANK_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppTagRank>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppTagRank>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppTagRank>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppTagRankIntervention>>> APPTAGRANKINTERVENTION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppTagRankIntervention>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppTagRankIntervention>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppTagRankIntervention>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<SystemEmergency>>> SYSTEM_EMERGENCY_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<SystemEmergency>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<SystemEmergency>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<SystemEmergency>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<SystemEmergency>> SYSTEM_EMERGENCY_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<SystemEmergency>>() {
        @Override
        public ApiRespWrapper<SystemEmergency> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<SystemEmergency>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppTag>>> APPTAGLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppTag>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppTag>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppTag>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteItunesDriverInstallInfo>>> PCSUITEITUNEWDLLLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteItunesDriverInstallInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteItunesDriverInstallInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteItunesDriverInstallInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteVersion>>> PCSUITEVERSIONLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteVersion>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteVersion>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteVersion>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<? extends ApiRespWrapper<PcSuiteVersion>> PCSUITEVERSION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<PcSuiteVersion>>() {
        @Override
        public ApiRespWrapper<PcSuiteVersion> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<PcSuiteVersion>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteUser>>> PCSUITEUSER_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteUser>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteUser>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteUser>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteConnectedDeviceInfo>>> PCSUITECONNECTEDDEVICEINFO_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteConnectedDeviceInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteConnectedDeviceInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteConnectedDeviceInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteChannel>>> PCSUITECHANNELLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteChannel>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteChannel>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteChannel>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteIphoneModel>>> PCSUITEIPHONEMODELLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteIphoneModel>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteIphoneModel>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteIphoneModel>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteIosProgrammerDriver>>> PCSUITEIOSPROGRAMMERDRIVERLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteIosProgrammerDriver>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteIosProgrammerDriver>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteIosProgrammerDriver>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppleDevice>>> APPLEDEVICELIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleDevice>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppleDevice>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppleDevice>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppleShareAccount>>> APPLESHAREACCOUNTLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleShareAccount>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppleShareAccount>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppleShareAccount>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<? extends ApiRespWrapper<AppleShareAccount>> APPLESHAREACCOUNT_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppleShareAccount>>() {
        @Override
        public ApiRespWrapper<AppleShareAccount> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppleShareAccount>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };


    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppleIdBuyHistory>>> APPLEIDBUYHISTORYLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleIdBuyHistory>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppleIdBuyHistory>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppleIdBuyHistory>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppWishInfo>>> APPWISHINFO_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppWishInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppWishInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppWishInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppWishHistory>>> APPWISHHISTORY_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppWishHistory>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppWishHistory>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppWishHistory>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AccountFeedback>>> ACCOUNTFEEDBACK_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AccountFeedback>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AccountFeedback>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AccountFeedback>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<ListAdInfo>>> LISTADINFO_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<ListAdInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<ListAdInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<ListAdInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<ListAdRecomInfo>>> LISTADRECOMINFO_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<ListAdRecomInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<ListAdRecomInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<ListAdRecomInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<Account>>> ACCOUNT_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<Account>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<Account>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<Account>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppleAccountAutomatedInfo>>> APPLEACCOUNTAUTOMATEDINFO_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleAccountAutomatedInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppleAccountAutomatedInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppleAccountAutomatedInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<AppleAccountAutomatedInfo>> APPLEACCOUNTAUTOMATEDINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppleAccountAutomatedInfo>>() {
        @Override
        public ApiRespWrapper<AppleAccountAutomatedInfo> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppleAccountAutomatedInfo>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<ListAdPlace>>> LISTADPLACE_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<ListAdPlace>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<ListAdPlace>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<ListAdPlace>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<AccountFeedback>> ACCOUNTFEEDBACK_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AccountFeedback>>() {
        @Override
        public ApiRespWrapper<AccountFeedback> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AccountFeedback>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<AppleIdBuyHistory>> APPLEIDBUYHISTORY_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppleIdBuyHistory>>() {
        @Override
        public ApiRespWrapper<AppleIdBuyHistory> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppleIdBuyHistory>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };


    public static final ReturnDataHandle<? extends ApiRespWrapper<PcSuiteIphoneModel>> PCSUITEIPHONEMODELL_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<PcSuiteIphoneModel>>() {
        @Override
        public ApiRespWrapper<PcSuiteIphoneModel> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<PcSuiteIphoneModel>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteItunesDriver>>> PCSUITEITUNESDRIVERLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteItunesDriver>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteItunesDriver>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteItunesDriver>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppleColorDict>>> APPLECOLORDICT_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleColorDict>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppleColorDict>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppleColorDict>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteCommMobileClientInfo>>> PCSUITECOMMMOBILECLIENTINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteCommMobileClientInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteCommMobileClientInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteCommMobileClientInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>>> PCSUITEAPPLEDEVICEDRIVERINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceConnectDriverInfo>>> PCSUITEAPPLEDEVICECONNECTDRIVERINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceConnectDriverInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceConnectDriverInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceConnectDriverInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceAuthorizerDriverInfo>>> PCSUITEAPPLEDEVICEAUTHORIZERDRIVERINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceAuthorizerDriverInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceAuthorizerDriverInfo>> handle(String value)
                throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceAuthorizerDriverInfo>>>() {}
                    .getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceFullDriverInfo>>> PCSUITEAPPLEDEVICEFULLDRIVERINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceFullDriverInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceFullDriverInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceFullDriverInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteUpgradeSwitchIpConf>>> PCSUITEUPGRADESWITCHIPCONF_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteUpgradeSwitchIpConf>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteUpgradeSwitchIpConf>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteUpgradeSwitchIpConf>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteLangInfo>>> PCSUITELANGINFO_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteLangInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteLangInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteLangInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<PcSuiteWebClipNavigationInfo>> PCSUITEWEBCLIPNAVIGATIONINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<PcSuiteWebClipNavigationInfo>>() {
        @Override
        public ApiRespWrapper<PcSuiteWebClipNavigationInfo> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<PcSuiteWebClipNavigationInfo>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverDetailInfo>>> PCSUITEAPPLEDEVICEDRIVERDETAILINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverDetailInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverDetailInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverDetailInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<PcSuiteBgLookupInfo>>> PCSUITEBGLOOKUPINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteBgLookupInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteBgLookupInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteBgLookupInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<PcSuiteItunesDriver>> PCSUITEITUNESDRIVER_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<PcSuiteItunesDriver>>() {
        @Override
        public ApiRespWrapper<PcSuiteItunesDriver> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<PcSuiteItunesDriver>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<? extends ApiRespWrapper<Map<String, String>>> ROOTAPPRANKMETATYPE_MAP_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<String, String>>>() {
        @Override
        public ApiRespWrapper<Map<String, String>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<String, String>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<RootAppRankSimple>>> ROOTAPPRANKSIMPLE_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<RootAppRankSimple>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<RootAppRankSimple>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<RootAppRankSimple>>>() {}.getType();
            return GsonUtils.convert(value, type);
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

    public static final ReturnDataHandle<ApiRespWrapper<Map<Integer, ApplicationSimple>>> APP_SIMPLE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<Integer, ApplicationSimple>>>() {
        @Override
        public ApiRespWrapper<Map<Integer, ApplicationSimple>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<Integer, ApplicationSimple>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Map<Integer, LimitDiscountApplicationSimple>>> LIMIT_DISCOUNT_APP_SIMPLE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<Integer, LimitDiscountApplicationSimple>>>() {
        @Override
        public ApiRespWrapper<Map<Integer, LimitDiscountApplicationSimple>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<Integer, LimitDiscountApplicationSimple>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Map<String, ApplicationSimple>>> BUNDLE_SIMPLE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<String, ApplicationSimple>>>() {
        @Override
        public ApiRespWrapper<Map<String, ApplicationSimple>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<String, ApplicationSimple>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<IpaPlist>>> IPAPLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<IpaPlist>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<IpaPlist>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<IpaPlist>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<IpaLoadStat>>> IPALOADSTATLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<IpaLoadStat>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<IpaLoadStat>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<IpaLoadStat>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<IpaItunesMetaData>>> IPAITUNESMETADATALIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<IpaItunesMetaData>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<IpaItunesMetaData>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<IpaItunesMetaData>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<EnterpriseIpaFile>>> ENTERPRISEIPAFILELIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<EnterpriseIpaFile>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<EnterpriseIpaFile>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<EnterpriseIpaFile>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<DownTypeIntervention>>> DOWNTYPEINTERVENTION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<DownTypeIntervention>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<DownTypeIntervention>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<DownTypeIntervention>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<DispatchDesc>>> DISPATCHDESC_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<DispatchDesc>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<DispatchDesc>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<DispatchDesc>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<ClientChannel>>> CLIENTCHANNEL_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<ClientChannel>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<ClientChannel>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<ClientChannel>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleAccount>>> APPLEACCOUNT_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleAccount>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppleAccount>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppleAccount>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteBindAppleIdInfo>>> PCSUITEBINDAPPLEIDINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PcSuiteBindAppleIdInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PcSuiteBindAppleIdInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PcSuiteBindAppleIdInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleAccountSource>>> APPLEACCOUNTSOURCE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleAccountSource>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppleAccountSource>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppleAccountSource>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<DispatchConfigure>>> DISPATCHCONFIGURE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<DispatchConfigure>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<DispatchConfigure>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<DispatchConfigure>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppLocale>>> APPLOCALE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppLocale>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppLocale>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppLocale>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<MarketInfo>>> MARKETINFOS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<MarketInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<MarketInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<MarketInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<ApplicationSimple>>> APP_SIMPLES_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<ApplicationSimple>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<ApplicationSimple>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<ApplicationSimple>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppBanned>>> APPBANNEDS_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppBanned>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppBanned>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppBanned>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppAppleRank>>> APPAPPLERANK_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppAppleRank>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppAppleRank>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppAppleRank>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<ClientUpgradeInfo>>> CLIENTUPGRADEINFOS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<ClientUpgradeInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<ClientUpgradeInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<ClientUpgradeInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<SearchResp>> SEARCH_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<SearchResp>>() {
        @Override
        public ApiRespWrapper<SearchResp> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<SearchResp>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<PromoteApplication>>> PROMTOE_APPS_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<PromoteApplication>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<PromoteApplication>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<PromoteApplication>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<Banner>>> BANNER_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<Banner>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<Banner>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<Banner>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<SearchHotword>>> SEARCHHOTWORDS_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<SearchHotword>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<SearchHotword>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<SearchHotword>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<SearchPlaceholder>>> SEARCHPLACEHOLDER_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<SearchPlaceholder>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<SearchPlaceholder>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<SearchPlaceholder>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppRankIntervention>>> APPRANKINTERVENTION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppRankIntervention>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppRankIntervention>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppRankIntervention>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppRank>>> APPRANK_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppRank>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppRank>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppRank>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppLimitDiscount>>> APPLIMITDISCOUNT_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppLimitDiscount>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppLimitDiscount>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppLimitDiscount>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppLimitDiscountIntervention>>> APPLIMITDISCOUNTINTERVENTION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppLimitDiscountIntervention>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppLimitDiscountIntervention>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppLimitDiscountIntervention>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<SearchDefaultResult>>> SEARCHDEFAULTRESULT_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<SearchDefaultResult>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<SearchDefaultResult>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<SearchDefaultResult>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<ProductPageDictionary>>> PRODUCTPAGEDICTIONARY_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<ProductPageDictionary>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<ProductPageDictionary>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<ProductPageDictionary>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Map<String, String>>> MAP_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<String, String>>>() {
        @Override
        public ApiRespWrapper<Map<String, String>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<String, String>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Map<String, IpaPlist>>> MAPIPAPLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<String, IpaPlist>>>() {
        @Override
        public ApiRespWrapper<Map<String, IpaPlist>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<String, IpaPlist>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<String>> STRING_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<String>>() {
        @Override
        public ApiRespWrapper<String> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<String>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<String>>> STRINGS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<String>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<String>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<String>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<List<String>>> STRINGLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<List<String>>>() {
        @Override
        public ApiRespWrapper<List<String>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<List<String>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Boolean>> BOOLEAN_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Boolean>>() {
        @Override
        public ApiRespWrapper<Boolean> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Boolean>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<RootApplication>> ROOTAPPLICATION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<RootApplication>>() {
        @Override
        public ApiRespWrapper<RootApplication> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<RootApplication>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<ApiRespWrapper<ClientConf>> CLIENTCONF_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ClientConf>>() {
        @Override
        public ApiRespWrapper<ClientConf> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ClientConf>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<ApiRespWrapper<IpaFileResp>> IPAFILERESP_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<IpaFileResp>>() {
        @Override
        public ApiRespWrapper<IpaFileResp> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<IpaFileResp>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<ApplePlatform>>> APPLEPLATFORM_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<ApplePlatform>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<ApplePlatform>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<ApplePlatform>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleOsVersion>>> APPLEOSVERSION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppleOsVersion>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppleOsVersion>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppleOsVersion>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Application>> APPLICATION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Application>>() {
        @Override
        public ApiRespWrapper<Application> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Application>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<Application>>> APPLICATIONS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<Application>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<Application>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<Application>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<IpaCertSignature>>> IPACERTSIGNATURELIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<IpaCertSignature>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<IpaCertSignature>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<IpaCertSignature>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<IpaCertSignature>> IPACERTSIGNATURE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<IpaCertSignature>>() {
        @Override
        public ApiRespWrapper<IpaCertSignature> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<IpaCertSignature>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Category>> CATEGORY_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Category>>() {
        @Override
        public ApiRespWrapper<Category> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Category>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<ApplicationItunesImgRes>>> RESES_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<ApplicationItunesImgRes>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<ApplicationItunesImgRes>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<ApplicationItunesImgRes>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<Category>>> CATEGORYS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<Category>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<Category>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<Category>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<AppResCacheModel>> APPRESCACHE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppResCacheModel>>() {
        @Override
        public ApiRespWrapper<AppResCacheModel> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppResCacheModel>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Integer>> INTEGER_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Integer>>() {
        @Override
        public ApiRespWrapper<Integer> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Integer>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };


    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<Banner>>> BANNERS_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<Banner>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<Banner>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<Banner>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };


    public static final ReturnDataHandle<ApiRespWrapper<Banner>> BANNER_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Banner>>() {
        @Override
        public ApiRespWrapper<Banner> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Banner>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppInstallReportInfo>>> APP_INSTALL_REPORT_INFO = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppInstallReportInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppInstallReportInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppInstallReportInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppDownloadCodeInfo>>> APP_DOWNLOAD_CODE_INFO = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppDownloadCodeInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppDownloadCodeInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppDownloadCodeInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>>> AUTHOR_APPDOWN_BOUGHT_INFO_RESP_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<RootApplication>>> ROOTAPPLICATION_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<RootApplication>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<RootApplication>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<RootApplication>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final PostParametersHandle<Object> DATAS_POST_HANDLE = new PostParametersHandle<Object>() {
        @Override
        public Map<String, Object> handle(Object value) {
            if (value == null) {
                return null;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            if (value instanceof String) {
                map.put("datas", value.toString());
            } else if (value instanceof List) {
                List<?> values = (List<?>) value;
                map.put("datas", GsonUtils.listToJsonStr(values));
            } else {
                map.put("datas", GsonUtils.toJsonStr(value));
            }
            return map;
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<OnlineAppResult>> ONLINEAPPRESULT_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<OnlineAppResult>>() {
        @Override
        public ApiRespWrapper<OnlineAppResult> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<OnlineAppResult>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<RootApplication>>> ROOTAPPLICATIONS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<RootApplication>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<RootApplication>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<RootApplication>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetail>>> FUNNY_COLUMN_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetail>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetail>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetail>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetailEdit>>> FUNNY_COLUMN_EDIT_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetailEdit>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetailEdit>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetailEdit>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>>> FUNNY_CLIENT_SPECIAL_COLUMN_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<RootApplication>> FUNNY_SEARCH_APPLICATION_HANDLE = new ReturnDataHandle<ApiRespWrapper<RootApplication>>() {

        @Override
        public ApiRespWrapper<RootApplication> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<RootApplication>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<AppStoreApplicationWrapper>> ROOTAPPLICATIONWRAPPER_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppStoreApplicationWrapper>>() {

        @Override
        public ApiRespWrapper<AppStoreApplicationWrapper> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppStoreApplicationWrapper>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };


    public static String formatColor(String color) {
        if (color.startsWith("#")) {
            color = color.replace("#", "");
        }
        return color;
    }

    public static String getRespMessage(ApiRespWrapper<Boolean> resp) {
        if (resp == null) {
            return "未知网络错误.";
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            return StringUtils.isEmpty(resp.getMessage()) ? "未知服务器错误.ErrCode:" + resp.getStatus() : resp.getMessage();
        }
        return "";
    }
}
