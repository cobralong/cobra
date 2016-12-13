package com.appchina.ios.mgnt.controller.info;

import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.info.AppStorePromoteApplication;
import com.appchina.ios.core.dto.info.AppStorePromoteTab;
import com.appchina.ios.core.dto.info.InfoType;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.info.StorePromoteAppsAddParameter;
import com.appchina.ios.mgnt.controller.model.info.StorePromoteAppsParameter;
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
 * Created by zhouyanhui on 8/5/15.
 */
@Controller
@RequestMapping(value = "/auth/store/promote/*")
public class StorePromoteController {
    private static final Logger log = Logger.getLogger(StorePromoteController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "redirect:/auth/store/promote/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Integer rootId, Integer rank, Integer tabId, String st, String et, Model model,
            RedirectAttributes rattrs) {
        StorePromoteAppsAddParameter para = new StorePromoteAppsAddParameter();
        // rootId exists
        String errMsg = checkAndFormatAddParameter(rootId, rank, tabId, st, et, para);
        rattrs.addFlashAttribute("tabId", String.valueOf(tabId));
        rattrs.addFlashAttribute("st", st);
        if (StringUtils.isEmpty(errMsg)) {
            try {
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addAppStorePromoteApplication(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add promote failed.RootId:" + rootId + ", tabId:" + tabId + ", st:" + st + ", et:" + et
                        + ", rank:" + rank + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/promote/list";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(Integer id, String ids, Integer status) {
        if (id != null && id <= 0) {
            return "id无效.";
        }
        if (id != null) {
            PromoteAppsParameter para = new PromoteAppsParameter();
            para.setId(id);
            para.setStatus(status);
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyStorePromoteStatus(para);
            if (resp == null) {
                return "未知错误.";
            }
            if (resp.getData() == null || !resp.getData()) {
                return resp.getMessage();
            }
            return "";
        } else if (!StringUtils.isEmpty(ids)) {
            PromoteAppsParameter para = new PromoteAppsParameter();
            para.setIds(ids);
            para.setStatus(status);
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyStorePromoteStatus(para);
            if (resp == null) {
                return "未知错误.";
            }
            if (resp.getData() == null || !resp.getData()) {
                return resp.getMessage();
            }
            return "";
        }
        return "";
    }

    @RequestMapping(value = "/modifyrank", method = RequestMethod.POST)
    @ResponseBody
    public String modifyRank(int id, int rank, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        PromoteAppsParameter para = new PromoteAppsParameter();
        para.setId(id);
        para.setRank(rank);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyStorePromoteRank(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    private String checkAndFormatAddParameter(Integer rid, Integer rank, Integer tabId, String st, String et,
            StorePromoteAppsAddParameter para) {
        String errMsg = "";
        if (rank == null || rank < 0) {
            errMsg = "推荐位置未定，请设置";
        } else {
            para.setRank(rank);
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(st)) {
            errMsg = "无效的时间";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(et)) {
            errMsg = "无效的时间";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                DateUtils.parseDateStr(st);
                para.setSt(st);
            } catch (Exception e) {
                errMsg = "无效的时间";
            }
        }
        if (StringUtils.isEmpty(errMsg) && DateUtils.beforeNow(DateUtils.parseDateStr(st))) {
            errMsg = "推荐开始时间不能小于当前时间";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                DateUtils.parseDateStr(et);
                para.setEt(et);
            } catch (Exception e) {
                errMsg = "无效的时间";
            }
        }
        if (StringUtils.isEmpty(errMsg) && DateUtils.beforeNow(et)) {
            errMsg = "推荐结束时间不能小于当前时间";
        }
        if (StringUtils.isEmpty(errMsg) && DateUtils.parseDateStr(et).before(DateUtils.parseDateStr(et))) {
            errMsg = "推荐结束时间不能早于推荐开始时间";
        }
        if (StringUtils.isEmpty(errMsg) && !isValidTab(tabId)) {
            errMsg = "无法给无效的推荐tab添加应用";
        } else {
            para.setTabId(tabId);
        }
        if (rid == null || rid.intValue() <= 0 || !isOnline(rid)) {
            errMsg = "无法推荐一个未上架的应用";
        } else {
            para.setRootId(rid);
        }
        return errMsg;
    }

    private boolean isValidTab(Integer tabId) {
        ApiRespWrapper<AppStorePromoteTab> resp = iosStoreApiService.queryAppStorePromoteTab(tabId);
        if (resp != null && resp.getData() != null) {
            AppStorePromoteTab tab = resp.getData();
            return tab.getStatus() == AppStorePromoteTab.STATUS_OK && tab.getType() == InfoType.APP_LIST.getType();
        }
        return false;
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.iosStoreApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    @RequestMapping("/list")
    public String list(StorePromoteAppsParameter para, @ModelAttribute("tabId") String tabId,
            @ModelAttribute("errMsg") String errMsg, @ModelAttribute("st") String st, Model model) {
        if (StringUtils.isNotBlank(tabId)) {
            para.setTabId(Integer.valueOf(tabId));
        }
        if (StringUtils.isNotBlank(st)) {
            para.setSt(st);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStorePromoteApplication>> resp = iosStoreApiService.listStorePromoteApps(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppStorePromoteApplication> values = (resp == null || resp.getData() == null || resp.getData()
                .getResultList() == null) ? null : resp.getData().getResultList();
        if (values != null && !values.isEmpty()) {
            List<Integer> rootIds = initRootIds(values);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimpleResp = this.iosStoreApiService
                    .getAppSimple(rootIds);
            if (appSimpleResp != null && appSimpleResp.getData() != null) {
                Map<String, ApplicationSimple> datas = new HashMap<String, ApplicationSimple>();
                for (Map.Entry<Integer, ApplicationSimple> entry : appSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", datas);
            }
        }
        ApiRespWrapper<Map<Integer, AppStorePromoteTab>> promoteTabsResp = iosStoreApiService
                .listAllAppStorePromoteTabs();
        if (promoteTabsResp != null && promoteTabsResp.getData() != null) {
            Map<String, String> datas = new HashMap<String, String>();
            for (Map.Entry<Integer, AppStorePromoteTab> entry : promoteTabsResp.getData().entrySet()) {
                datas.put(entry.getKey().toString(), entry.getValue().getName());
            }
            model.addAttribute("tabs", datas);
        }
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/info/promote/list.ftl";
    }

    private List<Integer> initRootIds(List<AppStorePromoteApplication> values) {
        List<Integer> ret = new ArrayList<Integer>();
        for (AppStorePromoteApplication promoteApplication : values) {
            ret.add(promoteApplication.getRootId());
        }
        return ret;
    }
}
