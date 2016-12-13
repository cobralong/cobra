package com.appchina.ios.mgnt.controller.info;

import com.appchina.ios.core.dto.info.AppStoreAppTagAppleTagMap;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.info.StoreAppTagAppleTagMapParameter;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouyanhui on 8/18/15.
 */
@Controller
@RequestMapping(value = "/auth/store/tagmap/*")
public class StoreAppTagAppleTagMapController {
    private static final Logger log = Logger.getLogger(StoreAppTagAppleTagMapController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "redirect:/auth/store/tagmap/list";
    }

    @RequestMapping("/list")
    public String list(StoreAppTagAppleTagMapParameter para, @ModelAttribute("errMsg") String errMsg, Model model) {
        para.transfer();
        ApiRespWrapper<Map<String, String>> appleCategoriesMapResp = iosStoreApiService.fetchAllAppleCategories();
        Map<String, String> appleCategoriesMap = appleCategoriesMapResp.getData();
        ApiRespWrapper<Map<String, String>> customAppTagsResp = iosStoreApiService.fetchCustomAppTags();
        Map<String, String> storeAppTags = customAppTagsResp.getData();
        ApiRespWrapper<ListWrapResp<AppStoreAppTagAppleTagMap>> resp = iosStoreApiService
                .listStoreAppTagAppleTagMaps(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppStoreAppTagAppleTagMap> values = (resp == null || resp.getData() == null || resp.getData()
                .getResultList() == null) ? null : resp.getData().getResultList();
        Map<String, String> leftAppleCategoriesMap = new HashMap<String, String>(appleCategoriesMap);
        for (AppStoreAppTagAppleTagMap appStoreAppTagAppleTagMap : values) {
            int appleCateId = appStoreAppTagAppleTagMap.getAppleCategoryId();
            if (leftAppleCategoriesMap.containsKey(String.valueOf(appleCateId))) {
                leftAppleCategoriesMap.remove(String.valueOf(appleCateId));
            }
        }
        model.addAttribute("leftAppleCategoriesMap", leftAppleCategoriesMap);
        model.addAttribute("appleCategories", appleCategoriesMap);
        model.addAttribute("storeAppTags", storeAppTags);
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/info/tagmap/list.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Integer appleCateId, Integer appTagId, Model model, RedirectAttributes rattrs) {
        StoreAppTagAppleTagMapParameter para = new StoreAppTagAppleTagMapParameter();
        String errMsg = checkAndFormatAddParameter(appleCateId, appTagId, para);
        if (StringUtils.isEmpty(errMsg)) {
            try {
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addAppStoreAppTagAppleTagMap(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add StoreAppTagAppleTagMap failed." + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/tagmap/list";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(Integer appleCateId, Integer appTagId, Integer status) {
        if (appleCateId != null && appleCateId <= 0) {
            return "apple category id无效.";
        }
        if (appTagId != null && appTagId <= 0) {
            return "app tag id无效.";
        }
        StoreAppTagAppleTagMapParameter para = new StoreAppTagAppleTagMapParameter();
        para.setAppleCateId(appleCateId);
        para.setAppTagId(appTagId);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppStoreAppTagAppleTagMapStatus(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    private String checkAndFormatAddParameter(Integer appleCateId, Integer appTagId,
            StoreAppTagAppleTagMapParameter para) {
        ApiRespWrapper<Map<String, String>> appleCategoriesMapResp = iosStoreApiService.fetchAllAppleCategories();
        Map<String, String> appleCategoriesMap = appleCategoriesMapResp.getData();

        if (appleCateId == null || !appleCategoriesMap.containsKey(String.valueOf(appleCateId))) {
            return "apple category id 不存在";
        } else {
            para.setAppleCateId(appleCateId);
        }
        ApiRespWrapper<Map<String, String>> customAppTagsResp = iosStoreApiService.fetchCustomAppTags();
        Map<String, String> storeAppTags = customAppTagsResp.getData();
        if (appTagId == null || !storeAppTags.containsKey(String.valueOf(appTagId))) {
            return "app tag id 不是有效的自定义Tag";
        } else {
            para.setAppTagId(appTagId);
        }
        return "";
    }

}
