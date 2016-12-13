package com.appchina.ios.mgnt.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.dto.system.AppleColorDict;
import com.appchina.ios.core.dto.system.AppleDevice;
import com.appchina.ios.core.dto.system.AppleOsVersion;
import com.appchina.ios.core.dto.system.ApplePlatform;
import com.appchina.ios.core.dto.system.PcSuiteBgLookupInfo;
import com.appchina.ios.core.dto.system.PcSuiteIphoneModel;
import com.appchina.ios.mgnt.controller.model.AppleColorDictParameter;
import com.appchina.ios.mgnt.controller.model.AppleDeviceParameter;
import com.appchina.ios.mgnt.controller.model.IdStatusParameter;
import com.appchina.ios.mgnt.controller.model.OsVersionParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteIphoneModelParameter;
import com.appchina.ios.mgnt.controller.model.PlatformOsVersionParameter;
import com.appchina.ios.mgnt.controller.model.PlatformParameter;
import com.appchina.ios.mgnt.controller.model.UploadImgResp;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BackendPcSuiteApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;

@Controller
@RequestMapping(value = "/auth/apple/*")
public class IosDeviceController {
    private static final Logger log = Logger.getLogger(IosDeviceController.class);
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private BackendPcSuiteApiService backendPcSuiteApiService;
    @Autowired
    private BannerStorageService bannerStorageService;

    // platform
    @RequestMapping(value = "/ios/platform/list")
    public String listplatform(@ModelAttribute("errMsg") String errMsg, Model model) {
        ApiRespWrapper<ListWrapResp<ApplePlatform>> resp = this.backendApiService.listPlatform();
        ListWrapResp<ApplePlatform> data = resp == null ? null : resp.getData();
        model.addAttribute("values", data.getResultList());
        return "apple/ios/platform/list.ftl";
    }

