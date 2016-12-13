// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao.ibatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appchina.ios.core.dto.app.UrgentOnlineRecord;
import com.appchina.ios.core.utils.SQLUtils;
import com.appchina.ios.mgnt.dao.UrgentOnlineRecordDao;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Repository("urgentOnlineRecordDao")
public class UrgentOnlineRecordDaoImpl implements UrgentOnlineRecordDao {
    @Resource(name = "ios-mgnt-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public void insertOrIgnore(UrgentOnlineRecord record) throws SQLException {
        SQLUtils.insertOrUpdate(sqlMapClient, "insertOrIgnoreUrgentOnlineRecord", record);
    }

    @Override
    public List<UrgentOnlineRecord> queryList(Integer itemId, int start, int size) throws SQLException {
        Map<String, Object> param = SQLUtils.buildStartSizeMap(start, size);
        param.put("itemId", itemId);
        return SQLUtils.queryList(sqlMapClient, "queryUrgentOnlineRecordList", param);
    }

    @Override
    public long count() throws SQLException {
        return SQLUtils.count(sqlMapClient, "countUrgentOnlineRecord", null);
    }

    @Override
    public void update(List<UrgentOnlineRecord> records) throws SQLException {
        SQLUtils.batchInsertOrUpdate(sqlMapClient, "updateUrgentOnlineRecord", records);
    }

    @Override
    public List<Integer> queryRootIds(int start, int size) throws SQLException {
        return SQLUtils.queryList(sqlMapClient, "queryUrgentOnlineRecordRootIds",
                SQLUtils.buildStartSizeMap(start, size));
    }

    @Override
    public List<UrgentOnlineRecord> queryList(int endId, int size) throws SQLException {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("endId", endId);
        param.put("size", size);
        return SQLUtils.queryList(sqlMapClient, "queryNoRootIdUrgentOnlineRecordList", param);
    }
}
