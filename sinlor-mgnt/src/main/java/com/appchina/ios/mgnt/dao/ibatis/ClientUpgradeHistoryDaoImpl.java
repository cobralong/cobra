// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao.ibatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.appchina.ios.core.utils.SQLUtils;
import com.appchina.ios.mgnt.dao.ClientUpgradeHistoryDao;
import com.appchina.ios.mgnt.dto.ClientUpgradeHistory;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Repository("clientUpgradeHistoryDao")
public class ClientUpgradeHistoryDaoImpl implements ClientUpgradeHistoryDao {
    @Resource(name = "ios-mgnt-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public void insertOrUpdate(ClientUpgradeHistory history) throws SQLException {
        SQLUtils.insertOrUpdate(sqlMapClient, "insertOrUpdateClientUpgradeHistory", history);
    }

    @Override
    public long count(String channel, Integer test, Integer status) throws SQLException {
        Map<String, Object> para = initPara(channel, test, status);
        return SQLUtils.count(sqlMapClient, "countClientUpgradeHistory", para);
    }

    @Override
    public List<ClientUpgradeHistory> queryList(String channel, Integer test, Integer status, int start, int size)
            throws SQLException {
        Map<String, Object> para = SQLUtils.buildStartSizeMap(start, size);
        para.putAll(initPara(channel, test, status));
        return SQLUtils.queryList(sqlMapClient, "queryClientUpgradeHistoryList", para);
    }

    private Map<String, Object> initPara(String channel, Integer test, Integer status) {
        Map<String, Object> para = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(channel)) {
            para.put("ipaChannel", channel);
        }
        if (test != null) {
            para.put("test", test);
        }
        if (status != null) {
            para.put("status", status);
        }
        return para;
    }

    @Override
    public void delete(int id) throws SQLException {
        SQLUtils.insertOrUpdate(sqlMapClient, "deleteClientUpgradeHistoryById", id);
    }

    @Override
    public ClientUpgradeHistory queryObject(int id) throws SQLException {
        return SQLUtils.queryObject(sqlMapClient, "queryClientUpgradeHistoryObject", id);
    }

    @Override
    public void updatePublishInfo(ClientUpgradeHistory data) throws SQLException {
        SQLUtils.insertOrUpdate(sqlMapClient, "updateClientUpgradeHistoryPublishInfo", data);
    }

}
