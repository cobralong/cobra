// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.appchina.ios.core.crawler.model.ImgRes;
import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetail;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumn;
import com.appchina.ios.core.service.LocalResService;
import com.appchina.ios.core.utils.AppStoreFunnyColumnDetailUtils;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.UploadFileResp;
import com.appchina.ios.mgnt.controller.model.UploadImgResp;
import com.appchina.ios.mgnt.controller.model.info.AppStoreApplicationWrapper;
import com.appchina.ios.mgnt.controller.model.info.AppStoreFunnyColumnDetailParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosFunnyApiService;
import com.appchina.ios.mgnt.utils.ImageUtil;
import com.appchina.ios.mgnt.utils.ImageUtil.ImageType;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.IOUtils;
import com.appchina.ios.web.utils.UploadFileUtils;
import com.appchina.ios.web.utils.UrlUtils;

/**
 * @author luofei@refanqie.com (Your Name Here)
 */
@Service("bannerStorageService")
public class BannerStorageServiceImpl implements BannerStorageService {
    private static final Logger log = Logger.getLogger(BannerStorageServiceImpl.class);
    private static final String HTML_HEAD1 = "<!DOCTYPE html><html xmlns=\"http://www.w3.org/1999/xhtml\">"
            + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>"
            + "<meta name=\"applicable-device\" content=\"pc\">" + "<title>";
    private static final String HTML_HEAD2 = "</title><meta name=\"viewport\" content=\"width =device-width, initial-scale=1, maximum-scale=1\"/>"
            + "<link rel=\"icon\" type=\"image/ico\" href=\"http://ios.appchina.com/static/ios/images/favicon.ico\"/>"
            + "<link href=\"../column.css\" rel=\"stylesheet\" type=\"text/css\"/>"
            + "<link rel=\"Shortcut Icon\" type=\"image/ico\" href=\"http://ios.appchina.com/static/ios/images/favicon.ico\" />"
            + "<style type=\"text/css\">h1 {font-size: 5.9vw;}h2 {font-size: 3.0vh;}p {font-size: 2vmin;}img{width:auto;height:auto;max-width:100%;max-height:100%;}</style>"
            + "</head><body>";

    private static final String funnyColumnContentDetailCss = "http://img.huiyoobao.com/column/content/css";

    private static final String COLUMN_HTML_HEAD1 = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"/><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/><meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge，chrome=1\"/><meta name=\"applicable-device\" content=\"mobile\"/><meta http-equiv=\"Cache-Control\" content=\"no-siteapp\"/><meta name=\"format-detection\" content=\"telephone=no, email=no\"/><meta name=\"apple-mobile-web-app-capable\" content=\"yes\" /><meta name=\"apple-mobile-web-app-status-bar-style\" content=\"default\" /><meta name=\"google\" value=\"notranslate\" /><title>";
    private static final String COLUMN_HTML_HEAD2 = "</title><link rel=\"stylesheet\" type=\"text/css\" href=\""
            + funnyColumnContentDetailCss
            + "/static/css/swiper.min.css\" /><link rel=\"stylesheet\" type=\"text/css\" href=\""
            + funnyColumnContentDetailCss + "/static/css/common.css\" /><script type=\"text/javascript\" src=\""
            + funnyColumnContentDetailCss
            + "/static/js/jquery-2.1.4.min.js\"></script><script type=\"text/javascript\" src=\""
            + funnyColumnContentDetailCss + "/static/js/swiper-3.3.1.jquery.min.js\"></script>";
    private static final String COLUMN_HTML_HEAD2_PREVIEW = "</title><link rel=\"stylesheet\" type=\"text/css\" href=\""
            + funnyColumnContentDetailCss
            + "/static/css/swiper.min.css\" /><link rel=\"stylesheet\" type=\"text/css\" href=\""
            + funnyColumnContentDetailCss
            + "/static/css/commonpreview.css\" /><script type=\"text/javascript\" src=\""
            + funnyColumnContentDetailCss
            + "/static/js/jquery-2.1.4.min.js\"></script><script type=\"text/javascript\" src=\""
            + funnyColumnContentDetailCss + "/static/js/swiper-3.3.1.jquery.min.js\"></script>";

    private static final String APPLICATIONSETS_HTML_HEAD1 = "<!DOCTYPE html><html><head><title>";
    private static final String APPLICATIONSETS_HTML_HEAD2 = "</title><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>"
            + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge，chrome=1\"/><meta name=\"applicable-device\" content=\"mobile\"/><meta http-equiv=\"Cache-Control\" content=\"no-siteapp\"/><meta name=\"format-detection\" content=\"telephone=no, email=no\"/>"
            + "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" /><meta name=\"apple-mobile-web-app-status-bar-style\" content=\"default\" /><meta name=\"google\" value=\"notranslate\" />"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\""
            + funnyColumnContentDetailCss
            + "/static/css/common-f.css\">";
    private static final String APPLICATIONSETS_HTML_JQUERY = "<script type=\"text/javascript\" src=\""
            + funnyColumnContentDetailCss + "/static/js/jquery-2.1.4.min.js\"></script>";
    private static final String APPLICATIONSETS_HTML_HEAD2_PREVIEW = "</title><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>"
            + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge，chrome=1\"/><meta name=\"applicable-device\" content=\"mobile\"/><meta http-equiv=\"Cache-Control\" content=\"no-siteapp\"/><meta name=\"format-detection\" content=\"telephone=no, email=no\"/>"
            + "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" /><meta name=\"apple-mobile-web-app-status-bar-style\" content=\"default\" /><meta name=\"google\" value=\"notranslate\" />"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\""
            + funnyColumnContentDetailCss
            + "/static/css/common-f-preview.css\">";
    private static final String APPLICATIONSETS_HTML_HEAD_END = "</head><body>";
    // private static final String
    // COLUMN_HTML_HEAD_SCRIPT_APPLICATIONAPPSTOREURL =
    // "<script type=\"text/javascript\">function showApplication(appstoreUrl){window.location.href=appstoreUrl;}</script>";

    private static final String COLUMN_HTML_HEAD_SCRIPT_APPLICATIONAPPSTOREURL = "<script type=\"text/javascript\">function showApplication(appstoreUrl,rootId,e) {e.preventDefault();"
            + "$.ajax({url:\"http://iosapi.huiyoobao.com/funny/system/keywordsearchurl.json\",data:{\"rootId\":rootId},type:'post',async:false,dataType:'jsonp',jsonp:'callback',success:function(resp){"
            + "if(resp.success && typeof (resp.data)!=='undefined' && resp.data != '' &&resp.data !=null){ window.location.href=resp.data;}else{ window.location.href=appstoreUrl;}},"
            + "error:function(errorThrown){window.location.href=appstoreUrl;}});}</script>";

    // private static final String
    // COLUMN_HTML_HEAD_SCRIPT_APPLICATIONAPPSTOREURL =
    // "<script type=\"text/javascript\">var appUrl;function jsonpCallback(result) {if(result[0].url!=''){appUrl=result[0].url;}window.location.href=appUrl;}"
    // +
    // "function showApplication(appstoreUrl,rootId,e) {e.preventDefault();appUrl=appstoreUrl;var oldScript = document.getElementById(appstoreUrl);if (oldScript) {oldScript.setAttribute(\"src\", appstoreUrl);return;}"
    // +
    // "var newScript = document.createElement(\"script\");newScript.setAttribute(\"type\", \"text/javascript\");newScript.setAttribute(\"src\", \"http://iosapi.huiyoobao.com/funny/system/keywordsearchurl.json?callback=jsonpCallback&rootId=\"+rootId); "
    // +
    // "newScript.setAttribute(\"id\", appstoreUrl);document.head.appendChild(newScript);}</script>";

    private static final String COLUMN_HTML_HEAD_SCRIPT_OC = "<script type=\"text/javascript\">function setupWebViewJavascriptBridge(callback) {if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }"
            + "window.WVJBCallbacks = [callback];var WVJBIframe = document.createElement('iframe');WVJBIframe.style.display = 'none';WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';document.documentElement.appendChild(WVJBIframe);setTimeout(function() { document.documentElement.removeChild(WVJBIframe)}, 0)}</script>";

    private static final String COLUMN_HTML_HEAD_COLUMN_REFERENCE_OC = "<script type=\"text/javascript\">function showColumnReference(viewUrl,columnId,e){e.preventDefault();setupWebViewJavascriptBridge(function(bridge) {bridge.callHandler('getColumnData', {'viewUrl':viewUrl,'columnId':columnId}, function responseCallback(responseData) {console.log(\"JS received response:\", responseData)})})}</script>";

