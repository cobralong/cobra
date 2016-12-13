package com.appchina.ios.mgnt.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
import com.appchina.ios.mgnt.controller.model.TitleLikeParameter;
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
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.utils.FunnyReturnDataUtils;
import com.appchina.ios.mgnt.utils.ParameterUtils;
import com.appchina.ios.mgnt.utils.StoreParameterUtils;
import com.appchina.ios.mgnt.utils.StoreReturnDataUtils;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.model.PostParametersHandle;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.model.StartSizeParameter;
import com.appchina.ios.web.utils.CollectionUtils;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * Created by zhouyanhui on 7/21/15.
 */
@Service("iosStoreApiService")
public class IosStoreApiServiceImpl implements IosStoreApiService {

    private static final Logger log = Logger.getLogger(IosStoreApiServiceImpl.class);

    // article
    @Value("${ios.mgnt.store.getAppStoreArticleByIdUrl}")
    private String getAppStoreArticleByIdUrl = "http://localhost:8080/ios-store/store/admin/article/detail.json";
    @Value("${ios.mgnt.store.addAppStoreArticleUrl}")
    private String addAppStoreArticleUrl = "http://localhost:8080/ios-store/store/admin/article/add.json";
    @Value("${ios.mgnt.store.updateAppStoreArticleUrl}")
    private String updateAppStoreArticleUrl = "http://localhost:8080/ios-store/store/admin/article/modify.json";
    @Value("${ios.mgnt.store.listAppStoreArticlesUrl}")
    private String listAppStoreArticlesUrl = "http://localhost:8080/ios-store/store/admin/article/list.json";
    @Value("${ios.mgnt.store.modifyAppStoreArticleStatusUrl}")
    private String modifyAppStoreArticleStatusUrl = "http://localhost:8080/ios-store/store/admin/article/modifystatus.json";
    @Value("${ios.mgnt.store.modifyTagOfAppStoreArticleUrl}")
    private String modifyTagOfAppStoreArticleUrl = "http://localhost:8080/ios-store/store/admin/article/modifytag.json";
    @Value("${ios.mgnt.store.modifyAppStoreArticleStartTimeUrl}")
    private String modifyAppStoreArticleStartTimeUrl = "http://localhost:8080/ios-store/store/admin/article/modifytime.json";


    @Value("${ios.mgnt.store.listappstorearticlerecomurl}")
    private String listAppStoreArticlesRecomUrl = "http://localhost:8080/ios-store/store/admin/article/recom/list.json";
    @Value("${ios.mgnt.store.modifyappstorearticlerecomstatusurl}")
    private String modifyAppStoreArticlesRecomUrl = "http://localhost:8080/ios-store/store/admin/article/recom/modifystatus.json";


    @Value("${ios.mgnt.store.getAppStoreArticleTagMapUrl}")
    private String getAppStoreArticleTagMapUrl = "http://localhost:8080/ios-store/store/admin/articletag/tagmap.json";
    @Value("${ios.mgnt.store.getAllAppStoreArticleTagMapUrl}")
    private String getAllAppStoreArticleTagMapUrl = "http://localhost:8080/ios-store/store/admin/articletag/alltagmap.json";
    @Value("${ios.mgnt.store.listAppStoreArticleTagUrl}")
    private String listAppStoreArticleTagUrl = "http://localhost:8080/ios-store/store/admin/articletag/listtag.json";
    @Value("${ios.mgnt.store.modifyAppStoreArticleTagStatusUrl}")
    private String modifyAppStoreArticleTagStatusUrl = "http://localhost:8080/ios-store/store/admin/articletag/modifystatus.json";
    @Value("${ios.mgnt.store.modifyAppStoreArticleTagColorUrl}")
    private String modifyAppStoreArticleTagColorUrl = "http://localhost:8080/ios-store/store/admin/articletag/modifycolor.json";
    @Value("${ios.mgnt.store.modifyAppStoreArticleTagUrl}")
    private String modifyAppStoreArticleTagUrl = "http://localhost:8080/ios-store/store/admin/articletag/modify.json";
    @Value("${ios.mgnt.store.saveAppStoreArticleTagUrl}")
    private String saveAppStoreArticleTagUrl = "http://localhost:8080/ios-store/store/admin/articletag/savetag.json";
    @Value("${ios.mgnt.store.searchAppStoreArticleByTitleUrl}")
    private String searchAppStoreArticleByTitleUrl = "http://localhost:8080/ios-store/store/admin/article/titlesearch.json";
    @Value("${ios.mgnt.store.getAppStoreArticlesUrl}")
    private String getAppStoreArticlesUrl = "http://localhost:8080/ios-store/store/admin/article/articlebyids.json";
    // article intervention
    @Value("${ios.mgnt.store.listappstorearticleinterventionurl}")
    private String listAppStoreArticleInterventionUrl = "http://localhost:8080/store/admin/article/recom/intervention/list.json";
    @Value("${ios.mgnt.store.modifyappstorearticleinterventionurl}")
    private String modifyAppStoreArticleInterventionUrl = "http://localhost:8080/store/admin/recom/intervention/modify.json";
    @Value("${ios.mgnt.store.addappstorearticleinterventionurl}")
    private String addAppStoreArticleInterventionUrl = "http://localhost:8080/store/admin/recom/intervention/add.json";

