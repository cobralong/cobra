// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import java.util.List;

import com.appchina.ios.mgnt.dto.ClientUpgradeHistory;
import com.appchina.ios.web.exception.ServiceException;

/**
 * 客户端升级服务
 * 
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface ClientUpgradeHistoryService {

    long count(String channel, Integer test, Integer status) throws ServiceException;

    List<ClientUpgradeHistory> list(String channel, Integer test, Integer status, int start, int size)
            throws ServiceException;

    int getTestIpaCount() throws ServiceException;

    int getProductIpaCount() throws ServiceException;

    void saveIpaFile(String upgradeInfo, Integer test) throws ServiceException;

    void delete(int id) throws ServiceException;

    ClientUpgradeHistory get(int id) throws ServiceException;

    /**
     * 发布此版本，入应用库
     * 
     * @param id
     * @param supportUpgrade
     * @param acceptUpgrade
     * @param upgradeCount
     * @throws ServiceException
     */
    void publish(int id, int supportUpgrade, int acceptUpgrade, Integer upgradeCount) throws ServiceException;


}
