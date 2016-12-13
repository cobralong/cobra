package com.appchina.ios.mgnt.controller;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.dto.app.RootApplicationTag;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * Created by zhouyanhui on 11/6/15.
 */
@Controller
@RequestMapping(value = "/auth/rootapptag/*")
public class RootApplicationTagController {
    private static final Logger log = Logger.getLogger(RootApplicationTagController.class);
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "redirect:/auth/rootapptag/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Integer rootId, String tag, Float score, Model model,
                      RedirectAttributes rattrs) {
        // rootId exists
        String errMsg = checkAndFormatParameter(rootId, tag, score);
        if (StringUtils.isEmpty(errMsg)) {
            try {
                RootApplicationTag rootApplicationTag = new RootApplicationTag();
                rootApplicationTag.setRootId(rootId);
                rootApplicationTag.setTag(tag);
                rootApplicationTag.setScore(score);
                ApiRespWrapper<Boolean> resp = this.backendApiService.addRootApplicationtag(rootApplicationTag);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add RootApplicationTag failed.RootId:" + rootId + ", tag:" + tag + ", score:" + score
                        + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/rootapptag/list";
    }

    private String checkAndFormatParameter(Integer rootId, String tag, Float score) {
        String errMsg = "";
        if (score == null || score < 0) {
            errMsg = "得分未设置，请设置";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isBlank(tag)) {
            errMsg = "未设置tag";
        }
        if (StringUtils.isEmpty(errMsg) && (rootId == null || rootId.intValue() <= 0 || !isOnline(rootId))) {
            errMsg = "未上架的应用";
        }
        return errMsg;
    }

    private boolean isOnline(int rid) {
        List<Integer> rootIds = Arrays.asList(rid);
        ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
        return resp != null && resp.getData() != null && resp.getData().get(rid) != null;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(Integer id, String tag, Integer status, Float score) {
        if (id == null || id.intValue() <= 0) {
            return "id无效.";
        }
        RootApplicationTag para = new RootApplicationTag();
        para.setId(id.intValue());
        if(status != null) {
            para.setStatus(status);
        }
        if(StringUtils.isNotBlank(tag)) {
            para.setTag(tag);
        }
        if(score != null) {
            para.setScore(score);
        }
        ApiRespWrapper<Boolean> resp = this.backendApiService.modifyRootApplicationTag(para);
        if (resp == null) {
            return "未知错误.";
        }
        if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/list")
    public String list(StatusStartSizeParameter para, Model model) {
        if (para == null) {
            para = new StatusStartSizeParameter();
        }
        para.transfer();
        if(para.getStatus() == null){
            para.setStatus(DbStatus.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<RootApplicationTag>> valueResp = backendApiService.listRootApplicationTags(para);
        List<RootApplicationTag> values = null;
        Map<String, ApplicationSimple> rootSimples = null;
        if (valueResp != null && valueResp.getData() != null && valueResp.getData().getResultList() != null) {
            para.getPager().setTotal(valueResp.getData().getTotalCount());
            values = valueResp.getData().getResultList();
            List<Integer> rootIds = initRootIds(valueResp.getData().getResultList());
            ApiRespWrapper<Map<Integer, ApplicationSimple>> simpleResp = this.backendApiService.getAppSimple(rootIds);
            if (simpleResp != null && simpleResp.getData() != null) {
                rootSimples = new HashMap<String, ApplicationSimple>();
                for (Map.Entry<Integer, ApplicationSimple> entry : simpleResp.getData().entrySet()) {
                    rootSimples.put(entry.getKey().toString(), entry.getValue());
                }
            }
        }
        model.addAttribute("values", values);
        model.addAttribute("rootSimples", rootSimples);
        model.addAttribute("status", DbStatus.STATUS);
        model.addAttribute("para", para);
        return "rootapptag/list.ftl";
    }

    private List<Integer> initRootIds(List<RootApplicationTag> list) {
        List<Integer> ret = new ArrayList<Integer>();
        for (RootApplicationTag rootApplicationTag : list) {
            ret.add(rootApplicationTag.getRootId());
        }
        return ret;
    }
}
