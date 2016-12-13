package com.appchina.ios.mgnt.controller;

import com.appchina.ios.core.cahe.model.app.CertAppSimple;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.crawler.model.AuthoAppDownBoughtInfoResp;
import com.appchina.ios.core.dto.app.IpaCertSignature;
import com.appchina.ios.core.dto.app.PromoteApplication;
import com.appchina.ios.core.utils.ChannelUtils;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.GroupPromote;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.impl.AuthoAppDownBoughtInfoService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
@RequestMapping(value = "/auth/promote/*")
public class PromoteController {
    private static final Logger log = Logger.getLogger(PromoteController.class);
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private AuthoAppDownBoughtInfoService<PromoteApplication> promoteApplicationAuthoAppDownBoughtInfoService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "redirect:/auth/promote/list";
    }

    @RequestMapping("/grouplist")
    public String grouplist(PromoteAppsParameter para, Model model) {
        para.transfer(Integer.MAX_VALUE);
        para.setChannel(null);
        ApiRespWrapper<ListWrapResp<PromoteApplication>> resp = backendApiService.listPromoteApps(para);
        List<PromoteApplication> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        List<GroupPromote> groupPromote = groupPromote(values);
        long totalCount = groupPromote.size();
        if (values != null && !values.isEmpty()) {
            List<Integer> rootIds = initRootIds(values);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimpleResp = this.backendApiService
                    .getAppSimple(rootIds);
            if (appSimpleResp != null && appSimpleResp.getData() != null) {
                Map<String, ApplicationSimple> datas = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : appSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", datas);
            }
        }
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", groupPromote);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        try {
            model.addAttribute("rootIdBoughtMap",
                    promoteApplicationAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
        } catch (Exception e) {
            log.error("gain PromoteApplication of rootId authority state failure!" + e.getMessage(), e);
        }
        return "promote/grouplist.ftl";
    }

    @RequestMapping(value = "/offlinegrouppromote", method = RequestMethod.POST)
    @ResponseBody
    public String offlineGroupPromote(int rootId) {
        if (rootId <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = this.backendApiService.offPromoteByRootId(rootId);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    /**
     * 按rootId进行group
     *
     * @param values
     * @return
     */
    private List<GroupPromote> groupPromote(List<PromoteApplication> values) {
        List<GroupPromote> ret = new ArrayList<GroupPromote>();
        for (PromoteApplication promote : values) {
            boolean newRoot = true;
            for (GroupPromote groupPromote : ret) {
                if (groupPromote.getRootId() == promote.getRootId()) {
                    groupPromote.getPromotes().add(promote);
                    newRoot = false;
                    break;
                }
            }
            if (!newRoot) {
                continue;
            }
            GroupPromote groupPromote = new GroupPromote();
            groupPromote.setRootId(promote.getRootId());
            List<PromoteApplication> promotes = new ArrayList<PromoteApplication>();
            promotes.add(promote);
            groupPromote.setPromotes(promotes);
            ret.add(groupPromote);
        }
        return ret;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Integer rootId, Integer rank, String channel, String st, String et, Model model,
            RedirectAttributes rattrs) {
        PromoteAppsParameter para = new PromoteAppsParameter();
        // rootId exists
        String errMsg = checkAndFormatAddParameter(rootId, rank, channel, st, et, para);
        rattrs.addFlashAttribute("channel", channel);
        rattrs.addFlashAttribute("st", st);
        if (StringUtils.isEmpty(errMsg)) {
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addPromote(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add promote failed.RootId:" + rootId + ", channel:" + channel + ", st:" + st + ", et:" + et
                        + ", rank:" + rank + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/promote/list";
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
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyPromote(para);
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
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyPromote(para);
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
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyPromoteRank(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/modifystarttime", method = RequestMethod.POST)
    @ResponseBody
    public String modifyStartTime(int id, String st, boolean allchannel, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        PromoteAppsParameter para = new PromoteAppsParameter();
        para.setId(id);
        para.setSt(st);
        ApiRespWrapper<Boolean> resp = null;
        if (allchannel) {
            resp = this.backendApiService.modifyPromoteStartTimeOnAllChannel(para);
        } else {
            resp = this.backendApiService.modifyPromoteStartTime(para);
        }
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/modifyendtime", method = RequestMethod.POST)
    @ResponseBody
    public String modifyEndTime(int id, String et, boolean allchannel, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        PromoteAppsParameter para = new PromoteAppsParameter();
        para.setId(id);
        para.setEt(et);
        ApiRespWrapper<Boolean> resp = null;
        if (allchannel) {
            resp = this.backendApiService.modifyPromoteEndTimeOnAllChannel(para);
        } else {
            resp = this.backendApiService.modifyPromoteEndTime(para);
        }
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/search")
    @ResponseBody
    public JsonResult<CertAppSimple> search(String id) {
        boolean success = false;
        String message;
        ApplicationSimple ret = null;
        IpaCertSignature ipaCertSignature = null;
        int rid = NumberUtils.toInt(id, -1);
        boolean authDownload = false;
        boolean bought = false;
        if (rid == -1) {
            message = "无效的根Id";
        } else {
            List<Integer> rootIds = Arrays.asList(rid);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
            if (resp == null || resp.getData() == null || resp.getData().get(rid) == null) {
                message = "无效的根Id";
            } else {
                success = true;
                message = "ok";
                ret = resp.getData().get(rid);
                if (!StringUtils.isEmpty(ret.getDownloadUrl())) {
                    try {
                        ApiRespWrapper<IpaCertSignature> ipaCertSignatureResp = this.backendApiService
                                .getIpapListCertInfo(ret);
                        if (ipaCertSignatureResp != null && ipaCertSignatureResp.getData() != null) {
                            ipaCertSignature = ipaCertSignatureResp.getData();
                        }
                    } catch (Exception e) {
                    }
                }
                // CHECK AUTHORIZER OR BUY STATUS
                // check auth
                // if can download authorizer ipa set status return
                // check buy status
                ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> infoStatusList = this.backendApiService
                        .getAuthorizerAppDownAndBuyInfoStatusList(rootIds);
                if (infoStatusList != null && infoStatusList.getData() != null
                        && infoStatusList.getData().getResultList() != null) {
                    List<AuthoAppDownBoughtInfoResp> statusResps = infoStatusList.getData().getResultList();
                    AuthoAppDownBoughtInfoResp infoResp = statusResps.get(0);
                    authDownload = infoResp.isDownload();
                    bought = infoResp.isBought();
                }
            }
        }
        return new JsonResult<CertAppSimple>(success, message, new CertAppSimple(ret, ipaCertSignature, bought,
                authDownload));
    }

    private String checkAndFormatAddParameter(Integer rid, Integer rank, String channel, String st, String et,
            PromoteAppsParameter para) {
        String errMsg = "";
        if (rank == null || rank < 0) {
            errMsg = "推荐位置未定，请设置";
        } else {
            para.setRank(rank);
        }
        if (StringUtils.isEmpty(errMsg)) {
            if (StringUtils.isEmpty(channel)) {
                errMsg = "未知渠道";
            } else if (!ChannelUtils.legalChannel(channel)) {
                errMsg = "未知渠道";
            } else {
                para.setChannel(channel);
            }
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
        if (rid == null || rid.intValue() <= 0 || !isOnline(rid)) {
            errMsg = "无法推荐一个未上架的应用";
        } else {
            para.setRootId(rid);
        }
        return errMsg;
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    @RequestMapping("/list")
    public String list(PromoteAppsParameter para, @ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("channel") String channel, @ModelAttribute("st") String st, Model model) {
        if (!StringUtils.isEmpty(channel) && !"All".equalsIgnoreCase(channel)) {
            para.setChannel(channel);
        }
        if (!StringUtils.isEmpty(st)) {
            para.setSt(st);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<PromoteApplication>> resp = backendApiService.listPromoteApps(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<PromoteApplication> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        if (values != null && !values.isEmpty()) {
            List<Integer> rootIds = initRootIds(values);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimpleResp = this.backendApiService
                    .getAppSimple(rootIds);
            if (appSimpleResp != null && appSimpleResp.getData() != null) {
                Map<String, ApplicationSimple> datas = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : appSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", datas);
            }
            // this.backendApiService.getRootAuthDownBuyInfo(rootIds);
        }
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("channels", PromoteAppsParameter.CHANNELS);
        model.addAttribute("allChannels", PromoteAppsParameter.ALL_CHANNELS);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        try {
            model.addAttribute("rootIdBoughtMap",
                    promoteApplicationAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
        } catch (Exception e) {
            log.error("gain PromoteApplication of rootId authority state failure!" + e.getMessage(), e);
        }
        model.addAttribute("errMsg", errMsg);
        return "promote/list.ftl";
    }

    private List<Integer> initRootIds(List<PromoteApplication> values) {
        Set<Integer> ret = new HashSet<Integer>();
        for (PromoteApplication promoteApplication : values) {
            ret.add(promoteApplication.getRootId());
        }
        return new ArrayList<Integer>(ret);
    }
}
