package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.dto.info.DefaultFlag;
import com.appchina.ios.core.dto.online.AppLanguage;
import com.appchina.ios.core.dto.system.AppleOsVersion;
import com.appchina.ios.core.dto.system.CpuArch;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceAuthorizerDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceConnectDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceDriverDetailInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteAppleDeviceFullDriverInfo;
import com.appchina.ios.core.dto.system.PcSuiteChannel;
import com.appchina.ios.core.dto.system.PcSuiteCommMobileClientInfo;
import com.appchina.ios.core.dto.system.PcSuiteIosProgrammerDriver;
import com.appchina.ios.core.dto.system.PcSuiteItunesDriver;
import com.appchina.ios.core.dto.system.PcSuiteItunesDriverInstallInfo;
import com.appchina.ios.core.dto.system.PcSuiteLangInfo;
import com.appchina.ios.core.dto.system.PcSuiteUpgradeSwitchIpConf;
import com.appchina.ios.core.dto.system.PcSuiteVersion;
import com.appchina.ios.core.dto.system.PcSuiteWebClipNavigationInfo;
import com.appchina.ios.core.dto.system.ProductIpSwitch;
import com.appchina.ios.core.dto.system.Windows;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.core.utils.PcSuiteVersionUtils;
import com.appchina.ios.mgnt.controller.model.IdStatusParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteAppleDeviceConnectDriverInfoParamater;
import com.appchina.ios.mgnt.controller.model.PcSuiteAppleDeviceDriverDetailInfoParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteAppleDeviceDriverInfoParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteChannelParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteIosProgrammerDriverParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteItunesDriverInstallInfoParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteItunesDriverParameter;
import com.appchina.ios.mgnt.controller.model.PcSuiteLangInfoParamater;
import com.appchina.ios.mgnt.controller.model.PcSuiteUpgradeSwitchIpConfParamater;
import com.appchina.ios.mgnt.controller.model.PcSuiteVersionParameter;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.UploadFileResp;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BackendPcSuiteApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.ApiRespWrapperUtils;
import com.appchina.ios.web.utils.CollectionUtils;

@Controller
@RequestMapping(value = "/auth/pcsuite/*")
public class PcsuiteController {
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private BackendPcSuiteApiService backendPcSuiteApiService;
    @Autowired
    private BannerStorageService bannerStorageService;

