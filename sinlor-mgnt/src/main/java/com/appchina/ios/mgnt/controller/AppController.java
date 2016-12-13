package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.cahe.model.app.DownloadTypeInfo;
import com.appchina.ios.core.cahe.model.app.SearchResp;
import com.appchina.ios.core.crawler.model.AppStoreApplication;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.crawler.model.CommitClientApiApplication;
import com.appchina.ios.core.crawler.model.CommitClientApiModel;
import com.appchina.ios.core.crawler.model.CrawlerApplication;
import com.appchina.ios.core.crawler.model.ImgRes;
import com.appchina.ios.core.crawler.model.OnlineAppResult;
import com.appchina.ios.core.dto.app.AppBanned;
import com.appchina.ios.core.dto.app.AppEmphasisInfo;
import com.appchina.ios.core.dto.app.AppType;
import com.appchina.ios.core.dto.app.Application;
import com.appchina.ios.core.dto.app.ApplicationItunesImgRes;
import com.appchina.ios.core.dto.app.Category;
import com.appchina.ios.core.dto.app.DownTypeIntervention;
import com.appchina.ios.core.dto.app.RootApplication;
import com.appchina.ios.core.dto.app.UrgentOnlineRecord;
import com.appchina.ios.core.dto.online.AppLanguage;
import com.appchina.ios.core.dto.online.AppOnlineRecord;
import com.appchina.ios.core.dto.online.AppOnlineRecordStat;
import com.appchina.ios.core.dto.online.AppOnlineType;
import com.appchina.ios.core.dto.online.OnlineStatus;
import com.appchina.ios.core.service.LocalResService;
import com.appchina.ios.core.service.app.UrgentOnlineRecordService;
import com.appchina.ios.core.utils.AppStoreApplicationUtils;
import com.appchina.ios.core.utils.ApplicationItunesImgResUtils;
import com.appchina.ios.core.utils.ParameterUtils;
import com.appchina.ios.mgnt.controller.model.AppBannedParameter;
import com.appchina.ios.mgnt.controller.model.AppBannedResult;
import com.appchina.ios.mgnt.controller.model.AppEmphasisParameter;
import com.appchina.ios.mgnt.controller.model.AppTitleSearchParameter;
import com.appchina.ios.mgnt.controller.model.AppUploadRecordParameter;
import com.appchina.ios.mgnt.controller.model.AppUploadStatParameter;
import com.appchina.ios.mgnt.controller.model.ApplicationModifyParameter;
import com.appchina.ios.mgnt.controller.model.ApplicationParameter;
import com.appchina.ios.mgnt.controller.model.CrawlerApplicationParameter;
import com.appchina.ios.mgnt.controller.model.DownTypeInterventionParameter;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.RootApplicationParameter;
import com.appchina.ios.mgnt.controller.model.UrgentOnlineRecordParameter;
import com.appchina.ios.mgnt.dto.User;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.DaemonApiService;
import com.appchina.ios.mgnt.service.OnlineApiService;
import com.appchina.ios.mgnt.service.UsersService;
import com.appchina.ios.mgnt.service.impl.AuthoAppDownBoughtInfoService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;

@Controller
@RequestMapping(value = "/auth/app/*")
public class AppController {
    private static final Logger log = Logger.getLogger(AppController.class);
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private DaemonApiService daemonApiService;
    @Autowired
    private OnlineApiService onlineApiService;
    @Autowired
    private UrgentOnlineRecordService urgentOnlineRecordService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private AuthoAppDownBoughtInfoService<Application> applicationAuthoAppDownBoughtInfoService;
    @Autowired
    private AuthoAppDownBoughtInfoService<ApplicationSimple> applicationSimpleAuthoAppDownBoughtInfoService;
    @Autowired
    private AuthoAppDownBoughtInfoService<UrgentOnlineRecord> urgentOnlineRecordAuthoAppDownBoughtInfoService;
    @Autowired
    private LocalResService localResService;

