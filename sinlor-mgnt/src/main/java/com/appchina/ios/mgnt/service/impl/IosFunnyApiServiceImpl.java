package com.appchina.ios.mgnt.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.appchina.ios.core.dto.app.RootApplication;
import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetail;
import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetailEdit;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumn;
import com.appchina.ios.mgnt.controller.model.info.AppStoreFunnyColumnDetailParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreRootApplicationPara;
import com.appchina.ios.mgnt.service.IosFunnyApiService;
import com.appchina.ios.mgnt.utils.ParameterUtils;
import com.appchina.ios.mgnt.utils.StoreParameterUtils;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.model.PostParametersHandle;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.utils.RemoteDataUtil;

@Service("iosFunnyApiService")
public class IosFunnyApiServiceImpl implements IosFunnyApiService {

    @Value("${ios.mgnt.funny.addFunnyColumnDetailUrl}")
    private String addAppStoreFunnyColumnDetailUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/funny/column/detail/add.json";
    @Value("${ios.mgnt.funny.listFunnyColumnDetailUrl}")
    private String listFunnyColumnDetailUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/funny/column/detail/list.json";
    @Value("${ios.mgnt.funny.listFunnyColumnDetailEditUrl}")
    private String listFunnyColumnDetailEditUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/funny/column/detail/listedit.json";
    @Value("${ios.mgnt.funny.listFunnyClientSpecialColumnByIdsUrl}")
    private String listFunnyClientSpecialColumnByIdsUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/listbyids.json";
    @Value("${ios.mgnt.funny.listRootApplicationByItemIdBundleId}")
    private String listRootApplicationByItemIdBundleId = "http://10.18.0.54:22223/ios-funny/funny/admin/funny/column/detail/listrootapplicationbyitemidbundleid.json";

    private static final Logger log = Logger.getLogger(IosFunnyApiServiceImpl.class);

    @Override
    public ApiRespWrapper<Boolean> addFunnyColumnDetail(AppStoreFunnyColumnDetailParameter para) {
        return post(addAppStoreFunnyColumnDetailUrl, para, StoreParameterUtils.APPSTORE_COLUMN_ADDPARAMETER_PD_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetail>> listAppStoreFunnyColumnDetail(
            AppStoreFunnyColumnDetailParameter para) {
        return get(listFunnyColumnDetailUrl, para, ParameterUtils.FUNNY_COLUMN_PD_HANDLE,
                ParameterUtils.FUNNY_COLUMN_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetailEdit>> listAppStoreFunnyColumnDetailEdit(
            AppStoreFunnyColumnDetailParameter para) {
        return get(listFunnyColumnDetailEditUrl, para, ParameterUtils.FUNNY_COLUMN_PD_HANDLE,
                ParameterUtils.FUNNY_COLUMN_EDIT_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>> listFunnyClientSpecialColumnByIds(String ids) {
        return get(listFunnyClientSpecialColumnByIdsUrl, ids,
                ParameterUtils.FUNNY_CLIENT_SPECIAL_COLUMN_HANDLE_PD_HANDLE,
                ParameterUtils.FUNNY_CLIENT_SPECIAL_COLUMN_HANDLE);
    }

    @Override
    public ApiRespWrapper<RootApplication> searchRootApplication(AppStoreRootApplicationPara para) {
        return get(listRootApplicationByItemIdBundleId, para, ParameterUtils.FUNNY_SEARCH_ROOTAPPLICATION_PD_HANDLE,
                ParameterUtils.FUNNY_SEARCH_APPLICATION_HANDLE);
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


}
