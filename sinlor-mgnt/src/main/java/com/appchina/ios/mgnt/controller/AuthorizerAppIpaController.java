package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.DownloadPureIpaFlag;
import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.TaskStatus;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccount;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountBuyAppInfo;
import com.appchina.ios.core.dto.app.AuthorizerAppDownloadFeedback;
import com.appchina.ios.core.dto.app.AuthorizerAppDownloadFeedbackStatus;
import com.appchina.ios.core.dto.app.AuthorizerAppDownloadTask;
import com.appchina.ios.core.dto.app.AuthorizerAppIpa;
import com.appchina.ios.core.dto.app.RootApplication;
import com.appchina.ios.core.dto.system.AuthorizerAppDownloadServerInfo;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountBuyAppInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppDownloadFeedbackParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppDownloadServerInfoParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppDownloadTaskParameter;
import com.appchina.ios.mgnt.controller.model.AuthorizerAppIpaParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BackendAppleAccountApiService;
import com.appchina.ios.mgnt.service.BackendAuthorizerApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.ApiRespWrapperUtils;
import com.appchina.ios.web.utils.CollectionUtils;
import com.appchina.ios.web.utils.UrlUtils;

/**
 * 授权ipa
 * 
 * @author luofei@appchina.com create date: 2016年3月29日
 *
 */
@Controller
@RequestMapping(value = "/auth/authorizer/app/*")
public class AuthorizerAppIpaController {
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private BackendAuthorizerApiService backendAuthorizerApiService;
    @Autowired
    private BackendAppleAccountApiService backendAppleAccountApiService;

