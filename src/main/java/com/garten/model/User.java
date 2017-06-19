package com.garten.model;

import java.sql.Timestamp;

public class User {
	
	private String userName;
	private String password;
	private Integer id;
	private String token;
	private Long tokenTime;
	public User(String userName, String password, Integer id, String token, Long tokenTime) {
		super();
		this.userName = userName;
		this.password = password;
		this.id = id;
		this.token = token;
		this.tokenTime = tokenTime;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", id=" + id + ", token=" + token
				+ ", tokenTime=" + tokenTime + "]";
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getTokenTime() {
		return tokenTime;
	}
	public void setTokenTime(Timestamp tokenTime) {
		this.tokenTime = tokenTime.getTime();
	}
	
	
}
