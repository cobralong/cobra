package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.account.Account;
import com.appchina.ios.core.dto.account.AccountFavorite;
import com.appchina.ios.core.dto.account.AccountFeedBackStatus;
import com.appchina.ios.core.dto.account.AccountFeedback;
import com.appchina.ios.core.dto.account.AccountWishInfo;
import com.appchina.ios.core.dto.account.AccountWishProgressInfo;
import com.appchina.ios.core.dto.account.PcSuiteConnectedDeviceInfo;
import com.appchina.ios.core.dto.account.PcSuiteUser;
import com.appchina.ios.core.dto.app.AppWishHistory;
import com.appchina.ios.core.dto.app.AppWishInfo;
import com.appchina.ios.core.dto.app.RootApplication;
import com.appchina.ios.core.dto.system.CpuArch;
import com.appchina.ios.core.dto.system.Windows;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.mgnt.controller.model.AccountFeedbackParameter;
import com.appchina.ios.mgnt.controller.model.AccountParamter;
import com.appchina.ios.mgnt.controller.model.AccountWishInfoParameter;
import com.appchina.ios.mgnt.controller.model.AccountWishProgressInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppWishParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteConnectedDeviceInfoParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteUserParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BackendPcSuiteApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.ApiRespWrapperUtils;
import com.appchina.ios.web.utils.CollectionUtils;

@Controller
@RequestMapping(value = "/auth/account/*")
public class AccountController {
    // private static final Logger log =
    // Logger.getLogger(AppleAccountController.class);
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private BackendPcSuiteApiService backendPcSuiteApiService;

