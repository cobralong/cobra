package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.crawler.model.LimitDiscountApplicationSimple;
import com.appchina.ios.core.dto.app.AppLimitDiscount;
import com.appchina.ios.core.dto.app.AppLimitDiscountIntervention;
import com.appchina.ios.mgnt.controller.model.AppLimitDiscountListParameter;
import com.appchina.ios.mgnt.service.AppLimitDiscountBackendApiService;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;

@Controller
@RequestMapping(value = "/auth/limit/*")
public class LimitDiscountController {
    private static final Logger log = Logger.getLogger(LimitDiscountController.class);
    @Autowired
    private AppLimitDiscountBackendApiService appLimitDiscountBackendApiService;
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/list")
    public String list(AppLimitDiscountListParameter para, Model model) {
        if (para == null) {
            para = new AppLimitDiscountListParameter();
        }
        String errMsg = "";
        ApiRespWrapper<Map<String, String>> typeResp = appLimitDiscountBackendApiService.mapSortTypeDictionary();
        if (typeResp == null || CollectionUtils.emptyOrNull(typeResp.getData())) {
            errMsg = "数据错误，不存在限免榜类别字典数据.";
        }
        Map<String, String> types = typeResp.getData();
        para.setErrMsg(errMsg);
        para.transfer(types);
        ApiRespWrapper<ListWrapResp<AppLimitDiscount>> valueResp = appLimitDiscountBackendApiService
                .listAppLimitDiscount(para);
        List<AppLimitDiscount> values = null;
        Map<String, LimitDiscountApplicationSimple> rootSimples = null;
        if (valueResp != null && valueResp.getData() != null && valueResp.getData().getResultList() != null) {
            para.getPager().setTotal(valueResp.getData().getTotalCount());
            values = valueResp.getData().getResultList();
            List<Integer> rootIds = fetchAppLimitDiscountRootIds(valueResp.getData().getResultList());
            ApiRespWrapper<Map<Integer, LimitDiscountApplicationSimple>> simpleResp = this.backendApiService
                    .getLimitDiscountApplicationSimple(rootIds);
            if (simpleResp != null && simpleResp.getData() != null) {
                rootSimples = new HashMap<String, LimitDiscountApplicationSimple>();
                for (Entry<Integer, LimitDiscountApplicationSimple> entry : simpleResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
            }
        }
        model.addAttribute("values", values);
        model.addAttribute("rootSimples", rootSimples);
        model.addAttribute("types", types);
        model.addAttribute("status", DbStatus.STATUS);
        model.addAttribute("para", para);
        return "limit/freelist.ftl";
    }

    @RequestMapping(value = "/intervention/list")
    public String interventionlist(AppLimitDiscountListParameter para, @ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("type") String type, Model model) {
        if (para == null) {
            para = new AppLimitDiscountListParameter();
        }
        if (!StringUtils.isEmpty(errMsg)) {
            para.setErrMsg(errMsg);
        }
        ApiRespWrapper<Map<String, String>> typeResp = appLimitDiscountBackendApiService.mapSortTypeDictionary();
        if (typeResp == null || CollectionUtils.emptyOrNull(typeResp.getData())) {
            errMsg = "数据错误，不存在限免榜类别字典数据.";
        }
        Map<String, String> types = typeResp.getData();

        para.setErrMsg(errMsg);
        if (StringUtils.isBlank(type)) {
            para.transfer(types);
        } else {
            para.transfer(Integer.valueOf(type));
        }
        ApiRespWrapper<ListWrapResp<AppLimitDiscountIntervention>> valueResp = appLimitDiscountBackendApiService
                .listAppLimitDiscountInterventions(para);
        List<AppLimitDiscountIntervention> values = null;
        Map<String, ApplicationSimple> rootSimples = null;
        if (valueResp != null && valueResp.getData() != null && valueResp.getData().getResultList() != null) {
            para.getPager().setTotal(valueResp.getData().getTotalCount());
            values = valueResp.getData().getResultList();
            List<Integer> rootIds = fetchAppLimitDiscountInterventionRootIds(valueResp.getData().getResultList());
            ApiRespWrapper<Map<Integer, ApplicationSimple>> simpleResp = this.backendApiService.getAppSimple(rootIds);
            if (simpleResp != null && simpleResp.getData() != null) {
                rootSimples = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : simpleResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
            }
        }
        model.addAttribute("values", values);
        model.addAttribute("rootSimples", rootSimples);
        model.addAttribute("types", types);
        model.addAttribute("status", DbStatus.STATUS);
        model.addAttribute("para", para);
        return "limit/interception/list.ftl";
    }

    @RequestMapping(value = "/intervention/modify", method = RequestMethod.POST)
    @ResponseBody
    public String interventionmodify(int id, int status) {
        if (id <= 0) {
            return "id无效.";
        }
        AppLimitDiscountListParameter para = new AppLimitDiscountListParameter(id, status);
        ApiRespWrapper<Boolean> resp = this.appLimitDiscountBackendApiService.modifyAppLimitDiscountInterventions(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/intervention/add", method = RequestMethod.POST)
    public String interventionAdd(Integer rootId, Integer type, Integer rank, RedirectAttributes rattrs) {
        rattrs.addFlashAttribute("type", type == null ? null : type.toString());
        String errMsg = checkAndFormatAddParameter(rootId, type, rank);
        if (StringUtils.isEmpty(errMsg)) {
            AppLimitDiscountListParameter para = new AppLimitDiscountListParameter(rootId, type, rank);
            try {
                ApiRespWrapper<Boolean> resp = this.appLimitDiscountBackendApiService
                        .addAppLimitDiscountInterventions(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add app limit discount intervention failed. Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/limit/intervention/list";
    }

    private String checkAndFormatAddParameter(Integer rootId, Integer type, Integer rank) {
        String errMsg = "";
        if (rank == null || rank < 0) {
            errMsg = "推荐位置未定，请设置";
        }
        if (StringUtils.isEmpty(errMsg) && type == null) {
            errMsg = "未知的限免榜类型.";
        }
        if (StringUtils.isEmpty(errMsg) && (rootId == null || rootId.intValue() <= 0 || !isOnline(rootId))) {
            errMsg = "无法推荐一个未上架的应用";
        }
        if (!isLimitFree(rootId)) {
            errMsg = "无法推荐一个非限免应用";
        }
        return errMsg;
    }

    private boolean isLimitFree(Integer rootId) {
        ApiRespWrapper<Boolean> resp = this.appLimitDiscountBackendApiService.isLimitFree(rootId);
        return resp != null && resp.getData() != null && resp.getData().booleanValue();
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    private List<Integer> fetchAppLimitDiscountInterventionRootIds(List<AppLimitDiscountIntervention> list) {
        List<Integer> ret = new ArrayList<Integer>();
        for (AppLimitDiscountIntervention appLimitDiscountIntervention : list) {
            ret.add(appLimitDiscountIntervention.getRootId());
        }
        return ret;
    }

    private List<Integer> fetchAppLimitDiscountRootIds(List<AppLimitDiscount> list) {
        List<Integer> ret = new ArrayList<Integer>();
        for (AppLimitDiscount appLimitDiscount : list) {
            ret.add(appLimitDiscount.getRootId());
        }
        return ret;
    }
}
