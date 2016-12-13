// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service;

import java.util.List;

import com.appchina.ios.web.exception.ServiceException;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public interface UserRoleService {
	
	void addUserRole(int userId) throws ServiceException;
	List<String> getUserRole(String userName) throws ServiceException;
}
