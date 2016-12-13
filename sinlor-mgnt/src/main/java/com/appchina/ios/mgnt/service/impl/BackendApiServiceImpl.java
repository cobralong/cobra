// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.
package com.appchina.ios.mgnt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.appchina.ios.core.cahe.model.app.RootAppRankSimple;
import com.appchina.ios.core.cahe.model.app.SearchResp;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.crawler.model.AuthoAppDownBoughtInfoResp;
import com.appchina.ios.core.crawler.model.LimitDiscountApplicationSimple;
import com.appchina.ios.core.dto.account.Account;
import com.appchina.ios.core.dto.account.AccountFavorite;
import com.appchina.ios.core.dto.account.AccountFeedback;
import com.appchina.ios.core.dto.account.AccountWishInfo;
import com.appchina.ios.core.dto.account.AccountWishProgressInfo;
import com.appchina.ios.core.dto.account.AppleAccount;
import com.appchina.ios.core.dto.account.AppleAccountAutomatedInfo;
import com.appchina.ios.core.dto.account.AppleAccountSource;
import com.appchina.ios.core.dto.account.AppleIdBuyHistory;
import com.appchina.ios.core.dto.account.AppleShareAccount;
import com.appchina.ios.core.dto.account.DispatchConfigure;
import com.appchina.ios.core.dto.account.DispatchDesc;
import com.appchina.ios.core.dto.account.PcSuiteBindAppleIdInfo;
import com.appchina.ios.core.dto.app.AppAppleRank;
import com.appchina.ios.core.dto.app.AppBanned;
import com.appchina.ios.core.dto.app.AppDownloadCodeInfo;
import com.appchina.ios.core.dto.app.AppInstallReportInfo;
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
import com.appchina.ios.core.dto.app.RootApplicationTag;
import com.appchina.ios.core.dto.app.SearchDefaultResult;
import com.appchina.ios.core.dto.app.SearchHotword;
import com.appchina.ios.core.dto.app.SearchPlaceholder;
import com.appchina.ios.core.dto.system.AppleColorDict;
import com.appchina.ios.core.dto.system.AppleDevice;
import com.appchina.ios.core.dto.system.AppleOsVersion;
import com.appchina.ios.core.dto.system.ApplePlatform;
import com.appchina.ios.core.dto.system.ClientApnCertInfo;
import com.appchina.ios.core.dto.system.ClientChannel;
import com.appchina.ios.core.dto.system.ClientConf;
import com.appchina.ios.core.dto.system.PcSuiteBgLookupInfo;
import com.appchina.ios.core.dto.system.PcSuiteIosProgrammerDriver;
import com.appchina.ios.core.dto.system.PcSuiteIphoneModel;
import com.appchina.ios.core.dto.system.PcSuiteItunesDriver;
import com.appchina.ios.core.dto.system.PcSuiteItunesDriverInstallInfo;
import com.appchina.ios.core.dto.system.SystemEmergency;
import com.appchina.ios.core.utils.ReturnDataHandleUtils;
import com.appchina.ios.mgnt.controller.model.AccountFeedbackParameter;
import com.appchina.ios.mgnt.controller.model.AccountParamter;
import com.appchina.ios.mgnt.controller.model.AccountWishInfoParameter;
import com.appchina.ios.mgnt.controller.model.AccountWishProgressInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppAppleRankListParameter;
import com.appchina.ios.mgnt.controller.model.AppBannedParameter;
import com.appchina.ios.mgnt.controller.model.AppEmphasisParameter;
import com.appchina.ios.mgnt.controller.model.AppInstallReportInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppLocaleParameter;
import com.appchina.ios.mgnt.controller.model.AppRankListParameter;
import com.appchina.ios.mgnt.controller.model.AppTagListParameter;
import com.appchina.ios.mgnt.controller.model.AppTagRankListParameter;
import com.appchina.ios.mgnt.controller.model.AppWishParameter;
import com.appchina.ios.mgnt.controller.model.AppleAccountAutomatedInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAccountParamter;
import com.appchina.ios.mgnt.controller.model.AppleAccountSourceParamter;
import com.appchina.ios.mgnt.controller.model.AppleColorDictParameter;
import com.appchina.ios.mgnt.controller.model.AppleDeviceParameter;
import com.appchina.ios.mgnt.controller.model.AppleIdBuyHistoryParamter;
import com.appchina.ios.mgnt.controller.model.AppleShareAccountParamter;
import com.appchina.ios.mgnt.controller.model.ApplicationModifyParameter;
import com.appchina.ios.mgnt.controller.model.ApplicationParameter;
import com.appchina.ios.mgnt.controller.model.BannerAddParameter;
import com.appchina.ios.mgnt.controller.model.ClientApnCertInfoParameter;
import com.appchina.ios.mgnt.controller.model.ClientChannelParameter;
import com.appchina.ios.mgnt.controller.model.ClientConfModifyParameter;
import com.appchina.ios.mgnt.controller.model.ClientUpgradeInfoParameter;
import com.appchina.ios.mgnt.controller.model.DispatchConfigureParameter;
import com.appchina.ios.mgnt.controller.model.DispatchConfiigureAddParameter;
import com.appchina.ios.mgnt.controller.model.DispatchDescParameter;
import com.appchina.ios.mgnt.controller.model.DownTypeInterventionParameter;
import com.appchina.ios.mgnt.controller.model.IdStatusParameter;
import com.appchina.ios.mgnt.controller.model.IpaLoadStatParameter;
import com.appchina.ios.mgnt.controller.model.IpaPlistModifyParameter;
import com.appchina.ios.mgnt.controller.model.IpaPlistParameter;
import com.appchina.ios.mgnt.controller.model.IpaUploadListParameter;
import com.appchina.ios.mgnt.controller.model.ListAdInfoParameter;
import com.appchina.ios.mgnt.controller.model.ListAdRecomInfoParameter;
import com.appchina.ios.mgnt.controller.model.MarketInfoParameter;
import com.appchina.ios.mgnt.controller.model.OsVersionParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteBindAppleIdInfoParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteIosProgrammerDriverParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteIphoneModelParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteItunesDriverInstallInfoParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteItunesDriverParameter;
import com.appchina.ios.mgnt.controller.model.PlatformOsVersionParameter;
import com.appchina.ios.mgnt.controller.model.PlatformParameter;
import com.appchina.ios.mgnt.controller.model.ProductPageDictionaryParameter;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankAddParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankInterventionParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankInterventionUpdateParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankParameter;
import com.appchina.ios.mgnt.controller.model.RootApplicationParameter;
import com.appchina.ios.mgnt.controller.model.SearchDefaultResultParameter;
import com.appchina.ios.mgnt.controller.model.SearchHotwordParameter;
import com.appchina.ios.mgnt.controller.model.SearchPlaceholderParameter;
import com.appchina.ios.mgnt.controller.model.SignatureIpasParameter;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.TitleLikeParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreApplicationWrapper;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.utils.ParameterUtils;
import com.appchina.ios.mgnt.utils.PostParameterUtils;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.model.PostParametersHandle;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * @author luofei@refanqie.com (Your Name Here)
 */
@Service("backendApiService")
public class BackendApiServiceImpl implements BackendApiService {
    private static final Logger log = Logger.getLogger(BackendApiServiceImpl.class);
    @Value("${ios.mgnt.daemon.enterpriseipatmpfilepathurl}")
    private String enterpriseIpaTmpFilePathUrl = "http://127.0.0.1:44444/ios-daemon/scheduler/enterprise/ipafileloader/path.json";
    @Value("${ios.mgnt.api.appsimpleurl}")
    private String appSimpleUrl = "http://127.0.0.1:8080/appchina-ios-backend/app/simples.json";
    @Value("${ios.mgnt.api.limitdiscountappsimpleurl}")
    private String limitDiscountAppSimpleUrl = "http://127.0.0.1:8080/appchina-ios-backend/limit/limitappsimples.json";
    @Value("${ios.mgnt.api.listitemidappsimpleurl}")
    private String listItemIdAppSimpleUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/itemidappsimple.json";
    @Value("${ios.mgnt.api.listbundleidappsimpleurl}")
    private String listBundleIdAppSimpleUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/bundleidappsimple.json";
    @Value("${ios.mgnt.api.searchappbytitleurl}")
    private String searchAppByTitleUrl = "http://10.18.0.54:11111/appchina-ios-backend/admin/app/apptitlesearch.json";
    @Value("${ios.mgnt.api.listdowntypeinterventionurl}")
    private String listDownTypeInterventionUrl = "http://10.18.0.54:11111/appchina-ios-backend/admin/app/listdowntypeintervention.json";
    @Value("${ios.mgnt.api.adddowntypeinterventionurl}")
    private String addDownTypeInterventionUrl = "http://10.18.0.54:11111/appchina-ios-backend/admin/app/adddowntypeintervention.json";
    @Value("${ios.mgnt.api.modifydowntypeinterventionurl}")
    private String modifyDownTypeInterventionUrl = "http://10.18.0.54:11111/appchina-ios-backend/admin/app/modifydowntypeintervention.json";

