package com.garten.Thread;

import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.BigcontrolService;
import com.garten.vo.attendance.TeacherAndBabyInfo;

public class DeleteGartenBaby implements Runnable{

	private List<TeacherAndBabyInfo> babys;
	
	public DeleteGartenBaby(List<TeacherAndBabyInfo> babys) {
		this.babys = babys;
	}

	@Override
	public void run() {
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		BigcontrolService  bigcontrolService = (BigcontrolService)context.getBean("bigcontrolService");
		bigcontrolService.deleteGartenBaby(babys);
	}

}
