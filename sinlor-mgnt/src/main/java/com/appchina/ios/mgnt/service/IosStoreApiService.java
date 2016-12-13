package com.appchina.ios.mgnt.service;

import java.util.List;
import java.util.Map;

import com.appchina.ios.core.cahe.model.info.AppStoreArticleSimple;
import com.appchina.ios.core.cahe.model.info.VideoInfoWrapper;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.app.DownTypeIntervention;
import com.appchina.ios.core.dto.info.AppStoreAppTagAppleTagMap;
import com.appchina.ios.core.dto.info.AppStoreArticle;
import com.appchina.ios.core.dto.info.AppStoreArticleRecom;
import com.appchina.ios.core.dto.info.AppStoreArticleRecomIntervention;
import com.appchina.ios.core.dto.info.AppStoreArticleTag;
import com.appchina.ios.core.dto.info.AppStoreBanner;
import com.appchina.ios.core.dto.info.AppStoreClientAuditInfo;
import com.appchina.ios.core.dto.info.AppStoreClientConf;
import com.appchina.ios.core.dto.info.AppStoreClientDynamicCode;
import com.appchina.ios.core.dto.info.AppStoreClientInfo;
import com.appchina.ios.core.dto.info.AppStoreClientLoadPageAd;
import com.appchina.ios.core.dto.info.AppStoreH5Game;
import com.appchina.ios.core.dto.info.AppStorePromoteApplication;
import com.appchina.ios.core.dto.info.AppStorePromoteTab;
import com.appchina.ios.core.dto.info.AppStoreUserCenterConfigure;
import com.appchina.ios.core.dto.info.AppStoreUserCenterTipInfo;
import com.appchina.ios.core.dto.info.AppStoreVideoInfoRecomCate;
import com.appchina.ios.core.dto.info.AppStoreVideoInfoRecomClient;
import com.appchina.ios.core.dto.info.AppStoreWallpaper;
import com.appchina.ios.core.dto.info.AppTagListIntervention;
import com.appchina.ios.core.dto.info.CategoryRecom;
import com.appchina.ios.core.dto.info.DailyRecom;
import com.appchina.ios.core.dto.info.FunnyClientAdInfo;
import com.appchina.ios.core.dto.info.StoreCategory;
import com.appchina.ios.core.dto.info.VideoInfo;
import com.appchina.ios.core.dto.info.VideoMetaData;
import com.appchina.ios.mgnt.controller.model.DownTypeInterventionParameter;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.StoreCateRecomParameter;
import com.appchina.ios.mgnt.controller.model.StoreClientConfParameter;
import com.appchina.ios.mgnt.controller.model.StoreClientDynamicCodeParameter;
import com.appchina.ios.mgnt.controller.model.StoreVideoCateParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreArticleInterventionParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreArticleParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreArticleRecomParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreArticleTagParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientAuditInfoParamter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientInfoParamter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreH5GameParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStorePromoteTabModifyParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStorePromoteTabParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreUserCenterConfigureParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreUserCenterTipInfoParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreVideoAuditParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreWallpaperParameter;
import com.appchina.ios.mgnt.controller.model.info.BundleIdTimedStatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.info.DailyRecomParameter;
import com.appchina.ios.mgnt.controller.model.info.FunnyClientAdParameter;
import com.appchina.ios.mgnt.controller.model.info.ModifyStoreBannerParameter;
import com.appchina.ios.mgnt.controller.model.info.StoreAppTagAppleTagMapParameter;
import com.appchina.ios.mgnt.controller.model.info.StoreAppTagListInterventionParameter;
import com.appchina.ios.mgnt.controller.model.info.StoreBannerParameter;
import com.appchina.ios.mgnt.controller.model.info.StoreLoadPageAdParameter;
import com.appchina.ios.mgnt.controller.model.info.StoreLoadPageWallpaperParameter;
import com.appchina.ios.mgnt.controller.model.info.StorePromoteAppsAddParameter;
import com.appchina.ios.mgnt.controller.model.info.StorePromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.info.VideoInfoModifyParameter;
import com.appchina.ios.mgnt.controller.model.info.VideoMetaParameter;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.StartSizeParameter;

