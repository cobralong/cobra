package com.appchina.ios.mgnt.controller.info;

import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.info.AppTagListIntervention;
import com.appchina.ios.mgnt.controller.model.info.StoreAppTagListInterventionParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
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

import java.util.*;

/**
 * Created by zhouyanhui on 8/18/15.
 */
@Controller
@RequestMapping(value = "/auth/store/apptag/*")
public class StoreAppTagInterventionController {
    private static final Logger log = Logger.getLogger(StoreAppTagInterventionController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/invadd", method = RequestMethod.GET)
    public String add(Model model) {
        return "redirect:/auth/store/apptag/invlist";
    }

    @RequestMapping("/invlist")
    public String list(@ModelAttribute("errMsg") String errMsg, @ModelAttribute("appTagId") String appTagId,
            StoreAppTagListInterventionParameter para, Model model) {
        para.transfer(StringUtils.isEmpty(appTagId) ? null : Integer.valueOf(appTagId));
        ApiRespWrapper<Map<String, String>> allAppTagsResp = iosStoreApiService.fetchAllAppTags();
        Map<String, String> storeAppTags = allAppTagsResp.getData();
        model.addAttribute("storeAppTags", storeAppTags);

        ApiRespWrapper<ListWrapResp<AppTagListIntervention>> resp = iosStoreApiService
                .listAppStoreAppTagListInterventions(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppTagListIntervention> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        if (values != null && !values.isEmpty()) {
            List<Integer> rootIds = initRootIds(values);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimpleResp = this.backendApiService
                    .getAppSimple(rootIds);
            if (appSimpleResp != null && appSimpleResp.getData() != null) {
                Map<String, ApplicationSimple> datas = new HashMap<String, ApplicationSimple>();
                for (Map.Entry<Integer, ApplicationSimple> entry : appSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", datas);
            }
        }

        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", AppTagListIntervention.STATUS);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/info/apptaginv/list.ftl";
    }

    private List<Integer> initRootIds(List<AppTagListIntervention> values) {
        List<Integer> rootIds = new ArrayList<Integer>();
        for (AppTagListIntervention value : values) {
            rootIds.add(value.getRootId());
        }
        return rootIds;
    }

    @RequestMapping(value = "/invadd", method = RequestMethod.POST)
    public String add(Integer rootId, Integer appTagId, Integer rank, Model model, RedirectAttributes rattrs) {
        StoreAppTagListInterventionParameter para = new StoreAppTagListInterventionParameter();
        String errMsg = checkAndFormatAddParameter(rootId, appTagId, rank, para);
        if (StringUtils.isEmpty(errMsg)) {
            try {
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addAppStoreAppTagListIntervention(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add StoreAppTagListIntervention failed." + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("appTagId", String.valueOf(appTagId));
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/apptag/invlist";
    }

    @RequestMapping(value = "/invmodifystatus", method = RequestMethod.POST)
    @ResponseBody
    public String modifyStatus(Integer id, Integer status) {
        if (id == null || id.intValue() <= 0) {
            return "StoreAppTagListIntervention id无效.";
        }

        StoreAppTagListInterventionParameter para = new StoreAppTagListInterventionParameter();
        para.setId(id);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppStoreAppTagListInterventionStatus(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/invmodifyrank", method = RequestMethod.POST)
    @ResponseBody
    public String modifyRank(Integer id, Integer rank) {
        if (id == null || id.intValue() <= 0) {
            return "StoreAppTagListIntervention id无效.";
        }

        StoreAppTagListInterventionParameter para = new StoreAppTagListInterventionParameter();
        para.setId(id);
        para.setRank(rank);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppStoreAppTagListInterventionRank(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    private String checkAndFormatAddParameter(Integer rootId, Integer appTagId, Integer rank,
            StoreAppTagListInterventionParameter para) {
        ApiRespWrapper<Map<String, String>> allAppTags = iosStoreApiService.fetchAllAppTags();
        Map<String, String> storeAppTags = allAppTags.getData();
        if (appTagId == null || !storeAppTags.containsKey(String.valueOf(appTagId))) {
            return "app tag id 不是有效的自定义Tag";
        } else {
            para.setAppTagId(appTagId);
        }
        if (rootId == null || rootId.intValue() <= 0 || !isOnline(rootId)) {
            return "无法推荐一个未上架的应用";
        } else {
            para.setRootId(rootId);
        }
        if (rank == null || rank < 0) {
            return "干预的位置非法";
        } else {
            para.setRank(rank);
        }
        return "";
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }
}
