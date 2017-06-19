package com.garten.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garten.dao.TeacherDao;
import com.garten.model.User;
import com.garten.model.worker.WorkerInfo;
import com.garten.service.UserService;


@Service("teacherService")
public class TeacherService{

	@Autowired
	private TeacherDao teacherDao;

	public Map<String, Object> login(String token) {
		Map<String,Object> result=new HashMap<String,Object>();
		System.err.println(token);
		WorkerInfo worker=teacherDao.findUserByToken(token);
		result.put("result", null==worker?"error":"success");
		return result;
	}
	
	

}
