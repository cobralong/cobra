package com.appchina.ios.mgnt.controller.info;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.appchina.ios.core.dto.info.AppStorePromoteTab;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStorePromoteTabModifyParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStorePromoteTabParameter;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.utils.ParameterUtils;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * Created by zhouyanhui on 8/5/15.
 */
@Controller
@RequestMapping(value = "/auth/store/promotetab/*")
public class StorePromoteTabController {
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BannerStorageService bannerStorageService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    protected String showAddPage(AppStorePromoteTabParameter para, Model model) {
        model.addAttribute("para", para);
        model.addAttribute("types", AppStorePromoteTabParameter.PROMOTE_TAB_TYPES);
        return "store/info/promotetab/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    protected String addPromoteTab(String name, int type, int rank, String color,
            @RequestParam(required = false) CommonsMultipartFile icon, Model model) {
        color = ParameterUtils.formatColor(color);
        AppStorePromoteTabParameter para = new AppStorePromoteTabParameter();
        para.setName(name);
        para.setType(type);
        para.setColor(color);
        para.setRank(rank);
        String errMsg = checkParameter(para);
        String iconUrl = null;
        if (StringUtils.isBlank(errMsg)) {
            try {
                iconUrl = bannerStorageService.saveStoreTabIcon(icon);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            para.setIcon(iconUrl);
            ApiRespWrapper<Boolean> resp = iosStoreApiService.addAppStorePromoteTab(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getStatus() != 0 || !Boolean.TRUE.equals(resp.getData())) {
                errMsg = resp.getMessage();
            } else {
                errMsg = "添加成功";
            }
        }
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("types", AppStorePromoteTabParameter.PROMOTE_TAB_TYPES);
        return "store/info/promotetab/add.ftl";
    }

    private String checkParameter(AppStorePromoteTabParameter para) {
        if (para == null) {
            return "参数为空";
        }
        if (StringUtils.isBlank(para.getName())) {
            return "Tab名称为空";
        }
        if (para.getRank() <= 0) {
            return "非法排序位置";
        }
        if (!AppStorePromoteTabParameter.PROMOTE_TAB_TYPES.containsKey(String.valueOf(para.getType()))) {
            return "非法类型";
        }
        return "";
    }

    @RequestMapping("/list")
    public String list(StatusStartSizeParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(AppStorePromoteTab.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<AppStorePromoteTab>> resp = iosStoreApiService.listAppStorePromoteTabs(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppStorePromoteTab> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();

        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", AppStorePromoteTab.STATUS);
        model.addAttribute("types", AppStorePromoteTabParameter.PROMOTE_TAB_TYPES);
        model.addAttribute("para", para);
        return "store/info/promotetab/list.ftl";
    }

    @RequestMapping("/detail")
    public String detail(int id, Model model) {
        ApiRespWrapper<AppStorePromoteTab> resp = iosStoreApiService.queryAppStorePromoteTab(id);
        AppStorePromoteTab value = resp == null ? null : resp.getData();
        model.addAttribute("value", value);
        model.addAttribute("status", AppStorePromoteTab.STATUS);
        model.addAttribute("types", AppStorePromoteTabParameter.PROMOTE_TAB_TYPES);
        return "store/info/promotetab/detail.ftl";
    }


    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(int id, Integer status, Integer rank, String name, String color) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        AppStorePromoteTabModifyParameter para = new AppStorePromoteTabModifyParameter();
        para.setTabId(id);
        if (status != null) {
            para.setStatus(status);
            resp = this.iosStoreApiService.modifyAppStorePromoteTabStatus(para);
        } else if (rank != null) {
            para.setRank(rank);
            resp = this.iosStoreApiService.modifyAppStorePromoteTabRank(para);
        } else if (!StringUtils.isEmpty(color)) {
            color = ParameterUtils.formatColor(color);
            para.setColor(color);
            resp = this.iosStoreApiService.modifyAppStorePromoteTabColor(para);
        } else if (!StringUtils.isEmpty(name)) {
            para.setName(name);
            resp = this.iosStoreApiService.modifyAppStorePromoteTabName(para);

        }
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/modifyicon", method = RequestMethod.POST)
    public String modifyicon(int id, @RequestParam(required = false) CommonsMultipartFile icon, Model model) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的记录Id";
        }
        if (icon == null || icon.isEmpty()) {
            errMsg = "未知的图标";
        }
        String iconUrl = null;
        if (StringUtils.isBlank(errMsg)) {
            try {
                iconUrl = bannerStorageService.saveStoreTabIcon(icon);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppStorePromoteTabModifyParameter para = new AppStorePromoteTabModifyParameter();
            para.setIcon(iconUrl);
            para.setTabId(id);
            ApiRespWrapper<Boolean> resp = iosStoreApiService.modifyAppStorePromoteTabIcon(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getStatus() != 0 || !Boolean.TRUE.equals(resp.getData())) {
                errMsg = resp.getMessage();
            } else {
                errMsg = "添加成功";
            }
        }
        model.addAttribute("id", id);
        model.addAttribute("errMsg", errMsg);
        return "redirect:/auth/store/info/promotetab/detail";
    }
}
