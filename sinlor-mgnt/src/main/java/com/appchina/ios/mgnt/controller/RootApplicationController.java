package com.appchina.ios.mgnt.controller;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appchina.ios.core.dto.app.RootApplication;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;

/**
 * Created by zhouyanhui on 11/6/15.
 */
@Controller
@RequestMapping(value = "/auth/root/application/*")
public class RootApplicationController {
    @Autowired
    private BackendApiService backendApiService;

    @RequestMapping(value = "/search")
    @ResponseBody
    public JsonResult<RootApplication> search(String id) {
        boolean success = false;
        String message;
        RootApplication ret = null;
        int rid = NumberUtils.toInt(id, -1);
        if (rid == -1) {
            message = "无效的根Id";
        } else {
            ApiRespWrapper<RootApplication> resp = this.backendApiService.getRootApplication(rid);
            if (resp == null || resp.getData() == null) {
                message = "无效的根Id";
            } else {
                success = true;
                message = "ok";
                ret = resp.getData();
            }
        }
        return new JsonResult<RootApplication>(success, message, ret);
    }

}
