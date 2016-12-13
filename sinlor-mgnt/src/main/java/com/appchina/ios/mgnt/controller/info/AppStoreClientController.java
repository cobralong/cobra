package com.appchina.ios.mgnt.controller.info;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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

import com.appchina.ios.core.dto.info.AppStoreClientAuditInfo;
import com.appchina.ios.core.dto.info.AppStoreClientAuditSwitchIpConf;
import com.appchina.ios.core.dto.info.AppStoreClientConf;
import com.appchina.ios.core.dto.info.AppStoreClientDynamicCode;
import com.appchina.ios.core.dto.info.AppStoreClientInfo;
import com.appchina.ios.core.dto.info.AppStoreClientShareInfo;
import com.appchina.ios.core.dto.info.AppStoreUserCenterConfigure;
import com.appchina.ios.core.dto.info.AppStoreUserCenterTipInfo;
import com.appchina.ios.core.dto.info.DefaultFlag;
import com.appchina.ios.core.dto.info.MarketFlag;
import com.appchina.ios.core.dto.info.ShowInType;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.core.utils.ParameterUtils;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.StoreClientConfParameter;
import com.appchina.ios.mgnt.controller.model.StoreClientDynamicCodeParameter;
import com.appchina.ios.mgnt.controller.model.UploadFileResp;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientAuditInfoParamter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientAuditSwitchIpConfParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientInfoParamter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreClientShareInfoParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreUserCenterConfigureParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreUserCenterTipInfoParameter;
import com.appchina.ios.mgnt.controller.model.info.BundleIdTimedStatusStartSizeParameter;
import com.appchina.ios.mgnt.service.AppStoreClientApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.service.impl.StoreCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.StartSizeParameter;
import com.appchina.ios.web.utils.ApiRespWrapperUtils;
import com.appchina.ios.web.utils.CollectionUtils;