    /**
     * 已注册用户列表
     * 
     * @param para
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String listRegisterAccount(AccountParamter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<Account>> resp = this.backendApiService.listAccount(para);
        List<Account> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        para.getPager().setTotal(total);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        return "account/list.ftl";
    }

    @RequestMapping(value = "/appwish/info/list")
    public String listAppWishInfo(AppWishParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppWishInfo>> resp = this.backendApiService.listAppWishInfo(para);
        if (resp != null && resp.getData() != null && CollectionUtils.notEmptyAndNull(resp.getData().getResultList())) {
            para.getPager().setTotal(resp.getData().getTotalCount());
            model.addAttribute("values", resp.getData().getResultList());
            List<Integer> rootIds = new ArrayList<Integer>();
            for (AppWishInfo history : resp.getData().getResultList()) {
                if (!rootIds.contains(history.getRootId())) {
                    rootIds.add(history.getRootId());
                }
            }
            ApiRespWrapper<Map<Integer, ApplicationSimple>> rootAppSimpleMapResp = this.backendApiService
                    .getAppSimple(rootIds);
            if (rootAppSimpleMapResp != null && rootAppSimpleMapResp.getData() != null) {
                Map<String, ApplicationSimple> rootSimples = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : rootAppSimpleMapResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", rootSimples);
            }
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("columns", AppWishInfo.ORDER_COLUMNS);
        model.addAttribute("para", para);
        return "account/appwish/info/list.ftl";
    }

    @RequestMapping(value = "/appwish/history/list")
    public String listAppWishHistory(AppWishParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppWishHistory>> resp = this.backendApiService.listAppWishHistory(para);
        if (resp != null && resp.getData() != null && CollectionUtils.notEmptyAndNull(resp.getData().getResultList())) {
            para.getPager().setTotal(resp.getData().getTotalCount());
            model.addAttribute("values", resp.getData().getResultList());
            List<Integer> rootIds = new ArrayList<Integer>();
            for (AppWishHistory history : resp.getData().getResultList()) {
                if (!rootIds.contains(history.getRootId())) {
                    rootIds.add(history.getRootId());
                }
            }
            ApiRespWrapper<Map<Integer, ApplicationSimple>> rootAppSimpleMapResp = this.backendApiService
                    .getAppSimple(rootIds);
            if (rootAppSimpleMapResp != null && rootAppSimpleMapResp.getData() != null) {
                Map<String, ApplicationSimple> rootSimples = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : rootAppSimpleMapResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", rootSimples);
            }
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("para", para);
        return "account/appwish/history/list.ftl";
    }

    @RequestMapping(value = "/feedback/list")
    public String listFeedback(AccountFeedbackParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AccountFeedback>> resp = this.backendApiService.listAccountFeedback(para);
        if (resp != null && resp.getData() != null && CollectionUtils.notEmptyAndNull(resp.getData().getResultList())) {
            para.getPager().setTotal(resp.getData().getTotalCount());
            model.addAttribute("values", resp.getData().getResultList());
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(AccountFeedBackStatus.values()));
        model.addAttribute("para", para);
        return "account/feedback/list.ftl";
    }

    @RequestMapping(value = "/feedback/detail")
    public String detailFeedback(@ModelAttribute("errMsg") String errMsg, @ModelAttribute("id") String id, Model model) {
        if (StringUtils.isEmpty(id)) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg = "未知详情Id";
            }
        } else {
            ApiRespWrapper<AccountFeedback> resp = this.backendApiService.detailAccountFeedback(Integer.parseInt(id));
            if (resp != null && resp.getData() != null) {
                model.addAttribute("value", resp.getData());
            }
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(AccountFeedBackStatus.values()));
        model.addAttribute("errMsg", errMsg);
        return "account/feedback/detail.ftl";
    }

    @RequestMapping(value = "/feedback/modify")
    public String modifyFeedback(AccountFeedbackParameter para, RedirectAttributes rattrs) {
        String errMsg = "";
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyAccountFeedbback(para);
        if (resp == null) {
            errMsg = "未知网络问题";
        } else {
            if (resp.getData() == null || !resp.getData().booleanValue()) {
                errMsg = (StringUtils.isEmpty(resp.getMessage())) ? "未知远程服务器错误。Error code:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("id", String.valueOf(para.getId()));
        return "redirect:/auth/account/feedback/detail";
    }

    @RequestMapping(value = "/feedback/status/modify.json")
    @ResponseBody
    public String modifyFeedbackStatus(int id, int status) {
        AccountFeedbackParameter para = new AccountFeedbackParameter();
        para.setId(id);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyAccountFeedbback(para);
        if (resp == null) {
            return "未知网络问题";
        } else {
            if (resp.getData() == null || !resp.getData().booleanValue()) {
                return (StringUtils.isEmpty(resp.getMessage())) ? "未知远程服务器错误。Error code:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        return "";
    }

    @RequestMapping(value = "/wish/info/list")
    public String listAccountWishInfo(@ModelAttribute("errMsg") String errMsg, AccountWishInfoParameter para,
            Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AccountWishInfo>> resp = this.backendApiService.listAccountWishInfo(para);
        List<AccountWishInfo> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
            List<Integer> accountIds = new ArrayList<>();
            List<Integer> rootIds = new ArrayList<>();
            for (AccountWishInfo accountWishInfo : values) {
                if (!accountIds.contains(accountWishInfo.getAccountId())) {
                    accountIds.add(accountWishInfo.getAccountId());
                }
                if (!rootIds.contains(accountWishInfo.getRootId())) {
                    rootIds.add(accountWishInfo.getRootId());
                }
            }
            if (CollectionUtils.notEmptyAndNull(accountIds)) {
                List<Account> accountList = this.backendApiService.listAccount(accountIds);
                Map<String, Account> accountMap = new HashMap<String, Account>();
                for (Account account : accountList) {
                    accountMap.put(String.valueOf(account.getId()), account);
                }
                model.addAttribute("accountMap", accountMap);
            }
            if (CollectionUtils.notEmptyAndNull(rootIds)) {
                ApiRespWrapper<ListWrapResp<RootApplication>> rootApplicationLists = this.backendApiService
                        .listRootApplication(rootIds);
                List<RootApplication> rootApplicationList = new ArrayList<RootApplication>();
                if (rootApplicationLists != null && rootApplicationLists.getData().getResultList() != null) {
                    rootApplicationList = rootApplicationLists.getData().getResultList();
                }
                Map<String, RootApplication> rootApplicationMap = new HashMap<String, RootApplication>();
                for (RootApplication rootApplication : rootApplicationList) {
                    rootApplicationMap.put(String.valueOf(rootApplication.getRootId()), rootApplication);
                }
                model.addAttribute("rootApplicationMap", rootApplicationMap);
            }
        }
        para.getPager().setTotal(total);
        model.addAttribute("para", para);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("values", values);
        model.addAttribute("errMsg", errMsg);
        return "account/wish/info/list.ftl";
    }

    @RequestMapping(value = "/wish/progress/list")
    public String listAccountWishProgress(@ModelAttribute("errMsg") String errMsg,
            AccountWishProgressInfoParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AccountWishProgressInfo>> resp = this.backendApiService
                .listAccountWishProgressInfo(para);
        List<AccountWishProgressInfo> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
            List<Integer> accountIds = new ArrayList<>();
            List<Integer> rootIds = new ArrayList<>();
            for (AccountWishProgressInfo accountWishInfo : values) {
                if (!accountIds.contains(accountWishInfo.getAccountId())) {
                    accountIds.add(accountWishInfo.getAccountId());
                }
                if (!rootIds.contains(accountWishInfo.getRootId())) {
                    rootIds.add(accountWishInfo.getRootId());
                }
            }
            if (CollectionUtils.notEmptyAndNull(accountIds)) {
                List<Account> accountList = this.backendApiService.listAccount(accountIds);
                Map<String, Account> accountMap = new HashMap<String, Account>();
                for (Account account : accountList) {
                    accountMap.put(String.valueOf(account.getId()), account);
                }
                model.addAttribute("accountMap", accountMap);
            }
            if (CollectionUtils.notEmptyAndNull(rootIds)) {
                ApiRespWrapper<ListWrapResp<RootApplication>> rootApplicationLists = this.backendApiService
                        .listRootApplication(rootIds);
                List<RootApplication> rootApplicationList = new ArrayList<RootApplication>();
                if (rootApplicationLists != null && rootApplicationLists.getData().getResultList() != null) {
                    rootApplicationList = rootApplicationLists.getData().getResultList();
                }
                Map<String, RootApplication> rootApplicationMap = new HashMap<String, RootApplication>();
                for (RootApplication rootApplication : rootApplicationList) {
                    rootApplicationMap.put(String.valueOf(rootApplication.getRootId()), rootApplication);
                }
                model.addAttribute("rootApplicationMap", rootApplicationMap);
            }
        }
        para.getPager().setTotal(total);
        model.addAttribute("para", para);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("values", values);
        model.addAttribute("errMsg", errMsg);
        return "account/wish/progress/list.ftl";
    }

    @RequestMapping(value = "/favorite/list")
    public String listAccountFavorite(@ModelAttribute("errMsg") String errMsg, AccountWishInfoParameter para,
            Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AccountFavorite>> resp = this.backendApiService.listAccountFavoriteInfo(para);
        List<AccountFavorite> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
            List<Integer> accountIds = new ArrayList<>();
            List<Integer> rootIds = new ArrayList<>();
            for (AccountFavorite accountWishInfo : values) {
                if (!accountIds.contains(accountWishInfo.getAccountId())) {
                    accountIds.add(accountWishInfo.getAccountId());
                }
                if (!rootIds.contains(accountWishInfo.getRootId())) {
                    rootIds.add(accountWishInfo.getRootId());
                }
            }
            if (CollectionUtils.notEmptyAndNull(accountIds)) {
                List<Account> accountList = this.backendApiService.listAccount(accountIds);
                Map<String, Account> accountMap = new HashMap<String, Account>();
                for (Account account : accountList) {
                    accountMap.put(String.valueOf(account.getId()), account);
                }
                model.addAttribute("accountMap", accountMap);
            }
            if (CollectionUtils.notEmptyAndNull(rootIds)) {
                ApiRespWrapper<ListWrapResp<RootApplication>> rootApplicationLists = this.backendApiService
                        .listRootApplication(rootIds);
                List<RootApplication> rootApplicationList = new ArrayList<RootApplication>();
                if (rootApplicationLists != null && rootApplicationLists.getData().getResultList() != null) {
                    rootApplicationList = rootApplicationLists.getData().getResultList();
                }
                Map<String, RootApplication> rootApplicationMap = new HashMap<String, RootApplication>();
                for (RootApplication rootApplication : rootApplicationList) {
                    rootApplicationMap.put(String.valueOf(rootApplication.getRootId()), rootApplication);
                }
                model.addAttribute("rootApplicationMap", rootApplicationMap);
            }
        }
        para.getPager().setTotal(total);
        model.addAttribute("para", para);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("values", values);
        model.addAttribute("errMsg", errMsg);
        return "account/favorite/list.ftl";
    }

    @RequestMapping(value = "/pcsuite/user/list")
    public String listPcSuiteUser(PcSuiteUserParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteUser>> resp = this.backendPcSuiteApiService.listPcSuiteUser(para);
        ApiRespWrapperUtils.handleListResp("channels", false, backendPcSuiteApiService.listPcSuiteChannel(null), para,
                model);
        ApiRespWrapperUtils.handleListResp("versions", false, backendPcSuiteApiService.listPcSuiteVersion(null), para,
                model);
        ApiRespWrapperUtils.handleListResp(resp, para, model);
        model.addAttribute("arches", EnumMapUtils.enumToMap(CpuArch.values()));
        model.addAttribute("windows", EnumMapUtils.enumToMap(Windows.values()));
        return "account/pcsuite/user/list.ftl";
    }

    @RequestMapping(value = "/pcsuite/connected/device/info/list")
    public String listPcSuiteConnectedDeviceInfo(PcSuiteConnectedDeviceInfoParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteConnectedDeviceInfo>> resp = this.backendPcSuiteApiService
                .listPcSuiteConnectedDeviceInfo(para);
        ApiRespWrapperUtils.handleListResp(resp, para, model);
        ApiRespWrapperUtils.handleListResp("platforms", false, this.backendApiService.listPlatform(), para, model);
        return "account/pcsuite/connected/device/info/list.ftl";
    }
}
