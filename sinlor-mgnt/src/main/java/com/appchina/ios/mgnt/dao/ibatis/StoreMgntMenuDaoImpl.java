// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao.ibatis;

import org.springframework.stereotype.Repository;

import com.appchina.ios.mgnt.dao.AbsMgntMenuDao;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Repository("storeMgntMenuDao")
public class StoreMgntMenuDaoImpl extends AbsMgntMenuDao {
    @Override
    protected String getTbName() {
        return "appstore_mgnt_menu";
    }

}
