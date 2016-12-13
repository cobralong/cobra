package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.app.ListAdInfo;
import com.appchina.ios.core.dto.app.ListAdInfoType;
import com.appchina.ios.core.dto.app.ListAdPlace;
import com.appchina.ios.core.dto.app.ListAdRecomInfo;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.core.utils.PromoteUtils;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.ListAdInfoParameter;
import com.appchina.ios.mgnt.controller.model.ListAdInfoSimple;
import com.appchina.ios.mgnt.controller.model.ListAdRecomInfoParameter;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.utils.ParameterUtils;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;
import com.appchina.ios.web.utils.UrlUtils;

@Controller
@RequestMapping(value = "/auth/listad/*")
public class ListAdController {
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private BannerStorageService bannerStorageService;

    @RequestMapping(value = "/info/add", method = RequestMethod.POST)
    public String addListAdInfo(Integer type, String target, String title,
            @RequestParam(required = false) CommonsMultipartFile icon, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(title)) {
            errMsg = "横幅广告标题不能为空";
        }
        if (StringUtils.isEmpty(errMsg) && type == null) {
            errMsg = "未知横幅广告类型";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(target)) {
            errMsg = "未知展示内容.Target:" + target;
        }
        if (StringUtils.isEmpty(errMsg)) {
            ListAdInfoType listAdInfoType = ListAdInfoType.valueOfType(type, false);
            if (listAdInfoType == null || listAdInfoType == ListAdInfoType.ALL) {
                errMsg = "未知横幅广告类型. Type:" + type;
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            if (type.intValue() == ListAdInfoType.APP_DETAIL.getType()) {
                if (StringUtils.isEmpty(target)) {
                    errMsg = "未知展示数据";
                } else {
                    int rootId = Integer.valueOf(target);
                    if (!isOnline(rootId)) {
                        errMsg = "无法将一个未上架的应用作为横幅广告";
                    }
                }
            } else if (type.intValue() == ListAdInfoType.URL_DETAIL.getType()) {
                if (!UrlUtils.isUrl(target)) {
                    errMsg = "请输入正确的url地址,http://**** or https://****.Target:" + target;
                }
            }
        }
        if (!StringUtils.isEmpty(errMsg) && (icon == null || icon.isEmpty())) {
            errMsg = "未知的横幅图片";
        }
        if (StringUtils.isEmpty(errMsg)) {
            String iconUrl = bannerStorageService.saveBanner(DateUtils.currentYearMonth(), icon);
            ListAdInfoParameter para = new ListAdInfoParameter();
            para.setTarget(target);
            para.setTitle(title);
            para.setType(type);
            para.setIcon(iconUrl);
            ApiRespWrapper<Boolean> resp = this.backendApiService.addListAdInfo(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知服务器错误,ErrCode:" + resp.getStatus() : resp
                        .getMessage();
            } else {
                errMsg = "";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/listad/info/list";
    }

    @RequestMapping(value = "/info/modify", method = RequestMethod.POST)
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

    public static String chechTime(String st) {
        if (StringUtils.isEmpty(st)) {
            return "无效的时间";
        }
        try {
            if (DateUtils.beforeNow(DateUtils.parseDateStr(st))) {
                return "轮播图开始时间不能小于当前时间";
            }
        } catch (Exception e) {
            return "无效的时间";
        }
        return "";
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    @RequestMapping("/info/list")
    public String listListAdInfo(@ModelAttribute("errMsg") String errMsg, ListAdInfoParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<ListAdInfo>> resp = backendApiService.listListAdInfo(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<ListAdInfo> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        if (CollectionUtils.notEmptyAndNull(values)) {
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
        model.addAttribute("types", EnumMapUtils.enumToMap(ListAdInfoType.values()));
        // addPlaces(model);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "listad/info/list.ftl";
    }

    @RequestMapping("/info/detail.json")
    @ResponseBody
    public JsonResult<ListAdInfoSimple> detailListAdInfo(int id) {
        boolean success = false;
        String message;
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(id);
        ApiRespWrapper<ListWrapResp<ListAdInfo>> recomResp = backendApiService.detailListAdInfo(ids);
        ListAdInfo info = null;
        ApplicationSimple app = null;
        if (recomResp != null && recomResp.getData() != null
                && CollectionUtils.notEmptyAndNull(recomResp.getData().getResultList())) {
            info = recomResp.getData().getResultList().get(0);
            if (info != null) {
                if (info.getType() == ListAdInfoType.APP_DETAIL.getType()) {
                    Integer rid = Integer.valueOf(info.getTarget());
                    List<Integer> rootIds = new ArrayList<Integer>();
                    rootIds.add(rid);
                    ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
                    if (resp == null || resp.getData() == null || resp.getData().get(rid) == null) {
                        message = "无效的根Id";
                    } else {
                        success = true;
                        message = "ok";
                        app = resp.getData().get(rid);
                    }
                } else {
                    // TODO LIST FROM OTHER TYPE
                    success = true;
                    message = "ok";
                }
            } else {
                message = "未知错误，未找到对应记录";
            }
        } else {
            message = "未知错误，未找到对应记录";
        }
        return new JsonResult<ListAdInfoSimple>(success, message, new ListAdInfoSimple(app, info));
    }

    @RequestMapping("/recom/list")
    public String listListAdRecomInfo(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("listAdInfoId") String listAdInfoId, ListAdRecomInfoParameter para, Model model) {
        if (!StringUtils.isEmpty(listAdInfoId) && para.getListAdInfoId() == null) {
            try {
                para.setListAdInfoId(Integer.parseInt(listAdInfoId));
            } catch (Exception e) {
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<ListAdRecomInfo>> recomResp = backendApiService.listListAdRecomInfo(para);
        long totalCount = (recomResp == null || recomResp.getData() == null) ? 0 : recomResp.getData().getTotalCount();
        List<ListAdRecomInfo> values = (recomResp == null || recomResp.getData() == null || recomResp.getData()
                .getResultList() == null) ? null : recomResp.getData().getResultList();
        List<Integer> listAdInfoIds = new ArrayList<Integer>();
        if (CollectionUtils.notEmptyAndNull(values)) {
            for (ListAdRecomInfo listAdRecomInfo : values) {
                if (!listAdInfoIds.contains(listAdRecomInfo.getListAdInfoId())) {
                    listAdInfoIds.add(listAdRecomInfo.getListAdInfoId());
                }
            }
        } else if (para.getListAdInfoId() != null && para.getListAdInfoId().intValue() > 0) {
            listAdInfoIds.add(para.getListAdInfoId());
        }
        ApiRespWrapper<ListWrapResp<ListAdInfo>> resp = backendApiService.detailListAdInfo(listAdInfoIds);
        if (resp != null && resp.getData() != null && CollectionUtils.notEmptyAndNull(resp.getData().getResultList())) {
            List<Integer> rootIds = new ArrayList<Integer>();
            for (ListAdInfo listAdInfo : resp.getData().getResultList()) {
                if (listAdInfo.getType() != ListAdInfoType.APP_DETAIL.getType()) {
                    continue;
                }
                rootIds.add(Integer.valueOf(listAdInfo.getTarget()));
            }
            ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimpleResp = this.backendApiService
                    .getAppSimple(rootIds);
            if (appSimpleResp != null && appSimpleResp.getData() != null) {
                Map<String, ApplicationSimple> datas = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : appSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", datas);
            }
            model.addAttribute("infos", resp.getData().getResultList());
            if (!StringUtils.isEmpty(listAdInfoId)) {
                model.addAttribute("info", resp.getData().getResultList().get(0));
            }
        }
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("types", EnumMapUtils.enumToMap(ListAdInfoType.values()));
        addPlaces(model);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("channels", PromoteAppsParameter.ALL_CHANNELS);
        return "listad/recom/list.ftl";
    }

    @RequestMapping(value = "/recom/add", method = RequestMethod.POST)
    public String addListAdRecomInfo(int listAdInfoId, String channel, String st, String et, String place,
            int position, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(channel)) {
            errMsg = "横幅广告展示渠道不能为空";
        }
        if (StringUtils.isEmpty(errMsg) && listAdInfoId <= 0) {
            errMsg = "未知的横幅广告";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(place)) {
            errMsg = "横幅广告展示列表不能为空";
        }
        if (!StringUtils.isEmpty(errMsg)) {
            errMsg = PromoteUtils.checkStEt(st, et);
        }
        if (StringUtils.isEmpty(errMsg) && position <= 0) {
            errMsg = "横幅广告展示位置错误";
        }

        if (StringUtils.isEmpty(errMsg)) {
            List<Integer> ids = new ArrayList<Integer>();
            ids.add(listAdInfoId);
            ApiRespWrapper<ListWrapResp<ListAdInfo>> resp = backendApiService.detailListAdInfo(ids);
            if (resp == null
                    || resp.getData() == null
                    || resp.getData().getResultList() == null
                    || resp.getData().getResultList().isEmpty()
                    || resp.getData().getResultList().size() != 1
                    || resp.getData().getResultList().get(0).getId() != listAdInfoId
                    || (resp.getData().getResultList().get(0).getType() == ListAdInfoType.APP_DETAIL.getType() && !isOnline(Integer
                            .parseInt(resp.getData().getResultList().get(0).getTarget())))) {
                errMsg = "未知的推荐资源";
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            ListAdRecomInfoParameter para = new ListAdRecomInfoParameter();
            para.setChannel(channel);
            para.setEt(et);
            para.setListAdInfoId(listAdInfoId);
            para.setPlace(place);
            para.setSt(st);
            para.setPosition(position);
            ApiRespWrapper<Boolean> resp = this.backendApiService.addListAdRecomInfo(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知服务器错误,ErrCode:" + resp.getStatus() : resp
                        .getMessage();
            } else {
                errMsg = "";
            }
        }
        rattrs.addFlashAttribute("listAdInfoId", String.valueOf(listAdInfoId));
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/listad/recom/list";
    }

    @RequestMapping(value = "/recom/modify")
    @ResponseBody
    public String modifyListAdRecomInfo(int id, Integer status, Integer position, String et, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        ListAdRecomInfoParameter para = new ListAdRecomInfoParameter();
        para.setId(id);
        para.setStatus(status);
        para.setPosition(position);
        para.setEt(et);
        resp = this.backendApiService.modifyListAdRecomInfo(para);
        return ParameterUtils.getRespMessage(resp);
    }

    private void addPlaces(Model model) {
        ApiRespWrapper<ListWrapResp<ListAdPlace>> listPlaceResp = backendApiService.listListAdPlace();
        if (listPlaceResp != null && listPlaceResp.getData() != null
                && CollectionUtils.notEmptyAndNull(listPlaceResp.getData().getResultList())) {
            Map<String, String> places = new HashMap<String, String>();
            for (ListAdPlace listAdPlace : listPlaceResp.getData().getResultList()) {
                places.put(String.valueOf(listAdPlace.getPlace()), listAdPlace.getDesc());
            }
            places.put("all", "全部");
            model.addAttribute("places", places);
        }
    }

    private static List<Integer> initRootIds(List<ListAdInfo> values) {
        List<Integer> rootIds = new ArrayList<Integer>();
        for (ListAdInfo listAdInfo : values) {
            if (listAdInfo.getType() == ListAdInfoType.APP_DETAIL.getType()) {
                rootIds.add(Integer.parseInt(listAdInfo.getTarget()));
            }
        }
        return rootIds;
    }

}
