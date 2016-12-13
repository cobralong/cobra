// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.appchina.ios.core.dto.info.FunnyClientSpecialColumn;
import com.appchina.ios.mgnt.controller.model.UploadFileResp;
import com.appchina.ios.mgnt.controller.model.UploadImgResp;
import com.appchina.ios.mgnt.controller.model.info.AppStoreFunnyColumnDetailParameter;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface BannerStorageService {
    public UploadFileResp savePcSuiteItunesDll(CommonsMultipartFile dll);

    public UploadFileResp savePcSuiteItunesDriver(CommonsMultipartFile driver);

    public UploadFileResp savePcSuiteAppleDeviceDriver(CommonsMultipartFile driver);

    public UploadImgResp savePcSuiteIphoneModelImg(CommonsMultipartFile dll);

    public UploadFileResp savePcSuiteIosProgrammerDriver(CommonsMultipartFile dll);

    public String saveBanner(int rootId, CommonsMultipartFile banner);

    public String saveStoreVideoCateIcon(int cateId, CommonsMultipartFile icon);

    public String saveStoreAppCateIcon(int cateId, CommonsMultipartFile icon);

    public String saveStoreDailyRecomBanner(int recomId, CommonsMultipartFile img);

    public String saveStoreArticle(String title, String html);

    public UploadFileResp saveStoreIcon(CommonsMultipartFile icon);

    public UploadFileResp saveStoreContentImg(CommonsMultipartFile imageFile);

    public String saveStoreBanner(CommonsMultipartFile banner);

    public String saveStoreAdImg(CommonsMultipartFile img);

    public String saveStoreVideoScreenImg(int id, CommonsMultipartFile screen);

    public String saveStoreTabIcon(CommonsMultipartFile icon);

    public UploadFileResp saveDynamicCode(int id, CommonsMultipartFile codes);

    public UploadFileResp saveH5GameIcon(CommonsMultipartFile icon);

    public UploadFileResp saveShareAccountIcon(CommonsMultipartFile icon);

    public String saveTestP12(CommonsMultipartFile testCertFile);

    public String saveProductP12(CommonsMultipartFile productCertFile);

    public UploadFileResp saveStoreWallpaperImg(CommonsMultipartFile img);

    public UploadFileResp saveStoreWallpaperImg(File img);

    public UploadFileResp saveColumnContentImg(CommonsMultipartFile img);

    public UploadFileResp saveColumnContentImg(File img);

    public String saveStoreFunnyColumnDetail(String authorName, String authorImage,
            AppStoreFunnyColumnDetailParameter para, List<FunnyClientSpecialColumn> gg, boolean haveApplication,
            Map<String, String> ctypesall);

    public String saveFunnyClientAdImg(CommonsMultipartFile img);

    public String saveFunnyClientColumnListBackImg(CommonsMultipartFile img);

    public String saveFunnyClientColumnAuthorImg(CommonsMultipartFile img);

    public String previewHtml(AppStoreFunnyColumnDetailParameter para, String authorName, String authorImage,
            List<FunnyClientSpecialColumn> gg, Map<String, String> ctypesall);

}
