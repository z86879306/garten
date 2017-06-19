package com.garten.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garten.model.User;
import com.garten.service.UserService;




/**
 * 用户Controller层
 * @author Administrator
 *
 */

public class Test {
	public static void main(String[] args) {
		System.err.println(test1());
	}
	public static String test1(){
		Long lon=1497619757000l;
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sp.format(lon);
		
	}
	

}