    // client conf
    @Value("${ios.mgnt.store.liststoreclienturl}")
    private String listStoreClientConfUrl = "http://10.18.0.54:11211/store/admin/system/listconf.json";
    @Value("${ios.mgnt.store.modifystoreclienturl}")
    private String modifyStoreClientConfUrl = "http://10.18.0.54:11211/store/admin/system/modify.json";
    @Value("${ios.mgnt.store.addstoreclienturl}")
    private String addStoreClientConfUrl = "http://10.18.0.54:11211/store/admin/system/add.json";
    @Value("${ios.mgnt.store.modifyauditinfourl}")
    private String modifyAuditInfoUrl = "http://10.18.0.54:11211/store/admin/system/modifyauditinfo.json";
    @Value("${ios.mgnt.store.listauditinfourl}")
    private String listAuditInfoUrl = "http://10.18.0.54:11211/store/admin/system/listauditinfo.json";
    @Value("${ios.mgnt.store.auditinfodetailurl}")
    private String auditInfoDeatilUrl = "http://10.18.0.54:11211/store/admin/system/auditinfodetail.json";
    @Value("${ios.mgnt.store.modifyclientinfourl}")
    private String modifyClientInfoUrl = "http://10.18.0.54:11211/store/admin/system/modifyclientinfo.json";
    @Value("${ios.mgnt.store.listclientinfourl}")
    private String listClientInfoUrl = "http://10.18.0.54:11211/store/admin/system/listclientinfo.json";
    @Value("${ios.mgnt.store.listclientversionurl}")
    private String listClientVersionUrl = "http://10.18.0.54:11211/store/admin/system/listclientversion.json";
    // dynamic code
    @Value("${ios.mgnt.store.addclientdynamiccodeurl}")
    private String addClientDynamicCodeUrl = "http://10.18.0.54:11211/store/admin/system/adddynamiccode.json";
    @Value("${ios.mgnt.store.listclientdynamiccodeurl}")
    private String listClientDynamicCodeUrl = "http://10.18.0.54:11211/store/admin/system/listdynamiccode.json";
    @Value("${ios.mgnt.store.modifyclientdynamiccodeurl}")
    private String modifyClientDynamicCodeUrl = "http://10.18.0.54:11211/store/admin/system/modifydynamiccode.json";
    @Value("${ios.mgnt.store.detailclientdynamiccodeurl}")
    private String detailClientDynamicCodeUrl = "http://10.18.0.54:11211/store/admin/system/detaildynamiccode.json";

    // user center configure board
    @Value("${ios.mgnt.store.addappstoreusercenterconfigureurl}")
    private String addAppStoreUserCenterConfigureUrl = "http://10.18.0.54:11211/store/admin/usercenter/configure/add.json";
    @Value("${ios.mgnt.store.modifyappstoreusercenterconfigureurl}")
    private String modifyAppStoreUserCenterConfigureUrl = "http://10.18.0.54:11211/store/admin/usercenter/configure/modify.json";
    @Value("${ios.mgnt.store.listappstoreusercenterconfigureurl}")
    private String listAppStoreUserCenterConfigureUrl = "http://10.18.0.54:11211/store/admin/usercenter/configure/list.json";
    // user center tip info board
    @Value("${ios.mgnt.store.addappstoreusercentertipinfourl}")
    private String addAppStoreUserCenterTipInfoUrl = "http://10.18.0.54:11211/store/admin/usercenter/tipinfo/add.json";
    @Value("${ios.mgnt.store.modifyappstoreusercentertipinfourl}")
    private String modifyAppStoreUserCenterTipInfoUrl = "http://10.18.0.54:11211/store/admin/usercenter/tipinfo/modify.json";
    @Value("${ios.mgnt.store.listappstoreusercentertipinfourl}")
    private String listAppStoreUserCenterTipInfoUrl = "http://10.18.0.54:11211/store/admin/usercenter/tipinfo/list.json";



    // H5 GAME
    @Value("${ios.mgnt.store.listh5gameurl}")
    private String listH5GameUrl = "http://10.18.0.54:11211/store/admin/h5game/list";
    @Value("${ios.mgnt.store.addh5gameurl}")
    private String addH5GameUrl = "http://10.18.0.54:11211/store/admin/h5game/add";
    @Value("${ios.mgnt.store.modifyh5gameurl}")
    private String modifyH5GameUrl = "http://10.18.0.54:11211/store/admin/h5game/modify";

    // banner
    @Value("${ios.mgnt.store.addappstorebannerurl}")
    private String addAppStoreBannerUrl = "http://10.18.0.54:11211/store/admin/banner/add.json";
    @Value("${ios.mgnt.store.modifyappstorebannerurl}")
    private String modifyAppStoreBannerUrl = "http://10.18.0.54:11211/store/admin/banner/modify.json";
    @Value("${ios.mgnt.store.listuserappstorebannerurl}")
    private String listUserAppStoreBannerUrl = "http://10.18.0.54:11211/store/admin/banner/listuser.json";
    @Value("${ios.mgnt.store.listeditorappstorebannerurl}")
    private String listEditorAppStoreBannerUrl = "http://10.18.0.54:11211/store/admin/banner/listeditor.json";
    @Value("${ios.mgnt.store.getAppStoreArticleSimpleMapUrl}")
    private String getAppStoreArticleSimpleMapUrl = "http://10.18.0.54:11211/store/article/simples.json";

    // promote tab
    @Value("${ios.mgnt.store.addAppStorePromoteTabUrl}")
    private String addAppStorePromoteTabUrl = "http://10.18.0.54:11211/store/admin/promotetab/add.json";
    @Value("${ios.mgnt.store.modifyAppStorePromoteTabStatusUrl}")
    private String modifyAppStorePromoteTabStatusUrl = "http://10.18.0.54:11211/store/admin/promotetab/modifystatus.json";
    @Value("${ios.mgnt.store.modifyappstorepromotetabnameurl}")
    private String modifyAppStorePromoteTabNameUrl = "http://10.18.0.54:11211/store/admin/promotetab/modifyname.json";
    @Value("${ios.mgnt.store.modifyAppStorePromoteTabRankUrl}")
    private String modifyAppStorePromoteTabRankUrl = "http://10.18.0.54:11211/store/admin/promotetab/modifyrank.json";
    @Value("${ios.mgnt.store.modifyAppStorePromoteTabColorUrl}")
    private String modifyAppStorePromoteTabColorUrl = "http://10.18.0.54:11211/store/admin/promotetab/modifycolor.json";
    @Value("${ios.mgnt.store.modifyappstorepromotetabiconurl}")
    private String modifyAppStorePromoteTabIconUrl = "http://10.18.0.54:11211/store/admin/promotetab/modifyicon.json";
    @Value("${ios.mgnt.store.listAppStorePromoteTabsUrl}")
    private String listAppStorePromoteTabsUrl = "http://10.18.0.54:11211/store/admin/promotetab/list.json";
    @Value("${ios.mgnt.store.queryAppStorePromoteTabUrl}")
    private String queryAppStorePromoteTabUrl = "http://10.18.0.54:11211/store/admin/promotetab/detail.json";

