package com.appchina.ios.mgnt.controller.info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

import com.appchina.ios.core.cahe.model.info.AppStoreArticleSimple;
import com.appchina.ios.core.dto.info.AppStoreArticle;
import com.appchina.ios.core.dto.info.AppStoreArticleRecom;
import com.appchina.ios.core.dto.info.AppStoreArticleRecomIntervention;
import com.appchina.ios.core.dto.info.AppStoreArticleTag;
import com.appchina.ios.core.dto.info.AppStoreClientInfo;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.UploadFileResp;
import com.appchina.ios.mgnt.controller.model.info.AppStoreArticleInterventionParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreArticleParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreArticleRecomParameter;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.service.impl.StoreCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;

/**
 * Created by zhouyanhui on 7/16/15.
 */
@Controller
@RequestMapping(value = "/auth/store/article/*")
public class ArticleController {
    private static final Logger log = Logger.getLogger(ArticleController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private StoreCommonParameterService storeCommonParameterService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@ModelAttribute("errMsg") String errMsg, Model model) {
        ApiRespWrapper<Map<Integer, AppStoreArticleTag>> tagsResp = this.iosStoreApiService
                .listAllAppStoreArticleTags();
        storeCommonParameterService.addBundles("bundleIds", model);
        if (tagsResp != null && tagsResp.getData() != null) {
            Map<String, String> datas = new HashMap<String, String>();
            for (Map.Entry<Integer, AppStoreArticleTag> entry : tagsResp.getData().entrySet()) {
                datas.put(entry.getKey().toString(), entry.getValue().getName());
            }
            model.addAttribute("tags", datas);
        }
        model.addAttribute("para", new AppStoreArticleParameter());
        return "store/info/article/add.ftl";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(int id, Model model) {
        String errMsg = "";
        if (id < 0) {
            errMsg = "id 不合法";
            model.addAttribute("errMsg", errMsg);
            return "store/info/article/list.ftl";
        }
        ApiRespWrapper<AppStoreArticle> appStoreArticleResp = this.iosStoreApiService.queryAppStoreArticle(id);
        if (appStoreArticleResp == null || appStoreArticleResp.getData() == null) {
            errMsg = "资讯没找到";
            model.addAttribute("errMsg", errMsg);
            return "store/info/article/add.ftl";
        }
        AppStoreArticleRecomParameter appStoreArticleRecomParameter = new AppStoreArticleRecomParameter();
        appStoreArticleRecomParameter.setArticleId(id);
        addArticleRecomInfos(null, id, "recomBundleIds", model);
        AppStoreArticle appStoreArticle = appStoreArticleResp.getData();
        ApiRespWrapper<Map<Integer, AppStoreArticleTag>> tagsResp = this.iosStoreApiService
                .listAllAppStoreArticleTags();

        if (tagsResp != null && tagsResp.getData() != null) {
            Map<String, String> datas = new HashMap<String, String>();
            for (Map.Entry<Integer, AppStoreArticleTag> entry : tagsResp.getData().entrySet()) {
                datas.put(entry.getKey().toString(), entry.getValue().getName());
            }
            model.addAttribute("tags", datas);
        }
        AppStoreArticleParameter para = AppStoreArticleParameter.instance(appStoreArticle);
        this.storeCommonParameterService.addBundles("bundleIds", model);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/info/article/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addOrModify(AppStoreArticleParameter param, @RequestParam(required = false) CommonsMultipartFile icon) {
        Integer articleId = param.getId();
        String errMsg = checkArticleParameter(param, icon, articleId == null || articleId.intValue() <= 0);
        if (StringUtils.isEmpty(errMsg)) {
            if (articleId == null) {
                errMsg = addNew(param, icon);
            } else {
                errMsg = editArticle(param, icon);
            }
        }
        return errMsg;
    }

    @RequestMapping(value = "/modifystatus.json")
    @ResponseBody
    public String modifyStatus(int id, Integer status) {
        AppStoreArticle para = new AppStoreArticle();
        para.setId(id);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = iosStoreApiService.modifyAppStoreArticleStatus(para);
        String errMsg = "";
        if (resp == null) {
            errMsg = "未知网络错误";
        }
        if (resp != null && (resp.getData() == null || !resp.getData())) {
            errMsg = resp.getMessage();
        }
        return errMsg;
    }


    @RequestMapping(value = "/recom/modifystatus.json")
    @ResponseBody
    public String modifyRecomStatus(int articleId, String bundleId, Integer status) {
        AppStoreArticleRecomParameter para = new AppStoreArticleRecomParameter();
        para.setBundleId(bundleId);
        para.setArticleId(articleId);
        ApiRespWrapper<Boolean> resp = iosStoreApiService.modifyAppStoreArticleRecomStatus(para);
        String errMsg = "";
        if (resp == null) {
            errMsg = "未知网络错误";
        }
        if (resp != null && (resp.getData() == null || !resp.getData())) {
            errMsg = resp.getMessage();
        }
        return errMsg;
    }

    private String editArticle(AppStoreArticleParameter param, CommonsMultipartFile icon) {
        String errMsg = "";
        UploadFileResp uploadFileResp = null;
        if (StringUtils.isEmpty(errMsg) && icon != null && !icon.isEmpty()) {
            try {
                uploadFileResp = bannerStorageService.saveStoreIcon(icon);
            } catch (Exception e) {
                errMsg = "存储文件失败, Errmsg:" + e.getMessage();
                log.error("存储文件失败", e);
            }
        }
        if (uploadFileResp == null) {
            errMsg = "存储文件失败.";
        }
        if (uploadFileResp != null && !uploadFileResp.isSaved()) {
            errMsg = uploadFileResp.getMessage();
        } else {
            param.setIconUrl(uploadFileResp.getUrl());
        }
        String articleUrl = bannerStorageService.saveStoreArticle(param.getTitle(), param.getContent());
        param.setArticleUrl(articleUrl);
        ApiRespWrapper<Boolean> resp = iosStoreApiService.updateAppStoreArticle(param);
        if (resp == null) {
            errMsg = "未知错误";
        } else if (resp.getData() == null || !resp.getData().booleanValue()) {
            errMsg = resp.getMessage();
        } else {
            errMsg = "";
        }
        return errMsg;
    }

    private String addNew(AppStoreArticleParameter param, CommonsMultipartFile icon) {
        String errMsg = checkArticleParameter(param, icon, true);
        if (StringUtils.isBlank(errMsg)) {
            UploadFileResp uploadFileResp = null;
            if (StringUtils.isEmpty(errMsg) && icon != null && !icon.isEmpty()) {
                try {
                    uploadFileResp = bannerStorageService.saveStoreIcon(icon);
                } catch (Exception e) {
                    errMsg = "存储文件失败, Errmsg:" + e.getMessage();
                    log.error("存储文件失败", e);
                }
            }
            if (uploadFileResp == null) {
                errMsg = "存储文件失败.";
            }
            if (uploadFileResp != null && !uploadFileResp.isSaved()) {
                errMsg = uploadFileResp.getMessage();
            } else {
                param.setIconUrl(uploadFileResp.getUrl());
            }
            String articleUrl = bannerStorageService.saveStoreArticle(param.getTitle(), param.getContent());
            param.setArticleUrl(articleUrl);
            ApiRespWrapper<Boolean> resp = iosStoreApiService.saveAppStoreArticle(param);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getStatus() != 0) {
                errMsg = resp.getMessage();
            } else {
                errMsg = "";
            }
        }
        return errMsg;
    }

    private String checkArticleParameter(AppStoreArticleParameter param, CommonsMultipartFile iconFile, boolean isNew) {
        if (StringUtils.isBlank(param.getContent())) {
            return "文章页面内容为空";
        }
        if (StringUtils.isBlank(param.getTitle())) {
            return "标题为空";
        }
        if (isNew && (iconFile == null || iconFile.getSize() == 0)) {
            return "请提供图标";
        }
        if (StringUtils.isBlank(param.getContentText())) {
            return "文章文本内容为空";
        }
        if (param.getTagId() <= 0) {
            return "请提供标签";
        }
        if (StringUtils.isBlank(param.getShortDesc())) {
            return "请提供简要描述";
        }
        return null;
    }

    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    @ResponseBody
    public ApiRespWrapper<UploadFileResp> uploadimg(String editorid,
            @RequestParam(required = false) CommonsMultipartFile upfile) {
        if (editorid == null || editorid.equals("articlecontainer")) {
            UploadFileResp resp = bannerStorageService.saveStoreContentImg(upfile);
            return new ApiRespWrapper<UploadFileResp>(resp);
        } else {
            log.error("unknown editorid: " + editorid);
            return new ApiRespWrapper<UploadFileResp>(-1, "not support editorid");
        }
    }

    @RequestMapping("/list")
    public String list(AppStoreArticleParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreArticle>> resp = this.iosStoreApiService.listAppStoreArticles(para);
        List<AppStoreArticle> values = null;
        if (resp != null && resp.getData() != null && !CollectionUtils.emptyOrNull(resp.getData().getResultList())) {
            values = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        } else {
            para.getPager().setTotal(0);
        }
        if (!CollectionUtils.emptyOrNull(values)) {
            List<Integer> tagIds = fetchAppStoreArticleTagIds(values);
            ApiRespWrapper<Map<Integer, AppStoreArticleTag>> tagsResp = this.iosStoreApiService
                    .getAppStoreArticleTags(tagIds);

            if (tagsResp != null && tagsResp.getData() != null) {
                Map<String, AppStoreArticleTag> datas = new HashMap<String, AppStoreArticleTag>();
                for (Map.Entry<Integer, AppStoreArticleTag> entry : tagsResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("tags", datas);
            }
            List<Integer> articleIds = new ArrayList<Integer>();
            for (AppStoreArticle value : values) {
                articleIds.add(value.getId());
            }
            // TODO visit
        }
        if (para.getBundleId() != null
                && !StringUtils.equalsIgnoreCase(AppStoreClientInfo.ALL_BUNDLE, para.getBundleId())) {
            AppStoreArticleRecomParameter appStoreArticleRecomParameter = new AppStoreArticleRecomParameter();
            appStoreArticleRecomParameter.setBundleId(para.getBundleId());
            ApiRespWrapper<ListWrapResp<AppStoreArticleRecom>> articleRecomResp = this.iosStoreApiService
                    .listAppStoreArticleRecom(appStoreArticleRecomParameter);
            if (articleRecomResp != null && articleRecomResp.getData() != null
                    && articleRecomResp.getData().getResultList() != null) {
                List<AppStoreArticleRecom> recoms = articleRecomResp.getData().getResultList();
                List<Integer> recomArticleIds = new ArrayList<Integer>();
                for (AppStoreArticleRecom appStoreArticleRecom : recoms) {
                    if (appStoreArticleRecom.getStatus() == AppStoreArticleRecom.STATUS_OK) {
                        recomArticleIds.add(appStoreArticleRecom.getArticleId());
                    }
                }
                model.addAttribute("recomArticleIds", recomArticleIds);
            }
        }
        storeCommonParameterService.addBundles("bundleIds", model);
        model.addAttribute("values", values);
        model.addAttribute("status", AppStoreArticle.STATUS);
        model.addAttribute("para", para);
        return "store/info/article/list.ftl";
    }

    @RequestMapping(value = "/search")
    @ResponseBody
    public JsonResult<AppStoreArticleSimple> search(String id) {
        boolean success = false;
        String message;
        AppStoreArticleSimple ret = null;
        int rid = NumberUtils.toInt(id, -1);
        if (rid == -1) {
            message = "无效的资讯Id";
        } else {
            List<Integer> rootIds = Arrays.asList(rid);
            ApiRespWrapper<Map<Integer, AppStoreArticleSimple>> resp = this.iosStoreApiService
                    .getAppStoreArticleSimple(rootIds);
            if (resp == null || resp.getData() == null || resp.getData().get(rid) == null) {
                message = "无效的资讯Id";
            } else {
                success = true;
                message = "ok";
                ret = resp.getData().get(rid);
            }
        }
        return new JsonResult<AppStoreArticleSimple>(success, message, ret);
    }

    private void addArticleRecomInfos(String bundleId, Integer articleId, String key, Model model) {
        AppStoreArticleRecomParameter appStoreArticleRecomParameter = new AppStoreArticleRecomParameter();
        appStoreArticleRecomParameter.setBundleId(bundleId);
        appStoreArticleRecomParameter.setArticleId(articleId);
        ApiRespWrapper<ListWrapResp<AppStoreArticleRecom>> articleRecomResp = this.iosStoreApiService
                .listAppStoreArticleRecom(appStoreArticleRecomParameter);
        if (articleRecomResp != null && articleRecomResp.getData() != null
                && articleRecomResp.getData().getResultList() != null) {
            List<AppStoreArticleRecom> recoms = articleRecomResp.getData().getResultList();
            List<String> recomBundleIds = new ArrayList<String>();
            for (AppStoreArticleRecom appStoreArticleRecom : recoms) {
                if (appStoreArticleRecom.getStatus() == AppStoreArticleRecom.STATUS_OK) {
                    recomBundleIds.add(appStoreArticleRecom.getBundleId());
                }
            }
            model.addAttribute(key, recomBundleIds);
        }
    }

    private List<Integer> fetchAppStoreArticleTagIds(List<AppStoreArticle> values) {
        Set<Integer> tagIds = new HashSet<Integer>();
        for (AppStoreArticle value : values) {
            tagIds.add(value.getTagId());
        }
        return new ArrayList<Integer>(tagIds);
    }

    @RequestMapping(value = "/recom/intervention/add", method = RequestMethod.POST)
    public String add(String bundleId, Integer articleId, Integer position, Model model, RedirectAttributes rattrs) {
        AppStoreArticleInterventionParameter para = new AppStoreArticleInterventionParameter();
        // rootId exists
        String errMsg = checkAndFormatAddParameter(articleId, position);
        if (StringUtils.isEmpty(errMsg)) {
            para.setBundleId(bundleId);
            para.setArticleId(articleId);
            para.setPosition(position);
            try {
                ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addAppStoreArticleIntervention(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error(
                        "Add promote failed.ArticleId:" + articleId + ", position:" + position + ",errMsg:"
                                + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("bundleId", bundleId);
        return "redirect:/auth/store/article/recom/intervention/list";
    }

    @RequestMapping(value = "/recom/intervention/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(Integer id, Integer status, Integer position) {
        if (id == null || id <= 0) {
            return "id无效.";
        }
        AppStoreArticleInterventionParameter para = new AppStoreArticleInterventionParameter();
        para.setId(id);
        para.setStatus(status);
        para.setPosition(position);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppStoreArticleIntervention(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }


    @RequestMapping("/recom/intervention/list")
    public String list(AppStoreArticleInterventionParameter para, @ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("bundleId") String bundleId, Model model) {
        if (StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreArticleRecomIntervention>> resp = iosStoreApiService
                .listAppStoreArticleIntervention(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppStoreArticleRecomIntervention> values = (resp == null || resp.getData() == null || resp.getData()
                .getResultList() == null) ? null : resp.getData().getResultList();
        if (values != null && !values.isEmpty()) {
            List<Integer> articleIds = initArticleIds(values);
            ApiRespWrapper<Map<Integer, AppStoreArticleSimple>> appSimpleResp = this.iosStoreApiService
                    .getAppStoreArticleSimple(articleIds);
            if (appSimpleResp != null && appSimpleResp.getData() != null) {
                Map<String, AppStoreArticleSimple> datas = new HashMap<String, AppStoreArticleSimple>();
                for (Map.Entry<Integer, AppStoreArticleSimple> entry : appSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("articleSimples", datas);
            }
        }
        this.storeCommonParameterService.addBundles("bundleIds", model);
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/info/articleinv/list.ftl";
    }

    private List<Integer> initArticleIds(List<AppStoreArticleRecomIntervention> values) {
        List<Integer> ret = new ArrayList<Integer>();
        for (AppStoreArticleRecomIntervention intervention : values) {
            ret.add(intervention.getArticleId());
        }
        return ret;
    }

    private String checkAndFormatAddParameter(Integer articleId, Integer position) {
        String errMsg = "";
        if (position == null || position < 0) {
            errMsg = "推荐位置未定，请设置";
        }

        if (StringUtils.isEmpty(errMsg) && (articleId == null || articleId.intValue() <= 0 || !isOnline(articleId))) {
            errMsg = "无法推荐一个未上架的资讯";
        }
        return errMsg;
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, AppStoreArticleSimple>> resp = this.iosStoreApiService
                .getAppStoreArticleSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

}
