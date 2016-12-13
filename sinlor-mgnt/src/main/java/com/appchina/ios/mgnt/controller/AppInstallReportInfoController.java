package com.appchina.ios.mgnt.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.dto.app.AppDownloadCodeInfo;
import com.appchina.ios.core.dto.app.AppDownloadInfoType;
import com.appchina.ios.core.dto.app.AppInstallReportInfo;
import com.appchina.ios.core.dto.app.AppInstallReportInfoHandleType;
import com.appchina.ios.core.dto.app.AppInstallReportInfoStateType;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.mgnt.controller.model.AppInstallReportInfoParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping("/auth/install/report/*")
public class AppInstallReportInfoController {

    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/list")
    public String list(AppInstallReportInfoParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(AppInstallReportInfo.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<AppDownloadCodeInfo>> respDownload = backendApiService
                .listAppDownloadCodeInfo(para);
        if (para.getType() != null && respDownload != null && respDownload.getData() != null) {
            String codes = findAllCode(respDownload.getData().getResultList());
            para.setCode(codes);
        }
        ApiRespWrapper<ListWrapResp<AppInstallReportInfo>> resp = backendApiService.listAppInstallResportInfo(para);;
        Map<String, Integer> downloadTypes = null;
        if (respDownload != null && respDownload.getData() != null) {
            downloadTypes = findDownloadType(respDownload);
        }
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppInstallReportInfo> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("downloadType", EnumMapUtils.enumToMap(AppDownloadInfoType.values()));
        model.addAttribute("downloadTypes", downloadTypes);
        model.addAttribute("resultType", EnumMapUtils.enumToMap(AppInstallReportInfoStateType.values()));
        model.addAttribute("handles", EnumMapUtils.enumToMap(AppInstallReportInfoHandleType.values()));
        model.addAttribute("para", para);
        return "/app/install/report/list.ftl";
    }

    private String findAllCode(List<AppDownloadCodeInfo> resultList) {
        String code = "(";
        for (int i = 0; i < resultList.size(); i++) {
            if (i > 0) {
                code += ",";
            }
            code += "'" + resultList.get(i).getCode() + "'";
        }
        code += ")";
        return code;
    }

    private Map<String, Integer> findDownloadType(ApiRespWrapper<ListWrapResp<AppDownloadCodeInfo>> respDownload) {
        Map<String, Integer> maps = new LinkedHashMap<String, Integer>();
        for (AppDownloadCodeInfo g : respDownload.getData().getResultList()) {
            maps.put(g.getCode(), g.getType());
        }
        return maps;
    }
}
