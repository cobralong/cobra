// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.dao.ibatis;

import org.springframework.stereotype.Repository;

import com.appchina.ios.mgnt.dao.AbsMgntMenuDao;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Repository("mgntMenuDao")
public class MgntMenuDaoImpl extends AbsMgntMenuDao {
    @Override
    protected String getTbName() {
        return "mgnt_menu";
    }

}
