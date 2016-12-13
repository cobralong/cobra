package com.appchina.ios.mgnt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.model.MgntMenuWebModel;
import com.appchina.ios.mgnt.service.MgntMenuService;

@Controller
@RequestMapping(value = "/auth/menu/*")
public class MgntMenuController {
    @Autowired
    private MgntMenuService mgntMenuService;

    @RequestMapping("/allMenuByName")
    @ResponseBody
    public JsonResult<List<MgntMenuWebModel>> allMenuByName(String name) {
        return new JsonResult<List<MgntMenuWebModel>>(true, "success", mgntMenuService.menus());
    }

    @RequestMapping("/allStoreMenuByName")
    @ResponseBody
    public JsonResult<List<MgntMenuWebModel>> allStoreMenuByName(String name) {
        return new JsonResult<List<MgntMenuWebModel>>(true, "success", mgntMenuService.storeMenus());
    }
}
