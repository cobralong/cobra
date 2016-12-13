package com.appchina.ios.mgnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appchina.ios.core.dto.app.AppDownloadCodeInfo;
import com.appchina.ios.core.dto.app.AppDownloadInfoType;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.mgnt.controller.model.AppInstallReportInfoParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping("/auth/downloadCode/info/*")
public class AppDownloadCodeInfoController {

    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/list")
    public String list(AppInstallReportInfoParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppDownloadCodeInfo>> respDownload = backendApiService
                .listAppDownloadCodeInfo(para);
        AppDownloadCodeInfo values = (respDownload != null && respDownload.getData() != null) ? respDownload.getData()
                .getResultList().get(0) : null;
        model.addAttribute("value", values);
        model.addAttribute("downloadType", EnumMapUtils.enumToMap(AppDownloadInfoType.values()));
        return "/app/downloadcode/detail/info/list.ftl";
    }
}