/**
 * Created by zhouyanhui on 7/20/15.
 */
public interface IosStoreApiService {

    ApiRespWrapper<ListWrapResp<AppStoreClientInfo>> listAppStoreClientInfo(StartSizeParameter param)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreClientInfo(AppStoreClientInfoParamter para) throws ServiceException;

    ApiRespWrapper<Boolean> saveAppStoreArticle(AppStoreArticleParameter param) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreClientConf>> getClientConfs(StoreClientConfParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyClientConf(StoreClientConfParameter para);

    ApiRespWrapper<Boolean> addClientConf(StoreClientConfParameter para);

    ApiRespWrapper<ListWrapResp<AppStoreArticle>> listAppStoreArticles(AppStoreArticleParameter para);

    ApiRespWrapper<Map<Integer, AppStoreArticleTag>> getAppStoreArticleTags(List<Integer> tagIds);

    ApiRespWrapper<Map<Integer, AppStoreArticleTag>> listAllAppStoreArticleTags();

    ApiRespWrapper<ListWrapResp<AppStoreArticleTag>> listAppStoreArticleTags(StatusStartSizeParameter para);

    ApiRespWrapper<Boolean> modifyAppStoreArticleTagStatus(AppStoreArticleTagParameter para);

    ApiRespWrapper<Boolean> modifyAppStoreArticleColor(AppStoreArticleTagParameter para);

    ApiRespWrapper<Boolean> modifyAppStoreArticleTag(AppStoreArticleTagParameter para);

    ApiRespWrapper<AppStoreArticleTag> saveAppStoreArticleTag(AppStoreArticleTagParameter param);

    ApiRespWrapper<AppStoreArticle> queryAppStoreArticle(int id) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreArticleRecom>> listAppStoreArticleRecom(AppStoreArticleRecomParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreArticleRecomStatus(AppStoreArticleRecomParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> updateAppStoreArticle(AppStoreArticleParameter article);

    ApiRespWrapper<Boolean> modifyAppStoreArticleStatus(AppStoreArticle article);

    ApiRespWrapper<Boolean> addAppStoreBanner(StoreBannerParameter para);

    ApiRespWrapper<Boolean> modifyBanner(StoreBannerParameter para);

    ApiRespWrapper<ListWrapResp<AppStoreBanner>> listUserAppStoreBanners(StoreBannerParameter para);

    ApiRespWrapper<ListWrapResp<AppStoreBanner>> listEditorAppStoreBanners(StoreBannerParameter para);

    ApiRespWrapper<ListWrapResp<AppStoreArticle>> getAppStoreArticle(List<Integer> artcileIds);

    ApiRespWrapper<Map<Integer, AppStoreArticleSimple>> getAppStoreArticleSimple(List<Integer> articleIds);

    ApiRespWrapper<ListWrapResp<AppStoreArticle>> searchAppStoreArticleByTitle(String title, int start, int size)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<StoreCategory>> getStoreVideoCates(StartSizeParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addStoreVideoCate(StoreVideoCateParameter para) throws ServiceException;

    ApiRespWrapper<StoreCategory> getStoreVideoCate(int id) throws ServiceException;

    ApiRespWrapper<Boolean> modifyVideoCate(StoreVideoCateParameter para) throws ServiceException;

    ApiRespWrapper<StoreCategory> getStoreAppTag(int id) throws ServiceException;

    ApiRespWrapper<ListWrapResp<StoreCategory>> getStoreAppTags(StartSizeParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppTag(StoreVideoCateParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addStoreAppTag(StoreVideoCateParameter para) throws ServiceException;


    ApiRespWrapper<CategoryRecom> getAppStoreCateRecom(int id) throws ServiceException;

    ApiRespWrapper<ListWrapResp<CategoryRecom>> getAppStoreCateRecoms(StoreCateRecomParameter para, boolean auditing)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreCateRecom(StoreCateRecomParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAppStoreCateRecom(StoreCateRecomParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreCateRecomSubCate(StoreCateRecomParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> removeAppStoreCateRecomSubCate(StoreCateRecomParameter para) throws ServiceException;


    ApiRespWrapper<ListWrapResp<DailyRecom>> listDailyRecomForAuditing(TimedStatusStartSizeParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<DailyRecom>> listDailyRecomForAudited(TimedStatusStartSizeParameter para)
            throws ServiceException;

    ApiRespWrapper<ApplicationSimple> getApplicationByRootId(int id) throws ServiceException;

    ApiRespWrapper<VideoInfo> getVideoInfo(int id) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomClient>> listAppStoreVideoInfoRecomBundleIds(int id)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomCate>> listAppStoreVideoInfoRecomCateIds(int id)
            throws ServiceException;

    ApiRespWrapper<VideoInfoWrapper> getVideoInfoWrapper(int id) throws ServiceException;

    ApiRespWrapper<Boolean> addAppStorePromoteTab(AppStorePromoteTabParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStorePromoteTabName(AppStorePromoteTabModifyParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStorePromoteTabStatus(AppStorePromoteTabModifyParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStorePromoteTabRank(AppStorePromoteTabModifyParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStorePromoteTabColor(AppStorePromoteTabModifyParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStorePromoteTabIcon(AppStorePromoteTabModifyParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStorePromoteTab>> listAppStorePromoteTabs(StatusStartSizeParameter para)
            throws ServiceException;

    ApiRespWrapper<AppStorePromoteTab> queryAppStorePromoteTab(Integer tabId) throws ServiceException;

    ApiRespWrapper<Boolean> addAppStorePromoteApplication(StorePromoteAppsAddParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyStorePromoteStatus(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyStorePromoteRank(PromoteAppsParameter para) throws ServiceException;

    ApiRespWrapper<Map<Integer, AppStorePromoteTab>> listAllAppStorePromoteTabs() throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStorePromoteApplication>> listStorePromoteApps(StorePromoteAppsParameter para)
            throws ServiceException;

    ApiRespWrapper<List<DailyRecom>> addDailyRecom(DailyRecomParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addLoadPageAd(StoreLoadPageAdParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyLoadPageAd(StoreLoadPageAdParameter param) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreClientLoadPageAd>> listLoadPageAds(StoreLoadPageAdParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreClientAuditInfo>> listAppStoreClientAuditInfo(
            AppStoreClientAuditInfoParamter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreClientAuditInfo(AppStoreClientAuditInfoParamter para) throws ServiceException;

    ApiRespWrapper<AppStoreClientAuditInfo> getAppStoreClientAuditInfo(int id) throws ServiceException;

    ApiRespWrapper<Map<String, String>> fetchAllAppleCategories() throws ServiceException;

    ApiRespWrapper<Map<String, String>> fetchCustomAppTags() throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreAppTagAppleTagMap>> listStoreAppTagAppleTagMaps(
            StoreAppTagAppleTagMapParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAppStoreAppTagAppleTagMap(StoreAppTagAppleTagMapParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreAppTagAppleTagMapStatus(StoreAppTagAppleTagMapParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreVideoMetaStatus(VideoMetaParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<VideoMetaData>> listAppStoreVideoMetaData(StartSizeParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<VideoInfoWrapper>> listAppStoreVideoInfoWrapper(VideoInfoModifyParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreVideoInfo(VideoInfoModifyParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreVideoRecomBundleId(VideoInfoModifyParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreVideoRecomCateId(VideoInfoModifyParameter para) throws ServiceException;

    ApiRespWrapper<VideoMetaData> getAppStorVideo(int id) throws ServiceException;

    ApiRespWrapper<Boolean> auditVideoInfo(AppStoreVideoAuditParameter para) throws ServiceException;

    ApiRespWrapper<String> getAppStoreHeadVideo() throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreDailyRecomStatus(ModifyStoreBannerParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreDailyRecomAuditedRank(ModifyStoreBannerParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreDailyRecomAuditingRank(ModifyStoreBannerParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreDailyRecomEndTime(ModifyStoreBannerParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreDailyRecom(DailyRecomParameter para) throws ServiceException;

    ApiRespWrapper<Map<Integer, ApplicationSimple>> getAppSimple(List<Integer> rootIds) throws ServiceException;

    ApiRespWrapper<VideoMetaData> getAppStoreVideoMetaData(String videoUrl) throws ServiceException;

    ApiRespWrapper<Map<String, String>> fetchAllAppTags() throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppTagListIntervention>> listAppStoreAppTagListInterventions(
            StoreAppTagListInterventionParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> addAppStoreAppTagListIntervention(StoreAppTagListInterventionParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreAppTagListInterventionStatus(StoreAppTagListInterventionParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreAppTagListInterventionRank(StoreAppTagListInterventionParameter para)
            throws ServiceException;


    ApiRespWrapper<Boolean> addAppStoreArticleIntervention(AppStoreArticleInterventionParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreArticleIntervention(AppStoreArticleInterventionParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreArticleRecomIntervention>> listAppStoreArticleIntervention(
            AppStoreArticleInterventionParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<DownTypeIntervention>> listDownTypeIntervention(DownTypeInterventionParameter para);

    ApiRespWrapper<Boolean> addDownTypeIntervention(DownTypeInterventionParameter para);

    ApiRespWrapper<Boolean> modifyDownTypeIntervention(DownTypeInterventionParameter para);

    ApiRespWrapper<List<String>> listAppStoreClientAllVersions(String bundleId) throws ServiceException;

    ApiRespWrapper<Boolean> addAppStoreClientDynamicCode(StoreClientDynamicCodeParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreClientDynamicCode(StoreClientDynamicCodeParameter para)
            throws ServiceException;

    ApiRespWrapper<AppStoreClientDynamicCode> detailAppStoreClientDynamicCode(StoreClientDynamicCodeParameter para)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreClientDynamicCode>> listAppStoreClientDynamicCode(
            StoreClientDynamicCodeParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<AppStoreH5Game>> listAppStoreH5Game(AppStoreH5GameParameter para)
            throws ServiceException;

    ApiRespWrapper<Boolean> addAppStoreH5Game(AppStoreH5GameParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreH5Game(AppStoreH5GameParameter para) throws ServiceException;

    // user center configure
    ApiRespWrapper<ListWrapResp<AppStoreUserCenterConfigure>> listAppStoreUserCenterConfigure(
            BundleIdTimedStatusStartSizeParameter para);

    ApiRespWrapper<Boolean> addAppStoreUserCenterConfigure(AppStoreUserCenterConfigureParameter configure);

    ApiRespWrapper<Boolean> modifyAppStoreUserCenterConfigure(AppStoreUserCenterConfigureParameter configure);

    // user center tip info
    ApiRespWrapper<ListWrapResp<AppStoreUserCenterTipInfo>> listAppStoreUserCenterTipInfo(
            BundleIdTimedStatusStartSizeParameter para);

    ApiRespWrapper<Boolean> addAppStoreUserCenterTipInfo(AppStoreUserCenterTipInfoParameter tipInfo);


    ApiRespWrapper<Boolean> modifyAppStoreUserCenterTipInfo(AppStoreUserCenterTipInfoParameter tipInfo);

    // wallpaper
    ApiRespWrapper<Boolean> addLoadPageWallpaper(StoreLoadPageWallpaperParameter wallpaper);

    ApiRespWrapper<ListWrapResp<AppStoreWallpaper>> listAppStoreWallpaper(AppStoreWallpaperParameter paper)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyAppStoreWallpaper(AppStoreWallpaperParameter paper) throws ServiceException;

    // funny
    ApiRespWrapper<Boolean> addFunnyClientAd(FunnyClientAdParameter para) throws ServiceException;

    ApiRespWrapper<Boolean> modifyFunnyClientAd(FunnyClientAdParameter para) throws ServiceException;

    ApiRespWrapper<ListWrapResp<FunnyClientAdInfo>> listFunnyClientAd(FunnyClientAdParameter para)
            throws ServiceException;

}
