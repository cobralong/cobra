// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao;

import java.sql.SQLException;
import java.util.List;

import com.appchina.ios.mgnt.dto.User;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface UsersDao {

    void updatePwd(String username, String newPwdEncode) throws SQLException;

    String queryUserPwd(String username) throws SQLException;

    int addUser(String userName, String password) throws SQLException;

    List<User> getUserList() throws SQLException;

    int getAdminUserId() throws SQLException;

    void deleteUser(int userId) throws SQLException;

    User queryObject(String userName) throws SQLException;
}
