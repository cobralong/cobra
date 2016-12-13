package com.appchina.ios.mgnt.controller.funny;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.info.FunnyClientKeywordSearch;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientKeywordSearchParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.FunnyClientApiService;
import com.appchina.ios.mgnt.service.impl.FunnyCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * 
 * @author liuxinglong
 * @date 2016年9月27日
 */
@Controller
@RequestMapping(value = "/funny/client/common/*")
public class FunnyClientCommonController {
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private FunnyClientApiService funnyClientApiService;
    @Autowired
    private FunnyCommonParameterService funnyCommonParameterService;
    @Autowired
    private BannerStorageService bannerStorageService;
    private Logger log = Logger.getLogger(FunnyClientCommonController.class);

    @RequestMapping("/keywordsearch/list")
    public String keywordSearchList(FunnyClientKeywordSearchParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(FunnyClientKeywordSearch.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<FunnyClientKeywordSearch>> resp = funnyClientApiService
                .listFunnyClientKeywordSearchs(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<FunnyClientKeywordSearch> values = (resp == null || resp.getData() == null || resp.getData()
                .getResultList() == null) ? null : resp.getData().getResultList();
        List<Integer> rootIds = initRootIds(values);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimpleResp = this.backendApiService.getAppSimple(rootIds);
        if (appSimpleResp != null && appSimpleResp.getData() != null) {
            Map<String, ApplicationSimple> datas = new HashMap<String, ApplicationSimple>();
            for (Entry<Integer, ApplicationSimple> entry : appSimpleResp.getData().entrySet()) {
                datas.put(entry.getKey().toString(), entry.getValue());
            }
            model.addAttribute("rootSimples", datas);
        }
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", FunnyClientKeywordSearch.STATUS);
        model.addAttribute("para", para);
        return "store/funny/column/common/keyword/list.ftl";
    }

    @RequestMapping(value = "/keywordsearch/add")
    public String addKeywordSearch(Integer rootId, String keyword, RedirectAttributes rattrs) {
        String errMsg = "";
        FunnyClientKeywordSearchParameter para = null;
        if (rootId == null) {
            errMsg = "应用id不能为空";
        }
        if (StringUtils.isEmpty(errMsg)) {
            para = new FunnyClientKeywordSearchParameter();
            para.setRootId(rootId);
            para.setKeyword(keyword);
        }
        ApiRespWrapper<Boolean> resp = null;
        if (StringUtils.isEmpty(errMsg)) {
            resp = this.funnyClientApiService.addFunnyClientKeywordSearch(para);
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
        return "redirect:/funny/client/common/keywordsearch/list";
    }

    @RequestMapping(value = "/keywordsearch/modify")
    @ResponseBody
    public String modifyKeywordSearch(Integer id, String keyword, Integer status) {
        if (id == null) {
            return "未知的分类id";
        }
        FunnyClientKeywordSearchParameter para = new FunnyClientKeywordSearchParameter();
        para.setId(id);
        para.setKeyword(keyword);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = this.funnyClientApiService.modifyFunnyClientKeywordSearch(para);
        if (resp == null) {
            return "未知错误";
        } else if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    private List<Integer> initRootIds(List<FunnyClientKeywordSearch> values) {
        Set<Integer> ret = new HashSet<Integer>();
        for (FunnyClientKeywordSearch value : values) {
            ret.add(value.getRootId());
        }
        return new ArrayList<Integer>(ret);
    }
}
