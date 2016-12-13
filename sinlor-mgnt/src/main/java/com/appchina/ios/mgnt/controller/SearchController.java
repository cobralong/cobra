package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.crawler.model.AuthoAppDownBoughtInfoResp;
import com.appchina.ios.core.dto.app.Application;
import com.appchina.ios.core.dto.app.ProductPageDictionary;
import com.appchina.ios.core.dto.app.SearchDefaultResult;
import com.appchina.ios.core.dto.app.SearchHotword;
import com.appchina.ios.core.dto.app.SearchPlaceholder;
import com.appchina.ios.core.utils.ChannelUtils;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.ProductPageDictionaryParameter;
import com.appchina.ios.mgnt.controller.model.PromoteAppsParameter;
import com.appchina.ios.mgnt.controller.model.SearchDefaultResultParameter;
import com.appchina.ios.mgnt.controller.model.SearchHotwordParameter;
import com.appchina.ios.mgnt.controller.model.SearchPlaceholderParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping(value = "/auth/search/*")
public class SearchController {
    private static final Logger log = Logger.getLogger(SearchController.class);
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/search")
    @ResponseBody
    public JsonResult<Application> search(String id) {
        boolean success = false;
        String message;
        Application ret = null;
        int rid = NumberUtils.toInt(id, -1);
        if (rid == -1) {
            message = "无效的根Id";
        } else {
            List<Integer> rootIds = Arrays.asList(rid);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> resp = this.backendApiService.getAppSimple(rootIds);
            if (resp == null || resp.getData() == null || resp.getData().get(rid) == null) {
                message = "无效的根Id";
            } else {
                success = true;
                message = "ok";
                ret = new Application();
                ret.setId(rid);
                ret.setTitle(resp.getData().get(rid).getName());
            }
        }
        return new JsonResult<Application>(success, message, ret);
    }

