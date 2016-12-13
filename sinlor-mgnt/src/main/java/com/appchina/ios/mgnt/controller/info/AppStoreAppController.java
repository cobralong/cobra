// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.info;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.cahe.model.app.DownloadTypeInfo;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.app.DownTypeIntervention;
import com.appchina.ios.mgnt.controller.model.DownTypeInterventionParameter;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */

@Controller
@RequestMapping(value = "/auth/store/app/*")
public class AppStoreAppController {
    private static final Logger log = Logger.getLogger(AppStoreAppController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;

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
        ApiRespWrapper<ListWrapResp<DownTypeIntervention>> downloadTypeInterResp = this.iosStoreApiService
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
            ApiRespWrapper<Map<Integer, ApplicationSimple>> rootAppSimpleMapResp = this.iosStoreApiService
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
        return "store/app/downtypeinter.ftl";
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
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addDownTypeIntervention(para);
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
        return "redirect:/auth/store/app/listdowntypeintervention";
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
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyDownTypeIntervention(para);
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

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.iosStoreApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    private List<Integer> initRootIds(List<DownTypeIntervention> values) {
        List<Integer> rootIds = new ArrayList<Integer>();
        for (DownTypeIntervention downloadTypeIntervention : values) {
            rootIds.add(downloadTypeIntervention.getRootId());
        }
        return rootIds;
    }
}
