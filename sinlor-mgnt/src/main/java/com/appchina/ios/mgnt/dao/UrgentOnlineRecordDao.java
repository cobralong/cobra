// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao;

import java.sql.SQLException;
import java.util.List;

import com.appchina.ios.core.dto.app.UrgentOnlineRecord;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface UrgentOnlineRecordDao {

    void insertOrIgnore(UrgentOnlineRecord record) throws SQLException;

    List<UrgentOnlineRecord> queryList(Integer itemId, int start, int size) throws SQLException;

    long count() throws SQLException;

    void update(List<UrgentOnlineRecord> records) throws SQLException;

    List<Integer> queryRootIds(int start, int size) throws SQLException;

    List<UrgentOnlineRecord> queryList(int endId, int size) throws SQLException;

}
