package com.appchina.ios.mgnt.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.dto.system.AppleDevice;
import com.appchina.ios.core.dto.system.ClientChannel;
import com.appchina.ios.core.dto.system.ClientChannelType;
import com.appchina.ios.core.dto.system.ClientConf;
import com.appchina.ios.mgnt.controller.model.AppleDeviceParameter;
import com.appchina.ios.mgnt.controller.model.ClientChannelParameter;
import com.appchina.ios.mgnt.controller.model.ClientConfModifyParameter;
import com.appchina.ios.mgnt.controller.model.ClientIpaUploadParameter;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.dto.ClientUpgradeHistory;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.ClientUpgradeHistoryService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.utils.CollectionUtils;

@Controller
@RequestMapping(value = "/auth/client/*")
public class ClientConfController {
    private static final Logger log = Logger.getLogger(ClientConfController.class);
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private ClientUpgradeHistoryService clientUpgradeHistoryService;

    @RequestMapping(value = "/conf")
    public String conf(Model model) {
        ApiRespWrapper<ClientConf> resp = this.backendApiService.getClientConf();
        ClientConf data = resp == null ? null : resp.getData();
        model.addAttribute("data", data);
        return "client/conf.ftl";
    }

    @RequestMapping(value = "/modifyconf")
    @ResponseBody
    public String modifyconf(int id, String type, int value) {
        ClientConfModifyParameter parameter = new ClientConfModifyParameter();
        parameter.setId(id);
        parameter.setType(type);
        parameter.setValue(value);
        try {
            ApiRespWrapper<Boolean> resp = this.backendApiService.modifyClientConf(parameter);
            if (resp == null || resp.getData() == null || !resp.getData()) {
                return resp == null ? "修改客户端缓存刷新时间失败,未知错误." : resp.getMessage();
            }
            return "";
        } catch (Exception e) {
            return "修改客户端缓存刷新时间失败,Errmsg:" + e.getMessage();
        }
    }

    @RequestMapping("/childchannel.json")
    @ResponseBody
    public ApiRespWrapper<List<ClientChannel>> childchannel(String channelId) {
        ClientChannelParameter para = new ClientChannelParameter();
        para.setParentId(Integer.valueOf(channelId));
        ApiRespWrapper<ListWrapResp<ClientChannel>> resp = this.backendApiService.listClientChannel(para);
        if (resp == null || resp.getData() == null) {
            return null;
        }
        return new ApiRespWrapper<List<ClientChannel>>(resp.getData().getResultList());
    }

    @RequestMapping("/topchannel.json")
    @ResponseBody
    public ApiRespWrapper<List<ClientChannel>> topchannel() {
        ApiRespWrapper<ListWrapResp<ClientChannel>> resp = this.backendApiService.listParentClientChannel();
        if (resp == null || resp.getData() == null) {
            return null;
        }
        return new ApiRespWrapper<List<ClientChannel>>(resp.getData().getResultList());
    }