    // version
    @RequestMapping(value = "/version/list")
    public String listPcSuiteVersion(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("channel") String channel, @ModelAttribute("version") String version,
            PcSuiteVersionParameter para, Model model) {
        para.transfer();
        if (StringUtils.isEmpty(channel)) {
            para.setChannel(channel);
        }
        if (StringUtils.isEmpty(version)) {
            para.setVersion(version);
        }
        ApiRespWrapper<ListWrapResp<PcSuiteVersion>> resp = backendPcSuiteApiService.listPcSuiteVersion(para);
        List<PcSuiteVersion> datas = null;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg = "网络异常错误。";
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        }
        PcSuiteChannelParameter pcSuiteChannelParameter = new PcSuiteChannelParameter();
        pcSuiteChannelParameter.getPager().setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<PcSuiteChannel>> channelResp = backendPcSuiteApiService
                .listPcSuiteChannel(pcSuiteChannelParameter);
        if (channelResp != null && channelResp.getData() != null) {
            model.addAttribute("channels", channelResp.getData().getResultList());
        }
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        model.addAttribute("productIpSwitches", EnumMapUtils.enumToMap(ProductIpSwitch.values(), false));
        return "pcsuite/version/list.ftl";
    }

    @RequestMapping(value = "/version/detail")
    public String detailPcSuiteVersion(int id, Model model) {
        ApiRespWrapper<PcSuiteVersion> resp = backendPcSuiteApiService.detailPcSuiteVersion(id);
        ApiRespWrapperUtils.handleValueResp(resp, null, model);
        PcSuiteChannelParameter pcSuiteChannelParameter = new PcSuiteChannelParameter();
        pcSuiteChannelParameter.getPager().setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<PcSuiteChannel>> channelResp = backendPcSuiteApiService
                .listPcSuiteChannel(pcSuiteChannelParameter);
        ApiRespWrapperUtils.handleListResp("channels", false, channelResp, null, model);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        model.addAttribute("productIpSwitches", EnumMapUtils.enumToMap(ProductIpSwitch.values(), false));
        return "pcsuite/version/detail.ftl";
    }

    @RequestMapping(value = "/version/add")
    public String addPcSuiteVersion(PcSuiteVersionParameter para, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(para.getChannel()) || StringUtils.isEmpty(para.getUpgradeInfo())
                || StringUtils.isEmpty(para.getMd5()) || StringUtils.isEmpty(para.getUrl())
                || StringUtils.isEmpty(para.getVersionName()) || StringUtils.isEmpty(para.getVersion())
                || (para.getVersionCode() == null || para.getVersionCode().intValue() <= 0)) {
            errMsg = "未知参数，请检查参数";
        }
        para.transfer();
        ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.addPcSuiteVersion(para);
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg = "网络异常错误。";
            }
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            errMsg = resp.getMessage();
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("channel", para.getChannel());
        rattrs.addFlashAttribute("version", para.getVersion());
        return "redirect:/auth/pcsuite/version/list";
    }

    @RequestMapping(value = "/version/modify")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyPcSuiteVersion(int id, Integer status, Integer productIpSwitch,
            String upgradeInfo, String upgradeInfoEn) {
        if (id <= 0) {
            return new ApiRespWrapper<Boolean>(-1, "未知参数，请检查参数", false);
        }
        PcSuiteVersionParameter para = new PcSuiteVersionParameter();
        para.setId(id);
        para.setUpgradeInfo(upgradeInfo);
        para.setUpgradeInfoEn(upgradeInfoEn);
        para.setStatus(status);
        para.setProductIpSwitch(productIpSwitch);
        return backendPcSuiteApiService.modifyPcSuiteVersion(para);
    }

    @RequestMapping(value = "/webclip/navigation/info/detail")
    public String detilPcSuiteWebClipNavigationInfo(Model model) {
        ApiRespWrapper<PcSuiteWebClipNavigationInfo> resp = backendPcSuiteApiService
                .detailPcSuiteWebClipNavigationInfo();
        ApiRespWrapperUtils.handleValueResp(resp, null, model);
        model.addAttribute("status", EnumMapUtils.enumToMap(StatusType.values()));
        return "pcsuite/webclip/navigation/info/detail.ftl";
    }

    @RequestMapping(value = "/webclip/navigation/info/modify.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> modifyPcSuiteWebClipNavigation(int id, String title, String desc, String bottom,
            String titleEn, String descEn, String bottomEn, String titleTw, String descTw, String bottomTw) {
        if (id <= 0) {
            return new ApiRespWrapper<Boolean>(-1, "未知参数，请检查参数", false);
        }
        PcSuiteWebClipNavigationInfo g = new PcSuiteWebClipNavigationInfo();
        g.setId(id);
        g.setBottom(bottom);
        g.setBottomEn(bottomEn);
        g.setBottomTw(bottomTw);
        g.setDesc(desc);
        g.setDescEn(descEn);
        g.setDescTw(descTw);
        g.setTitle(title);
        g.setTitleEn(titleEn);
        g.setTitleTw(titleTw);
        return backendPcSuiteApiService.modifyPcSuiteWebClipNavigationInfo(g);
    }

    // channel
    @RequestMapping(value = "/channel/list")
    public String listPcSuiteChannel(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("channel") String channel, PcSuiteChannelParameter para, Model model) {
        para.transfer();
        if (StringUtils.isEmpty(channel)) {
            para.setChannel(channel);
        }
        ApiRespWrapper<ListWrapResp<PcSuiteChannel>> resp = backendPcSuiteApiService.listPcSuiteChannel(para);
        List<PcSuiteChannel> datas = null;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg = "网络异常错误。";
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        return "pcsuite/channel/list.ftl";
    }

    @RequestMapping(value = "/channel/add")
    public String addPcSuiteChannel(PcSuiteChannelParameter para, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(para.getChannel()) || StringUtils.isEmpty(para.getName())) {
            errMsg = "未知参数，请检查参数";
        }
        para.transfer();
        ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.addPcSuiteChannel(para);
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg = "网络异常错误。";
            }
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            errMsg = resp.getMessage();
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("channel", para.getChannel());
        return "redirect:/auth/pcsuite/channel/list";
    }

    // developer driver
    @RequestMapping(value = "/apple/developer/driver/list")
    public String listPcsuiteProgrammerDriver(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("system") String version, PcSuiteIosProgrammerDriverParameter para, Model model) {
        if (!StringUtils.isEmpty(version)) {
            para.setVersion(version);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteIosProgrammerDriver>> resp = backendApiService
                .listPcSuiteProgrammerDriver(para);
        List<PcSuiteIosProgrammerDriver> datas = null;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += "网络异常错误。";
            }
        } else if (!StringUtils.isEmpty(resp.getMessage()) && !StringUtils.equalsIgnoreCase("ok", resp.getMessage())) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += resp.getMessage();
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        }
        ApiRespWrapper<ListWrapResp<AppleOsVersion>> osVersionResp = backendApiService.listOsVersion();
        if (osVersionResp != null && osVersionResp.getData() != null && osVersionResp.getData().getResultList() != null) {
            List<String> versions = new ArrayList<String>();
            for (AppleOsVersion appleOsVersion : osVersionResp.getData().getResultList()) {
                if (StringUtils.countMatches(appleOsVersion.getOsVersion(), ".") == 1) {// 只接收*.*版本的操作系统
                    versions.add(appleOsVersion.getOsVersion());
                }
            }
            Collections.sort(versions);
            Collections.reverse(versions);
            model.addAttribute("versions", versions);
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        model.addAttribute("status", DbStatus.STATUS);
        return "pcsuite/apple/developer/driver/list.ftl";
    }

    @RequestMapping(value = "/apple/developer/driver/modify")
    @ResponseBody
    public String modifyPcsuiteProgrammerDriver(int id, int status, Model model) {
        String errMsg = "";
        PcSuiteIosProgrammerDriverParameter para = new PcSuiteIosProgrammerDriverParameter();
        para.setId(id);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = backendApiService.modifyPcSuiteProgrammerDriver(para);
        if (resp == null) {
            errMsg = "未知网络异常错误。";
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp.getMessage();
        }
        return errMsg;
    }

    @RequestMapping(value = "/apple/developer/driver/add")
    public String addPcsuiteProgrammerDriver(String version, String fileUrl, String fileMd5,
            @RequestParam(required = false) CommonsMultipartFile driver, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(version)) {
            errMsg = "未知的系统版本";
        }
        if (StringUtils.isEmpty(errMsg)) {
            PcSuiteIosProgrammerDriverParameter para = new PcSuiteIosProgrammerDriverParameter();
            para.setVersion(PcSuiteVersionUtils.formatToShortVersion(version));
            if (driver != null && !driver.isEmpty()) {
                UploadFileResp resp = bannerStorageService.savePcSuiteIosProgrammerDriver(driver);
                if (resp.isSaved()) {
                    para.setFileMd5(resp.getMd5());
                    para.setFileUrl(resp.getUrl());
                } else {
                    errMsg = resp.getMessage();
                }
            } else if (!StringUtils.isEmpty(fileUrl) && !StringUtils.isEmpty(fileMd5)) {
                para.setFileMd5(fileMd5);
                para.setFileUrl(fileUrl);
            }
            ApiRespWrapper<Boolean> resp = backendApiService.addPcSuiteProgrammerDriver(para);
            if (resp == null) {
                errMsg = "未知网络异常错误。";
            } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("version", version);
        return "redirect:/auth/pcsuite/apple/developer/driver/list";
    }

    // pcsuite all itunes driver relace older
    @RequestMapping(value = "/apple/device/driver/detail/info/list")
    public String listPcsuiteAppleDeviceDrivceDetailInfo(Integer driverId, @ModelAttribute("errMsg") String errMsg,
            Model model) {
        model.addAttribute("addErrMsg", errMsg);
        PcSuiteAppleDeviceDriverInfoParameter para = new PcSuiteAppleDeviceDriverInfoParameter();
        para.setId(driverId.intValue());
        ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>> resp = backendPcSuiteApiService
                .listPcSuiteAppleDeviceDriverInfo(para);
        PcSuiteAppleDeviceDriverInfo info = null;
        List<PcSuiteAppleDeviceDriverDetailInfo> datas = null;
        long total = 0;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null
                || resp.getData().getResultList().size() != 1) {
            errMsg = "未知的驱动数据.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            info = resp.getData().getResultList().get(0);
            PcSuiteAppleDeviceDriverDetailInfo detail = new PcSuiteAppleDeviceDriverDetailInfo();
            detail.setDriverId(driverId);
            ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverDetailInfo>> detailResp = backendPcSuiteApiService
                    .listPcSuiteAppleDeviceDriverDetailInfo(detail);
            if (detailResp == null) {
                if (StringUtils.isEmpty(errMsg)) {
                    errMsg += "网络异常错误。";
                }
            } else if (!StringUtils.isEmpty(detailResp.getMessage())
                    && !StringUtils.equalsIgnoreCase("ok", detailResp.getMessage())) {
                if (StringUtils.isEmpty(errMsg)) {
                    errMsg += detailResp.getMessage();
                }
            } else if (detailResp.getData() != null) {
                datas = detailResp.getData().getResultList();
                total = detailResp.getData().getTotalCount();
            }
        }
        model.addAttribute("arches", CpuArch.values());
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("info", info);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        model.addAttribute("total", total);
        model.addAttribute("status", DbStatus.STATUS);
        return "pcsuite/apple/device/driver/detail/info/list.ftl";
    }

    // pcsuite all itunes driver relace older
    @RequestMapping(value = "/apple/device/driver/detail/info/add")
    public String addPcsuiteAppleDeviceDrivceDetailInfo(Integer driverId, String name, Model model, String installDesc,
            String uninstallStr, Integer installOrder, String installParam, String version, RedirectAttributes rattrs) {
        String errMsg = "";
        if (driverId <= 0 && StringUtils.isEmpty(uninstallStr)) {
            errMsg = "未知的驱动数据";
        }
        if (StringUtils.isEmpty(errMsg)) {
            PcSuiteAppleDeviceDriverDetailInfoParameter detail = new PcSuiteAppleDeviceDriverDetailInfoParameter();
            detail.setDriverId(driverId);
            detail.setInstallDesc(installDesc);
            if (installOrder != null) {
                detail.setInstallOrder(installOrder);
            }
            detail.setInstallParam(installParam);
            detail.setName(name);
            detail.setVersion(version);
            detail.setUninstallStr(uninstallStr);
            ApiRespWrapper<Boolean> installInfoResp = backendPcSuiteApiService
                    .addPcSuiteAppleDeviceDriverDetailInfo(detail);
            if (installInfoResp == null) {
                errMsg = "未知网络错误.";
            } else if (installInfoResp.getData() != null && !installInfoResp.getData().booleanValue()) {
                errMsg = installInfoResp.getMessage();
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("driverId", driverId);
        return "redirect:/auth/pcsuite/apple/device/driver/detail/info/list?driverId=" + driverId;
    }

    @RequestMapping(value = "/apple/device/driver/detail/info/modify")
    @ResponseBody
    public String modifyPcsuiteAppleDeviceDrivceDetailInfo(int id, String installParam, Integer installOrder,
            String installDesc, String uninstallStr, String version) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的驱动数据";
        }
        if (StringUtils.isEmpty(errMsg)) {
            PcSuiteAppleDeviceDriverDetailInfoParameter detail = new PcSuiteAppleDeviceDriverDetailInfoParameter();
            detail.setId(id);
            detail.setInstallDesc(installDesc);
            if (installOrder != null) {
                detail.setInstallOrder(installOrder);
            }
            detail.setInstallParam(installParam);
            detail.setVersion(version);
            detail.setUninstallStr(uninstallStr);
            ApiRespWrapper<Boolean> installInfoResp = backendPcSuiteApiService
                    .modifyPcSuiteAppleDeviceDriverDetailInfo(detail);
            if (installInfoResp == null) {
                errMsg = "未知网络错误.";
            } else if (installInfoResp.getData() != null && !installInfoResp.getData().booleanValue()) {
                errMsg = installInfoResp.getMessage();
            }
        }
        return errMsg;
    }

    // pcsuite all itunes driver relace older
    @RequestMapping(value = "/apple/device/driver/info/list")
    public String listAppleDeviceDrivceInfo(@ModelAttribute("errMsg") String errMsg,
            PcSuiteAppleDeviceDriverInfoParameter para, Model model) {
        model.addAttribute("addErrMsg", errMsg);
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>> resp = backendPcSuiteApiService
                .listPcSuiteAppleDeviceDriverInfo(para);
        List<PcSuiteAppleDeviceDriverInfo> datas = null;
        long total = 0;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += "网络异常错误。";
            }
        } else if (!StringUtils.isEmpty(resp.getMessage()) && !StringUtils.equalsIgnoreCase("ok", resp.getMessage())) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += resp.getMessage();
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
            para.getPager().setTotal(total);
        }
        model.addAttribute("defaultFlags", EnumMapUtils.enumToMap(DefaultFlag.values(), false));
        model.addAttribute("arches", EnumMapUtils.enumToMap(CpuArch.values(), true));
        model.addAttribute("windows", EnumMapUtils.enumToMap(Windows.values(), true));
        model.addAttribute("pureDefaultFlags", EnumMapUtils.enumToMap(DefaultFlag.values(), false));
        model.addAttribute("pureArches", EnumMapUtils.enumToMap(CpuArch.values(), false));
        model.addAttribute("pureWindows", EnumMapUtils.enumToMap(Windows.values(), false));
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        model.addAttribute("total", total);
        model.addAttribute("status", DbStatus.STATUS);
        return "pcsuite/apple/device/driver/info/list.ftl";
    }

    @RequestMapping(value = "/apple/device/driver/info/modify")
    @ResponseBody
    public String modifyAppleDeviceDrivceInfo(int id, int status, Model model) {
        String errMsg = "";
        PcSuiteAppleDeviceDriverInfoParameter para = new PcSuiteAppleDeviceDriverInfoParameter();
        para.setId(id);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.modifyPcSuiteAppleDeviceDriverInfo(para);
        if (resp == null) {
            errMsg = "未知网络异常错误。";
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp.getMessage();
        }
        return errMsg;
    }

    @RequestMapping(value = "/apple/device/driver/info/add")
    public String addPcSyuiteAppleDeviceDrivceInfo(String name, String arch, String desc, String version,
            String itunesVersion, String url, String md5, @RequestParam(required = false) CommonsMultipartFile driver,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(version) || StringUtils.isEmpty(name) || StringUtils.isEmpty(arch)) {
            errMsg = "未知的参数请求";
        }
        if (StringUtils.isEmpty(errMsg)) {
            PcSuiteAppleDeviceDriverInfoParameter para = new PcSuiteAppleDeviceDriverInfoParameter();
            para.setVersion(PcSuiteVersionUtils.formatToShortVersion(version));
            if (driver != null && !driver.isEmpty()) {
                UploadFileResp resp = bannerStorageService.savePcSuiteAppleDeviceDriver(driver);
                if (resp.isSaved()) {
                    para.setMd5(resp.getMd5());
                    para.setUrl(resp.getUrl());
                } else {
                    errMsg = resp.getMessage();
                }
            } else if (!StringUtils.isEmpty(url) && !StringUtils.isEmpty(md5)) {
                para.setMd5(md5);
                para.setUrl(url);
            }
            if (StringUtils.isEmpty(errMsg)) {
                para.setName(name);
                para.setDesc(desc);
                para.setArch(arch);
                para.setVersion(version);
                para.setItunesVersion(itunesVersion);
                ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.addPcSuiteAppleDeviceDriverInfo(para);
                if (resp == null) {
                    errMsg = "未知网络异常错误。";
                } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                    errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp
                            .getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/pcsuite/apple/device/driver/info/list";
    }


    @RequestMapping(value = "/comm/mobileclient/info/list")
    public String listPcsuiteCommMobileClientInfo(@ModelAttribute("errMsg") String errMsg,
            StatusStartSizeParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteCommMobileClientInfo>> resp = backendPcSuiteApiService
                .listPcsuiteCommMobileClientInfo(para);
        List<PcSuiteCommMobileClientInfo> datas = null;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += "网络异常错误。";
            }
        } else if (!StringUtils.isEmpty(resp.getMessage()) && !StringUtils.equalsIgnoreCase("ok", resp.getMessage())) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += resp.getMessage();
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        model.addAttribute("status", StatusStartSizeParameter.ALL_STATUS);
        return "pcsuite/comm/mobileclient/info/list.ftl";
    }

    @RequestMapping(value = "/comm/mobileclient/info/modify")
    @ResponseBody
    public String modifyPcsuiteCommMobileClientInfo(int id, int status) {
        String errMsg = "";
        IdStatusParameter para = new IdStatusParameter();
        para.setId(id);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.modifyPcsuiteCommMobileClientInfo(para);
        if (resp == null) {
            errMsg = "未知网络异常错误。";
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp.getMessage();
        }
        return errMsg;
    }

    @RequestMapping(value = "/comm/mobileclient/info/add")
    public String addPcsuiteCommMobileClientInfo(String keyword, String bundleId, String startVersion, String name,
            String enName, String twName, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(bundleId)) {
            errMsg = "未知的客户端";
        }
        if (StringUtils.isEmpty(errMsg)) {
            PcSuiteCommMobileClientInfo para = new PcSuiteCommMobileClientInfo();
            para.setBundleId(bundleId);
            para.setStartVersion(startVersion);
            para.setName(name);
            para.setEnName(enName);
            para.setTwName(twName);
            ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.addPcsuiteCommMobileClientInfo(para);
            if (resp == null) {
                errMsg = "未知网络异常错误。";
            } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/pcsuite/comm/mobileclient/info/list";
    }

    // connect driver
    @RequestMapping(value = "/apple/device/connect/driver/info/list")
    public String listPcSuiteAppleDeviceConnectDriverInfo(@ModelAttribute("errMsg") String errMsg,
            PcSuiteAppleDeviceConnectDriverInfoParamater para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceConnectDriverInfo>> resp = backendPcSuiteApiService
                .listPcSuiteAppleDeviceConnectDriverInfo(para);
        List<PcSuiteAppleDeviceConnectDriverInfo> datas = null;
        long total = 0;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += "网络异常错误。";
            }
        } else if (!StringUtils.isEmpty(resp.getMessage()) && !StringUtils.equalsIgnoreCase("ok", resp.getMessage())) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += resp.getMessage();
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
            para.getPager().setTotal(total);
        }
        PcSuiteAppleDeviceDriverInfoParameter driverPara = new PcSuiteAppleDeviceDriverInfoParameter();
        driverPara.setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>> driverResp = this.backendPcSuiteApiService
                .listPcSuiteAppleDeviceDriverInfo(driverPara);
        if (driverResp != null && driverResp.getData() != null
                && CollectionUtils.notEmptyAndNull(driverResp.getData().getResultList())) {
            model.addAttribute("drivers", driverResp.getData().getResultList());
        }
        ApiRespWrapper<List<String>> versionResp = backendPcSuiteApiService.listPcSuiteVersionVersion();
        if (versionResp != null && versionResp.getData() != null) {
            model.addAttribute("pcSuiteVersions", versionResp.getData());
        }
        model.addAttribute("defaultFlags", EnumMapUtils.enumToMap(DefaultFlag.values(), true));
        model.addAttribute("arches", EnumMapUtils.enumToMap(CpuArch.values(), true));
        model.addAttribute("windows", EnumMapUtils.enumToMap(Windows.values(), true));
        model.addAttribute("pureDefaultFlags", EnumMapUtils.enumToMap(DefaultFlag.values(), false));
        model.addAttribute("pureArches", EnumMapUtils.enumToMap(CpuArch.values(), false));
        model.addAttribute("pureWindows", EnumMapUtils.enumToMap(Windows.values(), false));
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        model.addAttribute("total", total);
        model.addAttribute("status", DbStatus.STATUS);
        return "pcsuite/apple/device/connect/driver/info/list.ftl";
    }

    @RequestMapping(value = "/apple/device/connect/driver/info/modify")
    @ResponseBody
    public String modifyPcSuiteAppleDeviceConnectDriverInfo(int id, Integer defaultFlag, Integer status) {
        String errMsg = "";
        PcSuiteAppleDeviceConnectDriverInfoParamater para = new PcSuiteAppleDeviceConnectDriverInfoParamater();
        para.setId(id);
        para.setDefaultFlag(defaultFlag);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.modifyPcSuiteAppleDeviceConnectDriverInfo(para);
        if (resp == null) {
            errMsg = "未知网络异常错误。";
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp.getMessage();
        }
        return errMsg;
    }

    @RequestMapping(value = "/apple/device/connect/driver/info/add")
    public String addPcSuiteAppleDeviceConnectDriverInfo(String system, String arch, String pcSuiteVersion,
            Integer defaultFlag, Integer driverId, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(system) || StringUtils.isEmpty(arch) || driverId == null || driverId.intValue() <= 0) {
            errMsg = "未知的参数请求";
        }
        if (StringUtils.isEmpty(errMsg)) {
            PcSuiteAppleDeviceConnectDriverInfoParamater para = new PcSuiteAppleDeviceConnectDriverInfoParamater();
            Windows window = Windows.instance(system);
            para.setArch(arch);
            para.setDefaultFlag(defaultFlag);
            para.setDriverId(driverId);
            para.setSystem(window.getVersion());
            para.setSystemDesc(window.getDesc());
            ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.addPcSuiteAppleDeviceConnectDriverInfo(para);
            if (resp == null) {
                errMsg = "未知网络异常错误。";
            } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/pcsuite/apple/device/connect/driver/info/list";
    }

    // authorizer driver
    @RequestMapping(value = "/apple/device/authorizer/driver/info/list")
    public String listPcSuiteAppleDeviceAuthorizerDriverInfo(@ModelAttribute("errMsg") String errMsg,
            PcSuiteAppleDeviceConnectDriverInfoParamater para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceAuthorizerDriverInfo>> resp = backendPcSuiteApiService
                .listPcSuiteAppleDeviceAuthorizerDriverInfo(para);
        List<PcSuiteAppleDeviceAuthorizerDriverInfo> datas = null;
        long total = 0;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += "网络异常错误。";
            }
        } else if (!StringUtils.isEmpty(resp.getMessage()) && !StringUtils.equalsIgnoreCase("ok", resp.getMessage())) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += resp.getMessage();
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
            para.getPager().setTotal(total);
        }
        PcSuiteAppleDeviceDriverInfoParameter driverPara = new PcSuiteAppleDeviceDriverInfoParameter();
        driverPara.setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>> driverResp = this.backendPcSuiteApiService
                .listPcSuiteAppleDeviceDriverInfo(driverPara);
        if (driverResp != null && driverResp.getData() != null
                && CollectionUtils.notEmptyAndNull(driverResp.getData().getResultList())) {
            model.addAttribute("drivers", driverResp.getData().getResultList());
        }
        ApiRespWrapper<List<String>> versionResp = backendPcSuiteApiService.listPcSuiteVersionVersion();
        if (versionResp != null && versionResp.getData() != null) {
            model.addAttribute("pcSuiteVersions", versionResp.getData());
        }
        model.addAttribute("defaultFlags", EnumMapUtils.enumToMap(DefaultFlag.values(), false));
        model.addAttribute("arches", EnumMapUtils.enumToMap(CpuArch.values(), true));
        model.addAttribute("windows", EnumMapUtils.enumToMap(Windows.values(), true));
        model.addAttribute("pureDefaultFlags", EnumMapUtils.enumToMap(DefaultFlag.values(), false));
        model.addAttribute("pureArches", EnumMapUtils.enumToMap(CpuArch.values(), false));
        model.addAttribute("pureWindows", EnumMapUtils.enumToMap(Windows.values(), false));
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        model.addAttribute("total", total);
        model.addAttribute("status", DbStatus.STATUS);
        return "pcsuite/apple/device/authorizer/driver/info/list.ftl";
    }

    @RequestMapping(value = "/apple/device/authorizer/driver/info/modify")
    @ResponseBody
    public String modifyPcSuiteAppleDeviceAuthorizerDriverInfo(int id, Integer defaultFlag, Integer status) {
        String errMsg = "";
        PcSuiteAppleDeviceConnectDriverInfoParamater para = new PcSuiteAppleDeviceConnectDriverInfoParamater();
        para.setId(id);
        para.setDefaultFlag(defaultFlag);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.modifyPcSuiteAppleDeviceAuthorizerDriverInfo(para);
        if (resp == null) {
            errMsg = "未知网络异常错误。";
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp.getMessage();
        }
        return errMsg;
    }

    @RequestMapping(value = "/apple/device/authorizer/driver/info/add")
    public String addPcSuiteAppleDeviceAuthorizerDriverInfo(String system, String arch, Integer defaultFlag,
            Integer driverId, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(system) || StringUtils.isEmpty(arch) || driverId == null || driverId.intValue() <= 0) {
            errMsg = "未知的参数请求";
        }
        if (StringUtils.isEmpty(errMsg)) {
            PcSuiteAppleDeviceConnectDriverInfoParamater para = new PcSuiteAppleDeviceConnectDriverInfoParamater();
            Windows window = Windows.instance(system);
            para.setArch(arch);
            para.setDefaultFlag(defaultFlag);
            para.setDriverId(driverId);
            para.setSystem(window.getVersion());
            para.setSystemDesc(window.getDesc());
            ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.addPcSuiteAppleDeviceAuthorizerDriverInfo(para);
            if (resp == null) {
                errMsg = "未知网络异常错误。";
            } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/pcsuite/apple/device/authorizer/driver/info/list";
    }

    // full driver
    @RequestMapping(value = "/apple/device/full/driver/info/list")
    public String listPcSuiteAppleDeviceFullDriverInfo(@ModelAttribute("errMsg") String errMsg,
            PcSuiteAppleDeviceConnectDriverInfoParamater para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceFullDriverInfo>> resp = backendPcSuiteApiService
                .listPcSuiteAppleDeviceFullDriverInfo(para);
        List<PcSuiteAppleDeviceFullDriverInfo> datas = null;
        long total = 0;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += "网络异常错误。";
            }
        } else if (!StringUtils.isEmpty(resp.getMessage()) && !StringUtils.equalsIgnoreCase("ok", resp.getMessage())) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += resp.getMessage();
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            total = resp.getData().getTotalCount();
            para.getPager().setTotal(total);
        }
        PcSuiteAppleDeviceDriverInfoParameter driverPara = new PcSuiteAppleDeviceDriverInfoParameter();
        driverPara.setSize(Integer.MAX_VALUE);
        ApiRespWrapper<ListWrapResp<PcSuiteAppleDeviceDriverInfo>> driverResp = this.backendPcSuiteApiService
                .listPcSuiteAppleDeviceDriverInfo(driverPara);
        if (driverResp != null && driverResp.getData() != null
                && CollectionUtils.notEmptyAndNull(driverResp.getData().getResultList())) {
            model.addAttribute("drivers", driverResp.getData().getResultList());
        }
        ApiRespWrapper<List<String>> versionResp = backendPcSuiteApiService.listPcSuiteVersionVersion();
        if (versionResp != null && versionResp.getData() != null) {
            model.addAttribute("pcSuiteVersions", versionResp.getData());
        }
        model.addAttribute("defaultFlags", EnumMapUtils.enumToMap(DefaultFlag.values(), true));
        model.addAttribute("arches", EnumMapUtils.enumToMap(CpuArch.values(), true));
        model.addAttribute("windows", EnumMapUtils.enumToMap(Windows.values(), true));
        model.addAttribute("pureDefaultFlags", EnumMapUtils.enumToMap(DefaultFlag.values(), false));
        model.addAttribute("pureArches", EnumMapUtils.enumToMap(CpuArch.values(), false));
        model.addAttribute("pureWindows", EnumMapUtils.enumToMap(Windows.values(), false));
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        model.addAttribute("total", total);
        model.addAttribute("status", DbStatus.STATUS);
        return "pcsuite/apple/device/full/driver/info/list.ftl";
    }

    @RequestMapping(value = "/apple/device/full/driver/info/modify")
    @ResponseBody
    public String modifyPcSuiteAppleDeviceFullDriverInfo(int id, Integer defaultFlag, Integer status) {
        String errMsg = "";
        PcSuiteAppleDeviceConnectDriverInfoParamater para = new PcSuiteAppleDeviceConnectDriverInfoParamater();
        para.setId(id);
        para.setDefaultFlag(defaultFlag);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.modifyPcSuiteAppleDeviceFullDriverInfo(para);
        if (resp == null) {
            errMsg = "未知网络异常错误。";
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp.getMessage();
        }
        return errMsg;
    }

    @RequestMapping(value = "/apple/device/full/driver/info/add")
    public String addPcSuiteAppleDeviceFullDriverInfo(String system, String arch, String pcSuiteVersion,
            Integer defaultFlag, Integer driverId, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(system) || StringUtils.isEmpty(arch) || driverId == null || driverId.intValue() <= 0) {
            errMsg = "未知的参数请求";
        }
        if (StringUtils.isEmpty(errMsg)) {
            PcSuiteAppleDeviceConnectDriverInfoParamater para = new PcSuiteAppleDeviceConnectDriverInfoParamater();
            Windows window = Windows.instance(system);
            para.setArch(arch);
            para.setDefaultFlag(defaultFlag);
            para.setDriverId(driverId);
            para.setSystem(window.getVersion());
            para.setSystemDesc(window.getDesc());
            ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.addPcSuiteAppleDeviceFullDriverInfo(para);
            if (resp == null) {
                errMsg = "未知网络异常错误。";
            } else if (resp.getData() == null || !resp.getData().booleanValue()) {
                errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知远程服务器错误。错误码:" + resp.getStatus() : resp
                        .getMessage();
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/pcsuite/apple/device/full/driver/info/list";
    }

    // old driver
    @RequestMapping(value = "/deprecated/driver/list")
    public String listPcsuiteDriver(@ModelAttribute("errMsg") String errMsg, @ModelAttribute("system") String system,
            PcSuiteItunesDriverParameter para, Model model) {
        if (!StringUtils.isEmpty(system)) {
            para.setSystem(system);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteItunesDriver>> resp = backendApiService.listPcSuiteItunesDriver(para);
        List<PcSuiteItunesDriver> datas = null;
        if (resp == null) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += "网络异常错误。";
            }
        } else if (!StringUtils.isEmpty(resp.getMessage()) && !StringUtils.equalsIgnoreCase("ok", resp.getMessage())) {
            if (StringUtils.isEmpty(errMsg)) {
                errMsg += resp.getMessage();
            }
        } else if (resp.getData() != null) {
            datas = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        }
        ApiRespWrapper<List<String>> versionResp = backendPcSuiteApiService.listPcSuiteVersionVersion();
        if (versionResp != null && versionResp.getData() != null) {
            model.addAttribute("versions", versionResp.getData());
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("windows", Windows.values());
        model.addAttribute("archs", CpuArch.values());
        model.addAttribute("datas", datas);
        model.addAttribute("para", para);
        return "pcsuite/deprecated/driver/list.ftl";
    }


    @RequestMapping(value = "/deprecated/driver/add")
    public String addPcsuiteDriver(PcSuiteItunesDriverParameter para, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(errMsg) && (StringUtils.isEmpty(para.getMd5()) || StringUtils.isEmpty(para.getUrl()))) {
            errMsg = "未知驱动文件";
        }
        if (StringUtils.isEmpty(errMsg)) {
            para.setDesc(Windows.instance(para.getSystem()).getDesc());
            ApiRespWrapper<Boolean> resp = this.backendApiService.addPcSuiteItunesDriver(para);
            if (resp == null) {
                errMsg = "网络异常错误。";
            } else {
                if (resp.getData() == null || !resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/pcsuite/deprecated/driver/list";
    }

    @RequestMapping(value = "/deprecated/driver/modify.json")
    @ResponseBody
    public String modifyDriverStatus(int id, int status) {
        if (id <= 0) {
            return "无效的Id.";
        }
        if (status != DbStatus.STATUS_DEL && status != DbStatus.STATUS_OK) {
            return "未知的操作状态.";
        }
        PcSuiteItunesDriverParameter pcSuiteItunesDriver = new PcSuiteItunesDriverParameter();
        pcSuiteItunesDriver.setId(id);
        pcSuiteItunesDriver.setStatus(status);
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyPcSuiteItunesDriverStatus(pcSuiteItunesDriver);
        if (resp == null) {
            return "网络异常错误。";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/deprecated/driver/detail")
    public String detailPcsuiteDriver(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("driverId") String driverId, @RequestParam(value = "id", required = false) Integer id,
            Model model) {
        PcSuiteItunesDriverInstallInfoParameter para = new PcSuiteItunesDriverInstallInfoParameter();
        if (!StringUtils.isEmpty(driverId)) {
            para.setDriverId(Integer.parseInt(driverId));
        } else {
            para.setDriverId(id);
        }
        ApiRespWrapper<PcSuiteItunesDriver> driverResp = backendApiService
                .detailPcSuiteItunesDriver(para.getDriverId());
        if (driverResp != null && driverResp.getData() != null) {
            model.addAttribute("driver", driverResp.getData());
        }
        ApiRespWrapper<ListWrapResp<PcSuiteItunesDriverInstallInfo>> installInfoResp = backendApiService
                .listPcSuiteItunesDriverInstallInfo(para);
        if (installInfoResp != null && installInfoResp.getData() != null) {
            model.addAttribute("installInfos", installInfoResp.getData().getResultList());
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("para", para);
        return "pcsuite/deprecated/driver/detail.ftl";
    }

    @RequestMapping(value = "/deprecated/driver/installinfo/add")
    public String addPcsuiteInstallInfo(PcSuiteItunesDriverInstallInfoParameter para, RedirectAttributes rattrs) {
        String errMsg = "";
        ApiRespWrapper<Boolean> installInfoResp = backendApiService.addPcSuiteItunesInstallInfo(para);
        if (installInfoResp == null) {
            errMsg = "未知网络错误.";
        } else if (installInfoResp.getData() != null && !installInfoResp.getData().booleanValue()) {
            errMsg = installInfoResp.getMessage();
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("driverId", para.getDriverId().toString());
        return "redirect:/auth/pcsuite/deprecated/driver/detail";
    }

    @RequestMapping(value = "/deprecated/driver/installinfo/modify.json")
    @ResponseBody
    public String modifyDriverInstallStatus(int id, Integer status, Integer index) {
        if (id <= 0) {
            return "无效的Id.";
        }
        PcSuiteItunesDriverInstallInfoParameter para = new PcSuiteItunesDriverInstallInfoParameter();
        para.setId(id);
        para.setStatus(status);
        if (index != null) {
            para.setIndexes(new Integer[] { index });
        }
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyPcSuiteItunesDriverInstallInfoStatus(para);
        if (resp == null) {
            return "网络异常错误。";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }



    // audit switch ip conf
    @RequestMapping(value = "/upgrade/switch/ip/conf/list")
    protected String listPcSuiteUpgradeSwitchIpConf(@ModelAttribute("addMsg") String addMsg,
            PcSuiteUpgradeSwitchIpConfParamater para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteUpgradeSwitchIpConf>> resp = backendPcSuiteApiService
                .listPcSuiteUpgradeSwitchIpConf(para);
        ApiRespWrapperUtils.handleListResp(resp, para, model);
        if (!StringUtils.isEmpty(addMsg)) {
            model.addAttribute("addMsg", addMsg);
        }
        return "pcsuite/upgrade/switch/ip/conf/list.ftl";
    }

    @RequestMapping(value = "/upgrade/switch/ip/conf/add")
    protected String addPcSuiteUpgradeSwitchIpConf(String ip, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(ip)) {
            errMsg = "错误的参数请求";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                PcSuiteUpgradeSwitchIpConfParamater g = new PcSuiteUpgradeSwitchIpConfParamater();
                g.setIp(ip);
                ApiRespWrapper<Boolean> resp = backendPcSuiteApiService.addPcSuiteUpgradeSwitchIpConf(g);
                errMsg = ApiRespWrapperUtils.handleBooleanResp(resp);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        rattrs.addFlashAttribute("addMsg", errMsg);
        return "redirect:/auth/pcsuite/upgrade/switch/ip/conf/list";
    }

    @RequestMapping(value = "/upgrade/switch/ip/conf/modify.json")
    @ResponseBody
    protected ApiRespWrapper<Boolean> modifyPcSuiteUpgradeSwitchIpConf(int id, int status) {
        if (id <= 0) {
            return new ApiRespWrapper<Boolean>(-1, "错误的参数请求---id", false);
        }
        PcSuiteUpgradeSwitchIpConfParamater g = new PcSuiteUpgradeSwitchIpConfParamater();
        g.setId(id);
        g.setStatus(status);
        return backendPcSuiteApiService.modifyPcSuiteUpgradeSwitchIpConf(g);
    }

    // pc lang
    @RequestMapping(value = "/lang/info/list")
    protected String listPcSuiteLangInfo(PcSuiteLangInfoParamater para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<PcSuiteLangInfo>> resp = backendPcSuiteApiService.listPcSuiteLangInfo(para);
        ApiRespWrapperUtils.handleListResp(resp, para, model);
        model.addAttribute("appLanguages", EnumMapUtils.enumToMap(AppLanguage.values()));
        return "pcsuite/lang/info/list.ftl";
    }

    @RequestMapping(value = "/lang/info/modify.json")
    @ResponseBody
    protected ApiRespWrapper<Boolean> modifyPcSuiteLangInfo(int id, String language) {
        if (StringUtils.isEmpty(language)) {
            return new ApiRespWrapper<Boolean>(-1, "错误的参数请求---language", false);
        }
        PcSuiteLangInfoParamater g = new PcSuiteLangInfoParamater();
        g.setId(id);
        g.setLanguage(language);
        return backendPcSuiteApiService.modifyPcSuiteLangInfo(g);
    }

}