    private static final String COLUMN_HTML_HEAD_COLUMN_IMAGE_OC = "<script type=\"text/javascript\">function showImageLarge(srcUrl,e){e.preventDefault();setupWebViewJavascriptBridge(function(bridge) {bridge.callHandler('getImageSource', {'srcUrl':srcUrl}, function responseCallback(responseData) {console.log(\"JS received response:\", responseData)})})}</script>";

    // private static final String COLUMN_HTML_BODY_OC =
    // "<script type=\"text/javascript\">$(function(){$(\"body\").click(function(){setupWebViewJavascriptBridge(function(bridge) {bridge.callHandler('onBackgroundTapped', {}, function responseCallback(responseData) {"
    // +
    // "console.log(\"JS received response:\", responseData)})})});});</script>";

    private static final String COLUMN_HTML_BODY_OC = "<script type=\"text/javascript\">function showFunnyTitle(){setupWebViewJavascriptBridge(function(bridge) {bridge.callHandler('onBackgroundTapped', {}, function responseCallback(responseData) {console.log(\"JS received response:\", responseData)})})}</script>";

    private static final String COLUMN_HTML_HEAD_END = "</head><body>";
    private static final String COLUMN_HTML_TAIL = "<div class=\"art-share\"><img src=\"" + funnyColumnContentDetailCss
            + "/static/images/art-share.png\" alt=\"\" /></div>"
            + "<script language=\"javascript\">var mySwiper = new Swiper ('.swiper-container', {"
            + "slidesPerView: 1.65,paginationClickable: true,spaceBetween: 10,freeMode: true})</script>";
    private static final String HTML_TAIL = "</body></html>";
    private Random random = new Random();

    // 企业版资源存储路径
    @Value("${ios.mgnt.iosimg.url.prefix}")
    private String iosimgUrlPrefix = "http://iosimg.yingyonghui.com";
    // 上架版资源存储路径
    @Value("${ios.mgnt.store.iosimg.url.prefix}")
    private String iosStoreImgUrlPrefix = "http://iosimg.yingyonghui.com/store";
    // funny资源存储路径
    @Value("${ios.mgnt.funny.iosimg.url.prefix}")
    private String iosFunnyImgUrlPrefix = "http://iosimg.huiyoobao.com/store";
    // pcsuite
    @Value("${ios.mgnt.pcsuite.url.prefix}")
    private String pcsuiteUrlPrefix = "http://iosimg.yingyonghui.com/pcsuite";
    // html
    @Value("${ios.mgnt.store.ioshtml.url.prefix}")
    private String iosHtmlUrlPrefix = "http://iosimg.yingyonghui.com/store";
    // funnyhtml
    @Value("${ios.mgnt.funny.ioshtml.url.prefix}")
    private String iosfunnyHtmlUrlPrefix = "http://img.huiyoobao.com/";

    /*********** 企业版版资源 *********/

    // 企业版banner
    @Value("${ios.mgnt.iosimg.bannerpath.prefix}")
    private String bannerImgPath = "/srv/images/banner";
    @Value("${ios.mgnt.iosimg.bannerurl.prefix}")
    private String bannerUrlPrefix = "banner";

    // ***宝箱
    @Value("${ios.mgnt.iosimg.shareaccountcionpath.prefix}")
    private String shareAccountIconPath = "/srv/images/icon/shareaccount";
    @Value("${ios.mgnt.iosimg.shareaccountcionurl.prefix}")
    private String shareAccountIconUrlPrefix = "icon/shareaccount";



    /*********** 上架版版资源 *********/

    // article icon
    @Value("${ios.mgnt.store.iosimg.articleicon.path}")
    private String iconImgPath = "/srv/images/store/article/icon";
    @Value("${ios.mgnt.store.iosimg.articleiconurl.prefix}")
    private String iconUrlPrefix = "article/icon";

    // image in article
    @Value("${ios.mgnt.store.iosimg.articleimg.path}")
    private String articleImgPath = "/srv/images/store/article/img";
    @Value("${ios.mgnt.store.iosimg.articleimgurl.prefix}")
    private String articleImgUrlPrefix = "article/img";

    @Value("${ios.mgnt.store.html.article.path}")
    private String htmlPath = "/srv/images/store/article/html";
    @Value("${ios.mgnt.store.ioshtml.articleurl.prefix}")
    private String htmlUrlPrefix = "article/html";

    // funny column
    @Value("${ios.mgnt.store.html.cloumn.path}")
    private String htmlColumnPath = "/srv/images/store/column/html";
    @Value("${ios.mgnt.store.ioshtml.columnurl.prefix}")
    private String htmlColumnUrlPrefix = "column/html";

    // load funny column content image
    @Value("${ios.mgnt.store.iosimage.loadpagecolumncontenturl.prefix}")
    private String appStoreColumnContentImagePrefix = "column/contentimage";
    @Value("${ios.mgnt.store.iosimg.loadpagecolumncontentimage.path}")
    private String appStoreColumnContentImageFileDir = "/srv/images/store/column/contentimage";

    // banner
    @Value("${ios.mgnt.store.iosimg.bannerurl.prefix}")
    private String storeBannerUrlPrefix = "banner";
    @Value("${ios.mgnt.store.iosimg.banner.path}")
    private String storeBannerImgPath = "/srv/images/store/banner";

    // load page ad
    @Value("${ios.mgnt.store.iosimg.loadpageadurl.prefix}")
    private String loadPageAdUrlPrefix = "ad/loadpage";
    @Value("${ios.mgnt.store.iosimg.loadpageadimg.path}")
    private String loadPageAdImgPath = "/srv/images/store/ad/loadpage";

    // funny client ad
    @Value("${ios.mgnt.funny.iosimg.loadpageadurl.prefix}")
    private String funnyClientAdUrlPrefix = "funny/loadpage";
    @Value("${ios.mgnt.funny.iosimg.loadpageadimg.path}")
    private String funnyClientAdImgPath = "/srv/images/store/funny/loadpage";

    // funny client column list backend img
    @Value("${ios.mgnt.funny.iosimg.columnbackurl.prefix}")
    private String funnyClientColumnBackUrlPrefix = "funny/columnback";
    @Value("${ios.mgnt.funny.iosimg.columnbackimg.path}")
    private String funnyClientColumnBackImgPath = "/srv/images/store/funny/columnback";

    // funny client column author icon
    @Value("${ios.mgnt.funny.iosimg.authoriconurl.prefix}")
    private String funnyClientAuthorIconUrlPrefix = "funny/authoricon";
    @Value("${ios.mgnt.funny.iosimg.authoriconimg.path}")
    private String funnyClientAuthorIconImgPath = "/srv/images/store/funny/authoricon";

    // load page wallpaper
    @Value("${ios.mgnt.store.iosimg.loadpagewallpaperurl.prefix}")
    private String loadPageWallpaperUrlPrefix = "wallpaper/loadpage";
    @Value("${ios.mgnt.store.iosimg.loadpagewallpaperimg.path}")
    private String loadPageWallpaperImgPath = "/srv/images/store/wallpaper/loadpage";

    // video screenshot
    @Value("${ios.mgnt.store.iosimg.videoscreenshoturl.prefix}")
    private String videoScreenShotUrlPrefix = "video";
    @Value("${ios.mgnt.store.iosimg.videoscreenshot.path}")
    private String videoScreenShotPath = "/srv/images/store/video";

    // tab icon
    @Value("${ios.mgnt.store.iosimg.tabicon.urlprefix}")
    private String tabIconUrlPrefix = "tab";
    @Value("${ios.mgnt.store.iosimg.tabicon.path}")
    private String tabIconPath = "/srv/images/store/tab";

    // 每日推荐banner
    @Value("${ios.mgnt.iosimg.dailyrecombannerpath.prefix}")
    private String dailyRecomBannerPath = "/srv/images/store/banner/dailryrecom";
    @Value("${ios.mgnt.iosimg.dailyrecombannerurl.prefix}")
    private String dailyRecomBannerUrlPrefix = "banner/dailryrecom";

    @Value("${ios.mgnt.iosimg.videocateiconpath.prefix}")
    private String videoCateIconPath = "/srv/images/store/icon/videocate";
    @Value("${ios.mgnt.iosimg.videocateiconurl.prefix}")
    private String videoCateIconUrlPrefix = "icon/videocate";

    @Value("${ios.mgnt.iosimg.appcateiconpath.prefix}")
    private String appCateIconPath = "/srv/images/store/icon/appcate";
    @Value("${ios.mgnt.iosimg.appcateiconurl.prefix}")
    private String appCateIconUrlPrefix = "icon/appcate";

