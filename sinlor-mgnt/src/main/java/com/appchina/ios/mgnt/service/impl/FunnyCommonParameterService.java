// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnAuthor;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnType;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnAuthorParameter;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnTypeParameter;
import com.appchina.ios.mgnt.service.FunnyClientApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;

/**
 * 
 * @author liuxinglong
 * @date 2016年9月5日
 */
@Service("funnyCommonParameterService")
public class FunnyCommonParameterService {
    @Autowired
    private FunnyClientApiService funnyClientApiService;

    public LinkedHashMap<String, String> addColumnTypes(String key, Model model, Boolean... notAll) {
        FunnyClientSpecialColumnTypeParameter g = new FunnyClientSpecialColumnTypeParameter();
        g.setStatus(FunnyClientSpecialColumnType.STATUS_OK);
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>> type = funnyClientApiService
                .listFunnySpecialColumnTypes(g);
        if (type != null && type.getData() != null && !CollectionUtils.emptyOrNull(type.getData().getResultList())) {
            LinkedHashMap<String, String> columnTypes = new LinkedHashMap<String, String>();
            if (CollectionUtils.emptyOrNull(notAll) || notAll[0] == null || !notAll[0].booleanValue()) {
                columnTypes.put("0", "全部类型");
            }
            for (FunnyClientSpecialColumnType columnType : type.getData().getResultList()) {
                columnTypes.put(columnType.getId() + "", columnType.getName());
            }
            model.addAttribute(key, columnTypes);
            return columnTypes;
        }
        return null;
    }

    public LinkedHashMap<String, String> addColumnAuthors(String key, String iconkey, Model model, Boolean... notAll) {
        FunnyClientSpecialColumnAuthorParameter g = new FunnyClientSpecialColumnAuthorParameter();
        g.setStatus(FunnyClientSpecialColumnAuthor.STATUS_OK);
        g.setSize(100);
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnAuthor>> authors = funnyClientApiService
                .listFunnySpecialColumnAuthors(g);
        if (authors != null && authors.getData() != null
                && !CollectionUtils.emptyOrNull(authors.getData().getResultList())) {
            LinkedHashMap<String, String> columnAuthors = new LinkedHashMap<String, String>();
            LinkedHashMap<String, String> columnAuthorIcons = new LinkedHashMap<String, String>();
            if (CollectionUtils.emptyOrNull(notAll) || notAll[0] == null || !notAll[0].booleanValue()) {
                columnAuthors.put("0", "---请选择---");
                // 手动设置的默认头像
                columnAuthorIcons.put("0", "http://iosimg.yingyonghui.com/icon/664/810/810664_icon72x72.jpeg");
            }
            for (FunnyClientSpecialColumnAuthor columnAuthor : authors.getData().getResultList()) {
                columnAuthors.put(columnAuthor.getId() + "", columnAuthor.getName());
                columnAuthorIcons.put(columnAuthor.getId() + "", columnAuthor.getIcon());
            }
            model.addAttribute(key, columnAuthors);
            model.addAttribute(iconkey, columnAuthorIcons);
            return columnAuthors;
        }
        return null;
    }

    public void addStatus(String key, Model model) {
        model.addAttribute(key, DbStatus.STATUS);
    }
}
