// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao.ibatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appchina.ios.core.utils.SQLUtils;
import com.appchina.ios.mgnt.dao.UsersDao;
import com.appchina.ios.mgnt.dto.User;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Repository("usersDao")
public class UsersDaoImpl implements UsersDao {
    @Resource(name = "ios-mgnt-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public void updatePwd(String username, String newPwdEncode) throws SQLException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("username", username);
        paras.put("newPwdEncode", newPwdEncode);
        this.sqlMapClient.update("updateUserPwd", paras);
    }

    @Override
    public String queryUserPwd(String username) throws SQLException {
        return (String) this.sqlMapClient.queryForObject("queryUserPwd", username);
    }

    @Override
    public int addUser(String userName, String password) throws SQLException {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put("username", userName);
        paras.put("password", password);
        return (int) this.sqlMapClient.insert("insertUser", paras);
    }

    @Override
    public List<User> getUserList() throws SQLException {
        return SQLUtils.queryList(sqlMapClient, "queryUserList", null);
    }

    @Override
    public int getAdminUserId() throws SQLException {
        String authority = "ADMIN_USER";
        return (int) this.sqlMapClient.queryForObject("queryAdminUserId", authority);
    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        this.sqlMapClient.delete("deleteUser", userId);
    }

    @Override
    public User queryObject(String userName) throws SQLException {
        return SQLUtils.queryObject(sqlMapClient, "queryUserObjectByUserName", userName);
    }

}
