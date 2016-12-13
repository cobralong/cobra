package com.appchina.ios.mgnt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appchina.ios.core.dto.app.Category;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping(value = "/auth/category/*")
public class CategoryController {
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/softcatelist", method = RequestMethod.GET)
    public String softcatelist(Model model) {
        return this.cateList(100, "应用", model);
    }

    @RequestMapping(value = "/gamecatelist", method = RequestMethod.GET)
    public String gamecatelist(Model model) {
        return this.cateList(200, "游戏", model);
    }

    private String cateList(int rootCategoryId, String typeName, Model model) {
        ApiRespWrapper<ListWrapResp<Category>> cateResp = this.backendApiService.listChildCategory(rootCategoryId);
        List<Category> values = cateResp.getData().getResultList();
        model.addAttribute("values", values);
        model.addAttribute("map", buildCategoryIdAppStoreCategoryNames(values));
        model.addAttribute("cateType", typeName);
        return "cate/list.ftl";
    }

    private Map<String, List<String>> buildCategoryIdAppStoreCategoryNames(List<Category> categorys) {
        Map<String, List<String>> ret = new HashMap<String, List<String>>();
        for (Category category : categorys) {
            ApiRespWrapper<ListWrapResp<String>> resp = this.backendApiService.listItunesCategoryName(category.getId());
            List<String> values = resp.getData().getResultList();
            ret.put(String.valueOf(category.getId()), values);
        }
        return ret;
    }

}