    // video
    @Value("${ios.mgnt.store.delvideometaurl}")
    private String modifyVideoMetaStatusUrl = "http://10.18.0.54:11211/store/admin/videoinfo/videometa/modify.json";
    @Value("${ios.mgnt.store.videolistforauditingurl}")
    private String listVideoForAuditingUrl = "http://10.18.0.54:11211/store/admin/videoinfo/auditingvideolist.json";
    @Value("${ios.mgnt.store.auditvideoinfourl}")
    private String auditVideoInfoUrl = "http://10.18.0.54:11211/store/admin/videoinfo/audit.json";
    @Value("${ios.mgnt.store.videoinfolisturl}")
    private String listVideoInfoUrl = "http://10.18.0.54:11211/store/admin/videoinfo/videoinfolist.json";
    @Value("${ios.mgnt.store.videoinfodetailurl}")
    private String detailAuditedVideoInfoUrl = "http://10.18.0.54:11211/store/admin/videoinfo/videoinfodetail.json";
    @Value("${ios.mgnt.store.modifyvideoinfourl}")
    private String modifyVideoInfoUrl = "http://10.18.0.54:11211/store/admin/videoinfo/modifyvideoinfo.json";
    @Value("${ios.mgnt.store.videoinfowrapperurl}")
    private String videoInfoWrapperUrl = "http://10.18.0.54:11211/store/admin/videoinfo/videoinfowrapper.json";
    @Value("${ios.mgnt.store.videometaurl}")
    private String videoMetaUrl = "http://10.18.0.54:11211/store/admin/videoinfo/videometa.json";
    @Value("${ios.mgnt.store.headvideodetailurl}")
    private String headVideoDetailUrl = "http://10.18.0.54:11211/store/admin/videoinfo/headvideodetail.json";

    @Value("${ios.mgnt.store.listappstorevideoinforecomclienturl}")
    private String listAppStoreVideoInfoRecomClientUrl = "http://10.18.0.54:11211/store/admin/videoinfo/recom/client/list.json";
    @Value("${ios.mgnt.store.listappstorevideoinforecomcateurl}")
    private String listAppStoreVideoInfoRecomCateUrl = "http://10.18.0.54:11211/store/admin/videoinfo/recom/cate/list.json";
    @Value("${ios.mgnt.store.modifyappstorevideoinforecomclienturl}")
    private String modifyAppStoreVideoInfoRecomClientUrl = "http://10.18.0.54:11211/store/admin/videoinfo/recom/client/modify.json";
    @Value("${ios.mgnt.store.modifyappstorevideoinforecomcateurl}")
    private String modifyAppStoreVideoInfoRecomCateUrl = "http://10.18.0.54:11211/store/admin/videoinfo/recom/cate/modify.json";

    // video cate
    @Value("${ios.mgnt.store.listvideocateurl}")
    private String listStoreVideoCateUrl = "http://10.18.0.54:11211/store/admin/system/listvideocate.json";
    @Value("${ios.mgnt.store.detailvideocateurl}")
    private String detailStoreVideoCateUrl = "http://10.18.0.54:11211/store/admin/system/detailvideocate.json";
    @Value("${ios.mgnt.store.modifyvideocateurl}")
    private String modifyStoreVideoCateUrl = "http://10.18.0.54:11211/store/admin/system/modifyvideocate.json";
    @Value("${ios.mgnt.store.addvideocateurl}")
    private String addStoreVideoCateUrl = "http://10.18.0.54:11211/store/admin/system/addvideocate.json";
    // app tag
    @Value("${ios.mgnt.store.listapptagurl}")
    private String listStoreAppTagUrl = "http://10.18.0.54:11211/store/admin/system/listapptag.json";
    @Value("${ios.mgnt.store.detailapptagurl}")
    private String detailStoreAppTagUrl = "http://10.18.0.54:11211/store/admin/system/detailapptag.json";
    @Value("${ios.mgnt.store.modifyapptagurl}")
    private String modifyStoreAppTagUrl = "http://10.18.0.54:11211/store/admin/system/modifyapptag.json";
    @Value("${ios.mgnt.store.addapptagurl}")
    private String addStoreAppTagUrl = "http://10.18.0.54:11211/store/admin/system/addapptag.json";

    // category recom
    @Value("${ios.mgnt.store.listappstorecaterecomforuserurl}")
    private String listAppStoreCateRecomForUserUrl = "http://10.18.0.54:11211/store/admin/cate/listcaterecomforuser.json";
    @Value("${ios.mgnt.store.listappstorecaterecomforeditorurl}")
    private String listAppStoreCateRecomForEditorUrl = "http://10.18.0.54:11211/store/admin/cate/listcaterecomforeditor.json";
    @Value("${ios.mgnt.store.detailcaterecomurl}")
    private String detailStoreCateRecomUrl = "http://10.18.0.54:11211/store/admin/cate/detailcaterecom.json";
    @Value("${ios.mgnt.store.modifycaterecomurl}")
    private String modifyStoreCateRecomUrl = "http://10.18.0.54:11211/store/admin/system/modifycaterecom.json";
    @Value("${ios.mgnt.store.modifysubcateurl}")
    private String modifySubCateUrl = "http://10.18.0.54:11211/store/admin/system/modifysubcate.json";
    @Value("${ios.mgnt.store.removesubcateurl}")
    private String removeSubCateUrl = "http://10.18.0.54:11211/store/admin/system/removesubcate.json";
    @Value("${ios.mgnt.store.addcaterecomurl}")
    private String addCateRecomUrl = "http://10.18.0.54:11211/store/admin/system/addcaterecom.json";

