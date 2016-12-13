package com.appchina.ios.mgnt.dto;

public class User {
	private int userId;
	private String userName;
	private String userRole;
	
	public User() {
		super();
    }
	
	public User(int userId, String userName,String userRole) {
		super();
        this.setUserId(userId);
        this.setUserName(userName);
        this.setUserRole(userRole);
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
