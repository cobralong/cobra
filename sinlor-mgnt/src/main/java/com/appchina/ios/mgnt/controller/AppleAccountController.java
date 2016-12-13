package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.crawler.model.AuthoAppDownBoughtInfoResp;
import com.appchina.ios.core.dto.account.AppleAccount;
import com.appchina.ios.core.dto.account.AppleAccountAutomatedInfo;
import com.appchina.ios.core.dto.account.AppleAccountAutomatedInfoActiveStatus;
import com.appchina.ios.core.dto.account.AppleAccountAutomatedInfoAutomatedType;
import com.appchina.ios.core.dto.account.AppleAccountSource;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccount;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountAuthDeviceInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountAuthPcMachineInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountAuthPcServerInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountBuyAppInfo;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountPrepare;
import com.appchina.ios.core.dto.account.AppleAuthorizerAccountType;
import com.appchina.ios.core.dto.account.AppleIdBuyHistory;
import com.appchina.ios.core.dto.account.AppleShareAccount;
import com.appchina.ios.core.dto.account.AppleShareAccountAuthroizeInfoWriteStatus;
import com.appchina.ios.core.dto.account.AppleShareAccountType;
import com.appchina.ios.core.dto.account.DispatchConfigure;
import com.appchina.ios.core.dto.account.DispatchDesc;
import com.appchina.ios.core.dto.account.PcSuiteBindAppleIdInfo;
import com.appchina.ios.core.dto.system.ClientChannel;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.mgnt.controller.model.AppleAccountAutomatedInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAccountParamter;
import com.appchina.ios.mgnt.controller.model.AppleAccountSourceParamter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountAuthDeviceInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountAuthPcMachineInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountAuthPcServerInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountBuyAppInfoParameter;
import com.appchina.ios.mgnt.controller.model.AppleAuthorizerAccountParameter;
import com.appchina.ios.mgnt.controller.model.AppleIdBuyHistoryParamter;
import com.appchina.ios.mgnt.controller.model.AppleShareAccountParamter;
import com.appchina.ios.mgnt.controller.model.ClientChannelParameter;
import com.appchina.ios.mgnt.controller.model.DispatchConfigureParameter;
import com.appchina.ios.mgnt.controller.model.DispatchConfiigureAddParameter;
import com.appchina.ios.mgnt.controller.model.DispatchDescParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteBindAppleIdInfoParameter;
import com.appchina.ios.mgnt.controller.model.UploadFileResp;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BackendAppleAccountApiService;
import com.appchina.ios.mgnt.service.BackendAuthorizerApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.ApiRespWrapperUtils;
import com.appchina.ios.web.utils.CollectionUtils;

@Controller
@RequestMapping(value = "/auth/appleaccount/*")
public class AppleAccountController {
    // private static final Logger log =
    // Logger.getLogger(AppleAccountController.class);
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private BackendAppleAccountApiService backendAppleAccountApiService;
    @Autowired
    private BackendAuthorizerApiService backendAuthorizerApiService;
    @Autowired
    private BannerStorageService bannerStorageService;

    @RequestMapping(value = "/automated/list")
    public String listAutomatedAppleAccount(AppleAccountAutomatedInfoParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppleAccountAutomatedInfo>> resp = this.backendApiService
                .listAppleAccountAutomatedInfo(para);
        List<AppleAccountAutomatedInfo> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        para.getPager().setTotal(total);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("activeStatuses",
                EnumMapUtils.enumToMap(AppleAccountAutomatedInfoActiveStatus.values(), true));
        model.addAttribute("automatedTypes",
                EnumMapUtils.enumToMap(AppleAccountAutomatedInfoAutomatedType.values(), true));
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        return "appleaccount/automated/list.ftl";
    }

    @RequestMapping(value = "/automated/detail")
    public String detailAutomatedAppleAccount(int id, Model model) {
        ApiRespWrapper<AppleAccountAutomatedInfo> resp = this.backendApiService.detailAppleAccountAutomatedInfo(id);
        ApiRespWrapperUtils.handleValueResp(resp, null, model);
        model.addAttribute("statuses", EnumMapUtils.enumToMap(StatusType.values(), false));
        model.addAttribute("activeStatuses",
                EnumMapUtils.enumToMap(AppleAccountAutomatedInfoActiveStatus.values(), false));
        model.addAttribute("automatedTypes",
                EnumMapUtils.enumToMap(AppleAccountAutomatedInfoAutomatedType.values(), false));
        return "appleaccount/automated/detail.ftl";
    }

