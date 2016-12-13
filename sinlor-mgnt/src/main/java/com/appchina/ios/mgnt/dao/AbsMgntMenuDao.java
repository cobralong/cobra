// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.appchina.ios.core.utils.SQLUtils;
import com.appchina.ios.mgnt.dto.MgntMenu;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public abstract class AbsMgntMenuDao implements MgntMenuDao {
    @Resource(name = "ios-mgnt-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public List<MgntMenu> queryMenus() throws SQLException {
        return SQLUtils.queryList(sqlMapClient, "queryMgntMenus", getTbName());
    }

    protected abstract String getTbName();

}