    @RequestMapping(value = "/hotword/list")
    public String hotwordlist(SearchHotwordParameter para, @ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("channel") String channel, Model model) {
        if (para == null) {
            para = new SearchHotwordParameter();
        }
        if (!StringUtils.isEmpty(errMsg)) {
            para.setErrMsg(errMsg);
        }
        if (!StringUtils.isEmpty(channel) && !"All".equalsIgnoreCase(channel)) {
            para.setChannel(channel);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<SearchHotword>> resp = backendApiService.listHotwords(para.getName(),
                para.getChannel(), para.getStatus(), para.getStart(), para.getSize());
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<SearchHotword> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("channels", PromoteAppsParameter.CHANNELS);
        model.addAttribute("allChannels", PromoteAppsParameter.ALL_CHANNELS);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        return "search/hotwords.ftl";
    }


    @RequestMapping(value = "/hotword/add")
    public String hotwordadd(String name, int rank, String channel, Model model, RedirectAttributes rattrs) {
        // rootId exists
        rattrs.addFlashAttribute("channel", channel);
        String errMsg = "";
        if (!ChannelUtils.legalChannel(channel)) {
            errMsg = "未知渠道";
        }
        if (StringUtils.isEmpty(errMsg)) {
            if (rank <= 0) {
                errMsg = "Rank应该大于0";
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addSearchHotword(name, rank, channel);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add search hotwords failed.Name:" + name + ", channel:" + channel, e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/search/hotword/list";
    }

    @RequestMapping(value = "/hotword/modify")
    @ResponseBody
    public String hotwordmodify(int id, int status, Model model) {
        ApiRespWrapper<Boolean> resp = backendApiService.modifySearchHotword(id, status);
        if (resp == null || resp.getData() == null || !resp.getData()) {
            return resp == null ? "未知错误." : resp.getMessage();
        }
        return "";
    }


    @RequestMapping(value = "/defaultresult/list")
    public String defaultresultlist(SearchDefaultResultParameter para, @ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("channel") String channel, Model model) {
        if (para == null) {
            para = new SearchDefaultResultParameter();
        }
        if (!StringUtils.isEmpty(errMsg)) {
            para.setErrMsg(errMsg);
        }
        if (!StringUtils.isEmpty(channel) && !"All".equalsIgnoreCase(channel)) {
            para.setChannel(channel);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<SearchDefaultResult>> resp = backendApiService.listSearchDefaultResult(
                para.getChannel(), para.getStatus(), para.getStart(), para.getSize());
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<SearchDefaultResult> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        Map<String, AuthoAppDownBoughtInfoResp> statusRespMaps = null;
        List<AuthoAppDownBoughtInfoResp> statusResps = null;
        if (values != null && !values.isEmpty()) {
            List<Integer> rootIds = initRootIds(values);
            ApiRespWrapper<Map<Integer, ApplicationSimple>> appSimpleResp = this.backendApiService
                    .getAppSimple(rootIds);
            // TODO
            ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> infoStatusList = this.backendApiService
                    .getAuthorizerAppDownAndBuyInfoStatusList(rootIds);
            if (appSimpleResp != null && appSimpleResp.getData() != null) {
                Map<String, ApplicationSimple> datas = new HashMap<String, ApplicationSimple>();
                for (Entry<Integer, ApplicationSimple> entry : appSimpleResp.getData().entrySet()) {
                    datas.put(entry.getKey().toString(), entry.getValue());
                }
                model.addAttribute("rootSimples", datas);
            }
            if (infoStatusList != null && infoStatusList.getData() != null
                    && infoStatusList.getData().getResultList() != null) {
                statusRespMaps = new HashMap<String, AuthoAppDownBoughtInfoResp>();
                statusResps = infoStatusList.getData().getResultList();
                for (AuthoAppDownBoughtInfoResp resps : statusResps) {
                    statusRespMaps.put(String.valueOf(resps.getRootId()), resps);
                }
            }
        }

        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("statusRespMaps", statusRespMaps);
        model.addAttribute("para", para);
        model.addAttribute("channels", PromoteAppsParameter.CHANNELS);
        model.addAttribute("allChannels", PromoteAppsParameter.ALL_CHANNELS);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        return "search/defaultresults.ftl";
    }


    private List<Integer> initRootIds(List<SearchDefaultResult> values) {
        List<Integer> ret = new ArrayList<Integer>();
        for (SearchDefaultResult searchDefaultResult : values) {
            ret.add(searchDefaultResult.getRootId());
        }
        return ret;
    }

    @RequestMapping(value = "/defaultresult/add")
    public String defaultresultadd(int rootId, int rank, String channel, Model model, RedirectAttributes rattrs) {
        // rootId exists
        rattrs.addFlashAttribute("channel", channel);
        String errMsg = "";
        if (!ChannelUtils.legalChannel(channel)) {
            errMsg = "未知渠道";
        }
        if (StringUtils.isEmpty(errMsg)) {
            if (rank <= 0) {
                errMsg = "Rank应该大于0";
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addSearchDefaultResult(rootId, rank, channel);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add search defaultresults failed.RootId:" + rootId + ", channel:" + channel, e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/search/defaultresult/list";
    }

    @RequestMapping(value = "/defaultresult/modify")
    @ResponseBody
    public String defaultresultmodify(int id, int status, Model model) {
        ApiRespWrapper<Boolean> resp = backendApiService.modifySearchDefaultResult(id, status);
        if (resp == null || resp.getData() == null || !resp.getData()) {
            return resp == null ? "未知错误." : resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/placeholder/modify")
    @ResponseBody
    public String placeholdermodify(int id, int status, Model model) {
        ApiRespWrapper<Boolean> resp = backendApiService.modifySearchPlaceholder(id, status);
        if (resp == null || resp.getData() == null || !resp.getData()) {
            return resp == null ? "未知错误." : resp.getMessage();
        }
        return "";
    }


    @RequestMapping(value = "/placeholder/list")
    public String placeholderlist(SearchPlaceholderParameter para, @ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("channel") String channel, Model model) {
        if (para == null) {
            para = new SearchPlaceholderParameter();
        }
        if (!StringUtils.isEmpty(errMsg)) {
            para.setErrMsg(errMsg);
        }
        if (!StringUtils.isEmpty(channel) && !"All".equalsIgnoreCase(channel)) {
            para.setChannel(channel);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<SearchPlaceholder>> resp = backendApiService.listPlaceholders(para.getChannel(),
                para.getStatus(), para.getStart(), para.getSize());
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<SearchPlaceholder> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        ApiRespWrapper<Map<String, String>> pageDescResp = this.backendApiService.mapProductChannelPageDictionary(para
                .getChannel());
        if (pageDescResp != null && pageDescResp.getData() != null) {
            model.addAttribute("pageDescMap", pageDescResp.getData());
        }
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("channels", PromoteAppsParameter.CHANNELS);
        model.addAttribute("allChannels", PromoteAppsParameter.ALL_CHANNELS);
        model.addAttribute("status", PromoteAppsParameter.STATUS);
        return "search/placeholders.ftl";
    }


    @RequestMapping(value = "/placeholder/add")
    public String placeholderadd(String channel, int page, String name, Model model, RedirectAttributes rattrs) {
        // rootId exists
        rattrs.addFlashAttribute("channel", channel);
        String errMsg = "";
        if (!ChannelUtils.legalChannel(channel)) {
            errMsg = "未知渠道";
        }
        if (StringUtils.isEmpty(errMsg)) {
            if (page < 0) {
                errMsg = "page应该大于0";
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addSearchPlaceholder(name, channel, page);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add search placeholder failed.Page:" + page + ", channel:" + channel + ", name:" + name
                        + ", errMsg:" + errMsg, e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/search/placeholder/list";
    }


    @RequestMapping(value = "/pagedictionary/list")
    public String pagedictionarylist(ProductPageDictionaryParameter para, @ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("channel") String channel, Model model) {
        if (para == null) {
            para = new ProductPageDictionaryParameter();
        }
        if (!StringUtils.isEmpty(errMsg)) {
            para.setErrMsg(errMsg);
        }
        if (!StringUtils.isEmpty(channel) && !"All".equalsIgnoreCase(channel)) {
            para.setChannel(channel);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<ProductPageDictionary>> resp = backendApiService.listProductPageDictionary(para
                .getChannel());
        List<ProductPageDictionary> values = (resp == null || resp.getData() == null || resp.getData().getResultList() == null)
                ? null : resp.getData().getResultList();
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("channels", PromoteAppsParameter.CHANNELS);
        model.addAttribute("allChannels", PromoteAppsParameter.ALL_CHANNELS);
        return "search/pagedictionarys.ftl";
    }

    @RequestMapping(value = "/pagedictionary/add")
    public String pagedictionaryadd(int page, String desc, String channel, Model model, RedirectAttributes rattrs) {
        // rootId exists
        rattrs.addFlashAttribute("channel", channel);
        String errMsg = "";
        if (!ChannelUtils.legalChannel(channel)) {
            errMsg = "未知渠道";
        }
        if (StringUtils.isEmpty(errMsg)) {
            if (page < 0) {
                errMsg = "页面索引应该大于0";
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addProductPageDictionary(channel, page, desc);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            } catch (Exception e) {
                log.error("Add product page dictionary failed.Channel:" + channel + ", page:" + page + ", desc:" + desc
                        + ", errMsg:" + e.getMessage(), e);
                errMsg = "添加失败";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/search/pagedictionary/list";
    }
}
