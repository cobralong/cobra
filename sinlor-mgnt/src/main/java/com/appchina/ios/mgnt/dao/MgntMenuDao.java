// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao;

import java.sql.SQLException;
import java.util.List;

import com.appchina.ios.mgnt.dto.MgntMenu;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface MgntMenuDao {

    List<MgntMenu> queryMenus() throws SQLException;

}
