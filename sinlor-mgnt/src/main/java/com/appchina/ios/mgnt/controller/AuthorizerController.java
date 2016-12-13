package com.appchina.ios.mgnt.controller;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.dto.system.AuthorizerPcMachineInfo;
import com.appchina.ios.core.dto.system.AuthorizerPcServerInfo;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.mgnt.controller.model.AuthorizerPcMachineInfoParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerPcServerInfoParameter;
import com.appchina.ios.mgnt.service.BackendAuthorizerApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.ApiRespWrapperUtils;
import com.appchina.ios.web.utils.UrlUtils;

@Controller
@RequestMapping(value = "/auth/authorizer/*")
public class AuthorizerController {
    @Autowired
    private BackendAuthorizerApiService backendAuthorizerApiService;

    @RequestMapping(value = "/pc/server/info/list")
    public String listAuthorizerPcServerInfo(@ModelAttribute("id") String id, @ModelAttribute("addMsg") String addMsg,
            AuthorizerPcServerInfoParameter para, Model model) {
        if (!StringUtils.isEmpty(id)) {
            try {
                para.setId(Integer.parseInt(id));
            } catch (Exception e) {
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AuthorizerPcServerInfo>> resp = this.backendAuthorizerApiService
                .listAuthorizerPcServceInfo(para);
        ApiRespWrapperUtils.handleListResp(resp, para, model);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        if (!StringUtils.isEmpty(addMsg)) {
            model.addAttribute("addMsg", addMsg);
        }
        return "authorizer/pc/server/info/list.ftl";
    }

    @RequestMapping(value = "/pc/server/info/add", method = RequestMethod.POST)
    public String addAuthorizerPcServerInfo(String url, String kmachineIda, String kmachineIdb, String osGuid,
            String osName, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(url)) {
            errMsg = "服务器地址不能为空";
        }
        if (StringUtils.isEmpty(errMsg) && !UrlUtils.isUrl(url)) {
            errMsg = "服务器地址格式错误";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(kmachineIda)) {
            errMsg = "服务器kmachineIda格式错误";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(kmachineIdb)) {
            errMsg = "服务器kmachineIdb格式错误";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(osGuid)) {
            errMsg = "服务器osGuid格式错误";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(osName)) {
            errMsg = "服务器osName格式错误";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AuthorizerPcServerInfoParameter para = new AuthorizerPcServerInfoParameter();
            para.setKmachineIda(kmachineIda);
            para.setKmachineIdb(kmachineIdb);
            para.setOsGuid(osGuid);
            para.setOsName(osName);
            para.setUrl(url);
            ApiRespWrapper<Boolean> resp = this.backendAuthorizerApiService.addAuthorizerPcServceInfo(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("addMsg", errMsg);
        return "redirect:/auth/authorizer/pc/server/info/list";
    }

    @RequestMapping(value = "/pc/server/info/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAuthorizerPcServerInfo(int id, Integer status) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的授权账号记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AuthorizerPcServerInfoParameter para = new AuthorizerPcServerInfoParameter();
            para.setId(id);
            para.setStatus(status);
            return this.backendAuthorizerApiService.modifyAuthorizerPcServceInfo(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }


    @RequestMapping(value = "/pc/machine/info/list")
    public String listAuthorizerPcMachineInfo(@ModelAttribute("addMsg") String addMsg,
            AuthorizerPcMachineInfoParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AuthorizerPcMachineInfo>> resp = this.backendAuthorizerApiService
                .listAuthorizerPcMachineInfo(para);
        ApiRespWrapperUtils.handleListResp(resp, para, model);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        if (!StringUtils.isEmpty(addMsg)) {
            model.addAttribute("addMsg", addMsg);
        }
        return "authorizer/pc/machine/info/list.ftl";
    }

    @RequestMapping(value = "/pc/machine/info/add", method = RequestMethod.POST)
    public String addAuthorizerPcMachineInfo(String kmachineIda, String kmachineIdb, String osGuid, String osName,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(osName)) {
            errMsg = "机器名称不能为空";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AuthorizerPcMachineInfoParameter para = new AuthorizerPcMachineInfoParameter();
            para.setKmachineIda(kmachineIda);
            para.setKmachineIdb(kmachineIdb);
            para.setOsGuid(osGuid);
            para.setOsName(osName);
            ApiRespWrapper<Boolean> resp = this.backendAuthorizerApiService.addAuthorizerPcMachineInfo(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("addMsg", errMsg);
        return "redirect:/auth/authorizer/pc/machine/info/list";
    }
}