    // DAILY RECOM
    @Value("${ios.mgnt.store.listdailyrecomauditorlisturl}")
    private String listDailyRecomAuditorListUrl = "http://10.18.0.54:11211/store/admin/dailyrecom/auditorlist.json";
    @Value("${ios.mgnt.store.listdailyrecomuserlisturl}")
    private String listDailyRecomUserListUrl = "http://10.18.0.54:11211/store/admin/dailyrecom/userlist.json";
    @Value("${ios.mgnt.store.adddailyrecomurl}")
    private String addDailyRecomUrl = "http://10.18.0.54:11211/store/admin/dailyrecom/adddailyrecom.json";
    @Value("${ios.mgnt.store.modifystatusurl}")
    private String modifyStatusUrl = "http://10.18.0.54:11211/store/admin/dailyrecom/modifystatus.json";
    @Value("${ios.mgnt.store.modifyendtimeurl}")
    private String modifyEndTimeUrl = "http://10.18.0.54:11211/store/admin/dailyrecom/modifyendtime.json";
    @Value("${ios.mgnt.store.modifyauditedrankurl}")
    private String modifyAuditedRankUrl = "http://10.18.0.54:11211/store/admin/dailyrecom/modifyauditedrank.json";
    @Value("${ios.mgnt.store.modifyauditingrankurl}")
    private String modifyAuditingRankUrl = "http://10.18.0.54:11211/store/admin/dailyrecom/modifyauditingrank.json";
    @Value("${ios.mgnt.store.modifydailyrecomurl}")
    private String modifyDailyRecomUrl = "http://10.18.0.54:11211/store/admin/dailyrecom/modify.json";

    // promote
    @Value("${ios.mgnt.store.addAppStorePromoteApplicationUrl}")
    private String addAppStorePromoteApplicationUrl = "http://10.18.0.54:11211/store/admin/promote/add.json";
    @Value("${ios.mgnt.store.modifyStorePromoteStatusUrl}")
    private String modifyStorePromoteStatusUrl = "http://10.18.0.54:11211/store/admin/promote/modifystatus.json";
    @Value("${ios.mgnt.store.modifyStorePromoteRankUrl}")
    private String modifyStorePromoteRankUrl = "http://10.18.0.54:11211/store/admin/promote/modifyrank.json";
    @Value("${ios.mgnt.store.listAllAppStorePromoteTabsUrl}")
    private String listAllAppStorePromoteTabsUrl = "http://10.18.0.54:11211/store/admin/promotetab/tabmap.json";
    @Value("${ios.mgnt.store.listStorePromoteAppsUrl}")
    private String listStorePromoteAppsUrl = "http://10.18.0.54:11211/store/admin/promote/list.json";

    // app
    @Value("${ios.mgnt.store.appsimpleurl}")
    private String appSimpleUrl = "http://10.18.0.54:11211/store/admin/app/simple.json";
    @Value("${ios.mgnt.store.appsimplesurl}")
    private String appSimplesUrl = "http://10.18.0.54:11211/store/admin/app/simples.json";
    @Value("${ios.mgnt.store.listdowntypeinterventionurl}")
    private String listDownTypeInterventionUrl = "http://10.18.0.54:11111/store/admin/app/listdowntypeintervention.json";
    @Value("${ios.mgnt.store.adddowntypeinterventionurl}")
    private String addDownTypeInterventionUrl = "http://10.18.0.54:11111/store/admin/app/adddowntypeintervention.json";
    @Value("${ios.mgnt.store.modifydowntypeinterventionurl}")
    private String modifyDownTypeInterventionUrl = "http://10.18.0.54:11111/store/admin/app/modifydowntypeintervention.json";

    // load page ad
    @Value("${ios.mgnt.store.addloadpageadurl}")
    private String addLoadPageAdUrl = "http://10.18.0.54:11211/store/admin/loadpagead/add.json";
    @Value("${ios.mgnt.store.modifyloadpageadurl}")
    private String modifyLoadPageAdUrl = "http://10.18.0.54:11211/store/admin/loadpagead/modify.json";
    @Value("${ios.mgnt.store.listloadpageadsurl}")
    private String listLoadPageAdsUrl = "http://10.18.0.54:11211/store/admin/loadpagead/list.json";

    // tag map
    @Value("${ios.mgnt.store.fetchAllAppleCategoriesUrl}")
    private String fetchAllAppleCategoriesUrl = "http://10.18.0.54:11211/store/admin/tagmap/applecates.json";
    @Value("${ios.mgnt.store.fetchCustomAppTagsUrl}")
    private String fetchCustomAppTagsUrl = "http://10.18.0.54:11211/store/admin/tagmap/ctags.json";
    @Value("${ios.mgnt.store.listStoreAppTagAppleTagMapsUrl}")
    private String listStoreAppTagAppleTagMapsUrl = "http://10.18.0.54:11211/store/admin/tagmap/list.json";
    @Value("${ios.mgnt.store.addAppStoreAppTagAppleTagMapUrl}")
    private String addAppStoreAppTagAppleTagMapUrl = "http://10.18.0.54:11211/store/admin/tagmap/add.json";
    @Value("${ios.mgnt.store.modifyAppStoreAppTagAppleTagMapStatusUrl}")
    private String modifyAppStoreAppTagAppleTagMapStatusUrl = "http://10.18.0.54:11211/store/admin/tagmap/modify.json";
    @Value("${ios.mgnt.store.fetchAllAppTagsUrl}")
    private String fetchAllAppTagsUrl = "http://10.18.0.54:11211/store/admin/tagmap/tags.json";

