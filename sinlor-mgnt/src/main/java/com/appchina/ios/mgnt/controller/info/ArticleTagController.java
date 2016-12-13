package com.appchina.ios.mgnt.controller.info;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appchina.ios.core.dto.info.AppStoreArticle;
import com.appchina.ios.core.dto.info.AppStoreArticleTag;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreArticleTagParameter;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * Created by zhouyanhui on 7/16/15.
 */
@Controller
@RequestMapping(value = "/auth/store/articletag/*")
public class ArticleTagController {
    @Autowired
    private IosStoreApiService iosStoreApiService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "store/info/tag/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addArticle(AppStoreArticleTagParameter param) {
        String errMsg = checkParameterForNew(param);
        if (StringUtils.isBlank(errMsg)) {
            ApiRespWrapper<AppStoreArticleTag> resp = iosStoreApiService.saveAppStoreArticleTag(param);
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

    private String checkParameterForNew(AppStoreArticleTagParameter param) {
        if (StringUtils.isBlank(param.getName())) {
            return "标签名称为空";
        }
        if (StringUtils.isBlank(param.getColor())) {
            return "标签颜色为空";
        }

        return null;
    }


    @RequestMapping("/list")
    public String list(StatusStartSizeParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(AppStoreArticle.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<AppStoreArticleTag>> resp = iosStoreApiService.listAppStoreArticleTags(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<AppStoreArticleTag> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();

        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", AppStoreArticle.STATUS);
        model.addAttribute("para", para);
        return "store/info/tag/list.ftl";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(int id, Integer status, String color, String name) {
        if (id <= 0) {
            return "id无效.";
        }
        ApiRespWrapper<Boolean> resp = null;
        AppStoreArticleTagParameter para = new AppStoreArticleTagParameter();
        para.setId(id);
        if (status != null) {
            para.setStatus(status);
            resp = this.iosStoreApiService.modifyAppStoreArticleTagStatus(para);
        } else if (!StringUtils.isEmpty(color)) {
            para.setColor(color);
            resp = this.iosStoreApiService.modifyAppStoreArticleColor(para);
        } else if (!StringUtils.isEmpty(name)) {
            para.setName(name);
            resp = this.iosStoreApiService.modifyAppStoreArticleTag(para);
        }
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }
}
