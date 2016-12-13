// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.info;

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

import com.appchina.ios.core.dto.info.AppStoreH5Game;
import com.appchina.ios.mgnt.controller.model.UploadFileResp;
import com.appchina.ios.mgnt.controller.model.info.AppStoreH5GameParameter;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.service.impl.StoreCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
@Controller
@RequestMapping(value = "/auth/store/h5game/*")
public class AppStoreH5GameController {
    private static final Logger log = Logger.getLogger(AppStoreH5GameController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private StoreCommonParameterService storeCommonParameterService;
    @Autowired
    private BannerStorageService bannerStorageService;

    @RequestMapping("/list")
    public String list(@ModelAttribute("errMsg") String errMsg, @ModelAttribute("bundleId") String bundleId,
            AppStoreH5GameParameter para, Model model) {
        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreH5Game>> h5GameListResp = this.iosStoreApiService.listAppStoreH5Game(para);
        if (h5GameListResp != null && h5GameListResp.getData() != null) {
            model.addAttribute("values", h5GameListResp.getData().getResultList());
            para.getPager().setTotal(h5GameListResp.getData().getTotalCount());
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("para", para);
        storeCommonParameterService.addBundles("bundleIds", model);
        return "store/h5game/list.ftl";
    }

    @RequestMapping("/add")
    public String add(String bundleId, String name, String desc, String url,
            @RequestParam(required = false) CommonsMultipartFile icon, RedirectAttributes rattrs) {
        // rootId exists
        String errMsg = "";
        if (StringUtils.isEmpty(bundleId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(desc)
                || StringUtils.isEmpty(url) || icon == null || icon.isEmpty()) {
            errMsg = "未知的参数。";
        }
        AppStoreH5GameParameter para = null;
        if (StringUtils.isEmpty(errMsg)) {
            UploadFileResp uploadFileResp = bannerStorageService.saveH5GameIcon(icon);
            if (!uploadFileResp.isSaved()) {
                errMsg = uploadFileResp.getMessage();
            } else {
                para = new AppStoreH5GameParameter();
                para.setBundleId(bundleId);
                para.setDesc(desc);
                para.setName(name);
                para.setUrl(url);
                para.setIcon(uploadFileResp.getUrl());
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addAppStoreH5Game(para);
                if (resp == null) {
                    errMsg = "未知网络错误.";
                } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                    errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误，错误码：" + resp.getStatus() : resp
                            .getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("bundleId", bundleId);
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/h5game/list";
    }

    @RequestMapping("/modify.json")
    @ResponseBody
    public String modify(int id, Integer status) {
        String errMsg = "";
        if (status != null) {
            AppStoreH5GameParameter para = new AppStoreH5GameParameter();
            para.setId(id);
            para.setStatus(status);
            try {
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppStoreH5Game(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                    errMsg = StringUtils.isEmpty(resp.getMessage()) ? "远程服务器错误，错误码：" + resp.getStatus() : resp
                            .getMessage();
                }
            } catch (Exception e) {
                log.error("Modify h5game data status failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败,Errmsg:" + e.getMessage();
            }
        }
        return errMsg;
    }

}