    @Value("${ios.mgnt.iosimg.dynamiccodepath.prefix}")
    private String dynamicCodePath = "/srv/images/store/codes";
    @Value("${ios.mgnt.iosimg.dynamiccodeurl.prefix}")
    private String dynamicCodeUrlPrefix = "codes";

    @Value("${ios.mgnt.iosimg.h5gameicon.prefix}")
    private String h5GameIconPath = "/srv/images/store/h5game/icon";
    @Value("${ios.mgnt.iosimg.h5gameiconurl.prefix}")
    private String h5GameIconUrlPrefix = "h5game/icon";


    // **************pcsuite*******************

    // itunes dll
    @Value("${ios.mgnt.pcsuite.itunesdll.urlprefix}")
    private String pcSuiteItunesDllUrlPrefix = "itunes/dll";
    @Value("${ios.mgnt.pcsuite.itunesdll.path}")
    private String pcSuiteItunesDllPath = "/srv/images/pcsuite/itunes/dll";

    // itunes driver
    @Value("${ios.mgnt.pcsuite.itunesdriver.urlprefix}")
    private String pcSuiteItunesDriverUrlPrefix = "itunes/driver";
    @Value("${ios.mgnt.pcsuite.itunesdriver.path}")
    private String pcSuiteItunesDriverPath = "/srv/images/pcsuite/itunes/driver";

    // iOS programmer driver
    @Value("${ios.mgnt.pcsuite.iosprogrammerdriver.urlprefix}")
    private String pcSuiteIosProgrammerDriverUrlPrefix = "ios/programmer";
    @Value("${ios.mgnt.pcsuite.iosprogrammerdriver.path}")
    private String pcSuiteIosProgrammerDriverPath = "/srv/images/pcsuite/ios/programmer";

    // apple device driver
    @Value("${ios.mgnt.pcsuite.appledevicedriver.urlprefix}")
    private String pcSuiteAppleDeviceDriverUrlPrefix = "ios/apple/device/driver";
    @Value("${ios.mgnt.pcsuite.appledevicedriver.path}")
    private String pcSuiteAppleDeviceDriverPath = "/srv/images/pcsuite/apple/device/driver";

    // iphone model img
    @Value("${ios.mgnt.pcsuite.iphonemodelimg.urlprefix}")
    private String PcSuiteIphoneModelImgUrlPrefix = "iphone/model";
    @Value("${ios.mgnt.pcsuite.iphonemodelimg.path}")
    private String PcSuiteIphoneModelImgPath = "/srv/images/pcsuite/iphone/model";
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private IosFunnyApiService iosFunnyApiService;
    @Autowired
    private LocalResService localResService;

    @Override
    public String saveBanner(int rootId, CommonsMultipartFile banner) {
        String localPath = getBannerImgFileDir();
        String urlPath = getBannerImgUrlDir();
        return saveImg(rootId, localPath, urlPath, banner, null);
    }

    @Override
    public String saveStoreVideoCateIcon(int cateId, CommonsMultipartFile icon) {
        String localPath = getVideoCateIconImgFileDir();
        String urlPath = getVideoCateIconUrlDir();
        return saveImg(cateId, localPath, urlPath, icon, null);
    }

    @Override
    public String saveStoreAppCateIcon(int cateId, CommonsMultipartFile icon) {
        String localPath = getAppCateIconFileDir();
        String urlPath = getAppCateIconUrlDir();
        return saveImg(cateId, localPath, urlPath, icon, null);
    }

    @Override
    public String saveStoreDailyRecomBanner(int recomId, CommonsMultipartFile banner) {
        String localPath = getDailyRecomBannerImgFileDir();
        String urlPath = getDailyRecomBannerImgUrlDir();
        return saveImg(recomId, localPath, urlPath, banner, ImageUtil.ImageType.Banner);
    }

