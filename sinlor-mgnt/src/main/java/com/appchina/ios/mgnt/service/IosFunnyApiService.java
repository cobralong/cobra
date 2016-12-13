package com.appchina.ios.mgnt.service;

import com.appchina.ios.core.dto.app.RootApplication;
import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetail;
import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetailEdit;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumn;
import com.appchina.ios.mgnt.controller.model.info.AppStoreFunnyColumnDetailParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreRootApplicationPara;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

public interface IosFunnyApiService {

    ApiRespWrapper<Boolean> addFunnyColumnDetail(AppStoreFunnyColumnDetailParameter para);

    ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetail>> listAppStoreFunnyColumnDetail(
            AppStoreFunnyColumnDetailParameter para);

    ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetailEdit>> listAppStoreFunnyColumnDetailEdit(
            AppStoreFunnyColumnDetailParameter para);

    ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>> listFunnyClientSpecialColumnByIds(String referenceColumnId);

    ApiRespWrapper<RootApplication> searchRootApplication(AppStoreRootApplicationPara para);
}
