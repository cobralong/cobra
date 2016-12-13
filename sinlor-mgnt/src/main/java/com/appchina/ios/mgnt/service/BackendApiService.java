// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import java.util.List;
import java.util.Map;

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
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankAddParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankInterventionParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankInterventionUpdateParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankParameter;
import com.appchina.ios.mgnt.controller.model.RootApplicationParameter;
import com.appchina.ios.mgnt.controller.model.SignatureIpasParameter;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreApplicationWrapper;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface BackendApiService {
    ApiRespWrapper<Map<Integer, ApplicationSimple>> getAppSimple(List<Integer> rootIds) throws ServiceException;

    ApiRespWrapper<ApplicationSimple> getAppSimple(int rootId) throws ServiceException;

    ApiRespWrapper<ListWrapResp<IpaPlist>> getIpaPlists(IpaPlistParameter para) throws ServiceException;

    ApiRespWrapper<Map<Integer, ApplicationSimple>> getRootSimpleByItemIds(List<Integer> itemIds)
            throws ServiceException;

    ApiRespWrapper<Map<String, ApplicationSimple>> getRootIdByBundleIds(List<String> bundleIds) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ApplicationSimple>> searchAppByTitle(String title, int start, int size)
            throws ServiceException;

    ApiRespWrapper<SearchResp> searchApp(String query, int start, int size) throws ServiceException;

    ApiRespWrapper<ListWrapResp<PromoteApplication>> listPromoteApps(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addPromote(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPromote(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> offPromoteByRootId(int rootId) throws ServiceException;


    ApiRespWrapper<ListWrapResp<Banner>> listBannerApps(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<Banner>> addBanner(BannerAddParameter parameter) throws ServiceException;

    ApiRespWrapper<Boolean> modifyBannerStatus(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyBannerRank(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyBannerEndTime(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> offlineFromBanner(int rootId) throws ServiceException;

    ApiRespWrapper<Banner> getBanner(int bannerId) throws ServiceException;

    ApiRespWrapper<ListWrapResp<SearchHotword>> listHotwords(String name, String channel, int status, int start,
            int size) throws ServiceException;

    ApiRespWrapper<Boolean> addSearchHotword(String name, int rank, String channel) throws ServiceException;

    ApiRespWrapper<Boolean> modifySearchHotword(int id, int status) throws ServiceException;

    ApiRespWrapper<ListWrapResp<SearchPlaceholder>> listPlaceholders(String channel, int status, int start, int size)
            throws ServiceException;

    ApiRespWrapper<Boolean> addSearchPlaceholder(String name, String channel, int page) throws ServiceException;

    ApiRespWrapper<Boolean> modifySearchPlaceholder(int id, int status) throws ServiceException;


    ApiRespWrapper<ListWrapResp<SearchDefaultResult>> listSearchDefaultResult(String channel, int status, int start,
            int size) throws ServiceException;

    ApiRespWrapper<Boolean> addSearchDefaultResult(int rootId, int rank, String channel) throws ServiceException;

    ApiRespWrapper<Boolean> modifySearchDefaultResult(int id, int status) throws ServiceException;

    ApiRespWrapper<Boolean> addProductPageDictionary(String channel, int page, String desc) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ProductPageDictionary>> listProductPageDictionary(String channel)
            throws ServiceException;

    ApiRespWrapper<Map<String, String>> mapProductChannelPageDictionary(String channel) throws ServiceException;

    /**
     * 得到排行榜类别 type->desc
     * 
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Map<String, String>> mapRankTypeDictionary() throws ServiceException;

    /**
     * 得到分类 cid->desc
     * 
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Map<String, String>> mapCategoryDictionary() throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppRankIntervention>> listAppRankInterventions(AppRankListParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppRankIntervention>> listAppRankInterventionByRootId(AppRankListParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addAppRankInterventions(AppRankListParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppRankInterventions(AppRankListParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppRankInterventionRank(AppRankListParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppRank>> listAppRank(AppRankListParameter para) throws ServiceException;

    ApiRespWrapper<Map<String, IpaPlist>> getIpaPlists(List<Integer> plistIds) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPlsit(IpaPlistModifyParameter param) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPromoteRank(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Application> getApplication(int id) throws ServiceException;

    ApiRespWrapper<Application> getApplicationByRootId(int id) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ApplicationItunesImgRes>> getApplicationResource(int id) throws ServiceException;

    ApiRespWrapper<Category> getCategory(int categoryId) throws ServiceException;

    ApiRespWrapper<ListWrapResp<Category>> listChildCategory(int categoryId) throws ServiceException;

    ApiRespWrapper<ListWrapResp<String>> listItunesCategoryName(int categoryId) throws ServiceException;

    ApiRespWrapper<ListWrapResp<Application>> listApplication(ApplicationParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ApplicationSimple>> listEmphasisApplication(AppEmphasisParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyEmphasisStatus(AppEmphasisParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addEmphasis(AppEmphasisParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppBanned>> listBannedApplication(AppBannedParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppBannedStatus(AppBannedParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAppBanned(AppBannedParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyApplicationEditorTitle(ApplicationModifyParameter applicationModifyParameter)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyApplicationEditorReview(ApplicationModifyParameter applicationModifyParameter)
            throws ServiceException;

    ApiRespWrapper<ClientConf> getClientConf() throws ServiceException;

    ApiRespWrapper<Boolean> modifyClientConf(ClientConfModifyParameter parameter) throws ServiceException;

    ApiRespWrapper<ListWrapResp<IpaCertSignature>> getEnterpriseSignatures(StatusStartSizeParameter parameter)
            throws ServiceException;

    ApiRespWrapper<IpaCertSignature> getEnterpriseSignature(SignatureIpasParameter parameter) throws ServiceException;

    ApiRespWrapper<ListWrapResp<IpaPlist>> getEnterpriseSignatureIpas(SignatureIpasParameter parameter)
            throws ServiceException;

    ApiRespWrapper<Boolean> disableEnterpriseSignatures(SignatureIpasParameter parameter) throws ServiceException;

    ApiRespWrapper<String> getPlistHostUrl() throws ServiceException;

    ApiRespWrapper<ListWrapResp<MarketInfo>> getMarketInfos() throws ServiceException;

    ApiRespWrapper<Boolean> addMarketInfo(MarketInfoParameter parameter) throws ServiceException;

    ApiRespWrapper<ListWrapResp<IpaPlist>> listMarketIpas(Integer id) throws ServiceException;

    ApiRespWrapper<ListWrapResp<IpaItunesMetaData>> listIpaItunesMetaDatas(IpaPlistParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<DownTypeIntervention>> listDownTypeIntervention(DownTypeInterventionParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addDownTypeIntervention(DownTypeInterventionParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyDownTypeIntervention(DownTypeInterventionParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ApplePlatform>> listPlatform() throws ServiceException;

    ApiRespWrapper<Boolean> addPlatform(PlatformParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleOsVersion>> listOsVersion() throws ServiceException;

    ApiRespWrapper<Boolean> modifyPlatformOsVersion(PlatformOsVersionParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addOsVersion(OsVersionParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<DispatchDesc>> listDispatchDesc() throws ServiceException;

    ApiRespWrapper<Boolean> modifyDispatchDesc(DispatchDescParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ClientChannel>> listClientChannel(ClientChannelParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ClientChannel>> listParentClientChannel() throws ServiceException;

    ApiRespWrapper<ListWrapResp<ClientChannel>> listLeafClientChannel() throws ServiceException;

    ApiRespWrapper<Boolean> addClientChannel(ClientChannelParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleAccount>> listAppleAccount(AppleAccountParamter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<PcSuiteBindAppleIdInfo>> listPcSuiteBindAppleIdInfo(PcSuiteBindAppleIdInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleAccountSource>> listAppleAccountSource() throws ServiceException;

    ApiRespWrapper<Boolean> addAppleAccountSource(AppleAccountSourceParamter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<DispatchConfigure>> listDispatchConfigure(DispatchConfigureParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addDispatchConfigure(DispatchConfiigureAddParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppLocale>> listGlobalLocales() throws ServiceException;

    ApiRespWrapper<Boolean> modifyGlobalLocale(AppLocaleParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppAppleRank>> listGlobalLocaleRankApps(AppAppleRankListParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<EnterpriseIpaFile>> listUploadIpaDatas(IpaUploadListParameter para)
            throws ServiceException;

    ApiRespWrapper<Map<Integer, LimitDiscountApplicationSimple>> getLimitDiscountApplicationSimple(List<Integer> rootIds)
            throws ServiceException;

    ApiRespWrapper<Boolean> publishClient(ClientUpgradeInfo data) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ClientUpgradeInfo>> listClientUpgradeInfos(ClientUpgradeInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyClientUpgradeInfo(ClientUpgradeInfoParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<RootApplication>> listRootApplication(List<Integer> rootIds) throws ServiceException;

    ApiRespWrapper<RootApplication> getRootApplication(int rootId) throws ServiceException;

    ApiRespWrapper<Map<String, String>> mapAppTagRankTypeDictionary() throws ServiceException;

    ApiRespWrapper<Map<String, String>> mapAppTagCategoryDictionary() throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppTagRank>> listAppTagRank(AppTagRankListParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppTagRankIntervention>> listAppTagRankInterventions(AppTagRankListParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppTagRankInterventionStatus(AppTagRankListParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppTagRankInterventionRank(AppTagRankListParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAppTagRankInterventions(AppTagRankListParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<SystemEmergency>> viewSystemEmergency() throws ServiceException;

    ApiRespWrapper<Boolean> saveSystemEmergency(SystemEmergency systemEmergency) throws ServiceException;

    ApiRespWrapper<Boolean> updateSystemEmergencyStatus(SystemEmergency systemEmergency) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppTag>> listChildTags(AppTagListParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppTag>> listAppTags() throws ServiceException;

    ApiRespWrapper<SystemEmergency> getSystemEmergencyById(Integer id) throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteItunesInstallInfo(PcSuiteItunesDriverInstallInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<PcSuiteItunesDriverInstallInfo>> listPcSuiteItunesDriverInstallInfo(
            PcSuiteItunesDriverInstallInfoParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteItunesDriverInstallInfoStatus(
            PcSuiteItunesDriverInstallInfoParameter pcSuiteItunesDll) throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteItunesDriver(PcSuiteItunesDriverParameter pcSuiteItunesDriver)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<PcSuiteItunesDriver>> listPcSuiteItunesDriver(PcSuiteItunesDriverParameter para)
            throws ServiceException;

    ApiRespWrapper<PcSuiteItunesDriver> detailPcSuiteItunesDriver(int id) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteItunesDriverStatus(PcSuiteItunesDriverParameter pcSuiteItunesDriver)
            throws ServiceException;

    ApiRespWrapper<IpaCertSignature> getIpapListCertInfo(ApplicationSimple ret) throws ServiceException;

    ApiRespWrapper<ListWrapResp<IpaLoadStat>> listIpaLoadStat(IpaLoadStatParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addRootApplicationtag(RootApplicationTag rootApplicationTag) throws ServiceException;

    ApiRespWrapper<Boolean> modifyRootApplicationTag(RootApplicationTag para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<RootApplicationTag>> listRootApplicationTags(StatusStartSizeParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<PcSuiteIphoneModel>> listPcSuiteIPhoneModel(PcSuiteIphoneModelParameter para)
            throws ServiceException;

    ApiRespWrapper<PcSuiteIphoneModel> detailPcSuiteIPhoneModel(PcSuiteIphoneModelParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteIPhoneModel(PcSuiteIphoneModelParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteIPhoneModel(PcSuiteIphoneModelParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<PcSuiteIosProgrammerDriver>> listPcSuiteProgrammerDriver(
            PcSuiteIosProgrammerDriverParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteProgrammerDriver(PcSuiteIosProgrammerDriverParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addPcSuiteProgrammerDriver(PcSuiteIosProgrammerDriverParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleDevice>> listAppleDevice(AppleDeviceParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAppleDevice(AppleDeviceParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleShareAccount>> listShareAccount(AppleShareAccountParamter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addShareAccount(AppleShareAccountParamter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyShareAccountSource(AppleShareAccountParamter para) throws ServiceException;

    ApiRespWrapper<AppleShareAccount> detailShareAccount(AppleShareAccountParamter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleIdBuyHistory>> listShareAccountBuyHistory(AppleIdBuyHistoryParamter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addShareAccountBuyHistory(AppleIdBuyHistoryParamter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyShareAccountBuyHistory(AppleShareAccountParamter para) throws ServiceException;

    ApiRespWrapper<Map<String, String>> fetchRootAppRankMetaTypes() throws ServiceException;

    ApiRespWrapper<ListWrapResp<RootAppRankSimple>> listRootAppRanks(RootAppRankParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addRootAppRankInterventions(RootAppRankAddParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyRootAppRankIntervention(RootAppRankInterventionUpdateParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<RootAppRankSimple>> listRootAppRankInterventions(RootAppRankInterventionParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppWishInfo>> listAppWishInfo(AppWishParameter para);

    ApiRespWrapper<ListWrapResp<AppWishHistory>> listAppWishHistory(AppWishParameter para);

    ApiRespWrapper<ListWrapResp<Category>> getRootApplicationCategory(Integer rootId);

    ApiRespWrapper<ListWrapResp<AccountFeedback>> listAccountFeedback(AccountFeedbackParameter para)
            throws ServiceException;

    ApiRespWrapper<AccountFeedback> detailAccountFeedback(int id) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAccountFeedbback(AccountFeedbackParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ListAdPlace>> listListAdPlace() throws ServiceException;

    ApiRespWrapper<Boolean> addListAdInfo(ListAdInfoParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ListAdInfo>> listListAdInfo(ListAdInfoParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ListAdInfo>> detailListAdInfo(List<Integer> ids) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ListAdRecomInfo>> listListAdRecomInfo(ListAdRecomInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addListAdRecomInfo(ListAdRecomInfoParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyListAdRecomInfo(ListAdRecomInfoParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<ClientApnCertInfo>> listAppStoreClientCertInfo(ClientApnCertInfoParameter parameter)
            throws ServiceException;

    ApiRespWrapper<Boolean> addAppStoreClientCertInfo(ClientApnCertInfoParameter parameter) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreClientCertInfo(ClientApnCertInfoParameter parameter) throws ServiceException;

    ApiRespWrapper<ListWrapResp<Account>> listAccount(AccountParamter para) throws ServiceException;

    List<Account> listAccount(List<Integer> ids) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AccountWishInfo>> listAccountWishInfo(AccountWishInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AccountFavorite>> listAccountFavoriteInfo(AccountWishInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AccountWishProgressInfo>> listAccountWishProgressInfo(
            AccountWishProgressInfoParameter para) throws ServiceException;

    List<AppleAccount> listAppleAccountByBindAccountId(List<Integer> accountIds) throws ServiceException;

    ApiRespWrapper<ListWrapResp<PcSuiteBgLookupInfo>> listPcSuiteBgLookupInfo() throws ServiceException;

    ApiRespWrapper<Boolean> modifyPcSuiteBgLookupInfo(IdStatusParameter para) throws ServiceException;

    ApiRespWrapper<Integer> fixPcSuiteBgLookupInfo(int id) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppleDevice(AppleDeviceParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppleColorDict(AppleColorDictParameter appleColorDict) throws ServiceException;

    ApiRespWrapper<Boolean> addAppleColorDict(AppleColorDictParameter appleColorDict) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleColorDict>> listAppleColorDict() throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppleAccountAutomatedInfo>> listAppleAccountAutomatedInfo(
            AppleAccountAutomatedInfoParameter para) throws ServiceException;

    ApiRespWrapper<AppleAccountAutomatedInfo> detailAppleAccountAutomatedInfo(int id) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPromoteStartTime(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPromoteEndTime(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPromoteStartTimeOnAllChannel(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyPromoteEndTimeOnAllChannel(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppInstallReportInfo>> listAppInstallResportInfo(AppInstallReportInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppDownloadCodeInfo>> listAppDownloadCodeInfo(AppInstallReportInfoParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> getAuthorizerAppDownAndBuyInfoStatusList(
            List<Integer> rootIds) throws ServiceException;

    ApiRespWrapper<ListWrapResp<RootApplication>> listRootApplication(RootApplicationParameter para)
            throws ServiceException;

    ApiRespWrapper<AppStoreApplicationWrapper> getApplicationWrapper(Integer rootId);
}