    // app tag list intervention
    @Value("${ios.mgnt.store.listAppStoreAppTagListInterventionsUrl}")
    private String listAppStoreAppTagListInterventionsUrl = "http://10.18.0.54:11211/store/admin/apptag/listinv.json";
    @Value("${ios.mgnt.store.addAppStoreAppTagListInterventionUrl}")
    private String addAppStoreAppTagListInterventionUrl = "http://10.18.0.54:11211/store/admin/apptag/addinv.json";
    @Value("${ios.mgnt.store.modifyAppStoreAppTagListInterventionStatusUrl}")
    private String modifyAppStoreAppTagListInterventionStatusUrl = "http://10.18.0.54:11211/store/admin/apptag/modifyinvstatus.json";
    @Value("${ios.mgnt.store.modifyAppStoreAppTagListInterventionRankUrl}")
    private String modifyAppStoreAppTagListInterventionRankUrl = "http://10.18.0.54:11211/store/admin/apptag/modifyinvrank.json";

    // load wallpaper
    @Value("${ios.mgnt.store.listwallpaperurl}")
    private String listWallpapersUrl = "http://10.18.0.54:11211/store/admin/wallpaper/list.json";
    @Value("${ios.mgnt.store.modifywallpaperurl}")
    private String mofigyWallpapersUrl = "http://10.18.0.54:11211/store/admin/wallpaper/modify.json";
    @Value("${ios.mgnt.store.addAppStoreWallpaperUrl}")
    private String addAppStoreWallpaperUrl = "http://10.18.0.54:11211/store/admin/wallpaper/add.json";

    // funny client ad
    @Value("${ios.mgnt.store.addfunnyclientadurl}")
    private String addFunnyClientAdUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/ad/add.json";
    @Value("${ios.mgnt.store.modifyfunnyclientadurl}")
    private String modifyFunnyClientAdUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/ad/modify.json";
    @Value("${ios.mgnt.store.listfunnyclientadsurl}")
    private String listFunnyClientAdsUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/ad/list.json";



