package com.garten.dao;

import java.util.Set;

import com.garten.model.User;

public interface UserDao {

	public User getByUserName(String userName);
	public Set<String> getRoles(String userName);
	public Set<String> getPermissions(String userName);
	public void updateToken(User user);
}
