package com.appchina.ios.mgnt.controller;

import com.appchina.ios.core.dto.system.SystemEmergency;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by zhouyanhui on 9/11/15.
 */
@Controller
@RequestMapping(value = "/auth/emergency/*")
public class SystemEmergencyController {
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewEmergency(Model model, @ModelAttribute("errMsg") String errMsg) {
        ApiRespWrapper<ListWrapResp<SystemEmergency>> resp = backendApiService.viewSystemEmergency();
        List<SystemEmergency> values = null;
        if (resp == null || resp.getData() == null) {
            errMsg = "未知错误";
        } else if (resp.getStatus() != 0) {
            errMsg = resp.getMessage();
        } else {
            values = resp.getData().getResultList();
        }

        model.addAttribute("values", values);
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("status", SystemEmergency.STATUS);
        return "system/emergency/list.ftl";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEmergency(SystemEmergency systemEmergency, RedirectAttributes rattrs) {
        ApiRespWrapper<Boolean> resp = backendApiService.saveSystemEmergency(systemEmergency);
        String errMsg = "";
        if (resp == null) {
            errMsg = "未知错误";
        } else if (resp.getStatus() != 0 || !resp.getData()) {
            errMsg = resp.getMessage();
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/emergency/view";
    }

    @RequestMapping(value = "/add")
    public String addEmergency(Model model) {
        model.addAttribute("status", SystemEmergency.STATUS);
        return "system/emergency/emergency.ftl";
    }

    @RequestMapping(value = "/detail")
    public String saveEmergency(Integer id, Model model) {
        ApiRespWrapper<SystemEmergency> resp = backendApiService.getSystemEmergencyById(id);
        String errMsg = "";
        SystemEmergency value = null;
        if (resp == null || resp.getData() == null) {
            errMsg = "未知错误";
        } else if (resp.getStatus() != 0) {
            errMsg = resp.getMessage();
        } else {
            value = resp.getData();
        }

        model.addAttribute("data", value);
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("status", SystemEmergency.STATUS);
        return "system/emergency/emergency.ftl";
    }
}