@Controller
@RequestMapping(value = "/auth/store/client/*")
public class AppStoreClientController {
    private static final Logger log = Logger.getLogger(AppStoreClientController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private AppStoreClientApiService appStoreClientApiService;
    @Autowired
    private StoreCommonParameterService storeCommonParameterService;
    @Autowired
    private BannerStorageService bannerStorageService;

    @RequestMapping("/listclientversions.json")
    @ResponseBody
    protected JsonResult<List<String>> listClientVersions(String bundleId) {
        ApiRespWrapper<List<String>> versionResp = iosStoreApiService.listAppStoreClientAllVersions(bundleId);
        if (versionResp != null && !CollectionUtils.emptyOrNull(versionResp.getData())) {
            return new JsonResult<List<String>>(true, "", versionResp.getData());
        }
        return new JsonResult<List<String>>(false, "", null);
    }

    @RequestMapping(value = "/audithistory")
    public String auditHistory(@ModelAttribute("bundleId") String bundleId, @ModelAttribute("errMsg") String errMsg,
            AppStoreClientAuditInfoParamter para, Model model) {
        if (para == null) {
            para = new AppStoreClientAuditInfoParamter();
        }
        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreClientAuditInfo>> resp = this.iosStoreApiService
                .listAppStoreClientAuditInfo(para);
        List<AppStoreClientAuditInfo> datas;
        long total = 0;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        storeCommonParameterService.addBundles("bundleIds", model);
        storeCommonParameterService.addBundles("pureBundleIds", model, true);
        para.getPager().setTotal(total);
        model.addAttribute("values", datas);
        model.addAttribute("audits", AppStoreClientAuditInfo.AUDITS);

        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/audit/history.ftl";
    }

    @RequestMapping(value = "/auditdetail")
    public String auditDetail(@ModelAttribute("errMsg") String errMsg, int id, Model model) {
        ApiRespWrapper<AppStoreClientAuditInfo> resp = this.iosStoreApiService.getAppStoreClientAuditInfo(id);
        AppStoreClientAuditInfo data;
        if (resp == null || resp.getData() == null) {
            data = null;
        } else {
            data = resp.getData();
        }
        model.addAttribute("data", data);
        model.addAttribute("audits", AppStoreClientAuditInfo.AUDITS);
        model.addAttribute("errMsg", errMsg);
        return "store/audit/detail.ftl";
    }

    @RequestMapping(value = "/addauditinfo")
    public String modifyAuditInfo(AppStoreClientAuditInfoParamter para, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(para.getClientVersion()) || StringUtils.isEmpty(para.getBundleId())) {
            errMsg = "未知审核数据结构";
        }
        ApiRespWrapper<Boolean> resp = null;
        if (StringUtils.isEmpty(errMsg)) {
            resp = this.iosStoreApiService.modifyAppStoreClientAuditInfo(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            rattrs.addFlashAttribute("errMsg", "添加成功");
        } else {
            rattrs.addFlashAttribute("errMsg", errMsg);
        }
        rattrs.addFlashAttribute("bundleId", para.getBundleId());
        return "redirect:/auth/store/client/audithistory";
    }

    @RequestMapping(value = "/modifyauditinfo")
    public String modifyAuditInfo(Integer id, String clientVersion, Integer auditStatus, String auditMessage,
            String bundleId, String appStoreUrl, RedirectAttributes rattrs) {
        String errMsg = "";
        AppStoreClientAuditInfoParamter para = null;
        boolean add = false;
        if (id == null || id.intValue() <= 0) {
            if (StringUtils.isEmpty(clientVersion) || StringUtils.isEmpty(bundleId)) {
                errMsg = "未知审核数据结构";
            }
            if (StringUtils.isEmpty(errMsg)) {
                para = new AppStoreClientAuditInfoParamter(clientVersion, bundleId);
            }
            add = true;
        } else {
            if (!AppStoreClientAuditInfo.AUDITS.containsKey(auditStatus.toString())) {
                errMsg = "未知审核数据结构";
            }
            if (StringUtils.isEmpty(errMsg)) {
                para = new AppStoreClientAuditInfoParamter(id, auditStatus, auditMessage, appStoreUrl);
            }
        }
        ApiRespWrapper<Boolean> resp = null;
        if (StringUtils.isEmpty(errMsg)) {
            resp = this.iosStoreApiService.modifyAppStoreClientAuditInfo(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            rattrs.addFlashAttribute("errMsg", "修改成功");
        } else {
            rattrs.addFlashAttribute("errMsg", errMsg);
        }
        rattrs.addAttribute("id", id);
        if (add) {
            return "redirect:/auth/store/client/audithistory";
        } else {
            return "redirect:/auth/store/client/auditdetail";
        }
    }

    @RequestMapping(value = "/conf")
    public String conf(@ModelAttribute("bundleId") String bundleId, @ModelAttribute("errMsg") String errMsg,
            StoreClientConfParameter para, Model model) {
        if (para == null) {
            para = new StoreClientConfParameter();
        }

        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreClientConf>> resp = this.iosStoreApiService.getClientConfs(para);
        List<AppStoreClientConf> datas;
        long total = 0;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        para.getPager().setTotal(total);
        storeCommonParameterService.addBundles("bundleIds", model);
        // 因为第一个是ALL,所以版本的第一个也为ALL
        // 所有的应用的所有版本上下架情况 可优化
        AppStoreClientAuditInfoParamter innerPara = new AppStoreClientAuditInfoParamter();
        innerPara.getPager().setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<AppStoreClientAuditInfo>> bundleIdAuditInfoResp = iosStoreApiService
                .listAppStoreClientAuditInfo(innerPara);
        if (bundleIdAuditInfoResp != null && bundleIdAuditInfoResp.getData() != null
                && !CollectionUtils.emptyOrNull(bundleIdAuditInfoResp.getData().getResultList())) {
            model.addAttribute("bundleIdVersionList", bundleIdAuditInfoResp.getData().getResultList());
        }
        model.addAttribute("btnItems", AppStoreClientConf.BUTTON_ITEM_MAP);
        model.addAttribute("homePageModels", AppStoreClientConf.HOMEPAGEMODEL_MAP);
        model.addAttribute("audits", AppStoreClientAuditInfo.AUDITS);
        model.addAttribute("models", AppStoreClientConf.APP_MODEL_MAP);
        model.addAttribute("values", datas);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/conf/conf.ftl";
    }

    @RequestMapping(value = "/modifyconf")
    @ResponseBody
    public String modifyconf(int id, Integer homeModel, Integer appModel, Integer index, String shareUrl,
            String webClipDesc, String webClipUrl, String browserUrl, Integer weight, Integer localAuthInstallSupport,
            Integer promoteAppSupport) {
        if (homeModel != null && homeModel != AppStoreClientConf.HOMEPAGE_APP
                && homeModel != AppStoreClientConf.HOMEPAGE_INFO) {
            return "未知应用类型";
        }
        StoreClientConfParameter para = new StoreClientConfParameter();
        para.setHomeModel(homeModel);
        para.setIndex(index);
        para.setId(id);
        para.setShareUrl(shareUrl);
        para.setLocalAuthInstallSupport(localAuthInstallSupport);
        para.setPromoteAppSupport(promoteAppSupport);
        para.setWebClipDesc(webClipDesc);
        para.setWebClipUrl(webClipUrl);
        para.setBrowserUrl(browserUrl);
        para.setAppModel(appModel);
        para.setWeight(weight);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyClientConf(para);
        if (resp == null) {
            return "未知错误";
        } else if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/addconf")
    public String addconf(String bundleId, String clientVersion, int appModel, int index, int homeModel,
            int localAuthInstallSupport, int promoteAppSupport, String shareUrl, String browserUrl, String webClipUrl,
            String webClipDesc, Integer weight, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(bundleId)) {
            errMsg = "未知应用客户端";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(clientVersion)) {
            errMsg = "未知客户端版本";
        }
        if (StringUtils.isEmpty(errMsg) && (!AppStoreClientConf.legalShowModel(homeModel))) {
            errMsg = "未知客户端首页样式";
        }
        if (StringUtils.isEmpty(errMsg) && (!AppStoreClientConf.legalAppModel(appModel))) {
            errMsg = "未知应用类型";
        }
        if (StringUtils.isEmpty(errMsg)) {
            StoreClientConfParameter para = new StoreClientConfParameter();
            para.setBundleId(bundleId);
            para.setHomeModel(homeModel);
            para.setIndex(index);
            para.setShareUrl(shareUrl);
            para.setLocalAuthInstallSupport(localAuthInstallSupport);
            para.setPromoteAppSupport(promoteAppSupport);
            para.setClientVersion(clientVersion);
            para.setAppModel(appModel);
            para.setWebClipUrl(webClipUrl);
            para.setWebClipDesc(webClipDesc);
            if (weight == null) {
                weight = 0;
            }
            para.setWeight(weight);
            para.setBrowserUrl(browserUrl);
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addClientConf(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("bundleId", bundleId);
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/client/conf";
    }

    @RequestMapping(value = "/history")
    public String history(@ModelAttribute("errMsg") String errMsg, StartSizeParameter para, Model model) {
        if (para == null) {
            para = new StartSizeParameter();
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreClientInfo>> resp = this.iosStoreApiService.listAppStoreClientInfo(para);
        List<AppStoreClientInfo> datas;
        long total = 0;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        para.getPager().setTotal(total);
        model.addAttribute("values", datas);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/client/info/list.ftl";
    }

    @RequestMapping(value = "/info/add")
    public String addInfo(String bundleId, String name, RedirectAttributes rattrs) {
        String errMsg = "";
        AppStoreClientInfoParamter para = null;

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(bundleId)) {
            errMsg = "未知上架产品数据结构";
        }
        if (StringUtils.isEmpty(errMsg)) {
            para = new AppStoreClientInfoParamter();
            para.setBundleId(bundleId);
            para.setName(name);
        }

        ApiRespWrapper<Boolean> resp = null;
        if (StringUtils.isEmpty(errMsg)) {
            resp = this.iosStoreApiService.modifyAppStoreClientInfo(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            rattrs.addFlashAttribute("errMsg", "修改成功");
        } else {
            rattrs.addFlashAttribute("errMsg", errMsg);
        }
        return "redirect:/auth/store/client/history";
    }

    @RequestMapping(value = "/info/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> addInfo(int id, Integer status, String name) {
        String errMsg = "";
        AppStoreClientInfoParamter para = null;

        if (id <= 0) {
            errMsg = "未知上架产品Id";
        }
        if (StringUtils.isEmpty(errMsg)) {
            para = new AppStoreClientInfoParamter();
            para.setId(id);
            para.setStatus(status);
            para.setName(name);
        }
        if (StringUtils.isEmpty(errMsg)) {
            return this.iosStoreApiService.modifyAppStoreClientInfo(para);
        }
        return new ApiRespWrapper<Boolean>(-1, errMsg, false);
    }

    @RequestMapping(value = "/adddynamiccode", method = RequestMethod.POST)
    public String adddynamiccode(String bundleId, String clientVersion, String name,
            @RequestParam(required = false) CommonsMultipartFile codes, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(name) || codes == null || codes.getSize() == 0) {
            errMsg = "未知代码数据";
        }
        UploadFileResp uploadFileResp = null;
        if (StringUtils.isEmpty(errMsg)) {
            try {
                uploadFileResp = bannerStorageService.saveDynamicCode(UUID.randomUUID().variant(), codes);
            } catch (Exception e) {
                errMsg = "存储文件失败, Errmsg:" + e.getMessage();
                log.error("存储文件失败", e);
            }
        }
        if (uploadFileResp == null) {
            errMsg = "存储文件失败.";
        }
        if (uploadFileResp != null && !uploadFileResp.isSaved()) {
            errMsg = uploadFileResp.getMessage();
        }
        if (StringUtils.isEmpty(errMsg) && (uploadFileResp != null && uploadFileResp.isSaved())) {
            StoreClientDynamicCodeParameter para = new StoreClientDynamicCodeParameter();
            para.setBundleId(bundleId);
            para.setClientVersion(clientVersion);
            para.setMd5(uploadFileResp.getMd5());
            para.setName(name);
            para.setUrl(uploadFileResp.getUrl());
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addAppStoreClientDynamicCode(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("bundleId", bundleId);
        rattrs.addFlashAttribute("clientVersion", clientVersion);
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/client/listdynamiccode";
    }

    @RequestMapping(value = "/listdynamiccode")
    public String listDynamicCode(@ModelAttribute("bundleId") String bundleId,
            @ModelAttribute("clientVersion") String clientVersion, @ModelAttribute("errMsg") String errMsg,
            StoreClientDynamicCodeParameter para, Model model) {
        if (para == null) {
            para = new StoreClientDynamicCodeParameter();
        }
        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        if (!StringUtils.isEmpty(clientVersion)) {
            para.setClientVersion(clientVersion);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreClientDynamicCode>> resp = this.iosStoreApiService
                .listAppStoreClientDynamicCode(para);
        List<AppStoreClientDynamicCode> datas;
        long total = 0;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        storeCommonParameterService.addBundles("bundleIds", model);
        storeCommonParameterService.addBundles("pureBundleIds", model, true);
        storeCommonParameterService.addBundleIdAuditInfo("bundleIdVersionList", model);
        para.getPager().setTotal(total);
        model.addAttribute("values", datas);
        model.addAttribute("audits", AppStoreClientAuditInfo.AUDITS);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/conf/dynamiccode.ftl";
    }

    @RequestMapping(value = "/modifydynamiccodestatus.json")
    @ResponseBody
    public String modifydynamiccode(int id, int status) {
        if (id <= 0
                || !(status == AppStoreClientDynamicCode.STATUS_OK || status == AppStoreClientDynamicCode.STATUS_DEL)) {
            return "未知参数.";
        }
        StoreClientDynamicCodeParameter para = new StoreClientDynamicCodeParameter();
        para.setId(id);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppStoreClientDynamicCode(para);
        if (resp == null) {
            return "未知错误";
        } else if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping("/usercenter/configure/list")
    protected String listAppStoreUserCenterConfigure(@ModelAttribute("bundleId") String bundleId,
            @ModelAttribute("errMsg") String errMsg, BundleIdTimedStatusStartSizeParameter para, Model model) {
        if (para == null) {
            para = new BundleIdTimedStatusStartSizeParameter();
        }
        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreUserCenterConfigure>> resp = this.iosStoreApiService
                .listAppStoreUserCenterConfigure(para);
        List<AppStoreUserCenterConfigure> datas;
        long total = 0;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        storeCommonParameterService.addBundles("bundleIds", model);
        storeCommonParameterService.addBundles("pureBundleIds", model, true);
        storeCommonParameterService.addBundleIdAuditInfo("bundleIdVersionList", model);
        storeCommonParameterService.addStatus("statuses", model);
        para.getPager().setTotal(total);
        model.addAttribute("values", datas);
        model.addAttribute("marketFlags", EnumMapUtils.enumToMap(MarketFlag.values(), false));
        model.addAttribute("defaultFlags", EnumMapUtils.enumToMap(DefaultFlag.values(), false));
        model.addAttribute("audits", AppStoreClientAuditInfo.AUDITS);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/client/usercenter/configure/list.ftl";
    }

    @RequestMapping("/usercenter/configure/add")
    protected String addAppStoreUserCenterConfigure(String bundleId, String clientVersion, int marketFlag,
            int defaultFlag, @RequestParam(required = false) CommonsMultipartFile icon, String title, String buttom,
            Model model, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(bundleId)) {
            errMsg = "错误的包名";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(title)) {
            errMsg = "错误的命名";
        }
        if (StringUtils.isEmpty(errMsg) && (icon == null || icon.isEmpty())) {
            errMsg = "请提供图标";
        }
        UploadFileResp uploadFileResp = null;
        if (StringUtils.isEmpty(errMsg) && icon != null && !icon.isEmpty()) {
            try {
                uploadFileResp = bannerStorageService.saveStoreIcon(icon);
            } catch (Exception e) {
                errMsg = "存储文件失败, Errmsg:" + e.getMessage();
                log.error("存储文件失败", e);
            }
        }
        if (uploadFileResp == null) {
            errMsg = "存储文件失败.";
        }
        if (uploadFileResp != null && !uploadFileResp.isSaved()) {
            errMsg = uploadFileResp.getMessage();
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppStoreUserCenterConfigureParameter configure = new AppStoreUserCenterConfigureParameter();
            configure.setBundleId(bundleId);
            configure.setButtom(buttom);
            configure.setClientVersion(clientVersion);
            configure.setDefaultFlag(defaultFlag);
            if (uploadFileResp != null && uploadFileResp.isSaved()) {
                configure.setIconUrl(uploadFileResp.getUrl());
            }
            configure.setMarketFlag(marketFlag);
            configure.setTitle(title);
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addAppStoreUserCenterConfigure(configure);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("bundleId", bundleId);
        return "redirect:/auth/store/client/usercenter/configure/list";
    }

    @RequestMapping("/usercenter/configure/modify.json")
    @ResponseBody
    protected String modifyAppStoreUserCenterConfigure(int id, String title, String buttom, Integer status) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "错误的Id。";
        }
        AppStoreUserCenterConfigureParameter configure = new AppStoreUserCenterConfigureParameter();
        configure.setId(id);
        configure.setTitle(title);
        configure.setButtom(buttom);
        configure.setStatus(status);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppStoreUserCenterConfigure(configure);
        if (resp == null) {
            errMsg = "未知错误";
        } else if (resp.getData() == null) {
            errMsg = resp.getMessage();
        } else {
            if (!resp.getData()) {
                errMsg = resp.getMessage();
            }
        }
        return errMsg;
    }


    // user center tip info
    @RequestMapping("/usercenter/tipinfo/list")
    protected String listAppStoreUserCenterTipInfo(@ModelAttribute("bundleId") String bundleId,
            @ModelAttribute("errMsg") String errMsg, BundleIdTimedStatusStartSizeParameter para, Model model) {
        if (para == null) {
            para = new BundleIdTimedStatusStartSizeParameter();
        }
        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreUserCenterTipInfo>> resp = this.iosStoreApiService
                .listAppStoreUserCenterTipInfo(para);
        List<AppStoreUserCenterTipInfo> datas;
        long total = 0;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
        }
        storeCommonParameterService.addBundles("bundleIds", model);
        storeCommonParameterService.addBundles("pureBundleIds", model, true);
        storeCommonParameterService.addBundleIdAuditInfo("bundleIdVersionList", model);
        storeCommonParameterService.addStatus("statuses", model);
        para.getPager().setTotal(total);
        model.addAttribute("values", datas);
        model.addAttribute("marketFlags", EnumMapUtils.enumToMap(MarketFlag.values(), false));
        model.addAttribute("audits", AppStoreClientAuditInfo.AUDITS);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/client/usercenter/tipinfo/list.ftl";
    }

    @RequestMapping("/usercenter/tipinfo/add")
    protected String addAppStoreUserCenterTipInfo(String bundleId, int marketFlag, int articleId,
            @RequestParam(required = false) CommonsMultipartFile icon, String iconUrl, String title,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(bundleId)) {
            errMsg = "错误的包名";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(title)) {
            errMsg = "错误的命名";
        }
        UploadFileResp uploadFileResp = null;
        if (StringUtils.isEmpty(errMsg) && icon != null && !icon.isEmpty()) {
            try {
                uploadFileResp = bannerStorageService.saveStoreIcon(icon);
            } catch (Exception e) {
                errMsg = "存储文件失败, Errmsg:" + e.getMessage();
                log.error("存储文件失败", e);
            }
        }
        if (uploadFileResp == null) {
            errMsg = "存储文件失败.";
        }
        if (uploadFileResp != null && !uploadFileResp.isSaved()) {
            errMsg = uploadFileResp.getMessage();
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppStoreUserCenterTipInfoParameter tipInfo = new AppStoreUserCenterTipInfoParameter();
            tipInfo.setArticleId(articleId);
            tipInfo.setBundleId(bundleId);
            if (uploadFileResp != null && uploadFileResp.isSaved()) {
                tipInfo.setIconUrl(uploadFileResp.getUrl());
            } else {
                tipInfo.setIconUrl(iconUrl);
            }
            tipInfo.setMarketFlag(marketFlag);
            tipInfo.setTitle(title);
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addAppStoreUserCenterTipInfo(tipInfo);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("bundleId", bundleId);
        return "redirect:/auth/store/client/usercenter/tipinfo/list";
    }

    @RequestMapping("/usercenter/tipinfo/modify.json")
    @ResponseBody
    protected String modifyAppStoreUserCenterTipInfo(int id, String title, Integer status) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "错误的Id.";
        }
        AppStoreUserCenterTipInfoParameter tipInfo = new AppStoreUserCenterTipInfoParameter();
        tipInfo.setId(id);
        tipInfo.setTitle(title);
        tipInfo.setStatus(status);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppStoreUserCenterTipInfo(tipInfo);
        if (resp == null) {
            errMsg = "未知错误";
        } else if (resp.getData() == null) {
            errMsg = resp.getMessage();
        } else {
            if (!resp.getData()) {
                errMsg = resp.getMessage();
            }
        }
        return errMsg;
    }

    // audit switch ip conf
    @RequestMapping(value = "/audit/switch/ip/conf/list")
    protected String listAppStoreClientAuditSwitchIpConf(@ModelAttribute("bundleId") String bundleId,
            @ModelAttribute("addMsg") String addMsg, AppStoreClientAuditSwitchIpConfParameter para, Model model) {
        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreClientAuditSwitchIpConf>> resp = appStoreClientApiService
                .listAppStoreClientAuditSwitchIpConf(para);
        ApiRespWrapperUtils.handleListResp(resp, para, model);
        storeCommonParameterService.addBundles("pureBundleIdMap", model, true);
        storeCommonParameterService.addBundles("bundleIdMap", model);
        if (!StringUtils.isEmpty(addMsg)) {
            model.addAttribute("addMsg", addMsg);
        }
        return "store/client/audit/switch/ip/conf/list.ftl";
    }

    @RequestMapping(value = "/audit/switch/ip/conf/add")
    protected String addAppStoreClientAuditSwitchIpConf(String bundleId, String ip, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(ip) || StringUtils.isEmpty(bundleId)) {
            errMsg = "错误的参数请求";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                AppStoreClientAuditSwitchIpConfParameter g = new AppStoreClientAuditSwitchIpConfParameter();
                g.setIp(ip);
                g.setBundleId(bundleId);
                ApiRespWrapper<Boolean> resp = appStoreClientApiService.addAppStoreClientAuditSwitchIpConf(g);
                errMsg = ApiRespWrapperUtils.handleBooleanResp(resp);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        rattrs.addFlashAttribute("addMsg", errMsg);
        rattrs.addFlashAttribute("bundleId", bundleId);
        return "redirect:/auth/store/client/audit/switch/ip/conf/list";
    }

    @RequestMapping(value = "/audit/switch/ip/conf/modify.json")
    @ResponseBody
    protected String modifyAppStoreClientAuditSwitchIpConf(int id, int status) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "错误的参数请求---id";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppStoreClientAuditSwitchIpConfParameter g = new AppStoreClientAuditSwitchIpConfParameter();
            g.setId(id);
            g.setStatus(status);
            try {
                ApiRespWrapper<Boolean> resp = appStoreClientApiService.modifyAppStoreClientAuditSwitchIpConf(g);
                errMsg = ApiRespWrapperUtils.handleBooleanResp(resp);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        return errMsg;
    }

    // share iinfo
    @RequestMapping(value = "/share/info/list")
    protected String listAppStoreClientShareInfo(@ModelAttribute("bundleId") String bundleId,
            @ModelAttribute("addMsg") String addMsg, AppStoreClientShareInfoParameter para, Model model) {
        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreClientShareInfo>> resp = appStoreClientApiService
                .listAppStoreClientShareInfo(para);
        ApiRespWrapperUtils.handleListResp(resp, para, model);
        storeCommonParameterService.addBundles("pureBundleIdMap", model, true);
        storeCommonParameterService.addBundles("bundleIdMap", model);
        model.addAttribute("marketFlags", EnumMapUtils.enumToMap(MarketFlag.values(), false));
        if (!StringUtils.isEmpty(addMsg)) {
            model.addAttribute("addMsg", addMsg);
        }
        model.addAttribute("showTypes", EnumMapUtils.enumToMap(ShowInType.values(), true));
        return "store/client/share/info/list.ftl";
    }

    @RequestMapping(value = "/share/info/detail")
    protected String detailAppStoreClientShareInfo(int id, Model model) {
        if (id <= 0) {
            return "store/client/share/info/detail.ftl";
        }
        ApiRespWrapper<AppStoreClientShareInfo> resp = appStoreClientApiService.detailAppStoreClientShareInfo(id);
        ApiRespWrapperUtils.handleValueResp(resp, null, model);
        model.addAttribute("showInTypes", EnumMapUtils.enumToMap(MarketFlag.values(), false));
        return "store/client/share/info/detail.ftl";
    }

    @RequestMapping(value = "/share/info/modify/icon")
    public String modifyAppleShareAccount(int id, @RequestParam(required = false) CommonsMultipartFile icon,
            RedirectAttributes rattrs) {
        String errMsg = "";
        String iconUrl = null;
        if (icon != null && !icon.isEmpty()) {
            UploadFileResp resp = bannerStorageService.saveStoreIcon(icon);
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
            AppStoreClientShareInfoParameter para = new AppStoreClientShareInfoParameter();
            para.setId(id);
            para.setIcon(iconUrl);
            ApiRespWrapper<Boolean> resp = this.appStoreClientApiService.modifyAppStoreClientShareInfo(para);
            if (resp == null) {
                errMsg = "未知网络错误";
            } else if (BooleanUtils.isFalse(resp.getData())) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误.Errcode:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addAttribute("errMsg", errMsg);
        return "redirect:/auth/store/client/share/info/detail?id=" + id;
    }

    @RequestMapping(value = "/share/info/add")
    String addAppStoreClientShareInfo(String bundleId, Integer showInType, String title, String content, String url,
            @RequestParam(required = false) CommonsMultipartFile icon, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(bundleId) || StringUtils.isEmpty(url)
                || StringUtils.isEmpty(content) || icon == null || icon.isEmpty()) {
            errMsg = "错误的参数请求";
        }
        UploadFileResp uploadFileResp = null;
        if (StringUtils.isEmpty(errMsg) && icon != null && !icon.isEmpty()) {
            try {
                uploadFileResp = bannerStorageService.saveStoreIcon(icon);
            } catch (Exception e) {
                errMsg = "存储文件失败, Errmsg:" + e.getMessage();
                log.error("存储文件失败", e);
            }
        }
        if (StringUtils.isEmpty(errMsg) && (uploadFileResp == null || !uploadFileResp.isSaved())) {
            errMsg = "存储文件失败.Errmsg:" + uploadFileResp.getMessage();
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                AppStoreClientShareInfoParameter g = new AppStoreClientShareInfoParameter();
                g.setBundleId(bundleId);
                g.setContent(content);
                if (uploadFileResp != null && uploadFileResp.isSaved()) {
                    g.setIcon(uploadFileResp.getUrl());
                }
                showInType = ParameterUtils.formatStatus(showInType);
                g.setShowInType(showInType);
                g.setTitle(title);
                g.setUrl(url);
                g.setBundleId(bundleId);
                ApiRespWrapper<Boolean> resp = appStoreClientApiService.addAppStoreClientShareInfo(g);
                errMsg = ApiRespWrapperUtils.handleBooleanResp(resp);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        rattrs.addFlashAttribute("addMsg", errMsg);
        rattrs.addFlashAttribute("bundleId", bundleId);
        return "redirect:/auth/store/client/share/info/list";
    }

    @RequestMapping(value = "/share/info/modify.json")
    @ResponseBody
    protected ApiRespWrapper<Boolean> modifyAppStoreClientShareInfo(int id, Integer status, String title, String url,
            String content) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "错误的参数请求---id";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppStoreClientShareInfoParameter g = new AppStoreClientShareInfoParameter();
            g.setId(id);
            g.setStatus(status);
            g.setTitle(title);
            g.setContent(content);
            g.setUrl(url);
            try {
                return appStoreClientApiService.modifyAppStoreClientShareInfo(g);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        return new ApiRespWrapper<Boolean>(0, errMsg, false);
    }

}
