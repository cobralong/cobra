package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.appchina.ios.core.crawler.model.AuthoAppDownBoughtInfoResp;
import com.appchina.ios.core.dto.app.AppTag;
import com.appchina.ios.core.dto.app.AppTagRank;
import com.appchina.ios.core.dto.app.AppTagRankIntervention;
import com.appchina.ios.mgnt.controller.model.AppTagListParameter;
import com.appchina.ios.mgnt.controller.model.AppTagRankListParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;

@Controller
@RequestMapping(value = "/auth/tagrank/*")
public class AppTagRankController {
    private static final Logger log = Logger.getLogger(AppTagRankController.class);
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/list")
    public String list(AppTagRankListParameter para, Model model) {
        if (para == null) {
            para = new AppTagRankListParameter();
        }
        String errMsg = "";
        ApiRespWrapper<Map<String, String>> typeResp = backendApiService.mapAppTagRankTypeDictionary();
        if (typeResp == null || CollectionUtils.emptyOrNull(typeResp.getData())) {
            errMsg = "数据错误，不存在AppTag排行榜类别字典数据.";
            para.setErrMsg(errMsg);
            model.addAttribute("para", para);
            return "tagrank/list.ftl";
        }
        Map<String, String> types = typeResp.getData();
        ApiRespWrapper<ListWrapResp<AppTag>> categoryResp = backendApiService.listAppTags();
        if (categoryResp == null || categoryResp.getData() == null
                || CollectionUtils.emptyOrNull(categoryResp.getData().getResultList())) {
            errMsg = "数据错误，不存在AppTag分类类别字典数据.";
            para.setErrMsg(errMsg);
            model.addAttribute("para", para);
            return "tagrank/list.ftl";
        }
        List<AppTag> categorys = categoryResp.getData().getResultList();
        para.transfer(types, categorys);
        ApiRespWrapper<ListWrapResp<AppTagRank>> valueResp = backendApiService.listAppTagRank(para);
        List<AppTagRank> values = null;
        Map<String, ApplicationSimple> rootSimples = null;
        Map<String, AuthoAppDownBoughtInfoResp> statusRespMaps = null;
        List<AuthoAppDownBoughtInfoResp> statusResps = null;
        if (valueResp != null && valueResp.getData() != null && valueResp.getData().getResultList() != null) {
            para.getPager().setTotal(valueResp.getData().getTotalCount());
            values = valueResp.getData().getResultList();
            List<Integer> rootIds = initAppTagRankRootIds(valueResp.getData().getResultList());
            ApiRespWrapper<Map<Integer, ApplicationSimple>> simpleResp = this.backendApiService.getAppSimple(rootIds);
            //TODO
            ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> infoStatusList = this.backendApiService
                    .getAuthorizerAppDownAndBuyInfoStatusList(rootIds);
            if (simpleResp != null && simpleResp.getData() != null) {
                rootSimples = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : simpleResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
            }
            if (infoStatusList != null && infoStatusList.getData() != null
                    && infoStatusList.getData().getResultList() != null) {
                statusRespMaps = new HashMap<String, AuthoAppDownBoughtInfoResp>();
                statusResps = infoStatusList.getData().getResultList();
                for (AuthoAppDownBoughtInfoResp resp : statusResps) {
                    statusRespMaps.put(String.valueOf(resp.getRootId()), resp);
                }
            }
        }
        model.addAttribute("values", values);
        model.addAttribute("rootSimples", rootSimples);
        model.addAttribute("statusRespMaps", statusRespMaps);
        model.addAttribute("types", types);
        model.addAttribute("status", DbStatus.STATUS);
        model.addAttribute("categorys", buildAppTag(categorys));
        model.addAttribute("para", para);
        return "tagrank/list.ftl";
    }

