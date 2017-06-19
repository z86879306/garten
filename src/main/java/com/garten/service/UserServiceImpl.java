package com.garten.service;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.garten.dao.UserDao;
import com.garten.model.User;
import com.garten.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	public User getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	public Set<String> getRoles(String userName) {
		return userDao.getRoles(userName);
	}

	public Set<String> getPermissions(String userName) {
		return userDao.getPermissions(userName);
	}

}
