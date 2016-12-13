package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.app.Banner;
import com.appchina.ios.core.utils.ChannelUtils;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.BannerAddParameter;
import com.appchina.ios.mgnt.controller.model.GroupBanner;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.impl.AuthoAppDownBoughtInfoService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping(value = "/auth/banner/*")
public class BannerController {
    private static final Logger log = Logger.getLogger(BannerController.class);
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private AuthoAppDownBoughtInfoService<Banner> bannerAuthoAppDownBoughtInfoService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(String errMsg, BannerAddParameter para, Model model) {
        model.addAttribute("channels", PromoteAppsParameter.ALL_CHANNELS);
        model.addAttribute("para", para);
        return "banner/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(BannerAddParameter para, @RequestParam(required = false) CommonsMultipartFile banner, Model model) {
        Date startTime = new Date();
        Date endTime = new Date();
        int rootId = para.getRootId();
        String errMsg = checkAndFormatAddParameter(rootId, para.getRank(), para.getChannel(), para.getSt(),
                para.getEt(), startTime, endTime, banner);

        if (StringUtils.isEmpty(errMsg)) {
            String bannerUrl = bannerStorageService.saveBanner(para.getRootId(), banner);
            para.setBannerUrl(bannerUrl);
            try {
                ApiRespWrapper<ListWrapResp<Banner>> resp = this.backendApiService.addBanner(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (resp.getStatus() != 0 || resp.getData() == null || resp.getData().getResultList() == null) {
                    errMsg = resp.getMessage();
                } else {
                    errMsg = "添加成功.";
                    if (resp.getData().getResultList() != null) {
                        model.addAttribute("values", resp.getData().getResultList());
                        ApiRespWrapper<Map<Integer, ApplicationSimple>> simpleResp = this.backendApiService
                                .getAppSimple(Arrays.asList(rootId));
                        if (simpleResp != null && simpleResp.getData() != null
                                && simpleResp.getData().get(rootId) != null) {
                            model.addAttribute("simple", simpleResp.getData().get(rootId));
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Add banner failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        model.addAttribute("para", para);
        model.addAttribute("channels", PromoteAppsParameter.ALL_CHANNELS);
        model.addAttribute("errMsg", errMsg);
        return "banner/add.ftl";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(int id, Integer status, Integer rank, String endTime, Integer allChannel, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        PromoteAppsParameter para = new PromoteAppsParameter();
        para.setId(id);
        if (status != null) {
            para.setStatus(status);
            resp = this.backendApiService.modifyBannerStatus(para);
        } else if (rank != null) {
            para.setRank(rank);
            resp = this.backendApiService.modifyBannerRank(para);
        } else if (!StringUtils.isEmpty(endTime)) {
            try {
                DateUtils.parseDateStr(endTime);
            } catch (Exception e) {
                return "日期格式错误.";
            }
            para.setEt(endTime);
            if (allChannel != null && allChannel.intValue() == 1) {
                para.setChannel("All");
            }
            resp = this.backendApiService.modifyBannerEndTime(para);
        }
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/offlinefrombanner", method = RequestMethod.POST)
    @ResponseBody
    public String offlinefrombanner(int rootId) {
        if (rootId <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = this.backendApiService.offlineFromBanner(rootId);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    private String checkAndFormatAddParameter(Integer rid, Integer rank, String channel, String st, String et,
            Date startTime, Date endTime, CommonsMultipartFile banner) {
        String errMsg = "";
        if (rank == null || rank < 0) {
            errMsg = "轮播图位置未定，请设置";
        }
        if (banner == null || banner.getSize() == 0) {
            errMsg = "请提供轮播图";
        }
        if (StringUtils.isEmpty(errMsg) && !ChannelUtils.legalChannel(channel)) {
            errMsg = "未知渠道";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(st)) {
            errMsg = "无效的时间";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(et)) {
            errMsg = "无效的时间";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                startTime.setTime(DateUtils.parseDateStr(st).getTime());
            } catch (Exception e) {
                errMsg = "无效的时间";
            }
        }
        if (StringUtils.isEmpty(errMsg) && DateUtils.beforeNow(startTime)) {
            errMsg = "轮播图开始时间不能小于当前时间";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                endTime.setTime(DateUtils.parseDateStr(et).getTime());
            } catch (Exception e) {
                errMsg = "无效的时间";
            }
        }
        if (StringUtils.isEmpty(errMsg) && DateUtils.beforeNow(et)) {
            errMsg = "轮播图结束时间不能小于当前时间";
        }
        if (StringUtils.isEmpty(errMsg) && endTime.before(startTime)) {
            errMsg = "轮播图结束时间不能早于轮播图开始时间";
        }
        if (rid == null || rid.intValue() <= 0 || !isOnline(rid)) {
            errMsg = "无法轮播一个未上架的应用";
        }
        return errMsg;
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    @RequestMapping("/list")
    public String list(PromoteAppsParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<Banner>> resp = backendApiService.listBannerApps(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<Banner> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null) ? null
                : resp.getData().getResultList();
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
        model.addAttribute("values", values);
        model.addAttribute("channels", PromoteAppsParameter.CHANNELS);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        try {
            model.addAttribute("rootIdBoughtMap", bannerAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
        } catch (Exception e) {
            log.error("gain Banner of rootId authority state failure!" + e.getMessage(), e);
        }
        return "banner/list.ftl";
    }

    @RequestMapping("/grouplist")
    public String grouplist(PromoteAppsParameter para, Model model) {
        para.transfer(Integer.MAX_VALUE);
        para.setChannel(null);
        ApiRespWrapper<ListWrapResp<Banner>> resp = backendApiService.listBannerApps(para);
        List<Banner> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null) ? null
                : resp.getData().getResultList();
        List<GroupBanner> groupBanners = groupBanner(values);
        long totalCount = groupBanners.size();
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
        model.addAttribute("values", groupBanners);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        try {
            model.addAttribute("rootIdBoughtMap", bannerAuthoAppDownBoughtInfoService.buildRootIdBoughtMap(values));
        } catch (Exception e) {
            log.error("gain Banner of rootId authority state failure!" + e.getMessage(), e);
        }
        return "banner/grouplist.ftl";
    }

    /**
     * 按rootId进行group
     * 
     * @param values
     * @return
     */
    private List<GroupBanner> groupBanner(List<Banner> values) {
        List<GroupBanner> ret = new ArrayList<GroupBanner>();
        for (Banner banner : values) {
            boolean newRoot = true;
            for (GroupBanner groupBanner : ret) {
                if (groupBanner.getRootId() == banner.getRootId()) {
                    groupBanner.getBanners().add(banner);
                    newRoot = false;
                    break;
                }
            }
            if (!newRoot) {
                continue;
            }
            GroupBanner groupBanner = new GroupBanner();
            groupBanner.setRootId(banner.getRootId());
            groupBanner.setBannerUrl(banner.getBannerUrl());
            List<Banner> banners = new ArrayList<Banner>();
            banners.add(banner);
            groupBanner.setBanners(banners);
            ret.add(groupBanner);
        }
        return ret;
    }

    private List<Integer> initRootIds(List<Banner> values) {
        List<Integer> ret = new ArrayList<Integer>();
        for (Banner banner : values) {
            ret.add(banner.getRootId());
        }
        return ret;
    }

}