    @RequestMapping(value = "/tag/list")
    public String taglist(AppTagListParameter para, Model model) {
        String errMsg = "";
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppTag>> tagResp = backendApiService.listChildTags(para);
        if (tagResp == null || tagResp.getData() == null) {
            errMsg = "数据错误，获取子标签列表出错.";
            para.setErrMsg(errMsg);
            model.addAttribute("para", para);
            return "tagrank/tag/list.ftl";
        }
        List<AppTag> values = tagResp.getData().getResultList();

        ApiRespWrapper<ListWrapResp<AppTag>> topResp = backendApiService.listChildTags(null);
        if (topResp == null || topResp.getData() == null
                || CollectionUtils.emptyOrNull(topResp.getData().getResultList())) {
            errMsg = "数据错误，获取顶层标签列表出错.";
            para.setErrMsg(errMsg);
            model.addAttribute("para", para);
            return "tagrank/tag/list.ftl";
        }
        List<AppTag> toptagList = topResp.getData().getResultList();
        Map<String, String> toptagMap = new HashMap<String, String>();
        toptagMap.put("0", "顶层标签");
        for (AppTag toptag : toptagList) {
            toptagMap.put(String.valueOf(toptag.getId()), toptag.getName());
        }

        model.addAttribute("values", values);
        model.addAttribute("tagMap", toptagMap);
        if (para.getParent() == null) {
            para.setParent(0);
        }
        model.addAttribute("para", para);
        model.addAttribute("status", DbStatus.STATUS);
        return "tagrank/tag/list.ftl";
    }

    @RequestMapping(value = "/intervention/list")
    public String interventionlist(AppTagRankListParameter para, @ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("type") String type, @ModelAttribute("cid") String cid, Model model) {
        if (para == null) {
            para = new AppTagRankListParameter();
        }
        if (!StringUtils.isEmpty(errMsg)) {
            para.setErrMsg(errMsg);
        }
        ApiRespWrapper<Map<String, String>> typeResp = backendApiService.mapAppTagRankTypeDictionary();
        if (typeResp == null || CollectionUtils.emptyOrNull(typeResp.getData())) {
            errMsg = "数据错误，不存在AppTag排行榜类别字典数据.";
        }
        Map<String, String> types = typeResp.getData();
        ApiRespWrapper<ListWrapResp<AppTag>> categoryResp = backendApiService.listAppTags();
        if (categoryResp == null || categoryResp.getData() == null
                || CollectionUtils.emptyOrNull(categoryResp.getData().getResultList())) {
            errMsg = "数据错误，不存在AppTag分类类别字典数据.";
        }
        List<AppTag> categorys = categoryResp.getData().getResultList();
        para.setErrMsg(errMsg);
        if (StringUtils.isEmpty(cid)) {
            para.transfer(types, categorys);
        } else {
            para.transfer(Integer.parseInt(type), Integer.parseInt(cid));
        }
        ApiRespWrapper<ListWrapResp<AppTagRankIntervention>> valueResp = backendApiService
                .listAppTagRankInterventions(para);
        List<AppTagRankIntervention> values = null;
        Map<String, ApplicationSimple> rootSimples = null;
        Map<String, AuthoAppDownBoughtInfoResp> statusRespMaps = null;
        List<AuthoAppDownBoughtInfoResp> statusResps = null;
        if (valueResp != null && valueResp.getData() != null && valueResp.getData().getResultList() != null) {
            para.getPager().setTotal(valueResp.getData().getTotalCount());
            values = valueResp.getData().getResultList();
            List<Integer> rootIds = initAppTagRankInterventionRootIds(valueResp.getData().getResultList());
            ApiRespWrapper<Map<Integer, ApplicationSimple>> simpleResp = this.backendApiService.getAppSimple(rootIds);
            //TODO
            ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> infoStatusList = this.backendApiService
                    .getAuthorizerAppDownAndBuyInfoStatusList(rootIds);
            if (simpleResp != null && simpleResp.getData() != null) {
                rootSimples = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : simpleResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
            }
            if (infoStatusList != null && infoStatusList.getData() != null
                    && infoStatusList.getData().getResultList() != null) {
                statusRespMaps = new HashMap<String, AuthoAppDownBoughtInfoResp>();
                statusResps = infoStatusList.getData().getResultList();
                for (AuthoAppDownBoughtInfoResp resp : statusResps) {
                    statusRespMaps.put(String.valueOf(resp.getRootId()), resp);
                }
            }
        }
        model.addAttribute("values", values);
        model.addAttribute("rootSimples", rootSimples);
        model.addAttribute("statusRespMaps", statusRespMaps);
        model.addAttribute("types", types);
        model.addAttribute("status", DbStatus.STATUS);
        model.addAttribute("categorys", buildAppTag(categorys));
        model.addAttribute("para", para);
        return "tagrank/interception/list.ftl";
    }