    @Value("${ios.mgnt.api.searchappurl}")
    private String searchAppUrl = "http://10.18.0.54:11111/appchina-ios-backend/search/apps.json";
    @Value("${ios.mgnt.api.listpromoteappsurl}")
    private String listPromoteAppsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/promote/list.json";
    @Value("${ios.mgnt.api.addpromoteappsurl}")
    private String addPromoteAppsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/promote/add.json";
    @Value("${ios.mgnt.api.modifypromoteappsurl}")
    private String modifyPromoteAppsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/promote/modify.json";
    @Value("${ios.mgnt.api.modifypromoterankurl}")
    private String modifyPromoteRankUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/promote/modifyRank.json";
    @Value("${ios.mgnt.api.modifypromotestarttimeurl}")
    private String modifyPromoteStartTimeUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/promote/modifystarttime.json";
    @Value("${ios.mgnt.api.modifypromoteendtimeurl}")
    private String modifyPromoteEndTimeUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/promote/modifyendtime.json";
    @Value("${ios.mgnt.api.offpromoteappsbyrootidurl}")
    private String offPromoteAppsByRootIdUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/promote/off/byRootId.json";
    @Value("${ios.mgnt.api.modifyAllChannelPromoteStartTimeUrl}")
    private String modifyAllChannelPromoteStartTimeUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/promote/modifyallst.json";
    @Value("${ios.mgnt.api.modifyAllChannelPromoteEndTimeUrl}")
    private String modifyAllChannelPromoteEndTimeUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/promote/modifyallet.json";


    // banner
    @Value("${ios.mgnt.api.listbannerappsurl}")
    private String listBannerAppsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/banner/list.json";
    @Value("${ios.mgnt.api.addbannerappsurl}")
    private String addBannerAppsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/banner/add.json";
    @Value("${ios.mgnt.api.modifybannerappsurl}")
    private String modifyBannerAppsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/banner/modify.json";
    @Value("${ios.mgnt.api.modifybannerrankurl}")
    private String modifyBannerRankUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/banner/modifyRank.json";
    @Value("${ios.mgnt.api.modifybannerendtimeurl}")
    private String modifyBannerEndtimeUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/banner/modifyendtime.json";
    @Value("${ios.mgnt.api.offlinerootbannerurl}")
    private String offileRootBannerUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/banner/offlinerootbanner.json";
    @Value("${ios.mgnt.api.bannerdetailurl}")
    private String bannerDetailUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/banner/detail.json";

    // search
    @Value("${ios.mgnt.api.hotwordslisturl}")
    private String hotwordsListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/hotword/list.json";
    @Value("${ios.mgnt.api.hotwordaddurl}")
    private String hotwordAddUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/hotword/add.json";
    @Value("${ios.mgnt.api.hotwordmodifyurl}")
    private String hotwordModifyUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/hotword/modify.json";

    @Value("${ios.mgnt.api.placeholderslisturl}")
    private String placeholdersListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/palceholder/list.json";
    @Value("${ios.mgnt.api.placeholderaddurl}")
    private String placeholderAddUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/palceholder/add.json";
    @Value("${ios.mgnt.api.placeholdermodifyurl}")
    private String placeholderModifyUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/palceholder/modify.json";

    @Value("${ios.mgnt.api.defaultresultslisturl}")
    private String defaultResultsListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/defaultresult/list.json";
    @Value("${ios.mgnt.api.defaultresultaddurl}")
    private String defaultResultAddUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/defaultresult/add.json";
    @Value("${ios.mgnt.api.defaultresultmodifyurl}")
    private String defaultResultModifyUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/defaultresult/modify.json";

    @Value("${ios.mgnt.api.pagedictionarylisturl}")
    private String pageDictionaryListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/pagedictionary/list.json";
    @Value("${ios.mgnt.api.pagedictionarymapurl}")
    private String pageDictionaryMapUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/pagedictionary/map.json";
    @Value("${ios.mgnt.api.pagedictionaryaddurl}")
    private String pageDictionaryAddUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/search/pagedictionary/add.json";

    @Value("${ios.mgnt.api.apprankinterventionaddurl}")
    private String appRankInterventionAddUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/intervention/add.json";
    @Value("${ios.mgnt.api.apprankinterventionlisturl}")
    private String appRankInterventionListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/intervention/list.json";
    @Value("${ios.mgnt.api.appRankInterventionListByRootIdAndCategoryUrl}")
    private String appRankInterventionListByRootIdAndCategoryUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/intervention/listbyrootid.json";
    @Value("${ios.mgnt.api.apprankinterventionmodifyurl}")
    private String appRankInterventionModifyUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/intervention/modify.json";
    @Value("${ios.mgnt.api.appRankInterventionModifyRankUrl}")
    private String appRankInterventionModifyRankUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/intervention/modifyrank.json";
    @Value("${ios.mgnt.api.appranklisturl}")
    private String appRankListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/list.json";

    @Value("${ios.mgnt.api.getRootApplicationCategoryUrl}")
    private String getRootApplicationCategoryUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/category/appcate.json";
    @Value("${ios.mgnt.api.categorymapurl}")
    private String categoryMapUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/category/map.json";
    @Value("${ios.mgnt.api.categoryurl}")
    private String categoryUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/category/detail.json";
    @Value("${ios.mgnt.api.categoryclildrenurl}")
    private String categoryClildrenUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/category/children.json";
    @Value("${ios.mgnt.api.cateitunesnamesurl}")
    private String cateItunesNamesUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/category/itunesnames.json";
    @Value("${ios.mgnt.api.ranktypemapurl}")
    private String rankTypeMapUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/map.json";

    @Value("${ios.mgnt.api.ipaplisturl}")
    private String ipaPlistUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/ipaplist.json";
    @Value("${ios.mgnt.api.ipaplistmapurl}")
    private String ipaPlistMapUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/ipaplistmap.json";
    @Value("${ios.mgnt.api.ipaplistinterveneurl}")
    private String ipaPlistInterveneUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/intervene.json";
    @Value("${ios.mgnt.api.ipaplistableurl}")
    private String ipaPlistAbleUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/able.json";
    @Value("${ios.mgnt.api.uploadenterpriseipasurl}")
    private String uploadEnterpriseIpasUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/uploadenterpriseipas.json";
    @Value("${ios.mgnt.api.parsedenterpriseipasurl}")
    private String parsedEnterpriseIpasUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/parsedenterpriseipas.json";
    @Value("${ios.mgnt.api.plistcertinfourl}")
    private String plistCertInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/plistcertinfo.json";
    @Value("${ios.mgnt.api.listipaloadstaturl}")
    private String listIpaLoadStatUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/listipaloadstat.json";

    @Value("${ios.mgnt.api.applicationurl}")
    private String applicationUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/detail.json";
    @Value("${ios.mgnt.api.detailrootapplicationurl}")
    private String detailRootApplicationUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/rootapplication/detail.json";
    @Value("${ios.mgnt.api.applicationrootdetailurl}")
    private String applicationRootDetailUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/rootDetail.json";
    @Value("${ios.mgnt.api.applicationlisturl}")
    private String applicationListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/list.json";
    @Value("${ios.mgnt.api.applicationlistemphasisurl}")
    private String applicationListEmphasisUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/listemphasis.json";
    @Value("${ios.mgnt.api.applicationaddtoemphasisidsurl}")
    private String applicationAddToEmphasisIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/addtoemphasisids.json";
    @Value("${ios.mgnt.api.applicationremovefromemphasisidsurl}")
    private String applicationRemoveFromEmphasisIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/removefromemphasisids.json";

    @Value("${ios.mgnt.api.applicationlistbannedurl}")
    private String applicationListBannedUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/listbanned.json";
    @Value("${ios.mgnt.api.applicationaddtobannedidsurl}")
    private String applicationAddToBannedIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/addtobannedids.json";
    @Value("${ios.mgnt.api.applicationremovefrombannedidsurl}")
    private String applicationRemoveFromBannedIdsUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/removefrombannedids.json";

    @Value("${ios.mgnt.api.applicationmodifyedtiortitleurl}")
    private String applicationModifyEdtiorTitleUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/modifyeditortitle.json";
    @Value("${ios.mgnt.api.applicationmodifyeditorreviewurl}")
    private String applicationModifyEditorReviewUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/modifyeditorreview.json";
    @Value("${ios.mgnt.api.apprescacheurl}")
    private String appResCacheUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/app/res.json";

    @Value("${ios.mgnt.api.clientconfurl}")
    private String clientConfUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/conf.json";
    @Value("${ios.mgnt.api.clientconfmodifyurl}")
    private String clientConfModifyUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/confmodify.json";
    @Value("${ios.mgnt.api.listplatformurl}")
    private String listPlatformUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/listplatform.json";
    @Value("${ios.mgnt.api.listosversionurl}")
    private String listOsVersionUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/listosversion.json";
    @Value("${ios.mgnt.api.addplatformurl}")
    private String addPlatformUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/addplatform.json";
    @Value("${ios.mgnt.api.addosversionurl}")
    private String addOsVersionUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/addosversion.json";
    @Value("${ios.mgnt.api.modifyplatformosversionurl}")
    private String modifyPlatformOsversionUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/modifyplatformosversion.json";

