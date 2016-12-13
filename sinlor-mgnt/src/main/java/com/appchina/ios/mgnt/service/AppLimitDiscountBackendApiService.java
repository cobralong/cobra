package com.appchina.ios.mgnt.service;

import java.util.Map;

import com.appchina.ios.core.dto.app.AppLimitDiscount;
import com.appchina.ios.core.dto.app.AppLimitDiscountIntervention;
import com.appchina.ios.mgnt.controller.model.AppLimitDiscountListParameter;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * Created by zhouyanhui on 6/10/15.
 */
public interface AppLimitDiscountBackendApiService {
    ApiRespWrapper<Map<String,String>> mapSortTypeDictionary();

    ApiRespWrapper<Boolean> isLimitFree(Integer rootId);

    ApiRespWrapper<Boolean> addAppLimitDiscountInterventions(AppLimitDiscountListParameter para);

    ApiRespWrapper<Boolean> modifyAppLimitDiscountInterventions(AppLimitDiscountListParameter para);

    ApiRespWrapper<ListWrapResp<AppLimitDiscountIntervention>> listAppLimitDiscountInterventions(AppLimitDiscountListParameter para);

    ApiRespWrapper<ListWrapResp<AppLimitDiscount>> listAppLimitDiscount(AppLimitDiscountListParameter para);
}