    private Map<String, String> buildAppTag(List<AppTag> appTags) {
        Map<String, String> ret = new LinkedHashMap<String, String>(appTags.size(), 1f);
        for (AppTag appTag : appTags) {
            if (appTag.getParent() == null) {
                ret.put(String.valueOf(appTag.getId()), appTag.getName());
            }
        }
        for (AppTag appTag : appTags) {
            if (appTag.getParent() != null) {
                String parentName = ret.get(appTag.getParent().toString());
                if (StringUtils.isEmpty(parentName)) {
                    continue;
                }
                ret.put(String.valueOf(appTag.getId()), parentName + "---" + appTag.getName());
            }
        }
        return ret;
    }

    @RequestMapping(value = "/intervention/modify", method = RequestMethod.POST)
    @ResponseBody
    public String interventionmodify(int id, int status) {
        if (id <= 0) {
            return "id无效.";
        }
        AppTagRankListParameter para = new AppTagRankListParameter(id, status);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyAppTagRankInterventionStatus(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/intervention/modifyrank", method = RequestMethod.POST)
    @ResponseBody
    public String interventionmodifyrank(int id, int rank) {
        if (id <= 0) {
            return "id无效.";
        }
        AppTagRankListParameter para = new AppTagRankListParameter();
        para.setId(id);
        para.setRank(rank);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyAppTagRankInterventionRank(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/intervention/add", method = RequestMethod.POST)
    public String interventionAdd(Integer rootId, Integer type, Integer cid, Integer rank, RedirectAttributes rattrs) {
        rattrs.addFlashAttribute("type", type == null ? null : type.toString());
        rattrs.addFlashAttribute("cid", cid == null ? null : cid.toString());
        String errMsg = checkAndFormatAddParameter(rootId, type, cid, rank);
        if (StringUtils.isEmpty(errMsg)) {
            AppTagRankListParameter para = new AppTagRankListParameter(rootId, type, cid, rank);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addAppTagRankInterventions(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add app tag rank intervention failed. Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/tagrank/intervention/list";
    }


    private String checkAndFormatAddParameter(Integer rootId, Integer type, Integer cid, Integer rank) {
        String errMsg = "";
        if (rank == null || rank < 0) {
            errMsg = "推荐位置未定，请设置";
        }
        if (StringUtils.isEmpty(errMsg) && type == null) {
            errMsg = "未知的排行榜类型.";
        }
        if (StringUtils.isEmpty(errMsg) && cid == null) {
            errMsg = "未知的分类类别.";
        }
        if (StringUtils.isEmpty(errMsg) && (rootId == null || rootId.intValue() <= 0 || !isOnline(rootId))) {
            errMsg = "无法推荐一个未上架的应用";
        }
        return errMsg;
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    private List<Integer> initAppTagRankInterventionRootIds(List<AppTagRankIntervention> list) {
        List<Integer> ret = new ArrayList<Integer>();
        for (AppTagRankIntervention appRankIntervention : list) {
            ret.add(appRankIntervention.getRootId());
        }
        return ret;
    }

    private List<Integer> initAppTagRankRootIds(List<AppTagRank> list) {
        List<Integer> ret = new ArrayList<Integer>();
        for (AppTagRank appTagRank : list) {
            ret.add(appTagRank.getRootId());
        }
        return ret;
    }
}