    @RequestMapping(value = "/search")
    @ResponseBody
    public JsonResult<Application> search(int id, Model model) {
        ApiRespWrapper<Application> searchResp = this.backendApiService.getApplicationByRootId(id);
        return new JsonResult<Application>(true, "ok", searchResp.getData());
    }

    @RequestMapping(value = "/listonline")
    public String listonline(@ModelAttribute("errMsg") String errMsg, UrgentOnlineRecordParameter para, Model model) {
        para.transfer();
        List<UrgentOnlineRecord> values = this.urgentOnlineRecordService.list(para.getItemId(), para.getStart(),
                para.getSize());
        if (para.getItemId() == null) {
            para.getPager().setTotal(this.urgentOnlineRecordService.count());
        }
        List<Integer> rootIds = initUrgentOnlineRootIds(values);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimple = this.backendApiService.getAppSimple(rootIds);
        if (appSimple != null && appSimple.getData() != null) {
            Map<String, ApplicationSimple> simples = new HashMap<String, ApplicationSimple>();
            for (Entry<Integer, ApplicationSimple> entry : appSimple.getData().entrySet()) {
                simples.put(entry.getKey().toString(), entry.getValue());
            }
            model.addAttribute("simples", simples);
        }
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        try {
            model.addAttribute("rootIdBoughtMap",
                    urgentOnlineRecordAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
        } catch (Exception e) {
            log.error("gain UrgentOnlineRecord of rootId authority state failure!" + e.getMessage(), e);
        }
        return "app/online.ftl";
    }

    @RequestMapping(value = "/listonlinenew")
    public String listOnlineNew(@ModelAttribute("errMsg") String errMsg, Model model) {
        model.addAttribute("errMsg", errMsg);
        return "app/onlinenew.ftl";
    }

    @RequestMapping(value = "/addnewapp")
    private String onlineCommitApplication(CommitClientApiModel model,
            @RequestParam(required = false) List<CommonsMultipartFile> icon,
            @RequestParam(required = false) List<CommonsMultipartFile> iphone,
            @RequestParam(required = false) List<CommonsMultipartFile> ipad, RedirectAttributes rattrs) {
        // checkOnlineLicense();
        AppStoreApplication data = jsonFormatCommitApiModel(model, icon, iphone, ipad);
        data.setOnlineLanguage(AppLanguage.cn);
        log.info("Post data " + model.getId() + " to server for online.");
        ApiRespWrapper<OnlineAppResult> resp = daemonApiService.onlineCommitClientApi(data);;
        log.info("Finish post data to server for online. Id:" + data.getId() + ". Result:" + resp);
        String errMsg = "";
        if (resp.getData() == null || !resp.getData().isSuccess()) {
            errMsg = resp.getData().getMessage();
        } else {
            errMsg = "应用上架成功！";
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/app/listonlinenew";
    }

    private AppStoreApplication jsonFormatCommitApiModel(CommitClientApiModel model, List<CommonsMultipartFile> icon,
            List<CommonsMultipartFile> iphone, List<CommonsMultipartFile> ipad) {
        CommitClientApiApplication ret = new CommitClientApiApplication();
        // ret.setAppId(appId);
        // ret.setAppPy(appPy);
        // ret.setId(id);
        // ret.setUrl(url);
        ret.setVersion(model.getVersion());
        model.setIconUrls(localResService.saveLocalIcons(icon));
        model.setIphoneScreens(localResService.saveLocalIphoneScreens(iphone));
        model.setIpadScreens(localResService.saveLocalIpadScreens(ipad));
        // ret.setLocale(locale);
        ret.setClientApiModel(model);
        return ret;
    }

    @RequestMapping(value = "/online")
    public String online(Integer itemId, String url, Integer preItemId, Model model, RedirectAttributes rattrs) {
        String errMsg = "";
        if (itemId != null) {
            try {
                urgentOnlineRecordService.addOnline(itemId);
                errMsg = "ok";
            } catch (Exception e) {
                errMsg = "Online itemid or url failed.Item Id:" + itemId + ", url:" + url;
                log.warn(errMsg + ",Errmsg:" + e.getMessage(), e);
            }
        }
        String locale = "";
        if (!StringUtils.isEmpty(url)) {
            if (!StringUtils.isEmpty(url)) {
                itemId = AppStoreApplicationUtils.getAppId(url);
                locale = AppStoreApplicationUtils.formatLocale(url);
            }
            try {
                urgentOnlineRecordService.addOnline(itemId, locale, url);
                errMsg = "ok";
            } catch (Exception e) {
                errMsg = "Online itemid or url failed.Item Id:" + itemId + ", url:" + url;
                log.warn(errMsg + ",Errmsg:" + e.getMessage(), e);
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/app/listonline";
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(String channel, Model model) {
        model.addAttribute("para", new AppTitleSearchParameter());
        return "app/find.ftl";
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String find(String channel, AppTitleSearchParameter para, Model model) {
        if (para.getType() == 2) {
            ApiRespWrapper<ListWrapResp<ApplicationSimple>> response = this.backendApiService.searchAppByTitle(
                    para.getTitle(), para.getStart(), para.getSize());
            if (response != null && response.getData() != null) {
                ListWrapResp<ApplicationSimple> data = response.getData();
                long count = data.getTotalCount();
                para.getPager().setTotal(count);
                List<ApplicationSimple> values = data.getResultList();
                try {
                    model.addAttribute("rootIdBoughtMap",
                            applicationSimpleAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
                } catch (Exception e) {
                    log.error("gain ApplicationSimple of rootId authority state failure!" + e.getMessage(), e);
                }
                model.addAttribute("values", values);
            }
        } else if (para.getType() == 3) {
            List<Integer> rootIds = ParameterUtils.idstringToIdList(para.getRootIds());
            ApiRespWrapper<Map<Integer, ApplicationSimple>> response = this.backendApiService.getAppSimple(rootIds);
            if (response != null && response.getData() != null) {
                List<ApplicationSimple> values = new ArrayList<ApplicationSimple>(response.getData().values());
                para.getPager().setTotal(values.size());
                try {
                    model.addAttribute("rootIdBoughtMap",
                            applicationSimpleAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
                } catch (Exception e) {
                    log.error("gain ApplicationSimple of rootId authority state failure!");
                }
                model.addAttribute("values", values);
            }
        } else {
            ApiRespWrapper<SearchResp> response = this.backendApiService.searchApp(para.getTitle(), para.getStart(),
                    para.getSize());
            if (response != null && response.getData() != null) {
                List<ApplicationSimple> values = response.getData().getResultList();
                long count = response.getData().getTotal() == null ? 0 : response.getData().getTotal().longValue();
                para.getPager().setTotal(count);
                try {
                    model.addAttribute("rootIdBoughtMap",
                            applicationSimpleAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
                } catch (Exception e) {
                    log.error("gain ApplicationSimple of rootId authority state failure!");
                }
                model.addAttribute("values", values);
            }
        }
        model.addAttribute("para", para);
        return "app/find.ftl";
    }


    @RequestMapping("/list")
    public String list(ApplicationParameter para, Model model) {
        if (para == null) {
            para = new ApplicationParameter();
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<Application>> applicationsResp = this.backendApiService.listApplication(para);
        ListWrapResp<Application> appList = applicationsResp.getData();
        List<Application> values = appList.getResultList();
        long count = appList.getTotalCount();

        ApiRespWrapper<Map<String, String>> catesResp = this.backendApiService.mapCategoryDictionary();
        para.getPager().setTotal(count);
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("categoryNameMap", catesResp.getData());
        model.addAttribute("sortTypes", ApplicationParameter.ORDER_TYPE);
        model.addAttribute("orders", ApplicationParameter.ORDERS);
        try {
            model.addAttribute("rootIdBoughtMap", applicationAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
        } catch (Exception e) {
            log.error("gain Application of rootId authority state failure!" + e.getMessage(), e);
        }
        return "app/list.ftl";
    }

    @RequestMapping("/listcommit")
    public String listCommit(RootApplicationParameter para, Model model) {
        para.setSortType("1");
        para.setOrder("desc");
        para.setAppType(AppType.TYPE_COMMIT);
        para.transfer();
        ApiRespWrapper<ListWrapResp<RootApplication>> rootApplicationsResp = this.backendApiService
                .listRootApplication(para);
        List<RootApplication> gg = new ArrayList<RootApplication>();
        if (rootApplicationsResp.getData() != null && rootApplicationsResp.getData().getResultList() != null) {
            gg = rootApplicationsResp.getData().getResultList();
        }
        List<Integer> rootIds = new ArrayList<Integer>();
        for (RootApplication g : gg) {
            rootIds.add(g.getRootId());
        }
        para.getPager().setTotal(gg.size());
        if (rootIds.size() > 0) {
            ApiRespWrapper<Map<Integer, ApplicationSimple>> response = this.backendApiService.getAppSimple(rootIds);
            if (response != null && response.getData() != null) {
                List<ApplicationSimple> values = new ArrayList<ApplicationSimple>(response.getData().values());
                try {
                    model.addAttribute("rootIdBoughtMap",
                            applicationSimpleAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
                } catch (Exception e) {
                    log.error("gain ApplicationSimple of rootId authority state failure!" + e.getMessage(), e);
                }
                model.addAttribute("values", values);
            }
        }
        model.addAttribute("para", para);
        return "app/listcommit.ftl";
    }

    @RequestMapping("/listemphasis")
    public String listemphasis(@ModelAttribute("errMsg") String errMsg, AppEmphasisParameter para, Model model) {
        if (para == null) {
            para = new AppEmphasisParameter();
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<ApplicationSimple>> applicationsResp = this.backendApiService
                .listEmphasisApplication(para);
        List<ApplicationSimple> values = null;
        if (applicationsResp != null && applicationsResp.getData() != null
                && !CollectionUtils.emptyOrNull(applicationsResp.getData().getResultList())) {
            ListWrapResp<ApplicationSimple> appList = applicationsResp.getData();
            values = appList.getResultList();
            long count = appList.getTotalCount();
            para.getPager().setTotal(count);
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("status", DbStatus.STATUS);
        return "app/listemphasis.ftl";
    }

    @RequestMapping("/modifyemphasisstatus")
    @ResponseBody
    public String modifyemphasisstatus(int rootId, int status) {
        AppEmphasisParameter param = new AppEmphasisParameter();
        ApiRespWrapper<Boolean> resp = null;
        param.setRootId(rootId);
        if (status == AppEmphasisInfo.STATUS_OK) {
            param.setStatus(AppEmphasisInfo.STATUS_OK);
            resp = this.backendApiService.addEmphasis(param);
        } else {
            param.setRootId(rootId);
            param.setStatus(AppEmphasisInfo.STATUS_DEL);
            resp = this.backendApiService.modifyEmphasisStatus(param);
        }
        if (resp.getData()) {
            return "";
        }
        return resp.getMessage();
    }

    @RequestMapping("/addemphasis")
    public String addemphasis(int rootId, RedirectAttributes rattrs) {
        // rootId exists
        String errMsg = "";
        if (rootId <= 0 || !isOnline(rootId)) {
            errMsg = "未知的应用Id.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppEmphasisParameter para = new AppEmphasisParameter();
            para.setRootId(rootId);
            para.setStatus(AppEmphasisInfo.STATUS_OK);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addEmphasis(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add down type intervention failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/app/listemphasis";
    }


    @RequestMapping("/listbannedapp")
    public String listbannedapp(@ModelAttribute("errMsg") String errMsg, AppBannedParameter para, Model model) {
        if (para == null) {
            para = new AppBannedParameter();
        }
        para.transfer();
        List<AppBannedResult> values = new ArrayList<AppBannedResult>();
        ApiRespWrapper<ListWrapResp<AppBanned>> applicationsResp = this.backendApiService.listBannedApplication(para);
        List<AppBanned> datas = Collections.emptyList();

        if (applicationsResp != null && applicationsResp.getData() != null
                && applicationsResp.getData().getResultList() != null) {
            datas = applicationsResp.getData().getResultList();
        } else {
            datas = Collections.emptyList();
        }

        List<Integer> rootIds = new ArrayList<Integer>();
        for (AppBanned data : datas) {
            rootIds.add(data.getRootId());
        }
        ApiRespWrapper<ListWrapResp<RootApplication>> rootApplicationsListInBanned = this.backendApiService
                .listRootApplication(rootIds);
        List<RootApplication> rootApplicationsInBanned = new ArrayList<RootApplication>();
        if (rootApplicationsListInBanned.getData() != null
                && rootApplicationsListInBanned.getData().getResultList() != null) {
            rootApplicationsInBanned = rootApplicationsListInBanned.getData().getResultList();
        }
        for (AppBanned data : datas) {
            RootApplication root = null;
            for (RootApplication rootApplication : rootApplicationsInBanned) {
                if (data.getRootId() == rootApplication.getRootId()) {
                    root = rootApplication;
                    break;
                }
            }
            values.add(new AppBannedResult(data, root));
        }
        long count = applicationsResp != null && applicationsResp.getData() != null ? applicationsResp.getData()
                .getTotalCount() : 0;
        para.getPager().setTotal(count);
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        return "app/offline.ftl";
    }

    @RequestMapping(value = "/searchrootid")
    @ResponseBody
    public JsonResult<RootApplication> searchRootId(String rootId) {
        boolean success = false;
        String message;
        RootApplication ret = null;
        int rid = NumberUtils.toInt(rootId, -1);
        if (rid == -1) {
            message = "无效的根Id";
        } else {
            List<Integer> rootIds = Arrays.asList(rid);
            ApiRespWrapper<ListWrapResp<RootApplication>> resp = this.backendApiService.listRootApplication(rootIds);
            if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
                message = "无效的根Id";
            } else {
                success = true;
                message = "ok";
                ret = resp.getData().getResultList().get(0);
            }
        }
        return new JsonResult<RootApplication>(success, message, ret);
    }

    @RequestMapping("/modifybannedappstatus")
    @ResponseBody
    public String modifybannedappstatus(int rootId, String info, int status) {
        String errMsg = "";
        if (rootId <= 0 || !isOnline(rootId)) {
            errMsg = "未知的应用Id.";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(info)) {
            errMsg = "未知的操作理由.";
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersService.getUser(userDetails.getUsername());
        if (StringUtils.isEmpty(errMsg) && user == null) {
            errMsg = "未知的操作者.";
        }
        ApiRespWrapper<Boolean> resp = null;
        if (StringUtils.isEmpty(errMsg)) {
            AppBannedParameter para = new AppBannedParameter();
            para.setRootId(rootId);
            para.setAdminId(user.getUserId());
            para.setInfo(info);
            if (status == AppEmphasisInfo.STATUS_OK) {
                para.setStatus(status);
                resp = this.backendApiService.addAppBanned(para);
            } else {
                para.setStatus(AppEmphasisInfo.STATUS_DEL);
                resp = this.backendApiService.modifyAppBannedStatus(para);
            }
            if (resp.getData()) {
                return "";
            } else {
                return resp.getMessage();
            }
        }
        return errMsg;
    }

    @RequestMapping("/addbannedapp")
    public String addbannedapp(Integer rootId, String info, RedirectAttributes rattrs) {
        // rootId exists
        String errMsg = "";
        if (rootId <= 0) {
            errMsg = "未知的应用Id.";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(info)) {
            errMsg = "未知的操作理由.";
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersService.getUser(userDetails.getUsername());
        if (StringUtils.isEmpty(errMsg) && user == null) {
            errMsg = "未知的操作者.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppBannedParameter para = new AppBannedParameter();
            para.setRootId(rootId);
            para.setAdminId(user.getUserId());
            para.setInfo(info);
            para.setStatus(AppEmphasisInfo.STATUS_OK);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addAppBanned(para);
                if (resp == null) {
                    errMsg = "添加到拉黑列表遇到未知错误";
                } else if (!resp.getData()) {
                    errMsg = "添加到拉黑列表" + resp.getMessage();
                } else {
                    resp = daemonApiService.offlineApp(rootId);
                    if (resp == null) {
                        errMsg = "下架应用时遇到未知错误";
                    } else if (!resp.getData()) {
                        errMsg = "下架应用时" + resp.getMessage();
                    }
                }
            } catch (Exception e) {
                log.error("Add app to banned list failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "拉黑失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/app/listbannedapp";
    }


    @RequestMapping("/detail")
    public String detail(Integer id, Integer rootId, Model model) {
        ApiRespWrapper<Application> dataResp = null;
        if (id == null) {
            dataResp = this.backendApiService.getApplicationByRootId(rootId.intValue());
        } else {
            dataResp = this.backendApiService.getApplication(id.intValue());
        }
        Application data = dataResp.getData();
        rootId = data.getRootId();
        ApiRespWrapper<RootApplication> rootApplicationResp = this.backendApiService.getRootApplication(rootId);
        if (rootApplicationResp != null && rootApplicationResp.getData() != null) {
            model.addAttribute("rootApplication", rootApplicationResp.getData());
        }
        List<ApplicationItunesImgRes> appRes = null;
        List<Category> cates = null;
        Category parentCate = null;
        if (data != null) {
            id = data.getId();
            ApiRespWrapper<ListWrapResp<ApplicationItunesImgRes>> appResResp = this.backendApiService
                    .getApplicationResource(id);
            appRes = appResResp == null || appResResp.getData() == null ? null : appResResp.getData().getResultList();
            ApiRespWrapper<ListWrapResp<Category>> cateResp = this.backendApiService.getRootApplicationCategory(rootId);
            cates = cateResp.getData().getResultList();
            if (!CollectionUtils.emptyOrNull(cates) && cates.get(0).getParent() != null) {
                ApiRespWrapper<Category> parentCateResp = this.backendApiService.getCategory(cates.get(0).getParent()
                        .intValue());
                parentCate = parentCateResp.getData();
            }
        }
        model.addAttribute("data", data);
        model.addAttribute("appRes", appRes);
        for (ApplicationItunesImgRes applicationItunesImgRes : appRes) {
            if (applicationItunesImgRes.getType() == ApplicationItunesImgRes.TYPE_ICON) {
                List<List<ImgRes>> icons = ApplicationItunesImgResUtils
                        .formatResourceToListImgList(applicationItunesImgRes);
                model.addAttribute("icons", icons);
            } else if (applicationItunesImgRes.getType() == ApplicationItunesImgRes.TYPE_SCREEN) {
                if (StringUtils.startsWithIgnoreCase(applicationItunesImgRes.getDevice(), "iphone")) {
                    List<List<ImgRes>> iphones = ApplicationItunesImgResUtils
                            .formatResourceToListImgList(applicationItunesImgRes);
                    model.addAttribute("iphones", iphones);
                } else if (StringUtils.startsWithIgnoreCase(applicationItunesImgRes.getDevice(), "ipad")) {
                    List<List<ImgRes>> ipads = ApplicationItunesImgResUtils
                            .formatResourceToListImgList(applicationItunesImgRes);
                    model.addAttribute("ipads", ipads);
                }
            }
        }
        model.addAttribute("cates", cates);
        model.addAttribute("parentCate", parentCate);
        model.addAttribute("id", id);
        return "app/detail.ftl";
    }

    @RequestMapping("/modifyeditortitle")
    @ResponseBody
    public String modifyEditorTitle(int rootId, String editorTitle) {
        ApplicationModifyParameter applicationModifyParameter = new ApplicationModifyParameter();
        applicationModifyParameter.setRootId(rootId);
        applicationModifyParameter.setEditorTitle(editorTitle);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyApplicationEditorTitle(applicationModifyParameter);
        if (resp.getData()) {
            return "";
        }
        return resp.getMessage();
    }

    @RequestMapping("/modifyeditorreview")
    @ResponseBody
    public String modifyeditorreview(int rootId, String editorReview) {
        ApplicationModifyParameter applicationModifyParameter = new ApplicationModifyParameter();
        applicationModifyParameter.setRootId(rootId);
        applicationModifyParameter.setEditorReview(editorReview);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyApplicationEditorReview(applicationModifyParameter);
        if (resp.getData()) {
            return "";
        }
        return resp.getMessage();
    }

    @RequestMapping("/listdowntypeintervention")
    public String listdowntypeintervention(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("type") String type, DownTypeInterventionParameter para, Model model) {
        if (!StringUtils.isEmpty(type)) {
            try {
                para.setType(Integer.parseInt(type));
            } catch (Exception e) {
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<DownTypeIntervention>> downloadTypeInterResp = this.backendApiService
                .listDownTypeIntervention(para);
        long count = 0;
        List<DownTypeIntervention> values = null;
        List<Integer> rootIds = null;

        if (downloadTypeInterResp != null && downloadTypeInterResp.getData() != null) {
            ListWrapResp<DownTypeIntervention> downloadTypeInterWrap = downloadTypeInterResp.getData();
            if (downloadTypeInterWrap != null) {
                count = downloadTypeInterWrap.getTotalCount();
                values = downloadTypeInterWrap.getResultList();
                rootIds = initRootIds(values);
            }
        }
        Map<String, ApplicationSimple> rootSimples = null;
        if (!CollectionUtils.emptyOrNull(rootIds)) {
            ApiRespWrapper<Map<Integer, ApplicationSimple>> rootAppSimpleMapResp = this.backendApiService
                    .getAppSimple(rootIds);
            if (rootAppSimpleMapResp != null && rootAppSimpleMapResp.getData() != null) {
                rootSimples = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : rootAppSimpleMapResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
            }
        }
        para.getPager().setTotal(count);
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("values", values);
        model.addAttribute("rootSimples", rootSimples);
        model.addAttribute("para", para);
        model.addAttribute("status", DownTypeInterventionParameter.ALL_STATUS);
        model.addAttribute("types", DownTypeInterventionParameter.TYPES);
        model.addAttribute("allTypes", DownTypeInterventionParameter.ALL_TYPES);
        return "app/downtypeinter.ftl";
    }

    @RequestMapping("/adddowntypeintervention")
    public String adddowntypeintervention(Integer rootId, Integer type, String url, Model model,
            RedirectAttributes rattrs) {
        // rootId exists
        String errMsg = "";
        if (rootId == null || rootId.intValue() <= 0 || !isOnline(rootId)) {
            errMsg = "未知的应用Id.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            if (type == null || type == DownloadTypeInfo.ALL.getType()
                    || !DownTypeInterventionParameter.TYPES.containsKey(type.toString())) {
                errMsg = "未知的类型.";
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            DownTypeInterventionParameter para = new DownTypeInterventionParameter();
            para.setRootId(rootId);
            para.setType(type);
            para.setUrl(url);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addDownTypeIntervention(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add down type intervention failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("type", type.toString());
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/app/listdowntypeintervention";
    }

    @RequestMapping("/modifydowntypeintervention.json")
    @ResponseBody
    public String modifydowntypeintervention(Integer rootId, Integer status, Model model, RedirectAttributes rattrs) {
        // rootId exists
        String errMsg = "";
        if (rootId == null || rootId.intValue() <= 0 || !isOnline(rootId)) {
            errMsg = "未知的应用Id.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            if (status == null
                    || (status != DownTypeIntervention.STATUS_DEL && status != DownTypeIntervention.STATUS_OK)) {
                errMsg = "未知的状态.";
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            DownTypeInterventionParameter para = new DownTypeInterventionParameter();
            para.setRootId(rootId);
            para.setStatus(status);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.modifyDownTypeIntervention(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Modify down type intervention failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        return errMsg;
    }

    @RequestMapping(value = "/upload/stat/list")
    public String listAppUploadStat(AppUploadStatParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppOnlineRecordStat>> statResp = this.onlineApiService.listAppUploadStat(para);
        if (statResp != null && statResp.getData() != null
                && CollectionUtils.notEmptyAndNull(statResp.getData().getResultList())) {
            para.getPager().setTotal(statResp.getData().getTotalCount());
            model.addAttribute("values", statResp.getData().getResultList());
        }
        ApiRespWrapper<Long> wairResp = this.onlineApiService.countWaitOnline(para);
        if (wairResp != null && wairResp.getData() != null) {
            model.addAttribute("wait", wairResp.getData());
        }
        model.addAttribute("types", AppOnlineType.values());
        model.addAttribute("para", para);
        return "app/upload/stat/list.ftl";
    }

    @RequestMapping(value = "/upload/stat/detail")
    public String listAppUploadRecord(int id, int type, Integer onlineStatus, AppUploadRecordParameter para, Model model) {
        para.setStatId(id);
        para.setType(type);
        para.setOnlineStatus(OnlineStatus.instance(onlineStatus).getStatus());
        ApiRespWrapper<ListWrapResp<AppOnlineRecord>> recordResp = this.onlineApiService.listAppUploadRecord(para);
        if (recordResp != null && recordResp.getData() != null
                && CollectionUtils.notEmptyAndNull(recordResp.getData().getResultList())) {
            para.getPager().setTotal(recordResp.getData().getTotalCount());
            model.addAttribute("values", recordResp.getData().getResultList());
        }
        model.addAttribute("onlinestatuses", OnlineStatus.values());
        model.addAttribute("types", AppOnlineType.values());
        model.addAttribute("para", para);
        return "app/upload/record/list.ftl";
    }

    @RequestMapping(value = "/upload/crawler/application/detail")
    public String detailCrawlerApplication(int id, int type, CrawlerApplicationParameter para, Model model) {
        para.setId(id);
        para.setType(type);
        ApiRespWrapper<CrawlerApplication> recordResp = this.onlineApiService.detailCrawlerApplication(para);
        if (recordResp != null && recordResp.getData() != null) {
            model.addAttribute("value", recordResp.getData());
        }
        model.addAttribute("para", para);
        return "app/upload/crawler/application/detail.ftl";
    }

    @RequestMapping(value = "/upload/crawler/application/list")
    public String listCrawlerApplication(CrawlerApplicationParameter para, Model model) {
        ApiRespWrapper<ListWrapResp<CrawlerApplication>> recordResp = this.onlineApiService
                .listCrawlerApplication(para);
        if (recordResp != null && recordResp.getData() != null
                && CollectionUtils.notEmptyAndNull(recordResp.getData().getResultList())) {
            para.getPager().setTotal(recordResp.getData().getTotalCount());
            model.addAttribute("values", recordResp.getData().getResultList());
        }
        ApiRespWrapper<Integer> maxOnlineIdResp = this.onlineApiService.getMaxOnlineRecordId(para);
        if (maxOnlineIdResp != null && maxOnlineIdResp.getData() != null) {
            model.addAttribute("maxOnlineRecordId", maxOnlineIdResp.getData().intValue());
        }
        model.addAttribute("para", para);
        model.addAttribute("types", AppOnlineType.values());
        return "app/upload/crawler/application/list.ftl";
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    private List<Integer> initRootIds(List<DownTypeIntervention> values) {
        List<Integer> rootIds = new ArrayList<Integer>();
        for (DownTypeIntervention downloadTypeIntervention : values) {
            rootIds.add(downloadTypeIntervention.getRootId());
        }
        return rootIds;
    }

    private List<Integer> initUrgentOnlineRootIds(List<UrgentOnlineRecord> values) {
        List<Integer> rootIds = new ArrayList<Integer>();
        for (UrgentOnlineRecord record : values) {
            rootIds.add(record.getRootId());
        }
        return rootIds;
    }
}
