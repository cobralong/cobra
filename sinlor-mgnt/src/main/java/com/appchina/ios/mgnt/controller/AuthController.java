package com.appchina.ios.mgnt.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.mgnt.service.UsersService;
import com.appchina.ios.web.exception.ServiceException;

@Controller
@RequestMapping(value = "/auth/*")
public class AuthController {
    private static final Logger log = Logger.getLogger(AuthController.class);
    @Autowired
    private UsersService usersService;

    @RequestMapping("/login")
    public String login() {
        return "auth/login.ftl";
    }


    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String reset() {
        return "auth/reset.ftl";
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String reset(@RequestParam String oldPassword, @RequestParam String newPassword,
            @RequestParam String confirmPassword, Model model, RedirectAttributes redirectAttributes) {
        log.info("Start to reset the password!");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        String message = null;
        if (StringUtils.isBlank(oldPassword)) {
            message = "原密码不能为空！";
        } else if (StringUtils.isBlank(newPassword)) {
            message = "新密码不能为空！";
        } else if (!newPassword.equals(confirmPassword)) {
            message = "新密码两次输入的不同！";
        } else {
            String oldPwd = this.usersService.getUserOldPwd(userDetails.getUsername());
            if (passwordEncoder.matches(oldPassword, oldPwd)) {
                try {
                    usersService.resetPassword(userDetails.getUsername(), passwordEncoder.encode(newPassword));
                    return "redirect:/auth/login";
                } catch (ServiceException e) {
                    message = "修改密码失败，请重试";
                }
            } else {
                message = "原密码输入错误,请重试";
            }
        }

        model.addAttribute("message", message);
        return "auth/reset.ftl";
    }

}