    @Override
    public String saveStoreArticle(String title, String html) {
        String fullHtml = HTML_HEAD1 + title + HTML_HEAD2 + html + HTML_TAIL;
        String localBaseDir = getArticleFileDir();
        String urlDir = getArticleUrlDir();
        File file = new File(getFilePath(localBaseDir), "article_" + System.currentTimeMillis() + ".html");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fullHtml);
            fileWriter.close();
        } catch (IOException e) {
            log.error("save article html file exception", e);
            throw ServiceException.getApplicationIllegalException(e.getMessage());
        }
        String subRelativePath = file.getPath().replace(localBaseDir, "");
        return UrlUtils.spliceUrl(urlDir, subRelativePath);
    }

    @Override
    public String saveStoreFunnyColumnDetail(String authorName, String authorImage,
            AppStoreFunnyColumnDetailParameter para, List<FunnyClientSpecialColumn> gg, boolean haveApplication,
            Map<String, String> ctypesall) {
        String fullHtml = "";
        if (ctypesall.get(para.getCtypeId()).equals(AppStoreFunnyColumnDetailUtils.FUNNY_APPLICATION_LIMIT)) {
            String content = dealWithFunnyColumnContentImg(para.getContent(), false, para);
            para.setContent(content);
            fullHtml = consoleApplicationLimit(content, para, authorName, authorImage);
        } else if (ctypesall.get(para.getCtypeId()).equals(AppStoreFunnyColumnDetailUtils.FUNNY_APPLICATION_SETS)) {
            String imgSrc = dealWithFunnyColumnContentImg(para.getContent(), true, para);
            fullHtml = consoleApplicationSets(para.getContent(), para, authorName, authorImage, imgSrc, false);
        } else {
            String content = dealWithFunnyColumnContentImg(para.getContent(), false, para);
            para.setContent(content);
            fullHtml = consoleColumnContent(content, haveApplication, para, authorName, authorImage, gg);
        }
        String localBaseDir = getColumnFileDir();
        String urlDir = getColumnUrlDir();
        String viewUrl = "";
        if (haveApplication) {
            viewUrl = para.getViewUrl();
        } else {
            viewUrl = para.getViewNoApplicationUrl();
        }
        String srcHtml = "column_" + System.currentTimeMillis() + ".html";
        String preHtml = "";
        if (!StringUtils.isEmpty(viewUrl)) {
            srcHtml = viewUrl.substring(viewUrl.lastIndexOf("/") + 1);
            String destFile = viewUrl.replace(getAppStoreColumnContentHtmlUrlDir(), htmlColumnPath);
            preHtml = destFile.substring(0, destFile.lastIndexOf("/"));
            FileUtils.deleteQuietly(new File(destFile));
        }
        File file = new File(getFilePath(localBaseDir), srcHtml);
        if (!StringUtils.isEmpty(viewUrl)) {
            file = new File(preHtml + File.separator, srcHtml);
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fullHtml);
            fileWriter.close();
        } catch (IOException e) {
            log.error("save article html file exception", e);
            throw ServiceException.getApplicationIllegalException(e.getMessage());
        }
        String subRelativePath = file.getPath().replace(localBaseDir, "");
        return UrlUtils.spliceUrl(urlDir, subRelativePath);
    }

    private String consoleApplicationSets(String content, AppStoreFunnyColumnDetailParameter para, String authorName,
            String authorImage, String imgSrc, boolean isPreview) {
        if (para.getCurrentType() == AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_DETAIL_EDIY_TYRE) {
            para.setViews(0);
        }
        int packageCount = dealWithFunnyColumnContentApplicationSets(content, para);
        String applicationSetsHtml = para.getAllApplicationSetsHtml();
        String titleHtml = "<div class=\"free-top\"><img src=\"" + imgSrc
                + "\" alt=\"\" /><div class=\"top-bg\"><p class=\"f18\">" + para.getTitle() + "</p><p><img src=\""
                + authorImage + "\" alt=\"\" /><p class=\"f14\">" + authorName + "<em>|</em>共收录" + packageCount
                + "个应用</p></div></div>";
        String nextHtml = "<div class=\"free-main\"><h1 class=\"free-js\">"
                + para.getApplicationDescription().replace("\n", "<br>") + "</h1>" + applicationSetsHtml + "</div>";
        String fullHtml = APPLICATIONSETS_HTML_HEAD1 + para.getTitle()
                + (isPreview ? APPLICATIONSETS_HTML_HEAD2_PREVIEW : APPLICATIONSETS_HTML_HEAD2)
                + APPLICATIONSETS_HTML_JQUERY + COLUMN_HTML_HEAD_SCRIPT_APPLICATIONAPPSTOREURL
                + COLUMN_HTML_HEAD_SCRIPT_OC + COLUMN_HTML_BODY_OC + APPLICATIONSETS_HTML_HEAD_END + titleHtml
                + nextHtml + "<img src=\"" + funnyColumnContentDetailCss
                + "/static/images/share-line.png\" alt=\"\" />" + HTML_TAIL;
        return fullHtml;
    }

    private String consoleColumnContent(String content, boolean haveApplication,
            AppStoreFunnyColumnDetailParameter para, String authorName, String authorImage,
            List<FunnyClientSpecialColumn> gg) {
        content = dealWithFunnyColumnContentApplication(content, haveApplication);
        String fullHtml = COLUMN_HTML_HEAD1 + para.getTitle() + COLUMN_HTML_HEAD2
                + COLUMN_HTML_HEAD_SCRIPT_APPLICATIONAPPSTOREURL + COLUMN_HTML_HEAD_SCRIPT_OC
                + COLUMN_HTML_HEAD_COLUMN_REFERENCE_OC + COLUMN_HTML_HEAD_COLUMN_IMAGE_OC + COLUMN_HTML_BODY_OC
                + COLUMN_HTML_HEAD_END;
        String titleAuthorHtml = "<div class=\"art-titl\"><h1>" + para.getTitle()
                + "</h1><div class=\"art-name\"><img src=\"" + authorImage + "\" alt=\"\"/><span>" + authorName
                + "</span><span>" + para.getShowDate().split(" ")[0] + "</span></div></div>";
        fullHtml = fullHtml + titleAuthorHtml + content;
        fullHtml = dealWithFunnyColumnContentReferenceColumn(fullHtml, gg, haveApplication);
        fullHtml += COLUMN_HTML_TAIL;
        fullHtml += HTML_TAIL;
        return fullHtml;
    }


    @Override
    public String previewHtml(AppStoreFunnyColumnDetailParameter para, String authorName, String authorImage,
            List<FunnyClientSpecialColumn> gg, Map<String, String> ctypesall) {
        String content = para.getContent();
        if (ctypesall.get(para.getCtypeId()).equals(AppStoreFunnyColumnDetailUtils.FUNNY_APPLICATION_SETS)) {
            int pointStart = content.indexOf("<img alt=\"\"");
            if (pointStart != -1) {
                String followStr = content.substring(pointStart + 5);
                int srcPosition = followStr.indexOf("src=\"");
                String srcStr = followStr.substring(srcPosition + 5);
                String imgSrc = srcStr.substring(0, srcStr.indexOf("\""));
                return consoleApplicationSets(content, para, authorName, authorImage, imgSrc, true);
            } else {
                String imgSrc = "http://img.huiyoobao.com/column/contentimage/20160922/2762ef6345acc473d61f32fc96205e32_1474531064514_orign.jpg";
                return consoleApplicationSets(content, para, authorName, authorImage, imgSrc, true);
            }
        }
        if (ctypesall.get(para.getCtypeId()).equals(AppStoreFunnyColumnDetailUtils.FUNNY_APPLICATION_LIMIT)) {
            content = consoleApplicationLimitContent(para.getContent());
        } else {
            content = dealWithFunnyColumnContentApplication(para.getContent(), true);
        }
        String fullHtml = COLUMN_HTML_HEAD1 + para.getTitle() + COLUMN_HTML_HEAD2_PREVIEW
                + COLUMN_HTML_HEAD_SCRIPT_APPLICATIONAPPSTOREURL + COLUMN_HTML_HEAD_SCRIPT_OC
                + COLUMN_HTML_HEAD_COLUMN_REFERENCE_OC + COLUMN_HTML_HEAD_COLUMN_IMAGE_OC + COLUMN_HTML_BODY_OC
                + COLUMN_HTML_HEAD_END;
        String titleAuthorHtml = "<div class=\"art-titl\"><h1>" + para.getTitle()
                + "</h1><div class=\"art-name\"><img src=\"" + authorImage + "\" alt=\"\"/><span>" + authorName
                + "</span><span>" + para.getShowDate().split(" ")[0] + "</span></div></div>";
        fullHtml = fullHtml + titleAuthorHtml + content;
        fullHtml = dealWithFunnyColumnContentReferenceColumn(fullHtml, gg, true);
        if (!ctypesall.get(para.getCtypeId()).equals(AppStoreFunnyColumnDetailUtils.FUNNY_APPLICATION_LIMIT)) {
            fullHtml += COLUMN_HTML_TAIL;
        }
        fullHtml += HTML_TAIL;
        return fullHtml;
    }

    private String dealWithFunnyColumnContentReferenceColumn(String fullHtml, List<FunnyClientSpecialColumn> gg,
            boolean haveApplication) {
        if (gg.size() > 0) {
            fullHtml += "<div class=\"art-book\"><img src=\""
                    + funnyColumnContentDetailCss
                    + "/static/images/art-book.png\" alt=\"\" /></div><div class=\"swiper-container\"><div class=\"swiper-wrapper\">";
        }
        for (FunnyClientSpecialColumn g : gg) {
            AppStoreFunnyColumnDetailParameter para = new AppStoreFunnyColumnDetailParameter();
            para.setId(g.getColumnDetailId());
            ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetail>> resp = this.iosFunnyApiService
                    .listAppStoreFunnyColumnDetail(para);
            int viewCount = 0;
            String viewUrl = "";
            if (resp != null && resp.getData() != null && resp.getData().getResultList() != null
                    && resp.getData().getResultList().size() > 0) {
                viewCount = resp.getData().getResultList().get(0).getViews();
                if (!haveApplication) {
                    viewUrl = resp.getData().getResultList().get(0).getViewNoApplicationUrl();
                } else {
                    viewUrl = resp.getData().getResultList().get(0).getViewUrl();
                }
            }
            fullHtml += "<div class=\"swiper-slide\"><a href=\"javascript:void(0);\" onclick=\"showColumnReference('"
                    + viewUrl + "'," + g.getId() + ",event)\"><img src=\"" + g.getImg()
                    + "\" alt=\"\"><p class=\"art-swtit\">" + g.getTitle() + "</p><p class=\"art-swlook\"><img src=\""
                    + funnyColumnContentDetailCss + "/static/images/icon.png\" alt=\"\"/>" + viewCount
                    + "</p></a></div>";
        }
        if (gg.size() > 0) {
            fullHtml += "</div></div>";
        }
        return fullHtml;
    }

    private String dealWithFunnyColumnContentApplication(String content, boolean haveApplication) {
        do {
            int pointStart = content.indexOf("<p><img class=\"insert_application_point\"");
            String applicationHtml = "";
            if (pointStart != -1) {
                String subStr = content.substring(pointStart);
                if (haveApplication) {
                    String rootIdIndexStr = subStr.substring(subStr.indexOf("rootId:") + 7);
                    int rootid = Integer.valueOf(rootIdIndexStr.substring(0, rootIdIndexStr.indexOf(",")));
                    ApiRespWrapper<AppStoreApplicationWrapper> resp = backendApiService.getApplicationWrapper(rootid);
                    AppStoreApplicationWrapper application = resp.getData();
                    double size = new BigDecimal(Double.valueOf(application.getSize()) / 1024 / 1024).setScale(2,
                            BigDecimal.ROUND_HALF_UP).doubleValue();
                    applicationHtml = "<div class=\"art-app\"><img src=\""
                            + application.getIcon()
                            + "\" alt=\""
                            + application.getTitle()
                            + "\">"
                            + "<div class=\"app-info\"><div class=\"app-info-c f18\"><a href=\"javascript:void(0);\" onclick=\"showApplication('"
                            + application.getAppStoreUrl()
                            + "')\">"
                            + application.getTitle()
                            + "</a></div><div class=\"app-info-c f14\"><span>"
                            + size
                            + "MB"
                            + "</span> | <span>"
                            + application.getCategory().getName()
                            + "</span></div><div class=\"app-info-c f16\">"
                            + (application.getEditorReview() == null ? application.getShortDesc() : application
                                    .getEditorReview())
                            + "</div></div><a href=\"javascript:void(0);\" onclick=\"showApplication('"
                            + application.getAppStoreUrl() + "'," + application.getRootId()
                            + ",event)\" class=\"down\">获取</a></div>";
                }
                int pointEnd = subStr.indexOf("</p>");
                subStr = subStr.substring(pointEnd + 4);
                if (haveApplication) {
                    content = content.substring(0, pointStart) + applicationHtml + subStr;
                } else {
                    content = content.substring(0, pointStart) + subStr;
                }
            }
        } while (content.indexOf("<p><img class=\"insert_application_point\"") != -1);
        return content;
    }

    private String consoleApplicationLimit(String content, AppStoreFunnyColumnDetailParameter para, String authorName,
            String authorImage) {
        content = consoleApplicationLimitContent(content);
        String fullHtml = COLUMN_HTML_HEAD1 + para.getTitle() + COLUMN_HTML_HEAD2
                + COLUMN_HTML_HEAD_SCRIPT_APPLICATIONAPPSTOREURL + COLUMN_HTML_HEAD_SCRIPT_OC
                + COLUMN_HTML_HEAD_COLUMN_REFERENCE_OC + COLUMN_HTML_HEAD_COLUMN_IMAGE_OC + COLUMN_HTML_BODY_OC
                + COLUMN_HTML_HEAD_END;
        String titleAuthorHtml = "<div class=\"art-titl\"><h1>" + para.getTitle()
                + "</h1><div class=\"art-name\"><img src=\"" + authorImage + "\" alt=\"\"/><span>" + authorName
                + "</span><span>" + para.getShowDate().split(" ")[0] + "</span></div></div>";
        fullHtml = fullHtml + titleAuthorHtml + content;
        fullHtml += HTML_TAIL;
        return fullHtml;
    }

    private String consoleApplicationLimitContent(String content) {
        do {
            int pointStart = content.indexOf("<p><img class=\"insert_application_point\"");
            if (pointStart != -1) {
                String applicationHtml = "";
                String subStr = content.substring(pointStart);
                int orignPrice = 0;
                int currentPrice = 0;
                String rootIdIndexStr = subStr.substring(subStr.indexOf("rootId:") + 7);
                int rootid = Integer.valueOf(rootIdIndexStr.substring(0, rootIdIndexStr.indexOf(",")));
                int orignPricePosition = rootIdIndexStr.indexOf("<p>原价:");
                String priceStr = rootIdIndexStr.substring(orignPricePosition);
                int currentPricePosition = priceStr.indexOf("现价:");
                String orignPriceStr = priceStr.substring(0, currentPricePosition);
                String currentPriceStr = priceStr.substring(currentPricePosition, priceStr.indexOf("</p>"));
                int orignPos = orignPriceStr.indexOf("value=\"");
                int currentPos = currentPriceStr.indexOf("value=\"");
                if (orignPos != -1) {
                    String subOrignStr = orignPriceStr.substring(orignPos + 7);
                    orignPrice = Integer.valueOf(subOrignStr.substring(0, subOrignStr.indexOf("\"")));
                }
                if (currentPos != -1) {
                    String subCurrentStr = currentPriceStr.substring(currentPos + 7);
                    currentPrice = Integer.valueOf(subCurrentStr.substring(0, subCurrentStr.indexOf("\"")));
                }
                ApiRespWrapper<AppStoreApplicationWrapper> resp = backendApiService.getApplicationWrapper(rootid);
                AppStoreApplicationWrapper application = resp.getData();
                String imgLimit = funnyColumnContentDetailCss + "/static/images/free-free.png";
                if (currentPrice != 0 && currentPrice < orignPrice) {
                    imgLimit = funnyColumnContentDetailCss + "/static/images/free-pl.png";
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                applicationHtml = "<ul class=\"free-ul\"><li><span class=\"float\"><img src=\""
                        + imgLimit
                        + "\" alt=\"\" /></span>"
                        + "<img src=\""
                        + application.getIcon()
                        + "\" alt=\"\" class=\"icon\" />"
                        + "<div class=\"app\"><p>"
                        + (application.getEditorReview() == null ? application.getShortDesc() : application
                                .getEditorReview())
                        + "</p><p class=\"f12\">"
                        + df.format(application.getReleaseDate())
                        + "<em>|</em><em class=\"ml\"><span>"
                        + "原价："
                        + orignPrice
                        + "</span></em>"
                        + (currentPrice != 0 ? ("<em>|</em>现价：" + currentPrice) : "")
                        + "</p><p class=\"f13\">"
                        + (application.getEditorReview() == null ? application.getShortDesc() : application
                                .getEditorReview()) + "</p></div>"
                        + "<a href=\"javascript:void(0);\" onclick=\"showApplication('" + application.getAppStoreUrl()
                        + "'," + application.getRootId() + ",event)\" class=\"down\">获取</a></li></ul>";

                int pointEnd = subStr.indexOf("</p>");
                subStr = subStr.substring(pointEnd + 4);
                int pointEndP = subStr.indexOf("</p>");
                subStr = subStr.substring(pointEndP + 4);
                content = content.substring(0, pointStart) + applicationHtml + subStr;
            }
        } while (content.indexOf("<p><img class=\"insert_application_point\"") != -1);
        return content;
    }

    private Integer dealWithFunnyColumnContentApplicationSets(String content, AppStoreFunnyColumnDetailParameter para) {
        int count = 0;
        String htmlApplicationStartHtml = "<ul class=\"free-ul\">";
        String applicationSetsHtml = "";
        do {
            int pointStart = content.indexOf("<p><img class=\"insert_application_point\"");
            String applicationHtml = "";
            if (pointStart != -1) {
                count++;
                String subStr = content.substring(pointStart);
                String rootIdIndexStr = subStr.substring(subStr.indexOf("rootId:") + 7);
                int rootid = Integer.valueOf(rootIdIndexStr.substring(0, rootIdIndexStr.indexOf(",")));
                String talkStr = "<p>小编叨叨:<textarea name=\"applicationSetsDescription" + rootid + "\">";
                int positionTalk = rootIdIndexStr.indexOf(talkStr);
                String subTalkStr = rootIdIndexStr.substring(positionTalk);
                String editorTalk = subTalkStr.substring(talkStr.length(), subTalkStr.indexOf("</textarea></p>"));
                ApiRespWrapper<AppStoreApplicationWrapper> resp = backendApiService.getApplicationWrapper(rootid);
                AppStoreApplicationWrapper application = resp.getData();
                applicationHtml = "<li class=\"li\"><img src=\""
                        + application.getIcon()
                        + "\" alt=\""
                        + application.getTitle()
                        + "\" class=\"icon\" />"
                        + "<div class=\"app app-js\"><p>"
                        + application.getTitle()
                        + "</p><p class=\"f13\">"
                        + (StringUtils.isEmpty(editorTalk) ? (application.getEditorReview() == null ? application
                                .getShortDesc() : application.getEditorReview()) : editorTalk) + "</p></div>"
                        + "<a href=\"javascript:void(0);\" onclick=\"showApplication('" + application.getAppStoreUrl()
                        + "'," + application.getRootId() + ",event)\" class=\"down\">获取</a></li>";
                int pointEnd = subStr.indexOf("</p>");
                subStr = subStr.substring(pointEnd + 4);
                int pointEndP = subStr.indexOf("</p>");
                subStr = subStr.substring(pointEndP + 4);
                applicationSetsHtml += applicationHtml;
                content = content.substring(0, pointStart) + applicationHtml + subStr;
            }
        } while (content.indexOf("<p><img class=\"insert_application_point\"") != -1);
        String allApplicationSetsHtml = htmlApplicationStartHtml + applicationSetsHtml + "</ul>";
        para.setAllApplicationSetsHtml(allApplicationSetsHtml);
        return count;
    }

    private String dealWithFunnyColumnContentImg(String content, boolean isReturnImgSrc,
            AppStoreFunnyColumnDetailParameter para) {
        String imgReturnSrc = "";
        do {
            int pointStart = content.indexOf("<img alt=\"\"");
            if (pointStart != -1) {
                String largeImg = "";
                String subStr = content.substring(0, pointStart + 5);
                String followStr = content.substring(pointStart + 5);
                int srcPosition = followStr.indexOf("src=\"");
                String srcStr = followStr.substring(srcPosition + 5);
                String beforeSrcStr = followStr.substring(0, srcPosition + 5);
                String imgSrc = srcStr.substring(0, srcStr.indexOf("\""));
                String afterImgSrc = srcStr.substring(srcStr.indexOf("\""));
                int idPosition = beforeSrcStr.indexOf("id=\"");
                if (idPosition != -1) {
                    largeImg = beforeSrcStr.substring(idPosition + 4);
                    largeImg = largeImg.substring(0, largeImg.indexOf("\""));
                }
                String urlPath = getAppStoreColumnContentImageUrlDir();
                String destFile = "";
                String destUrlFile = "";
                String suffixGif = imgSrc.substring(imgSrc.lastIndexOf(".") + 1);
                boolean isExists = false;
                if (imgSrc.indexOf(urlPath) != -1) {
                    destFile = imgSrc.replace(urlPath, appStoreColumnContentImageFileDir);
                    isExists = true;
                } else {
                    String suffix = imgSrc.substring(imgSrc.lastIndexOf(".") + 1);
                    suffix = StringUtils.isEmpty(suffix) ? "png" : suffix;
                    File file = new File(getFilePath(getAppStoreColumnContentImageFileDir()).getAbsolutePath(),
                            DigestUtils.md5Hex(DateUtils.getNow()) + "_" + System.currentTimeMillis() + "_orign."
                                    + suffix);
                    destFile = localResService.saveColumnImg(imgSrc, file);
                    String subRelativePath = file.getPath().replace(getAppStoreColumnContentImageFileDir(), "");
                    destUrlFile = UrlUtils.spliceUrl(urlPath, subRelativePath);
                }
                File file = new File(destFile);
                if (imgSrc.indexOf(urlPath) == -1) {
                    followStr = beforeSrcStr + destUrlFile + afterImgSrc;
                    imgSrc = destUrlFile;
                }
                if (!isExists && !suffixGif.equalsIgnoreCase("gif")
                        && UploadFileUtils.getFileBytes(file).length > 256 * 1024) {
                    String ysSrc = saveColumnContentImg(file).getUrl();
                    followStr = beforeSrcStr + ysSrc + afterImgSrc;
                }
                imgReturnSrc = imgSrc;
                if (StringUtils.isEmpty(largeImg)) {
                    largeImg = imgSrc;
                }
                // content = subStr + " onclick=\"showImageLarge('" + imgSrc +
                // "',event)\" " + followStr;
                content = subStr + " onclick=\"showImageLarge('" + largeImg + "',event)\" " + "id=\"" + largeImg
                        + "\" " + followStr;
            }
        } while (content.indexOf("<img alt=\"\"") != -1);
        if (isReturnImgSrc) {
            para.setContent(content);
            return imgReturnSrc;
        } else {
            return content;
        }
    }

    @Override
    public UploadFileResp saveStoreIcon(CommonsMultipartFile icon) {
        String localPath = getIconFileDir();
        String urlPath = getIconImgUrlDir();
        String imgUrlFile = saveImg(0, localPath, urlPath, icon, null);
        UploadFileResp uploadImageResp = new UploadFileResp();
        uploadImageResp.setSaved(true);
        uploadImageResp.setUrl(imgUrlFile);
        return uploadImageResp;
    }

    @Override
    public UploadFileResp saveStoreContentImg(CommonsMultipartFile imageFile) {
        String localPath = getArticleImgFileDir();
        String urlPath = getArticleImgUrlDir();
        String imgUrlFile = saveImg(DateUtils.currentYearMonth(), localPath, urlPath, imageFile, null);
        UploadFileResp uploadImageResp = new UploadFileResp();
        uploadImageResp.setSaved(true);
        uploadImageResp.setUrl(imgUrlFile);
        return uploadImageResp;
    }

    @Override
    public String saveStoreBanner(CommonsMultipartFile banner) {
        String localPath = getStoreBannerImgFileDir();
        String urlPath = getStoreBannerImgUrlDir();
        return saveImg(DateUtils.currentYearMonth(), localPath, urlPath, banner, ImageType.Banner);
    }

    @Override
    public String saveStoreAdImg(CommonsMultipartFile img) {
        String localPath = getLoadPageAdImgFileDir();
        String urlPath = getLoadPageAdImgUrlDir();
        return saveImg(0, localPath, urlPath, img, ImageType.AD);
    }

    @Override
    public String saveStoreVideoScreenImg(int id, CommonsMultipartFile screen) {
        String localPath = getVideoScreenShotFileDir();
        String urlPath = getVideoScreenShotUrlDir();
        return saveImg(id, localPath, urlPath, screen, ImageType.Vedio);
    }

    @Override
    public String saveStoreTabIcon(CommonsMultipartFile icon) {
        String localPath = getTabIconFileDir();
        String urlPath = getTabIconUrlDir();
        return saveImg(0, localPath, urlPath, icon, null);
    }

    @Override
    public UploadFileResp savePcSuiteItunesDll(CommonsMultipartFile dll) {
        String localPath = getPcSuiteItunesDllFileDir();
        String urlPath = getPcSuiteItunesDllUrlDir();
        return saveFile(localPath, urlPath, dll);
    }

    @Override
    public UploadFileResp savePcSuiteItunesDriver(CommonsMultipartFile dll) {
        String localPath = getPcSuiteItunesDriverFileDir();
        String urlPath = getPcSuiteItunesDriverUrlDir();
        return saveFile(localPath, urlPath, dll);
    }

    @Override
    public UploadFileResp savePcSuiteIosProgrammerDriver(CommonsMultipartFile dll) {
        String localPath = getPcSuiteIosProgrammerDriverFileDir();
        String urlPath = getPcSuiteIosProgrammerDiverUrlDir();
        return saveFile(localPath, urlPath, dll);
    }

    @Override
    public UploadImgResp savePcSuiteIphoneModelImg(CommonsMultipartFile dll) {
        String localPath = getPcSuiteIphoneModelImgFileDir();
        String urlPath = getPcSuiteIphoneModelImgUrlDir();
        return savePngImg(localPath, urlPath, dll);
    }

    @Override
    public UploadFileResp saveColumnContentImg(CommonsMultipartFile img) {
        String localPath = getAppStoreColumnContentImageFileDir();
        String urlPath = getAppStoreColumnContentImageUrlDir();
        return saveColumnImage(localPath, urlPath, img);
    }

    @Override
    public UploadFileResp saveColumnContentImg(File img) {
        String localPath = getAppStoreColumnContentImageFileDir();
        String urlPath = getAppStoreColumnContentImageUrlDir();
        return saveColumnImage(localPath, urlPath, img);
    }

    private String getIconImgUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, iconUrlPrefix);
    }

    private String getIconFileDir() {
        return iconImgPath;
    }

    private String getArticleImgUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, articleImgUrlPrefix);
    }

    private String getArticleImgFileDir() {
        return articleImgPath;
    }

    private String getDynamicCodeUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, dynamicCodeUrlPrefix);
    }

    private String getH5GameIconUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, h5GameIconUrlPrefix);
    }

    private String getDynamicCodeFileDir() {
        return dynamicCodePath;
    }

    private String getH5GameIconFileDir() {
        return h5GameIconPath;
    }

    private String getShareAccountIconFileDir() {
        return shareAccountIconPath;
    }

    private String getArticleFileDir() {
        return htmlPath;
    }

    private String getColumnFileDir() {
        return htmlColumnPath;
    }

    private String getArticleUrlDir() {
        return IOUtils.spliceUrlPath(iosHtmlUrlPrefix, htmlUrlPrefix);
    }

    private String getColumnUrlDir() {
        return IOUtils.spliceUrlPath(iosfunnyHtmlUrlPrefix, htmlColumnUrlPrefix);
    }

    private String getStoreBannerImgUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, storeBannerUrlPrefix);
    }

    private String getStoreBannerImgFileDir() {
        return storeBannerImgPath;
    }

    private String getPcSuiteItunesDllFileDir() {
        return pcSuiteItunesDllPath;
    }

    private String getPcSuiteItunesDriverFileDir() {
        return pcSuiteItunesDriverPath;
    }

    private String getPcSuiteIosProgrammerDriverFileDir() {
        return pcSuiteIosProgrammerDriverPath;
    }

    private String getPcSuiteIphoneModelImgFileDir() {
        return PcSuiteIphoneModelImgPath;
    }

    private String getPcSuiteAppleDeviceDriverFileDir() {
        return pcSuiteAppleDeviceDriverPath;
    }

    private String getAppStoreColumnContentImageUrlDir() {
        return IOUtils.spliceUrlPath(iosfunnyHtmlUrlPrefix, appStoreColumnContentImagePrefix);
    }

    private String getAppStoreColumnContentHtmlUrlDir() {
        return IOUtils.spliceUrlPath(iosfunnyHtmlUrlPrefix, htmlColumnUrlPrefix);
    }

    private String getAppStoreColumnContentImageFileDir() {
        return appStoreColumnContentImageFileDir;
    }

    private String getLoadPageAdImgUrlDir() {
        return IOUtils.spliceUrlPath(iosFunnyImgUrlPrefix, loadPageAdUrlPrefix);
    }

    private String getfunnyClientAdImgUrlDir() {
        return IOUtils.spliceUrlPath(iosFunnyImgUrlPrefix, funnyClientAdUrlPrefix);
    }

    private String getfunnyClientColumnBackImgUrlDir() {
        return IOUtils.spliceUrlPath(iosFunnyImgUrlPrefix, funnyClientColumnBackUrlPrefix);
    }

    private String getfunnyClientAuthorIconImgUrlDir() {
        return IOUtils.spliceUrlPath(iosFunnyImgUrlPrefix, funnyClientAuthorIconUrlPrefix);
    }

    private String getLoadPageAdImgFileDir() {
        return loadPageAdImgPath;
    }

    private String getFunnyClientAdImgFileDir() {
        return funnyClientAdImgPath;
    }

    private String getFunnyClientColumnBackImgFileDir() {
        return funnyClientColumnBackImgPath;
    }

    private String getFunnyClientAuthorIconImgFileDir() {
        return funnyClientAuthorIconImgPath;
    }

    private String getVideoScreenShotUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, videoScreenShotUrlPrefix);
    }

    private String getVideoScreenShotFileDir() {
        return videoScreenShotPath;
    }

    private String getTabIconUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, tabIconUrlPrefix);
    }

    private String getPcSuiteItunesDllUrlDir() {
        return IOUtils.spliceUrlPath(pcsuiteUrlPrefix, pcSuiteItunesDllUrlPrefix);
    }

    private String getPcSuiteItunesDriverUrlDir() {
        return IOUtils.spliceUrlPath(pcsuiteUrlPrefix, pcSuiteItunesDriverUrlPrefix);
    }

    private String getPcSuiteIosProgrammerDiverUrlDir() {
        return IOUtils.spliceUrlPath(pcsuiteUrlPrefix, pcSuiteIosProgrammerDriverUrlPrefix);
    }

    private String getPcSuiteIphoneModelImgUrlDir() {
        return IOUtils.spliceUrlPath(pcsuiteUrlPrefix, PcSuiteIphoneModelImgUrlPrefix);
    }

    private String getPcSuiteAppleDeviceDriverUrlDir() {
        return IOUtils.spliceUrlPath(pcsuiteUrlPrefix, pcSuiteAppleDeviceDriverUrlPrefix);
    }

    private String getTabIconFileDir() {
        return tabIconPath;
    }

    private static File getFilePath(String parent) {
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dayDateFormat.format(new Date());
        String path = IOUtils.spliceFilePath(parent, today) + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private String getBannerImgUrlDir() {
        return IOUtils.spliceUrlPath(iosimgUrlPrefix, bannerUrlPrefix);
    }

    private String getShareAccountIconUrlDir() {
        return IOUtils.spliceUrlPath(iosimgUrlPrefix, shareAccountIconUrlPrefix);
    }

    private String getBannerImgFileDir() {
        return bannerImgPath;
    }

    private String getDailyRecomBannerImgUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, dailyRecomBannerUrlPrefix);
    }

    private String getDailyRecomBannerImgFileDir() {
        return dailyRecomBannerPath;
    }

    private String getVideoCateIconUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, videoCateIconUrlPrefix);
    }

    private String getVideoCateIconImgFileDir() {
        return videoCateIconPath;
    }

    private String getAppCateIconUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, appCateIconUrlPrefix);
    }

    private String getAppCateIconFileDir() {
        return appCateIconPath;
    }

    private static UploadFileResp saveFile(String localPath, String urlPath, CommonsMultipartFile file) {
        UploadFileResp resp = new UploadFileResp();
        String fileMd5 = DigestUtils.md5Hex(file.getBytes());
        String fileMd5Name = IOUtils.getFileMd5Name(fileMd5, file.getOriginalFilename());
        File dest = new File(getFilePath(localPath).getAbsolutePath(), System.currentTimeMillis() + "_" + fileMd5Name);
        try {
            dest.getParentFile().mkdirs();
            file.transferTo(dest);
            String subRelativePath = dest.getPath().replace(localPath, "");
            String fileUrl = UrlUtils.spliceUrl(urlPath, subRelativePath);
            String fileFullPath = dest.getPath();
            resp.setLocation(fileFullPath);
            resp.setMd5(fileMd5);
            resp.setSaved(true);
            resp.setUrl(fileUrl);
        } catch (Exception e) {
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    private static UploadImgResp savePngImg(String localPath, String urlPath, CommonsMultipartFile file) {
        String suffix = "png";
        byte[] bytes = UploadFileUtils.getFileBytes(file);
        String fileMd5 = DigestUtils.md5Hex(bytes);
        File originFile = new File(getFilePath(localPath).getAbsolutePath(), fileMd5 + "_" + System.currentTimeMillis()
                + "_origin." + suffix);
        UploadImgResp resp = new UploadImgResp();
        try {
            ImgRes imgReg = ImageUtil.saveImage(originFile, suffix, bytes);
            String subRelativePath = originFile.getPath().replace(localPath, "");
            String fileUrl = UrlUtils.spliceUrl(urlPath, subRelativePath);
            String fileFullPath = originFile.getPath();
            resp.setLocation(fileFullPath);
            resp.setMd5(fileMd5);
            resp.setSaved(true);
            resp.setUrl(fileUrl);
            resp.setHeight(imgReg.getHeight());
            resp.setWidth(imgReg.getWidth());
        } catch (IOException e) {
            throw ServiceException.getInternalException("存储图片失败. Errmsg:" + e.getMessage());
        }
        return resp;
    }

    private UploadFileResp saveColumnImage(String localPath, String urlPath, CommonsMultipartFile file) {
        String suffix = UploadFileUtils.getSuffix(file);
        suffix = StringUtils.isEmpty(suffix) ? "png" : suffix;
        byte[] bytes = UploadFileUtils.getFileBytes(file);
        String fileMd5 = DigestUtils.md5Hex(bytes);
        File originFile = new File(getFilePath(localPath).getAbsolutePath(), fileMd5 + "_" + System.currentTimeMillis()
                + "_origin." + suffix);
        UploadImgResp resp = new UploadImgResp();
        try {
            ImageUtil.saveOrignImage(originFile, bytes);
            String subRelativePath = originFile.getPath().replace(localPath, "");
            String fileUrl = UrlUtils.spliceUrl(urlPath, subRelativePath);
            String fileFullPath = originFile.getPath();
            resp.setLocation(fileFullPath);
            resp.setMd5(fileMd5);
            resp.setSaved(true);
            resp.setUrl(fileUrl);
        } catch (IOException e) {
            throw ServiceException.getInternalException("存储图片失败. Errmsg:" + e.getMessage());
        }
        return resp;
    }

    private UploadFileResp saveColumnImage(String localPath, String urlPath, File file) {
        String suffix = UploadFileUtils.getSuffix(file.getName());
        suffix = StringUtils.isEmpty(suffix) ? "png" : suffix;
        byte[] bytes = UploadFileUtils.getFileBytes(file);
        String fileMd5 = DigestUtils.md5Hex(bytes);
        File optFile = new File(getFilePath(localPath).getAbsolutePath(), fileMd5 + "_" + System.currentTimeMillis()
                + "_opt." + suffix);
        UploadImgResp resp = new UploadImgResp();
        try {
            ImgRes imgReg = ImageUtil.saveImage(optFile, suffix, bytes);
            String subRelativePath = optFile.getPath().replace(localPath, "");
            String fileUrl = UrlUtils.spliceUrl(urlPath, subRelativePath);
            String fileFullPath = optFile.getPath();
            resp.setLocation(fileFullPath);
            resp.setMd5(fileMd5);
            resp.setSaved(true);
            resp.setUrl(fileUrl);
            resp.setHeight(imgReg.getHeight());
            resp.setWidth(imgReg.getWidth());
        } catch (IOException e) {
            throw ServiceException.getInternalException("存储图片失败. Errmsg:" + e.getMessage());
        }
        return resp;
    }

    private static UploadFileResp saveImg(String localPath, String urlPath, CommonsMultipartFile file) {
        String suffix = UploadFileUtils.getSuffix(file);
        String optFileSuffix = "jpg";
        suffix = StringUtils.isEmpty(suffix) ? "png" : suffix;
        byte[] bytes = UploadFileUtils.getFileBytes(file);
        String fileMd5 = DigestUtils.md5Hex(bytes);
        File originFile = new File(getFilePath(localPath).getAbsolutePath(), fileMd5 + "_" + System.currentTimeMillis()
                + "_origin." + suffix);
        File optFile = new File(getFilePath(localPath).getAbsolutePath(), fileMd5 + "_" + System.currentTimeMillis()
                + "_opt." + optFileSuffix);
        File realyUseFile;
        UploadFileResp resp = new UploadFileResp();
        try {
            realyUseFile = ImageUtil.saveImage(originFile, suffix, optFile, optFileSuffix, bytes, null);
            String subRelativePath = realyUseFile.getPath().replace(localPath, "");
            String fileUrl = UrlUtils.spliceUrl(urlPath, subRelativePath);
            String fileFullPath = realyUseFile.getPath();
            resp.setLocation(fileFullPath);
            resp.setMd5(fileMd5);
            resp.setSaved(true);
            resp.setUrl(fileUrl);
        } catch (IOException e) {
            throw ServiceException.getInternalException("存储图片失败. Errmsg:" + e.getMessage());
        }
        return resp;
    }

    private static String saveImg(int id, String localPath, String urlPath, CommonsMultipartFile icon,
            ImageType imageType) {
        String suffix = UploadFileUtils.getSuffix(icon);
        String optFileSuffix = "jpg";
        suffix = StringUtils.isEmpty(suffix) ? "png" : suffix;
        byte[] bytes = UploadFileUtils.getFileBytes(icon);
        File originFile = new File(getFilePath(id, localPath).getAbsolutePath(), id + "_" + System.currentTimeMillis()
                + "_origin." + suffix);
        File optFile = new File(getFilePath(id, localPath).getAbsolutePath(), id + "_" + System.currentTimeMillis()
                + "_opt." + optFileSuffix);
        File realyUseFile;
        try {
            realyUseFile = ImageUtil.saveImage(originFile, suffix, optFile, optFileSuffix, bytes, imageType);
        } catch (IOException e) {
            throw ServiceException.getInternalException("存储图片失败. Errmsg:" + e.getMessage());
        }
        // saveScaleImg(file, bytes, icon.getSize());
        String subRelativePath = realyUseFile.getPath().replace(localPath, "");
        return UrlUtils.spliceUrl(urlPath, subRelativePath);
    }

    private static File getFilePath(int rid, String parent) {
        String idStr = String.valueOf(rid);
        if (idStr.length() > 4) {
            idStr = idStr.substring(0, 4);
        }
        String path = IOUtils.spliceFilePath(parent, idStr) + File.separator;
        File file = new File(path);
        file.mkdir();
        return file;
    }

    @Override
    public UploadFileResp saveDynamicCode(int id, CommonsMultipartFile codes) {
        String localPath = getDynamicCodeFileDir();
        String urlPath = getDynamicCodeUrlDir();
        return saveFile(localPath, urlPath, codes);
    }

    @Override
    public UploadFileResp saveH5GameIcon(CommonsMultipartFile icon) {
        String localPath = getH5GameIconFileDir();
        String urlPath = getH5GameIconUrlDir();
        return saveImg(localPath, urlPath, icon);
    }

    @Override
    public UploadFileResp saveShareAccountIcon(CommonsMultipartFile icon) {
        String localPath = getShareAccountIconFileDir();
        String urlPath = getShareAccountIconUrlDir();
        return saveImg(localPath, urlPath, icon);
    }

    @Override
    public String saveTestP12(CommonsMultipartFile testCertFile) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String saveProductP12(CommonsMultipartFile productCertFile) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UploadFileResp savePcSuiteAppleDeviceDriver(CommonsMultipartFile driver) {
        String localPath = getPcSuiteAppleDeviceDriverFileDir();
        String urlPath = getPcSuiteAppleDeviceDriverUrlDir();
        return saveFile(localPath, urlPath, driver);
    }

    @Override
    public UploadFileResp saveStoreWallpaperImg(CommonsMultipartFile img) {
        String localPath = getLoadPageWallpaperImgFileDir();
        String urlPath = getLoadPageWallpaperImgUrlDir();
        return saveWallpaperImg(localPath, urlPath, img);
    }

    private UploadFileResp saveWallpaperImg(String localPath, String urlPath, CommonsMultipartFile img) {
        String suffix = UploadFileUtils.getSuffix(img);
        String optFileSuffix = "jpg";
        suffix = StringUtils.isEmpty(suffix) ? "png" : suffix;
        byte[] bytes = UploadFileUtils.getFileBytes(img);
        String fileMd5 = DigestUtils.md5Hex(bytes);
        File originFile = new File(getFilePath(localPath).getAbsolutePath(), fileMd5 + "_" + System.currentTimeMillis()
                + "_origin." + suffix);
        File optFile = new File(getFilePath(localPath).getAbsolutePath(), fileMd5 + "_" + System.currentTimeMillis()
                + "_opt." + optFileSuffix);
        UploadFileResp resp = new UploadFileResp();
        try {
            ArrayList<File> fileList = ImageUtil.saveImages(originFile, suffix, optFile, optFileSuffix, bytes,
                    ImageType.Wallpaper);
            resp.setUrl(parseUrl(urlPath, fileList.get(0), localPath));
            resp.setResizeUrl(parseUrl(urlPath, fileList.get(0), localPath));
            if (fileList.size() > 1) {
                resp.setResizeUrl(parseUrl(urlPath, fileList.get(1), localPath));
            }
            resp.setMd5(fileMd5);
            resp.setSaved(true);
        } catch (IOException e) {
            throw ServiceException.getInternalException("存储图片失败. Errmsg:" + e.getMessage());
        }
        return resp;
    }

    private String parseUrl(String urlPath, File reallyFile, String localPath) {
        String subRelativePath = reallyFile.getPath().replace(localPath, "");
        return UrlUtils.spliceUrl(urlPath, subRelativePath);
    }

    private String getLoadPageWallpaperImgUrlDir() {
        return IOUtils.spliceUrlPath(iosStoreImgUrlPrefix, loadPageWallpaperUrlPrefix);
    }

    private String getLoadPageWallpaperImgFileDir() {
        return loadPageWallpaperImgPath;
    }

    @Override
    public UploadFileResp saveStoreWallpaperImg(File img) {
        String localPath = getLoadPageWallpaperImgFileDir();
        String urlPath = getLoadPageWallpaperImgUrlDir();
        return saveWallpaperImg(localPath, urlPath, img);
    }

    private UploadFileResp saveWallpaperImg(String localPath, String urlPath, File img) {
        String suffix = UploadFileUtils.getSuffix(img.getName());
        String optFileSuffix = "jpg";
        suffix = StringUtils.isEmpty(suffix) ? "png" : suffix;
        byte[] bytes = UploadFileUtils.getFileBytes(img);
        String fileMd5 = DigestUtils.md5Hex(bytes);
        File originFile = new File(getFilePath(localPath).getAbsolutePath(), fileMd5 + "_" + System.currentTimeMillis()
                + "_origin." + suffix);
        File optFile = new File(getFilePath(localPath).getAbsolutePath(), fileMd5 + "_" + System.currentTimeMillis()
                + "_opt." + optFileSuffix);
        UploadFileResp resp = new UploadFileResp();
        try {
            ArrayList<File> fileList = ImageUtil.saveImages(originFile, suffix, optFile, optFileSuffix, bytes,
                    ImageType.Wallpaper);
            resp.setUrl(parseUrl(urlPath, fileList.get(0), localPath));
            resp.setResizeUrl(parseUrl(urlPath, fileList.get(0), localPath));
            if (fileList.size() > 1) {
                resp.setResizeUrl(parseUrl(urlPath, fileList.get(1), localPath));
            }
            resp.setMd5(fileMd5);
            resp.setSaved(true);
        } catch (IOException e) {
            throw ServiceException.getInternalException("存储图片失败. Errmsg:" + e.getMessage());
        }
        return resp;
    }


    @Override
    public String saveFunnyClientAdImg(CommonsMultipartFile img) {
        String localPath = getFunnyClientAdImgFileDir();
        String urlPath = getfunnyClientAdImgUrlDir();
        return saveImg(random.nextInt(30), localPath, urlPath, img, ImageType.AD);
    }

    @Override
    public String saveFunnyClientColumnListBackImg(CommonsMultipartFile img) {
        String localPath = getFunnyClientColumnBackImgFileDir();
        String urlPath = getfunnyClientColumnBackImgUrlDir();
        return saveImg(random.nextInt(30), localPath, urlPath, img, ImageType.Banner);
    }

    @Override
    public String saveFunnyClientColumnAuthorImg(CommonsMultipartFile img) {
        String localPath = getFunnyClientAuthorIconImgFileDir();
        String urlPath = getfunnyClientAuthorIconImgUrlDir();
        return saveImg(random.nextInt(30), localPath, urlPath, img, ImageType.Banner);
    }

}