    @RequestMapping(value = "/ios/platform/modify")
    @ResponseBody
    public String modifyPlatform(int id, Integer status, Integer defaultStatus) {
        if (id <= 0) {
            return "无效的Id.";
        }
        if (status == null && defaultStatus == null) {
            return "非法的操作数据.";
        }
        PlatformOsVersionParameter para = new PlatformOsVersionParameter(id, 1, status, defaultStatus);
        try {
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyPlatformOsVersion(para);
            if (resp == null) {
                return "未知错误";
            } else if (!resp.getData()) {
                return resp.getMessage();
            }
            return "";
        } catch (Exception e) {
            log.error("Modify platform osversion failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
            return "添加失败";
        }
    }

    @RequestMapping(value = "/ios/platform/add", method = RequestMethod.POST)
    public String addplatform(String platform, String content, Integer defaultStatus, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(platform)) {
            errMsg = "平台型号不能为空.";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(content)) {
            errMsg = "型号描述不能为空.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            defaultStatus = defaultStatus == null || defaultStatus.intValue() != 1 ? 0 : 1;
        }
        if (StringUtils.isEmpty(errMsg)) {
            PlatformParameter para = new PlatformParameter(platform, content, defaultStatus);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addPlatform(para);
                if (resp == null) {
                    errMsg = "未知错误!";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                } else {
                    errMsg = "添加成功!";
                }
            } catch (Exception e) {
                log.error("Add platform failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败!";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/apple/ios/platform/list";
    }

    // ios version
    @RequestMapping(value = "/ios/version/add", method = RequestMethod.POST)
    public String addosversion(String osVersion, Integer defaultStatus, RedirectAttributes rattrs) {

        String errMsg = "";
        if (StringUtils.isEmpty(osVersion)) {
            errMsg = "系统版本不能为空.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            defaultStatus = defaultStatus == null || defaultStatus.intValue() != 1 ? 0 : 1;
        }
        if (StringUtils.isEmpty(errMsg)) {
            OsVersionParameter para = new OsVersionParameter(osVersion, defaultStatus);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addOsVersion(para);
                if (resp == null) {
                    errMsg = "未知错误!";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                } else {
                    errMsg = "添加成功!";
                }
            } catch (Exception e) {
                log.error("Add osversion failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败!";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/apple/ios/version/list";
    }

    @RequestMapping(value = "/ios/version/modify")
    @ResponseBody
    public String modifyIosVersion(int id, Integer status, Integer defaultStatus) {
        if (id <= 0) {
            return "无效的Id.";
        }
        if (status == null && defaultStatus == null) {
            return "操作的操作数据.";
        }
        PlatformOsVersionParameter para = new PlatformOsVersionParameter(id, 2, status, defaultStatus);
        try {
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyPlatformOsVersion(para);
            if (resp == null) {
                return "未知错误";
            } else if (!resp.getData()) {
                return resp.getMessage();
            }
            return "";
        } catch (Exception e) {
            log.error("Modify platform osversion failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
            return "添加失败";
        }
    }

    @RequestMapping(value = "/ios/version/list")
    public String listosversion(@ModelAttribute("errMsg") String errMsg, Model model) {
        ApiRespWrapper<ListWrapResp<AppleOsVersion>> resp = this.backendApiService.listOsVersion();
        ListWrapResp<AppleOsVersion> data = resp == null ? null : resp.getData();
        model.addAttribute("values", data.getResultList());
        model.addAttribute("errMsg", errMsg);
        return "apple/ios/version/list.ftl";
    }

    @RequestMapping(value = "/device/model/list")
    public String listAppleDeviceModel(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("platform") String platform, @ModelAttribute("color") String rgb,
            @ModelAttribute("appleDeviceId") String appleDeviceId, PcSuiteIphoneModelParameter para, Model model) {
        if (!StringUtils.isEmpty(rgb) && !StringUtils.isEmpty(platform)) {
            AppleDeviceParameter appleDeviceParameter = new AppleDeviceParameter();
            appleDeviceParameter.setPlatform(platform);
            appleDeviceParameter.setRgb(rgb);
            ApiRespWrapper<ListWrapResp<AppleDevice>> deviceResp = backendApiService
                    .listAppleDevice(appleDeviceParameter);
            if (deviceResp != null && deviceResp.getData() != null && deviceResp.getData().getResultList().size() == 1) {
                AppleDevice appleDevice = deviceResp.getData().getResultList().get(0);
                if (appleDevice != null) {
                    para.setAppleDeviceId(appleDevice.getId());
                }
            }
        } else if (!StringUtils.isEmpty(appleDeviceId)) {
            para.setAppleDeviceId(Integer.valueOf(appleDeviceId));
            model.addAttribute("addAppleDeviceId", appleDeviceId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteIphoneModel>> resp = backendApiService.listPcSuiteIPhoneModel(para);
        List<PcSuiteIphoneModel> datas = null;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += "网络异常错误。";
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        }
        Map<String, String> platformDeviceMap = new HashMap<String, String>();
        try {
            ApiRespWrapper<ListWrapResp<ApplePlatform>> deviceResp = backendApiService.listPlatform();
            if (deviceResp != null && deviceResp.getData() != null && deviceResp.getData().getResultList() != null) {
                for (ApplePlatform applePlatform : deviceResp.getData().getResultList()) {
                    platformDeviceMap.put(applePlatform.getPlatform(), applePlatform.getContent());
                }
                model.addAttribute("platforms", platformDeviceMap);
            }
        } catch (Exception e) {
        }
        AppleDeviceParameter appleDeviceParameter = new AppleDeviceParameter();
        appleDeviceParameter.setStatus(StatusType.STATUS_OK.getStatus());
        appleDeviceParameter.setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<AppleDevice>> appleDeviceListResp = backendApiService
                .listAppleDevice(appleDeviceParameter);
        if (appleDeviceListResp != null && appleDeviceListResp.getData() != null
                && CollectionUtils.notEmptyAndNull(appleDeviceListResp.getData().getResultList())) {
            model.addAttribute("devices", appleDeviceListResp.getData().getResultList());
        }
        ApiRespWrapper<ListWrapResp<ApplePlatform>> platformResp = backendApiService.listPlatform();
        if (platformResp != null && platformResp.getData() != null) {
            Map<String, String> platforms = new HashMap<String, String>();
            for (ApplePlatform applePlatform : platformResp.getData().getResultList()) {
                platforms.put(applePlatform.getPlatform(), applePlatform.getContent());
            }
            model.addAttribute("platforms", platforms);
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        return "apple/device/model/list.ftl";
    }

    @RequestMapping(value = "/device/model/add")
    public String addPcsuiteIphoneModel(int appleDeviceId, Integer paddingLeft, Integer paddingTop,
            Integer paddingBottom, Integer paddingRight, @RequestParam(required = false) CommonsMultipartFile screen,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (appleDeviceId <= 0 || paddingBottom == null || paddingLeft == null || paddingRight == null
                || paddingTop == null || screen == null || screen.isEmpty()) {
            errMsg = "未知的参数";
        }
        if (StringUtils.isEmpty(errMsg)) {
            UploadImgResp uploadImgResp = bannerStorageService.savePcSuiteIphoneModelImg(screen);
            if (!uploadImgResp.isSaved()) {
                errMsg = uploadImgResp.getMessage();
            } else {
                PcSuiteIphoneModelParameter para = new PcSuiteIphoneModelParameter();
                para.setHeight(uploadImgResp.getHeight());
                para.setImgUrl(uploadImgResp.getUrl());
                para.setPaddingBottom(paddingBottom);
                para.setPaddingLeft(paddingLeft);
                para.setPaddingRight(paddingRight);
                para.setPaddingTop(paddingTop);
                para.setWidth(uploadImgResp.getWidth());
                para.setAppleDeviceId(appleDeviceId);
                ApiRespWrapper<Boolean> resp = backendApiService.addPcSuiteIPhoneModel(para);
                if (resp == null) {
                    errMsg = "未知网络异常错误。";
                } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                    errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp
                            .getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("appleDeviceId", String.valueOf(appleDeviceId));
        return "redirect:/auth/apple/device/model/list";
    }

    @RequestMapping(value = "/device/model/modify")
    @ResponseBody
    public String modifyPcsuiteIphoneModel(Integer id, Integer paddingLeft, Integer paddingTop, Integer paddingBottom,
            Integer paddingRight, Integer status, RedirectAttributes rattrs) {
        String errMsg = "";
        if (paddingBottom == null && paddingLeft == null && paddingRight == null && paddingTop == null
                && status == null) {
            errMsg = "未知的参数";
        }
        if (StringUtils.isEmpty(errMsg)) {
            PcSuiteIphoneModelParameter para = new PcSuiteIphoneModelParameter();
            para.setStatus(status);
            para.setPaddingBottom(paddingBottom);
            para.setPaddingLeft(paddingLeft);
            para.setPaddingRight(paddingRight);
            para.setPaddingTop(paddingTop);
            para.setId(id);
            ApiRespWrapper<Boolean> resp = backendApiService.modifyPcSuiteIPhoneModel(para);
            if (resp == null) {
                errMsg = "未知网络异常错误。";
            } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        return errMsg;
    }

    @RequestMapping(value = "/device/model/modify/icon")
    public String modifyPcsuiteIphoneModelIcon(int id, @RequestParam(required = false) CommonsMultipartFile screen,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (screen == null || screen.isEmpty()) {
            errMsg = "未知的参数";
        }
        if (StringUtils.isEmpty(errMsg)) {
            UploadImgResp uploadImgResp = bannerStorageService.savePcSuiteIphoneModelImg(screen);
            if (!uploadImgResp.isSaved()) {
                errMsg = uploadImgResp.getMessage();
            } else {
                PcSuiteIphoneModelParameter para = new PcSuiteIphoneModelParameter();
                para.setId(id);
                para.setHeight(uploadImgResp.getHeight());
                para.setImgUrl(uploadImgResp.getUrl());
                para.setWidth(uploadImgResp.getWidth());
                ApiRespWrapper<Boolean> resp = backendApiService.modifyPcSuiteIPhoneModel(para);
                if (resp == null) {
                    errMsg = "未知网络异常错误。";
                } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                    errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp
                            .getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/apple/device/model/detail?id=" + id;
    }

    @RequestMapping(value = "/device/list")
    public String listAppleDevice(@ModelAttribute("errMsg") String errMsg, @ModelAttribute("platform") String platform,
            AppleDeviceParameter para, Model model) {
        if (StringUtils.isNotBlank(platform)) {
            para.setPlatform(platform);
        }
        para.transfer();
        try {
            ApiRespWrapper<ListWrapResp<AppleDevice>> resp = this.backendApiService.listAppleDevice(para);
            if (resp != null && resp.getData() != null) {
                model.addAttribute("values", resp.getData().getResultList());
                para.getPager().setTotal(resp.getData().getTotalCount());
            }
        } catch (Exception e) {
        }
        try {
            ApiRespWrapper<ListWrapResp<ApplePlatform>> resp = this.backendApiService.listPlatform();
            if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
                Map<String, String> platforms = new LinkedHashMap<String, String>();
                platforms.put(String.valueOf(StatusType.STATUS_ALL.getStatus()), StatusType.STATUS_ALL.getDesc());
                for (ApplePlatform applePlatform : resp.getData().getResultList()) {
                    platforms.put(applePlatform.getPlatform(), applePlatform.getContent());
                }
                model.addAttribute("platforms", platforms);
            }
        } catch (Exception e) {
        }
        try {
            ApiRespWrapper<ListWrapResp<AppleColorDict>> resp = backendApiService.listAppleColorDict();
            if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
                Map<String, String> colors = new HashMap<String, String>();
                colors.put(String.valueOf(StatusType.STATUS_ALL.getStatus()), StatusType.STATUS_ALL.getDesc());
                for (AppleColorDict color : resp.getData().getResultList()) {
                    colors.put(String.valueOf(color.getId()), color.getColor() + "----" + color.getRgb());
                }
                model.addAttribute("colors", colors);
            }
        } catch (Exception e) {
        }
        model.addAttribute("para", para);
        model.addAttribute("colorId", null);
        model.addAttribute("platform", platform);
        return "apple/device/list.ftl";
    }

    @RequestMapping(value = "/device/add", method = RequestMethod.POST)
    public String addAppleDevice(String platform, int colorDictId, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isBlank(platform)) {
            errMsg = "平台型号不能为空.";
        }
        String color = null;
        String rgb = null;
        try {
            ApiRespWrapper<ListWrapResp<AppleColorDict>> resp = backendApiService.listAppleColorDict();
            if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
                for (AppleColorDict dict : resp.getData().getResultList()) {
                    if (dict.getId() == colorDictId) {
                        color = dict.getColor();
                        rgb = dict.getRgb();
                    }
                }
            }
        } catch (Exception e) {
        }
        if (StringUtils.isEmpty(rgb)) {
            errMsg = "设备颜色值不能为空";
        }
        if (StringUtils.isEmpty(errMsg)) {
            AppleDeviceParameter para = new AppleDeviceParameter();
            para.setPlatform(platform);
            para.setColor(color);
            para.setRgb(rgb);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addAppleDevice(para);
                if (resp == null) {
                    errMsg = "未知错误!";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                } else {
                    errMsg = "添加成功!";
                }
            } catch (Exception e) {
                log.error("Add platform failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败!";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("platform", platform);
        return "redirect:/auth/apple/device/list";
    }

    @RequestMapping(value = "/device/modify")
    @ResponseBody
    public String modifyAppleDevice(int id, String color, String rgb) {
        AppleDeviceParameter para = new AppleDeviceParameter();
        para.setId(id);
        para.setColor(color);
        para.setRgb(rgb);
        try {
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyAppleDevice(para);
            if (resp == null) {
                return "未知错误";
            } else if (!resp.getData()) {
                return resp.getMessage();
            }
        } catch (Exception e) {
            log.error("修改苹果设备相关信息失败", e);
            return e.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/device/model/detail")
    public String detailPcsuiteIphoneModel(int id, @ModelAttribute("errMsg") String errMsg, Model model) {
        PcSuiteIphoneModelParameter para = new PcSuiteIphoneModelParameter();
        para.setId(id);
        ApiRespWrapper<PcSuiteIphoneModel> resp = backendApiService.detailPcSuiteIPhoneModel(para);
        if (resp != null && resp.getData() != null) {
            model.addAttribute("data", resp.getData());
            AppleDeviceParameter appleDeviceParameter = new AppleDeviceParameter();
            appleDeviceParameter.setId(resp.getData().getAppleDeviceId());

            ApiRespWrapper<ListWrapResp<AppleDevice>> appleDeviceResp = backendApiService
                    .listAppleDevice(appleDeviceParameter);
            if (appleDeviceResp != null && appleDeviceResp.getData() != null
                    && appleDeviceResp.getData().getResultList() != null
                    && appleDeviceResp.getData().getResultList().size() == 1) {
                AppleDevice appleDevice = appleDeviceResp.getData().getResultList().get(0);
                model.addAttribute("appleDevice", appleDevice);
                ApiRespWrapper<ListWrapResp<ApplePlatform>> platformResp = backendApiService.listPlatform();
                if (platformResp != null && platformResp.getData() != null) {
                    for (ApplePlatform applePlatform : platformResp.getData().getResultList()) {
                        if (StringUtils.equalsIgnoreCase(applePlatform.getPlatform(), appleDevice.getPlatform())) {
                            model.addAttribute("platform", applePlatform);
                            break;
                        }
                    }
                }
            }

        }
        model.addAttribute("errMsg", errMsg);
        return "apple/device/model/detail.ftl";
    }

    @RequestMapping(value = "/color/dict/list")
    public String listColorDict(@ModelAttribute("errMsg") String errMsg, Model model) {
        ApiRespWrapper<ListWrapResp<AppleColorDict>> resp = backendApiService.listAppleColorDict();
        if (resp != null && resp.getData() != null) {
            model.addAttribute("values", resp.getData().getResultList());
        }
        model.addAttribute("errMsg", errMsg);
        return "apple/color/dict/list.ftl";
    }

    @RequestMapping(value = "/device/model/lookup/list")
    public String listAppleDeviceModelLookup(Model model) {
        try {
            ApiRespWrapper<ListWrapResp<PcSuiteBgLookupInfo>> resp = backendApiService.listPcSuiteBgLookupInfo();
            if (resp != null && resp.getData() != null) {
                model.addAttribute("values", resp.getData().getResultList());
                if (resp.getData().getResultList() != null) {
                    model.addAttribute("total", resp.getData().getResultList().size());
                }
            }
        } catch (Exception e) {
        }
        try {
            ApiRespWrapper<ListWrapResp<ApplePlatform>> resp = backendApiService.listPlatform();
            if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
                Map<String, String> platforms = new HashMap<String, String>();
                for (ApplePlatform platform : resp.getData().getResultList()) {
                    platforms.put(platform.getPlatform(), platform.getContent());
                }
                model.addAttribute("platforms", platforms);
            }
        } catch (Exception e) {
        }
        try {
            ApiRespWrapper<ListWrapResp<AppleColorDict>> resp = backendApiService.listAppleColorDict();
            if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
                Map<String, String> colors = new HashMap<String, String>();
                for (AppleColorDict platform : resp.getData().getResultList()) {
                    colors.put(platform.getRgb(), platform.getColor());
                }
                model.addAttribute("colors", colors);
            }
        } catch (Exception e) {
        }
        return "apple/device/model/lookup/list.ftl";
    }

    @RequestMapping(value = "/device/model/lookup/modify.json")
    @ResponseBody
    public String modifyAppleDeviceModelLookup(int id, int status) {
        String errMsg = "";
        IdStatusParameter g = new IdStatusParameter();
        g.setId(id);
        g.setStatus(status);
        try {
            ApiRespWrapper<Boolean> resp = backendApiService.modifyPcSuiteBgLookupInfo(g);
            if (resp == null) {
                errMsg = "未知服务错误，请检测访问地址";
            } else {
                if (resp.getData() == null || !resp.getData()) {
                    if (StringUtils.isEmpty(resp.getMessage())) {
                        errMsg = "远程API服务器内部错误。Errmsg:" + resp.getStatus();
                    } else {
                        errMsg = "远程API服务器内部错误。Errmsg:" + resp.getMessage();
                    }
                }
            }
        } catch (Exception e) {
            errMsg = "未知编辑后台错误，Errmsg:" + e.getMessage();
        }
        return errMsg;
    }

    @RequestMapping(value = "/device/model/lookup/fix")
    public String fixAppleDeviceModelLookup(int id, RedirectAttributes rattrs) {
        String errMsg = "";
        try {
            ApiRespWrapper<Integer> resp = backendApiService.fixPcSuiteBgLookupInfo(id);
            if (resp == null) {
                errMsg = "未知服务错误，请检测访问地址";
            } else {
                if (resp.getData() == null || resp.getData() <= 0) {
                    if (StringUtils.isEmpty(resp.getMessage())) {
                        errMsg = "远程API服务器内部错误。Errmsg:" + resp.getStatus();
                    } else {
                        errMsg = "远程API服务器内部错误。Errmsg:" + resp.getMessage();
                    }
                } else {
                    rattrs.addFlashAttribute("appleDeviceId", String.valueOf(resp.getData().intValue()));
                }
            }
        } catch (Exception e) {
            errMsg = "未知编辑后台错误，Errmsg:" + e.getMessage();
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/apple/device/model/list";
    }


    @RequestMapping(value = "/color/dict/modify")
    @ResponseBody
    public String modifyColorDict(int id, String color, Model model) {
        String errMsg = "";
        AppleColorDictParameter g = new AppleColorDictParameter();
        g.setId(id);
        g.setColor(color);
        try {
            ApiRespWrapper<Boolean> resp = backendApiService.modifyAppleColorDict(g);
            if (resp == null) {
                errMsg = "未知服务错误，请检测访问地址";
            } else {
                if (resp.getData() == null || !resp.getData()) {
                    if (StringUtils.isEmpty(resp.getMessage())) {
                        errMsg = "远程API服务器内部错误。Errmsg:" + resp.getStatus();
                    } else {
                        errMsg = "远程API服务器内部错误。Errmsg:" + resp.getMessage();
                    }
                }
            }
        } catch (Exception e) {
            errMsg = "未知编辑后台错误，Errmsg:" + e.getMessage();
        }
        return errMsg;
    }

    @RequestMapping(value = "/color/dict/add")
    public String addColorDict(RedirectAttributes rattrs, String rgb, String color, Model model) {
        String errMsg = null;
        AppleColorDictParameter g = new AppleColorDictParameter();
        g.setRgb(rgb);
        g.setColor(color);
        try {
            ApiRespWrapper<Boolean> resp = backendApiService.addAppleColorDict(g);
            if (resp == null) {
                errMsg = "未知服务错误，请检测访问地址";
            } else {
                if (resp.getData() == null || !resp.getData()) {
                    if (StringUtils.isEmpty(resp.getMessage())) {
                        errMsg = "远程API服务器内部错误。Errmsg:" + resp.getStatus();
                    } else {
                        errMsg = "远程API服务器内部错误。Errmsg:" + resp.getMessage();
                    }
                }
            }
        } catch (Exception e) {
            errMsg = "未知编辑后台错误，Errmsg:" + e.getMessage();
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/apple/color/dict/list";
    }
}
