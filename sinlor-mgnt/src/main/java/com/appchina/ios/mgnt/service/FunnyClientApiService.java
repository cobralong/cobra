package com.appchina.ios.mgnt.service;

import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetail;
import com.appchina.ios.core.dto.info.FunnyClientKeywordSearch;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumn;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnAuthor;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnEdit;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnType;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientKeywordSearchParameter;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnAuthorParameter;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnParameter;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnTypeParameter;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * 
 * @author liuxinglong
 * @date 2016年8月31日
 */
public interface FunnyClientApiService {

    // funny special column
    ApiRespWrapper<Boolean> addFunnySpecialColumn(FunnyClientSpecialColumnParameter column) throws ServiceException;

    ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>> listFunnySpecialColumns(
            FunnyClientSpecialColumnParameter column) throws ServiceException;

    ApiRespWrapper<Boolean> modifyFunnySpecialColumn(FunnyClientSpecialColumnParameter column) throws ServiceException;

    ApiRespWrapper<FunnyClientSpecialColumn> getFunnySpecialColumnList(Integer id) throws ServiceException;

    ApiRespWrapper<FunnyClientSpecialColumn> getFunnySpecialColumnListByDetailId(Integer did) throws ServiceException;

    // funny special column edit
    ApiRespWrapper<Boolean> addFunnySpecialColumnEdit(FunnyClientSpecialColumnParameter column) throws ServiceException;

    ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnEdit>> listFunnySpecialColumnEdits(
            FunnyClientSpecialColumnParameter column) throws ServiceException;

    ApiRespWrapper<Boolean> modifyFunnySpecialColumnEdit(FunnyClientSpecialColumnParameter column)
            throws ServiceException;

    ApiRespWrapper<FunnyClientSpecialColumnEdit> getFunnySpecialColumnEditList(Integer id) throws ServiceException;

    ApiRespWrapper<FunnyClientSpecialColumnEdit> getFunnySpecialColumnEditListByDetailId(Integer id)
            throws ServiceException;

    ApiRespWrapper<FunnyClientSpecialColumnEdit> getFunnySpecialColumnEditListByOriDetailId(Integer id)
            throws ServiceException;

    ApiRespWrapper<FunnyClientSpecialColumnEdit> getFunnySpecialColumnEditListByOriAppsetId(Integer id)
            throws ServiceException;

    // funny special column type
    ApiRespWrapper<Boolean> addFunnySpecialColumnType(FunnyClientSpecialColumnTypeParameter type)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>> listFunnySpecialColumnTypes(
            FunnyClientSpecialColumnTypeParameter type) throws ServiceException;

    ApiRespWrapper<Boolean> modifyFunnySpecialColumnType(FunnyClientSpecialColumnTypeParameter type)
            throws ServiceException;

    // funny special column author
    ApiRespWrapper<Boolean> addFunnySpecialColumnAuthor(FunnyClientSpecialColumnAuthorParameter auth)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnAuthor>> listFunnySpecialColumnAuthors(
            FunnyClientSpecialColumnAuthorParameter auth) throws ServiceException;

    ApiRespWrapper<Boolean> modifyFunnySpecialColumnAuthor(FunnyClientSpecialColumnAuthorParameter auth)
            throws ServiceException;

    ApiRespWrapper<FunnyClientSpecialColumnAuthor> getFunnySpecialColumnAuthor(Integer id) throws ServiceException;

    // funny special column detail get
    ApiRespWrapper<AppStoreFunnyColumnDetail> getAppStoreFunnyColumnDetailById(Integer id) throws ServiceException;

    ApiRespWrapper<AppStoreFunnyColumnDetail> getAppStoreFunnyColumnDetailByAndroidDid(Integer id)
            throws ServiceException;

    ApiRespWrapper<AppStoreFunnyColumnDetail> getAppStoreFunnyColumnDetailByAndroidAppsetId(Integer id)
            throws ServiceException;

    ApiRespWrapper<ListWrapResp<Integer>> getFunnySpecialColumnListColumnContentIds() throws ServiceException;

    // funny client keyword search

    ApiRespWrapper<ListWrapResp<FunnyClientKeywordSearch>> listFunnyClientKeywordSearchs(
            FunnyClientKeywordSearchParameter keyword) throws ServiceException;

    ApiRespWrapper<Boolean> addFunnyClientKeywordSearch(FunnyClientKeywordSearchParameter keyword)
            throws ServiceException;

    ApiRespWrapper<Boolean> modifyFunnyClientKeywordSearch(FunnyClientKeywordSearchParameter keyword)
            throws ServiceException;

}