    @RequestMapping(value = "/list")
    public String listAppleAccount(AppleAccountParamter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppleAccount>> resp = this.backendApiService.listAppleAccount(para);
        List<AppleAccount> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        ApiRespWrapper<ListWrapResp<AppleAccountSource>> sourceResp = this.backendApiService.listAppleAccountSource();
        if (sourceResp != null && !CollectionUtils.emptyOrNull(sourceResp.getData().getResultList())) {
            Map<String, String> sourceMap = new LinkedHashMap<String, String>();
            sourceMap.putAll(AppleAccountParamter.SOURCE_MAP);
            model.addAttribute("sources", sourceMap);
            for (AppleAccountSource appleAccountSource : sourceResp.getData().getResultList()) {
                sourceMap.put(String.valueOf(appleAccountSource.getId()), appleAccountSource.getDesc());
            }
        }
        para.getPager().setTotal(total);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("binds", AppleAccountParamter.BINDMAPS);
        model.addAttribute("bindStatus", AppleAccount.LOGINSTATUS_MAP);
        return "appleaccount/list.ftl";
    }

    @RequestMapping(value = "/source/list")
    public String listappleaccountsource(@ModelAttribute("errMsg") String errMsg, Model model) {
        ApiRespWrapper<ListWrapResp<AppleAccountSource>> resp = this.backendApiService.listAppleAccountSource();
        List<AppleAccountSource> values = null;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("values", values);
        model.addAttribute("errMsg", errMsg);
        return "appleaccount/source/list.ftl";
    }

    @RequestMapping(value = "/source/add")
    public String addappleaccountsource(String desc, String url, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(desc)) {
            errMsg = "未知的描述信息.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleAccountSourceParamter para = new AppleAccountSourceParamter();
            para.setDesc(desc);
            para.setUrl(url);
            ApiRespWrapper<Boolean> resp = this.backendApiService.addAppleAccountSource(para);
            errMsg = resp == null ? "未知错误" : resp.getData() == null || !resp.getData() ? resp.getMessage() : "";
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/appleaccount/source/list";
    }

    @RequestMapping(value = "/dispatch/desc/list")
    public String conf(Model model) {
        ApiRespWrapper<ListWrapResp<DispatchDesc>> resp = this.backendApiService.listDispatchDesc();
        List<DispatchDesc> values = resp == null || resp.getData() == null ? null : resp.getData().getResultList();
        model.addAttribute("values", values);
        return "appleaccount/dispatch/desc/list.ftl";
    }


    @RequestMapping(value = "/dispatch/desc/modify")
    @ResponseBody
    public String modifydispatchdesc(int id, String desc) {
        DispatchDescParameter parameter = new DispatchDescParameter();
        parameter.setId(id);
        parameter.setDesc(desc);
        try {
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyDispatchDesc(parameter);
            if (resp == null || resp.getData() == null || !resp.getData()) {
                return resp == null ? "修改苹果帐号分发结果描述语失败,错误." : resp.getMessage();
            }
            return "";
        } catch (Exception e) {
            return "修改苹果帐号分发结果描述语失败，错误:" + e.getMessage();
        }
    }