    @Value("${ios.mgnt.api.enterprisesignatureslisturl}")
    private String enterpriseSignaturesListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/signlist.json";
    @Value("${ios.mgnt.api.enterprisesignaturesurl}")
    private String enterpriseSignaturesUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/sign.json";
    @Value("${ios.mgnt.api.signaturesipalisturl}")
    private String signaturesIpaListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/signcertipalist.json";
    @Value("${ios.mgnt.api.signaturesdisableurl}")
    private String signaturesDisableUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/signdisable.json";
    @Value("${ios.mgnt.api.plisthosturl}")
    private String plisthostUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/plisthosturl.json";

    @Value("${ios.mgnt.api.addmarketinfourl}")
    private String addMarketInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/addmarketinfo.json";
    @Value("${ios.mgnt.api.marketinfosurl}")
    private String marketInfosUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/marketinfos.json";
    @Value("${ios.mgnt.api.marketipalisturl}")
    private String marketIpaListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/plist/marketipas.json";


    @Value("${ios.mgnt.api.listdispatchdescurl}")
    private String listdispatchdescurl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/listdispatchdesc.json";
    @Value("${ios.mgnt.api.modifydispatchdescurl}")
    private String modifydispatchdescurl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/modifydispatchdesc.json";

    @Value("${ios.mgnt.api.listclientchannelurl}")
    private String listClientChannelUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/listclientchannel.json";
    @Value("${ios.mgnt.api.listparentclientchannelurl}")
    private String listParentClientChannelUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/listparentclientchannel.json";
    @Value("${ios.mgnt.api.listleafclientchannelurl}")
    private String listLeafClientChannelUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/listleafclientchannel.json";
    @Value("${ios.mgnt.api.addclientchannelurl}")
    private String addClientChannelUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/addclientchannel.json";

    @Value("${ios.mgnt.api.listshareaccounturl}")
    private String listShareAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/shareaccount/list.json";
    @Value("${ios.mgnt.api.detailshareaccounturl}")
    private String detailShareAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/shareaccount/detail.json";
    @Value("${ios.mgnt.api.modifyshareaccounturl}")
    private String modifyShareAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/shareaccount/modify.json";
    @Value("${ios.mgnt.api.addshareaccounturl}")
    private String addShareAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/shareaccount/add.json";
    @Value("${ios.mgnt.api.listshareaccountbuyappurl}")
    private String listShareAccountBuyAppUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/shareaccount/buyapp/list.json";
    @Value("${ios.mgnt.api.modifyshareaccountbuyappurl}")
    private String modifyShareAccountBuyAppUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/shareaccount/buyapp/modify.json";
    @Value("${ios.mgnt.api.addshareaccountbuyappurl}")
    private String addShareAccountBuyAppUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/shareaccount/buyapp/add.json";
    @Value("${ios.mgnt.api.listappwishinfourl}")
    private String listAppWishInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/appwish/info/list.json";
    @Value("${ios.mgnt.api.listappwishhistoryurl}")
    private String listAppWishHistoryUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/appwish/history/list.json";
    @Value("${ios.mgnt.api.listaccountfeedbackurl}")
    private String listAccountFeedbackUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/feedback/list.json";
    @Value("${ios.mgnt.api.detailaccountfeedbackurl}")
    private String detailAccountFeedbackUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/feedback/detail.json";
    @Value("${ios.mgnt.api.modifyaccountfeedbackurl}")
    private String modifyAccountFeedbackUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/feedback/modify.json";

    @Value("${ios.mgnt.api.listaappleaccounturl}")
    private String listAppleAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/listappleaccount.json";
    @Value("${ios.mgnt.api.listaappleaccountsourceurl}")
    private String listAppleAccountSourceUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/listappleaccountsource.json";
    @Value("${ios.mgnt.api.addappleaccountsourceurl}")
    private String addAppleAccountSourceUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/addappleaccountsource.json";
    @Value("${ios.mgnt.api.listdispatchconfigureurl}")
    private String listDispatchConfigureUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/listdispatchconfigure.json";
    @Value("${ios.mgnt.api.adddispatchconfigureurl}")
    private String addDispatchConfigureUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/adddispatchconfigure.json";
    @Value("${ios.mgnt.api.modifydispatchconfigureurl}")
    private String modifyDispatchConfigureUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/modifydispatchconfigure.json";

    @Value("${ios.mgnt.api.listaccounturl}")
    private String listAccountUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/account/list.json";
    @Value("${ios.mgnt.api.listpcsuitebindappleidinfourl}")
    private String listPcSuiteBindAppleIdInfoUrl = "http://127.0.0.1:11111/ios-backend/admin/account/listbindappleidinfo.json";

    @Value("${ios.mgnt.api.listappleaccountautomatedinfourl}")
    private String listAppleAccountAutomatedInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/appleaccount/automated/info/list.json";
    @Value("${ios.mgnt.api.detailappleaccountautomatedinfourl}")
    private String detailAppleAccountAutomatedInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/appleaccount/automated/info/detail.json";

    @Value("${ios.mgnt.api.listgloballocaleurl}")
    private String listGlobalLocaleUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/listgloballocale.json";
    @Value("${ios.mgnt.api.modifygloballocaleurl}")
    private String modifyGlobalLocaleUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/modifygloballocale.json";
    @Value("${ios.mgnt.api.listglobalrankappurl}")
    private String listGlobalRankAppUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rank/listgloballocalerankapp.json";

    @Value("${ios.mgnt.api.publicclienturl}")
    private String publicClientUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/client/publish.json";
    @Value("${ios.mgnt.api.modifyclientupgradeinfourl}")
    private String modifyClientUpgradeInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/client/modifyclientupgradeinfo.json";
    @Value("${ios.mgnt.api.listclientupgradeinfourl}")
    private String listClientUpgradeInfoUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/system/client/listclientupgradeinfo.json";
    @Value("${ios.mgnt.api.listRootApplicationUrl}")
    private String listRootApplicationUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/rootapplication/list.json";

    // app tag
    @Value("${ios.mgnt.api.mapAppTagRankTypeDictionaryUrl}")
    private String mapAppTagRankTypeDictionaryUrl = "http://127.0.0.1:8080/admin/tagrank/tag/types.json";
    @Value("${ios.mgnt.api.mapAppTagCategoryDictionaryUrl}")
    private String mapAppTagCategoryDictionaryUrl = "http://127.0.0.1:8080/admin/tagrank/tag/catemap.json";
    @Value("${ios.mgnt.api.listChildAppTagsUrl}")
    private String listChildAppTagsUrl = "http://127.0.0.1:8080/admin/tagrank/tag/child.json";
    @Value("${ios.mgnt.api.listAppTagsUrl}")
    private String listAppTagsUrl = "http://127.0.0.1:8080/admin/tagrank/tag/list.json";
    @Value("${ios.mgnt.api.listAppTagRankUrl}")
    private String listAppTagRankUrl = "http://127.0.0.1:8080/admin/tagrank/list.json";
    @Value("${ios.mgnt.api.listAppTagRankInterventionsUrl}")
    private String listAppTagRankInterventionsUrl = "http://127.0.0.1:8080/admin/tagrankinv/list.json";
    @Value("${ios.mgnt.api.modifyAppTagRankInterventionStatusUrl}")
    private String modifyAppTagRankInterventionStatusUrl = "http://127.0.0.1:8080/admin/tagrankinv/modify.json";
    @Value("${ios.mgnt.api.modifyAppTagRankInterventionRankUrl}")
    private String modifyAppTagRankInterventionRankUrl = "http://127.0.0.1:8080/admin/tagrankinv/modify.json";
    @Value("${ios.mgnt.api.addAppTagRankInterventionsUrl}")
    private String addAppTagRankInterventionsUrl = "http://127.0.0.1:8080/admin/tagrankinv/add.json";

    // system
    @Value("${ios.mgnt.api.viewSystemEmergencyUrl}")
    private String viewSystemEmergencyUrl = "http://127.0.0.1:8080/admin/system/emergency/view.json";
    @Value("${ios.mgnt.api.saveSystemEmergencyUrl}")
    private String saveSystemEmergencyUrl = "http://127.0.0.1:8080/admin/system/emergency/save.json";
    @Value("${ios.mgnt.api.updateSystemEmergencyStatusUrl}")
    private String updateSystemEmergencyStatusUrl = "http://127.0.0.1:8080/admin/system/emergency/updatestatus.json";
    @Value("${ios.mgnt.api.getSystemEmergencyByIdUrl}")
    private String getSystemEmergencyByIdUrl = "http://127.0.0.1:8080/admin/system/emergency/detail.json";

    // root application tag
    @Value("${ios.mgnt.api.addRootApplicationtagUrl}")
    private String addRootApplicationtagUrl = "http://127.0.0.1:11111/admin/rootapptag/add.json";
    @Value("${ios.mgnt.api.modifyRootApplicationTagUrl}")
    private String modifyRootApplicationTagUrl = "http://127.0.0.1:11111/admin/rootapptag/modify.json";
    @Value("${ios.mgnt.api.listRootApplicationTagsUrl}")
    private String listRootApplicationTagsUrl = "http://127.0.0.1:11111/admin/rootapptag/list.json";

