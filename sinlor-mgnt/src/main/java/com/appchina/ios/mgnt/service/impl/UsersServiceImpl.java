// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appchina.ios.mgnt.dao.UsersDao;
import com.appchina.ios.mgnt.dto.User;
import com.appchina.ios.mgnt.service.UsersService;
import com.appchina.ios.web.exception.ServiceException;

/**
 * will remove to ios-account project
 *
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;

    @Override
    public String getUserOldPwd(String username) throws ServiceException {
        try {
            return this.usersDao.queryUserPwd(username);
        } catch (SQLException e) {
            throw ServiceException.getSQLException("更新密码时发生数据库错误.");
        }
    }

    @Override
    public void resetPassword(String username, String newPwdEncode) throws ServiceException {
        try {
            usersDao.updatePwd(username, newPwdEncode);
        } catch (SQLException e) {
            throw ServiceException.getSQLException("更新密码时发生数据库错误.");
        }
    }

    @Override
    public int addUser(String userName, String password) throws ServiceException {
        try {
            return usersDao.addUser(userName, password);
        } catch (SQLException e) {
            throw ServiceException.getSQLException("添加用户时发生数据库错误.");
        } catch (Exception ee) {
            throw ServiceException.getSQLException("添加用户时发生数据库错误.");
        }

    }

    @Override
    public List<User> getUserList() throws ServiceException {
        try {
            return usersDao.getUserList();
        } catch (SQLException e) {
            throw ServiceException.getSQLException("获取用户列表时发生数据库错误.");
        }
    }

    @Override
    public int getAdminUserId() throws ServiceException {
        try {
            return usersDao.getAdminUserId();
        } catch (SQLException e) {
            throw ServiceException.getSQLException("获取管理员ID时发生数据库错误.");
        }
    }

    @Override
    public void deleteUser(int userId) throws ServiceException {
        // TODO Auto-generated method stub
        try {
            usersDao.deleteUser(userId);
        } catch (SQLException e) {
            throw ServiceException.getSQLException("删除用户时发生数据库错误.");
        }
    }

    @Override
    public User getUser(String userName) throws ServiceException {
        try {
            return usersDao.queryObject(userName);
        } catch (SQLException e) {
            throw ServiceException.getSQLException("获取用户时发生数据库错误.");
        }
    }
}
