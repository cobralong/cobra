// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import java.util.List;

import com.appchina.ios.mgnt.dto.User;
import com.appchina.ios.web.exception.ServiceException;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface UsersService {

    void resetPassword(String username, String newPwdEncode) throws ServiceException;

    String getUserOldPwd(String username) throws ServiceException;

    User getUser(String username) throws ServiceException;

    int addUser(String userName, String password) throws ServiceException;

    List<User> getUserList() throws ServiceException;

    int getAdminUserId() throws ServiceException;

    void deleteUser(int userId) throws ServiceException;
}