    @Override
    public ApiRespWrapper<Boolean> saveAppStoreArticle(AppStoreArticleParameter param) {
        return post(addAppStoreArticleUrl, param, StoreParameterUtils.APPSTORE_ARTICLE_ADD_PARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<Boolean> updateAppStoreArticle(AppStoreArticleParameter param) {
        return post(updateAppStoreArticleUrl, param, StoreParameterUtils.APPSTORE_ARTICLE_ADD_PARAMETER_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
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
    public ApiRespWrapper<ListWrapResp<AppStoreArticle>> listAppStoreArticles(AppStoreArticleParameter para) {
        return get(listAppStoreArticlesUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLE_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<Integer, AppStoreArticleTag>> getAppStoreArticleTags(List<Integer> tagIds) {
        return get(getAppStoreArticleTagMapUrl, tagIds, StoreParameterUtils.APPSTORE_ARTICLE_TAG_IDS_PD_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLE_TAG_MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<Integer, AppStoreArticleTag>> listAllAppStoreArticleTags() {
        return get(getAllAppStoreArticleTagMapUrl, null, null, StoreReturnDataUtils.APPSTORE_ARTICLE_TAG_MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreArticleTag>> listAppStoreArticleTags(StatusStartSizeParameter para) {
        return get(listAppStoreArticleTagUrl, para, ParameterUtils.STATUSSTARTSIZE_PD_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLE_TAG_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreArticleTagStatus(AppStoreArticleTagParameter para) {
        return get(modifyAppStoreArticleTagStatusUrl, para, StoreParameterUtils.APPSTORE_ARTICLE_TAG_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreArticleColor(AppStoreArticleTagParameter para) {
        return get(modifyAppStoreArticleTagColorUrl, para, StoreParameterUtils.APPSTORE_ARTICLE_TAG_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreArticleTag(AppStoreArticleTagParameter para) {
        return get(modifyAppStoreArticleTagUrl, para, StoreParameterUtils.APPSTORE_ARTICLE_TAG_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppStoreArticleTag> saveAppStoreArticleTag(AppStoreArticleTagParameter para) {
        return get(saveAppStoreArticleTagUrl, para, StoreParameterUtils.APPSTORE_ARTICLE_TAG_PD_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLE_TAG_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppStoreArticle> queryAppStoreArticle(int id) {
        return get(getAppStoreArticleByIdUrl, id, StoreParameterUtils.ID_PD_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreArticleRecom>> listAppStoreArticleRecom(
            AppStoreArticleRecomParameter para) {
        return get(listAppStoreArticlesRecomUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLERECOM_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreArticleRecomStatus(AppStoreArticleRecomParameter para) {
        return get(modifyAppStoreArticlesRecomUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreBanner(StoreBannerParameter para) {
        return get(addAppStoreBannerUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyBanner(StoreBannerParameter para) {
        return get(modifyAppStoreBannerUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreBanner>> listEditorAppStoreBanners(StoreBannerParameter para) {
        return get(listEditorAppStoreBannerUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_BANNER_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreBanner>> listUserAppStoreBanners(StoreBannerParameter para) {
        return get(listUserAppStoreBannerUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_BANNER_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreArticle>> getAppStoreArticle(List<Integer> articleIds) {
        return get(getAppStoreArticlesUrl, articleIds, ParameterUtils.IDS_PD_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLES_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<Integer, AppStoreArticleSimple>> getAppStoreArticleSimple(List<Integer> articleIds) {
        return get(getAppStoreArticleSimpleMapUrl, articleIds, ParameterUtils.IDS_PD_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLE_SIMPLE_MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreArticle>> searchAppStoreArticleByTitle(String title, int start, int size)
            throws ServiceException {
        TitleLikeParameter para = new TitleLikeParameter(title, start, size);
        return get(searchAppStoreArticleByTitleUrl, para, ParameterUtils.TITLE_LIKE_PD_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLES_HANDLE);
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

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreClientConf>> getClientConfs(StoreClientConfParameter para)
            throws ServiceException {
        return get(listStoreClientConfUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_CLIENT_CONFS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyClientConf(StoreClientConfParameter para) {
        return get(modifyStoreClientConfUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addClientConf(StoreClientConfParameter para) {
        return get(addStoreClientConfUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<StoreCategory>> getStoreVideoCates(StartSizeParameter para)
            throws ServiceException {
        return get(listStoreVideoCateUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOCATEGORY_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addStoreVideoCate(StoreVideoCateParameter para) throws ServiceException {
        return get(addStoreVideoCateUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<StoreCategory> getStoreVideoCate(int id) throws ServiceException {
        return get(detailStoreVideoCateUrl, id, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOCATEGORY_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyVideoCate(StoreVideoCateParameter para) throws ServiceException {
        return get(modifyStoreVideoCateUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<StoreCategory>> getStoreAppTags(StartSizeParameter para) throws ServiceException {
        return get(listStoreAppTagUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOCATEGORY_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addStoreAppTag(StoreVideoCateParameter para) throws ServiceException {
        return get(addStoreAppTagUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<StoreCategory> getStoreAppTag(int id) throws ServiceException {
        return get(detailStoreAppTagUrl, id, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOCATEGORY_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppTag(StoreVideoCateParameter para) throws ServiceException {
        return get(modifyStoreAppTagUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<CategoryRecom> getAppStoreCateRecom(int id) throws ServiceException {
        return get(detailStoreCateRecomUrl, id, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_CATEGORYRECOM_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<CategoryRecom>> getAppStoreCateRecoms(StoreCateRecomParameter para,
            boolean auditing) throws ServiceException {
        String url = "";
        if (auditing) {
            url = listAppStoreCateRecomForEditorUrl;
        } else {
            url = listAppStoreCateRecomForUserUrl;
        }
        return get(url, para, ParametersHandle.PS_HANDLE, StoreReturnDataUtils.APPSTORE_CATEGORYRECOM_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreCateRecom(StoreCateRecomParameter para) throws ServiceException {
        return get(modifyStoreCateRecomUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreCateRecomSubCate(StoreCateRecomParameter para) throws ServiceException {
        return get(modifySubCateUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> removeAppStoreCateRecomSubCate(StoreCateRecomParameter para) throws ServiceException {
        return get(removeSubCateUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreCateRecom(StoreCateRecomParameter para) throws ServiceException {
        return get(addCateRecomUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<DailyRecom>> listDailyRecomForAuditing(TimedStatusStartSizeParameter para)
            throws ServiceException {
        return get(listDailyRecomAuditorListUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_DAILYRECOM_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<DailyRecom>> listDailyRecomForAudited(TimedStatusStartSizeParameter para)
            throws ServiceException {
        return get(listDailyRecomUserListUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_DAILYRECOM_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreDailyRecomAuditedRank(ModifyStoreBannerParameter para)
            throws ServiceException {
        return get(modifyAuditedRankUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreDailyRecomAuditingRank(ModifyStoreBannerParameter para)
            throws ServiceException {
        return get(modifyAuditingRankUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreDailyRecomEndTime(ModifyStoreBannerParameter para)
            throws ServiceException {
        return get(modifyEndTimeUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreDailyRecomStatus(ModifyStoreBannerParameter para)
            throws ServiceException {
        return get(modifyStatusUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreDailyRecom(DailyRecomParameter para) throws ServiceException {
        return get(modifyDailyRecomUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<VideoInfo> getVideoInfo(int id) throws ServiceException {
        return get(detailAuditedVideoInfoUrl, id, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOINFO_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomClient>> listAppStoreVideoInfoRecomBundleIds(int id)
            throws ServiceException {
        return get(listAppStoreVideoInfoRecomClientUrl, id, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOINFORECOMCLIENTLIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomCate>> listAppStoreVideoInfoRecomCateIds(int id)
            throws ServiceException {
        return get(listAppStoreVideoInfoRecomCateUrl, id, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOINFORECOMCATELIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStorePromoteTab(AppStorePromoteTabParameter para) throws ServiceException {
        return get(addAppStorePromoteTabUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStorePromoteTabName(AppStorePromoteTabModifyParameter para)
            throws ServiceException {
        return get(modifyAppStorePromoteTabNameUrl, para, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStorePromoteTabStatus(AppStorePromoteTabModifyParameter para)
            throws ServiceException {
        return get(modifyAppStorePromoteTabStatusUrl, para, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStorePromoteTabRank(AppStorePromoteTabModifyParameter para)
            throws ServiceException {
        return get(modifyAppStorePromoteTabRankUrl, para, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    public ApiRespWrapper<Boolean> modifyAppStorePromoteTabColor(AppStorePromoteTabModifyParameter para)
            throws ServiceException {
        return get(modifyAppStorePromoteTabColorUrl, para, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    public ApiRespWrapper<Boolean> modifyAppStorePromoteTabIcon(AppStorePromoteTabModifyParameter para)
            throws ServiceException {
        return get(modifyAppStorePromoteTabIconUrl, para, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStorePromoteTab>> listAppStorePromoteTabs(StatusStartSizeParameter para)
            throws ServiceException {
        return get(listAppStorePromoteTabsUrl, para, ParameterUtils.STATUSSTARTSIZE_PD_HANDLE,
                StoreReturnDataUtils.APPSTORE_PROMOTE_TAB_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppStorePromoteTab> queryAppStorePromoteTab(Integer tabId) throws ServiceException {
        return get(queryAppStorePromoteTabUrl, tabId, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_PROMOTEAPP_TAB_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addLoadPageAd(StoreLoadPageAdParameter para) throws ServiceException {
        return get(addLoadPageAdUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyLoadPageAd(StoreLoadPageAdParameter param) throws ServiceException {
        return get(modifyLoadPageAdUrl, param, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreClientLoadPageAd>> listLoadPageAds(StoreLoadPageAdParameter para)
            throws ServiceException {
        return get(listLoadPageAdsUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_LOADPAGEAD_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<String, String>> fetchAllAppleCategories() throws ServiceException {
        return get(fetchAllAppleCategoriesUrl, null, null, ParameterUtils.MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<String, String>> fetchCustomAppTags() throws ServiceException {
        return get(fetchCustomAppTagsUrl, null, null, ParameterUtils.MAP_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<Map<String, String>> fetchAllAppTags() throws ServiceException {
        return get(fetchAllAppTagsUrl, null, null, ParameterUtils.MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppTagListIntervention>> listAppStoreAppTagListInterventions(
            StoreAppTagListInterventionParameter para) throws ServiceException {
        return get(listAppStoreAppTagListInterventionsUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_APPTAG_LIST_INTERVENTION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreAppTagListIntervention(StoreAppTagListInterventionParameter para)
            throws ServiceException {
        return get(addAppStoreAppTagListInterventionUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreAppTagListInterventionStatus(StoreAppTagListInterventionParameter para)
            throws ServiceException {
        return get(modifyAppStoreAppTagListInterventionStatusUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreAppTagListInterventionRank(StoreAppTagListInterventionParameter para)
            throws ServiceException {
        return get(modifyAppStoreAppTagListInterventionRankUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreArticleIntervention(AppStoreArticleInterventionParameter para)
            throws ServiceException {
        return get(addAppStoreArticleInterventionUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreArticleStatus(AppStoreArticle article) {
        return get(modifyAppStoreArticleStatusUrl, article, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreArticleIntervention(AppStoreArticleInterventionParameter para)
            throws ServiceException {
        return get(modifyAppStoreArticleInterventionUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreArticleRecomIntervention>> listAppStoreArticleIntervention(
            AppStoreArticleInterventionParameter para) throws ServiceException {
        return get(listAppStoreArticleInterventionUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_ARTICLE_INTERVENTION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreAppTagAppleTagMap>> listStoreAppTagAppleTagMaps(
            StoreAppTagAppleTagMapParameter para) throws ServiceException {
        return get(listStoreAppTagAppleTagMapsUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_APPTAG_APPLETAG_MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreAppTagAppleTagMap(StoreAppTagAppleTagMapParameter para)
            throws ServiceException {
        return get(addAppStoreAppTagAppleTagMapUrl, para, ParametersHandle.PS_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreAppTagAppleTagMapStatus(StoreAppTagAppleTagMapParameter para)
            throws ServiceException {
        return get(modifyAppStoreAppTagAppleTagMapStatusUrl, para, ParametersHandle.PS_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStorePromoteApplication(StorePromoteAppsAddParameter para)
            throws ServiceException {
        return get(addAppStorePromoteApplicationUrl, para, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyStorePromoteStatus(PromoteAppsParameter para) throws ServiceException {
        return get(modifyStorePromoteStatusUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyStorePromoteRank(PromoteAppsParameter para) throws ServiceException {
        return get(modifyStorePromoteRankUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<Integer, AppStorePromoteTab>> listAllAppStorePromoteTabs() throws ServiceException {
        return get(listAllAppStorePromoteTabsUrl, null, null, StoreReturnDataUtils.APPSTORE_PROMOTETAB_MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStorePromoteApplication>> listStorePromoteApps(StorePromoteAppsParameter para)
            throws ServiceException {
        return get(listStorePromoteAppsUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_PROMOTEAPPS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<List<DailyRecom>> addDailyRecom(DailyRecomParameter para) throws ServiceException {
        return get(addDailyRecomUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORELIST_DAILYRECOM_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreClientAuditInfo>> listAppStoreClientAuditInfo(
            AppStoreClientAuditInfoParamter para) throws ServiceException {
        return get(listAuditInfoUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_CLIENTAUDITINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppStoreClientAuditInfo> getAppStoreClientAuditInfo(int id) throws ServiceException {
        return get(auditInfoDeatilUrl, id, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_CLIENTAUDITINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreClientAuditInfo(AppStoreClientAuditInfoParamter para)
            throws ServiceException {
        return get(modifyAuditInfoUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreVideoMetaStatus(VideoMetaParameter para) throws ServiceException {
        return get(modifyVideoMetaStatusUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<VideoMetaData>> listAppStoreVideoMetaData(StartSizeParameter para)
            throws ServiceException {
        return get(listVideoForAuditingUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOMETADATA_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<VideoInfoWrapper>> listAppStoreVideoInfoWrapper(VideoInfoModifyParameter para)
            throws ServiceException {
        return get(listVideoInfoUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOINFOWRAPPER_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreVideoInfo(VideoInfoModifyParameter para) throws ServiceException {
        return get(modifyVideoInfoUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreVideoRecomCateId(VideoInfoModifyParameter para)
            throws ServiceException {
        return get(modifyAppStoreVideoInfoRecomCateUrl, para, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreVideoRecomBundleId(VideoInfoModifyParameter para)
            throws ServiceException {
        return get(modifyAppStoreVideoInfoRecomClientUrl, para, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<VideoMetaData> getAppStorVideo(int id) throws ServiceException {
        return get(videoMetaUrl, id, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOMETADATA_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<String> getAppStoreHeadVideo() throws ServiceException {
        return get(headVideoDetailUrl, null, null, ReturnDataHandle.STRING_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> auditVideoInfo(AppStoreVideoAuditParameter para) throws ServiceException {
        return get(auditVideoInfoUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ApplicationSimple> getApplicationByRootId(int id) throws ServiceException {
        return get(appSimpleUrl, "?rootId=" + id, null, StoreReturnDataUtils.APPSTORE_APPSIMPLE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Map<Integer, ApplicationSimple>> getAppSimple(List<Integer> rootIds) throws ServiceException {
        if (CollectionUtils.emptyOrNull(rootIds)) {
            return new ApiRespWrapper<Map<Integer, ApplicationSimple>>(null);
        }
        return get(appSimplesUrl, "?rootIds=" + CollectionUtils.listToString(rootIds, ","), null,
                StoreReturnDataUtils.APPSTORE_APPSIMPLE_MAP_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<VideoMetaData> getAppStoreVideoMetaData(String videoUrl) throws ServiceException {
        return get(videoMetaUrl, "?videoUrl=" + videoUrl, null, StoreReturnDataUtils.APPSTORE_VIDEOMETADATA_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<VideoInfoWrapper> getVideoInfoWrapper(int id) throws ServiceException {
        return get(videoInfoWrapperUrl, id, ParametersHandle.PS_ID_HANDLE,
                StoreReturnDataUtils.APPSTORE_VIDEOINFOWRAPPER_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<DownTypeIntervention>> listDownTypeIntervention(
            DownTypeInterventionParameter para) {
        return get(listDownTypeInterventionUrl, para, ParameterUtils.DOWNTYPEINTERVENTION_PD_HANDLE,
                ParameterUtils.DOWNTYPEINTERVENTION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addDownTypeIntervention(DownTypeInterventionParameter para) {
        return get(addDownTypeInterventionUrl, para, ParameterUtils.DOWNTYPEINTERVENTION_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyDownTypeIntervention(DownTypeInterventionParameter para) {
        return get(modifyDownTypeInterventionUrl, para, ParameterUtils.DOWNTYPEINTERVENTION_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreClientInfo>> listAppStoreClientInfo(StartSizeParameter para)
            throws ServiceException {
        return get(listClientInfoUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_CLIENTINFO_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreClientInfo(AppStoreClientInfoParamter para) throws ServiceException {
        return get(modifyClientInfoUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<List<String>> listAppStoreClientAllVersions(String bundleId) throws ServiceException {
        return get(listClientVersionUrl + "?bundleId=" + bundleId, null, null, ParameterUtils.STRINGLIST_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<Boolean> addAppStoreClientDynamicCode(StoreClientDynamicCodeParameter para)
            throws ServiceException {
        return get(addClientDynamicCodeUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreClientDynamicCode>> listAppStoreClientDynamicCode(
            StoreClientDynamicCodeParameter para) throws ServiceException {
        return get(listClientDynamicCodeUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_CLIENTDYNAMICCODE_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreClientDynamicCode(StoreClientDynamicCodeParameter para)
            throws ServiceException {
        return get(modifyClientDynamicCodeUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<AppStoreClientDynamicCode> detailAppStoreClientDynamicCode(
            StoreClientDynamicCodeParameter para) throws ServiceException {
        return get(detailClientDynamicCodeUrl, para, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTORE_CLIENTDYNAMICCODE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreH5Game>> listAppStoreH5Game(AppStoreH5GameParameter para)
            throws ServiceException {
        return get(listH5GameUrl, para, ParametersHandle.PS_HANDLE, StoreReturnDataUtils.APPSTOREH5GAMELIST_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<Boolean> addAppStoreH5Game(AppStoreH5GameParameter para) throws ServiceException {
        return get(addH5GameUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }


    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreH5Game(AppStoreH5GameParameter para) throws ServiceException {
        return get(modifyH5GameUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreUserCenterConfigure>> listAppStoreUserCenterConfigure(
            BundleIdTimedStatusStartSizeParameter g) {
        return get(listAppStoreUserCenterConfigureUrl, g, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTOREUSERCENTERCONFIGURE_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreUserCenterConfigure(AppStoreUserCenterConfigureParameter g) {
        return get(addAppStoreUserCenterConfigureUrl, g, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreUserCenterConfigure(AppStoreUserCenterConfigureParameter g) {
        return get(modifyAppStoreUserCenterConfigureUrl, g, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreUserCenterTipInfo>> listAppStoreUserCenterTipInfo(
            BundleIdTimedStatusStartSizeParameter g) {
        return get(listAppStoreUserCenterTipInfoUrl, g, ParametersHandle.PS_HANDLE,
                StoreReturnDataUtils.APPSTOREUSERCENTERTIPINFO_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppStoreUserCenterTipInfo(AppStoreUserCenterTipInfoParameter g) {
        return get(addAppStoreUserCenterTipInfoUrl, g, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreUserCenterTipInfo(AppStoreUserCenterTipInfoParameter g) {
        return get(modifyAppStoreUserCenterTipInfoUrl, g, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addLoadPageWallpaper(StoreLoadPageWallpaperParameter g) {
        return get(addAppStoreWallpaperUrl, g, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreWallpaper>> listAppStoreWallpaper(AppStoreWallpaperParameter g) {
        return get(listWallpapersUrl, g, ParametersHandle.PS_HANDLE, StoreReturnDataUtils.APPSTORE_WALLPAPER_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppStoreWallpaper(AppStoreWallpaperParameter paper) {
        return get(mofigyWallpapersUrl, paper, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addFunnyClientAd(FunnyClientAdParameter para) throws ServiceException {
        return get(addFunnyClientAdUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyFunnyClientAd(FunnyClientAdParameter para) throws ServiceException {
        return get(modifyFunnyClientAdUrl, para, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<FunnyClientAdInfo>> listFunnyClientAd(FunnyClientAdParameter para)
            throws ServiceException {
        return get(listFunnyClientAdsUrl, para, ParametersHandle.PS_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_AD_LIST_RD_HANDLE);
    }
}
