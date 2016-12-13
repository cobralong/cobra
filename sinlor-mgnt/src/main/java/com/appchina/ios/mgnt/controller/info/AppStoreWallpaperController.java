package com.appchina.ios.mgnt.controller.info;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.appchina.ios.core.dto.info.AppStoreWallpaper;
import com.appchina.ios.core.dto.info.AppStoreWallpaperChosen;
import com.appchina.ios.core.utils.EnumMapUtils;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.UploadFileResp;
import com.appchina.ios.mgnt.controller.model.info.AppStoreWallpaperParameter;
import com.appchina.ios.mgnt.controller.model.info.StoreLoadPageWallpaperParameter;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.UploadFileUtils;

@Controller
@RequestMapping(value = "/auth/store/wallpaper/*")
public class AppStoreWallpaperController {
    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private IosStoreApiService iosStoreApiService;
    private Logger log = Logger.getLogger(AppStoreWallpaperController.class);

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(String errMsg, StoreLoadPageWallpaperParameter wallpaper, Model model) {
        model.addAttribute("wallpaper", wallpaper);
        return "/store/wallpaper/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(StoreLoadPageWallpaperParameter wallpaper,
            @RequestParam(required = false) CommonsMultipartFile img, Model model) {
        String errMsg = checkAndFormatAddParameter(wallpaper.getTitle(), img);
        if (StringUtils.isEmpty(errMsg)) {
            UploadFileResp ufr = bannerStorageService.saveStoreWallpaperImg(img);
            wallpaper.setUrl(ufr.getUrl());
            wallpaper.setResizeUrl(ufr.getResizeUrl());
            try {
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addLoadPageWallpaper(wallpaper);
                if (resp.getData()) {
                    errMsg = resp.getMessage();
                } else {
                    throw ServiceException.getInternalException(resp.getMessage());
                }
            } catch (Exception e) {
                log.warn("保存数据时出现错误.e=" + e.getMessage());
            }
        }
        model.addAttribute("wallpaper", wallpaper);
        model.addAttribute("errMsg", errMsg);
        return "/store/wallpaper/add.ftl";
    }

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    public String add(String filePath, Model model) {
        String errMsg = checkFilePath(filePath);
        if (StringUtils.isEmpty(errMsg)) {
            File dir = new File(filePath);
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (!isImgStyle(files[i])) {
                    continue;
                }
                StoreLoadPageWallpaperParameter paper = new StoreLoadPageWallpaperParameter();
                UploadFileResp ufr = bannerStorageService.saveStoreWallpaperImg(files[i]);
                paper.setUrl(ufr.getUrl());
                paper.setResizeUrl(ufr.getResizeUrl());
                paper.setTitle(files[i].getName().substring(0, files[i].getName().lastIndexOf(".")));
                try {
                    ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addLoadPageWallpaper(paper);
                    if (resp.getData()) {
                        errMsg = resp.getMessage();
                        continue;
                    } else {
                        throw ServiceException.getInternalException(resp.getMessage());
                    }
                } catch (Exception e) {
                    log.warn("保存数据时出现错误.fileName=" + files[i].getName() + ";e=" + e.getMessage());
                }
            }
        }
        model.addAttribute("errMsg", errMsg);
        return "/store/wallpaper/add.ftl";
    }

    private boolean isImgStyle(File file) {
        String imgTypeStr = UploadFileUtils.getSuffix(file.getName());
        if (imgTypeStr.equalsIgnoreCase("jpg") || imgTypeStr.equalsIgnoreCase("gif")
                || imgTypeStr.equalsIgnoreCase("jpeg") || imgTypeStr.equalsIgnoreCase("png")
                || imgTypeStr.equalsIgnoreCase("swf")) {
            return true;
        } else {
            return false;
        }
    }

    private String checkFilePath(String filePath) {
        String errMsg = "";
        if (StringUtils.isEmpty(filePath)) {
            errMsg = "壁纸目录必填！";
        } else {
            File dir = new File(filePath);
            File[] files = dir.listFiles();
            if (files == null) {
                errMsg = "该目录不存在！";
            } else if (files.length == 0) {
                errMsg = "该目录下没有壁纸！";
            }
        }
        return errMsg;
    }

    private String checkAndFormatAddParameter(String title, CommonsMultipartFile img) {
        String errMsg = "";
        if (img == null || img.getSize() == 0) {
            errMsg = "请提供墙纸图";
        }
        return errMsg;
    }

    @RequestMapping("/list")
    public String list(AppStoreWallpaperParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(AppStoreWallpaper.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<AppStoreWallpaper>> resp = iosStoreApiService.listAppStoreWallpaper(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppStoreWallpaper> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        model.addAttribute("chosens", EnumMapUtils.enumToMap(AppStoreWallpaperChosen.values()));
        return "store/wallpaper/list.ftl";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(int id, Integer status, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        AppStoreWallpaperParameter param = new AppStoreWallpaperParameter();
        param.setId(id);
        param.setStatus(status);
        resp = this.iosStoreApiService.modifyAppStoreWallpaper(param);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/chosen/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modifyChosen(int id, Integer chosen, Model model) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        AppStoreWallpaperParameter param = new AppStoreWallpaperParameter();
        param.setId(id);
        param.setChosen(chosen);
        resp = this.iosStoreApiService.modifyAppStoreWallpaper(param);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }
}
