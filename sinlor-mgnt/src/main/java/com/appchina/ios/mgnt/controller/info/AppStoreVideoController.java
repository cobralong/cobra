package com.appchina.ios.mgnt.controller.info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.cahe.model.info.VideoInfoWrapper;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.info.AppStoreVideoInfoRecomCate;
import com.appchina.ios.core.dto.info.AppStoreVideoInfoRecomClient;
import com.appchina.ios.core.dto.info.StoreCategory;
import com.appchina.ios.core.dto.info.VideoInfo;
import com.appchina.ios.core.dto.info.VideoMetaData;
import com.appchina.ios.mgnt.controller.model.info.AppStoreVideoAuditParameter;
import com.appchina.ios.mgnt.controller.model.info.VideoInfoModel;
import com.appchina.ios.mgnt.controller.model.info.VideoInfoModifyParameter;
import com.appchina.ios.mgnt.controller.model.info.VideoMetaParameter;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.service.impl.StoreCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.StartSizeParameter;
import com.appchina.ios.web.utils.CollectionUtils;
import com.appchina.ios.web.utils.GsonUtils;

/**
 * Created by zhouyanhui on 7/16/15.
 */
@Controller
@RequestMapping(value = "/auth/store/video/*")
public class AppStoreVideoController {
    private static final Logger log = Logger.getLogger(AppStoreVideoController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private StoreCommonParameterService storeCommonParameterService;

    @RequestMapping("/auditinglist")
    public String auditingList(StartSizeParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<VideoMetaData>> resp = iosStoreApiService.listAppStoreVideoMetaData(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<VideoMetaData> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        return "store/video/auditinglist.ftl";
    }

    @RequestMapping("/disablevideometa.json")
    @ResponseBody
    public String disableVideoMeta(int id) {
        try {
            VideoMetaParameter para = new VideoMetaParameter();
            para.setId(id);
            para.setStatus(VideoMetaData.STATUS_DEL);
            ApiRespWrapper<Boolean> resp = iosStoreApiService.modifyAppStoreVideoMetaStatus(para);
            if (resp == null) {
                return "未知网络错误.";
            }
            if (resp.getData() == null || !resp.getData().booleanValue()) {
                return resp.getMessage();
            }
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/auditedlist")
    public String auditedList(VideoInfoModifyParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<VideoInfoWrapper>> resp = iosStoreApiService.listAppStoreVideoInfoWrapper(para);
        ApiRespWrapper<ListWrapResp<StoreCategory>> videoCateResp = iosStoreApiService
                .getStoreVideoCates(StartSizeParameter.instanceAllList());
        List<StoreCategory> videoCates = null;
        if (videoCateResp == null || videoCateResp.getData() == null) {
            videoCates = Collections.emptyList();
        } else {
            videoCates = videoCateResp.getData().getResultList();
        }
        Map<String, String> cates = new LinkedHashMap<String, String>();
        cates.put(String.valueOf(StoreCategory.ALL_CATEGORY.getId()), StoreCategory.ALL_CATEGORY.getName());
        for (StoreCategory storeCategory : videoCates) {
            cates.put(String.valueOf(storeCategory.getId()), storeCategory.getName());
        }
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<VideoInfoWrapper> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        storeCommonParameterService.addBundles("bundleIds", model);
        para.getPager().setTotal(totalCount);
        model.addAttribute("cates", cates);
        model.addAttribute("status", VideoInfoModifyParameter.ALL_STATUS);
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        return "store/video/auditedlist.ftl";
    }

    @RequestMapping("/auditingdetail")
    public String auditingDetail(@ModelAttribute("id") Integer id, @ModelAttribute("errMsg") String errMsg,
            AppStoreVideoAuditParameter para, Model model) {
        if (id != null && id.intValue() > 0) {
            para.setId(id);
        }
        ApiRespWrapper<VideoMetaData> resp = (iosStoreApiService.getAppStorVideo(id));
        ApiRespWrapper<String> headVideoUrlResp = iosStoreApiService.getAppStoreHeadVideo();
        VideoMetaData value = (resp == null || resp.getData() == null) ? null : resp.getData();
        model.addAttribute("value", value);
        model.addAttribute("headVideoUrl", headVideoUrlResp == null ? "" : headVideoUrlResp.getData());
        storeCommonParameterService.addBundles("bundleIds", model);
        List<StoreCategory> cates = listVideoCategory();
        Map<String, String> maps = new HashMap<String, String>();
        for (StoreCategory cate : cates) {
            maps.put(String.valueOf(cate.getId()), cate.getName());
        }
        model.addAttribute("cateMap", maps);
        // transfer(para, value);
        model.addAttribute("para", para);
        model.addAttribute("cates", cates);
        model.addAttribute("errMsg", errMsg);

        return "store/video/auditingdetail.ftl";
    }

    @RequestMapping("/videoinfo")
    public String videoinfo(@ModelAttribute("id") Integer id, @ModelAttribute("errMsg") String errMsg,
            AppStoreVideoAuditParameter para, Model model) {
        ApiRespWrapper<VideoInfo> resp = (iosStoreApiService.getVideoInfo(id));
        VideoInfo value = (resp == null || resp.getData() == null) ? null : resp.getData();
        List<StoreCategory> cates = listVideoCategory();
        List<VideoMetaData> metas = new ArrayList<VideoMetaData>();
        try {
            List<String> videoUrlList = GsonUtils.fromJsonStrToStrList(value.getVideoUrl());
            for (String videoUrl : videoUrlList) {
                ApiRespWrapper<VideoMetaData> videoMetaResp = this.iosStoreApiService
                        .getAppStoreVideoMetaData(videoUrl);
                VideoMetaData videoMetaData = videoMetaResp == null ? null : videoMetaResp.getData();
                if (videoMetaData != null) {
                    metas.add(videoMetaData);
                }
            }
        } catch (Exception e) {
        }

        List<ApplicationSimple> apps = new ArrayList<ApplicationSimple>();
        try {
            List<String> rootIdStr = GsonUtils.fromJsonStrToStrList(value.getRelateRootIds());
            List<Integer> rootIdList = CollectionUtils.collectionsToIntList(rootIdStr, false);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> appResp = this.iosStoreApiService.getAppSimple(rootIdList);
            Map<Integer, ApplicationSimple> rootIdAppMap = appResp == null ? null : appResp.getData();
            if (!CollectionUtils.emptyOrNull(rootIdAppMap)) {
                rootIdAppMap.values().removeAll(Arrays.asList(new ApplicationSimple[] { null }));
            }
            if (!CollectionUtils.emptyOrNull(rootIdAppMap)) {
                apps = new ArrayList<ApplicationSimple>(rootIdAppMap.values());
            }
        } catch (Exception e) {
        }
        ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomClient>> bundleIdListResp = iosStoreApiService
                .listAppStoreVideoInfoRecomBundleIds(id);
        if (bundleIdListResp != null && bundleIdListResp.getData() != null
                && CollectionUtils.notEmptyAndNull(bundleIdListResp.getData().getResultList())) {
            List<String> recomBundleIds = new ArrayList<String>();
            for (AppStoreVideoInfoRecomClient appStoreVideoInfoRecomClient : bundleIdListResp.getData().getResultList()) {
                if (appStoreVideoInfoRecomClient.getStatus() == AppStoreVideoInfoRecomClient.STATUS_OK) {
                    recomBundleIds.add(appStoreVideoInfoRecomClient.getBundleId());
                }
            }
            model.addAttribute("recomBundleIds", recomBundleIds);
        }

        ApiRespWrapper<ListWrapResp<AppStoreVideoInfoRecomCate>> cateIdListResp = iosStoreApiService
                .listAppStoreVideoInfoRecomCateIds(id);
        List<Integer> recomCateIds = new ArrayList<Integer>();
        if (bundleIdListResp != null && bundleIdListResp.getData() != null
                && CollectionUtils.notEmptyAndNull(bundleIdListResp.getData().getResultList())) {
            for (AppStoreVideoInfoRecomCate appStoreVideoInfoRecomCate : cateIdListResp.getData().getResultList()) {
                if (appStoreVideoInfoRecomCate.getStatus() == AppStoreVideoInfoRecomCate.STATUS_OK) {
                    recomCateIds.add(appStoreVideoInfoRecomCate.getCateId());
                }
            }
            model.addAttribute("recomCateIds", recomCateIds);
        }

        VideoInfoModel data = VideoInfoModel.newInstance(value, metas, apps);
        storeCommonParameterService.addBundles("bundleIds", model);
        model.addAttribute("data", data);
        model.addAttribute("apps", apps);
        model.addAttribute("metas", metas);
        model.addAttribute("para", para);
        model.addAttribute("cates", cates);
        model.addAttribute("errMsg", errMsg);

        return "store/video/videoinfo.ftl";
    }

    @RequestMapping("/audit")
    public String audit(AppStoreVideoAuditParameter para, @RequestParam(required = false) Integer headVideoChecked,
            @RequestParam(required = false) String headVideoUrl,
            @RequestParam(required = false) CommonsMultipartFile screen, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(para.getTitle())) {
            errMsg = "标题不能为空";
        }
        if (StringUtils.isEmpty(errMsg) && para.getMainCategory() <= 0) {
            errMsg = "未知的视频分类";
        }
        if (StringUtils.isEmpty(errMsg) && (screen == null || screen.isEmpty())) {
            errMsg = "未知的视频截图";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(para.getContent())) {
            errMsg = "未知的视频正文";
        }
        String videoShowImg = bannerStorageService.saveStoreVideoScreenImg(para.getId(), screen);
        para.setVideoImg(videoShowImg);
        para.transfer(headVideoChecked != null && headVideoChecked.intValue() == 1, headVideoUrl);
        ApiRespWrapper<Boolean> resp = iosStoreApiService.auditVideoInfo(para);
        boolean value = (resp == null || resp.getData() == null) ? false : resp.getData();
        if (value) {
            return "redirect:/auth/store/video/auditedlist";
        } else {
            rattrs.addAttribute("id", para.getId());
            errMsg = resp == null ? "网络错误" : resp.getMessage();
            rattrs.addFlashAttribute("errMsg", errMsg);
            return "redirect:/auth/store/video/auditingdetail";
        }
    }

    @RequestMapping("/modifyvideoinfo.json")
    @ResponseBody
    public String modifyVideoInfo(int id, Integer mainTag, String title, String relateRootIds, String desc) {
        try {
            VideoInfoModifyParameter para = new VideoInfoModifyParameter();
            para.setVideoInfoId(id);
            para.setMainCategory(mainTag);
            para.setRelateRootIds(relateRootIds);
            para.setDesc(desc);
            para.setTitle(title);
            ApiRespWrapper<Boolean> resp = iosStoreApiService.modifyAppStoreVideoInfo(para);
            if (resp == null) {
                return "未知网络错误.";
            }
            if (resp.getData() == null || !resp.getData().booleanValue()) {
                return resp.getMessage();
            }
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/recom/client/modify.json")
    @ResponseBody
    public String modifyRecomClient(int videoInfoId, String bundleId, int status) {
        try {
            VideoInfoModifyParameter para = new VideoInfoModifyParameter();
            para.setVideoInfoId(videoInfoId);
            para.setBundleId(bundleId);
            para.setStatus(status);
            ApiRespWrapper<Boolean> resp = iosStoreApiService.modifyAppStoreVideoRecomBundleId(para);
            if (resp == null) {
                return "未知网络错误.";
            }
            if (resp.getData() == null || !resp.getData().booleanValue()) {
                return resp.getMessage();
            }
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/recom/cate/modify.json")
    @ResponseBody
    public String modifyRecomCate(int videoInfoId, int cateId, int status) {
        try {
            VideoInfoModifyParameter para = new VideoInfoModifyParameter();
            para.setVideoInfoId(videoInfoId);
            para.setCateId(cateId);
            para.setStatus(status);
            ApiRespWrapper<Boolean> resp = iosStoreApiService.modifyAppStoreVideoRecomCateId(para);
            if (resp == null) {
                return "未知网络错误.";
            }
            if (resp.getData() == null || !resp.getData().booleanValue()) {
                return resp.getMessage();
            }
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/modifyvideoinfobanner.json")
    public String modifyVideoInfoBanner(int id, @RequestParam(required = false) CommonsMultipartFile screen,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (id <= 0 || screen == null || screen.isEmpty()) {
            errMsg = "参数错误,Id不存在或者未上传截图.";
        }
        String videoImgUrl = null;
        if (StringUtils.isEmpty(errMsg)) {
            try {
                videoImgUrl = bannerStorageService.saveStoreVideoScreenImg(id, screen);
            } catch (Exception e) {
                errMsg = "存储banner失败.";
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                VideoInfoModifyParameter para = new VideoInfoModifyParameter();
                para.setVideoInfoId(id);
                para.setVideoImg(videoImgUrl);
                ApiRespWrapper<Boolean> resp = iosStoreApiService.modifyAppStoreVideoInfo(para);
                if (resp == null) {
                    errMsg = "未知网络错误.";
                }
                if (resp.getData() == null || !resp.getData().booleanValue()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        rattrs.addAttribute("id", id);
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/video/videoinfo";
    }

    private List<StoreCategory> listVideoCategory() {
        ApiRespWrapper<ListWrapResp<StoreCategory>> resp = null;
        try {
            resp = this.iosStoreApiService.getStoreVideoCates(StartSizeParameter.instanceAllList());
        } catch (Exception e) {
            log.error("获取视频分类失败.", e);
        }
        List<StoreCategory> datas;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
        }
        return datas;
    }

}
