package com.appchina.ios.mgnt.controller;

import com.appchina.ios.core.cahe.model.app.RootAppRankSimple;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.crawler.model.AuthoAppDownBoughtInfoResp;
import com.appchina.ios.core.dto.app.Category;
import com.appchina.ios.core.dto.app.RootAppRankIntervention;
import com.appchina.ios.core.dto.app.RootAppRankMetaMap;
import com.appchina.ios.core.dto.app.RootAppRankMetaType;
import com.appchina.ios.mgnt.controller.model.RootAppRankAddParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankInterventionParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankInterventionUpdateParameter;
import com.appchina.ios.mgnt.controller.model.RootAppRankParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;

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

import java.util.*;

/**
 * Created by zhouyanhui on 12/3/15.
 */
@Controller
@RequestMapping(value = "/auth/rootrank/*")
public class RootAppRankController {
    private static final Logger log = Logger.getLogger(RootAppRankController.class);
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/querysrc")
    @ResponseBody
    public Map<String, String> querySrc(int srcType, Model model) {
        Map<String, String> srcMap = fetchRankSrcMap(srcType);
        return srcMap;
    }

    @RequestMapping(value = "/list")
    public String list(RootAppRankParameter para, Model model,
                       @ModelAttribute("errMsg") String preErrMsg,
                       @ModelAttribute("srcId") String srcId,
                       @ModelAttribute("srcType") String srcType,
                       @ModelAttribute("rankId") String rankId) {
        if (para == null) {
            para = new RootAppRankParameter(RootAppRankMetaMap.RankSrcType.CATEGORY.getValue(),
                    Category.GAME_PARENT_ID, RootAppRankMetaType.LATEST_RANK_ID);
        }
        if (StringUtils.isNotBlank(srcId) && StringUtils.isNotBlank(srcType) && StringUtils.isNotBlank(rankId)) {
            para.setRankId(Integer.parseInt(rankId));
            para.setSrcId(Integer.parseInt(srcId));
            para.setSrcType(Integer.parseInt(srcType));
        }
        para.transfer();
        Map<String, String> rankSrcTypes = fetchRankSrcTypes();
        Map<String, String> rankTypes = fetchRankTypes();
        String errMsg = preErrMsg;
        if (rankTypes == null || CollectionUtils.emptyOrNull(rankTypes)) {
            errMsg = "数据错误，排行榜类别为空.";
        }
        if (!rankSrcTypes.containsKey(String.valueOf(para.getSrcType())) ||
                !rankTypes.containsKey(String.valueOf(para.getRankId()))) {
            errMsg = "参数错误.";
        }
        if (StringUtils.isNotBlank(errMsg)) {
            para.setErrMsg(errMsg);
            model.addAttribute("para", para);
            return "rootapprank/list.ftl";
        }
        Map<String, String> srcMap = fetchRankSrcMap(para.getSrcType());
        if (rankTypes == null || CollectionUtils.emptyOrNull(rankTypes)) {
            errMsg = "数据错误，排行榜对应来源种类为空.";
        }
        if (StringUtils.isNotBlank(errMsg)) {
            para.setErrMsg(errMsg);
            model.addAttribute("para", para);
            return "rootapprank/list.ftl";
        }

        ApiRespWrapper<ListWrapResp<RootAppRankSimple>> rankAppsResp = backendApiService.listRootAppRanks(para);
        List<RootAppRankSimple> values = null;
        Map<String, ApplicationSimple> rootSimples = null;
        Map<String, AuthoAppDownBoughtInfoResp> statusRespMaps = null;
        List<AuthoAppDownBoughtInfoResp> statusResps = null;
        if (rankAppsResp != null && rankAppsResp.getData() != null && rankAppsResp.getData().getResultList() != null) {
            para.getPager().setTotal(rankAppsResp.getData().getTotalCount());
            values = rankAppsResp.getData().getResultList();
            List<Integer> rootIds = fetchRootIds(rankAppsResp.getData().getResultList());
            ApiRespWrapper<Map<Integer, ApplicationSimple>> simpleResp = this.backendApiService.getAppSimple(rootIds);
          //TODO
            ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> infoStatusList = this.backendApiService
                    .getAuthorizerAppDownAndBuyInfoStatusList(rootIds);
            if (simpleResp != null && simpleResp.getData() != null) {
                rootSimples = new HashMap<String, ApplicationSimple>();
                for (Map.Entry<Integer, ApplicationSimple> entry : simpleResp.getData().entrySet()) {
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
        model.addAttribute("rankSrcTypes", rankSrcTypes);
        model.addAttribute("rankTypes", rankTypes);
        model.addAttribute("srcMap", srcMap);
        model.addAttribute("para", para);
        return "rootrank/list.ftl";
    }

    private Map<String, String> fetchRankSrcMap(int srcType) {
        if (srcType == RootAppRankMetaMap.RankSrcType.CATEGORY.getValue()) {
            ApiRespWrapper<Map<String, String>> categoryResp = backendApiService.mapCategoryDictionary();
            if (categoryResp != null && !CollectionUtils.emptyOrNull(categoryResp.getData())) {
                return categoryResp.getData();
            }
        } else if (srcType == RootAppRankMetaMap.RankSrcType.TAG.getValue()) {
            ApiRespWrapper<Map<String, String>> tagResp = backendApiService.mapAppTagCategoryDictionary();
            if (tagResp != null && !CollectionUtils.emptyOrNull(tagResp.getData())) {
                return tagResp.getData();
            }
        }
        return Collections.emptyMap();
    }

    private List<Integer> fetchRootIds(List<RootAppRankSimple> resultList) {
        List<Integer> rootIds = new ArrayList<Integer>();
        for (RootAppRankSimple rootAppRankSimple : resultList) {
            rootIds.add(rootAppRankSimple.getRootId());
        }
        return rootIds;
    }

    private Map<String, String> fetchRankTypes() {
        ApiRespWrapper<Map<String, String>> mapApiRespWrapper = backendApiService.fetchRootAppRankMetaTypes();
        if (mapApiRespWrapper != null && mapApiRespWrapper.getData() != null) {
            return mapApiRespWrapper.getData();
        }
        return Collections.emptyMap();
    }

    private Map<String, String> fetchRankSrcTypes() {
        Map<String, String> rankSrcTypeMap = new HashMap<String, String>();
        for (RootAppRankMetaMap.RankSrcType rankSrcType : RootAppRankMetaMap.RankSrcType.values()) {
            rankSrcTypeMap.put(String.valueOf(rankSrcType.getValue()), rankSrcType.getDesc());
        }
        return rankSrcTypeMap;
    }

    @RequestMapping(value = "/intervention/list")
    public String interlist(RootAppRankInterventionParameter para, Model model,
                            @ModelAttribute("errMsg") String preErrMsg,
                            @ModelAttribute("srcId") String srcId,
                            @ModelAttribute("srcType") String srcType,
                            @ModelAttribute("rankId") String rankId,
                            @ModelAttribute("status") String status) {
        if (para == null) {
            para = new RootAppRankInterventionParameter(RootAppRankMetaMap.RankSrcType.CATEGORY.getValue(),
                    Category.GAME_PARENT_ID, RootAppRankMetaType.LATEST_RANK_ID);
        }
        if (StringUtils.isNotBlank(srcId) && StringUtils.isNotBlank(srcType) && StringUtils.isNotBlank(rankId)) {
            para.setRankId(Integer.parseInt(rankId));
            para.setSrcId(Integer.parseInt(srcId));
            para.setSrcType(Integer.parseInt(srcType));
        }
        if (StringUtils.isNotBlank(status)) {
            para.setStatus(Integer.parseInt(status));
        }
        para.transfer();
        String errMsg = preErrMsg;
        Map<String, String> rankSrcTypes = fetchRankSrcTypes();
        Map<String, String> rankTypes = fetchRankTypes();
        if (rankTypes == null || CollectionUtils.emptyOrNull(rankTypes)) {
            errMsg = "数据错误，排行榜类别为空.";
        }
        if (!rankSrcTypes.containsKey(String.valueOf(para.getSrcType())) ||
                !rankTypes.containsKey(String.valueOf(para.getRankId()))) {
            errMsg = "参数错误.";
        }
        if (StringUtils.isNotBlank(errMsg)) {
            para.setErrMsg(errMsg);
            model.addAttribute("para", para);
            return "rootrank/interlist.ftl";
        }
        Map<String, String> srcMap = fetchRankSrcMap(para.getSrcType());
        if (rankTypes == null || CollectionUtils.emptyOrNull(rankTypes)) {
            errMsg = "数据错误，排行榜对应来源种类为空.";
        }
        if (StringUtils.isNotBlank(errMsg)) {
            para.setErrMsg(errMsg);
            model.addAttribute("para", para);
            return "rootapprank/list.ftl";
        }

        ApiRespWrapper<ListWrapResp<RootAppRankSimple>> rankAppsResp = backendApiService.listRootAppRankInterventions(para);
        List<RootAppRankSimple> values = null;
        Map<String, ApplicationSimple> rootSimples = null;
        Map<String, AuthoAppDownBoughtInfoResp> statusRespMaps = null;
        List<AuthoAppDownBoughtInfoResp> statusResps = null;
        if (rankAppsResp != null && rankAppsResp.getData() != null && rankAppsResp.getData().getResultList() != null) {
            para.getPager().setTotal(rankAppsResp.getData().getTotalCount());
            values = rankAppsResp.getData().getResultList();
            List<Integer> rootIds = fetchRootIds(rankAppsResp.getData().getResultList());
            ApiRespWrapper<Map<Integer, ApplicationSimple>> simpleResp = this.backendApiService.getAppSimple(rootIds);
          //TODO
            ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> infoStatusList = this.backendApiService
                    .getAuthorizerAppDownAndBuyInfoStatusList(rootIds);
            if (simpleResp != null && simpleResp.getData() != null) {
                rootSimples = new HashMap<String, ApplicationSimple>();
                for (Map.Entry<Integer, ApplicationSimple> entry : simpleResp.getData().entrySet()) {
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
        model.addAttribute("rankSrcTypes", rankSrcTypes);
        model.addAttribute("rankTypes", rankTypes);
        model.addAttribute("srcMap", srcMap);
        model.addAttribute("status", RootAppRankIntervention.STATUS);
        model.addAttribute("para", para);
        return "rootrank/interlist.ftl";
    }

    @RequestMapping(value = "/intervention/modify", method = RequestMethod.POST)
    @ResponseBody
    public String interventionmodify(int interventId, int status) {
        if (interventId <= 0) {
            return "id无效.";
        }
        RootAppRankInterventionUpdateParameter para = new RootAppRankInterventionUpdateParameter(interventId, status, null);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyRootAppRankIntervention(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/intervention/modifypos", method = RequestMethod.POST)
    @ResponseBody
    public String interventionmodifyrank(int interventId, int pos) {
        if (interventId <= 0 || pos <= 0) {
            return "参数错误";
        }
        RootAppRankInterventionUpdateParameter para = new RootAppRankInterventionUpdateParameter(interventId, null, pos);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyRootAppRankIntervention(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/intervention/add", method = RequestMethod.POST)
    public String interventionAdd(Integer rootId, Integer srcId, Integer srcType,
                                  Integer rankId, Integer pos, String refUrl,
                                  RedirectAttributes rattrs) {
        rattrs.addFlashAttribute("srcId", srcId == null ? null : srcId.toString());
        rattrs.addFlashAttribute("srcType", srcType == null ? null : srcType.toString());
        rattrs.addFlashAttribute("rankId", rankId == null ? null : rankId.toString());
        String errMsg = checkAndFormatAddParameter(rootId, srcId, srcType, rankId, pos);
        if (StringUtils.isEmpty(errMsg)) {
            RootAppRankAddParameter para = new RootAppRankAddParameter(rootId, srcId, srcType, rankId, pos);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addRootAppRankInterventions(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add app rank intervention failed. Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:" + refUrl;
    }

    /**
     * remove APP from rank list
     *
     * @param rootId
     * @param srcId
     * @param srcType
     * @param rankId
     * @param rattrs
     * @return
     */
    @RequestMapping(value = "/intervention/remove", method = RequestMethod.POST)
    @ResponseBody
    public String interventionAdd(Integer rootId, Integer srcId, Integer srcType,
                                  Integer rankId) {
        int pos = 0;
        int status = -2;
        String errMsg = checkAndFormatAddParameter(rootId, srcId, srcType, rankId);
        if (StringUtils.isNotBlank(errMsg)) {
            return errMsg;
        }
        RootAppRankAddParameter para = new RootAppRankAddParameter(rootId, srcId, srcType, rankId, pos, status);
        try {
            ApiRespWrapper<Boolean> resp = this.backendApiService.addRootAppRankInterventions(para);
            if (resp == null) {
                return "未知错误";
            } else if (!resp.getData()) {
                return resp.getMessage();
            }
        } catch (Exception e) {
            log.error("Add app rank intervention failed. Data:" + para + ",errMsg:" + e.getMessage(), e);
            return "添加干预失败";
        }
        return "";
    }

    private String checkAndFormatAddParameter(Integer rootId, Integer srcId,
                                              Integer srcType, Integer rankId, Integer pos) {
        String errMsg = "";
        if (pos == null || pos <= 0) {
            errMsg = "干预位置未定，请设置";
        }
        if (StringUtils.isEmpty(errMsg) && rankId == null) {
            errMsg = "未知的排行类型.";
        }
        if (StringUtils.isEmpty(errMsg) && (srcId == null || srcType == null)) {
            errMsg = "未知的排行类别.";
        }
        if (StringUtils.isEmpty(errMsg) && (rootId == null || rootId.intValue() <= 0 || !isOnline(rootId))) {
            errMsg = "无法推荐一个未上架的应用";
        }
        return errMsg;
    }

    private String checkAndFormatAddParameter(Integer rootId, Integer srcId,
                                              Integer srcType, Integer rankId) {
        String errMsg = "";
        if (rankId == null) {
            errMsg = "未知的排行类型.";
        }
        if (StringUtils.isEmpty(errMsg) && (srcId == null || srcType == null)) {
            errMsg = "未知的排行类别.";
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
}
