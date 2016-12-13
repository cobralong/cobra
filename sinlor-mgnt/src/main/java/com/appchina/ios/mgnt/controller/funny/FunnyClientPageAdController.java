package com.appchina.ios.mgnt.controller.funny;

import java.util.Date;
import java.util.List;

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

import com.appchina.ios.core.dto.info.FunnyClientAdInfo;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.info.FunnyClientAdParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.service.impl.StoreCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping(value = "/funny/client/ad/*")
public class FunnyClientPageAdController {
    private static final Logger log = Logger.getLogger(FunnyClientPageAdController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private StoreCommonParameterService storeCommonParameterService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(String errMsg, FunnyClientAdParameter para, Model model) {
        model.addAttribute("para", para);
        storeCommonParameterService.addBundles("bundleIds", model);
        return "store/funny/column/ad/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(FunnyClientAdParameter para, @RequestParam(required = false) CommonsMultipartFile img, Model model) {
        Date startTime = new Date();
        Date endTime = new Date();
        String errMsg = checkAndFormatAddParameter(para.getSt(), para.getEt(), startTime, endTime, img);

        if (StringUtils.isEmpty(errMsg)) {
            String imgUrl = bannerStorageService.saveFunnyClientAdImg(img);
            para.setImgUrl(imgUrl);
            try {
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addFunnyClientAd(para);
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
        return "store/funny/column/ad/add.ftl";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(int id, Integer status, String et, String st, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        FunnyClientAdParameter param = new FunnyClientAdParameter();
        param.setId(id);
        if (status != null) {
            param.setStatus(status);
            resp = this.iosStoreApiService.modifyFunnyClientAd(param);
        } else {
            if (!StringUtils.isEmpty(st)) {
                try {
                    DateUtils.parseDateStr(st);
                    param.setSt(st);
                } catch (Exception e) {
                    return "日期格式错误.";
                }
            }
            if (!StringUtils.isEmpty(et)) {
                try {
                    DateUtils.parseDateStr(et);
                    param.setEt(et);
                } catch (Exception e) {
                    return "日期格式错误.";
                }
            }
            resp = this.iosStoreApiService.modifyFunnyClientAd(param);
        }
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    private String checkAndFormatAddParameter(String st, String et, Date startTime, Date endTime,
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

        return errMsg;
    }

    @RequestMapping("/list")
    public String list(FunnyClientAdParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(FunnyClientAdInfo.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<FunnyClientAdInfo>> resp = iosStoreApiService.listFunnyClientAd(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<FunnyClientAdInfo> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", FunnyClientAdInfo.STATUS);
        model.addAttribute("para", para);
        storeCommonParameterService.addBundles("bundleIds", model);
        return "store/funny/column/ad/list.ftl";
    }

}
