package com.appchina.ios.mgnt.controller.info;

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

import com.appchina.ios.core.cahe.model.info.VideoInfoWrapper;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.info.DailyRecom;
import com.appchina.ios.core.dto.info.StoreCategory;
import com.appchina.ios.core.dto.info.VideoInfo;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.info.BundleIdTimedStatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.info.DailyRecomCommonSearch;
import com.appchina.ios.mgnt.controller.model.info.DailyRecomParameter;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.service.impl.StoreCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping(value = "/auth/store/dailyrecom/*")
public class DailyRecomController {
    private static final Logger log = Logger.getLogger(DailyRecomController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private StoreCommonParameterService storeCommonParameterService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(DailyRecomParameter para, Model model) {
        storeCommonParameterService.addBundles("bundleIds", model);
        model.addAttribute("types", DailyRecom.ALL_TYPES);
        model.addAttribute("shows", DailyRecom.SHOWS);
        model.addAttribute("para", para);
        return "store/dailyrecom/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(int recomId, int type, String st, String et, String title, String mainTag, String bundleId,
            int showInAudit, Integer auditingPosition, Integer auditedPosition,
            @RequestParam(required = false) CommonsMultipartFile img, Model model) {
        DailyRecomParameter para = new DailyRecomParameter();
        checkAndFormatAddParameter(recomId, type, st, et, img);
        String errMsg = "";
        List<DailyRecom> values = null;
        if (StringUtils.isEmpty(errMsg)) {
            String imgUrl = null;
            try {
                if (type == DailyRecom.TYPE_VIDEO && (img == null || img.isEmpty())) {
                    ApiRespWrapper<VideoInfo> videoInfoResp = this.iosStoreApiService.getVideoInfo(recomId);
                    if (videoInfoResp == null || videoInfoResp.getData() == null) {
                        errMsg = "未知的推荐视频资源.";
                    }
                    imgUrl = videoInfoResp.getData().getVideoImg();
                } else {
                    imgUrl = this.bannerStorageService.saveStoreDailyRecomBanner(recomId, img);
                }
            } catch (Exception e) {
                errMsg = e.getMessage();
                log.error("Save daily recom banner failed. Errmsg:" + e.getMessage(), e);
            }
            if (StringUtils.isEmpty(errMsg)) {
                para = new DailyRecomParameter(recomId, type, bundleId, st, et, mainTag, imgUrl, title, showInAudit,
                        auditingPosition, auditedPosition);
                try {
                    ApiRespWrapper<List<DailyRecom>> resp = this.iosStoreApiService.addDailyRecom(para);
                    if (resp == null) {
                        errMsg = "保存每日精选记录时失败，未知错误";
                    } else if (resp.getData() == null || resp.getData() == null) {
                        errMsg = "保存每日精选记录时失败，错误:" + resp.getMessage();
                    } else {
                        values = resp.getData();
                        errMsg = "添加成功";
                    }
                } catch (Exception e) {
                    errMsg = e.getMessage();
                }
            }
        }
        model.addAttribute("para", para);
        model.addAttribute("values", values);
        model.addAttribute("types", DailyRecom.ALL_TYPES);
        model.addAttribute("shows", DailyRecom.SHOWS);
        model.addAttribute("errMsg", errMsg);
        storeCommonParameterService.addBundles("bundleIds", model);
        return "store/dailyrecom/add.ftl";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(int id, String title, Integer status, String endTime, Integer auditingPosition,
            Integer auditedPosition, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        DailyRecomParameter para = new DailyRecomParameter();
        para.setId(id);
        if (status != null) {
            para.setStatus(status);
            resp = this.iosStoreApiService.modifyAppStoreDailyRecom(para);
        } else if (auditedPosition != null) {
            para.setAuditedPosition(auditedPosition);
            resp = iosStoreApiService.modifyAppStoreDailyRecom(para);
        } else if (auditingPosition != null) {
            para.setAuditingPosition(auditingPosition);
            resp = iosStoreApiService.modifyAppStoreDailyRecom(para);
        } else if (!StringUtils.isEmpty(endTime)) {
            try {
                DateUtils.parseDateStr(endTime);
            } catch (Exception e) {
                return "日期格式错误.";
            }
            para.setEt(endTime);
            resp = this.iosStoreApiService.modifyAppStoreDailyRecom(para);
        } else if (!StringUtils.isEmpty(title)) {
            // para.set
            DailyRecomParameter dailyRecomParameter = new DailyRecomParameter();
            dailyRecomParameter.setId(id);
            dailyRecomParameter.setTitle(title);
            resp = iosStoreApiService.modifyAppStoreDailyRecom(dailyRecomParameter);
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
    public JsonResult<DailyRecomCommonSearch> search(int id, int type) {
        if (id <= 0) {
            return new JsonResult<DailyRecomCommonSearch>(false, "无效的推荐资源Id", null);
        }
        DailyRecomCommonSearch data = null;
        if (type == DailyRecom.TYPE_APP_DETAIL) {
            ApiRespWrapper<ApplicationSimple> searchResp = this.iosStoreApiService.getApplicationByRootId(id);
            data = searchResp == null || searchResp.getData() == null ? null : DailyRecomCommonSearch
                    .instance(searchResp.getData());
        } else if (type == DailyRecom.TYPE_APP_LIST) {
            ApiRespWrapper<StoreCategory> searchResp = this.iosStoreApiService.getStoreAppTag(id);
            data = searchResp == null || searchResp.getData() == null ? null : DailyRecomCommonSearch
                    .instance(searchResp.getData());
        } else if (type == DailyRecom.TYPE_VIDEO) {
            ApiRespWrapper<VideoInfoWrapper> searchResp = this.iosStoreApiService.getVideoInfoWrapper(id);
            data = searchResp == null || searchResp.getData() == null ? null : DailyRecomCommonSearch
                    .instance(searchResp.getData());
        } else {
            return new JsonResult<DailyRecomCommonSearch>(false, "无效的推荐类型", null);
        }
        if (data == null) {
            return new JsonResult<DailyRecomCommonSearch>(false, "未找到推荐资源.Id:" + id, null);
        }
        return new JsonResult<DailyRecomCommonSearch>(true, "ok", data);
    }

    private String checkAndFormatAddParameter(int recomId, int type, String st, String et, CommonsMultipartFile img) {
        String errMsg = "";
        if (recomId <= 0) {
            errMsg = "未知的推荐资源";
        }
        if (StringUtils.isEmpty(errMsg) && (type != DailyRecom.TYPE_VIDEO && (img == null || img.getSize() == 0))) {
            errMsg = "请提供轮播图";
        }
        if (StringUtils.isEmpty(errMsg) && !DailyRecom.ALL_TYPES.containsKey(String.valueOf(type))) {
            errMsg = "无效的每日推荐类型";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(st)) {
            errMsg = "无效的时间";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(et)) {
            errMsg = "无效的时间";
        }
        Date startTime = new Date();
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
        Date endTime = new Date();
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
        if (StringUtils.isEmpty(errMsg)) {
            JsonResult<DailyRecomCommonSearch> resp = this.search(recomId, type);
            if (resp == null || !resp.isSuccess() || resp.getData() == null) {
                errMsg = "未知推荐资源";
            }
        }
        return errMsg;
    }

    @RequestMapping("/auditorlist")
    public String auditorlist(BundleIdTimedStatusStartSizeParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<DailyRecom>> resp = iosStoreApiService.listDailyRecomForAuditing(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<DailyRecom> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        storeCommonParameterService.addBundles("bundleIds", model);
        model.addAttribute("values", values);
        model.addAttribute("types", DailyRecom.ALL_TYPES);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        model.addAttribute("user", 0);
        return "store/dailyrecom/listforauditing.ftl";
    }

    @RequestMapping("/userlist")
    public String userlist(BundleIdTimedStatusStartSizeParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<DailyRecom>> resp = iosStoreApiService.listDailyRecomForAudited(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<DailyRecom> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        storeCommonParameterService.addBundles("bundleIds", model);
        model.addAttribute("values", values);
        model.addAttribute("types", DailyRecom.ALL_TYPES);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        model.addAttribute("user", 1);
        return "store/dailyrecom/listforauditing.ftl";
    }
}
