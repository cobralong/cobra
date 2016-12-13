package com.appchina.ios.mgnt.controller.info;

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
import com.appchina.ios.core.dto.app.RootApplication;
import com.appchina.ios.core.dto.info.AppStoreClientLoadPageAd;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.info.StoreLoadPageAdParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.service.impl.StoreCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping(value = "/auth/store/ad/*")
public class StoreLoadPageAdController {
    private static final Logger log = Logger.getLogger(StoreLoadPageAdController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private StoreCommonParameterService storeCommonParameterService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(String errMsg, StoreLoadPageAdParameter para, Model model) {
        model.addAttribute("para", para);
        storeCommonParameterService.addBundles("bundleIds", model);
        return "store/info/ad/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(StoreLoadPageAdParameter para, @RequestParam(required = false) CommonsMultipartFile img,
            Model model) {
        Date startTime = new Date();
        Date endTime = new Date();
        String errMsg = checkAndFormatAddParameter(para.getRootId(), para.getSt(), para.getEt(), startTime, endTime,
                img);

        if (StringUtils.isEmpty(errMsg)) {
            if (!StringUtils.isEmpty(para.getRootId())) {
                ApiRespWrapper<RootApplication> rootApplicaitonResp = backendApiService.getRootApplication(Integer
                        .parseInt(para.getRootId()));
                if (rootApplicaitonResp != null && rootApplicaitonResp.getData() != null) {
                    para.setItemId(rootApplicaitonResp.getData().getItemId());
                }
            }
            String imgUrl = bannerStorageService.saveStoreAdImg(img);
            para.setImgUrl(imgUrl);
            try {
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addLoadPageAd(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (resp.getStatus() != 0 || resp.getData() == null) {
                    errMsg = resp.getMessage();
                } else {
                    errMsg = "添加成功.";
                }
            } catch (Exception e) {
                log.error("Add load page ad failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        storeCommonParameterService.addBundles("bundleIds", model);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/info/ad/add.ftl";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(int id, Integer status, String et, String st, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        StoreLoadPageAdParameter param = new StoreLoadPageAdParameter();
        param.setId(id);
        param.setStatus(status);
        param.setEt(et);
        param.setSt(st);
        resp = this.iosStoreApiService.modifyLoadPageAd(param);
        if (!StringUtils.isEmpty(et)) {
            try {
                DateUtils.parseDateStr(et);
            } catch (Exception e) {
                return "日期格式错误.";
            }
        }
        if (!StringUtils.isEmpty(et)) {
            try {
                DateUtils.parseDateStr(et);
            } catch (Exception e) {
                return "日期格式错误.";
            }
        }

        resp = this.iosStoreApiService.modifyLoadPageAd(param);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    private String checkAndFormatAddParameter(String rootId, String st, String et, Date startTime, Date endTime,
            CommonsMultipartFile img) {
        String errMsg = "";

        if (img == null || img.getSize() == 0) {
            errMsg = "请提供广告图";
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

        if (StringUtils.isNotBlank(rootId)) {
            int rid = Integer.valueOf(rootId);
            if (rid <= 0 || !isOnline(rid)) {
                errMsg = "无法轮播一个未上架的应用";
            }
        }

        return errMsg;
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    @RequestMapping("/list")
    public String list(StoreLoadPageAdParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(AppStoreClientLoadPageAd.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<AppStoreClientLoadPageAd>> resp = iosStoreApiService.listLoadPageAds(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppStoreClientLoadPageAd> values = (resp == null || resp.getData() == null || resp.getData()
                .getResultList() == null) ? null : resp.getData().getResultList();
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
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        storeCommonParameterService.addBundles("bundleIds", model);
        return "store/info/ad/list.ftl";
    }

    private List<Integer> initRootIds(List<AppStoreClientLoadPageAd> values) {
        List<Integer> rootIds = new ArrayList<Integer>();
        for (AppStoreClientLoadPageAd loadPageAd : values) {
            if (loadPageAd.getRootId() != null) {
                rootIds.add(loadPageAd.getRootId());
            }
        }
        return rootIds;
    }

}
