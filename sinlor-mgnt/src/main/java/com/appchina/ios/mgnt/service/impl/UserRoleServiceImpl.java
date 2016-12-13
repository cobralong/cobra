package com.appchina.ios.mgnt.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appchina.ios.mgnt.dao.UserRoleDao;
import com.appchina.ios.mgnt.service.UserRoleService;
import com.appchina.ios.web.exception.ServiceException;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
    private UserRoleDao userRoleDao;
	
	@Override
	public void addUserRole(int userId) throws ServiceException {
		// TODO Auto-generated method stub
		try{
			this.userRoleDao.insertUserRole(userId, "ROLE_USER");
		}catch (SQLException e){
            throw ServiceException.getSQLException("插入用户权限时发生数据库错误.");
		}
	}

	@Override
	public List<String> getUserRole(String userName) throws ServiceException {
		// TODO Auto-generated method stub
		try{
			return this.userRoleDao.getUserRole(userName);
		}catch (SQLException e){
            throw ServiceException.getSQLException("获取用户权限时发生数据库错误.");
		}
		
	}
	

}
