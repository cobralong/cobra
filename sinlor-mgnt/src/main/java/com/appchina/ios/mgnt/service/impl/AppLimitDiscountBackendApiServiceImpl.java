package com.appchina.ios.mgnt.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.appchina.ios.core.dto.app.AppLimitDiscount;
import com.appchina.ios.core.dto.app.AppLimitDiscountIntervention;
import com.appchina.ios.mgnt.controller.model.AppLimitDiscountListParameter;
import com.appchina.ios.mgnt.service.AppLimitDiscountBackendApiService;
import com.appchina.ios.mgnt.utils.ParameterUtils;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * Created by zhouyanhui on 6/10/15.
 */
@Service("appLimitDiscountBackendApiService")
public class AppLimitDiscountBackendApiServiceImpl implements AppLimitDiscountBackendApiService {
    private static final Logger log = Logger.getLogger(AppLimitDiscountBackendApiServiceImpl.class);

    @Value("${ios.mgnt.api.applimitdiscountinterventionaddurl}")
    private String appLimitDiscountInterventionAddUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/limit/intervention/add.json";
    @Value("${ios.mgnt.api.applimitdiscountinterventionlisturl}")
    private String appLimitDiscountInterventionListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/limit/intervention/list.json";
    @Value("${ios.mgnt.api.applimitdiscountinterventionmodifyurl}")
    private String appLimitDiscountInterventionModifyUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/limit/intervention/modify.json";
    @Value("${ios.mgnt.api.applimitdiscountlisturl}")
    private String appLimitDiscountListUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/limit/free.json";
    @Value("${ios.mgnt.api.applimitdiscounttypemapurl}")
    private String appLimitDiscountTypeMapUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/limit/types.json";
    @Value("${ios.mgnt.api.applimitdiscount_islimitfreeurl}")
    private String appLimitDiscountCheckLimitFreeUrl = "http://127.0.0.1:8080/appchina-ios-backend/admin/limit/check.json";

    @Override
    public ApiRespWrapper<Map<String, String>> mapSortTypeDictionary() {
        return get(this.appLimitDiscountTypeMapUrl, null, null, ParameterUtils.MAP_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> isLimitFree(Integer rootId) {
        return get(this.appLimitDiscountCheckLimitFreeUrl, rootId, ParameterUtils.ROOTID_PD_HANDLE, ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addAppLimitDiscountInterventions(AppLimitDiscountListParameter para) {
        return get(this.appLimitDiscountInterventionAddUrl, para, ParameterUtils.APPLIMITDISCOUNTLIST_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyAppLimitDiscountInterventions(AppLimitDiscountListParameter para) {
        return get(this.appLimitDiscountInterventionModifyUrl, para, ParameterUtils.APPLIMITDISCOUNTLIST_PD_HANDLE,
                ParameterUtils.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppLimitDiscountIntervention>> listAppLimitDiscountInterventions(AppLimitDiscountListParameter para) {
        return get(this.appLimitDiscountInterventionListUrl, para, ParameterUtils.APPLIMITDISCOUNTLIST_PD_HANDLE, ParameterUtils.APPLIMITDISCOUNTINTERVENTION_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<AppLimitDiscount>> listAppLimitDiscount(AppLimitDiscountListParameter para) {
        return get(this.appLimitDiscountListUrl, para, ParameterUtils.APPLIMITDISCOUNTLIST_PD_HANDLE, ParameterUtils.APPLIMITDISCOUNT_RD_HANDLE);
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
}
