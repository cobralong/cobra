package com.appchina.ios.mgnt.utils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.cahe.model.info.AppStoreArticleSimple;
import com.appchina.ios.core.cahe.model.info.CateListWrapper;
import com.appchina.ios.core.cahe.model.info.VideoInfoWrapper;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
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
import com.appchina.ios.core.dto.info.StoreCategory;
import com.appchina.ios.core.dto.info.VideoInfo;
import com.appchina.ios.core.dto.info.VideoMetaData;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

/**
 * Created by zhouyanhui on 7/21/15.
 */
public class StoreReturnDataUtils {
    public static final ReturnDataHandle<ApiRespWrapper<AppStoreArticle>> APPSTORE_ARTICLE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppStoreArticle>>() {
        @Override
        public ApiRespWrapper<AppStoreArticle> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppStoreArticle>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreArticleRecom>>> APPSTORE_ARTICLERECOM_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreArticleRecom>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreArticleRecom>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreArticleRecom>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreArticle>>> APPSTORE_ARTICLE_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreArticle>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreArticle>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreArticle>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };


    public static final ReturnDataHandle<ApiRespWrapper<Map<Integer, AppStoreArticleTag>>> APPSTORE_ARTICLE_TAG_MAP_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<Integer, AppStoreArticleTag>>>() {
        @Override
        public ApiRespWrapper<Map<Integer, AppStoreArticleTag>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<Integer, AppStoreArticleTag>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreArticleTag>>> APPSTORE_ARTICLE_TAG_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreArticleTag>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreArticleTag>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreArticleTag>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<ApiRespWrapper<AppStoreArticleTag>> APPSTORE_ARTICLE_TAG_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppStoreArticleTag>>() {
        @Override
        public ApiRespWrapper<AppStoreArticleTag> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppStoreArticleTag>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };


    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientConf>>> APPSTORE_CLIENT_CONFS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientConf>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreClientConf>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreClientConf>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<StoreCategory>>> APPSTORE_VIDEOCATEGORY_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<StoreCategory>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<StoreCategory>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<StoreCategory>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<ApiRespWrapper<StoreCategory>> APPSTORE_VIDEOCATEGORY_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<StoreCategory>>() {
        @Override
        public ApiRespWrapper<StoreCategory> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<StoreCategory>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<CategoryRecom>>> APPSTORE_CATEGORYRECOM_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<CategoryRecom>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<CategoryRecom>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<CategoryRecom>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<CateListWrapper>>> APPSTORE_CATEGORYRECOMWRAPPER_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<CateListWrapper>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<CateListWrapper>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<CateListWrapper>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<DailyRecom>>> APPSTORE_DAILYRECOM_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<DailyRecom>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<DailyRecom>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<DailyRecom>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<DailyRecom>> APPSTORE_DAILYRECOM_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<DailyRecom>>() {
        @Override
        public ApiRespWrapper<DailyRecom> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<DailyRecom>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<List<DailyRecom>>> APPSTORELIST_DAILYRECOM_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<List<DailyRecom>>>() {
        @Override
        public ApiRespWrapper<List<DailyRecom>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<List<DailyRecom>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ApplicationSimple>> APPSTORE_APPSIMPLE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ApplicationSimple>>() {
        @Override
        public ApiRespWrapper<ApplicationSimple> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ApplicationSimple>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Map<Integer, ApplicationSimple>>> APPSTORE_APPSIMPLE_MAP_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<Integer, ApplicationSimple>>>() {
        @Override
        public ApiRespWrapper<Map<Integer, ApplicationSimple>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<Integer, ApplicationSimple>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<VideoInfo>> APPSTORE_VIDEOINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<VideoInfo>>() {
        @Override
        public ApiRespWrapper<VideoInfo> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<VideoInfo>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<VideoInfoWrapper>> APPSTORE_VIDEOINFOWRAPPER_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<VideoInfoWrapper>>() {
        @Override
        public ApiRespWrapper<VideoInfoWrapper> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<VideoInfoWrapper>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<VideoInfoWrapper>>> APPSTORE_VIDEOINFOWRAPPER_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<VideoInfoWrapper>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<VideoInfoWrapper>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<VideoInfoWrapper>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomClient>>> APPSTORE_VIDEOINFORECOMCLIENTLIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomClient>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomClient>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomClient>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomCate>>> APPSTORE_VIDEOINFORECOMCATELIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomCate>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomCate>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomCate>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<CategoryRecom>> APPSTORE_CATEGORYRECOM_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<CategoryRecom>>() {
        @Override
        public ApiRespWrapper<CategoryRecom> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<CategoryRecom>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };


    public static final ReturnDataHandle<ApiRespWrapper<AppStoreBanner>> STORE_BANNER_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppStoreBanner>>() {
        @Override
        public ApiRespWrapper<AppStoreBanner> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppStoreBanner>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreBanner>>> APPSTORE_BANNER_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreBanner>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreBanner>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreBanner>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    public static final ReturnDataHandle<ApiRespWrapper<Map<Integer, AppStoreArticleSimple>>> APPSTORE_ARTICLE_SIMPLE_MAP_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<Integer, AppStoreArticleSimple>>>() {
        @Override
        public ApiRespWrapper<Map<Integer, AppStoreArticleSimple>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<Integer, AppStoreArticleSimple>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStorePromoteTab>>> APPSTORE_PROMOTE_TAB_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStorePromoteTab>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStorePromoteTab>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStorePromoteTab>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<Map<Integer, AppStorePromoteTab>>> APPSTORE_PROMOTETAB_MAP_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<Map<Integer, AppStorePromoteTab>>>() {
        @Override
        public ApiRespWrapper<Map<Integer, AppStorePromoteTab>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<Map<Integer, AppStorePromoteTab>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStorePromoteApplication>>> APPSTORE_PROMOTEAPPS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStorePromoteApplication>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStorePromoteApplication>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStorePromoteApplication>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<VideoMetaData>>> APPSTORE_VIDEOMETADATA_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<VideoMetaData>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<VideoMetaData>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<VideoMetaData>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<VideoMetaData>> APPSTORE_VIDEOMETADATA_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<VideoMetaData>>() {
        @Override
        public ApiRespWrapper<VideoMetaData> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<VideoMetaData>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientAuditInfo>>> APPSTORE_CLIENTAUDITINFO_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientAuditInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreClientAuditInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreClientAuditInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientInfo>>> APPSTORE_CLIENTINFO_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreClientInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreClientInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientDynamicCode>>> APPSTORE_CLIENTDYNAMICCODE_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientDynamicCode>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreClientDynamicCode>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreClientDynamicCode>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreH5Game>>> APPSTOREH5GAMELIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreH5Game>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreH5Game>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreH5Game>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreUserCenterConfigure>>> APPSTOREUSERCENTERCONFIGURE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreUserCenterConfigure>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreUserCenterConfigure>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreUserCenterConfigure>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreUserCenterTipInfo>>> APPSTOREUSERCENTERTIPINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreUserCenterTipInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreUserCenterTipInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreUserCenterTipInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<AppStoreClientDynamicCode>> APPSTORE_CLIENTDYNAMICCODE_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppStoreClientDynamicCode>>() {
        @Override
        public ApiRespWrapper<AppStoreClientDynamicCode> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppStoreClientDynamicCode>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<AppStoreClientAuditInfo>> APPSTORE_CLIENTAUDITINFO_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppStoreClientAuditInfo>>() {
        @Override
        public ApiRespWrapper<AppStoreClientAuditInfo> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppStoreClientAuditInfo>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<AppStorePromoteTab>> APPSTORE_PROMOTEAPP_TAB_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppStorePromoteTab>>() {
        @Override
        public ApiRespWrapper<AppStorePromoteTab> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppStorePromoteTab>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<AppStoreClientLoadPageAd>> APPSTORE_LOADPAGEAD_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppStoreClientLoadPageAd>>() {
        @Override
        public ApiRespWrapper<AppStoreClientLoadPageAd> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppStoreClientLoadPageAd>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientLoadPageAd>>> APPSTORE_LOADPAGEAD_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreClientLoadPageAd>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreClientLoadPageAd>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreClientLoadPageAd>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };


    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreAppTagAppleTagMap>>> APPSTORE_APPTAG_APPLETAG_MAP_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreAppTagAppleTagMap>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreAppTagAppleTagMap>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreAppTagAppleTagMap>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppTagListIntervention>>> APPSTORE_APPTAG_LIST_INTERVENTION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppTagListIntervention>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppTagListIntervention>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppTagListIntervention>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppStoreArticleSimple>>> APPSTORE_ARTICLE_SIMPLES_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreArticleSimple>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreArticleSimple>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreArticleSimple>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppStoreArticle>>> APPSTORE_ARTICLES_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreArticle>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreArticle>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreArticle>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppStoreArticleRecomIntervention>>> APPSTORE_ARTICLE_INTERVENTION_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreArticleRecomIntervention>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreArticleRecomIntervention>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreArticleRecomIntervention>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
    
    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<AppStoreWallpaper>>> APPSTORE_WALLPAPER_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<AppStoreWallpaper>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<AppStoreWallpaper>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<AppStoreWallpaper>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
}
