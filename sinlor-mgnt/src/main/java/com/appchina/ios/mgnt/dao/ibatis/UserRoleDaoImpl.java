package com.appchina.ios.mgnt.dao.ibatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appchina.ios.core.utils.SQLUtils;
import com.appchina.ios.mgnt.dao.UserRoleDao;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("userRoleDao")
public class UserRoleDaoImpl implements UserRoleDao {
    @Resource(name = "ios-mgnt-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public void insertUserRole(int userId, String authority) throws SQLException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("userId", userId);
        paras.put("authority", authority);
        this.sqlMapClient.insert("insertUserRole", paras);
    }

    @Override
    public List<String> getUserRole(String userName) throws SQLException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("userName", userName);
        List<String> list = SQLUtils.queryList(sqlMapClient, "queryUserRole", paras);
        return list;
    }

}
