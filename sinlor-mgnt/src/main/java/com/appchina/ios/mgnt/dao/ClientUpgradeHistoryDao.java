// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao;

import java.sql.SQLException;
import java.util.List;

import com.appchina.ios.mgnt.dto.ClientUpgradeHistory;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface ClientUpgradeHistoryDao {

    void insertOrUpdate(ClientUpgradeHistory history) throws SQLException;

    long count(String channel, Integer test, Integer status) throws SQLException;

    List<ClientUpgradeHistory> queryList(String channel, Integer test, Integer status, int start, int size)
            throws SQLException;

    void delete(int id) throws SQLException;

    ClientUpgradeHistory queryObject(int id) throws SQLException;

    void updatePublishInfo(ClientUpgradeHistory data) throws SQLException;

}
