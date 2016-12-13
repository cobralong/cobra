package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.dto.info.AppStoreClientInfo;
import com.appchina.ios.core.dto.system.ClientApnCertInfo;
import com.appchina.ios.mgnt.controller.model.ClientApnCertInfoParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.StartSizeParameter;
import com.appchina.ios.web.utils.CollectionUtils;

/**
 * 上架版本的APP相关
 * 
 * @author luofei@appchina.com (Your Name Here)
 *
 */
@Controller
@RequestMapping(value = "/auth/system/appstore/client/*")
public class AppStoreClientInfoController {
//    private static final Logger log = Logger.getLogger(AppStoreClientInfoController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private BannerStorageService bannerStorageService;

    @RequestMapping(value = "/cert/list", method = RequestMethod.GET)
    public String addUser(@ModelAttribute("errMsg") String errMsg, @ModelAttribute("bundleId") String bundleId,
            Model model) {
        ClientApnCertInfoParameter parameter = new ClientApnCertInfoParameter();
        if (!StringUtils.isEmpty(bundleId)) {
            parameter.setBundleId(bundleId);
        }
        parameter.setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<ClientApnCertInfo>> certResp = backendApiService
                .listAppStoreClientCertInfo(parameter);
        StartSizeParameter para = new StartSizeParameter();
        para.setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<AppStoreClientInfo>> clientResp = iosStoreApiService.listAppStoreClientInfo(para);
        Map<String, String> bundleNameMap = new HashMap<String, String>();
        if (clientResp != null && clientResp.getData() != null
                && CollectionUtils.notEmptyAndNull(clientResp.getData().getResultList())) {
            for (AppStoreClientInfo info : clientResp.getData().getResultList()) {
                bundleNameMap.put(info.getBundleId(), info.getName());
            }
        }
        List<ClientApnCertInfo> certInfo = new ArrayList<ClientApnCertInfo>();
        if (certResp != null && certResp.getData() != null
                && CollectionUtils.notEmptyAndNull(certResp.getData().getResultList())) {
            certInfo = certResp.getData().getResultList();

        }
        model.addAttribute("datas", certInfo);
        model.addAttribute("bundleIdNameMap", bundleNameMap);
        if (!StringUtils.isEmpty(errMsg)) {
            model.addAttribute("errMsg", errMsg);
        }
        return "system/appstore/client/cert/list.ftl";
    }

    @RequestMapping(value = "/cert/add", method = RequestMethod.POST)
    public String addCert(String bundleId, @RequestParam(required = false) CommonsMultipartFile testCertFile,
            @RequestParam(required = false) CommonsMultipartFile productCertFile, String testCertPwd,
            String productCertPwd, int testPoolSize, int productPoolSize, RedirectAttributes rattrs) {
        String testP12 = "";
        if (testCertFile != null && !testCertFile.isEmpty()) {
            testP12 = bannerStorageService.saveTestP12(testCertFile);
        }
        String productP12 = "";
        if (testCertFile != null && !testCertFile.isEmpty()) {
            productP12 = bannerStorageService.saveProductP12(productCertFile);
        }
        ClientApnCertInfoParameter parameter = new ClientApnCertInfoParameter();
        parameter.setBundleId(bundleId);
        parameter.setProductCertFile(productP12);
        parameter.setProductCertPwd(productCertPwd);
        parameter.setProductPoolSize(productPoolSize);
        parameter.setTestCertFile(testP12);
        parameter.setTestCertPwd(testCertPwd);
        parameter.setTestPoolSize(testPoolSize);
        ApiRespWrapper<Boolean> resp = backendApiService.addAppStoreClientCertInfo(parameter);
        String errMsg = "";
        if (resp == null) {
            errMsg = "未知编辑后台错误。数据返回为空!";
        } else if (resp.getData() == null) {
            errMsg = "未知API服务器错误：Status:" + resp.getStatus();
        } else if (!resp.getData()) {
            errMsg = resp.getMessage();
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("bundleId", bundleId);
        return "redirect:/auth/system/appstore/client/cert/list";
    }

}