    // pcsuite dll
    @Value("${ios.mgnt.api.listpcsuiteitunesdriverinstallinfourl}")
    private String listPcSuiteItunesDriverInstallInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/itunesdriver/installinfo/list.json";
    @Value("${ios.mgnt.api.modifypcsuiteitunesdriverinstallinfourl}")
    private String modifyPcSuiteItunesDriverInstallInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/itunesdriver/installinfo/installinfo/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteitunesdriverinstallinfourl}")
    private String addPcSuiteItunesDriverInstallInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/itunesdriver/installinfo/add.json";
    // driver
    @Value("${ios.mgnt.api.detailpcsuiteitunesdriverurl}")
    private String detailPcSuiteItunesDriverUrl = "http://127.0.0.1:11111/admin/pcsuite/itunesdriver/detail.json";
    @Value("${ios.mgnt.api.listpcsuiteitunesdriverurl}")
    private String listPcSuiteItunesDriverUrl = "http://127.0.0.1:11111/admin/pcsuite/itunesdriver/list.json";
    @Value("${ios.mgnt.api.modifypcsuiteitunesdriverurl}")
    private String modifyPcSuiteItunesDriverUrl = "http://127.0.0.1:11111/admin/pcsuite/itunesdriver/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteitunesdriverurl}")
    private String addPcSuiteItunesDriverUrl = "http://127.0.0.1:11111/admin/pcsuite/itunesdriver/add.json";

    // pcsuite channel
    @Value("${ios.mgnt.api.listpcsuiteiphonemodelurl}")
    private String listPcSuiteIphoneModelUrl = "http://127.0.0.1:11111/admin/pcsuite/iphone/model/list.json";
    @Value("${ios.mgnt.api.detailpcsuiteiphonemodelurl}")
    private String detailPcSuiteIphoneModelUrl = "http://127.0.0.1:11111/admin/pcsuite/iphone/model/detail.json";
    @Value("${ios.mgnt.api.modifypcsuiteiphonemodelurl}")
    private String modifyPcSuiteIphoneModelUrl = "http://127.0.0.1:11111/admin/pcsuite/iphone/model/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteiphonemodelurl}")
    private String addPcSuiteIphoneModelUrl = "http://127.0.0.1:11111/admin/pcsuite/iphone/model/add.json";
    // programmer driver
    @Value("${ios.mgnt.api.listpcsuiteprogrammerdriverurl}")
    private String listPcSuiteProgrammerDriverUrl = "http://127.0.0.1:11111/admin/pcsuite/programmer/driver/list.json";
    @Value("${ios.mgnt.api.modifypcsuiteprogrammerdriverurl}")
    private String modifyPcSuiteProgrammerDriverUrl = "http://127.0.0.1:11111/admin/pcsuite/programmer/driver/modify.json";
    @Value("${ios.mgnt.api.addpcsuiteprogrammerdriverurl}")
    private String addPcSuiteProgrammerDriverUrl = "http://127.0.0.1:11111/admin/pcsuite/programmer/driver/add.json";

    // apple device
    @Value("${ios.mgnt.api.listappledeviceurl}")
    private String listAppleDeviceUrl = "http://127.0.0.1:11111/admin/system/apple/device/list.json";
    @Value("${ios.mgnt.api.addappledeviceurl}")
    private String addAppleDeviceUrl = "http://127.0.0.1:11111/admin/system/apple/device/add.json";
    @Value("${ios.mgnt.api.modifyappledeviceurl}")
    private String modifyAppleDeviceUrl = "http://127.0.0.1:11111/admin/system/apple/device/modify.json";

    // root app rank
    @Value("${ios.mgnt.api.fetchRootAppRankMetaTypesUrl}")
    private String fetchRootAppRankMetaTypesUrl = "http://127.0.0.1:11111/admin/rootrank/ranktypes.json";
    @Value("${ios.mgnt.api.listRootAppRanksUrl}")
    private String listRootAppRanksUrl = "http://127.0.0.1:11111/admin/rootrank/applist.json";
    @Value("${ios.mgnt.api.addRootAppRankInterventionsUrl}")
    private String addRootAppRankInterventionsUrl = "http://127.0.0.1:11111/admin/rootrank/intervent.json";
    @Value("${ios.mgnt.api.modifyRootAppRankInterventionUrl}")
    private String modifyRootAppRankInterventionUrl = "http://127.0.0.1:11111/admin/rootrank/updateinter.json";
    @Value("${ios.mgnt.api.listRootAppRankInterventionsUrl}")
    private String listRootAppRankInterventionsUrl = "http://127.0.0.1:11111/admin/rootrank/listinter.json";

    // 横幅广告

    @Value("${ios.mgnt.api.listlistadplaceurl}")
    private String listListAdPlaceUrl = "http://127.0.0.1:11111/admin/listad/place/list.json";
    @Value("${ios.mgnt.api.listlistadrecominfourl}")
    private String listListAdRecomInfoUrl = "http://127.0.0.1:11111/admin/listad/recominfo/list.json";
    @Value("${ios.mgnt.api.addlistadrecominfourl}")
    private String addListAdRecomInfoUrl = "http://127.0.0.1:11111/admin/listad/recominfo/add.json";
    @Value("${ios.mgnt.api.modifylistadrecominfourl}")
    private String modifyListAdRecomInfoUrl = "http://127.0.0.1:11111/admin/listad/recominfo/modfiy.json";
    @Value("${ios.mgnt.api.listlistadinfourl}")
    private String listListAdInfoUrl = "http://127.0.0.1:11111/admin/listad/info/list.json";
    @Value("${ios.mgnt.api.detaillistadinfourl}")
    private String detailListAdInfoUrl = "http://127.0.0.1:11111/admin/listad/info/detail.json";
    @Value("${ios.mgnt.api.addlistadinfourl}")
    private String addListAdInfoUrl = "http://127.0.0.1:11111/admin/listad/info/add.json";
    @Value("${ios.mgnt.api.listapplecolordicturl}")
    private String listAppleColorDict = "http://127.0.0.1:11111/admin/system/apple/color/dict/list.json";
    @Value("${ios.mgnt.api.addapplecolordicturl}")
    private String addAppleColorDict = "http://127.0.0.1:11111/admin/system/apple/color/dict/add.json";
    @Value("${ios.mgnt.api.modifyapplecolordicturl}")
    private String modifyAppleColorDict = "http://127.0.0.1:11111/admin/system/apple/color/dict/modify.json";

    @Value("${ios.mgnt.api.listpcsuitebglookupinfourl}")
    private String listPcSuiteBgLookupInfoUrl = "http://127.0.0.1:11111/admin/system/apple/device/model/lookup/list.json";
    @Value("${ios.mgnt.api.modifypcsuitebglookupinfourl}")
    private String modifyPcSuiteBgLookupInfoUrl = "http://127.0.0.1:11111/admin/system/apple/device/model/lookup/modify.json";
    @Value("${ios.mgnt.api.fixpcsuitebglookupinfourl}")
    private String fixPcSuiteBgLookupInfoUrl = "http://127.0.0.1:11111/admin/system/apple/device/model/lookup/fix.json";

    @Value("${ios.mgnt.api.listAppInstallReportInfoUrl}")
    private String listAppInstallReportInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/install/report/list.json";
    @Value("${ios.mgnt.api.listAppDownloadCodeInfoUrl}")
    private String listAppDownloadCodeInfoUrl = "http://127.0.0.1:11111/admin/pcsuite/download/code/list.json";

    @Value("${ios.mgnt.api.getauthorizerappdownandbuyinfostatuslisturl}")
    private String getAuthorizerAppDownAndBuyInfoStatusListUrl = "http://127.0.0.1:8081/ios-backend/admin/authorizer/app/download/bought/info.json";
    @Value("${ios.mgnt.api.rootapplicationListUrl}")
    private String rootapplicationListUrl = "http://127.0.0.0.1:8080/ios-backend/admin/rootapplication/listbypara.json";
    @Value("${ios.mgnt.api.listappstoreapplicationwrapperurl}")
    private String listAppStoreApplicationWrapperUrl = "http://127.0.0.0.1:8080/ios-backend/app/detail.json";

    @Override
    public ApiRespWrapper<Map<Integer, ApplicationSimple>> getAppSimple(List<Integer> rootIds) throws ServiceException {
        return post(appSimpleUrl, rootIds, PostParameterUtils.ROOT_APPSIMPLE_HANDLE,
                ParameterUtils.APP_SIMPLE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ApplicationSimple> getAppSimple(int rootId) throws ServiceException {
        List<Integer> rootIds = new ArrayList<Integer>();
        rootIds.add(rootId);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = getAppSimple(rootIds);
        if (resp != null && resp.getData() != null) {
            return new ApiRespWrapper<ApplicationSimple>(resp.getData().get(rootId));
        }
        return new ApiRespWrapper<ApplicationSimple>(null);
    }

    @Override
    public ApiRespWrapper<Map<Integer, LimitDiscountApplicationSimple>> getLimitDiscountApplicationSimple(
            List<Integer> rootIds) {
        return get(limitDiscountAppSimpleUrl, rootIds, ParameterUtils.ROOT_LIMIT_DISCOUNT_APPSIMPLE_HANDLE,
                ParameterUtils.LIMIT_DISCOUNT_APP_SIMPLE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<IpaPlist>> getIpaPlists(IpaPlistParameter para) throws ServiceException {
        return get(ipaPlistUrl, para, ParameterUtils.IPAPLIST_PD_HANDLE, ParameterUtils.IPAPLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<IpaLoadStat>> listIpaLoadStat(IpaLoadStatParameter para) throws ServiceException {
        return get(listIpaLoadStatUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.IPALOADSTATLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<Integer, ApplicationSimple>> getRootSimpleByItemIds(List<Integer> itemIds)
            throws ServiceException {
        try {
            return RemoteDataUtil.get(listItemIdAppSimpleUrl, itemIds, ParameterUtils.ITEMID_APPSIMPLE_HANDLE,
                    ParameterUtils.APP_SIMPLE_RD_HANDLE, false);
        } catch (Exception e) {
            String errmsg = "Get item app simple failed. Url:" + listItemIdAppSimpleUrl + ", errmsg:" + e.getMessage();
            log.error(errmsg, e);
            throw ServiceException.getInternalException(e.getMessage());
        }
    }

    @Override
    public ApiRespWrapper<Map<String, ApplicationSimple>> getRootIdByBundleIds(List<String> bundleIds)
            throws ServiceException {
        try {
            return RemoteDataUtil.get(listBundleIdAppSimpleUrl, bundleIds, ParameterUtils.BUNDLEID_APPSIMPLE_HANDLE,
                    ParameterUtils.BUNDLE_SIMPLE_RD_HANDLE, false);
        } catch (Exception e) {
            String errmsg = "Get buldie app simple failed. Url:" + listBundleIdAppSimpleUrl + ", errmsg:"
                    + e.getMessage();
            log.error(errmsg, e);
            throw ServiceException.getInternalException(e.getMessage());
        }
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

    private static <K, V> V post(String url, K para, PostParametersHandle<K> parametersHandle,
            ReturnDataHandle<V> returnDataHandle) {
        try {
            return RemoteDataUtil.post(url, para, parametersHandle, returnDataHandle, false);
        } catch (Exception e) {
            String errmsg = "Post data failed.. Url:" + url + ", errmsg:" + e.getMessage();
            log.error(errmsg, e);
            throw ServiceException.getInternalException(e.getMessage());
        }
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ApplicationSimple>> searchAppByTitle(String title, int start, int size)
            throws ServiceException {
        TitleLikeParameter para = new TitleLikeParameter(title, start, size);
        return get(searchAppByTitleUrl, para, ParameterUtils.TITLE_LIKE_PD_HANDLE, ParameterUtils.APP_SIMPLES_HANDLE);
    }

    @Override
    public ApiRespWrapper<SearchResp> searchApp(String query, int start, int size) throws ServiceException {
        TitleLikeParameter para = new TitleLikeParameter(start, size, query, null);
        return get(searchAppUrl, para, ParameterUtils.TITLE_LIKE_PD_HANDLE, ParameterUtils.SEARCH_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PromoteApplication>> listPromoteApps(PromoteAppsParameter para)
            throws ServiceException {
        return get(listPromoteAppsUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.PROMTOE_APPS_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPromote(PromoteAppsParameter para) throws ServiceException {
        return get(addPromoteAppsUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPromote(PromoteAppsParameter para) throws ServiceException {
        return get(modifyPromoteAppsUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> offPromoteByRootId(int rootId) throws ServiceException {
        return get(offPromoteAppsByRootIdUrl, rootId, ParameterUtils.ROOTID_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<Banner>> listBannerApps(PromoteAppsParameter para) throws ServiceException {
        return get(listBannerAppsUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.BANNER_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<Banner>> addBanner(BannerAddParameter parameter) throws ServiceException {
        return get(addBannerAppsUrl, parameter, ParametersHandle.PS_HANDLE, ParameterUtils.BANNERS_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyBannerStatus(PromoteAppsParameter para) throws ServiceException {
        return get(modifyBannerAppsUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyBannerRank(PromoteAppsParameter para) throws ServiceException {
        return get(modifyBannerRankUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyBannerEndTime(PromoteAppsParameter para) throws ServiceException {
        return get(modifyBannerEndtimeUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> offlineFromBanner(int rootId) throws ServiceException {
        return get(offileRootBannerUrl, rootId, ParameterUtils.ROOTID_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPromoteRank(PromoteAppsParameter para) throws ServiceException {
        return get(modifyPromoteRankUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPromoteStartTime(PromoteAppsParameter para) throws ServiceException {
        return get(modifyPromoteStartTimeUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPromoteEndTime(PromoteAppsParameter para) throws ServiceException {
        return get(modifyPromoteEndTimeUrl, para, ParameterUtils.PROMOTE_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPromoteStartTimeOnAllChannel(PromoteAppsParameter para)
            throws ServiceException {
        return get(modifyAllChannelPromoteStartTimeUrl, para, ParameterUtils.PROMOTE_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPromoteEndTimeOnAllChannel(PromoteAppsParameter para) throws ServiceException {
        return get(modifyAllChannelPromoteEndTimeUrl, para, ParameterUtils.PROMOTE_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Banner> getBanner(int id) {
        return get(bannerDetailUrl, id, ParameterUtils.ID_PD_HANDLE, ParameterUtils.BANNER_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<SearchHotword>> listHotwords(String name, String channel, int status, int start,
            int size) throws ServiceException {
        SearchHotwordParameter para = new SearchHotwordParameter(name, channel, status, start, size);
        return get(hotwordsListUrl, para, ParameterUtils.HOTWORDS_PD_HANDLE, ParameterUtils.SEARCHHOTWORDS_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifySearchHotword(int id, int status) throws ServiceException {
        SearchHotwordParameter para = new SearchHotwordParameter(id, status);
        return get(hotwordModifyUrl, para, ParameterUtils.HOTWORDS_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addSearchHotword(String name, int rank, String channel) throws ServiceException {
        SearchHotwordParameter para = new SearchHotwordParameter(name, rank, channel);
        return get(hotwordAddUrl, para, ParameterUtils.HOTWORDS_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<SearchPlaceholder>> listPlaceholders(String channel, int status, int start,
            int size) throws ServiceException {
        SearchPlaceholderParameter para = new SearchPlaceholderParameter(channel, status, start, size);
        return get(placeholdersListUrl, para, ParameterUtils.PLACEHOLDER_PD_HANDLE,
                ParameterUtils.SEARCHPLACEHOLDER_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifySearchPlaceholder(int id, int status) throws ServiceException {
        SearchPlaceholderParameter para = new SearchPlaceholderParameter(id, status);
        return get(placeholderModifyUrl, para, ParameterUtils.PLACEHOLDER_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addSearchPlaceholder(String name, String channel, int page) throws ServiceException {
        SearchPlaceholderParameter para = new SearchPlaceholderParameter(name, channel, page);
        return get(placeholderAddUrl, para, ParameterUtils.PLACEHOLDER_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<SearchDefaultResult>> listSearchDefaultResult(String channel, int status,
            int start, int size) throws ServiceException {
        SearchDefaultResultParameter para = new SearchDefaultResultParameter(channel, status, start, size);
        return get(defaultResultsListUrl, para, ParameterUtils.DEFAULT_RESULT_PD_HANDLE,
                ParameterUtils.SEARCHDEFAULTRESULT_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addSearchDefaultResult(int rootId, int rank, String channel) throws ServiceException {
        SearchDefaultResultParameter para = new SearchDefaultResultParameter(rootId, rank, channel);
        return get(defaultResultAddUrl, para, ParameterUtils.DEFAULT_RESULT_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifySearchDefaultResult(int id, int status) throws ServiceException {
        SearchDefaultResultParameter para = new SearchDefaultResultParameter(id, status);
        return get(defaultResultModifyUrl, para, ParameterUtils.DEFAULT_RESULT_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addProductPageDictionary(String channel, int page, String desc)
            throws ServiceException {
        ProductPageDictionaryParameter para = new ProductPageDictionaryParameter(channel, page, desc);
        return get(pageDictionaryAddUrl, para, ParameterUtils.PAGEDICTIONARY_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ProductPageDictionary>> listProductPageDictionary(String channel)
            throws ServiceException {
        ProductPageDictionaryParameter para = new ProductPageDictionaryParameter(channel);
        return get(pageDictionaryListUrl, para, ParameterUtils.PAGEDICTIONARY_PD_HANDLE,
                ParameterUtils.PRODUCTPAGEDICTIONARY_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<String, String>> mapProductChannelPageDictionary(String channel) throws ServiceException {
        ProductPageDictionaryParameter para = new ProductPageDictionaryParameter(channel);
        return get(pageDictionaryMapUrl, para, ParameterUtils.PAGEDICTIONARY_PD_HANDLE, ParameterUtils.MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<String, String>> mapRankTypeDictionary() throws ServiceException {
        return get(this.rankTypeMapUrl, null, null, ParameterUtils.MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<String, String>> mapCategoryDictionary() throws ServiceException {
        return get(this.categoryMapUrl, null, null, ParameterUtils.MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppRankIntervention>> listAppRankInterventions(AppRankListParameter para)
            throws ServiceException {
        return get(this.appRankInterventionListUrl, para, ParameterUtils.APPRANKLIST_PD_HANDLE,
                ParameterUtils.APPRANKINTERVENTION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppRankIntervention>> listAppRankInterventionByRootId(AppRankListParameter para)
            throws ServiceException {
        return get(this.appRankInterventionListByRootIdAndCategoryUrl, para, ParameterUtils.APPRANKLIST_PD_HANDLE,
                ParameterUtils.APPRANKINTERVENTION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppRankInterventions(AppRankListParameter para) throws ServiceException {
        return get(this.appRankInterventionAddUrl, para, ParameterUtils.APPRANKLIST_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppRankInterventions(AppRankListParameter para) throws ServiceException {
        return get(this.appRankInterventionModifyUrl, para, ParameterUtils.APPRANKLIST_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppRankInterventionRank(AppRankListParameter para) throws ServiceException {
        return get(this.appRankInterventionModifyRankUrl, para, ParameterUtils.APPRANKLIST_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppRank>> listAppRank(AppRankListParameter para) throws ServiceException {
        return get(this.appRankListUrl, para, ParameterUtils.APPRANKLIST_PD_HANDLE, ParameterUtils.APPRANK_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<String, IpaPlist>> getIpaPlists(List<Integer> plistIds) throws ServiceException {
        return get(this.ipaPlistMapUrl, plistIds, ParameterUtils.IDS_PD_HANDLE, ParameterUtils.MAPIPAPLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPlsit(IpaPlistModifyParameter param) throws ServiceException {
        String url = param.getAble() == null ? ipaPlistInterveneUrl : ipaPlistAbleUrl;
        return get(url, param, ParameterUtils.PLISTMODIFY_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Application> getApplication(int id) throws ServiceException {
        return get(this.applicationUrl, id, ParameterUtils.ID_PD_HANDLE, ParameterUtils.APPLICATION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Application> getApplicationByRootId(int id) throws ServiceException {
        return get(this.applicationRootDetailUrl, id, ParameterUtils.ID_PD_HANDLE, ParameterUtils.APPLICATION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ApplicationItunesImgRes>> getApplicationResource(int id) throws ServiceException {
        return get(this.appResCacheUrl, id, ParameterUtils.ID_PD_HANDLE, ParameterUtils.RESES_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Category> getCategory(int categoryId) throws ServiceException {
        return get(this.categoryUrl, categoryId, ParameterUtils.ID_PD_HANDLE, ParameterUtils.CATEGORY_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<Category>> listChildCategory(int categoryId) throws ServiceException {
        return get(this.categoryClildrenUrl, categoryId, ParameterUtils.ID_PD_HANDLE,
                ParameterUtils.CATEGORYS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<String>> listItunesCategoryName(int categoryId) throws ServiceException {
        return get(this.cateItunesNamesUrl, categoryId, ParameterUtils.ID_PD_HANDLE, ParameterUtils.STRINGS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<Application>> listApplication(ApplicationParameter para) throws ServiceException {
        return get(this.applicationListUrl, para, ParameterUtils.APPLICATION_PD_HANDLE,
                ParameterUtils.APPLICATIONS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ApplicationSimple>> listEmphasisApplication(AppEmphasisParameter para)
            throws ServiceException {
        return get(this.applicationListEmphasisUrl, para, ParameterUtils.APPEMPHASIS_PD_HANDLE,
                ParameterUtils.APP_SIMPLES_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyEmphasisStatus(AppEmphasisParameter para) throws ServiceException {
        return get(this.applicationRemoveFromEmphasisIdsUrl, para, ParameterUtils.APPEMPHASIS_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addEmphasis(AppEmphasisParameter para) throws ServiceException {
        return get(this.applicationAddToEmphasisIdsUrl, para, ParameterUtils.APPEMPHASIS_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppBanned>> listBannedApplication(AppBannedParameter para)
            throws ServiceException {
        return get(this.applicationListBannedUrl, para, ParameterUtils.APPBANNED_PD_HANDLE,
                ParameterUtils.APPBANNEDS_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppBanned(AppBannedParameter para) throws ServiceException {
        return get(this.applicationAddToBannedIdsUrl, para, ParameterUtils.APPBANNED_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppBannedStatus(AppBannedParameter para) throws ServiceException {
        return get(this.applicationRemoveFromBannedIdsUrl, para, ParameterUtils.APPBANNED_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyApplicationEditorTitle(ApplicationModifyParameter applicationModifyParameter)
            throws ServiceException {
        return get(this.applicationModifyEdtiorTitleUrl, applicationModifyParameter,
                ParameterUtils.APPLICATIONMODIFY_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyApplicationEditorReview(ApplicationModifyParameter applicationModifyParameter)
            throws ServiceException {
        return get(this.applicationModifyEditorReviewUrl, applicationModifyParameter,
                ParameterUtils.APPLICATIONMODIFY_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ClientConf> getClientConf() throws ServiceException {
        return get(clientConfUrl, null, ParameterUtils.DEFAULT_PD_HANDLE, ParameterUtils.CLIENTCONF_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyClientConf(ClientConfModifyParameter parameter) throws ServiceException {
        return get(this.clientConfModifyUrl, parameter, ParameterUtils.CLIENTCONFMODIFY_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ApplePlatform>> listPlatform() throws ServiceException {
        return get(listPlatformUrl, null, ParameterUtils.DEFAULT_PD_HANDLE, ParameterUtils.APPLEPLATFORM_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPlatform(PlatformParameter para) throws ServiceException {
        return get(addPlatformUrl, para, ParameterUtils.PLATFORMPARAMETER_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPlatformOsVersion(PlatformOsVersionParameter para) throws ServiceException {
        return get(modifyPlatformOsversionUrl, para, ParameterUtils.PLATFORMOSVERSIONPARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleOsVersion>> listOsVersion() throws ServiceException {
        return get(listOsVersionUrl, null, ParameterUtils.DEFAULT_PD_HANDLE, ParameterUtils.APPLEOSVERSION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addOsVersion(OsVersionParameter para) throws ServiceException {
        return get(addOsVersionUrl, para, ParameterUtils.OSVERSIONPARAMETER_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<IpaCertSignature>> getEnterpriseSignatures(StatusStartSizeParameter parameter)
            throws ServiceException {
        return get(this.enterpriseSignaturesListUrl, parameter, ParameterUtils.STATUSSTARTSIZE_PD_HANDLE,
                ParameterUtils.IPACERTSIGNATURELIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<IpaCertSignature> getEnterpriseSignature(SignatureIpasParameter parameter)
            throws ServiceException {
        return get(this.enterpriseSignaturesUrl, parameter, ParameterUtils.SIGNATUREIPASPARAMETER_PD_HANDLE,
                ParameterUtils.IPACERTSIGNATURE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<IpaPlist>> getEnterpriseSignatureIpas(SignatureIpasParameter parameter)
            throws ServiceException {
        return get(this.signaturesIpaListUrl, parameter, ParameterUtils.SIGNATUREIPASPARAMETER_PD_HANDLE,
                ParameterUtils.IPAPLIST_RD_HANDLE);

    }

    @Override
    public ApiRespWrapper<Boolean> disableEnterpriseSignatures(SignatureIpasParameter parameter)
            throws ServiceException {
        return get(this.signaturesDisableUrl, parameter, ParameterUtils.SIGNATUREIPASPARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<String> getPlistHostUrl() throws ServiceException {
        return get(this.plisthostUrl, null, ParameterUtils.DEFAULT_PD_HANDLE, ParameterUtils.STRING_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<MarketInfo>> getMarketInfos() throws ServiceException {
        return get(this.marketInfosUrl, null, ParameterUtils.DEFAULT_PD_HANDLE, ParameterUtils.MARKETINFOS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addMarketInfo(MarketInfoParameter parameter) throws ServiceException {
        return get(this.addMarketInfoUrl, parameter, ParameterUtils.MARKETINFPARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<IpaPlist>> listMarketIpas(Integer id) throws ServiceException {
        return get(this.marketIpaListUrl, id, ParameterUtils.ID_PD_HANDLE, ParameterUtils.IPAPLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<IpaItunesMetaData>> listIpaItunesMetaDatas(IpaPlistParameter para)
            throws ServiceException {
        return get(parsedEnterpriseIpasUrl, para, ParameterUtils.IPAPLIST_PD_HANDLE,
                ParameterUtils.IPAITUNESMETADATALIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<EnterpriseIpaFile>> listUploadIpaDatas(IpaUploadListParameter para)
            throws ServiceException {
        return get(uploadEnterpriseIpasUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.ENTERPRISEIPAFILELIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<DownTypeIntervention>> listDownTypeIntervention(
            DownTypeInterventionParameter para) throws ServiceException {
        return get(listDownTypeInterventionUrl, para, ParameterUtils.DOWNTYPEINTERVENTION_PD_HANDLE,
                ParameterUtils.DOWNTYPEINTERVENTION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addDownTypeIntervention(DownTypeInterventionParameter para) throws ServiceException {
        return get(addDownTypeInterventionUrl, para, ParameterUtils.DOWNTYPEINTERVENTION_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyDownTypeIntervention(DownTypeInterventionParameter para)
            throws ServiceException {
        return get(modifyDownTypeInterventionUrl, para, ParameterUtils.DOWNTYPEINTERVENTION_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<DispatchDesc>> listDispatchDesc() throws ServiceException {
        return get(listdispatchdescurl, null, ParameterUtils.DEFAULT_PD_HANDLE, ParameterUtils.DISPATCHDESC_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyDispatchDesc(DispatchDescParameter para) throws ServiceException {
        return get(modifydispatchdescurl, para, ParameterUtils.DISPATCHDESCPARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);

    }

    @Override
    public ApiRespWrapper<ListWrapResp<ClientChannel>> listClientChannel(ClientChannelParameter para)
            throws ServiceException {
        return get(listClientChannelUrl, para, ParameterUtils.CLIENTCHANNELPARAMETER_PD_HANDLE,
                ParameterUtils.CLIENTCHANNEL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ClientChannel>> listParentClientChannel() throws ServiceException {
        return get(listParentClientChannelUrl, null, ParameterUtils.DEFAULT_PD_HANDLE,
                ParameterUtils.CLIENTCHANNEL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ClientChannel>> listLeafClientChannel() throws ServiceException {
        return get(listLeafClientChannelUrl, null, ParameterUtils.DEFAULT_PD_HANDLE,
                ParameterUtils.CLIENTCHANNEL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addClientChannel(ClientChannelParameter para) throws ServiceException {
        return get(addClientChannelUrl, para, ParameterUtils.CLIENTCHANNELPARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleAccount>> listAppleAccount(AppleAccountParamter para)
            throws ServiceException {
        return get(listAppleAccountUrl, para, ParameterUtils.APPLEACCOUNTPARAMETER_PD_HANDLE,
                ParameterUtils.APPLEACCOUNT_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteBindAppleIdInfo>> listPcSuiteBindAppleIdInfo(
            PcSuiteBindAppleIdInfoParameter para) throws ServiceException {
        return get(listPcSuiteBindAppleIdInfoUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEBINDAPPLEIDINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleAccountSource>> listAppleAccountSource() throws ServiceException {
        return get(listAppleAccountSourceUrl, null, ParameterUtils.DEFAULT_PD_HANDLE,
                ParameterUtils.APPLEACCOUNTSOURCE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppleAccountSource(AppleAccountSourceParamter para) throws ServiceException {
        return get(addAppleAccountSourceUrl, para, ParameterUtils.APPLEACCOUNTSOURCEPARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addDispatchConfigure(DispatchConfiigureAddParameter para) throws ServiceException {
        return get(addDispatchConfigureUrl, para, ParameterUtils.DISPATCHCONFIIGUREADDPARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<DispatchConfigure>> listDispatchConfigure(DispatchConfigureParameter para)
            throws ServiceException {
        return get(listDispatchConfigureUrl, para, ParameterUtils.DISPATCHCONFIGUREPARAMETER_PD_HANDLE,
                ParameterUtils.DISPATCHCONFIGURE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppAppleRank>> listGlobalLocaleRankApps(AppAppleRankListParameter para)
            throws ServiceException {
        return get(listGlobalRankAppUrl, para, ParameterUtils.APPAPPLERANKLISTPARAMETER_PD_HANDLE,
                ParameterUtils.APPAPPLERANK_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppLocale>> listGlobalLocales() throws ServiceException {
        return get(listGlobalLocaleUrl, null, null, ParameterUtils.APPLOCALE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyGlobalLocale(AppLocaleParameter para) throws ServiceException {
        return get(modifyGlobalLocaleUrl, para, ParameterUtils.APPLOCALEPARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> publishClient(ClientUpgradeInfo para) throws ServiceException {
        return get(publicClientUrl, para, ParametersHandle.PS_DATA_JSONMAP_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ClientUpgradeInfo>> listClientUpgradeInfos(ClientUpgradeInfoParameter para)
            throws ServiceException {
        return get(listClientUpgradeInfoUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.CLIENTUPGRADEINFOS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyClientUpgradeInfo(ClientUpgradeInfoParameter para) throws ServiceException {
        return get(modifyClientUpgradeInfoUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<RootApplication>> listRootApplication(List<Integer> rootIds)
            throws ServiceException {
        return get(listRootApplicationUrl, rootIds, ParameterUtils.INTEGER_ROOTIDS_HANDLE,
                ParameterUtils.ROOTAPPLICATION_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<RootApplication> getRootApplication(int rootId) throws ServiceException {
        return get(detailRootApplicationUrl, rootId, ParametersHandle.PS_ROOTID_HANDLE,
                ParameterUtils.ROOTAPPLICATION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<String, String>> mapAppTagRankTypeDictionary() throws ServiceException {
        return get(mapAppTagRankTypeDictionaryUrl, null, null, ParameterUtils.MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<String, String>> mapAppTagCategoryDictionary() throws ServiceException {
        return get(mapAppTagCategoryDictionaryUrl, null, null, ParameterUtils.MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppTagRank>> listAppTagRank(AppTagRankListParameter para)
            throws ServiceException {
        return get(listAppTagRankUrl, para, ParameterUtils.APPTAGRANKLIST_PD_HANDLE,
                ParameterUtils.APPTAGRANK_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppTagRankIntervention>> listAppTagRankInterventions(AppTagRankListParameter para)
            throws ServiceException {
        return get(listAppTagRankInterventionsUrl, para, ParameterUtils.APPTAGRANKLIST_PD_HANDLE,
                ParameterUtils.APPTAGRANKINTERVENTION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppTagRankInterventionStatus(AppTagRankListParameter para)
            throws ServiceException {
        return get(modifyAppTagRankInterventionStatusUrl, para, ParameterUtils.APPTAGRANKLIST_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppTagRankInterventionRank(AppTagRankListParameter para)
            throws ServiceException {
        return get(modifyAppTagRankInterventionRankUrl, para, ParameterUtils.APPTAGRANKLIST_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppTagRankInterventions(AppTagRankListParameter para) throws ServiceException {
        return get(addAppTagRankInterventionsUrl, para, ParameterUtils.APPTAGRANKLIST_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<SystemEmergency>> viewSystemEmergency() throws ServiceException {
        return get(viewSystemEmergencyUrl, null, null, ParameterUtils.SYSTEM_EMERGENCY_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<SystemEmergency> getSystemEmergencyById(Integer id) throws ServiceException {
        return get(getSystemEmergencyByIdUrl, id, ParameterUtils.ID_PD_HANDLE,
                ParameterUtils.SYSTEM_EMERGENCY_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> saveSystemEmergency(SystemEmergency systemEmergency) throws ServiceException {
        return get(saveSystemEmergencyUrl, systemEmergency, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> updateSystemEmergencyStatus(SystemEmergency systemEmergency) throws ServiceException {
        return get(updateSystemEmergencyStatusUrl, systemEmergency, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppTag>> listChildTags(AppTagListParameter para) throws ServiceException {
        return get(listChildAppTagsUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.APPTAGLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppTag>> listAppTags() throws ServiceException {
        return get(listAppTagsUrl, null, null, ParameterUtils.APPTAGLIST_RD_HANDLE);
    }

    // pcsuite
    @Override
    public ApiRespWrapper<Boolean> addPcSuiteItunesInstallInfo(PcSuiteItunesDriverInstallInfoParameter para)
            throws ServiceException {
        return get(addPcSuiteItunesDriverInstallInfoUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteItunesDriverInstallInfo>> listPcSuiteItunesDriverInstallInfo(
            PcSuiteItunesDriverInstallInfoParameter para) {
        return get(listPcSuiteItunesDriverInstallInfoUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEITUNEWDLLLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteItunesDriverInstallInfoStatus(
            PcSuiteItunesDriverInstallInfoParameter para) {
        return get(modifyPcSuiteItunesDriverInstallInfoUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteItunesDriver(PcSuiteItunesDriverParameter para) {
        return get(addPcSuiteItunesDriverUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteItunesDriver>> listPcSuiteItunesDriver(PcSuiteItunesDriverParameter para) {
        return get(listPcSuiteItunesDriverUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEITUNESDRIVERLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<PcSuiteItunesDriver> detailPcSuiteItunesDriver(int id) {
        return get(detailPcSuiteItunesDriverUrl, id, ParametersHandle.PS_ID_HANDLE,
                ParameterUtils.PCSUITEITUNESDRIVER_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteItunesDriverStatus(PcSuiteItunesDriverParameter para) {
        return get(modifyPcSuiteItunesDriverUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<IpaCertSignature> getIpapListCertInfo(ApplicationSimple ret) throws ServiceException {
        return get(plistCertInfoUrl, ret, ParametersHandle.PS_HANDLE, ParameterUtils.IPACERTSIGNATURE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addRootApplicationtag(RootApplicationTag rootApplicationTag) throws ServiceException {
        return get(addRootApplicationtagUrl, rootApplicationTag, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyRootApplicationTag(RootApplicationTag rootApplicationTag)
            throws ServiceException {
        return get(modifyRootApplicationTagUrl, rootApplicationTag, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<RootApplicationTag>> listRootApplicationTags(StatusStartSizeParameter para)
            throws ServiceException {
        return get(listRootApplicationTagsUrl, para, ParameterUtils.STATUSSTARTSIZE_PD_HANDLE,
                ReturnDataHandleUtils.ROOTAPPLICATIONTAG_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteIphoneModel>> listPcSuiteIPhoneModel(PcSuiteIphoneModelParameter para)
            throws ServiceException {
        return get(listPcSuiteIphoneModelUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEIPHONEMODELLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<PcSuiteIphoneModel> detailPcSuiteIPhoneModel(PcSuiteIphoneModelParameter para)
            throws ServiceException {
        return get(detailPcSuiteIphoneModelUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEIPHONEMODELL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteIPhoneModel(PcSuiteIphoneModelParameter para) throws ServiceException {
        return get(modifyPcSuiteIphoneModelUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteIPhoneModel(PcSuiteIphoneModelParameter para) throws ServiceException {
        return get(addPcSuiteIphoneModelUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteIosProgrammerDriver>> listPcSuiteProgrammerDriver(
            PcSuiteIosProgrammerDriverParameter para) throws ServiceException {
        return get(listPcSuiteProgrammerDriverUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.PCSUITEIOSPROGRAMMERDRIVERLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteProgrammerDriver(PcSuiteIosProgrammerDriverParameter para)
            throws ServiceException {
        return get(modifyPcSuiteProgrammerDriverUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addPcSuiteProgrammerDriver(PcSuiteIosProgrammerDriverParameter para)
            throws ServiceException {
        return get(addPcSuiteProgrammerDriverUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleDevice>> listAppleDevice(AppleDeviceParameter para) throws ServiceException {
        return get(listAppleDeviceUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.APPLEDEVICELIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppleDevice(AppleDeviceParameter para) throws ServiceException {
        return get(addAppleDeviceUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppleDevice(AppleDeviceParameter para) throws ServiceException {
        return get(modifyAppleDeviceUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<String, String>> fetchRootAppRankMetaTypes() throws ServiceException {
        return get(fetchRootAppRankMetaTypesUrl, null, null, ParameterUtils.ROOTAPPRANKMETATYPE_MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<RootAppRankSimple>> listRootAppRanks(RootAppRankParameter para)
            throws ServiceException {
        return get(listRootAppRanksUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.ROOTAPPRANKSIMPLE_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addRootAppRankInterventions(RootAppRankAddParameter para) throws ServiceException {
        return get(addRootAppRankInterventionsUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyRootAppRankIntervention(RootAppRankInterventionUpdateParameter para)
            throws ServiceException {
        return get(modifyRootAppRankInterventionUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<RootAppRankSimple>> listRootAppRankInterventions(
            RootAppRankInterventionParameter para) throws ServiceException {
        return get(listRootAppRankInterventionsUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.ROOTAPPRANKSIMPLE_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleShareAccount>> listShareAccount(AppleShareAccountParamter para)
            throws ServiceException {
        return get(listShareAccountUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.APPLESHAREACCOUNTLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addShareAccount(AppleShareAccountParamter para) throws ServiceException {
        return get(addShareAccountUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyShareAccountSource(AppleShareAccountParamter para) throws ServiceException {
        return get(modifyShareAccountUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppleShareAccount> detailShareAccount(AppleShareAccountParamter para) throws ServiceException {
        return get(detailShareAccountUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.APPLESHAREACCOUNT_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleIdBuyHistory>> listShareAccountBuyHistory(AppleIdBuyHistoryParamter para)
            throws ServiceException {
        return get(listShareAccountBuyAppUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.APPLEIDBUYHISTORYLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addShareAccountBuyHistory(AppleIdBuyHistoryParamter para) throws ServiceException {
        return get(addShareAccountBuyAppUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyShareAccountBuyHistory(AppleShareAccountParamter para) throws ServiceException {
        return get(modifyShareAccountBuyAppUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppWishInfo>> listAppWishInfo(AppWishParameter para) {
        return get(listAppWishInfoUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.APPWISHINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppWishHistory>> listAppWishHistory(AppWishParameter para) {
        return get(listAppWishHistoryUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.APPWISHHISTORY_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<Category>> getRootApplicationCategory(Integer rootId) {
        return get(getRootApplicationCategoryUrl, rootId, ParametersHandle.PS_ROOTID_HANDLE,
                ParameterUtils.CATEGORYS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AccountFeedback>> listAccountFeedback(AccountFeedbackParameter para)
            throws ServiceException {
        return get(listAccountFeedbackUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.ACCOUNTFEEDBACK_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AccountFeedback> detailAccountFeedback(int id) throws ServiceException {
        return get(detailAccountFeedbackUrl, id, ParametersHandle.PS_ID_HANDLE,
                ParameterUtils.ACCOUNTFEEDBACK_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAccountFeedbback(AccountFeedbackParameter para) throws ServiceException {
        return get(modifyAccountFeedbackUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ListAdPlace>> listListAdPlace() throws ServiceException {
        return get(listListAdPlaceUrl, null, null, ParameterUtils.LISTADPLACE_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addListAdInfo(ListAdInfoParameter para) throws ServiceException {
        return get(addListAdInfoUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ListAdInfo>> listListAdInfo(ListAdInfoParameter para) throws ServiceException {
        return get(listListAdInfoUrl, null, null, ParameterUtils.LISTADINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ListAdInfo>> detailListAdInfo(List<Integer> ids) throws ServiceException {
        return get(detailListAdInfoUrl, ids, ParameterUtils.IDS_PD_HANDLE, ParameterUtils.LISTADINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ListAdRecomInfo>> listListAdRecomInfo(ListAdRecomInfoParameter para)
            throws ServiceException {
        return get(listListAdRecomInfoUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.LISTADRECOMINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addListAdRecomInfo(ListAdRecomInfoParameter para) throws ServiceException {
        return get(addListAdRecomInfoUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyListAdRecomInfo(ListAdRecomInfoParameter para) throws ServiceException {
        return get(modifyListAdRecomInfoUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<ClientApnCertInfo>> listAppStoreClientCertInfo(
            ClientApnCertInfoParameter parameter) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreClientCertInfo(ClientApnCertInfoParameter parameter)
            throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreClientCertInfo(ClientApnCertInfoParameter parameter)
            throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public ApiRespWrapper<ListWrapResp<AppleAccountAutomatedInfo>> listAppleAccountAutomatedInfo(
            AppleAccountAutomatedInfoParameter para) throws ServiceException {
        return get(listAppleAccountAutomatedInfoUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.APPLEACCOUNTAUTOMATEDINFO_LIST_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<AppleAccountAutomatedInfo> detailAppleAccountAutomatedInfo(int id) throws ServiceException {
        return get(detailAppleAccountAutomatedInfoUrl, id, ParametersHandle.PS_ID_HANDLE,
                ParameterUtils.APPLEACCOUNTAUTOMATEDINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<Account>> listAccount(AccountParamter para) throws ServiceException {
        return get(listAccountUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.ACCOUNT_LIST_RD_HANDLE);
    }

    @Override
    public List<Account> listAccount(List<Integer> ids) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AccountWishInfo>> listAccountWishInfo(AccountWishInfoParameter para)
            throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AccountFavorite>> listAccountFavoriteInfo(AccountWishInfoParameter para)
            throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AccountWishProgressInfo>> listAccountWishProgressInfo(
            AccountWishProgressInfoParameter para) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AppleAccount> listAppleAccountByBindAccountId(List<Integer> accountIds) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<PcSuiteBgLookupInfo>> listPcSuiteBgLookupInfo() throws ServiceException {
        return get(listPcSuiteBgLookupInfoUrl, null, null, ParameterUtils.PCSUITEBGLOOKUPINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyPcSuiteBgLookupInfo(IdStatusParameter g) throws ServiceException {
        return get(modifyPcSuiteBgLookupInfoUrl, g, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Integer> fixPcSuiteBgLookupInfo(int id) throws ServiceException {
        return get(fixPcSuiteBgLookupInfoUrl, id, ParametersHandle.PS_ID_HANDLE, ParameterUtils.INTEGER_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<Boolean> modifyAppleColorDict(AppleColorDictParameter g) throws ServiceException {
        return get(modifyAppleColorDict, g, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppleColorDict(AppleColorDictParameter g) throws ServiceException {
        return get(addAppleColorDict, g, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppleColorDict>> listAppleColorDict() throws ServiceException {
        return get(listAppleColorDict, null, null, ParameterUtils.APPLECOLORDICT_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppInstallReportInfo>> listAppInstallResportInfo(AppInstallReportInfoParameter g)
            throws ServiceException {
        return get(listAppInstallReportInfoUrl, g, ParametersHandle.PS_HANDLE, ParameterUtils.APP_INSTALL_REPORT_INFO);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppDownloadCodeInfo>> listAppDownloadCodeInfo(AppInstallReportInfoParameter g)
            throws ServiceException {
        return get(listAppDownloadCodeInfoUrl, g, ParametersHandle.PS_HANDLE, ParameterUtils.APP_DOWNLOAD_CODE_INFO);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> getAuthorizerAppDownAndBuyInfoStatusList(
            List<Integer> rootIds) throws ServiceException {
        return get(getAuthorizerAppDownAndBuyInfoStatusListUrl, rootIds,
                ParameterUtils.AUTHOR_APPDOWN_BOUGHT_INFO_RESP_PD_HANDLE,
                ParameterUtils.AUTHOR_APPDOWN_BOUGHT_INFO_RESP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<RootApplication>> listRootApplication(RootApplicationParameter paras)
            throws ServiceException {
        return get(this.rootapplicationListUrl, paras, ParameterUtils.ROOTAPPLICATION_PD_HANDLE,
                ParameterUtils.ROOTAPPLICATIONS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppStoreApplicationWrapper> getApplicationWrapper(Integer rootId) {
        return get(listAppStoreApplicationWrapperUrl, rootId, ParameterUtils.ROOTAPPLICATION_ROOTID_PD_HANDLE,
                ParameterUtils.ROOTAPPLICATIONWRAPPER_RD_HANDLE);
    }

}
