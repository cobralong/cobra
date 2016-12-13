// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.appchina.ios.mgnt.dao.MgntMenuDao;
import com.appchina.ios.mgnt.dto.MgntMenu;
import com.appchina.ios.mgnt.model.MgntMenuWebModel;
import com.appchina.ios.mgnt.service.MgntMenuService;
import com.appchina.ios.web.exception.ServiceException;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Service("mgntMenuService")
public class MgntMenuServiceImpl implements MgntMenuService {
    @Autowired
    @Qualifier("mgntMenuDao")
    private MgntMenuDao mgntMenuDao;
    @Autowired
    @Qualifier("storeMgntMenuDao")
    private MgntMenuDao storeMgntMenuDao;

    @Override
    public List<MgntMenuWebModel> menus() {
        return menus(mgntMenuDao);
    }

    private List<MgntMenuWebModel> menus(MgntMenuDao menuDao) {
        List<MgntMenuWebModel> ret = new ArrayList<MgntMenuWebModel>();
        List<MgntMenu> menus;
        try {
            menus = menuDao.queryMenus();
        } catch (SQLException e) {
            throw ServiceException.getInternalException("无法获取菜单栏");
        }
        List<MgntMenu> waitAddToChildMenus = new ArrayList<MgntMenu>();
        for (MgntMenu mgntMenu : menus) {
            if (mgntMenu.getParent() == null) {
                MgntMenuWebModel rootMgntMenuWebModel = forMgntmenuWebModel(mgntMenu);
                ret.add(rootMgntMenuWebModel);
                addChild(rootMgntMenuWebModel, waitAddToChildMenus);
            } else {
                boolean isChild = false;
                for (MgntMenuWebModel mgntMenuWebModel : ret) {
                    if (mgntMenuWebModel.getId() == mgntMenu.getParent()) {
                        MgntMenuWebModel subMgntMenuWebModel = forMgntmenuWebModel(mgntMenu);
                        mgntMenuWebModel.getChildren().add(subMgntMenuWebModel);
                        addChild(subMgntMenuWebModel, waitAddToChildMenus);
                        isChild = true;
                        break;
                    }
                }
                if (isChild) {
                    continue;
                }
                waitAddToChildMenus.add(mgntMenu);
            }
        }
        return ret;
    }

    private void addChild(MgntMenuWebModel mgntMenuWebModel, List<MgntMenu> waitAddToChildMenus) {
        Iterator<MgntMenu> iterator = waitAddToChildMenus.iterator();
        while (iterator.hasNext()) {
            MgntMenu mgntMenu = iterator.next();
            if (mgntMenuWebModel.getId() == mgntMenu.getParent()) {
                MgntMenuWebModel subMgntMenuWebModel = forMgntmenuWebModel(mgntMenu);
                mgntMenuWebModel.getChildren().add(subMgntMenuWebModel);
                iterator.remove();
                addChild(subMgntMenuWebModel, waitAddToChildMenus);
            }
        }
    }

    private MgntMenuWebModel forMgntmenuWebModel(MgntMenu mgntMenu) {
        MgntMenuWebModel ret = new MgntMenuWebModel();
        ret.setId(mgntMenu.getId());
        ret.setName(mgntMenu.getName());
        ret.setParent(mgntMenu.getParent());
        ret.setUrl(mgntMenu.getUrl());
        ret.setChildren(new ArrayList<MgntMenuWebModel>());
        return ret;
    }

    @Override
    public List<MgntMenuWebModel> storeMenus() {
        return menus(storeMgntMenuDao);
    }

}
