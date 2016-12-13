package com.appchina.ios.mgnt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/*")
public class IndexController {
    @RequestMapping(value = "index")
    public String execute(HttpServletResponse response) {
        // 为了在跳转中保持用户名显示，将用户名设置到session范围内
        Cookie cookie = new Cookie("manage_username", "Hello");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        return "index_choose.ftl";
    }

    @RequestMapping(value = "index/enterprise")
    public String enterprise(HttpServletResponse response) {
        // 为了在跳转中保持用户名显示，将用户名设置到session范围内
        Cookie cookie = new Cookie("manage_username", "Hello");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        return "index.ftl";
    }

    @RequestMapping(value = "index/appstore")
    public String appstore(HttpServletResponse response) {
        // 为了在跳转中保持用户名显示，将用户名设置到session范围内
        Cookie cookie = new Cookie("manage_username", "Hello");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        return "store_index.ftl";
    }
}