    @RequestMapping(value = "/dispatch/configure/list")
    public String dispatchconfigure(@ModelAttribute("errMsg") String errMsg, DispatchConfigureParameter para,
            Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<DispatchConfigure>> resp = this.backendApiService.listDispatchConfigure(para);
        List<DispatchConfigure> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            total = resp.getData().getTotalCount();
            values = resp.getData().getResultList();
        }
        if (!CollectionUtils.emptyOrNull(values)) {
            List<Integer> channelIds = initChannelIds(values);
            ClientChannelParameter clientChannelParameter = new ClientChannelParameter();
            clientChannelParameter.setChannels(CollectionUtils.listToString(channelIds, ","));
            ApiRespWrapper<ListWrapResp<ClientChannel>> channelListResp = this.backendApiService
                    .listClientChannel(clientChannelParameter);
            if (channelListResp != null && channelListResp.getData() != null
                    && channelListResp.getData().getResultList() != null) {
                Map<String, String> channels = buildChannels(channelListResp.getData().getResultList());
                model.addAttribute("channels", channels);
            }
        }
        para.getPager().setTotal(total);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("topChannels", DispatchConfigureParameter.TOP_CHANNELS);
        model.addAttribute("status", DispatchConfigureParameter.ALL_STATUS);
        return "appleaccount/dispatch/configure/list.ftl";
    }

    @RequestMapping(value = "/dispatch/configure/add")
    public String adddispatchconfigure(String rc, String fc, String sc, String lc, String st, String et, String dd,
            boolean rw, boolean ri, Model model, RedirectAttributes rattrs) {
        String errMsg = "";
        DispatchConfiigureAddParameter para = new DispatchConfiigureAddParameter();
        if (StringUtils.isEmpty(rc)) {
            errMsg = "渠道不能为空";
        }
        if (StringUtils.isEmpty(errMsg)) {
            errMsg = checkDate(st);
        }
        if (StringUtils.isEmpty(errMsg)) {
            errMsg = checkDate(et);
        }
        if (StringUtils.isEmpty(errMsg)) {
            errMsg = checkInt(dd, para);
        }
        String channel = "";
        if (StringUtils.isEmpty(errMsg)) {
            if (StringUtils.equals("-9999", rc)) {
                channel = DispatchConfigure.ALL_CHANNEL;
            } else if (StringUtils.equals("-1", fc)) {
                channel = DispatchConfigure.ALL_CHANNEL;
            } else if (StringUtils.equals("-1", sc)) {
                channel = fc;
            } else if (StringUtils.equals("-1", lc)) {
                channel = sc;
            } else {
                channel = lc;
            }
        }
        para.setRewrite(rw);
        para.setReinit(ri);
        para.setChannel(channel);
        para.setSt(st);
        para.setEt(et);
        ApiRespWrapper<Boolean> resp = this.backendApiService.addDispatchConfigure(para);
        errMsg = resp == null ? "未知错误" : resp.getData() == null || !resp.getData() ? resp.getMessage() : "";
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/appleaccount/dispatch/configure/list";
    }

    private String checkInt(String dd, DispatchConfiigureAddParameter para) {
        if (StringUtils.isEmpty(dd)) {
            return "数据格式错误.";
        }
        try {
            para.setDayDispatchs(Integer.parseInt(dd));
        } catch (Exception e) {
            return "数据格式错误.";
        }
        return null;
    }

    private String checkDate(String st) {
        if (StringUtils.isEmpty(st)) {
            return "时间格式错误";
        }
        try {
            DateUtils.parseLongStr(st);
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    private Map<String, String> buildChannels(List<ClientChannel> channelList) {
        Map<String, String> ret = new HashMap<String, String>();
        for (ClientChannel clientChannel : channelList) {
            ret.put(String.valueOf(clientChannel.getId()), clientChannel.getChannel());
        }
        return ret;
    }

    private List<Integer> initChannelIds(List<DispatchConfigure> values) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/shareaccount/list")
    public String listAppleShareAccount(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("appleId") String appleId, AppleShareAccountParamter para, Model model) {
        if (!StringUtils.isEmpty(appleId)) {
            para.setAppleId(appleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppleShareAccount>> resp = this.backendApiService.listShareAccount(para);
        if (resp != null && resp.getData() != null && CollectionUtils.notEmptyAndNull(resp.getData().getResultList())) {
            para.getPager().setTotal(resp.getData().getTotalCount());
            model.addAttribute("values", resp.getData().getResultList());
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("types", AppleShareAccountType.values());
        model.addAttribute("para", para);
        return "appleaccount/shareaccount/list.ftl";
    }

    @RequestMapping(value = "/shareaccount/add", method = RequestMethod.POST)
    public String addShareAccount(String appleId, String password, String name, String type, String locale,
            String info, @RequestParam(required = false) CommonsMultipartFile icon, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(appleId)) {
            errMsg = "共享苹果账号Id不能为空";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(password)) {
            errMsg = "共享苹果账号密码不能为空";
        }
        String iconUrl = null;
        if (StringUtils.isEmpty(errMsg) && icon != null && !icon.isEmpty()) {
            UploadFileResp resp = bannerStorageService.saveShareAccountIcon(icon);
            if (resp != null && resp.isSaved()) {
                iconUrl = resp.getUrl();
            } else {
                if (resp == null) {
                    errMsg = "存储头像遇到未知异常.";
                } else {
                    errMsg = resp.getMessage();
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleShareAccountParamter para = new AppleShareAccountParamter();
            para.setAppleId(appleId);
            para.setName(name);
            para.setLocale(locale);
            para.setInfo(info);
            para.setIcon(iconUrl);
            para.setPassword(password);
            AppleShareAccountType accountType = AppleShareAccountType.instance(type);
            para.setType(accountType.getType());
            ApiRespWrapper<Boolean> resp = this.backendApiService.addShareAccount(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("errMsg", errMsg);
        rattrs.addAttribute("appleId", appleId);
        return "redirect:/auth/appleaccount/shareaccount/list";
    }

    @RequestMapping(value = "/shareaccount/modify.json")
    @ResponseBody
    public String modifyAppleShareAccount(int id, Integer status, String password, String name, String locale,
            @RequestParam(required = false) CommonsMultipartFile icon) {
        String errMsg = "";
        String iconUrl = null;
        if (icon != null && !icon.isEmpty()) {
            UploadFileResp resp = bannerStorageService.saveShareAccountIcon(icon);
            if (resp != null && resp.isSaved()) {
                iconUrl = resp.getUrl();
            } else {
                if (resp == null) {
                    errMsg = "存储头像遇到未知异常.";
                } else {
                    errMsg = resp.getMessage();
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleShareAccountParamter para = new AppleShareAccountParamter();
            para.setId(id);
            para.setStatus(status);
            para.setPassword(password);
            para.setIcon(iconUrl);
            para.setName(name);
            para.setLocale(locale);
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyShareAccountSource(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        return errMsg;
    }

    @RequestMapping(value = "/shareaccount/modify/icon")
    public String modifyAppleShareAccount(int id, @RequestParam(required = false) CommonsMultipartFile icon,
            RedirectAttributes rattrs) {
        String errMsg = "";
        String iconUrl = null;
        if (icon != null && !icon.isEmpty()) {
            UploadFileResp resp = bannerStorageService.saveShareAccountIcon(icon);
            if (resp != null && resp.isSaved()) {
                iconUrl = resp.getUrl();
            } else {
                if (resp == null) {
                    errMsg = "存储头像遇到未知异常.";
                } else {
                    errMsg = resp.getMessage();
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleShareAccountParamter para = new AppleShareAccountParamter();
            para.setId(id);
            para.setIcon(iconUrl);
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyShareAccountSource(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("errMsg", errMsg);
        return "redirect:/auth/appleaccount/shareaccount/detail?id=" + id;
    }

    @RequestMapping(value = "/shareaccount/detail")
    public String detailAppleShareAccount(int id, Model model) {
        AppleShareAccountParamter para = new AppleShareAccountParamter();
        para.setId(id);
        ApiRespWrapper<AppleShareAccount> resp = backendApiService.detailShareAccount(para);
        if (resp != null && resp.getData() != null) {
            model.addAttribute("value", resp.getData());
            model.addAttribute("type", AppleShareAccountType.instance(resp.getData().getType()).getDesc());
        }
        return "appleaccount/shareaccount/detail.ftl";
    }

    @RequestMapping(value = "/shareaccount/buyapp/list")
    public String listAppleShareAccountBuyApp(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("appleId") String appleId, AppleIdBuyHistoryParamter para, Model model) {
        if (!StringUtils.isEmpty(appleId)) {
            para.setAppleId(appleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppleIdBuyHistory>> resp = this.backendApiService.listShareAccountBuyHistory(para);
        if (resp != null && resp.getData() != null && CollectionUtils.notEmptyAndNull(resp.getData().getResultList())) {
            para.getPager().setTotal(resp.getData().getTotalCount());
            model.addAttribute("values", resp.getData().getResultList());
            List<Integer> rootIds = new ArrayList<Integer>();
            for (AppleIdBuyHistory history : resp.getData().getResultList()) {
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
        return "appleaccount/shareaccount/buyapp/list.ftl";
    }

    @RequestMapping(value = "/shareaccount/buyapp/add", method = RequestMethod.POST)
    public String addAppleShareAccountBuyApp(String appleId, Integer rootId, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(appleId)) {
            errMsg = "共享苹果账号Id不能为空";
        }
        if (StringUtils.isEmpty(errMsg)
                && (rootId == null || rootId.intValue() <= 0 || backendApiService.getApplicationByRootId(rootId
                        .intValue()) == null)) {
            errMsg = "购买的应用未上架，请先上架再处理.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleIdBuyHistoryParamter para = new AppleIdBuyHistoryParamter();
            para.setAppleId(appleId);
            para.setRootId(rootId);
            ApiRespWrapper<Boolean> resp = this.backendApiService.addShareAccountBuyHistory(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("errMsg", errMsg);
        rattrs.addAttribute("appleId", appleId);
        return "redirect:/auth/appleaccount/shareaccount/buyapp/list";
    }

    @RequestMapping(value = "/shareaccount/buyapp/modify.json")
    @ResponseBody
    public String modifyAppleShareAccountBuyApp(int id, Integer status) {
        String errMsg = "";
        if (StringUtils.isEmpty(errMsg)) {
            AppleShareAccountParamter para = new AppleShareAccountParamter();
            para.setId(id);
            para.setStatus(status);
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyShareAccountBuyHistory(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        return errMsg;
    }

    @RequestMapping(value = "/authorizer/list")
    public String listAppleAuthorizerAccount(@ModelAttribute("addMsg") String addMsg,
            @ModelAttribute("appleId") String appleId, AppleAuthorizerAccountParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> resp = this.backendAppleAccountApiService
                .listAppleAuthorizerAccount(para);
        List<AppleAuthorizerAccount> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        para.getPager().setTotal(total);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("types", EnumMapUtils.enumToMap(AppleAuthorizerAccountType.values()));
        model.addAttribute("appleAuthorizerAccountPrepares",
                EnumMapUtils.enumToMap(AppleAuthorizerAccountPrepare.values()));
        if (!StringUtils.isEmpty(addMsg)) {
            model.addAttribute("addMsg", addMsg);
        }
        return "appleaccount/authorizer/list.ftl";
    }

    @RequestMapping(value = "/authorizer/add", method = RequestMethod.POST)
    public String addAppleAuthorizerAccount(String appleId, String password, String name, String locale, Integer type,
            @RequestParam(required = false) CommonsMultipartFile icon, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(appleId)) {
            errMsg = "共享苹果账号Id不能为空";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(password)) {
            errMsg = "共享苹果账号密码不能为空";
        }
        String iconUrl = null;
        if (StringUtils.isEmpty(errMsg) && icon != null && !icon.isEmpty()) {
            UploadFileResp resp = bannerStorageService.saveShareAccountIcon(icon);
            if (resp != null && resp.isSaved()) {
                iconUrl = resp.getUrl();
            } else {
                if (resp == null) {
                    errMsg = "存储头像遇到未知异常.";
                } else {
                    errMsg = resp.getMessage();
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleAuthorizerAccountParameter para = new AppleAuthorizerAccountParameter();
            para.setAppleId(appleId);
            para.setName(name);
            para.setLocale(locale);
            para.setIcon(iconUrl);
            para.setPassword(password);
            para.setType(type);
            ApiRespWrapper<Boolean> resp = this.backendAppleAccountApiService.addAppleAuthorizerAccount(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("addMsg", errMsg);
        return "redirect:/auth/appleaccount/authorizer/list";
    }

    @RequestMapping(value = "/authorizer/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccount(int id, Integer status, String password, String name) {
        String errMsg = "";
        String iconUrl = null;
        if (id <= 0) {
            errMsg = "未知的授权账号记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleAuthorizerAccountParameter para = new AppleAuthorizerAccountParameter();
            para.setId(id);
            para.setStatus(status);
            para.setPassword(password);
            para.setIcon(iconUrl);
            para.setName(name);
            return this.backendAppleAccountApiService.modifyAppleAuthorizerAccount(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }

    @RequestMapping(value = "/authorizer/icon/modify")
    public String modifyAppleAuthorierAccount(int id, @RequestParam(required = false) CommonsMultipartFile icon,
            RedirectAttributes rattrs) {
        String errMsg = "";
        String iconUrl = null;
        if (icon != null && !icon.isEmpty()) {
            UploadFileResp resp = bannerStorageService.saveShareAccountIcon(icon);
            if (resp != null && resp.isSaved()) {
                iconUrl = resp.getUrl();
            } else {
                if (resp == null) {
                    errMsg = "存储头像遇到未知异常.";
                } else {
                    errMsg = resp.getMessage();
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleAuthorizerAccountParameter para = new AppleAuthorizerAccountParameter();
            para.setId(id);
            para.setIcon(iconUrl);
            ApiRespWrapper<Boolean> resp = this.backendAppleAccountApiService.modifyAppleAuthorizerAccount(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("errMsg", errMsg);
        return "redirect:/auth/appleaccount/authorizer/detail?id=" + id;
    }

    @RequestMapping(value = "/authorizer/detail")
    public String detailAppleAuthorizerAccount(int id, Model model) {
        AppleAuthorizerAccountParameter para = new AppleAuthorizerAccountParameter();
        para.setId(id);
        ApiRespWrapper<AppleAuthorizerAccount> resp = backendAppleAccountApiService.detailAppleAuthorizerAccount(para);
        if (resp != null && resp.getData() != null) {
            model.addAttribute("value", resp.getData());
        }
        return "appleaccount/authorizer/detail.ftl";
    }

    // authorice account buy app

    @RequestMapping(value = "/authorizer/buy/app/info/list")
    public String listAppleAuthorizerAccountBuyAppInfo(@ModelAttribute("errMsg") String errMsg,
            AppleAuthorizerAccountBuyAppInfoParameter para, Model model) {
        // 后台授权应用从已购列表按钮跳转后，再分页或过滤会带上地址和过滤框中两个appleId过来，特别处理下
        if (!StringUtils.isEmpty(para.getAppleId())) {
            String appleId = para.getAppleId();
            if (appleId.contains(",")) {
                // index 0为url带入的appleId,从已购列表按钮跳转后以这个为准
                appleId = appleId.split(",")[0];
                para.setAppleId(appleId);
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountBuyAppInfo>> resp = this.backendAppleAccountApiService
                .listAppleAuthorizerAccountBuyAppInfo(para);
        Map<String, AuthoAppDownBoughtInfoResp> statusRespMaps = null;
        List<AuthoAppDownBoughtInfoResp> statusResps = null;
        if (resp != null && resp.getData() != null && CollectionUtils.notEmptyAndNull(resp.getData().getResultList())) {
            para.getPager().setTotal(resp.getData().getTotalCount());
            model.addAttribute("values", resp.getData().getResultList());
            List<Integer> rootIds = new ArrayList<Integer>();
            for (AppleAuthorizerAccountBuyAppInfo history : resp.getData().getResultList()) {
                if (!rootIds.contains(history.getRootId())) {
                    rootIds.add(history.getRootId());
                }
            }
            ApiRespWrapper<Map<Integer, ApplicationSimple>> rootAppSimpleMapResp = this.backendApiService
                    .getAppSimple(rootIds);
            // TODO
            ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> infoStatusList = this.backendApiService
                    .getAuthorizerAppDownAndBuyInfoStatusList(rootIds);
            if (rootAppSimpleMapResp != null && rootAppSimpleMapResp.getData() != null) {
                Map<String, ApplicationSimple> rootSimples = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : rootAppSimpleMapResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", rootSimples);
            }
            if (infoStatusList != null && infoStatusList.getData() != null
                    && infoStatusList.getData().getResultList() != null) {
                statusRespMaps = new HashMap<String, AuthoAppDownBoughtInfoResp>();
                statusResps = infoStatusList.getData().getResultList();
                for (AuthoAppDownBoughtInfoResp resps : statusResps) {
                    statusRespMaps.put(String.valueOf(resps.getRootId()), resps);
                }
            }
        }
        AppleAuthorizerAccountParameter appleAuthorizerAccountParameter = new AppleAuthorizerAccountParameter();
        appleAuthorizerAccountParameter.setStatus(AppleAuthorizerAccount.STATUS_OK);
        appleAuthorizerAccountParameter.setSize(Integer.MAX_VALUE);
        ApiRespWrapperUtils.handleListResp("appleAuthorizerAccountList", false,
                backendAppleAccountApiService.listAppleAuthorizerAccount(appleAuthorizerAccountParameter), para, model);
        model.addAttribute("statusRespMaps", statusRespMaps);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("para", para);
        return "appleaccount/authorizer/buy/app/info/list.ftl";
    }

    @RequestMapping(value = "/authorizer/buy/app/info/add")
    public String addAppleAuthorizerAccountBuyAppInfo(String appleId, Integer rootId, Integer searchRootId,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(appleId)) {
            errMsg = "共享苹果账号Id不能为空";
        }
        if (searchRootId == null && rootId == null) {
            errMsg = "购买的应用未上架，请先上架再处理.";
        }
        if (StringUtils.isEmpty(errMsg) && searchRootId != null) {
            if (searchRootId.intValue() <= 0
                    || backendApiService.getApplicationByRootId(searchRootId.intValue()) == null) {
                errMsg = "购买的应用未上架，请先上架再处理.";
            } else {
                rootId = searchRootId;
            }
        } else {
            if (StringUtils.isEmpty(errMsg) && rootId != null) {
                if (rootId.intValue() <= 0 || backendApiService.getApplicationByRootId(rootId.intValue()) == null) {
                    errMsg = "购买的应用未上架，请先上架再处理.";
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleAuthorizerAccountBuyAppInfoParameter para = new AppleAuthorizerAccountBuyAppInfoParameter();
            para.setAppleId(appleId);
            para.setRootId(rootId);
            ApiRespWrapper<Boolean> resp = this.backendAppleAccountApiService.addAppleAuthorizerAccountBuyAppInfo(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("errMsg", errMsg);
        rattrs.addAttribute("appleId", appleId);
        return "redirect:/auth/appleaccount/authorizer/buy/app/info/list";
    }

    @RequestMapping(value = "/authorizer/buy/app/info/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountBuyAppInfo(int id, Integer status) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的授权账号授权用户设备记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleAuthorizerAccountBuyAppInfoParameter para = new AppleAuthorizerAccountBuyAppInfoParameter();
            para.setId(id);
            para.setStatus(status);
            return this.backendAppleAccountApiService.modifyAppleAuthorizerAccountBuyAppInfo(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }


    // auth device

    @RequestMapping(value = "/authorizer/auth/device/info/list")
    public String listAppleAuthorizerAccountAuthDeviceInfo(@ModelAttribute("appleAccountId") String appleAccountId,
            @ModelAttribute("imei") String imei, @ModelAttribute("udid") String udid,
            @ModelAttribute("appleId") String appleId, AppleAuthorizerAccountAuthDeviceInfoParameter para, Model model) {
        if (!StringUtils.isEmpty(imei)) {
            try {
                para.setImei(imei);
            } catch (Exception e) {
            }
        }
        if (!StringUtils.isEmpty(udid)) {
            try {
                para.setUdid(udid);
            } catch (Exception e) {
            }
        }
        if (!StringUtils.isEmpty(appleAccountId)) {
            try {
                para.setAppleAccountId(Integer.valueOf(appleAccountId));
            } catch (Exception e) {
            }
        }
        if (!StringUtils.isEmpty(appleId)) {
            AppleAuthorizerAccountParameter appleAuthorizerAccountParameter = new AppleAuthorizerAccountParameter();
            appleAuthorizerAccountParameter.setAppleId(appleId);
            ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> appleAuthorizerAccountResp = this.backendAppleAccountApiService
                    .listAppleAuthorizerAccount(appleAuthorizerAccountParameter);
            if (appleAuthorizerAccountResp != null && appleAuthorizerAccountResp.getData() != null
                    && CollectionUtils.notEmptyAndNull(appleAuthorizerAccountResp.getData().getResultList())) {
                para.setAppleAccountId(appleAuthorizerAccountResp.getData().getResultList().get(0).getId());
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountAuthDeviceInfo>> resp = this.backendAppleAccountApiService
                .listAppleAuthorizerAccountAuthDeviceInfo(para);
        List<AppleAuthorizerAccountAuthDeviceInfo> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        para.getPager().setTotal(total);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("writeStatusMap", EnumMapUtils.enumToMap(AppleShareAccountAuthroizeInfoWriteStatus.values()));
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        return "appleaccount/authorizer/auth/device/info/list.ftl";
    }

    @RequestMapping(value = "/authorizer/auth/device/info/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountAuthDeviceInfo(int id, Integer status) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的授权账号授权用户设备记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleAuthorizerAccountAuthDeviceInfoParameter para = new AppleAuthorizerAccountAuthDeviceInfoParameter();
            para.setId(id);
            para.setStatus(status);
            return this.backendAppleAccountApiService.modifyAppleAuthorizerAccountAuthDeviceInfo(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }


    // auth pc server

    @RequestMapping(value = "/authorizer/auth/pc/server/info/list")
    public String listAppleAuthorizerAccountAuthPcServerInfo(@ModelAttribute("appleAccountId") String appleAccountId,
            @ModelAttribute("pcServerId") String pcServerId, @ModelAttribute("appleId") String appleId,
            AppleAuthorizerAccountAuthPcServerInfoParameter para, Model model) {
        if (!StringUtils.isEmpty(pcServerId)) {
            try {
                para.setPcServerId(Integer.valueOf(pcServerId));
            } catch (Exception e) {
            }
        }
        if (!StringUtils.isEmpty(appleAccountId)) {
            try {
                para.setAppleAccountId(Integer.valueOf(appleAccountId));
            } catch (Exception e) {
            }
        }
        if (!StringUtils.isEmpty(appleId)) {
            AppleAuthorizerAccountParameter appleAuthorizerAccountParameter = new AppleAuthorizerAccountParameter();
            appleAuthorizerAccountParameter.setAppleId(appleId);
            ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> appleAuthorizerAccountResp = this.backendAppleAccountApiService
                    .listAppleAuthorizerAccount(appleAuthorizerAccountParameter);
            List<AppleAuthorizerAccount> appleAuthorizerAccounts = ApiRespWrapperUtils.handleListResp(
                    "authorizerAppleAccountIdMap", false, appleAuthorizerAccountResp, para, model);
            if (CollectionUtils.notEmptyAndNull(appleAuthorizerAccounts)) {
                para.setAppleAccountId(appleAuthorizerAccountResp.getData().getResultList().get(0).getId());
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountAuthPcServerInfo>> resp = this.backendAppleAccountApiService
                .listAppleAuthorizerAccountAuthPcServerInfo(para);
        List<AppleAuthorizerAccountAuthPcServerInfo> datas = ApiRespWrapperUtils.handleListResp(resp, para, model);
        List<Integer> authorizerAppleAccountIds = new ArrayList<Integer>();
        List<Integer> pcIds = new ArrayList<Integer>();
        for (AppleAuthorizerAccountAuthPcServerInfo appleAuthorizerAccountAuthPcServerInfo : datas) {
            // PC SERVER INFO
            pcIds.add(appleAuthorizerAccountAuthPcServerInfo.getPcServerId());
            if (StringUtils.isEmpty(appleId) || para.getAppleAccountId() == null
                    || para.getAppleAccountId().intValue() <= 0) {
                authorizerAppleAccountIds.add(appleAuthorizerAccountAuthPcServerInfo.getAppleAccountId());
            }
            // APPLE INFO
        }
        if (CollectionUtils.notEmptyAndNull(authorizerAppleAccountIds)) {
            ApiRespWrapperUtils.handleListResp("authorizerAppleAccountIdMap", false,
                    backendAppleAccountApiService.listAppleAuthorizerAccount(authorizerAppleAccountIds), para, model);
        }
        if (CollectionUtils.notEmptyAndNull(pcIds)) {
            ApiRespWrapperUtils.handleListResp("authorizerPcServerInfoMap", false,
                    backendAuthorizerApiService.listAuthorizerPcServerInfo(pcIds), para, model);
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        return "appleaccount/authorizer/auth/pc/server/info/list.ftl";
    }

    @RequestMapping(value = "/authorizer/auth/pc/server/info/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountAuthPcServerInfo(int id, Integer status) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的授权账号信任授权服务器记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleAuthorizerAccountAuthPcServerInfoParameter para = new AppleAuthorizerAccountAuthPcServerInfoParameter();
            para.setId(id);
            para.setStatus(status);
            return this.backendAppleAccountApiService.modifyAppleAuthorizerAccountAuthPcServerInfo(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }

    // auth virtual machine

    @RequestMapping(value = "/authorizer/auth/pc/machine/info/list")
    public String listAppleAuthorizerAccountAuthPcMachineInfo(@ModelAttribute("appleAccountId") String appleAccountId,
            @ModelAttribute("pcInfoId") String pcInfoId, @ModelAttribute("appleId") String appleId,
            AppleAuthorizerAccountAuthPcMachineInfoParameter para, Model model) {
        if (!StringUtils.isEmpty(pcInfoId)) {
            try {
                para.setPcServerId(Integer.valueOf(pcInfoId));
            } catch (Exception e) {
            }
        }
        if (!StringUtils.isEmpty(appleAccountId)) {
            try {
                para.setAppleAccountId(Integer.valueOf(appleAccountId));
            } catch (Exception e) {
            }
        }
        if (!StringUtils.isEmpty(appleId)) {
            AppleAuthorizerAccountParameter appleAuthorizerAccountParameter = new AppleAuthorizerAccountParameter();
            appleAuthorizerAccountParameter.setAppleId(appleId);
            ApiRespWrapper<ListWrapResp<AppleAuthorizerAccount>> appleAuthorizerAccountResp = this.backendAppleAccountApiService
                    .listAppleAuthorizerAccount(appleAuthorizerAccountParameter);
            if (appleAuthorizerAccountResp != null && appleAuthorizerAccountResp.getData() != null
                    && CollectionUtils.notEmptyAndNull(appleAuthorizerAccountResp.getData().getResultList())) {
                para.setAppleAccountId(appleAuthorizerAccountResp.getData().getResultList().get(0).getId());
                ApiRespWrapperUtils.handleListResp("authorizerAppleAccountIdMap", false, appleAuthorizerAccountResp,
                        para, model);
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppleAuthorizerAccountAuthPcMachineInfo>> resp = this.backendAppleAccountApiService
                .listAppleAuthorizerAccountAuthPcMachineInfo(para);
        List<AppleAuthorizerAccountAuthPcMachineInfo> datas = ApiRespWrapperUtils.handleListResp(resp, para, model);
        List<Integer> authorizerAppleAccountIds = new ArrayList<Integer>();
        List<Integer> pcServerIds = new ArrayList<Integer>();
        List<Integer> pcInfoIds = new ArrayList<Integer>();
        for (AppleAuthorizerAccountAuthPcMachineInfo appleAuthorizerAccountAuthPcMachineInfo : datas) {
            // pc server info
            pcServerIds.add(appleAuthorizerAccountAuthPcMachineInfo.getPcServerId());
            // pc machine info
            pcInfoIds.add(appleAuthorizerAccountAuthPcMachineInfo.getPcInfoId());
            if (StringUtils.isEmpty(appleId) || para.getAppleAccountId() == null
                    || para.getAppleAccountId().intValue() <= 0) {
                authorizerAppleAccountIds.add(appleAuthorizerAccountAuthPcMachineInfo.getAppleAccountId());
            }
        }
        if (CollectionUtils.notEmptyAndNull(authorizerAppleAccountIds)) {
            ApiRespWrapperUtils.handleListResp("authorizerAppleAccountIdMap", false,
                    backendAppleAccountApiService.listAppleAuthorizerAccount(authorizerAppleAccountIds), para, model);
        }
        if (CollectionUtils.notEmptyAndNull(pcServerIds)) {
            ApiRespWrapperUtils.handleListResp("authorizerPcServerInfoMap", false,
                    backendAuthorizerApiService.listAuthorizerPcServerInfo(pcServerIds), para, model);
        }
        if (CollectionUtils.notEmptyAndNull(pcInfoIds)) {
            ApiRespWrapperUtils.handleListResp("authorizerPcMachineInfoMap", false,
                    backendAuthorizerApiService.listAuthorizerPcMachineInfo(pcInfoIds), para, model);
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        return "appleaccount/authorizer/auth/pc/machine/info/list.ftl";
    }

    @RequestMapping(value = "/authorizer/auth/pc/machine/info/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyAppleAuthorizerAccountAuthPcMachineInfo(int id, Integer status) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的授权账号信任授权服务器记录!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleAuthorizerAccountAuthPcMachineInfoParameter para = new AppleAuthorizerAccountAuthPcMachineInfoParameter();
            para.setId(id);
            para.setStatus(status);
            return this.backendAppleAccountApiService.modifyAppleAuthorizerAccountAuthPcMachineInfo(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }

    // pcsuite bind apple id info page
    @RequestMapping(value = "/pcsuite/bind/apple/id/info/list")
    public String listPcSuiteBindAppleIdInfo(PcSuiteBindAppleIdInfoParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteBindAppleIdInfo>> resp = this.backendApiService
                .listPcSuiteBindAppleIdInfo(para);
        List<PcSuiteBindAppleIdInfo> values = null;
        long total = 0;
        if (resp != null && resp.getData() != null) {
            values = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        para.getPager().setTotal(total);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        return "account/pcsuite/info/bind/list.ftl";
    }
}