    @RequestMapping(value = "/listchannel")
    public String listchannel(@ModelAttribute("errMsg") String errMsg, @ModelAttribute("parentId") String parentId,
            ClientChannelParameter para, Model model) {
        if (!StringUtils.isEmpty(parentId)) {
            try {
                para.setParentId(Integer.valueOf(parentId));
            } catch (Exception e) {
            }
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<ClientChannel>> parentClientChannelResp = this.backendApiService
                .listParentClientChannel();
        List<ClientChannel> parentValeus = parentClientChannelResp == null || parentClientChannelResp.getData() == null
                ? null : parentClientChannelResp.getData().getResultList();
        Map<String, String> parentChannelIdNames = new LinkedHashMap<String, String>(ClientChannelParameter.ALL_CHANNE);
        if (parentValeus != null) {
            for (ClientChannel parent : parentValeus) {
                parentChannelIdNames.put(String.valueOf(parent.getId()),
                        parent.getChannel() + "-------" + parent.getDesc());
            }
        }
        List<ClientChannel> values = null;
        long total = 0;
        if (para.getParentId() != null && para.getParentId() == ClientChannelParameter.TOP_CHANNEL) {
            if (parentClientChannelResp != null && parentClientChannelResp.getData() != null) {
                values = parentValeus;
                total = values.size();
            }
        } else {
            ApiRespWrapper<ListWrapResp<ClientChannel>> resp = this.backendApiService.listClientChannel(para);
            values = resp == null || resp.getData() == null ? null : resp.getData().getResultList();
            total = resp == null || resp.getData() == null ? 0 : resp.getData().getTotalCount();
        }

        para.getPager().setTotal(total);
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("parentChannelIdNames", parentChannelIdNames);
        if (!StringUtils.isEmpty(errMsg)) {
            model.addAttribute("errMsg", errMsg);
        }
        return "client/channel.ftl";
    }

    @RequestMapping(value = "/addchannel", method = RequestMethod.POST)
    public String addclientchannel(String channel, String desc, Integer parentId, Integer leaf,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(channel)) {
            errMsg = "渠道不能为空.";
        }
        if (StringUtils.isEmpty(errMsg)) {
            ClientChannelParameter para = new ClientChannelParameter();
            para.setChannel(channel);
            para.setDesc(desc);
            if (parentId != null
                    && (parentId.intValue() == ClientChannelParameter.TOP_CHANNEL || parentId.intValue() == ClientChannelType.ALL
                            .getType())) {
                parentId = null;
            }
            para.setParentId(parentId);
            para.setLeaf(leaf);
            try {
                ApiRespWrapper<Boolean> resp = this.backendApiService.addClientChannel(para);
                if (resp == null) {
                    errMsg = "未知错误!";
                } else if (!resp.getData()) {
                    errMsg = resp.getMessage();
                } else {
                    errMsg = "添加成功!";
                }
            } catch (Exception e) {
                log.error("Add client channel failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
                errMsg = "添加失败!";
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        rattrs.addFlashAttribute("parentId", parentId == null ? String.valueOf(ClientChannelParameter.TOP_CHANNEL)
                : parentId.toString());
        return "redirect:/auth/client/listchannel";
    }

    @RequestMapping(value = "/upgrade/uploadhistory")
    public String uploadhistory(ClientIpaUploadParameter para, Model model) {
        if (para == null) {
            para = new ClientIpaUploadParameter();
        }
        para.transfer();
        long totalCount = clientUpgradeHistoryService.count(para.getChannel(), para.getTest(), para.getStatus());
        List<ClientUpgradeHistory> datas = clientUpgradeHistoryService.list(para.getChannel(), para.getTest(),
                para.getStatus(), para.getStart(), para.getSize());
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", datas);
        model.addAttribute("para", para);
        model.addAttribute("status", ClientIpaUploadParameter.ALL_STATUS);
        model.addAttribute("tests", ClientIpaUploadParameter.ALL_TEST);
        return "client/upgrade/uploadhistory.ftl";
    }

    @RequestMapping(value = "/upgrade/detail")
    public String upgradedetail(int id, Model model) {
        ClientUpgradeHistory data = clientUpgradeHistoryService.get(id);
        model.addAttribute("id", id);
        model.addAttribute("data", data);
        return "client/upgrade/detail.ftl";
    }

    @RequestMapping(value = "/upgrade/ipaupload", method = RequestMethod.GET)
    public String ipaupload(@ModelAttribute("errMsg") String errMsg, Model model) {
        // 所有子渠道
        ApiRespWrapper<ListWrapResp<ClientChannel>> resp = this.backendApiService.listLeafClientChannel();
        Map<String, String> leafChannelMap = new HashMap<String, String>();
        if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
            for (ClientChannel clientChanne : resp.getData().getResultList()) {
                leafChannelMap.put(Integer.toString(clientChanne.getId()), clientChanne.getChannel());
            }
        }
        int testIpas = clientUpgradeHistoryService.getTestIpaCount();
        int productIpas = clientUpgradeHistoryService.getProductIpaCount();
        model.addAttribute("channels", leafChannelMap);
        model.addAttribute("productIpas", productIpas);
        model.addAttribute("testIpas", testIpas);
        model.addAttribute("errMsg", errMsg);
        // model.ad
        return "client/upgrade/ipaupload.ftl";
    }

    @RequestMapping(value = "/upgrade/ipaupload", method = RequestMethod.POST)
    public String ipauploadpost(String upgradeInfo, Integer test, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(upgradeInfo)) {
            errMsg = "更新说明不能为空.";
            rattrs.addAttribute("errMsg", errMsg);
            return "redirect:/auth/client/upgrade/ipaupload";
        }
        if (StringUtils.isEmpty(errMsg)) {
            clientUpgradeHistoryService.saveIpaFile(upgradeInfo, test);
        }
        return "redirect:/auth/client/upgrade/uploadhistory";
    }

    @RequestMapping(value = "/upgrade/delclientuploadinfo")
    @ResponseBody
    public String delclientuploadinfo(int id) {
        if (id <= 0) {
            return "无效的Id.";
        }
        try {
            this.clientUpgradeHistoryService.delete(id);
            return "";
        } catch (Exception e) {
            log.error("Delete client upload info failed.Data:" + id + ",errMsg:" + e.getMessage(), e);
            return "添加失败";
        }
    }


    @RequestMapping(value = "/apple/device/color/list.json")
    @ResponseBody
    protected JsonResult<List<String>> listAppleDeviceColor(String platform) {
        AppleDeviceParameter para = new AppleDeviceParameter();
        para.setPlatform(platform);
        ApiRespWrapper<ListWrapResp<AppleDevice>> resp = backendApiService.listAppleDevice(para);
        if (resp != null && resp.getData() != null && !CollectionUtils.emptyOrNull(resp.getData().getResultList())) {
            List<AppleDevice> datas = resp.getData().getResultList();
            List<String> colors = new ArrayList<String>();
            for (AppleDevice appleDevice : datas) {
                if (appleDevice.getStatus() == AppleDevice.STATUS_OK) {
                    colors.add(appleDevice.getColor());
                }
            }
            return new JsonResult<List<String>>(true, "", colors);
        }
        return new JsonResult<List<String>>(true, "", new ArrayList<String>());
    }

}
