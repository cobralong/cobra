package com.appchina.ios.mgnt.dao;

import java.sql.SQLException;
import java.util.List;

public interface UserRoleDao {
	void insertUserRole(int userId,String authority) throws SQLException;
	List<String> getUserRole(String userName) throws SQLException;
}
