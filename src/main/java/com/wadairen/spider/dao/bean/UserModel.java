package com.wadairen.spider.dao.bean;

import java.io.Serializable;

public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public UserModel(String username,String password){
		setUsername(username);
		setPassword(password);
	}
	
	public UserModel(){}
	
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private long uid;
	
	private String username;
	
	private String password;

}
