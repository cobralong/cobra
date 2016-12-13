package com.appchina.ios.mgnt.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
import com.appchina.ios.mgnt.service.FunnyClientApiService;
import com.appchina.ios.mgnt.utils.FunnyReturnDataUtils;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.ParametersHandle;
import com.appchina.ios.web.model.ReturnDataHandle;
import com.appchina.ios.web.utils.RemoteDataUtil;

/**
 * 
 * @author liuxinglong
 * @date 2016年8月31日
 */
@Service("funnyClientApiService")
public class FunnyClientApiServiceImpl implements FunnyClientApiService {

    private static final Logger log = Logger.getLogger(FunnyClientApiServiceImpl.class);

    // funny
    @Value("${ios.mgnt.funny.listcolumnurl}")
    private String listColumnListsUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/list.json";
    @Value("${ios.mgnt.funny.addcolumnurl}")
    private String addColumnListUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/add.json";
    @Value("${ios.mgnt.funny.modifycolumnurl}")
    private String modifyColumnListUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/modify.json";
    @Value("${ios.mgnt.funny.getcolumnurl}")
    private String getColumnListUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/get.json";
    @Value("${ios.mgnt.funny.getcolumnurlbydid}")
    private String getColumnListUrlByDid = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/getbydid.json";
    // funny edit
    @Value("${ios.mgnt.funny.listcolumnediturl}")
    private String listColumnListEditsUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/edit/list.json";
    @Value("${ios.mgnt.funny.addcolumnediturl}")
    private String addColumnListEditUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/edit/add.json";
    @Value("${ios.mgnt.funny.modifycolumnediturl}")
    private String modifyColumnListEditUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/edit/modify.json";
    @Value("${ios.mgnt.funny.getcolumnediturl}")
    private String getColumnListEditUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/edit/get.json";
    @Value("${ios.mgnt.funny.getcolumnediturlbydid}")
    private String getColumnListEditUrlByDid = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/edit/getbydid.json";
    @Value("${ios.mgnt.funny.getcolumnediturlbyoridid}")
    private String getColumnListEditUrlByOriDid = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/edit/getbydid.json";
    @Value("${ios.mgnt.funny.getcolumnediturlbyoriappsetid}")
    private String getColumnListEditUrlByOriAppsetId = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/edit/getbyappsetid.json";
    // funny type
    @Value("${ios.mgnt.funny.listcolumntypeurl}")
    private String listColumnTypesUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/type/list.json";
    @Value("${ios.mgnt.funny.addcolumntypeurl}")
    private String addColumnTypeUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/type/add.json";
    @Value("${ios.mgnt.funny.modifycolumntypeurl}")
    private String modifyColumnTypeUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/type/modify.json";
    // funny author
    @Value("${ios.mgnt.funny.listcolumnauthorurl}")
    private String listColumnAuthorsUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/author/list.json";
    @Value("${ios.mgnt.funny.addcolumnauthorurl}")
    private String addColumnAuthorUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/author/add.json";
    @Value("${ios.mgnt.funny.modifycolumnauthorurl}")
    private String modifyColumnAuthorUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/author/modify.json";
    @Value("${ios.mgnt.funny.getcolumnauthorurl}")
    private String getColumnAuthorUrl = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/author/get.json";
    // funny detail
    @Value("${ios.mgnt.funny.getcolumndetailurl}")
    private String getColumnDetailUrl = "http://10.18.0.54:22223/ios-funny/funny/column/content/detail/get.json";
    @Value("${ios.mgnt.funny.getcolumndetailbyandroiddidurl}")
    private String getColumnDetailByAndroidDidUrl = "http://10.18.0.54:22223/ios-funny/funny/column/content/detail/androidid/get.json";
    @Value("${ios.mgnt.funny.getcolumndetailbyandroidappsetidurl}")
    private String getColumnDetailByAndroidAppsetIdUrl = "http://10.18.0.54:22223/ios-funny/funny/column/content/detail/androidappsetid/get.json";
    @Value("${ios.mgnt.funny.getfunnyspecialcolumnlistcolumncontentids}")
    private String getFunnySpecialColumnListColumnContentIds = "http://10.18.0.54:22223/ios-funny/funny/admin/special/column/getfunnyspecialcolumnlistcolumncontentids.json";
    // funny client keyword search
    @Value("${ios.mgnt.funny.listfunnyclientkeywordsearchurl}")
    private String listFunnyClientKeywordSearchUrl = "http://10.18.0.54.22223/ios-funny/funny/admin/system/keywordsearch/list.json";
    @Value("${ios.mgnt.funny.addfunnyclientkeywordsearchurl}")
    private String addFunnyClientKeywordSearchUrl = "http://10.18.0.54.22223/ios-funny/funny/admin/system/keywordsearch/add.json";
    @Value("${ios.mgnt.funny.modifyfunnyclientkeywordsearchurl}")
    private String modifyFunnyClientKeywordSearchUrl = "http://10.18.0.54.22223/ios-funny/funny/admin/system/keywordsearch/modify.json";

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
    public ApiRespWrapper<Boolean> addFunnySpecialColumn(FunnyClientSpecialColumnParameter column)
            throws ServiceException {
        return get(addColumnListUrl, column, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>> listFunnySpecialColumns(
            FunnyClientSpecialColumnParameter column) throws ServiceException {
        return get(listColumnListsUrl, column, ParametersHandle.PS_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyFunnySpecialColumn(FunnyClientSpecialColumnParameter column)
            throws ServiceException {
        return get(modifyColumnListUrl, column, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<FunnyClientSpecialColumn> getFunnySpecialColumnList(Integer id) throws ServiceException {
        return get(getColumnListUrl, id, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_LIST_DETAIL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<FunnyClientSpecialColumn> getFunnySpecialColumnListByDetailId(Integer did)
            throws ServiceException {
        return get(getColumnListUrlByDid, did, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_LIST_DETAIL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addFunnySpecialColumnEdit(FunnyClientSpecialColumnParameter column)
            throws ServiceException {
        return get(addColumnListEditUrl, column, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnEdit>> listFunnySpecialColumnEdits(
            FunnyClientSpecialColumnParameter column) throws ServiceException {
        return get(listColumnListEditsUrl, column, ParametersHandle.PS_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_LIST_EDIT_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyFunnySpecialColumnEdit(FunnyClientSpecialColumnParameter column)
            throws ServiceException {
        return get(modifyColumnListEditUrl, column, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<FunnyClientSpecialColumnEdit> getFunnySpecialColumnEditList(Integer id)
            throws ServiceException {
        return get(getColumnListEditUrl, id, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_LIST_EDIT_DETAIL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<FunnyClientSpecialColumnEdit> getFunnySpecialColumnEditListByDetailId(Integer id)
            throws ServiceException {
        return get(getColumnListEditUrlByDid, id, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_LIST_EDIT_DETAIL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<FunnyClientSpecialColumnEdit> getFunnySpecialColumnEditListByOriDetailId(Integer id)
            throws ServiceException {
        return get(getColumnListEditUrlByOriDid, id, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_LIST_EDIT_DETAIL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<FunnyClientSpecialColumnEdit> getFunnySpecialColumnEditListByOriAppsetId(Integer id)
            throws ServiceException {
        return get(getColumnListEditUrlByOriAppsetId, id, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_LIST_EDIT_DETAIL_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addFunnySpecialColumnType(FunnyClientSpecialColumnTypeParameter type)
            throws ServiceException {
        return get(addColumnTypeUrl, type, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>> listFunnySpecialColumnTypes(
            FunnyClientSpecialColumnTypeParameter type) throws ServiceException {
        return get(listColumnTypesUrl, type, ParametersHandle.PS_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_TYPES_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyFunnySpecialColumnType(FunnyClientSpecialColumnTypeParameter type)
            throws ServiceException {
        return get(modifyColumnTypeUrl, type, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addFunnySpecialColumnAuthor(FunnyClientSpecialColumnAuthorParameter auth)
            throws ServiceException {
        return get(addColumnAuthorUrl, auth, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnAuthor>> listFunnySpecialColumnAuthors(
            FunnyClientSpecialColumnAuthorParameter auth) throws ServiceException {
        return get(listColumnAuthorsUrl, auth, ParametersHandle.PS_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_AUTHORS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyFunnySpecialColumnAuthor(FunnyClientSpecialColumnAuthorParameter auth)
            throws ServiceException {
        return get(modifyColumnAuthorUrl, auth, ParametersHandle.PS_HANDLE, ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<FunnyClientSpecialColumnAuthor> getFunnySpecialColumnAuthor(Integer id)
            throws ServiceException {
        return get(getColumnAuthorUrl, id, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_AUTHOR_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppStoreFunnyColumnDetail> getAppStoreFunnyColumnDetailById(Integer id)
            throws ServiceException {
        return get(getColumnDetailUrl, id, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_DETAIL_BYDID_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppStoreFunnyColumnDetail> getAppStoreFunnyColumnDetailByAndroidDid(Integer id)
            throws ServiceException {
        return get(getColumnDetailByAndroidDidUrl, id, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_DETAIL_BYDID_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<AppStoreFunnyColumnDetail> getAppStoreFunnyColumnDetailByAndroidAppsetId(Integer id)
            throws ServiceException {
        return get(getColumnDetailByAndroidAppsetIdUrl, id, ParametersHandle.PS_ID_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_DETAIL_BYDID_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<Integer>> getFunnySpecialColumnListColumnContentIds() throws ServiceException {
        return get(getFunnySpecialColumnListColumnContentIds, null, null,
                FunnyReturnDataUtils.FUNNY_CLIENT_COLUMN_CONTENTIDS_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> addFunnyClientKeywordSearch(FunnyClientKeywordSearchParameter keyword)
            throws ServiceException {
        return get(addFunnyClientKeywordSearchUrl, keyword, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<ListWrapResp<FunnyClientKeywordSearch>> listFunnyClientKeywordSearchs(
            FunnyClientKeywordSearchParameter keyword) throws ServiceException {
        return get(listFunnyClientKeywordSearchUrl, keyword, ParametersHandle.PS_HANDLE,
                FunnyReturnDataUtils.FUNNY_CLIENT_KEYWORDSEARCH_LIST_RD_HANDLE);
    }

    @Override
    public ApiRespWrapper<Boolean> modifyFunnyClientKeywordSearch(FunnyClientKeywordSearchParameter keyword)
            throws ServiceException {
        return get(modifyFunnyClientKeywordSearchUrl, keyword, ParametersHandle.PS_HANDLE,
                ReturnDataHandle.BOOLEAN_RD_HANDLE);
    }
}
