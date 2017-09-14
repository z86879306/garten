package com.garten.Thread;

import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.BigcontrolService;
import com.garten.vo.attendance.TeacherAndBabyInfo;

public class DeleteGartenTeacher implements Runnable{

	private List<TeacherAndBabyInfo> teachers;
	
	
	public DeleteGartenTeacher(List<TeacherAndBabyInfo> teachers) {
		this.teachers = teachers;
	}


	@Override
	public void run() {
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		BigcontrolService  bigcontrolService = (BigcontrolService)context.getBean("bigcontrolService");
		bigcontrolService.deleteGartenTeacher(teachers);
		
	}

}
