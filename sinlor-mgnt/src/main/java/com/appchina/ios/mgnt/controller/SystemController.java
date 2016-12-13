package com.appchina.ios.mgnt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appchina.ios.mgnt.dto.User;
import com.appchina.ios.mgnt.service.UserRoleService;
import com.appchina.ios.mgnt.service.UsersService;
import com.appchina.ios.web.exception.ServiceException;

@Controller
@RequestMapping(value = "/auth/system/*")
public class SystemController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser(Model model) {
        boolean isAdminUser = this.isAdminUser();
        model.addAttribute("isAdminUser", isAdminUser);
        return "system/addUser.ftl";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String register(@RequestParam String userName, @RequestParam String password,
            @RequestParam String confirmPassword, Model model) {
        StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        Map<String, Object> requestParameters = new HashMap<String, Object>();
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            requestParameters.put("message", "emptyInfo");
        } else if (!StringUtils.equals(password, confirmPassword)) {
            requestParameters.put("message", "differPassword");
        } else {
            int userId = usersService.addUser(userName, passwordEncoder.encode(password));
            userRoleService.addUserRole(userId);
            requestParameters.put("message", "addSucceed");
        }

        boolean isAdminUser = true;
        model.addAttribute("isAdminUser", isAdminUser);
        model.addAttribute("requestParameters", requestParameters);
        return "system/addUser.ftl";
    }

    @RequestMapping(value = "/showUserList", method = RequestMethod.GET)
    public String showUsers(Model model) {
        int adminUserId;
        boolean isAdminUser = this.isAdminUser();
        String message = null;
        List<User> list = usersService.getUserList();
        if (list.size() != 0) {
            adminUserId = usersService.getAdminUserId();
            message = "succeed";
            model.addAttribute("adminUserId", adminUserId);
            model.addAttribute("userList", list);
        } else {
            message = "empty";
        }
        model.addAttribute("message", message);
        model.addAttribute("isAdminUser", isAdminUser);
        return "system/showUserList.ftl";
    }

    @RequestMapping(value = "/recoverPassword", method = RequestMethod.GET)
    public String recoverPassword(Model model) {
        boolean isAdminUser = this.isAdminUser();
        model.addAttribute("isAdminUser", isAdminUser);
        return "system/recoverPassword.ftl";
    }

    @RequestMapping(value = "/recoverPassword", method = RequestMethod.POST)
    public String recoverPasswordPost(@RequestParam String userName, Model model) {
        Map<String, Object> requestParameters = new HashMap<String, Object>();
        if (userName.length() == 0) {
            requestParameters.put("message", "emptyInput");
        } else {
            // 此处判断是否存在用户，复用获取用户密码方法。
            String userPassword = usersService.getUserOldPwd(userName);
            if (userPassword != null) {
                StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
                usersService.resetPassword(userName, passwordEncoder.encode("123456"));
                requestParameters.put("message", "recoverSucceed");
            } else {
                requestParameters.put("message", "notExist");
            }
        }
        boolean isAdminUser = true;
        model.addAttribute("isAdminUser", isAdminUser);
        model.addAttribute("requestParameters", requestParameters);
        return "system/recoverPassword.ftl";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public String deleteUser(@RequestParam int id) {
        String msg = null;
        try {
            msg = "true";
            usersService.deleteUser(id);
        } catch (ServiceException e) {
            msg = "error";
        }
        return msg;
    }

    private boolean isAdminUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> userRoleList = userRoleService.getUserRole(userDetails.getUsername());
        boolean isAdminUser = false;
        for (int i = 0; i < userRoleList.size(); i++) {
            String type = userRoleList.get(i);
            if (StringUtils.equals(type, "ADMIN_USER")) {
                isAdminUser = true;
                break;
            }
        }
        return isAdminUser;
    }
}
