package com.garten.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garten.service.TeacherService;





/**
 * 用户Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("teacher")
public class TeacherController {
	/**
	 * 用户登录
	 * @param user
	 * @param request
	 * @return
	 */
	@Autowired
	private TeacherService teacherService;
	
	@RequestMapping("message")
	@ResponseBody
	public Map<String, Object> login(String token){
		Map<String,Object> result=teacherService.login(token);
		
		return result;
		
	}
	

}
