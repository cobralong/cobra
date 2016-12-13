package com.appchina.ios.mgnt.controller;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.app.AppAppleRank;
import com.appchina.ios.core.dto.app.AppLocale;
import com.appchina.ios.core.dto.app.AppRank;
import com.appchina.ios.core.dto.app.AppRankIntervention;
import com.appchina.ios.mgnt.controller.model.AppAppleRankListParameter;
import com.appchina.ios.mgnt.controller.model.AppLocaleParameter;
import com.appchina.ios.mgnt.controller.model.AppRankListParameter;
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
import java.util.Map.Entry;

@Controller
@RequestMapping(value = "/auth/rank/*")
public class RankController {
    private static final Logger log = Logger.getLogger(RankController.class);
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/list")
    public String list(AppRankListParameter para, Model model) {
        if (para == null) {
            para = new AppRankListParameter();
        }
        String errMsg = "";
        ApiRespWrapper<Map<String, String>> typeResp = backendApiService.mapRankTypeDictionary();
        if (typeResp == null || CollectionUtils.emptyOrNull(typeResp.getData())) {
            errMsg = "数据错误，不存在排行榜类别字典数据.";
        }
        Map<String, String> types = typeResp.getData();
        ApiRespWrapper<Map<String, String>> categoryResp = backendApiService.mapCategoryDictionary();
        if (categoryResp == null || CollectionUtils.emptyOrNull(categoryResp.getData())) {
            errMsg = "数据错误，不存在分类类别字典数据.";
        }
        Map<String, String> categorys = categoryResp.getData();
        para.setErrMsg(errMsg);
        para.transfer(types, categorys);
        ApiRespWrapper<ListWrapResp<AppRank>> valueResp = backendApiService.listAppRank(para);
        List<AppRank> values = null;
        Map<String, ApplicationSimple> rootSimples = null;
        if (valueResp != null && valueResp.getData() != null && valueResp.getData().getResultList() != null) {
            para.getPager().setTotal(valueResp.getData().getTotalCount());
            values = valueResp.getData().getResultList();
            List<Integer> rootIds = initAppRankRootIds(valueResp.getData().getResultList());
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
        model.addAttribute("categorys", new LinkedHashMap<String, String>(categorys));
        model.addAttribute("para", para);
        return "rank/list.ftl";
    }

    @RequestMapping(value = "/intervention/list")
    public String interventionlist(AppRankListParameter para, @ModelAttribute("errMsg") String errMsg,
                                   @ModelAttribute("rstart") String rstart, @ModelAttribute("rsize") String rsize,
                                   @ModelAttribute("type") String type, @ModelAttribute("cid") String cid, Model model) {
        if (para == null) {
            para = new AppRankListParameter();
        }
        if (!StringUtils.isEmpty(errMsg)) {
            para.setErrMsg(errMsg);
        }
        if (StringUtils.isNotBlank(rstart)) {
            para.setStart(Integer.valueOf(rstart));
        }
        if (StringUtils.isNotBlank(rsize)) {
            para.setSize(Integer.valueOf(rsize));
        }
        ApiRespWrapper<Map<String, String>> typeResp = backendApiService.mapRankTypeDictionary();
        if (typeResp == null || CollectionUtils.emptyOrNull(typeResp.getData())) {
            errMsg = "数据错误，不存在排行榜类别字典数据.";
        }
        Map<String, String> types = typeResp.getData();
        ApiRespWrapper<Map<String, String>> categoryResp = backendApiService.mapCategoryDictionary();
        if (categoryResp == null || CollectionUtils.emptyOrNull(categoryResp.getData())) {
            errMsg = "数据错误，不存在分类类别字典数据.";
        }
        Map<String, String> categorys = categoryResp.getData();
        para.setErrMsg(errMsg);
        if (StringUtils.isEmpty(cid)) {
            para.transfer(types, categorys);
        } else {
            para.transfer(Integer.parseInt(type), Integer.parseInt(cid));
        }
        List<AppRankIntervention> values = null;
        ApiRespWrapper<ListWrapResp<AppRankIntervention>> valueResp = null;
        if (para.getRootId() == null) {
            valueResp = backendApiService.listAppRankInterventions(para);

        } else {
            valueResp = backendApiService.listAppRankInterventionByRootId(para);

        }
        if (valueResp != null && valueResp.getData() != null && valueResp.getData().getResultList() != null) {
            para.getPager().setTotal(valueResp.getData().getTotalCount());
            values = valueResp.getData().getResultList();
        }
        Map<String, ApplicationSimple> rootSimples = null;
        if (values != null) {
            List<Integer> rootIds = initAppRankInterventionRootIds(values);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> simpleResp = this.backendApiService.getAppSimple(rootIds);
            if (simpleResp != null && simpleResp.getData() != null) {
                rootSimples = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : simpleResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
            }
            if (values.size() == 1) {
                para.setStatus(values.get(0).getStatus());
            }
        }
        model.addAttribute("values", values);
        model.addAttribute("rootSimples", rootSimples);
        model.addAttribute("types", types);
        model.addAttribute("status", DbStatus.STATUS);
        model.addAttribute("categorys", new LinkedHashMap<String, String>(categorys));
        model.addAttribute("para", para);
        return "rank/interception/list.ftl";
    }

    @RequestMapping(value = "/intervention/modify", method = RequestMethod.POST)
    @ResponseBody
    public String interventionmodify(int id, int status) {
        if (id <= 0) {
            return "id无效.";
        }
        AppRankListParameter para = new AppRankListParameter(id, status);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyAppRankInterventions(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/intervention/modifyrank", method = RequestMethod.POST)
    public String interventionmodifyrank(AppRankListParameter para, RedirectAttributes rattrs) {
        String errMsg = "";
        rattrs.addAttribute("rstart", para.getStart());
        rattrs.addAttribute("rsize", para.getSize());
        rattrs.addAttribute("cid", para.getCid());
        rattrs.addAttribute("type", para.getType());
        if (para.getId() <= 0) {
            errMsg = "id无效.";
        } else if (para.getRank() <= 0) {
            errMsg = "rank无效";
        }
        if (StringUtils.isNotBlank(errMsg)) {
            rattrs.addAttribute("errMsg", errMsg);
            return "redirect:/auth/rank/intervention/list";
        }
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyAppRankInterventionRank(para);
        if (resp == null) {
            errMsg = "未知错误.";
        } else if (resp.getData() == null || !resp.getData()) {
            errMsg = resp.getMessage();
        }
        rattrs.addAttribute("errMsg", errMsg);
        return "redirect:/auth/rank/intervention/list";
    }

    @RequestMapping(value = "/intervention/add", method = RequestMethod.POST)
    public String interventionAdd(Integer rootId, Integer type, Integer cid, Integer rank, RedirectAttributes rattrs) {
        rattrs.addFlashAttribute("type", type == null ? null : type.toString());
        rattrs.addFlashAttribute("cid", cid == null ? null : cid.toString());
        String errMsg = checkAndFormatAddParameter(rootId, type, cid, rank);
        if (StringUtils.isEmpty(errMsg)) {
            AppRankListParameter para = new AppRankListParameter(rootId, type, cid, rank);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addAppRankInterventions(para);
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
        return "redirect:/auth/rank/intervention/list";
    }

    /**
     * 获取具体国家排行榜
     *
     * @return
     */
    @RequestMapping(value = "/globallocale/rank")
    protected String globallocaleranks(AppAppleRankListParameter para, Model model) {
        model.addAttribute("feedtypes", AppAppleRank.FEEDTYPES);
        Map<String, String> locales = new LinkedHashMap<String, String>();
        ApiRespWrapper<ListWrapResp<AppLocale>> appLocalesResp = backendApiService.listGlobalLocales();
        if (appLocalesResp != null && appLocalesResp.getData() != null) {
            for (AppLocale appLocale : appLocalesResp.getData().getResultList()) {
                locales.put(Integer.toString(appLocale.getId()), appLocale.getName());
            }
        }
        model.addAttribute("locales", locales);
        if (para == null || para.getFeedType() == null || para.getLocale() == null) {
            model.addAttribute("para", para);
            return "rank/globallocale/rank.ftl";
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppAppleRank>> appSimplesResp = backendApiService.listGlobalLocaleRankApps(para);
        if (appSimplesResp != null && appSimplesResp.getData() != null) {
            para.getPager().setTotal(appSimplesResp.getData().getTotalCount());
            model.addAttribute("values", appSimplesResp.getData().getResultList());
        }

        Map<String, ApplicationSimple> rootSimples = new HashMap<String, ApplicationSimple>();
        if (appSimplesResp != null && appSimplesResp.getData() != null
                && appSimplesResp.getData().getResultList() != null) {
            List<Integer> rootIds = initRootIds(appSimplesResp.getData().getResultList());
            ApiRespWrapper<Map<Integer, ApplicationSimple>> simpleResp = this.backendApiService.getAppSimple(rootIds);
            if (simpleResp != null && simpleResp.getData() != null) {
                for (Entry<Integer, ApplicationSimple> entry : simpleResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
            }
        }
        model.addAttribute("rootSimples", rootSimples);
        model.addAttribute("para", para);
        return "rank/globallocale/rank.ftl";
    }

    private List<Integer> initRootIds(List<AppAppleRank> list) {
        List<Integer> ret = new ArrayList<Integer>();
        for (AppAppleRank appRankIntervention : list) {
            if (appRankIntervention.getRootId() != null) {
                ret.add(appRankIntervention.getRootId());
            }
        }
        return ret;
    }

    /**
     * 获取全球排行榜国家列表
     *
     * @return
     */
    @RequestMapping(value = "/globallocale/list")
    protected String globallocales(@ModelAttribute("errMsg") String errMsg, Model model) {
        ApiRespWrapper<ListWrapResp<AppLocale>> appLocalesResp = backendApiService.listGlobalLocales();
        if (appLocalesResp != null && appLocalesResp.getData() != null) {
            model.addAttribute("values", appLocalesResp.getData().getResultList());
        }
        model.addAttribute("errMsg", errMsg);
        return "rank/globallocale/list.ftl";
    }

    @RequestMapping(value = "/globallocale/modify", method = RequestMethod.POST)
    public String modifygloballocale(Integer id, Integer status, Integer position) {
        if (id == null || id <= 0) {
            return "id无效.";
        }
        AppLocaleParameter para = new AppLocaleParameter();
        para.setId(id);
        para.setStatus(status);
        para.setPosition(position);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyGlobalLocale(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "redirect:/auth/rank/globallocale/list";
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

    private List<Integer> initAppRankInterventionRootIds(List<AppRankIntervention> list) {
        List<Integer> ret = new ArrayList<Integer>();
        for (AppRankIntervention appRankIntervention : list) {
            ret.add(appRankIntervention.getRootId());
        }
        return ret;
    }

    private List<Integer> initAppRankRootIds(List<AppRank> list) {
        List<Integer> ret = new ArrayList<Integer>();
        for (AppRank appRankIntervention : list) {
            ret.add(appRankIntervention.getRootId());
        }
        return ret;
    }
}
