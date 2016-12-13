// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import java.util.List;

import com.appchina.ios.mgnt.model.MgntMenuWebModel;


/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface MgntMenuService {

    List<MgntMenuWebModel> menus();
    List<MgntMenuWebModel> storeMenus();
}
