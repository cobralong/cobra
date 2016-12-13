package com.appchina.ios.mgnt.utils;

import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetail;
import com.appchina.ios.core.dto.info.FunnyClientAdInfo;
import com.appchina.ios.core.dto.info.FunnyClientKeywordSearch;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumn;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnAuthor;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnEdit;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnType;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author liuxinglong
 * @date 2016年8月31日
 */
public class FunnyReturnDataUtils {
    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>>> FUNNY_CLIENT_COLUMN_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<FunnyClientAdInfo>>> FUNNY_CLIENT_AD_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<FunnyClientAdInfo>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<FunnyClientAdInfo>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<FunnyClientAdInfo>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>>> FUNNY_CLIENT_COLUMN_TYPES_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnAuthor>>> FUNNY_CLIENT_COLUMN_AUTHORS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnAuthor>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnAuthor>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnAuthor>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<FunnyClientSpecialColumnAuthor>> FUNNY_CLIENT_COLUMN_AUTHOR_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<FunnyClientSpecialColumnAuthor>>() {
        @Override
        public ApiRespWrapper<FunnyClientSpecialColumnAuthor> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<FunnyClientSpecialColumnAuthor>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<FunnyClientSpecialColumn>> FUNNY_CLIENT_COLUMN_LIST_DETAIL_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<FunnyClientSpecialColumn>>() {
        @Override
        public ApiRespWrapper<FunnyClientSpecialColumn> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<FunnyClientSpecialColumn>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnEdit>>> FUNNY_CLIENT_COLUMN_LIST_EDIT_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnEdit>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnEdit>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnEdit>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<FunnyClientSpecialColumnEdit>> FUNNY_CLIENT_COLUMN_LIST_EDIT_DETAIL_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<FunnyClientSpecialColumnEdit>>() {
        @Override
        public ApiRespWrapper<FunnyClientSpecialColumnEdit> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<FunnyClientSpecialColumnEdit>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<AppStoreFunnyColumnDetail>> FUNNY_CLIENT_COLUMN_DETAIL_BYDID_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<AppStoreFunnyColumnDetail>>() {
        @Override
        public ApiRespWrapper<AppStoreFunnyColumnDetail> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<AppStoreFunnyColumnDetail>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<ApiRespWrapper<ListWrapResp<Integer>>> FUNNY_CLIENT_COLUMN_CONTENTIDS_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<Integer>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<Integer>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<Integer>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<ListWrapResp<FunnyClientKeywordSearch>>> FUNNY_CLIENT_KEYWORDSEARCH_LIST_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<ListWrapResp<FunnyClientKeywordSearch>>>() {
        @Override
        public ApiRespWrapper<ListWrapResp<FunnyClientKeywordSearch>> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<ListWrapResp<FunnyClientKeywordSearch>>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };

    public static final ReturnDataHandle<? extends ApiRespWrapper<FunnyClientKeywordSearch>> FUNNY_CLIENT_KEYWORDSEARCH_RD_HANDLE = new ReturnDataHandle<ApiRespWrapper<FunnyClientKeywordSearch>>() {
        @Override
        public ApiRespWrapper<FunnyClientKeywordSearch> handle(String value) throws Exception {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            Type type = new TypeToken<ApiRespWrapper<FunnyClientKeywordSearch>>() {}.getType();
            return GsonUtils.convert(value, type);
        }
    };
}
