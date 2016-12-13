package com.appchina.ios.mgnt.controller.info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.appchina.ios.core.cahe.model.info.AppStoreArticleSimple;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.info.AppStoreBanner;
import com.appchina.ios.core.dto.info.InfoType;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.info.StoreBannerParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.service.impl.StoreCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping(value = "/auth/store/banner/*")
public class StoreBannerController {
    private static final Logger log = Logger.getLogger(StoreBannerController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private StoreCommonParameterService storeCommonParameterService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(String errMsg, StoreBannerParameter para, Model model) {
        storeCommonParameterService.addBundles("bundleIds", model);
        model.addAttribute("types", StoreBannerParameter.ALL_TYPES);
        model.addAttribute("shows", AppStoreBanner.SHOWS);
        model.addAttribute("para", para);
        return "store/info/banner/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(StoreBannerParameter para, @RequestParam(required = false) CommonsMultipartFile banner,
            Model model) {
        Date startTime = new Date();
        Date endTime = new Date();
        int type = para.getType();
        int refId = -1;
        if (type == InfoType.APP.getType()) {
            refId = para.getRootId();
        } else if (type == InfoType.INFO.getType()) {
            refId = para.getArticleId();
        }
        String errMsg = checkAndFormatAddParameter(refId, type, para.getShowInAudit(), para.getAuditingPosition(),
                para.getAuditedPosition(), para.getSt(), para.getEt(), startTime, endTime, banner);

        if (StringUtils.isEmpty(errMsg)) {
            if (para.getAuditedPosition() == null) {
                para.setAuditedPosition(0);
            }
            if (para.getAuditingPosition() == null) {
                para.setAuditingPosition(0);
            }
            String bannerUrl = bannerStorageService.saveStoreBanner(banner);
            para.setBannerUrl(bannerUrl);
            para.setRefId(refId);
            try {
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addAppStoreBanner(para);
                if (resp == null) {
                    errMsg = "未知错误!";
                } else if (resp.getStatus() != 0 || resp.getData() == null) {
                    errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知错误!" : resp.getMessage();
                } else {
                    errMsg = "添加成功!";
                }
            } catch (Exception e) {
                log.error("Add banner failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败!";
            }
        }
        model.addAttribute("para", para);
        model.addAttribute("types", StoreBannerParameter.ALL_TYPES);
        model.addAttribute("errMsg", errMsg);
        storeCommonParameterService.addBundles("bundleIds", model);
        model.addAttribute("shows", AppStoreBanner.SHOWS);
        return "store/info/banner/add.ftl";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(int id, Integer status, Integer auditingPosition, Integer auditedPosition, String et,
            String st, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        StoreBannerParameter para = new StoreBannerParameter();
        para.setId(id);
        para.setStatus(status);
        para.setAuditedPosition(auditedPosition);
        para.setAuditingPosition(auditingPosition);
        para.setSt(st);
        para.setEt(et);
        resp = this.iosStoreApiService.modifyBanner(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    private String checkAndFormatAddParameter(Integer refId, Integer type, Integer showInAudit,
            Integer auditingPosition, Integer auditedPosition, String st, String et, Date startTime, Date endTime,
            CommonsMultipartFile banner) {
        String errMsg = "";
        if (StringUtils.isEmpty(errMsg) && (banner == null || banner.getSize() == 0)) {
            errMsg = "请提供轮播图";
        }
        if (StringUtils.isEmpty(errMsg) && auditedPosition == null && auditingPosition == null) {
            errMsg = "请设置要展示的位置";
        }
        if (StringUtils.isEmpty(errMsg)
                && (type == null || !StoreBannerParameter.ALL_TYPES.containsKey(String.valueOf(type)))) {
            errMsg = "无效Banner类型";
        }
        if (StringUtils.isEmpty(errMsg)
                && (showInAudit == AppStoreBanner.SHOW_IN_AUDIT_ALL || showInAudit == AppStoreBanner.SHOW_IN_AUDITING)
                && (auditingPosition == null || auditingPosition.intValue() <= 0)) {
            errMsg = "请设置要给审核人员展示的位置";
        }
        if (StringUtils.isEmpty(errMsg)
                && (showInAudit == AppStoreBanner.SHOW_IN_AUDIT_ALL || showInAudit == AppStoreBanner.SHOW_IN_AUDITED)
                && (auditedPosition == null || auditedPosition.intValue() <= 0)) {
            errMsg = "请设置要给用户展示的位置";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(st)) {
            errMsg = "无效的时间";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(et)) {
            errMsg = "无效的时间";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                startTime.setTime(DateUtils.parseDateStr(st).getTime());
            } catch (Exception e) {
                errMsg = "无效的时间";
            }
        }
        if (StringUtils.isEmpty(errMsg) && DateUtils.beforeNow(startTime)) {
            errMsg = "轮播图开始时间不能小于当前时间";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                endTime.setTime(DateUtils.parseDateStr(et).getTime());
            } catch (Exception e) {
                errMsg = "无效的时间";
            }
        }
        if (StringUtils.isEmpty(errMsg) && DateUtils.beforeNow(et)) {
            errMsg = "轮播图结束时间不能小于当前时间";
        }
        if (StringUtils.isEmpty(errMsg) && endTime.before(startTime)) {
            errMsg = "轮播图结束时间不能早于轮播图开始时间";
        }
        if (refId == null || refId.intValue() <= 0 || !isOnline(type, refId)) {
            errMsg = "无法轮播一个未上架的应用";
        }
        return errMsg;
    }

    private boolean isOnline(int type, int rid) {
        if (type == InfoType.APP.getType()) {
            List<Integer> rootIds = Arrays.asList(rid);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
            return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
        } else {
            List<Integer> rootIds = Arrays.asList(rid);
            ApiRespWrapper<Map<Integer, AppStoreArticleSimple>> resp = this.iosStoreApiService
                    .getAppStoreArticleSimple(rootIds);
            return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
        }
    }

    @RequestMapping("/listuser")
    public String listuser(StoreBannerParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreBanner>> resp = iosStoreApiService.listUserAppStoreBanners(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppStoreBanner> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        if (values != null && !values.isEmpty()) {
            Pair<List<Integer>, List<Integer>> refIds = initRefIds(values);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimpleResp = this.backendApiService.getAppSimple(refIds
                    .getLeft());
            if (appSimpleResp != null && appSimpleResp.getData() != null) {
                Map<String, ApplicationSimple> datas = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : appSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", datas);
            }

            ApiRespWrapper<Map<Integer, AppStoreArticleSimple>> articleSimpleResp = this.iosStoreApiService
                    .getAppStoreArticleSimple(refIds.getRight());
            if (articleSimpleResp != null && articleSimpleResp.getData() != null) {
                Map<String, AppStoreArticleSimple> datas = new HashMap<String, AppStoreArticleSimple>();
                for (Entry<Integer, AppStoreArticleSimple> entry : articleSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("articleSimples", datas);
            }
        }
        para.setShowInAudit(AppStoreBanner.SHOW_IN_AUDITED);
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("types", StoreBannerParameter.ALL_TYPES);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        storeCommonParameterService.addBundles("bundleIds", model);
        return "store/info/banner/list.ftl";
    }

    @RequestMapping("/listeditor")
    public String listEditor(StoreBannerParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreBanner>> resp = iosStoreApiService.listEditorAppStoreBanners(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppStoreBanner> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        if (values != null && !values.isEmpty()) {
            Pair<List<Integer>, List<Integer>> refIds = initRefIds(values);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimpleResp = this.backendApiService.getAppSimple(refIds
                    .getLeft());
            if (appSimpleResp != null && appSimpleResp.getData() != null) {
                Map<String, ApplicationSimple> datas = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : appSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", datas);
            }

            ApiRespWrapper<Map<Integer, AppStoreArticleSimple>> articleSimpleResp = this.iosStoreApiService
                    .getAppStoreArticleSimple(refIds.getRight());
            if (articleSimpleResp != null && articleSimpleResp.getData() != null) {
                Map<String, AppStoreArticleSimple> datas = new HashMap<String, AppStoreArticleSimple>();
                for (Entry<Integer, AppStoreArticleSimple> entry : articleSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("articleSimples", datas);
            }
        }
        para.setShowInAudit(AppStoreBanner.SHOW_IN_AUDITING);
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("types", StoreBannerParameter.ALL_TYPES);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        storeCommonParameterService.addBundles("bundleIds", model);
        return "store/info/banner/list.ftl";
    }

    private Pair<List<Integer>, List<Integer>> initRefIds(List<AppStoreBanner> values) {
        List<Integer> rootIds = new ArrayList<Integer>();
        List<Integer> articleIds = new ArrayList<Integer>();
        for (AppStoreBanner banner : values) {
            if (banner.getType() == InfoType.APP.getType()) {
                rootIds.add(banner.getRefId());
            } else if (banner.getType() == InfoType.INFO.getType()) {
                articleIds.add(banner.getRefId());
            }
        }
        return Pair.of(rootIds, articleIds);
    }

}