    @RequestMapping(value = "/ipa/list")
    public String listAuthorizerAppIpa(@ModelAttribute("appleAccountId") String appleAccountId,
            @ModelAttribute("appleId") String appleId, AuthorizerAppIpaParameter para, Model model) {
        if (!StringUtils.isEmpty(appleAccountId)) {
            try {
                para.setAppleAccountId(Integer.valueOf(appleAccountId));
            } catch (Exception e) {
            }
        }
        if (!StringUtils.isEmpty(appleId)) {
            AppleAuthorizerAccountParameter g = new AppleAuthorizerAccountParameter();
            g.setAppleId(appleId);
            ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> appleAuthorizerAccountResp = backendAppleAccountApiService
                    .listAppleAuthorizerAccount(g);
            if (appleAuthorizerAccountResp != null && appleAuthorizerAccountResp.getData() != null
                    && CollectionUtils.notEmptyAndNull(appleAuthorizerAccountResp.getData().getResultList())) {
                para.setAppleAccountId(appleAuthorizerAccountResp.getData().getResultList().get(0).getId());
                ApiRespWrapperUtils.handleListResp("appleAuthorizerAccountList", false, appleAuthorizerAccountResp,
                        para, model);
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AuthorizerAppIpa>> resp = backendAuthorizerApiService.listAuthorizerAppIpa(para);
        List<AuthorizerAppIpa> datas = ApiRespWrapperUtils.handleListResp(resp, para, model);
        List<Integer> rootIds = new ArrayList<Integer>();
        List<Integer> authorizerAppleAccountIds = new ArrayList<Integer>();
        if (CollectionUtils.notEmptyAndNull(datas)) {
            for (AuthorizerAppIpa authorizerAppIpa : datas) {
                rootIds.add(authorizerAppIpa.getRootId());
                if (StringUtils.isEmpty(appleId) || para.getAppleAccountId() == null
                        || para.getAppleAccountId().intValue() <= 0) {
                    authorizerAppleAccountIds.add(authorizerAppIpa.getAppleAccountId());
                }
            }
            if (CollectionUtils.notEmptyAndNull(authorizerAppleAccountIds)) {
                ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> appleAccountResp = backendAppleAccountApiService
                        .listAppleAuthorizerAccount(authorizerAppleAccountIds);
                ApiRespWrapperUtils.handleListResp("appleAuthorizerAccountList", false, appleAccountResp, para, model);
            }
            if (CollectionUtils.notEmptyAndNull(rootIds)) {
                ApiRespWrapper<Map<Integer, ApplicationSimple>> rootIdApplicationSimpleMapResp = this.backendApiService
                        .getAppSimple(rootIds);
                ApiRespWrapperUtils.handleMapResp("rootIdApplicationSimpleMap", false, rootIdApplicationSimpleMapResp,
                        para, model);
            }
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        return "authorizer/app/ipa/list.ftl";
    }

    @RequestMapping(value = "/ipa/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAuthorizerAppIpa(int id, Integer status) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的授权账号记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AuthorizerAppIpaParameter para = new AuthorizerAppIpaParameter();
            para.setId(id);
            para.setStatus(status);
            return this.backendAuthorizerApiService.modifyAuthorizerAppIpa(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }

    @RequestMapping(value = "/ipa/detail")
    public String detailAuthorizerAppIpa(int id, Model model) {
        if (id <= 0) {
            return "authorizer/app/ipa/detail.ftl";
        }
        AuthorizerAppIpaParameter para = new AuthorizerAppIpaParameter();
        para.setId(id);
        AuthorizerAppIpa data = ApiRespWrapperUtils.handleValueResp(
                backendAuthorizerApiService.detailAuthorizerAppIpa(para), para, model);
        if (data != null) {
            AppleAuthorizerAccountParameter appleAuthorizerAccountParameter = new AppleAuthorizerAccountParameter();
            appleAuthorizerAccountParameter.setId(data.getAppleAccountId());
            ApiRespWrapper<AppleAuthorizerAccount> appleAccountResp = backendAppleAccountApiService
                    .detailAppleAuthorizerAccount(appleAuthorizerAccountParameter);
            ApiRespWrapperUtils.handleValueResp("authorizerAppleAccount", appleAccountResp, para, model);
            RootApplication rootApplication = ApiRespWrapperUtils.handleValueResp("rootApplication",
                    backendApiService.getRootApplication(data.getRootId()), para, model);
            if (rootApplication.isOk() && rootApplication.getLatestAppId() != null
                    && rootApplication.getLatestAppId().intValue() > 0) {
                ApiRespWrapperUtils.handleValueResp("appSimple", backendApiService.getAppSimple(data.getRootId()),
                        para, model);
            }
        }
        return "authorizer/app/ipa/detail.ftl";
    }

    @RequestMapping(value = "/download/task/detail")
    public String detailAuthorizerAppDownloadTask(int id, Model model) {
        if (id <= 0) {
            return "authorizer/app/download/task/detail.ftl";
        }
        AuthorizerAppDownloadTaskParameter para = new AuthorizerAppDownloadTaskParameter();
        para.setId(id);
        AuthorizerAppDownloadTask data = ApiRespWrapperUtils.handleValueResp(
                backendAuthorizerApiService.detailAuthorizerAppDownloadTask(para), para, model);
        AppleAuthorizerAccountBuyAppInfo buyAppInfo = null;
        if (data != null) {
            AppleAuthorizerAccountBuyAppInfoParameter appleAuthorizerAccountBuyAppInfoParameter = new AppleAuthorizerAccountBuyAppInfoParameter();
            appleAuthorizerAccountBuyAppInfoParameter.setId(data.getBuyInfoId());
            buyAppInfo = ApiRespWrapperUtils.handleValueResp("buyAppInfo", backendAppleAccountApiService
                    .detailAppleAuthorizerAccountBuyAppInfo(appleAuthorizerAccountBuyAppInfoParameter), para, model);
            if (buyAppInfo != null) {
                AppleAuthorizerAccountParameter appleAuthorizerAccountParameter = new AppleAuthorizerAccountParameter();
                appleAuthorizerAccountParameter.setId(buyAppInfo.getAccountId());
                ApiRespWrapperUtils.handleValueResp("authorizerAppleAccount",
                        backendAppleAccountApiService.detailAppleAuthorizerAccount(appleAuthorizerAccountParameter),
                        para, model);
            }

            List<Integer> itemIds = new ArrayList<Integer>();
            itemIds.add(data.getItemId());
            ApiRespWrapperUtils.handleMapResp("itemIdRootSimples", false,
                    backendApiService.getRootSimpleByItemIds(itemIds), para, model);
        }
        model.addAttribute("taskStatus", EnumMapUtils.enumToMap(TaskStatus.values()));
        return "authorizer/app/download/task/detail.ftl";
    }

    @RequestMapping(value = "/download/task/list")
    public String listAuthorizerAppDownloadTask(AuthorizerAppDownloadTaskParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadTask>> resp = backendAuthorizerApiService
                .listAuthorizerAppDownloadTask(para);
        List<AuthorizerAppDownloadTask> datas = ApiRespWrapperUtils.handleListResp(resp, para, model);
        List<Integer> itemIds = new ArrayList<Integer>();
        List<Integer> buyInfoIds = new ArrayList<Integer>();
        List<Integer> downloadServerIds = new ArrayList<Integer>();
        if (CollectionUtils.notEmptyAndNull(datas)) {
            for (AuthorizerAppDownloadTask authorizerAppIpa : datas) {
                itemIds.add(authorizerAppIpa.getItemId());
                buyInfoIds.add(authorizerAppIpa.getBuyInfoId());
                if (authorizerAppIpa.getDownloadServerId() != null
                        && authorizerAppIpa.getDownloadServerId().intValue() > 0) {
                    downloadServerIds.add(authorizerAppIpa.getDownloadServerId());
                }
            }
        }
        if (CollectionUtils.notEmptyAndNull(itemIds)) {
            ApiRespWrapperUtils.handleMapResp("itemIdRootSimples", false,
                    backendApiService.getRootSimpleByItemIds(itemIds), para, model);
        }
        if (CollectionUtils.notEmptyAndNull(downloadServerIds)) {
            ApiRespWrapperUtils.handleListResp("downloadServers", false,
                    backendAuthorizerApiService.listAuthorizerAppDownloadServerInfo(downloadServerIds), para, model);
        }
        AppleAuthorizerAccountParameter appleAuthorizerAccountParameter = new AppleAuthorizerAccountParameter();
        appleAuthorizerAccountParameter.setSize(Integer.MAX_VALUE);
        ApiRespWrapperUtils.handleListResp("appleAuthorizerAccounts", false,
                backendAppleAccountApiService.listAppleAuthorizerAccount(appleAuthorizerAccountParameter), para, model);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("taskStatus", EnumMapUtils.enumToMap(TaskStatus.values()));
        model.addAttribute("pureIpas", EnumMapUtils.enumToMap(DownloadPureIpaFlag.values()));
        return "authorizer/app/download/task/list.ftl";
    }

    @RequestMapping(value = "/download/task/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAuthorizerAppDownloadTask(int id, Integer status, Integer taskStatus,
            Integer pureIpa) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的授权账号记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AuthorizerAppDownloadTaskParameter para = new AuthorizerAppDownloadTaskParameter();
            para.setId(id);
            para.setStatus(status);
            para.setTaskStatus(taskStatus);
            para.setPureIpa(pureIpa);
            return this.backendAuthorizerApiService.modifyAuthorizerAppDownloadTask(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }

    @RequestMapping(value = "/download/feedback/detail")
    public String detailAuthorizerAppDownloadFeedback(int id, Model model) {
        if (id <= 0) {
            return "authorizer/app/download/feedback/detail.ftl";
        }
        AuthorizerAppDownloadFeedbackParameter para = new AuthorizerAppDownloadFeedbackParameter();
        para.setId(id);
        AuthorizerAppDownloadFeedback data = ApiRespWrapperUtils.handleValueResp(
                backendAuthorizerApiService.detailAuthorizerAppDownloadFeedback(para), para, model);
        if (data != null) {
            int taskId = data.getTaskId();
            AuthorizerAppDownloadTaskParameter taskPara = new AuthorizerAppDownloadTaskParameter();
            taskPara.setId(taskId);
            AppleAuthorizerAccountBuyAppInfo buyAppInfo = null;
            AuthorizerAppDownloadTask authorizerAppDownloadTask = null;
            authorizerAppDownloadTask = ApiRespWrapperUtils.handleValueResp("authorizerAppDownloadTask",
                    backendAuthorizerApiService.detailAuthorizerAppDownloadTask(taskPara), para, model);
            if (authorizerAppDownloadTask != null) {
                AppleAuthorizerAccountBuyAppInfoParameter appleAuthorizerAccountBuyAppInfoParameter = new AppleAuthorizerAccountBuyAppInfoParameter();
                appleAuthorizerAccountBuyAppInfoParameter.setId(authorizerAppDownloadTask.getBuyInfoId());
                buyAppInfo = ApiRespWrapperUtils
                        .handleValueResp("buyAppInfo", backendAppleAccountApiService
                                .detailAppleAuthorizerAccountBuyAppInfo(appleAuthorizerAccountBuyAppInfoParameter),
                                para, model);
            }
            if (buyAppInfo != null) {
                AppleAuthorizerAccountParameter appleAuthorizerAccountParameter = new AppleAuthorizerAccountParameter();
                appleAuthorizerAccountParameter.setId(buyAppInfo.getAccountId());
                ApiRespWrapper<AppleAuthorizerAccount> appleAccountResp = backendAppleAccountApiService
                        .detailAppleAuthorizerAccount(appleAuthorizerAccountParameter);
                ApiRespWrapperUtils.handleValueResp("authorizerAppleAccount", appleAccountResp, para, model);
                RootApplication rootApplication = ApiRespWrapperUtils.handleValueResp("rootApplication",
                        backendApiService.getRootApplication(buyAppInfo.getRootId()), para, model);
                if (rootApplication.isOk() && rootApplication.getLatestAppId() != null
                        && rootApplication.getLatestAppId().intValue() > 0) {
                    ApiRespWrapperUtils.handleValueResp("appSimple",
                            backendApiService.getAppSimple(buyAppInfo.getRootId()), para, model);
                }
            }
        }
        model.addAttribute("feedbackStatus", EnumMapUtils.enumToMap(AuthorizerAppDownloadFeedbackStatus.values()));
        model.addAttribute("taskStatus", EnumMapUtils.enumToMap(TaskStatus.values()));
        return "authorizer/app/download/feedback/detail.ftl";
    }

    @RequestMapping(value = "/download/feedback/list")
    public String listAuthorizerAppDownloadFeedback(@ModelAttribute("taskId") String taskId,
            AuthorizerAppDownloadFeedbackParameter para, Model model) {
        if (!StringUtils.isEmpty(taskId) && (para.getTaskId() != null && para.getTaskId().intValue() > 0)) {
            try {
                para.setTaskId(Integer.valueOf(taskId));
            } catch (Exception e) {
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadFeedback>> resp = backendAuthorizerApiService
                .listAuthorizerAppDownloadFeedback(para);
        List<AuthorizerAppDownloadFeedback> datas = ApiRespWrapperUtils.handleListResp(resp, para, model);
        List<Integer> rootIds = new ArrayList<Integer>();
        if (CollectionUtils.notEmptyAndNull(datas)) {
            for (AuthorizerAppDownloadFeedback authorizerAppDownloadFeedback : datas) {
                rootIds.add(authorizerAppDownloadFeedback.getRootId());
            }
        }
        if (CollectionUtils.notEmptyAndNull(rootIds)) {
            ApiRespWrapperUtils.handleMapResp("rootIdApplicationSimpleMap", false,
                    this.backendApiService.getAppSimple(rootIds), para, model);
        }
        AppleAuthorizerAccountParameter appleAuthorizerAccountParameter = new AppleAuthorizerAccountParameter();
        appleAuthorizerAccountParameter.setSize(Integer.MAX_VALUE);
        ApiRespWrapperUtils.handleListResp("appleAuthorizerAccounts", false,
                backendAppleAccountApiService.listAppleAuthorizerAccount(appleAuthorizerAccountParameter), para, model);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("feedbackStatus", EnumMapUtils.enumToMap(AuthorizerAppDownloadFeedbackStatus.values()));
        return "authorizer/app/download/feedback/list.ftl";
    }

    @RequestMapping(value = "/download/feedback/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAuthorizerAppDownloadFeedback(int id, Integer status, Integer feedbackStatus) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的授权账号记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AuthorizerAppDownloadFeedbackParameter para = new AuthorizerAppDownloadFeedbackParameter();
            para.setId(id);
            if (status != null) {
                para.setStatus(StatusType.STATUS_DEL.getStatus());
            }
            para.setFeedbackStatus(feedbackStatus);
            return this.backendAuthorizerApiService.modifyAuthorizerAppDownloadFeedback(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }

    @RequestMapping(value = "/download/server/info/list")
    public String listAuthorizerAppDownloadServerInfo(@ModelAttribute("addMsg") String addMsg,
            AuthorizerAppDownloadServerInfoParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AuthorizerAppDownloadServerInfo>> resp = backendAuthorizerApiService
                .listAuthorizerAppDownloadServerInfo(para);
        ApiRespWrapperUtils.handleListResp(resp, para, model);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        if (!StringUtils.isEmpty(addMsg)) {
            model.addAttribute("addMsg", addMsg);
        }
        return "authorizer/app/download/server/info/list.ftl";
    }

    @RequestMapping(value = "/download/server/info/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAuthorizerAppDownloadServerInfo(int id, Integer status, String url,
            String name, String testUrl) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的授权账号记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AuthorizerAppDownloadServerInfoParameter para = new AuthorizerAppDownloadServerInfoParameter();
            para.setId(id);
            para.setStatus(status);
            para.setName(name);
            para.setUrl(url);
            para.setTestUrl(testUrl);
            return this.backendAuthorizerApiService.modifyAuthorizerAppDownloadServerInfo(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }

    @RequestMapping(value = "/download/server/info/add")
    public String addAuthorizerAppDownloadServerInfo(String url, String name, String testUrl, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(name)) {
            errMsg = "未知的下载服务器名称!";
        }
        if (StringUtils.isEmpty(errMsg) && !UrlUtils.isUrl(url)) {
            errMsg = "未知的下载服务器地址!";
        }
        if (StringUtils.isEmpty(errMsg) && !UrlUtils.isUrl(testUrl)) {
            errMsg = "未知的服务器测试响应地址!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AuthorizerAppDownloadServerInfoParameter para = new AuthorizerAppDownloadServerInfoParameter();
            para.setName(name);
            para.setUrl(url);
            para.setTestUrl(testUrl);
            ApiRespWrapper<Boolean> resp = this.backendAuthorizerApiService.addAuthorizerAppDownloadServerInfo(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("addMsg", errMsg);
        return "redirect:/auth/authorizer/app/download/server/info/list";
    }
}
